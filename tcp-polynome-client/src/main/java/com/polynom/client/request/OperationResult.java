package com.polynom.client.request;

/**
 *
 **/

public class OperationResult {
    private String polynom;
    private String error;
    private boolean success;

    public OperationResult() {
    }

    public OperationResult(String polynom, String error, boolean success) {
        this.polynom = polynom;
        this.error = error;
        this.success = success;
    }

    public String getPolynom() {
        return polynom;
    }

    public void setPolynom(String polynom) {
        this.polynom = polynom;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "{" +
                "polynom=" + polynom +
                ", error='" + error + '\'' +
                ", success=" + success +
                '}';
    }
}
