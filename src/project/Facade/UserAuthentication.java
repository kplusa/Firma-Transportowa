package project.Facade;

import com.jfoenix.controls.JFXTextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAuthentication {
    public void login(Statement stmt, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws SQLException, IOException {

        ResultSet rs = stmt.executeQuery("select MAIL,HASLO,typUzytkownika from FirmaTransportowa.dbo.Uzytkownik");
        String status = "Niepoprawna nazwa uzytkownika lub haslo";
        String mail = dataInputStream.readUTF();
        String pass = dataInputStream.readUTF();
        while (rs.next()) {
            String email = rs.getString(1);
            String password = rs.getString(2);
            String type = rs.getString(3);
            if (mail.equals(email) && pass.equals(password)) {
                status = "Poprawne dane-" + type;
                text.appendText("\nZalogowano:" + mail);
                break;
            }
        }
        dataOutputStream.writeUTF(status);
    }
    public void register(Statement stmt, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws SQLException, IOException {
        String status = "Zarejestrowano";
        String mail = dataInputStream.readUTF();
        String pass = dataInputStream.readUTF();
        String pass2 = dataInputStream.readUTF();
        int phone = dataInputStream.readInt();
        String type = dataInputStream.readUTF();
        String firstname = dataInputStream.readUTF();
        String lastname = dataInputStream.readUTF();
        String city = dataInputStream.readUTF();
        String street = dataInputStream.readUTF();
        String number = dataInputStream.readUTF();
        String code = dataInputStream.readUTF();
        if (!pass.equals(pass2)) {
            status = "Hasla sie roznia";
        } else {
            ResultSet rs = stmt.executeQuery("select * from FirmaTransportowa.dbo.Adres");
            String unistreet = null, uninumber = null, unicity = null, unicode = null;
            int tmpint = 0;
            String tmpstring;
            while (rs.next()) {
                unistreet = null; uninumber = null; unicity = null; unicode = null;
                tmpint = rs.getInt(1);
                tmpstring = rs.getString(2);
                if (tmpstring.equals(street)) {
                    unistreet = "equals";
                }
                tmpstring = rs.getString(3);
                if (tmpstring.equals(number)) {
                    uninumber = "equals";
                }
                tmpstring = rs.getString(4);
                if (tmpstring.equals(city)) {
                    unicity = "equals";
                }
                tmpstring = rs.getString(5);
                if (tmpstring.equals(code)) {
                    unicode = "equals";
                }
                if (unistreet != null && uninumber!= null  && unicity!= null  && unicode!= null ) {
                    break;
                }
            }
            String sql;
            if (!unistreet.equals("equals") && !uninumber.equals("equals") && !unicity.equals("equals") && !unicode.equals("equals")) {
                sql = "insert FirmaTransportowa.dbo.Adres values('" + street + "','" + number + "','" + city + "','" + code + "')";
                stmt.execute(sql);
            }
            rs = stmt.executeQuery("select * from FirmaTransportowa.dbo.Uzytkownik");
            while (rs.next()) {
                tmpstring = rs.getString(4);
                if (tmpstring.equals(mail)) {
                    status = "Podany uzytkownik jest zarejestrowany";
                }
            }
            if (status.equals("Zarejestrowano")) {
                if (!unistreet.equals("equals") && !uninumber.equals("equals") && !unicity.equals("equals") && !unicode.equals("equals")) {
                    sql = "insert FirmaTransportowa.dbo.Uzytkownik values('" + firstname + "','" + lastname + "','" + mail + "','" + pass + "','" + phone + "','" + type + "','" + tmpint + "')";
                    stmt.execute(sql);
                } else {
                    sql = "insert FirmaTransportowa.dbo.Uzytkownik values('" + firstname + "','" + lastname + "','" + mail + "','" + pass + "','" + phone + "','" + type + "',(select max(id) from FirmaTransportowa.dbo.Adres))";
                    stmt.execute(sql);
                }
                sql = "Select FirmaTransportowa.dbo.Uzytkownik.id from FirmaTransportowa.dbo.Uzytkownik where FirmaTransportowa.dbo.Uzytkownik.mail='" + mail + "'";
                rs = stmt.executeQuery(sql);
                rs.next();
                tmpint = rs.getInt(1);
                if (type.equals("Kurier")) {
                    sql = "insert FirmaTransportowa.dbo.Kurier values('Poczatkowy','" + firstname + "','" + lastname + "','" + tmpint + "')";
                    stmt.execute(sql);
                    text.appendText("\nDodano uzytkownika jako kuriera");
                }
                text.appendText("\nPomyslnie zarejestrowano uzytkownika:" + mail);
            }
        }
        dataOutputStream.writeUTF(status);
    }
}
