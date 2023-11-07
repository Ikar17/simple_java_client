package com.shared;

import java.io.Serializable;

public class CancelVisitMessage implements Serializable {
    private Visit visit;

    public CancelVisitMessage(Visit visit) {
        this.visit = visit;
    }

    public Visit getVisit() {
        return visit;
    }

}
