package com.polynom.client.request;

import java.util.List;

/**
 *
 **/

public class OperationRequest {

    public OperationRequest(String operation, List<String > polynoms) {
        this.operation = operation;
        this.polynoms = polynoms;
    }

    public OperationRequest() {
    }

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
        return "{" +
                "operation=" + operation +
                ", polynoms=" + polynoms +
                '}';
    }
}
