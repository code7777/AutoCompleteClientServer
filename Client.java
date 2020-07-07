import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
//import java.io.File;

/*Input stream (in) reads from server and PrintWriter (out) sends requests to it. 
Likewise, for Server.java*/
//https://stackoverflow.com/questions/7166328/when-why-to-call-system-out-flush-in-java
//https://stackoverflow.com/questions/27117567/java-transfer-a-file-from-server-to-client-and-from-client-to-server
//https://coderanch.com/t/206557/java/send-message-Server-Client
public class Client {
    private int port;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private String fileName;
    private String prefix;

    public String getfileName() {
        return fileName;
    }

    // Setter
    public void setFileName(String newName) {
        this.fileName = newName;
    }

    public String getPrefix() {
        return prefix;
    }

    // Setter
    public void setPrefix(String newPrefix) {
        this.prefix = newPrefix;
    }

    public void init() {
        try {
            try {
                socket = new Socket("localhost", port);
                System.out.println("Socket created on the local port " + socket.getLocalPort());
                System.out.println("A connection established with the remote port " + socket.getPort() + " at " + socket.getInetAddress().toString());
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream());
                System.out.println("I/O setup done.");
                sendCommands();
            } finally {
                socket.close();
            }
        } catch (IOException exception) {
        }
    }

    public void sendCommands() {
        System.out.println( "Hello Server!");
        out.println(fileName);
        out.println(prefix);
        out.flush();

        // implement a solution to use getResponse() to accept every line from server
        System.out.println(getResponse());

        sendCommand("QUIT\n");
    }

    public void sendCommand(String command) {
        out.print(command);
        out.flush();
        try {
            socket.close();
            System.out.println("A connection closed.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public String getResponse() {
        return in.nextLine();
    }

    public static void main(String[] args) {
        Client client = new Client();
        int i = Integer.parseInt(args[0]);
        client.port = i;
        if (args.length > 1) {
            System.out.println("args 1 is " + args[1]);
            client.setFileName(args[1]);
        }
        if (args.length > 2) {
            System.out.println("args 2 is " + args[2]);
            client.setPrefix(args[2]);
        }
        //System.out.println("the args length is" + args.length);
        client.init();
    }
}
