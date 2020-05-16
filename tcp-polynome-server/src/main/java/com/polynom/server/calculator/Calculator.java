package com.polynom.server.calculator;

import com.polynom.server.exception.InvalidOperationRequest;
import com.polynom.server.request.OperationRequest;

import java.util.List;

/**
 *
 **/

public class Calculator {

    public static String handleOperationRequest(OperationRequest operationRequest) throws InvalidOperationRequest {
        if (operationRequest == null) {
            throw new InvalidOperationRequest("operationRequest must not be null");
        }
        if (operationRequest.getOperation() == null) {
            throw new InvalidOperationRequest("operation must not be null");
        }
        if (operationRequest.getPolynoms() == null) {
            throw new InvalidOperationRequest("Polynom must not be null");
        }
        switch (operationRequest.getOperation().toUpperCase()) {
            case "ADD":
                return add(operationRequest.getPolynoms()).toString();
            case "SUS":
                return sus(operationRequest.getPolynoms()).toString();
            case "MUL":
                return mul(operationRequest.getPolynoms()).toString();
            case "DIV":
                return div(operationRequest.getPolynoms()).toString();
            case "DIR":
                return dir(operationRequest.getPolynoms()).toString();
            case "INTG":
                return integ(operationRequest.getPolynoms()).toString();

            default:
                throw new InvalidOperationRequest("operation non supporté : " + operationRequest.getOperation());
        }
    }

    private static Polynom dir(List<String> polynoms) throws InvalidOperationRequest {
        return new Polynom(polynoms.get(0)).derivative();
    }

    private static Polynom div(List<String> polynoms) throws InvalidOperationRequest {
        throw new InvalidOperationRequest("operation non supporté : division");
    }

    private static Polynom integ(List<String> polynoms) throws InvalidOperationRequest {
        throw new InvalidOperationRequest("operation non supporté :integral");
    }

    private static Polynom mul(List<String> polynoms) {
        if (polynoms == null || polynoms.isEmpty()) {
            return new Polynom("0");
        }
        Polynom result = new Polynom(polynoms.get(0));
        for (int i = 1; i < polynoms.size(); i++) {
            result.multiply(new Polynom(polynoms.get(i)));
        }
        return result;
    }

    private static Polynom sus(List<String> polynoms) {
        if (polynoms == null || polynoms.isEmpty()) {
            return new Polynom("0");
        }
        Polynom result = new Polynom(polynoms.get(0));
        for (int i = 1; i < polynoms.size(); i++) {
            result.substract(new Polynom(polynoms.get(i)));
        }
        return result;
    }

    private static Polynom add(List<String> polynoms) {
        if (polynoms == null || polynoms.isEmpty()) {
            return new Polynom("0");
        }
        Polynom result = new Polynom(polynoms.get(0));
        for (int i = 1; i < polynoms.size(); i++) {
            result.add(new Polynom(polynoms.get(i)));
        }
        return result;
    }
}
