package connection_oriented;

import java.net.*;
import java.io.*;

public class EchoServer {
    static boolean connectionFlag = false;
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = createServerSocket(portNumber);
                Socket clientSocket = serverSocketAccept(serverSocket);
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            if (connectionFlag == true) {
                out.println("?##" + portNumber + "##?");
            }
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                String echo = inputLine + "$$$";
                out.println(echo);
                System.out.println("Echoed: " + echo);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static ServerSocket createServerSocket(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Server is created and listening at port: " + portNumber + ".........!");
        return serverSocket;
    }

    public static Socket serverSocketAccept(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        connectionFlag = true;
        System.out.println("Client connected...!");
        return socket;
    }
}