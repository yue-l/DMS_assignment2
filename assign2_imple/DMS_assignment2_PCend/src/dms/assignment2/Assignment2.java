package dms.assignment2;

import dms.assignment2.model.ChatAccess;
import dms.assignment2.view.ChatFrame;
import java.io.IOException;
import javax.swing.JFrame;

// Class to manage Client chat Box.
public class Assignment2 {

    public static void main(String[] args) {
        String server = args[0];
        int port = 8083;
        ChatAccess access = null;
        try {
            access = new ChatAccess(server, port);
        } catch (IOException ex) {
            System.out.println("Cannot connect to " + server + ":" + port);
            ex.printStackTrace();
            System.exit(0);
        }
        JFrame frame = new ChatFrame(access);
        frame.setTitle("MyChatApp - connected to " + server + ":" + port);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
