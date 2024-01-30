package TestPrac1;

import org.junit.jupiter.api.Test;
import prac1.Plate;

import static org.junit.jupiter.api.Assertions.*;

class PlateTest {

    @Test
    void compareTo() {
        Plate testPlate = new Plate(5 , "0234", "ree");
        Plate tesplateRe = new Plate(4 , "0234", "ree");
        assertEquals(1, testPlate.compareTo(tesplateRe));
    }

    @Test
    void testEquals() {
        Plate testPlate = new Plate(5 , "0234", "ree");
        Plate tesPlate2 = new Plate(5 , "0234", "ree");
        assertTrue(testPlate.equals(tesPlate2));
    }
}