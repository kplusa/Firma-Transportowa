package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import project.Client;
import project.Utils.OpenStreetMapUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static project.Utils.DataUtil.distance;

class ClientHandler extends Thread implements Initializable {
        JFXTextArea text;
        Socket socket=null;
        DataInputStream dataInputStream=null;
        DataOutputStream dataOutputStream=null;
        private Statement stmt,stmt2;
        private ResultSet rs,rs2;
        private String mail,email,pass,password,status,type,pass2,tmpstring,uni,sql,from,to,tmpstring2,sql2;
        private int tmpint,option,counter,id,UserId;
        private float tmpfloat;
        private char tmpchar;
        private Date data;
        private List<String> StringList=new ArrayList<String>();
        private List<String> secondStringList=new ArrayList<String>();

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
                String url = "jdbc:sqlserver://DESKTOP-TRG3U04\\SQLEXPRESS:1433";
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
                            sql="Select FirmaTransportowa.dbo.Uzytkownik.id from FirmaTransportowa.dbo.Uzytkownik where FirmaTransportowa.dbo.Uzytkownik.mail='" + mail + "'";
                            rs=stmt.executeQuery(sql);
                            rs.next();
                            tmpint=rs.getInt(1);
                            if(type.equals("Kurier"))
                            {
                                sql="insert FirmaTransportowa.dbo.Kurier values('Poczatkowy','" + firstname + "','" + lastname + "','" + tmpint + "')";
                                stmt.execute(sql);
                                text.appendText("\nDodano uzytkownika jako kuriera");
                            }
                            text.appendText("\nPomyslnie zarejestrowano uzytkownika:"+mail);
                        }
                    }
                    conn.close();
                    dataOutputStream.writeUTF(status);
                }
                else if(option==3)//Check sql querry, may be bad because of expecting changing od Cennik
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
                else if(option==4)//Check sql querry, may be bad,because of dont expect changing of doplata
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
                else if(option==5)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    tmpstring= dataInputStream.readUTF();
                    tmpint=getUserId(tmpstring,conn);
                    try{
                        sql="select FirmaTransportowa.dbo.Zlecenie.id, FirmaTransportowa.dbo.Zlecenie.status,FirmaTransportowa.dbo.Kurier.imie,FirmaTransportowa.dbo.Kurier.nazwisko from FirmaTransportowa.dbo.Zlecenie, FirmaTransportowa.dbo.Kurier,FirmaTransportowa.dbo.ZlecenieKurier where (FirmaTransportowa.dbo.Zlecenie.id=FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId)AND(FirmaTransportowa.dbo.ZlecenieKurier.kurierId=FirmaTransportowa.dbo.Kurier.Id)AND(FirmaTransportowa.dbo.Zlecenie.uzytkownikId='"+tmpint+"');";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            tmpstring+=" ";
                            tmpstring+=rs.getString(4);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\nWyslano aktualne zlecenia");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==6){
                    stmt = conn.createStatement();
                    tmpstring= dataInputStream.readUTF();
                    tmpint=getUserId(tmpstring,conn);
                    counter=0;
                    try{
                        sql="select FirmaTransportowa.dbo.Zlecenie.id,FirmaTransportowa.dbo.Zlecenie.adresPoczatkowy,FirmaTransportowa.dbo.Zlecenie.adresKoncowy,FirmaTransportowa.dbo.Zlecenie.dataNadania,(select COUNT(*) from FirmaTransportowa.dbo.Paczka where FirmaTransportowa.dbo.Zlecenie.id=FirmaTransportowa.dbo.Paczka.zlecenieId) from FirmaTransportowa.dbo.Zlecenie where (FirmaTransportowa.dbo.Zlecenie.uzytkownikId='"+tmpint+"');";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(4);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(5);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\nWyslano aktualne zlecenia do dodawania zlecen");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==7){//check sql querry,TODO
                    Map<Integer,Double> toList =new HashMap<>();
                    Map<Integer, Double> fromList =new HashMap<>();
                    Map<Integer, String> branchList= new HashMap<>();



                    StringList.clear();
                    stmt = conn.createStatement();
                    tmpint=getUserId(dataInputStream.readUTF(),conn);
                    from=dataInputStream.readUTF();
                    to=dataInputStream.readUTF();
                    try{
                        sql = "select * from  FirmaTransportowa.dbo.Oddzial";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            Integer id =rs.getInt(1);
                            tmpstring= rs.getString(2);
                            branchList.put(id, tmpstring);
                        }

                        branchList.forEach((k, v) -> {
                            Map<String, Double> coords;
                            double latA=0;
                            double latB=0;
                            double latC=0;
                            double lonA=0;
                            double lonB=0;
                            double lonC=0;
                            coords = OpenStreetMapUtils.getInstance().getCoordinates(v);
                            latA+= coords.get("lat");
                            lonA+= coords.get("lon");
                            coords = OpenStreetMapUtils.getInstance().getCoordinates(from);
                            latB+= coords.get("lat");
                            lonB+= coords.get("lon");
                            coords = OpenStreetMapUtils.getInstance().getCoordinates(to);
                            latC+= coords.get("lat");
                            lonC+= coords.get("lon");
                            fromList.put(k,distance(latA,latB,lonA,lonB));
                            toList.put(k,distance(latA,latC,lonA,lonC));
                        });
                        Map.Entry<Integer, Double> minFromList = Collections.min(fromList.entrySet(), new Comparator<Map.Entry<Integer, Double>>() {
                            public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
                                return entry1.getValue().compareTo(entry2.getValue());
                            }
                        });

                        Map.Entry<Integer, Double> minToList = Collections.min(toList.entrySet(), new Comparator<Map.Entry<Integer, Double>>() {
                            public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
                                return entry1.getValue().compareTo(entry2.getValue());
                            }
                        });


//                        System.out.printf("%s: %f", minFromList.getKey(), minFromList.getValue());
//                        System.out.printf("%s: %f", minToList.getKey(), minToList.getValue());



                        sql="insert into FirmaTransportowa.dbo.Zlecenie values(GETDATE(),'"+from+"','"+to+"','Nowe','"+tmpint+"','"+minFromList.getKey()+"','"+minToList.getKey()+"');";
                        boolean status=stmt.execute(sql);
                        if(status==false)
                        {
                            text.appendText("\nDodano zlecenie");
                            dataOutputStream.writeUTF("Added");
                        }
                        else
                        {
                            text.appendText("\nNie dodano zlecenie");
                            dataOutputStream.writeUTF("Error in add");
                        }
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==8){
                    stmt = conn.createStatement();
                    stmt2 = conn.createStatement();
                    Map<Integer,Double> toList =new HashMap<>();
                    Map<Integer, Double> fromList =new HashMap<>();
                    Map<Integer, String> branchList= new HashMap<>();
                    toList.clear(); fromList.clear(); branchList.clear();
                    StringList.clear();
                    tmpint=dataInputStream.readInt();
                    from=dataInputStream.readUTF();
                    to=dataInputStream.readUTF();
                    try{
                        try{
                            sql="select * from FirmaTransportowa.dbo.Zlecenie Where FirmaTransportowa.dbo.Zlecenie.id='"+tmpint+"';";
                            rs=stmt.executeQuery(sql);
                            rs.next();
                        }
                        catch ( SQLException e)
                        {
                            e.printStackTrace();
                        }
                        sql = "select * from  FirmaTransportowa.dbo.Oddzial";
                        rs2=stmt2.executeQuery(sql);
                        while (rs2.next())
                        {
                            Integer id =rs2.getInt(1);
                            tmpstring= rs2.getString(2);
                            branchList.put(id, tmpstring);
                        }

                        branchList.forEach((k, v) -> {
                            Map<String, Double> coords;
                            double latA=0;
                            double latB=0;
                            double latC=0;
                            double lonA=0;
                            double lonB=0;
                            double lonC=0;
                            coords = OpenStreetMapUtils.getInstance().getCoordinates(v);
                            latA+= coords.get("lat");
                            lonA+= coords.get("lon");
                            coords = OpenStreetMapUtils.getInstance().getCoordinates(from);
                            latB+= coords.get("lat");
                            lonB+= coords.get("lon");
                            coords = OpenStreetMapUtils.getInstance().getCoordinates(to);
                            latC+= coords.get("lat");
                            lonC+= coords.get("lon");
                            fromList.put(k,distance(latA,latB,lonA,lonB));
                            toList.put(k,distance(latA,latC,lonA,lonC));
                        });

                        Map.Entry<Integer, Double> minFromList = Collections.min(fromList.entrySet(), new Comparator<Map.Entry<Integer, Double>>() {
                            public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
                                return entry1.getValue().compareTo(entry2.getValue());
                            }
                        });

                        Map.Entry<Integer, Double> minToList = Collections.min(toList.entrySet(), new Comparator<Map.Entry<Integer, Double>>() {
                            public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
                                return entry1.getValue().compareTo(entry2.getValue());
                            }
                        });
                        sql="update FirmaTransportowa.dbo.Zlecenie set FirmaTransportowa.dbo.Zlecenie.dataNadania='"+rs.getString(2)+"',FirmaTransportowa.dbo.Zlecenie.adresPoczatkowy='"+from+"'," +
                                "FirmaTransportowa.dbo.Zlecenie.adresKoncowy='"+to+"',FirmaTransportowa.dbo.Zlecenie.status='"+rs.getString(5)+"',FirmaTransportowa.dbo.Zlecenie.uzytkownikId='"+rs.getString(6)+"',FirmaTransportowa.dbo.Zlecenie.oddzialPoczatkowyId='"+minFromList.getKey()+"',FirmaTransportowa.dbo.Zlecenie.oddzialKoncowyId='"+minToList.getKey()+"'" +
                                " where FirmaTransportowa.dbo.Zlecenie.id='"+tmpint+"'";
                        boolean status=stmt.execute(sql);
                        if(status==false)
                        {
                            text.appendText("\nEdytowano zlecenie");
                            dataOutputStream.writeUTF("Edited");
                        }
                        else
                        {
                            text.appendText("\nNie edytowano zlecenia");
                            dataOutputStream.writeUTF("Error in edit");
                        }
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==9){
                    stmt = conn.createStatement();
                    StringList.clear();
                    tmpint=dataInputStream.readInt();
                    try{
                        sql="Delete from FirmaTransportowa.dbo.Zlecenie where FirmaTransportowa.dbo.Zlecenie.id='"+tmpint+"';";
                        boolean status=stmt.execute(sql);
                        if(status==false)
                        {
                            text.appendText("\nUsunieto zlecenie");
                            dataOutputStream.writeUTF("Deleted");
                        }
                        else
                        {
                            text.appendText("\nNie usunieto zlecenia");
                            dataOutputStream.writeUTF("Error in delete");
                        }
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==10){
                    stmt = conn.createStatement();
                    boolean helpme=false;
                    counter=0;
                    StringList.clear();
                    secondStringList.clear();
                    try{
                        sql="select FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.opis,FirmaTransportowa.dbo.Cennik.dataZmiany from FirmaTransportowa.dbo.Cennik order by FirmaTransportowa.dbo.Cennik.dataZmiany DESC;";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                        tmpstring=rs.getString(1);
                        for(String send:StringList){
                            if(tmpstring.equals(send)){
                                helpme=true;
                                break;
                            }
                        }
                        if(!helpme)
                        {
                            counter++;
                            StringList.add(rs.getString(1));
                            secondStringList.add(rs.getString(1));
                            secondStringList.add(rs.getString(2));
                        }
                            helpme=false;
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:secondStringList)
                        {
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\nWyslano cennik do Combo dla add pack");

                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                    helpme=false;
                    counter=0;
                    StringList.clear();
                    try{
                        sql="select FirmaTransportowa.dbo.Doplata.typDoplaty,FirmaTransportowa.dbo.Doplata.dataZmiany from FirmaTransportowa.dbo.Doplata order by FirmaTransportowa.dbo.Doplata.dataZmiany DESC;";
                        rs=stmt.executeQuery(sql);
                        while(rs.next())
                        {
                            tmpstring=rs.getString(1);
                            for(String send:StringList){
                                if(tmpstring.equals(send)){
                                    helpme=true;
                                    break;
                                }
                            }
                            if(!helpme)
                            {
                                counter++;
                                StringList.add(rs.getString(1));
                            }
                            helpme=false;
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList)
                        {
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\nWyslano doplaty do Combo dla add pack");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==11){
                    stmt = conn.createStatement();
                    tmpchar=dataInputStream.readChar();
                    try{
                        sql="select FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.kwota,FirmaTransportowa.dbo.Cennik.dataZmiany from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='"+tmpchar+"' order by FirmaTransportowa.dbo.Cennik.dataZmiany DESC;";
                        rs=stmt.executeQuery(sql);
                        rs.next();
                        dataOutputStream.writeFloat(rs.getFloat(2));
                        text.appendText("\nWyslano kwote do dimenison add pack");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==12){
                    stmt = conn.createStatement();
                    tmpstring=dataInputStream.readUTF();
                    try{
                        sql="select FirmaTransportowa.dbo.Doplata.typDoplaty,FirmaTransportowa.dbo.Doplata.kwota,FirmaTransportowa.dbo.Doplata.dataZmiany from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='"+tmpstring+"' order by FirmaTransportowa.dbo.Doplata.dataZmiany DESC;";
                        rs=stmt.executeQuery(sql);
                        rs.next();
                        dataOutputStream.writeFloat(rs.getFloat(2));
                        dataOutputStream.writeUTF(rs.getString(1));
                        text.appendText("\nWyslano kwote do aditional prizes add pack");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==13){//TODO HERE IM DOING SOMETHING
                    stmt = conn.createStatement();
                    try{
                        tmpfloat=dataInputStream.readFloat();//1
                        sql="insert into FirmaTransportowa.dbo.Oplata values(GETDATE(),'"+tmpfloat+"','Nowe','Przelew');";
                        stmt.execute(sql);
                        sql="select max(FirmaTransportowa.dbo.Doplata.id) from FirmaTransportowa.dbo.Doplata;";
                        rs=stmt.executeQuery(sql);
                        rs.next();
                        int oplatid=rs.getInt(1);
                        Float waga=dataInputStream.readFloat();//2
                        tmpint=dataInputStream.readInt();//3
                        tmpchar=dataInputStream.readChar();//4
                        sql="select FirmaTransportowa.dbo.Cennik.id,FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.kwota,FirmaTransportowa.dbo.Cennik.dataZmiany from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='"+tmpchar+"' order by FirmaTransportowa.dbo.Cennik.dataZmiany DESC;";
                        rs=stmt.executeQuery(sql);
                        rs.next();
                        int idcenn=rs.getInt(1);
                        sql="insert into FirmaTransportowa.dbo.Paczka values('"+waga+"','"+tmpint+"','"+idcenn+"','"+oplatid+"');";
                        stmt.execute(sql);
                        tmpint=dataInputStream.readInt();
                        sql="select max(FirmaTransportowa.dbo.Paczka.id) from FirmaTransportowa.dbo.Paczka;";
                        rs=stmt.executeQuery(sql);
                        rs.next();
                        oplatid=rs.getInt(1);
                        for(int i=0;i<tmpint;i++)
                        {
                            tmpstring=dataInputStream.readUTF();
                            sql="select FirmaTransportowa.dbo.Doplata.id,FirmaTransportowa.dbo.Doplata.typDoplaty,FirmaTransportowa.dbo.Doplata.dataZmiany from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='"+tmpstring+"' order by FirmaTransportowa.dbo.Doplata.dataZmiany DESC;";
                            rs=stmt.executeQuery(sql);
                            rs.next();
                            idcenn=rs.getInt(1);
                            sql="insert into FirmaTransportowa.dbo.PaczkaDoplata values('"+oplatid+"','"+idcenn+"');";
                            stmt.execute(sql);
                        }
                        text.appendText("\nDodano paczke");
                        dataOutputStream.writeUTF("Pack added");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option ==21)
                {
                    status="";
                    stmt = conn.createStatement();
                    String dimension=dataInputStream.readUTF();
                    float amount=dataInputStream.readFloat();
                    String description=dataInputStream.readUTF();
                    String limit=dataInputStream.readUTF();
                    try{
                        rs = stmt.executeQuery("select * from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='"+dimension+"' " +
                                "or FirmaTransportowa.dbo.Cennik.limit='"+limit+"' ");
                        while (rs.next()) {
                            counter++;
                        }
                        if(counter>0) {
                            text.appendText("\n Takie same dane");
                            status="Data error";
                        }

                        else {
                            sql="insert into FirmaTransportowa.dbo.Cennik values ('"+dimension+"','"+amount+"','"+description+"',GETDATE(),'"+limit+"')";
                            stmt.execute(sql);
                            status="Added";
                            text.appendText("\n Dodano do bazy");
                        }
                        dataOutputStream.writeUTF(status);
                        conn.close();
                    }

                    catch ( SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option ==22)
                {
                    status="";
                    stmt = conn.createStatement();
                    String type=dataInputStream.readUTF();
                    float amount=dataInputStream.readFloat();
                    try{
                        rs = stmt.executeQuery("select * from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='"+type+"' ");
                        while (rs.next()) {
                            counter++;
                        }
                        if(counter>0) {
                            text.appendText("\n Takie same dane");
                            status="Data error";
                        }

                        else {

                            sql="insert into FirmaTransportowa.dbo.Doplata values ('"+type+"','"+amount+"',GETDATE())";
                            stmt.execute(sql);
                            status="Added";
                            text.appendText("\n Dodano do bazy");
                        }
                        dataOutputStream.writeUTF(status);
                        conn.close();
                    }

                    catch ( SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option ==23)
                {
                    status="";
                    stmt = conn.createStatement();
                    String dimension=dataInputStream.readUTF();
                    float amount=dataInputStream.readFloat();
                    String description=dataInputStream.readUTF();
                    String limit=dataInputStream.readUTF();
                    try{
                        sql="update FirmaTransportowa.dbo.Cennik set FirmaTransportowa.dbo.Cennik.gabaryt='"+dimension+"',FirmaTransportowa.dbo.Cennik.kwota='"+amount+"'," +
                                "FirmaTransportowa.dbo.Cennik.opis='"+description+"',FirmaTransportowa.dbo.Cennik.dataZmiany=GETDATE(),FirmaTransportowa.dbo.Cennik.limit='"+limit+"'" +
                                "where FirmaTransportowa.dbo.Cennik.gabaryt='"+dimension+"'";
                        stmt.execute(sql);
                        status="Edited";
                        text.appendText("\n Dodano do bazy");

                        dataOutputStream.writeUTF(status);
                        conn.close();
                    }
                    catch ( SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option ==24)
                {
                    status="";
                    stmt = conn.createStatement();
                    String type=dataInputStream.readUTF();
                    float amount=dataInputStream.readFloat();
                    try{
                        sql="update FirmaTransportowa.dbo.Doplata set FirmaTransportowa.dbo.Doplata.typDoplaty='"+type+"',FirmaTransportowa.dbo.Doplata.kwota='"+amount+"'," +
                                "FirmaTransportowa.dbo.Doplata.dataZmiany=GETDATE()  where FirmaTransportowa.dbo.Doplata.typDoplaty='"+type+"'";
                        stmt.execute(sql);
                        status="Edited";
                        text.appendText("\n Dodano do bazy");
                        dataOutputStream.writeUTF(status);
                        conn.close();
                    }

                    catch ( SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option ==25)
                {
                    status="";
                    stmt = conn.createStatement();
                    String dimension=dataInputStream.readUTF();
                    try{
                        sql = "delete  from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='"+dimension+"'";
                        stmt.execute(sql);
                        text.appendText("\n Usunieto");
                        status="Deleted";
                        dataOutputStream.writeUTF(status);
                        conn.close();
                    }

                    catch ( SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option ==26)
                {
                    status="";
                    stmt = conn.createStatement();
                    String type=dataInputStream.readUTF();
                    float amount=dataInputStream.readFloat();
                    try{
                        sql = "delete  from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='"+type+"'" +
                                "and FirmaTransportowa.dbo.Doplata.kwota= '"+amount+"'";
                        stmt.execute(sql);
                        text.appendText("\n Usunieto");
                        status="Deleted";
                        dataOutputStream.writeUTF(status);
                        conn.close();
                    }

                    catch ( SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==27)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        sql="select FirmaTransportowa.dbo.Zlecenie.id, (select FirmaTransportowa.dbo.Oddzial.miejscowosc  \n" +
                                "from FirmaTransportowa.dbo.Oddzial \n" +
                                "where  FirmaTransportowa.dbo.Oddzial.id =FirmaTransportowa.dbo.Zlecenie.oddzialPoczatkowyId),\n" +
                                "(select FirmaTransportowa.dbo.Oddzial.miejscowosc  \n" +
                                "from FirmaTransportowa.dbo.Oddzial \n" +
                                "where  FirmaTransportowa.dbo.Oddzial.id =FirmaTransportowa.dbo.Zlecenie.oddzialKoncowyId)\n" +
                                "from FirmaTransportowa.dbo.Zlecenie \n" +
                                "where not FirmaTransportowa.dbo.Zlecenie.id in( select FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId\n" +
                                "from FirmaTransportowa.dbo.ZlecenieKurier \n" +
                                "where FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId = FirmaTransportowa.dbo.Zlecenie.id)";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\n Wyslano Zlecenie");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==28)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        sql="with cte as (\n" +
                                "  select K.id Col1, Z.adresKoncowy Col2, ZK.id Col3\n" +
                                "    , row_number() over (partition by K.id order by ZK.id desc) RowNum\n" +
                                "  from FirmaTransportowa.dbo.Kurier K \n" +
                                "  join FirmaTransportowa.dbo.ZlecenieKurier ZK on K.id = ZK.kurierId\n" +
                                "  join FirmaTransportowa.dbo.Zlecenie Z on Z.id = ZK.zlecenieId\n" +
                                "  where K.id = ZK.kurierId \n" +
                                ")\n" +
                                "select Col1, Col2, Col3\n" +
                                "from cte\n" +
                                "where RowNum = 1\n" +
                                "order by Col1 desc;";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\n Wyslano Kurierow");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==29)
                {
                    stmt = conn.createStatement();

                    counter=0;
                    try{
                        String IdCourier=dataInputStream.readUTF();
                        String IdOrder=dataInputStream.readUTF();
                        sql="insert into FirmaTransportowa.dbo.ZlecenieKurier values ('"+IdOrder+"','"+IdCourier+"','1')";
                        stmt.execute(sql);
                        dataOutputStream.writeUTF("Added");
                        text.appendText("\n Dodano Zlecenie do kuriera");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==30)
                { stmt = conn.createStatement();
                StringList.clear();;
                counter=0;
                try{
                    sql="select FirmaTransportowa.dbo.Oddzial.miejscowosc from FirmaTransportowa.dbo.Oddzial";
                    rs=stmt.executeQuery(sql);
                    while(rs.next()){
                        counter++;
                        StringList.add(rs.getString(1));
                    }
                    dataOutputStream.writeInt(counter);
                    for(String send:StringList){
                        dataOutputStream.writeUTF(send);
                    }
                    text.appendText("\nWyslano oddzialy dla spedytora");
                    conn.close();
                }
                catch (IOException | SQLException e)
                {
                    e.printStackTrace();
                }}
                else if(option==31)
                {
                    stmt = conn.createStatement();
                    tmpstring=dataInputStream.readUTF();
                    boolean helpme=false;
                    try{
                        sql="select FirmaTransportowa.dbo.Oddzial.miejscowosc from FirmaTransportowa.dbo.Oddzial";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            if(rs.getString(1).equals(tmpstring))
                            {
                                helpme=true;
                                break;
                            }
                        }
                        if(!helpme)
                        {
                            sql="insert into FirmaTransportowa.dbo.Oddzial values('"+tmpstring+"')";
                            if(!stmt.execute(sql))
                            {
                                dataOutputStream.writeUTF("Dodano");
                                text.appendText("\nDodano oddzial");
                            }
                            else{
                                dataOutputStream.writeUTF("Blad");
                                text.appendText("\nBlad w dodawaniu oddzialu");
                            }
                        }
                        else
                        {
                            dataOutputStream.writeUTF("Podany oddzial istnieje");
                            text.appendText("\nOddzial istnieje");
                        }

                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }}
                else if(option==32)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    LocalDate date= LocalDate.now();
                    tmpint=date.getMonthValue();
                    tmpstring=String.valueOf(tmpint);
                    counter=0;
                    try{
                        sql="with cte as \n" +
                                "(select K.id Col1, K.imie Col2, COUNT(P.id) Col3, row_number() \n" +
                                "over (partition by K.id  order by K.id desc) RowNum \n" +
                                "from FirmaTransportowa.dbo.Kurier K right join FirmaTransportowa.dbo.ZlecenieKurier ZK on K.id=ZK.kurierId\n" +
                                "right join FirmaTransportowa.dbo.Zlecenie Z on ZK.zlecenieId=Z.id \n" +
                                "left join FirmaTransportowa.dbo.Paczka P on P.zlecenieId=Z.id\n" +
                                "where P.zlecenieId=Z.id AND MONTH(z.dataNadania)='"+tmpstring+"'\n" +
                                "group by K.id,K.imie\n" +
                                ")\n" +
                                "select Col1, Col2, Col3 from cte \n" +
                                "order by col1 asc\n" +
                                ";";
                        rs=stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\n Wyslano Kurierow do plac");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==33)
                { stmt = conn.createStatement();
                    counter=0;
                    tmpint=dataInputStream.readInt();
                    UserId=dataInputStream.readInt();
                    try{
                        status="Juz istnieje";
                        LocalDate data;
                        data=LocalDate.now();
                        int rok=data.getYear();
                        int miesiac=data.getMonthValue();
                        sql="select * from FirmaTransportowa.dbo.WyplataKurier where FirmaTransportowa.dbo.WyplataKurier.rok='"+rok+"' AND FirmaTransportowa.dbo.WyplataKurier.miesiac='"+miesiac+"'AND FirmaTransportowa.dbo.WyplataKurier.kurierId='"+UserId+"'";
                        rs=stmt.executeQuery(sql);
                        if(!rs.next()){
                            sql="insert into FirmaTransportowa.dbo.WyplataKurier values('"+rok+"','"+miesiac+"','"+tmpint+"','"+UserId+"')";
                            stmt.execute(sql);
                            status="Dodano";
                        }
                        dataOutputStream.writeUTF(status);
                        if(status.equals("Dodano"))
                        {
                            text.appendText("\nDodano prowizje kuriera");
                        }
                        else{
                            text.appendText("\nNie dodano prowizje kuriera");
                        }
                        conn.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }}
                else if(option==41)
                {
                    tmpstring = dataInputStream.readUTF();
                    stmt = conn.createStatement();
                    stmt2= conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        sql = "SELECT FirmaTransportowa.dbo.Zlecenie.id, FirmaTransportowa.dbo.Zlecenie.dataNadania, FirmaTransportowa.dbo.Zlecenie.adresPoczatkowy, FirmaTransportowa.dbo.Zlecenie.adresKoncowy, FirmaTransportowa.dbo.Zlecenie.status, FirmaTransportowa.dbo.Zlecenie.uzytkownikId \n" +
                                "\tFROM FirmaTransportowa.dbo.Zlecenie, FirmaTransportowa.dbo.ZlecenieKurier, FirmaTransportowa.dbo.Uzytkownik, FirmaTransportowa.dbo.Kurier\n" +
                                "\tWHERE FirmaTransportowa.dbo.Zlecenie.id = FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId\n" +
                                "\tAND FirmaTransportowa.dbo.ZlecenieKurier.kurierId = FirmaTransportowa.dbo.Kurier.id\n" +
                                "\tAND FirmaTransportowa.dbo.Kurier.uzytkownikId = FirmaTransportowa.dbo.Uzytkownik.id\n" +
                                "\tAND FirmaTransportowa.dbo.Uzytkownik.mail= '"+ tmpstring +"'\n" +
                                "\tAND FirmaTransportowa.dbo.Zlecenie.status = 'Odebrane';";
                        tmpstring2 = tmpstring;
                        rs=stmt.executeQuery(sql);
                        sql2 = "SELECT FirmaTransportowa.dbo.Paczka.zlecenieId ,COUNT(*)\n" +
                                "  FROM FirmaTransportowa.dbo.Paczka, FirmaTransportowa.dbo.Zlecenie, FirmaTransportowa.dbo.Uzytkownik, FirmaTransportowa.dbo.ZlecenieKurier, FirmaTransportowa.dbo.Kurier\n" +
                                "  WHERE FirmaTransportowa.dbo.Zlecenie.id = FirmaTransportowa.dbo.Paczka.zlecenieId\n" +
                                "  AND FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId = FirmaTransportowa.dbo.Zlecenie.id\n" +
                                "  AND FirmaTransportowa.dbo.ZlecenieKurier.kurierId = FirmaTransportowa.dbo.Kurier.id\n" +
                                "  AND FirmaTransportowa.dbo.Kurier.uzytkownikId = FirmaTransportowa.dbo.Uzytkownik.id\n" +
                                "  AND FirmaTransportowa.dbo.Zlecenie.status = 'Odebrane'\n" +
                                "  AND FirmaTransportowa.dbo.Uzytkownik.mail = '"+tmpstring2+"' GROUP BY\n" +
                                "  FirmaTransportowa.dbo.Paczka.zlecenieId;";
                        rs2 = stmt2.executeQuery(sql2);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(2);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(3);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(4);
                            StringList.add(tmpstring);
                            tmpstring=rs.getString(5);
                            StringList.add(tmpstring);

                            rs2.next();
                            tmpstring = rs2.getString(2);
                            StringList.add(tmpstring);

                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\nWyslano aktualne zlecenia dla kuriera");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==42)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        tmpstring = dataInputStream.readUTF();
                        tmpint = dataInputStream.readInt();
                        sql="UPDATE FirmaTransportowa.dbo.Zlecenie SET status = '"+tmpstring+"' WHERE id = '"+tmpint+"';";
                        stmt.execute(sql);
                        text.appendText("\nZakutalizowano status dostawy");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==43)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        tmpstring = dataInputStream.readUTF();
                        sql="  SELECT FirmaTransportowa.dbo.Kurier.typKuriera FROM\n" +
                                "  FirmaTransportowa.dbo.Kurier, FirmaTransportowa.dbo.Uzytkownik WHERE\n" +
                                "  FirmaTransportowa.dbo.Kurier.uzytkownikId = FirmaTransportowa.dbo.Uzytkownik.id AND\n" +
                                "  FirmaTransportowa.dbo.Uzytkownik.mail = '" + tmpstring + "';";
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                            counter++;
                            tmpstring=rs.getString(1);
                            StringList.add(tmpstring);
                        }
                        dataOutputStream.writeInt(counter);
                        for(String send:StringList){
                            dataOutputStream.writeUTF(send);
                        }
                        text.appendText("\nPobrano typ Kuriera");
                        conn.close();
                    }
                    catch (IOException | SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(option==44)
                {
                    stmt = conn.createStatement();
                    StringList.clear();
                    counter=0;
                    try{
                        tmpstring = dataInputStream.readUTF();
                        tmpstring2 = dataInputStream.readUTF();
                        sql="UPDATE FirmaTransportowa.dbo.Kurier SET" +
                                " FirmaTransportowa.dbo.Kurier.typKuriera = '"+tmpstring+"' WHERE" +
                                " FirmaTransportowa.dbo.Kurier.uzytkownikId = " +
                                "(SELECT FirmaTransportowa.dbo.Uzytkownik.id " +
                                "FROM FirmaTransportowa.dbo.Uzytkownik " +
                                "WHERE FirmaTransportowa.dbo.Uzytkownik.mail = '"+tmpstring2+"');";
                        stmt.execute(sql);
                        text.appendText("\nZaktualizowano typ kuriera");
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
            rs.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
    public int getUserId(String mail, Connection conn) throws IOException, SQLException {
        dataInputStream=new DataInputStream(socket.getInputStream());
        dataOutputStream=new DataOutputStream(socket.getOutputStream());
        stmt = conn.createStatement();
        try{
            sql="select FirmaTransportowa.dbo.Uzytkownik.id from FirmaTransportowa.dbo.Uzytkownik where FirmaTransportowa.dbo.Uzytkownik.mail='"+mail+"';";
            rs=stmt.executeQuery(sql);
            rs.next();
            UserId=rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return UserId;
    }
}
