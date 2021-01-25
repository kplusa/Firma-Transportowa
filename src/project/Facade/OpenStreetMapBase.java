package project.Facade;

import com.jfoenix.controls.JFXTextArea;
import project.Utils.OpenStreetMapUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static project.Utils.DataUtil.distance;

public class OpenStreetMapBase {
    public void UpdateOrder(Statement stmt, Statement stmt2, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws IOException {
        Map<Integer, Double> toList = new HashMap<>();
        Map<Integer, Double> fromList = new HashMap<>();
        Map<Integer, String> branchList = new HashMap<>();
        int tmpint = dataInputStream.readInt();
        String from = dataInputStream.readUTF();
        String to = dataInputStream.readUTF();
        try {
            String sql;
            ResultSet rs=null;
            try {
                sql = "select * from FirmaTransportowa.dbo.Zlecenie Where FirmaTransportowa.dbo.Zlecenie.id='" + tmpint + "';";
                rs = stmt.executeQuery(sql);
                rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sql = "select * from  FirmaTransportowa.dbo.Oddzial";
            ResultSet rs2 = stmt2.executeQuery(sql);
            while (rs2.next()) {
                Integer id = rs2.getInt(1);
                String tmpstring = rs2.getString(2);
                branchList.put(id, tmpstring);
            }
            branchList.forEach((k, v) -> {
                Map<String, Double> coords;
                double latA = 0;
                double latB = 0;
                double latC = 0;
                double lonA = 0;
                double lonB = 0;
                double lonC = 0;
                coords = OpenStreetMapUtils.getInstance().getCoordinates(v);
                latA += coords.get("lat");
                lonA += coords.get("lon");
                coords = OpenStreetMapUtils.getInstance().getCoordinates(from);
                latB += coords.get("lat");
                lonB += coords.get("lon");
                coords = OpenStreetMapUtils.getInstance().getCoordinates(to);
                latC += coords.get("lat");
                lonC += coords.get("lon");
                fromList.put(k, distance(latA, latB, lonA, lonB));
                toList.put(k, distance(latA, latC, lonA, lonC));
            });
            Map.Entry<Integer, Double> minFromList = Collections.min(fromList.entrySet(), (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));
            Map.Entry<Integer, Double> minToList = Collections.min(toList.entrySet(), Map.Entry.comparingByValue());
            sql = "update FirmaTransportowa.dbo.Zlecenie set FirmaTransportowa.dbo.Zlecenie.dataNadania='" + rs.getString(2) + "',FirmaTransportowa.dbo.Zlecenie.adresPoczatkowy='" + from + "'," +
                    "FirmaTransportowa.dbo.Zlecenie.adresKoncowy='" + to + "',FirmaTransportowa.dbo.Zlecenie.status='" + rs.getString(5) + "',FirmaTransportowa.dbo.Zlecenie.uzytkownikId='" + rs.getString(6) + "',FirmaTransportowa.dbo.Zlecenie.oddzialPoczatkowyId='" + minFromList.getKey() + "',FirmaTransportowa.dbo.Zlecenie.oddzialKoncowyId='" + minToList.getKey() + "'" +
                    " where FirmaTransportowa.dbo.Zlecenie.id='" + tmpint + "'";
            boolean state = stmt.execute(sql);
            if (state) {
                text.appendText("\nNie edytowano zlecenia");
                dataOutputStream.writeUTF("Error in edit");
            } else {
                text.appendText("\nEdytowano zlecenie");
                dataOutputStream.writeUTF("Edited");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertOrder(int tmpint,Statement stmt, Statement stmt2, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws IOException {
        Map<Integer, Double> toList = new HashMap<>();
        Map<Integer, Double> fromList = new HashMap<>();
        Map<Integer, String> branchList = new HashMap<>();
        String from = dataInputStream.readUTF();
        String to = dataInputStream.readUTF();
        try {
            String sql = "select * from  FirmaTransportowa.dbo.Oddzial";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String tmpstring = rs.getString(2);
                branchList.put(id, tmpstring);
                System.out.println(id);
            }
            branchList.forEach((k, v) -> {
                Map<String, Double> coords;
                double latA = 0;
                double latB = 0;
                double latC = 0;
                double lonA = 0;
                double lonB = 0;
                double lonC = 0;
                coords = OpenStreetMapUtils.getInstance().getCoordinates(v);
                latA += coords.get("lat");
                lonA += coords.get("lon");
                coords = OpenStreetMapUtils.getInstance().getCoordinates(from);
                latB += coords.get("lat");
                lonB += coords.get("lon");
                coords = OpenStreetMapUtils.getInstance().getCoordinates(to);
                latC += coords.get("lat");
                lonC += coords.get("lon");
                fromList.put(k, distance(latA, latB, lonA, lonB));
                toList.put(k, distance(latA, latC, lonA, lonC));
            });
            Map.Entry<Integer, Double> minFromList = Collections.min(fromList.entrySet(), (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));
            Map.Entry<Integer, Double> minToList = Collections.min(toList.entrySet(), (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));
            sql = "insert into FirmaTransportowa.dbo.Zlecenie values(GETDATE(),'" + from + "','" + to + "','Nowe','" + tmpint + "','" + minFromList.getKey() + "','" + minToList.getKey() + "');";
            boolean state = stmt.execute(sql);
            if (state) {
                text.appendText("\nNie dodano zlecenie");
                dataOutputStream.writeUTF("Error in add");
            } else {
                text.appendText("\nDodano zlecenie");
                dataOutputStream.writeUTF("Added");
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
