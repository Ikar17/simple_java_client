package com.shared;

import java.io.Serializable;

public class ReserveVisitMessage implements Serializable {

    private Visit visit;

    public ReserveVisitMessage(Visit visit) {
        this.visit = visit;
    }

    public Visit getVisit() {
        return visit;
    }

}
