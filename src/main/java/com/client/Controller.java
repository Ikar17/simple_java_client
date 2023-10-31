package com.client;

import com.shared.Visit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private ServerHandler serverHandler;
    private ObservableList<Visit> visitsList;
    private ListView<Visit> visitsListView;
    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.visitsList = FXCollections.observableArrayList();
        this.visitsListView = new ListView<>();
        this.visitsListView.setItems(this.visitsList);

        this.serverHandler = new ServerHandler(this.visitsList);
        renderView();

        Platform.runLater(() -> {
            Stage stage = (Stage) container.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                this.serverHandler.closeConnectionWithServer();
            });
        });
    }

    private void renderView(){
        this.visitsListView.setCellFactory(param -> new ListCell<>(){
            private final Button button = new Button();
            @Override
            protected void updateItem(Visit visit, boolean empty){
                super.updateItem(visit, empty);

                if(empty || visit == null){
                    setText(null);
                    setGraphic(null);
                }else{
                    setText(visit.getVisitStartTime().toString() + " - " + visit.getVisitEndTime().toString());
                    if(!visit.isReserved()){
                        renderReserveButton(button,visit);
                    }else{
                        renderCancelButton(button, visit);
                    }
                    setGraphic(button);
                }
            }
        });

        this.container.getChildren().add(this.visitsListView);
    }

    public void renderReserveButton(Button button, Visit visit){
        button.setVisible(true);
        button.setText("Zarezerwuj");
        button.setOnAction( event ->{
            System.out.printf("Rezerwuje wizytę nr: %d\n", visit.getVisitId());
            serverHandler.reserveVisit(visit);
        });
    }

    public void renderCancelButton(Button button, Visit visit){
        if(visit.getClientId() == serverHandler.getMyId()){
            button.setText("Odwolaj wizyte");
            button.setOnAction( event ->{
                System.out.printf("Odwoluje wizytę nr: %d\n", visit.getVisitId());
                serverHandler.cancelVisit(visit);
            });
        }else{
            button.setVisible(false);
        }
    }

}