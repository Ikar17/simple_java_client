package com.shared;

import java.io.Serializable;

public class ReserveVisitMessage implements Serializable {

    private Visit visit;
    private int clientId;
    public ReserveVisitMessage() {}

    public ReserveVisitMessage(Visit visit, int clientId) {
        this.visit = visit;
        this.clientId = clientId;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
