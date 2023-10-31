package com.client;

import com.shared.CancelVisitMessage;
import com.shared.ReserveVisitMessage;
import com.shared.Visit;
import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private int myId;
    private ObservableList<Visit> visitsList;

    public ServerHandler(ObservableList<Visit> visitsList){
        try{
            this.visitsList = visitsList;

            String address = "localhost";
            int port = 9876;

            this.socket = new Socket(address, port);
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());

            this.myId = inputStream.readInt();
            ArrayList<Visit> visitsFromServer = (ArrayList<Visit>)inputStream.readObject();

            this.visitsList.addAll(visitsFromServer);

            Thread listener = new Thread(this::listenerMessagesFromServer);
            listener.setDaemon(true);
            listener.start();

        }catch(IOException e){
            System.out.println("Server doesn't respond\n");
            System.exit(-1);
        }catch(ClassNotFoundException e){
            System.out.println("Problem with serializable class\n");
            closeConnectionWithServer();
            System.exit(-1);
        }
    }

    public void listenerMessagesFromServer(){
        try{
            while(socket.isConnected()){
                Object object = inputStream.readObject();
                if(object instanceof Visit){
                    Platform.runLater(() -> {
                        Visit visit = (Visit)object;
                        int visitId = visit.getVisitId();
                        visitsList.set(visitId, visit);
                    });
                }
            }
        }catch(Exception e){
            closeConnectionWithServer();
        }

    }

    public void reserveVisit(Visit visit){
        try{
            ReserveVisitMessage reserveVisitMessage = new ReserveVisitMessage(visit, myId);
            this.outputStream.writeObject(reserveVisitMessage);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void cancelVisit(Visit visit){
        try{
            CancelVisitMessage reserveVisitMessage = new CancelVisitMessage(visit, myId);
            this.outputStream.writeObject(reserveVisitMessage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getMyId() {
        return myId;
    }

    public void closeConnectionWithServer(){
        try{
            if(inputStream != null ) inputStream.close();
            if(outputStream != null) outputStream.close();
            if(socket != null) socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
