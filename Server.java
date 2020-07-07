import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.*;
import java.lang.*;
import java.util.Arrays;

public class Server {
    private int port;
    private ServerSocket serverSocket;

    /*A method to initialise the server. The method getLocalPort will give the port number that your system used to create a socket for this process on your system. It is random and the one available. Method to get Inet address-- would give you the local IP adrees at which the socket was created for you*/
    public void init() {
        try {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Socket created.");
                while (true) {
                    System.out.println("Listening for a connection on the local port " + serverSocket.getLocalPort() + "...");
                    /*The accept method would be called forth when client tries to bind to this server i.e. when you compile and run Client.java*/
                    final Socket socket = serverSocket.accept();
                    System.out.println("\nA connection established with the remote port " + socket.getPort() + " at "
                            + socket.getInetAddress().toString());
                    executeCommand(socket);
                }
            } finally {
                serverSocket.close();
            }
        } catch (final IOException exception) {
        }
    }

    private void executeCommand(final Socket socket) {
        try {
            try {
                final Scanner in = new Scanner(socket.getInputStream());
                final PrintWriter out = new PrintWriter(socket.getOutputStream());
                System.out.println("I/O setup done");
                while (true) {
                    if (in.hasNext()) {
                        final String command = in.next();
                        if (command.equals("QUIT")) {
                            System.out.println("QUIT: Connection being closed.");
                            out.print("QUIT accepted. Connection being closed.\n");
                            out.flush();
                            return;
                        } else {
                            accessAutocomplete(command, in, out);
                        }
                    }
                }
            } finally {
                socket.close();
                System.out.println("A connection is closed.");
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    /**/
    /*
     * For the exercose in the HW, replace this method with a method called
     * accessAutocomplete. Create a new object of Autocomplete and take cue from the
     * main method of Autocomplete and the following method to send query results to
     * Client's console
     */
    private void accessAutocomplete(final String command, final Scanner in, final PrintWriter out) {
        String fileName = command;
        String prefix = in.next();
        System.out.println("Hello Client! FileName is " + fileName);
        System.out.println("Hello Client! prefix is " + prefix);

        In File_in = new In(fileName);
        int N = File_in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = File_in.readLong();
            File_in.readChar();
            String query = File_in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }

        Autocomplete autocomplete = new Autocomplete(terms);
        Term[] sendToClient = autocomplete.allMatches(prefix);
        for(int i=0; i < sendToClient.length; i++) {
            out.println(sendToClient[0]);
        }
        out.flush();
    }

    public static void main(final String[] args) {
        final Server server = new Server();/*
         * Have a number greater than 1023. Numbers 0 throuh 1023 have been
         * standardized. A port number once used cannot be reused, unless you kill the
         * process by closing the console, just Ctrl-Z/D wouldn't work. You will have to
         * close the terminal
         */
        final int i = Integer.parseInt(args[0]);
        server.port = i;
        server.init();
    }
}
