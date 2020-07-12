package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;

    public Server() {


        ServerSocket server = null;
        Socket socket = null;


        try {
            server = new ServerSocket(8191);
            System.out.println("Сервер запущен");
            clients = new Vector<>();


            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                connect(new ClientHandler(this, socket));
            }




        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void connect(ClientHandler client) {
        clients.add(client);
    }
    public void disconnect(ClientHandler client) {
        clients.remove(client);
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler c: clients) {
            c.sendMsg(msg);
        }
    }
}
