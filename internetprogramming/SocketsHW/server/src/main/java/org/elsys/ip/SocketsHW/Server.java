package org.elsys.ip.SocketsHW;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private final Map<String, ClientHandler> clients = new HashMap<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            ClientHandler client = new ClientHandler(serverSocket.accept(), this);
            client.start();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private final Server server;
        private String name;

        public ClientHandler(Socket socket, Server server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

//                name = in.readLine();
//                server.addToMap(name, this);

                while (true) {
                    String line = in.readLine().toLowerCase(Locale.ROOT);
                    System.out.println(line);
                    if (line == null) {
                        break;
                    }
                    if (line.equals("exit") || line.equals("quit")){
                        clientSocket.close();
                        dispose();
                    }
                    //server.printlnAll(line);
                }
            } catch (Throwable t) {
                System.out.println(t.getMessage());
            } finally {
                dispose();
            }
        }

        private void dispose() {
            try {
                server.removeClient(name);
                if (clientSocket != null) clientSocket.close();
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Throwable t) {
                System.out.println(t.getMessage());
            }
        }
    }

    private void addToMap(String name, ClientHandler client) {
        clients.put(name, client);
    }

    private void removeClient(String name) {
        clients.remove(name);
    }

//    private void printlnAll(String line) {
//            clients.values().forEach(c -> c.out.println(" > " + line));
//    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        if(args.length == 0 || Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) >= 65536){
            System.err.println("invalid arguments");
            System.exit(1);
        }

        try{
            Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            System.err.println("invalid arguments");
            System.exit(1);
        }
        try {
            server.start(Integer.parseInt(args[0]));
        }catch (BindException e){
            System.err.println("port is already in use");
            System.exit(2);
        }
    }
}