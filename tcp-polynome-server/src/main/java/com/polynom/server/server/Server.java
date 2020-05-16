package com.polynom.server.server;

import com.polynom.server.exception.TCPException;
import com.polynom.server.exception.TCPStartException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 **/

public class Server {
    private final int port;
    private boolean isRunning = true;
    private static final Logger logger = LogManager.getLogger(Server.class);

    /**
     * Nombre limite de sockets client que notre serveur peut tolérer avant de rejeter les demandes.
     */
    private final int fileAttente;
    private ServerSocket server;

    public Server(int port, int fileAttente) {
        this.port = port;
        this.fileAttente = fileAttente;
    }

    public void start() throws TCPException {
        try {
            server = new ServerSocket(port, fileAttente);
        } catch (IOException e) {
            throw new TCPStartException(e);
        }
    }

    //On lance notre serveur
    public void open(){

        //Toujours dans un thread à part vu qu'il est dans une boucle infinie
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(isRunning == true){

                    try {
                        //On attend une connexion d'un client
                        Socket client = server.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        logger.info("Connexion cliente reçue.");
                        Thread t = new Thread(new ClientProcessor(client));
                        t.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    server = null;
                }
            }
        });

        t.start();
    }

    public void close(){
        isRunning = false;
    }
}
