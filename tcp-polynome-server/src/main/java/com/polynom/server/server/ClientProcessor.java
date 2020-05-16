package com.polynom.server.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polynom.server.calculator.Calculator;
import com.polynom.server.request.OperationRequest;
import com.polynom.server.request.OperationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientProcessor implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    private static final Logger logger = LogManager.getLogger(ClientProcessor.class);
    public ClientProcessor(Socket pSock) {
        sock = pSock;
    }

    //Le traitement lancé dans un thread séparé
    public void run() {

        boolean closeConnexion = false;
        //tant que la connexion est active, on traite les demandes
        boolean precess = true;
        while (precess && !sock.isClosed()) {

            try {
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());
                String thread = "[" + Thread.currentThread().getName() + "]";

                OperationResult operationResult;
                String response = "uknown";
                try {
                    //On attend la demande du client
                    String request = read();
                    InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();
                    OperationRequest operationRequest = new ObjectMapper().readValue(request, OperationRequest.class);
                    logger.info("Request received from : {}:{}. request {} " , remote.getAddress().getHostAddress() , remote.getPort(),operationRequest);
                    String result = Calculator.handleOperationRequest(operationRequest);
                    operationResult = new OperationResult(result, null, true);
                    response = new ObjectMapper().writeValueAsString(operationResult);
                    logger.info("Operation result : {}" , operationResult);
                } catch (Exception ex) {
                    operationResult = new OperationResult(null, ex.getMessage(), false);
                    response = new ObjectMapper().writeValueAsString(operationResult);
                    logger.error("Operation fail" , ex);
                    precess = false;
                    break;
                }

                writer.write(response);
                writer.flush();
                if (closeConnexion) {
                    logger.error("COMMANDE CLOSE DETECTEE ! ");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
            } catch (SocketException e) {
                logger.error("LA CONNEXION A ETE INTERROMPUE ! ", e);
                break;
            } catch (IOException e) {
                logger.error("IO ERROR ! ", e);
            }
        }
    }

    //La méthode que nous utilisons pour lire les réponses
    private String read() throws IOException {
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

}