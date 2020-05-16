package com.polynom.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polynom.client.request.OperationRequest;
import com.polynom.client.request.OperationResult;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 **/

public class ApplicationClient {

    public static void main(String[] args) {

        System.out.println("*************************************************************");
        System.out.println("******************** TCP POLYNOMES CLIENT ********************");
        System.out.println("*************************************************************");

        int port = 8005;
        if (args != null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        String host = "localhost";
        if (args != null && args.length > 1) {
            host = args[1];
        }
        Socket connexion = null;
        for (int i = 0; i < 10; i++) {
            System.out.println("connexion serveur : " + host + ":" + port);
            try {
                connexion = new Socket(host, port);
                Thread.sleep(3 * 1000);
                break;
            } catch (IOException | InterruptedException e) {
                System.err.println("error de connexion  au serveur cause : " + e.getMessage());
            }
        }
        if (connexion == null) {
            System.err.println("Echec de connexion au serveur. Fermeture de programe");
            System.exit(2);
        }
        System.out.println("Connexion au serveur a été établie avec succès");
        PrintWriter writer = null;
        BufferedInputStream reader = null;
        try {
            writer = new PrintWriter(connexion.getOutputStream(), true);
            reader = new BufferedInputStream(connexion.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }

        boolean process = true;
        int polynomCount = 0;
        while (process) {
            String choix = printMenu();
            String operation = null;
            String operationDes = null;
            switch (choix.toUpperCase()) {
                case "+":
                case "1":
                    operation = "ADD";
                    operationDes = "Addition";
                    polynomCount = 2;
                    break;
                case "-":
                case "2":
                    operation = "SUS";
                    operationDes = "Soustraction";
                    polynomCount = 2;
                    break;
                case "*":
                case "x":
                case "3":
                    operation = "MUL";
                    operationDes = "Multiplication";
                    polynomCount = 2;
                    break;
                case "/":
                case "4":
                    operation = "DIV";
                    operationDes = "Division";
                    polynomCount = 2;
                    break;
                case "5":
                case "D/DT":
                    operation = "DIR";
                    operationDes = "Dérivation";
                    polynomCount = 1;
                    break;
                case "6":
                    operation = "INTG";
                    operationDes = "Integral";
                    polynomCount = 1;
                    break;
                case "7":
                case "Q":
                case "EXIT":
                    process = false;
                    break;
                default:
                    operation = null;
                    break;
            }
            if (process) {
                if (operation == null) {
                    System.err.println("Option invalide. Veuillez essayer");
                } else {
                    try {
                        System.out.println(operationDes + " polynomes");
                        List<String> polynomes = readPolynpmes(polynomCount);
                        OperationRequest operationRequest = new OperationRequest(operation, polynomes);
                        writer.write(new ObjectMapper().writeValueAsString(operationRequest));
                        writer.flush();
                        System.out.println("Commande " + operationRequest + " envoyée au serveur");

                        //On attend la réponse
                        String response = read(reader);
                        OperationResult result = null;
                        try {
                            result = new ObjectMapper().readValue(response, OperationResult.class);
                        } catch (IOException ex) {
                            System.err.println("Error de decodage reponse serveur" + ex.getMessage());
                        }

                        if (result != null) {
                            if (result.isSuccess()) {
                                System.out.println("---------------------------------------");
                                System.out.println("   Resultat : " + result.getPolynom());
                                System.out.println("---------------------------------------");
                            } else {
                                System.err.println("Operation echoue cause : " + result.getError());
                            }
                        }
                    } catch (IOException ex) {
                        System.err.println("Error inatendue" + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            }
            System.out.println("\n****************************************************\n");
            System.out.println("Appyer sur une touche pour continuer ...");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> readPolynpmes(int polynomCount) {

        List<String> polynomes = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < polynomCount; i++) {
            System.out.println("Veuillez saisir le polynome "+ (i+1)  +" avec le format suivant (a_1*X^b_1 + a_2*X^b_2 ... a_n*Xb_n)");
            polynomes.add(in.nextLine());
        }
        return polynomes;
    }

    static private String printMenu() {
        Scanner in = new Scanner(System.in);
        System.out.println("Choisissez une opération : ");
        System.out.println("    1- Addition (+)");
        System.out.println("    2- Soustraction (-)");
        System.out.println("    3- Multiplication (x)");
        System.out.println("    4- Division (/)");
        System.out.println("    5- Dérivation (d/dt)");
        System.out.println("    6- Integral");
        System.out.println("    7- Exist");
        return in.nextLine();
    }

    //Méthode pour lire les réponses du serveur
    private static String read(BufferedInputStream reader) throws IOException {
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

}
