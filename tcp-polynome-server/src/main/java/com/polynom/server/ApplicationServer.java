package com.polynom.server;

import com.polynom.server.exception.TCPException;
import com.polynom.server.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 **/

public class ApplicationServer {

    private static final Logger logger = LogManager.getLogger(ApplicationServer.class);


    public static void main(String[] args) throws TCPException {
        int port = 8005;
        if(args != null && args.length>0) {
            port = Integer.parseInt(args[0]);
        }
        int fileAttente = 100;
        if(args != null && args.length>1) {
            fileAttente = Integer.parseInt(args[1]);
        }
        System.out.println("*************************************************************");
        System.out.println("******************** TCP POLYNOM SERVER *********************");
        System.out.println("*************************************************************");
        System.out.println("Lancement du serveur port: " + port);
        Server server = new Server(port, fileAttente);
        server.start();
        server.open();
        System.out.println("Serveur lancer");
        System.out.println("En attente de connexions  ... ");
    }

}
