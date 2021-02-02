package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class ClientHandlerTest {


    ServerSocket serverSocket;
    Socket socket, socketServer;
    Connection conn;
    ClientHandler client;
    Statement stmt;
    {
        try {
            serverSocket = new ServerSocket(5057);
            InetAddress ip = InetAddress.getByName("localhost");
            socket = new Socket(ip, 5057);
            client = new ClientHandler(socket, null);
            Socket s=serverSocket.accept();
            JFXTextArea text = null;
            String url = "jdbc:sqlserver://DESKTOP-U746ETR\\SQLEXPRESS:1433";
            String username = "PIPpro";
            String password = "12345";
            conn = DriverManager.getConnection(url, username, password);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    public void getCourierIdTest() throws SQLException, IOException {
        //Given
        String email = "mroz.kamil@gmail.com";

        Integer result = client.getUserId(email, conn);

        Assertions.assertEquals(3, result);

        socket.close();
        serverSocket.close();
    }

    @org.junit.jupiter.api.Test
    public void getForwarderIdTest() throws SQLException, IOException {
        //Given
        String email = "dominik.tuora@gmail.com";

        Integer result = client.getUserId(email, conn);

        Assertions.assertEquals(2, result);

        socket.close();
        serverSocket.close();
    }

    @org.junit.jupiter.api.Test
    public void getUserIdTest() throws SQLException, IOException {
        //Given
        String email = "piotr.walasek@gmail.com";

        Integer result = client.getUserId(email, conn);

        Assertions.assertEquals(1, result);

        socket.close();
        serverSocket.close();
    }

    /*@org.junit.jupiter.api.Test
    public void getErrorIdTest() throws IOException {
        //Given
        String email = "ERROR";

        Assertions.assertThrows(SQLException.class, () -> client.getUserId(email, conn));

        socket.close();
        serverSocket.close();
    }*/

    /*@org.junit.jupiter.api.Test
    public void clientHandler_Option_41_Test() throws IOException, InterruptedException {

        InetAddress ip = InetAddress.getByName("localhost");
        socket = new Socket(ip, 5057);
        socket = serverSocket.accept();
        client = new ClientHandler(socket, null);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        dos.writeInt(41);
        dos.writeUTF("mroz.kamil@gmail.com");

        client.start();

        dis.readUTF();

    }*/
}