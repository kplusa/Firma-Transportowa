package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import project.Facade.Facade;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


class ClientHandler extends Facade {
    JFXTextArea text;
    Socket socket;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;
    private Statement stmt;
    private int UserId;

    ClientHandler(Socket s, JFXTextArea t) {
        socket = s;
        text = t;
    }

    public void run() {
        try {
            Connection conn = config();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            try {
                int option = dataInputStream.readInt();
                stmt = conn.createStatement();
                String status = "";
                String tmpstring1;
                int tmpint1;
                Statement stmt2;
                if (option == 0) {
                    dataOutputStream.writeUTF(getNotify());
                    deleteNotify();
                } else if (option == 1) {
                    login(stmt, dataInputStream, dataOutputStream, text);
                } else if (option == 2) {
                    register(stmt, dataInputStream, dataOutputStream, text);
                } else if (option == 3) {
                    SelectFromDatabase(4, "Select FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.kwota, FirmaTransportowa.dbo.Cennik.opis, FirmaTransportowa.dbo.Cennik.limit from FirmaTransportowa.dbo.Cennik", stmt, dataOutputStream);
                    text.appendText("\n Wyslano cennik");
                } else if (option == 4) {
                    SelectFromDatabase(2, "Select FirmaTransportowa.dbo.Doplata.typDoplaty, FirmaTransportowa.dbo.Doplata.kwota from FirmaTransportowa.dbo.Doplata", stmt, dataOutputStream);
                    text.appendText("\n Wyslano doplate");
                } else if (option == 5) {
                    tmpstring1 = dataInputStream.readUTF();
                    tmpint1 = getUserId(tmpstring1, conn);
                    SelectFromDatabase(4, "select FirmaTransportowa.dbo.Zlecenie.id, FirmaTransportowa.dbo.Zlecenie.status,FirmaTransportowa.dbo.Kurier.imie,FirmaTransportowa.dbo.Kurier.nazwisko from FirmaTransportowa.dbo.Zlecenie, FirmaTransportowa.dbo.Kurier,FirmaTransportowa.dbo.ZlecenieKurier where (FirmaTransportowa.dbo.Zlecenie.id=FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId)AND(FirmaTransportowa.dbo.ZlecenieKurier.kurierId=FirmaTransportowa.dbo.Kurier.Id)AND(FirmaTransportowa.dbo.Zlecenie.uzytkownikId='" + tmpint1 + "');", stmt, dataOutputStream);
                    text.appendText("\nWyslano aktualne zlecenia");
                } else if (option == 6) {
                    tmpstring1 = dataInputStream.readUTF();
                    tmpint1 = getUserId(tmpstring1, conn);
                    SelectFromDatabase(5, "select FirmaTransportowa.dbo.Zlecenie.id,FirmaTransportowa.dbo.Zlecenie.adresPoczatkowy,FirmaTransportowa.dbo.Zlecenie.adresKoncowy,FirmaTransportowa.dbo.Zlecenie.dataNadania,(select COUNT(*) from FirmaTransportowa.dbo.Paczka where FirmaTransportowa.dbo.Zlecenie.id=FirmaTransportowa.dbo.Paczka.zlecenieId) from FirmaTransportowa.dbo.Zlecenie where (FirmaTransportowa.dbo.Zlecenie.uzytkownikId='" + tmpint1 + "');", stmt, dataOutputStream);
                    text.appendText("\nWyslano aktualne zlecenia do dodawania zlecen");
                } else if (option == 7) {
                    tmpint1 = getUserId(dataInputStream.readUTF(), conn);
                    stmt2 = conn.createStatement();
                    InsertOrder(tmpint1, stmt, stmt2, dataInputStream, dataOutputStream, text);
                    setNotify();
                } else if (option == 8) {
                    stmt2 = conn.createStatement();
                    UpdateOrder(stmt, stmt2, dataInputStream, dataOutputStream, text);
                } else if (option == 9) {
                    tmpint1 = dataInputStream.readInt();
                    EditBase("Delete from FirmaTransportowa.dbo.Zlecenie where FirmaTransportowa.dbo.Zlecenie.id='" + tmpint1 + "';", stmt);
                    text.appendText("\nUsunieto zlecenie");
                    dataOutputStream.writeUTF("Deleted");
                } else if (option == 10) {
                    Selectnewest("select FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.opis,FirmaTransportowa.dbo.Cennik.dataZmiany from FirmaTransportowa.dbo.Cennik order by FirmaTransportowa.dbo.Cennik.dataZmiany DESC;", 1, stmt, dataOutputStream);
                    text.appendText("\nWyslano cennik do Combo dla add pack");
                    Selectnewest("select FirmaTransportowa.dbo.Doplata.typDoplaty,FirmaTransportowa.dbo.Doplata.dataZmiany from FirmaTransportowa.dbo.Doplata order by FirmaTransportowa.dbo.Doplata.dataZmiany DESC;", 0, stmt, dataOutputStream);
                    text.appendText("\nWyslano doplaty do Combo dla add pack");
                } else if (option == 11) {
                    char tmpchar = dataInputStream.readChar();
                    selectonecell("select FirmaTransportowa.dbo.Cennik.gabaryt, FirmaTransportowa.dbo.Cennik.kwota,FirmaTransportowa.dbo.Cennik.dataZmiany from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='" + tmpchar + "' order by FirmaTransportowa.dbo.Cennik.dataZmiany DESC;", 0, stmt, dataOutputStream);
                    text.appendText("\nWyslano kwote do dimenison add pack");
                } else if (option == 12) {
                    tmpstring1 = dataInputStream.readUTF();
                    selectonecell("select FirmaTransportowa.dbo.Doplata.typDoplaty,FirmaTransportowa.dbo.Doplata.kwota,FirmaTransportowa.dbo.Doplata.dataZmiany from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='" + tmpstring1 + "' order by FirmaTransportowa.dbo.Doplata.dataZmiany DESC;", 1, stmt, dataOutputStream);
                    text.appendText("\nWyslano kwote do aditional prizes add pack");
                } else if (option == 13) {
                    InsertPack(stmt, dataOutputStream, dataInputStream, text);
                } else if (option == 21) {
                    String dimension = dataInputStream.readUTF();
                    float amount = dataInputStream.readFloat();
                    String description = dataInputStream.readUTF();
                    String limit = dataInputStream.readUTF();
                    addwithselect("select * from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='" + dimension + "' " +
                            "or FirmaTransportowa.dbo.Cennik.limit='" + limit + "' ", "insert into FirmaTransportowa.dbo.Cennik values ('" + dimension + "','" + amount + "','" + description + "',GETDATE(),'" + limit + "')", stmt, dataOutputStream, text);
                } else if (option == 22) {
                    String type = dataInputStream.readUTF();
                    float amount = dataInputStream.readFloat();
                    addwithselect("select * from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='" + type + "' ", "insert into FirmaTransportowa.dbo.Doplata values ('" + type + "','" + amount + "',GETDATE())", stmt, dataOutputStream, text);
                } else if (option == 23) {
                    String dimension = dataInputStream.readUTF();
                    float amount = dataInputStream.readFloat();
                    String description = dataInputStream.readUTF();
                    String limit = dataInputStream.readUTF();
                    EditBase("update FirmaTransportowa.dbo.Cennik set FirmaTransportowa.dbo.Cennik.gabaryt='" + dimension + "',FirmaTransportowa.dbo.Cennik.kwota='" + amount + "'," +
                            "FirmaTransportowa.dbo.Cennik.opis='" + description + "',FirmaTransportowa.dbo.Cennik.dataZmiany=GETDATE(),FirmaTransportowa.dbo.Cennik.limit='" + limit + "'" +
                            "where FirmaTransportowa.dbo.Cennik.gabaryt='" + dimension + "'", stmt);
                    status = "Edited";
                    text.appendText("\n Dodano do bazy");
                    dataOutputStream.writeUTF(status);
                } else if (option == 24) {
                    String type = dataInputStream.readUTF();
                    float amount = dataInputStream.readFloat();
                    EditBase("update FirmaTransportowa.dbo.Doplata set FirmaTransportowa.dbo.Doplata.typDoplaty='" + type + "',FirmaTransportowa.dbo.Doplata.kwota='" + amount + "'," +
                            "FirmaTransportowa.dbo.Doplata.dataZmiany=GETDATE()  where FirmaTransportowa.dbo.Doplata.typDoplaty='" + type + "'", stmt);
                    status = "Edited";
                    text.appendText("\n Dodano do bazy");
                    dataOutputStream.writeUTF(status);
                } else if (option == 25) {
                    String dimension = dataInputStream.readUTF();
                    EditBase("delete  from FirmaTransportowa.dbo.Cennik where FirmaTransportowa.dbo.Cennik.gabaryt='" + dimension + "'", stmt);
                    text.appendText("\n Usunieto");
                    status = "Deleted";
                    dataOutputStream.writeUTF(status);
                } else if (option == 26) {
                    String type = dataInputStream.readUTF();
                    float amount = dataInputStream.readFloat();
                    EditBase("delete  from FirmaTransportowa.dbo.Doplata where FirmaTransportowa.dbo.Doplata.typDoplaty='" + type + "'" +
                            "and FirmaTransportowa.dbo.Doplata.kwota= '" + amount + "'", stmt);
                    text.appendText("\n Usunieto");
                    status = "Deleted";
                    dataOutputStream.writeUTF(status);
                } else if (option == 27) {
                    SelectFromDatabase(3, "select FirmaTransportowa.dbo.Zlecenie.id, (select FirmaTransportowa.dbo.Oddzial.miejscowosc  \n" +
                            "from FirmaTransportowa.dbo.Oddzial\n" +
                            "where  FirmaTransportowa.dbo.Oddzial.id =FirmaTransportowa.dbo.Zlecenie.oddzialPoczatkowyId),\n" +
                            "(select FirmaTransportowa.dbo.Oddzial.miejscowosc\n" +
                            "from FirmaTransportowa.dbo.Oddzial\n" +
                            "where  FirmaTransportowa.dbo.Oddzial.id =FirmaTransportowa.dbo.Zlecenie.oddzialKoncowyId)\n" +
                            "from FirmaTransportowa.dbo.Zlecenie\n" +
                            "where not FirmaTransportowa.dbo.Zlecenie.id in( select FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId\n" +
                            "from FirmaTransportowa.dbo.ZlecenieKurier \n" +
                            "where FirmaTransportowa.dbo.ZlecenieKurier.zlecenieId = FirmaTransportowa.dbo.Zlecenie.id)", stmt, dataOutputStream);
                    text.appendText("\n Wyslano Zlecenie");
                } else if (option == 28) {
                    SelectFromDatabase(2, "with cte as (\n" +
                            "select K.id Col1, Z.adresKoncowy Col2, ZK.id Col3\n" +
                            ", row_number() over (partition by K.id order by ZK.id desc) RowNum\n" +
                            "from FirmaTransportowa.dbo.Kurier K \n" +
                            "join FirmaTransportowa.dbo.ZlecenieKurier ZK on K.id = ZK.kurierId\n" +
                            "join FirmaTransportowa.dbo.Zlecenie Z on Z.id = ZK.zlecenieId\n" +
                            "where K.id = ZK.kurierId)select Col1, Col2, Col3\n" +
                            "from cte where RowNum = 1\n" +
                            "order by Col1 desc;", stmt, dataOutputStream);
                    text.appendText("\n Wyslano Kurierow");
                } else if (option == 29) {
                    try {
                        String IdCourier = dataInputStream.readUTF();
                        String IdOrder = dataInputStream.readUTF();
                        EditBase("insert into FirmaTransportowa.dbo.ZlecenieKurier values ('" + IdOrder + "','" + IdCourier + "','1')", stmt);
                        dataOutputStream.writeUTF("Added");
                        text.appendText("\n Dodano Zlecenie do kuriera");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (option == 30) {
                    SelectFromDatabase(1, "select FirmaTransportowa.dbo.Oddzial.miejscowosc from FirmaTransportowa.dbo.Oddzial", stmt, dataOutputStream);
                    text.appendText("\nWyslano oddzialy dla spedytora");
                } else if (option == 31) {
                    tmpstring1 = dataInputStream.readUTF();
                    addwithselect("select FirmaTransportowa.dbo.Oddzial.miejscowosc from FirmaTransportowa.dbo.Oddzial where FirmaTransportowa.dbo.Oddzial.miejscowosc='" + tmpstring1 + "'", "insert into FirmaTransportowa.dbo.Oddzial values('" + tmpstring1 + "')", stmt, dataOutputStream, text);
                } else if (option == 32) {
                    LocalDate date = LocalDate.now();
                    int tmpint = date.getMonthValue();
                    String tmpstring = String.valueOf(tmpint);
                    SelectFromDatabase(3, "with cte as\n" +
                            "(select K.id Col1, K.imie Col2, COUNT(P.id) Col3, row_number() \n" +
                            "over (partition by K.id  order by K.id desc) RowNum\n" +
                            "from FirmaTransportowa.dbo.Kurier K right join FirmaTransportowa.dbo.ZlecenieKurier ZK on K.id=ZK.kurierId\n" +
                            "right join FirmaTransportowa.dbo.Zlecenie Z on ZK.zlecenieId=Z.id \n" +
                            "left join FirmaTransportowa.dbo.Paczka P on P.zlecenieId=Z.id\n" +
                            "where P.zlecenieId=Z.id AND MONTH(z.dataNadania)='" + tmpstring + "'\n" +
                            "group by K.id,K.imie)\n" +
                            " select IsNull(Col1,0), IsNull(Col2,0), Col3\n" +
                            "from cte\n" +
                            "order by col1 asc", stmt, dataOutputStream);
                    text.appendText("\n Wyslano Kurierow do plac");
                } else if (option == 33) {
                    tmpint1 = dataInputStream.readInt();
                    UserId = dataInputStream.readInt();
                    LocalDate data;
                    data = LocalDate.now();
                    int rok = data.getYear();
                    int miesiac = data.getMonthValue();
                    addwithselect("select * from FirmaTransportowa.dbo.WyplataKurier where FirmaTransportowa.dbo.WyplataKurier.rok='" + rok + "' AND FirmaTransportowa.dbo.WyplataKurier.miesiac='" + miesiac + "'AND FirmaTransportowa.dbo.WyplataKurier.kurierId='" + UserId + "'", "insert into FirmaTransportowa.dbo.WyplataKurier values('" + rok + "','" + miesiac + "','" + tmpint1 + "','" + UserId + "')", stmt, dataOutputStream, text);
                } else if (option == 41) {
                    tmpstring1 = dataInputStream.readUTF();
                    SelectFromDatabase(6, "with cte as (\n" +
                            "select Z.id Col1, P.id Col2, Z.dataNadania Col3, Z.adresPoczatkowy Col4, Z.adresKoncowy Col5, Z.status Col6\n" +
                            "from FirmaTransportowa.dbo.Zlecenie Z \n" +
                            "left join FirmaTransportowa.dbo.Paczka P on P.zlecenieId = Z.id  \n" +
                            "join FirmaTransportowa.dbo.ZlecenieKurier ZK on ZK.zlecenieId = Z.id\n" +
                            "join FirmaTransportowa.dbo.Kurier K on K.id = ZK.kurierId\n" +
                            "join FirmaTransportowa.dbo.Uzytkownik U on U.id = K.uzytkownikId\n" +
                            "where z.status <> 'Dostarczone'\n" +
                            "and u.mail='" + tmpstring1 + "'\n" +
                            ")\n" +
                            "select Col1,  Col3, Col4, Col5, Col6, count(Col2)\n" +
                            "from cte\n" +
                            "group by Col1, Col3, Col4, Col5, Col6", stmt, dataOutputStream);
                    text.appendText("\nWyslano aktualne zlecenia dla kuriera");
                } else if (option == 42) {
                    try {
                        tmpstring1 = dataInputStream.readUTF();
                        tmpint1 = dataInputStream.readInt();
                        EditBase("UPDATE FirmaTransportowa.dbo.Zlecenie SET status = '" + tmpstring1 + "' WHERE id = '" + tmpint1 + "';", stmt);
                        text.appendText("\nZakutalizowano status dostawy");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (option == 43) {
                    tmpstring1 = dataInputStream.readUTF();
                    SelectFromDatabase(1, "SELECT FirmaTransportowa.dbo.Kurier.typKuriera FROM\n" +
                            "  FirmaTransportowa.dbo.Kurier, FirmaTransportowa.dbo.Uzytkownik WHERE\n" +
                            "  FirmaTransportowa.dbo.Kurier.uzytkownikId = FirmaTransportowa.dbo.Uzytkownik.id AND\n" +
                            "  FirmaTransportowa.dbo.Uzytkownik.mail = '" + tmpstring1 + "';", stmt, dataOutputStream);
                    text.appendText("\nPobrano typ Kuriera");
                } else if (option == 44) {
                    try {
                        tmpstring1 = dataInputStream.readUTF();
                        String tmpstring2 = dataInputStream.readUTF();
                        EditBase("UPDATE FirmaTransportowa.dbo.Kurier SET" +
                                " FirmaTransportowa.dbo.Kurier.typKuriera = '" + tmpstring1 + "' WHERE" +
                                " FirmaTransportowa.dbo.Kurier.uzytkownikId = " +
                                "(SELECT FirmaTransportowa.dbo.Uzytkownik.id " +
                                "FROM FirmaTransportowa.dbo.Uzytkownik " +
                                "WHERE FirmaTransportowa.dbo.Uzytkownik.mail = '" + tmpstring2 + "');", stmt);
                        text.appendText("\nZaktualizowano typ kuriera");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
            conn.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserId(String mail, Connection conn) throws SQLException {
        stmt = conn.createStatement();
        try {
            String sql = "select FirmaTransportowa.dbo.Uzytkownik.id from FirmaTransportowa.dbo.Uzytkownik where FirmaTransportowa.dbo.Uzytkownik.mail='" + mail + "';";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            UserId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserId;
    }
}