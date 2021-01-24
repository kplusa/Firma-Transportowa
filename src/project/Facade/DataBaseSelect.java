package project.Facade;

import com.jfoenix.controls.JFXTextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseSelect {
    public void SelectFromDatabase(int rows, String sql, Statement stmt, DataOutputStream dataOutputStream) {
        try {
            List<String> StringList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);
            int counter = 0;
            while (rs.next()) {
                counter++;
                for (int i = 1; i <= rows; i++) {
                    String tmpstring = rs.getString(i);
                    StringList.add(tmpstring);
                }
            }
            dataOutputStream.writeInt(counter);
            for (String send : StringList) {
                dataOutputStream.writeUTF(send);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void EditBase(String sql, Statement stmt) {
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addwithselect(String sql, String insertSql, Statement stmt, DataOutputStream dataOutputStream, JFXTextArea text) {
        try {
            ResultSet rs = stmt.executeQuery(sql);
            String status;
            int counter = 0;
            while (rs.next()) {
                counter++;
            }
            if (counter > 0) {
                text.appendText("\n Takie same dane");
                status = "Data error";
            } else {
                stmt.execute(insertSql);
                status = "Added";
                text.appendText("\n Dodano do bazy");
            }
            dataOutputStream.writeUTF(status);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void selectonecell(String sql, int option, Statement stmt, DataOutputStream dataOutputStream) {
        try {
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            dataOutputStream.writeFloat(rs.getFloat(2));
            if (option == 1) {
                dataOutputStream.writeUTF(rs.getString(1));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void Selectnewest(String sql, int option, Statement stmt, DataOutputStream dataOutputStream) {
        List<String> StringList = new ArrayList<>();
        List<String> secondStringList = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            int counter = 0;
            while (rs.next()) {
                String tmpstring = rs.getString(1);
                boolean state = false;
                for (String send : StringList) {
                    if (tmpstring.equals(send)) {
                        state = true;
                        break;
                    }
                }
                if (!state) {
                    counter++;
                    StringList.add(rs.getString(1));
                    secondStringList.add(rs.getString(1));
                    if (option == 1) {
                        secondStringList.add(rs.getString(2));
                    }
                }
            }
            dataOutputStream.writeInt(counter);
            for (String send : secondStringList) {
                dataOutputStream.writeUTF(send);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void InsertPack(Statement stmt, DataOutputStream dataOutputStream, DataInputStream dataInputStream, JFXTextArea text) {
        try {
            float tmpfloat = dataInputStream.readFloat();//1
            float waga = dataInputStream.readFloat();//2
            int tmpint = dataInputStream.readInt();//3
            char tmpchar = dataInputStream.readChar();//4
            String sql = "insert into FirmaTransportowa.dbo.Oplata values(GETDATE(),'" + tmpfloat + "','Nowe','Przelew');";
            stmt.execute(sql);
            sql = "select max(FirmaTransportowa.dbo.Doplata.id) from FirmaTransportowa.dbo.Doplata;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int oplatid = rs.getInt(1);
            sql = "select FirmaTransportowa.dbo.Cennik.id,FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.kwota,FirmaTransportowa.dbo.Cennik.dataZmiany from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='" + tmpchar + "' order by FirmaTransportowa.dbo.Cennik.dataZmiany DESC;";
            rs = stmt.executeQuery(sql);
            rs.next();
            int idcenn = rs.getInt(1);
            sql = "insert into FirmaTransportowa.dbo.Paczka values('" + waga + "','" + tmpint + "','" + idcenn + "','" + oplatid + "');";
            stmt.execute(sql);
            tmpint = dataInputStream.readInt();
            sql = "select max(FirmaTransportowa.dbo.Paczka.id) from FirmaTransportowa.dbo.Paczka;";
            rs = stmt.executeQuery(sql);
            rs.next();
            oplatid = rs.getInt(1);
            for (int i = 0; i < tmpint; i++) {
                String tmpstring = dataInputStream.readUTF();
                sql = "select FirmaTransportowa.dbo.Doplata.id,FirmaTransportowa.dbo.Doplata.typDoplaty,FirmaTransportowa.dbo.Doplata.dataZmiany from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='" + tmpstring + "' order by FirmaTransportowa.dbo.Doplata.dataZmiany DESC;";
                rs = stmt.executeQuery(sql);
                rs.next();
                idcenn = rs.getInt(1);
                sql = "insert into FirmaTransportowa.dbo.PaczkaDoplata values('" + oplatid + "','" + idcenn + "');";
                stmt.execute(sql);
            }
            text.appendText("\nDodano paczke");
            dataOutputStream.writeUTF("Pack added");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
