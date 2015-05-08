package dms.assignment2;

import dms.assignment2.model.SocketInputReader;
import dms.assignment2.view.ChatFrame;
import javax.swing.JFrame;

/**
 * For PC end client in chatting room
 *
 * @author yl
 */
public class PcClient {

    public static void main(String[] args) {
        String server = args[0];
        int port = 8083;
        SocketInputReader access = new SocketInputReader(server, port);
        JFrame frame = new ChatFrame(access);
        frame.setTitle("MyChatApp - connected to " + server + ":" + port);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
