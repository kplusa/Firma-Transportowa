package project.Factory;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import project.Controller.ClientMenuForm;
import project.Controller.CourierMenuForm;
import project.Controller.ForwarderMenuForm;

import java.io.IOException;

public interface Menu {
    Parent loadFXML(FXMLLoader loader, ForwarderMenuForm forwarderMenuForm, JFXTextField mail , String clientType) throws IOException;
    Parent loadFXML(FXMLLoader loader, CourierMenuForm courierMenuFormMenuForm, JFXTextField mail , String clientType) throws IOException;
    Parent loadFXML(FXMLLoader loader, ClientMenuForm clientMenuForm, JFXTextField mail , String clientType) throws IOException;
}
