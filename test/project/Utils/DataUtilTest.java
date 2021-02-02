package project.Utils;

import project.Utils.DataUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataUtilTest {

    @Test
    void distanceCorrectCalculations() {

        assertEquals(180.49310929417464, DataUtil.distance(-20.37, -21.02,-50.53, -52.12 ));

    }
}