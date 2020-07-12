package Dz;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServerMain {
    //2. Написать консольный вариант клиент\серверного приложения, в котором пользователь может писать сообщения,
    // как на клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать "Привет",
    // нажать Enter то сообщение должно передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной стороне,
    // сообщение соответственно передается клиенту и печатается у него в консоли. Есть одна особенность,
    // которую нужно учитывать: клиент или сервер может написать несколько сообщений подряд, такую ситуацию необходимо корректно обработать
    public static void main(String[] args)  {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            socket = server.accept();

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner console = new Scanner(System.in);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String str = in.nextLine();
                        if(str.equals("/end")) {
                            out.println("/end");
                            break;
                        }
                        System.out.println("Client " + str);
                    }
                }
            });
            t1.start();

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Введите сообщение");
                        String str = console.nextLine();
                        System.out.println("Сообщение отправлено!");
                        out.println(str);
                    }
                }
            });
            t2.setDaemon(true);
            t2.start();

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
}