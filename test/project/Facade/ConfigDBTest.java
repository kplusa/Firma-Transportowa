package project.Facade;

import project.Facade.ConfigDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class ConfigDBTest {

    @Test
    void configTest() {
        ConfigDB configDB = new ConfigDB();

        Connection conn = configDB.config();

        Assertions.assertNotEquals(null, conn);
    }
}