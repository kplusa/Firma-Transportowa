package project.Facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ConfigDBTest {

    @Test
    void configTest() {
        ConfigDB configDB = new ConfigDB();

        Connection conn = configDB.config();

        Assertions.assertNotEquals(null, conn);
    }
}