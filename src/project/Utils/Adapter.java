package project.Utils;

import java.util.Map;

public interface Adapter {
    String getRequest(String url) throws Exception;

    Map<String, Double> getCoordinates(String address);
}
