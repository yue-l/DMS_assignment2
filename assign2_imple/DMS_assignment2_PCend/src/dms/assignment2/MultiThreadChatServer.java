package dms.assignment2;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * Server class, can run under cli environment with threading features
 *
 * @author yl
 */
public class MultiThreadChatServer {

    private static ServerSocket SERVER_SOCKET = null;
    private static Socket CLIENT_SOCKET = null;
    private static final int MAX_CLIENT_LIMIT = 30;
    private static final ClientThread[] CLIENT_THREADS = new ClientThread[MAX_CLIENT_LIMIT];

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
            SERVER_SOCKET = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        // server will listen to connection and disconnection
        while (true) {
            try {
                CLIENT_SOCKET = SERVER_SOCKET.accept();
                int i = 0;
                for (i = 0; i < MAX_CLIENT_LIMIT; i++) {
                    if (CLIENT_THREADS[i] == null) {
                        System.out.println("create client thread: " + i);
                        (CLIENT_THREADS[i] = new ClientThread(CLIENT_SOCKET, CLIENT_THREADS)).start();
                        break;
                    }
                }
                if (i == MAX_CLIENT_LIMIT) {
                    PrintStream os = new PrintStream(CLIENT_SOCKET.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    CLIENT_SOCKET.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
