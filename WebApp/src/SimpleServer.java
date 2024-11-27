import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.in;

public class SimpleServer {

    public static void main(String[] args) {
        int port = 12345; // Номер порта для сервера

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Принимаем входящее соединение
                System.out.println("Подключено к клиенту: " + clientSocket.getPort());

                // Создаем новый поток для обработки клиента
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            final String name = in.readLine(); // Читаем строку из сокета
            out.println(String.format("Привет, %s, твой порт %d",  name, clientSocket.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); // Закрываем соединение
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
