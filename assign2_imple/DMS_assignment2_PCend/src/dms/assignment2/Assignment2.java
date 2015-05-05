package dms.assignment2;

import javax.swing.*;

/**
 * application entry point, either start a server or pc client
 *
 * @author yl
 */
public class Assignment2 {

    public enum Type {

        SERVER, CLIENT
    }

    public static void main(String[] args) {

        Type initType = Type.SERVER;

        Object selection = JOptionPane.showInputDialog(null, "Welcome to DMS assignment 2\nLogin as : ", "MyChatApp", JOptionPane.QUESTION_MESSAGE, null, Type.values(), initType);
        if (selection.equals(Type.SERVER)) {
            String[] arguments = new String[]{};
            new MultiThreadChatServer().main(arguments);
        } else if (selection.equals(Type.CLIENT)) {

            String serverAddress;
            do {
                serverAddress = JOptionPane.showInputDialog("Enter the Server ip adress");
            } while (serverAddress == null || serverAddress.split("\\.").length != 4);

            String[] arguments = new String[]{serverAddress};
            new PcClient().main(arguments);
        }

    }

}
