package com.polynom.server.request;

import java.util.List;

/**
 *
 **/

public class OperationRequest {
    private String operation;
    private List<String> polynoms;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<String> getPolynoms() {
        return polynoms;
    }

    public void setPolynoms(List<String> polynoms) {
        this.polynoms = polynoms;
    }

    @Override
    public String toString() {
        return operation + polynoms;
    }
}
