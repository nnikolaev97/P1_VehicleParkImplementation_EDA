package TestPrac1;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import prac1.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleParkImplementationTest {

    static Plate unknown [] = {
            new Plate (1, "9999", "KGT"), //0
            new Plate (6, "3344", "PPT"), //0
            new Plate (4, "0489", "JKP"), //3
            new Plate (5, "5239", "BRO")  //5
    };

    static Plate plates [] = {
            new Plate (1, "0254", "AAC"), //0
            new Plate (2, "3325", "KBD"), //1
            new Plate (3, "1245", "WJI"), //2
            new Plate (4, "0488", "JKP"), //3
            new Plate (4, "8745", "AAC"), //4
            new Plate (5, "5239", "BRN"), //5
            new Plate (1, "6478", "ALP"), //7
            new Plate (1, "0254", "ATC"), //8
            new Plate (3, "1215", "KMD"), //9
            new Plate (2, "0036", "ZBD"), //10
            new Plate (2, "0036", "KBD"), //11
    };

    static String owners [] = {
            "Joan", "Carles", "Joan", "Pere", "Maria", "Josep",
            "Carles", "Maria", "Marta", "Pere", "Joan", "Joan"
    };

    static VehiclePark vp = new VehicleParkImplementation();
        static{
            vp.enter(new PrivateVehicle(plates[0], 10, owners[2], 4, 4));
            vp.enter(new PrivateVehicle(plates[1], 10, owners[2], 4, 4));
            vp.enter(new PrivateVehicle(plates[2], 10, owners[1], 4, 4));
            vp.enter(new PrivateVehicle(plates[3], 10, owners[0], 4, 4));
        }

    static PrivateVehicle [] privs = new PrivateVehicle [2];
        static{
            privs[0] = new PrivateVehicle(plates[4], 10, owners[2], 4, 4);
            privs[1] = new PrivateVehicle(plates[5], 10, owners[2], 4, 4);
    }
    static CommercialVehicle [] comms = new CommercialVehicle [4];
        static{
            comms[0] = new CommercialVehicle(plates[6], 10, owners[2], 750, false);
            comms[1] = new CommercialVehicle(plates[7], 10, owners[2], 750, false);
            comms[2] = new CommercialVehicle(plates[8], 10, owners[1], 750, false);
            comms[3] = new CommercialVehicle(plates[9], 10, owners[0], 750, true);
    }
    @Test
    @Order(1)
    void inPark() {

        assertFalse(vp.inPark(unknown[0]),"This plate should not be already in the park");
        assertFalse(vp.inPark(unknown[1]),"This plate should not be already in the park");
        assertFalse(vp.inPark(unknown[2]),"This plate should not be already in the park");
        assertFalse(vp.inPark(unknown[3]),"This plate should not be already in the park");

        assertTrue(vp.inPark(plates[0]), "This plate should be in the vehiclepark");
        assertTrue(vp.inPark(plates[1]), "This plate should be in the vehiclepark");
        assertTrue(vp.inPark(plates[2]), "This plate should be in the vehiclepark");
        assertTrue(vp.inPark(plates[3]), "This plate should be in the vehiclepark");

    }

    @Test
    @Order(2)
    void enter() {

        NullPointerException exceptionNull = assertThrows(NullPointerException.class, () -> {
            vp.enter((Vehicle) null);
        });
        AlreadyStoredException exceptionStored = assertThrows(AlreadyStoredException.class, () -> {
            vp.enter(new PrivateVehicle(plates[1], 10, owners[2], 4, 4));
        });


        assertEquals("Null parameter", exceptionNull.getMessage(), "Exception message should match");
        assertEquals("Already there", exceptionStored.getMessage(), "Exception message should match");


    }

    @Test
    @Order(3)
    void testEnter() {

        Collection colStored = new ArrayList();

        colStored.addAll(Arrays.asList(privs));
        colStored.addAll(Arrays.asList(comms));
        colStored.add(null);

        int stored = vp.enter(colStored);

        assertEquals(6, stored, "You added more or less than the required amount");

    }

    @Test
    @Order(4)
    void leave() {

          assertFalse(vp.leave(unknown[0]), "This plate was never added in Vehiclepark");
          assertFalse(vp.leave(unknown[1]), "This plate was never added in Vehiclepark");
          assertFalse(vp.leave(unknown[2]), "This plate was never added in Vehiclepark");
          assertFalse(vp.leave(unknown[3]), "This plate was never added in Vehiclepark");

          assertTrue(vp.leave(plates[0]), "This plate was added in Vehiclepark");
          assertTrue(vp.leave(plates[1]), "This plate was added in Vehiclepark");
          assertTrue(vp.leave(plates[2]), "This plate was added in Vehiclepark");
          assertTrue(vp.leave(plates[3]), "This plate was added in Vehiclepark");

    }

    @Test
    @Order(5)
    void testLeave() {

            assertEquals(5, vp.leave(owners[2]).length, "The removed vehicles should have the same owner name");
            assertEquals(0, vp.leave(owners[3]).length, "You should've removed 0 vehicles");

    }

    @Test
    @Order(6)
    void containsDangerousPayload() {

            VehiclePark vpDangerous = new VehicleParkImplementation();
            vpDangerous.enter(comms[3]);

            assertFalse(vp.containsDangerousPayload(), "There should be no dangerous payload in Vehiclepark");
            assertTrue(vpDangerous.containsDangerousPayload(), "This VehiclePark should be dangerous");
    }
}