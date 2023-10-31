package com.shared;

import java.io.Serializable;
import java.time.LocalTime;

public class Visit implements Serializable {
    private int visitId;
    private LocalTime visitStartTime;
    private LocalTime visitEndTime;
    private boolean reserved;
    private int clientId;

    public Visit(){};
    public Visit(int visitId, LocalTime visitStartTime, LocalTime visitEndTime) {
        this.visitId = visitId;
        this.visitStartTime = visitStartTime;
        this.visitEndTime = visitEndTime;
        this.reserved = false;
        this.clientId = -1;
    }

    @Override
    public boolean equals(Object object){
        if(object == this) return true;
        if (!(object instanceof Visit)) return false;

        Visit visit = (Visit) object;
        return visit.getVisitId() == this.visitId;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public LocalTime getVisitStartTime() {
        return visitStartTime;
    }

    public void setVisitStartTime(LocalTime visitStartTime) {
        this.visitStartTime = visitStartTime;
    }

    public LocalTime getVisitEndTime() {
        return visitEndTime;
    }

    public void setVisitEndTime(LocalTime visitEndTime) {
        this.visitEndTime = visitEndTime;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
