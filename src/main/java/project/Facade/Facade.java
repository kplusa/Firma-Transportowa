package project.Facade;

import com.jfoenix.controls.JFXTextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Facade extends Thread {
    ConfigDB configDB = new ConfigDB();
    DataBaseSelect dataBaseSelect = new DataBaseSelect();
    OpenStreetMapBase openStreetMapBase = new OpenStreetMapBase();
    UserAuthentication userAuthentication = new UserAuthentication();
    static String notify = "";

    public void login(Statement stmt, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws IOException, SQLException {
        this.userAuthentication.login(stmt, dataInputStream, dataOutputStream, text);
    }

    public void register(Statement stmt, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws IOException, SQLException {
        this.userAuthentication.register(stmt, dataInputStream, dataOutputStream, text);
    }

    public void UpdateOrder(Statement stmt, Statement stmt2, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws IOException {
        this.openStreetMapBase.UpdateOrder(stmt, stmt2, dataInputStream, dataOutputStream, text);
    }

    public void InsertOrder(int tmpint, Statement stmt, Statement stmt2, DataInputStream dataInputStream, DataOutputStream dataOutputStream, JFXTextArea text) throws IOException {
        this.openStreetMapBase.InsertOrder(tmpint, stmt, stmt2, dataInputStream, dataOutputStream, text);
    }

    public void SelectFromDatabase(int rows, String sql, Statement stmt, DataOutputStream dataOutputStream) {
        this.dataBaseSelect.SelectFromDatabase(rows, sql, stmt, dataOutputStream);
    }

    public void EditBase(String sql, Statement stmt) {
        this.dataBaseSelect.EditBase(sql, stmt);
    }

    public void addwithselect(String sql, String insertSql, Statement stmt, DataOutputStream dataOutputStream, JFXTextArea text) {
        this.dataBaseSelect.addwithselect(sql, insertSql, stmt, dataOutputStream, text);
    }

    public void selectonecell(String sql, int option, Statement stmt, DataOutputStream dataOutputStream) {
        this.dataBaseSelect.selectonecell(sql, option, stmt, dataOutputStream);
    }

    public void Selectnewest(String sql, int option, Statement stmt, DataOutputStream dataOutputStream) {
        this.dataBaseSelect.Selectnewest(sql, option, stmt, dataOutputStream);
    }

    public void InsertPack(Statement stmt, DataOutputStream dataOutputStream, DataInputStream dataInputStream, JFXTextArea text) {
        this.dataBaseSelect.InsertPack(stmt, dataOutputStream, dataInputStream, text);
    }

    public Connection config() {
        return this.configDB.config();
    }

    public static void setNotify() {
        notify = "Nowe zlecenie";
    }

    public static void deleteNotify() {
        notify = "";
    }

    public static String getNotify() {
        return notify;
    }
}
