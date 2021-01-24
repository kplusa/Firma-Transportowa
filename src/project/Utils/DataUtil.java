package project.Utils;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class DataUtil {
    public static String Name;
    public static String ClientType;
    public static InetAddress ip;
    public static DataInputStream dis;
    public static DataOutputStream dos;
    public static Socket s;

    @FXML
    private Label name;
    @FXML
    private  Label clientType;

    public static String getName() {
        return Name;
    }

    public void setName(String name, Label nameL) {
        Name = name;
        this.name = nameL;
        nameL.setText(name);
    }

    public static String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType, Label clientTypeL) {
        ClientType = clientType;
        this.clientType = clientTypeL;
        clientTypeL.setText(clientType);
    }

    public void show() {
        System.out.println(Name);
        System.out.println(ClientType);
    }

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2) {

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;
        return (c * r);
    }

    public  static void connectClient() {
        try {
            ip = InetAddress.getByName("localhost");
            s = new Socket(ip, 5057);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
