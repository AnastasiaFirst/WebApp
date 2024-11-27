import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {

    public static void main(String[] args) {
        String hostname = "localhost"; // Адрес сервера
        int port = 12345; // Номер порта сервера

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String message = "Привет, сервер!";
            out.println(message); // Отправляем строку на сервер
            System.out.println("Сообщение отправлено: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
