package dms.assignment2;

import javax.swing.*;

//Class to precise who is connected : Client or Server
public class ClientServer {

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
            String IPServer = JOptionPane.showInputDialog("Enter the Server ip adress");
            String[] arguments = new String[]{IPServer};
            new Assignment2().main(arguments);
        }

    }

}
