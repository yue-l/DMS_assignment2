/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.assignment2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author yl
 */
public class ClientThread extends Thread {

    private final int MAX_CLIENT_COUNT;
    private final ClientThread[] threads;

    private String clientName = null;
    private DataInputStream is = null;
    private PrintStream os = null;
    private Socket clientSocket = null;

    public ClientThread(Socket clientSocket, ClientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        MAX_CLIENT_COUNT = threads.length;
    }

    @Override
    public void run() {
        int localClientsLimit = this.MAX_CLIENT_COUNT;
        ClientThread[] threads = this.threads;

        try {
            //Create input and output streams for this client.
            is = new DataInputStream(clientSocket.getInputStream());
            try (Scanner scan = new Scanner(is)) {
                os = new PrintStream(clientSocket.getOutputStream());
                String name;
                while (true) {
                    os.println("Enter your name.");
                    name = scan.nextLine().trim();
                    if (name.indexOf('@') == -1) {
                        break;
                    } else {
                        os.println("The name should not contain '@' character.");
                    }
                }
                // new client               
                os.println("Welcome " + name
                        + " to our chat room."
                        + "\nTo leave enter /quit in a new line.");
                clientEnteringUpdate(localClientsLimit, name);
                /* Start the conversation. blocking*/
                while (true) {
                    String line = null;
                    try {
                        line = scan.nextLine();
                    } catch (Exception ex) {
                        break;
                    }
                    if (line.startsWith("/quit")) {
                        break;
                    }
                    /* If the message is private sent it to the given client. */
                    if (line.startsWith("@")) {
                        String[] words = line.split("\\s", 2);
                        if (words.length > 1 && words[1] != null) {
                            words[1] = words[1].trim();
                            if (!words[1].isEmpty()) {
                                synchronized (this) {
                                    for (int i = 0; i < localClientsLimit; i++) {
                                        if (threads[i] != null && threads[i] != this
                                                && threads[i].clientName != null
                                                && threads[i].clientName.equals(words[0])) {
                                            threads[i].os.println("<" + name + "> " + words[1]);
                                            /*
                                             * Echo this message to let the client know the private
                                             * message was sent.
                                             */
                                            this.os.println(">" + name + "> " + words[1]);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        broadcastMessage(name, line, localClientsLimit);
                    }
                }
                broadcaseExiting(name, localClientsLimit);
                os.println("*** Bye " + name + " ***");
                terminateThread(localClientsLimit);
            }
            os.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    private synchronized void terminateThread(int localClientsLimit) {
        for (int i = 0; i < localClientsLimit; i++) {
            if (threads[i].equals(this)) {
                threads[i] = null;
            }
        }
    }

    private synchronized void broadcastMessage(String name, String line, int localClientsLimit) {
        for (int i = 0; i < localClientsLimit; i++) {
            if (threads[i] != null && threads[i].clientName != null) {
                threads[i].os.println("<" + name + "> " + line);
            }
        }
    }

    private synchronized void broadcaseExiting(String name, int localClientsLimit) {
        for (int i = 0; i < localClientsLimit; i++) {
            if (threads[i] != null && threads[i] != this
                    && threads[i].clientName != null) {
                threads[i].os.println("*** The user " + name
                        + " is leaving the chat room !!! ***");
            }
        }
    }

    private void clientEnteringUpdate(int localClientsLimit, String name) {
        for (int i = 0; i < localClientsLimit; i++) {
            if (threads[i] != null && threads[i] == this) {
                clientName = "@" + name;
                break;
            }
        }
        for (int i = 0; i < localClientsLimit; i++) {
            if (threads[i] != null && threads[i] != this) {
                threads[i].os.println("*** A new user " + name
                        + " entered the chat room !!! ***");
            }
        }
    }
}
