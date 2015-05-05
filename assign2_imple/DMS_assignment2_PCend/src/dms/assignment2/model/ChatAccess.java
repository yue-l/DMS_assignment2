/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dms.assignment2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ChatAcess will notify the changes to View. This is part of Observable and
 * Observer pattern
 *
 * @author yue
 */
public class ChatAccess extends Observable {

    private static final String CRLF = "\r\n"; // newline

    private Socket socket;
    private OutputStream outputStream;

    /**
     * Create socket, and receiving thread
     *
     * @param server
     * @param port
     */
    public ChatAccess(String server, int port) {
        try {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(ChatAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        notifyObservers(line);
                    }
                } catch (IOException ex) {
                    notifyObservers(ex);
                }
            }
        };
        receivingThread.start();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    /**
     * Send a line of text
     *
     * @param text
     */
    public void send(String text) {
        try {
            outputStream.write((text + CRLF).getBytes());
            outputStream.flush();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }

    /**
     * Close the socket
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
}
