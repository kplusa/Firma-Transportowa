package project.Facade;

import com.jfoenix.controls.JFXTextArea;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthenticationTest {

    ServerSocket serverSocket;
    Socket socket, socketServer;
    Connection conn;
    Statement stmt;
    {
        try {
            serverSocket = new ServerSocket(5057);
            InetAddress ip = InetAddress.getByName("localhost");
            socket = new Socket(ip, 5057);
            JFXTextArea text = null;
            String url = "jdbc:sqlserver://DESKTOP-U746ETR\\SQLEXPRESS:1433";
            String username = "PIPpro";
            String password = "12345";
            conn = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void ValidLoginTest() throws SQLException, IOException, InterruptedException {

        new Thread( () -> {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                UserAuthentication test = new UserAuthentication();
                stmt = conn.createStatement();
                test.login(stmt, dis, dos, null);
                socket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        socketServer = serverSocket.accept();
        DataOutputStream dos1 = new DataOutputStream(socketServer.getOutputStream());
        DataInputStream dis1 = new DataInputStream(socketServer.getInputStream());
        dos1.writeUTF("mroz.kamil@gmail.com");
        dos1.writeUTF("kurier");
        String expected = "Poprawne dane-Kurier", result;
        result = dis1.readUTF();
        assertEquals(expected, result);
        socketServer.close();
        serverSocket.close();
    }

    @Test
    void InvalidLoginTest() throws SQLException, IOException, InterruptedException {

        new Thread( () -> {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                UserAuthentication test = new UserAuthentication();
                stmt = conn.createStatement();
                test.login(stmt, dis, dos, null);
                socket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        socketServer = serverSocket.accept();
        DataOutputStream dos1 = new DataOutputStream(socketServer.getOutputStream());
        DataInputStream dis1 = new DataInputStream(socketServer.getInputStream());
        dos1.writeUTF("ERROR");
        dos1.writeUTF("kurier");
        String expected = "Niepoprawna nazwa uzytkownika lub haslo", result;
        result = dis1.readUTF();
        assertEquals(expected, result);
        socketServer.close();
        serverSocket.close();
    }

    @Test
    void PasswordNotMatchRegister() throws IOException {

        new Thread( () -> {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                UserAuthentication test = new UserAuthentication();
                stmt = conn.createStatement();
                test.register(stmt, dis, dos, null);
                socket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        socketServer = serverSocket.accept();
        DataOutputStream dos1 = new DataOutputStream(socketServer.getOutputStream());
        DataInputStream dis1 = new DataInputStream(socketServer.getInputStream());

        /*String mail = dataInputStream.readUTF();
        String pass = dataInputStream.readUTF();
        String pass2 = dataInputStream.readUTF();
        int phone = dataInputStream.readInt();
        String type = dataInputStream.readUTF();
        String firstname = dataInputStream.readUTF();
        String lastname = dataInputStream.readUTF();
        String city = dataInputStream.readUTF();
        String street = dataInputStream.readUTF();
        String number = dataInputStream.readUTF();
        String code = dataInputStream.readUTF();*/

        dos1.writeUTF("mroz.kamil@gmail.com");
        dos1.writeUTF("kurier");
        dos1.writeUTF("kurkier");
        dos1.writeInt(222555666);
        dos1.writeUTF("kurier");
        dos1.writeUTF("Piotrek");
        dos1.writeUTF("Falszywy");
        dos1.writeUTF("Kielce");
        dos1.writeUTF("Magiczna");
        dos1.writeUTF("25/21");
        dos1.writeUTF("25-000");
        String expected = "Hasla sie roznia", result;
        result = dis1.readUTF();
        assertEquals(expected, result);
        socketServer.close();
        serverSocket.close();
    }

    @Test
    void DuplicateUserRegister() throws IOException {

        new Thread( () -> {
            try {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                UserAuthentication test = new UserAuthentication();
                stmt = conn.createStatement();
                test.register(stmt, dis, dos, null);
                socket.close();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        socketServer = serverSocket.accept();
        DataOutputStream dos1 = new DataOutputStream(socketServer.getOutputStream());
        DataInputStream dis1 = new DataInputStream(socketServer.getInputStream());

        /*String mail = dataInputStream.readUTF();
        String pass = dataInputStream.readUTF();
        String pass2 = dataInputStream.readUTF();
        int phone = dataInputStream.readInt();
        String type = dataInputStream.readUTF();
        String firstname = dataInputStream.readUTF();
        String lastname = dataInputStream.readUTF();
        String city = dataInputStream.readUTF();
        String street = dataInputStream.readUTF();
        String number = dataInputStream.readUTF();
        String code = dataInputStream.readUTF();*/

        dos1.writeUTF("mroz.kamil@gmail.com");
        dos1.writeUTF("kurier");
        dos1.writeUTF("kurier");
        dos1.writeInt(222555666);
        dos1.writeUTF("kurier");
        dos1.writeUTF("Piotrek");
        dos1.writeUTF("Falszywy");
        dos1.writeUTF("Kielce");
        dos1.writeUTF("Magiczna");
        dos1.writeUTF("25/21");
        dos1.writeUTF("25-000");
        String expected = "Podany uzytkownik jest zarejestrowany", result;
        result = dis1.readUTF();
        assertEquals(expected, result);
        socketServer.close();
        serverSocket.close();
    }
}