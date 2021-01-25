package project.Observer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Observer extends Thread implements ObserverInterface {
    static String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(600000);
                if (!status.equals("Nowe zlecenie")) {
                    DataInputStream dis = null;
                    DataOutputStream dos = null;
                    try {
                        InetAddress ip = InetAddress.getByName("localhost");
                        Socket s = new Socket(ip, 5057);
                        dis = new DataInputStream(s.getInputStream());
                        dos = new DataOutputStream(s.getOutputStream());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dos.writeInt(0);
                    setStatus(dis.readUTF());
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
