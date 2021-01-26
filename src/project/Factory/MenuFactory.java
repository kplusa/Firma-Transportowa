package project.Factory;


import project.Controller.ClientMenuForm;
import project.Controller.CourierMenuForm;
import project.Controller.ForwarderMenuForm;
import project.Utils.DataUtil;

public class MenuFactory extends DataUtil {


    public MenuFactory getMenu(String name) {
        if (name.equals("Client"))
            return new ClientMenuForm();
        else if (name.equals("Courier"))
            return new CourierMenuForm();
        else if (name.equals("Forwarder"))
            return new ForwarderMenuForm();
        return null;
    }
}
