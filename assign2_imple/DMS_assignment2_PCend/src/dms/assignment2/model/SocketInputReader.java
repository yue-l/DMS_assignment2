package dms.assignment2.model;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.*;

/**
 * ChatAcess will notify the changes to View. This is part of Observable and
 * Observer pattern
 *
 * @author yue
 */
public class SocketInputReader extends Observable {

    private static final String NEWLINE = "\r\n"; // newline

    private Socket socket;
    private OutputStream outputStream;

    /**
     * Create socket, and receiving thread
     *
     * @param server
     * @param port
     */
    public SocketInputReader(String server, int port) {
        try {
            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(SocketInputReader.class.getName()).log(Level.SEVERE, null, ex);
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
            outputStream.write((text + NEWLINE).getBytes());
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
