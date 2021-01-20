package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import project.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

class ClientHandler extends Thread implements Initializable {
        JFXTextArea text;
        Socket socket=null;
        DataInputStream dataInputStream=null;
        DataOutputStream dataOutputStream=null;
        private Statement stmt;
        private ResultSet rs;
        private String mail,email,pass,password,status,type,pass2,tmpstring,uni,sql;
        private int tmpint,option,counter;
        private List<String> StringList=new ArrayList<String>();
    ClientHandler(Socket s,JFXTextArea t){
            socket=s;
            text=t;
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void run(){
        try {
            Connection conn = null;
            try {
                String url = "jdbc:sqlserver://DESKTOP-U746ETR\\SQLEXPRESS:1433";
                String username= "PIPpro";
                String password= "12345";
                conn = DriverManager.getConnection(url, username, password);
                if (conn != null) {
                    DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            try {
                option=dataInputStream.readInt();
                if(option==1){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select MAIL,HASLO,typUzytkownika from FirmaTransportowa.dbo.Uzytkownik");
                status = "Niepoprawna nazwa uzytkownika lub haslo";
                mail = dataInputStream.readUTF();
                pass = dataInputStream.readUTF();
                while (rs.next()) {
                    email =rs.getString(1);
                    password =rs.getString(2);
                    type=rs.getString(3);
                    if (mail.equals(email) && pass.equals(password)) {
                        status = "Poprawne dane-"+type;
                        text.appendText("\nZalogowano:"+mail);
                        break;
                    }
                }
                conn.close();
                dataOutputStream.writeUTF(status);
            }
                else if(option==2)
                {
                    stmt = conn.createStatement();
                    status = "Zarejestrowano";
                    mail=dataInputStream.readUTF();
                    pass=dataInputStream.readUTF();
                    pass2=dataInputStream.readUTF();
                    int phone = dataInputStream.readInt();
                    type=dataInputStream.readUTF();
                    String firstname = dataInputStream.readUTF();
                    String lastname = dataInputStream.readUTF();
                    String city = dataInputStream.readUTF();
                    String street = dataInputStream.readUTF();
                    String number = dataInputStream.readUTF();
                    String code = dataInputStream.readUTF();
                    if (!pass.equals(pass2)) {
                        status = "Hasla sie roznia";
                    } else
                    {
                        uni=null;
                        rs=stmt.executeQuery("select * from FirmaTransportowa.dbo.Adres");
                        String unistreet=null,uninumber=null,unicity=null,unicode=null;
                        while(rs.next())
                        {
                            tmpint=rs.getInt(1);
                            tmpstring=rs.getString(2);
                            if(tmpstring.equals(street)){
                                unistreet = "equals";
                            }
                            tmpstring=rs.getString(3);
                            if(tmpstring.equals(number)){
                                uninumber = "equals";
                            }
                            tmpstring=rs.getString(4);
                            if(tmpstring.equals(city)){
                                unicity = "equals";
                            }
                            tmpstring=rs.getString(5);
                            if(tmpstring.equals(code)){
                                unicode = "equals";
                            }
                            if(unistreet=="equals"&&uninumber == "equals"&&unicity == "equals"&&unicode == "equals")
                            {
                                break;
                            }
                        }
                    if(unistreet!="equals"&&uninumber != "equals"&&unicity != "equals"&&unicode != "equals")
                    {
                        sql="insert FirmaTransportowa.dbo.Adres values('" + street + "','" + number + "','" + city + "','" + code + "')";
                        stmt.execute(sql);
                    }
                        rs = stmt.executeQuery("select * from FirmaTransportowa.dbo.Uzytkownik");
                        while (rs.next()) {
                            tmpstring = rs.getString(4);
                            if(tmpstring.equals(mail))
                            {
                                status="Podany uzytkownik jest zarejestrowany";
                            }
                        }
                        if (status == "Zarejestrowano") {
                            if(unistreet!="equals"&&uninumber != "equals"&&unicity != "equals"&&unicode != "equals")
                            {
                                sql="insert FirmaTransportowa.dbo.Uzytkownik values('" + firstname + "','" + lastname + "','" + mail + "','" + pass + "','" + phone + "','" + type + "','"+tmpint+"')";
                                stmt.execute(sql);
                            }
                            else
                            {
                                sql="insert FirmaTransportowa.dbo.Uzytkownik values('" + firstname + "','" + lastname + "','" + mail + "','" + pass + "','" + phone + "','" + type + "',(select max(id) from FirmaTransportowa.dbo.Adres))";
                                stmt.execute(sql);
                            }
                            text.appendText("\nPomyslnie zarejestrowano uzytkownika:"+mail);
                        }
                    }
                    conn.close();
                    dataOutputStream.writeUTF(status);
                }
                else if(option==3)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                    sql="Select * from FirmaTransportowa.dbo.Cennik";
                    rs=stmt.executeQuery(sql);
                    while (rs.next())
                    {
                        counter++;
                        tmpstring=rs.getString(2);
                        StringList.add(tmpstring);
                        tmpstring=rs.getString(3);
                        StringList.add(tmpstring);
                        tmpstring=rs.getString(4);
                        StringList.add(tmpstring);
                        tmpstring=rs.getString(6);
                        StringList.add(tmpstring);
                    }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\n Wyslano cennik");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                    e.printStackTrace();
                    }
                }
                else if(option==4)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        sql="Select * from FirmaTransportowa.dbo.Doplata";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\n Wyslano doplate");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==5)//TODO
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        sql="Select * from FirmaTransportowa.dbo.Doplata";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\n Wyslano aktualne zlecenia");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
