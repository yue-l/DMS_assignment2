package dms.assignment2;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Server class, can run under cli environment
 *
 * @author yl
 */
public class MultiThreadChatServer {

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsLimit = 10;
    private static final ClientThread[] clientThreads = new ClientThread[maxClientsLimit];

    public static void main(String args[]) {

        int portNumber = 8083;
        if (args.length < 1) {
            System.out.println("no port specified, using default: " + portNumber);
        } else if (Integer.valueOf(args[0]) < 1024) {
            System.out.println("First 1024 ports are reserved for the operating system"
                    + "\nDefault port is selected as server port");
        } else {
            portNumber = Integer.valueOf(args[0]);
        }

        /*
         * Open a server socket on the portNumber (default 2222). Note that we can
         * not choose a port less than 1023 if we are not privileged users (root).
         */
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        // server will listen to connection and disconnection
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsLimit; i++) {
                    if (clientThreads[i] == null) {
                        System.out.println("create client thread: " + i);
                        (clientThreads[i] = new ClientThread(clientSocket, clientThreads)).start();
                        break;
                    }
                }
                if (i == maxClientsLimit) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
