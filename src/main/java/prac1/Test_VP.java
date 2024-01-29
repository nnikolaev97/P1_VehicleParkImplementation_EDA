package prac1;

import static utilsProva.UtilsProva.*;

import java.util.*;

public class Test_VP {
	
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
			new Plate (6, "9157", "UTH"), //6
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
	
	static Plate aPlate = new Plate(3, "8978", "RTY");
	static Vehicle aVehicle = new PrivateVehicle(aPlate, 5, "Carla", 4, 4);
	
	static PrivateVehicle [] privs = new PrivateVehicle [6];
	static CommercialVehicle [] comms = new CommercialVehicle [6];
	
	static VehiclePark vp = new VehicleParkImplementation();
	static ArrayList<Vehicle> allVehicles = new ArrayList();
	
	public static void main (String [] args) {
		for (int i=0; i<6; i++) {
			privs[i] = new PrivateVehicle(plates[i], 10, owners[i], 4,4 );
			allVehicles.add(privs[i]);
		}
		for (int i=0; i<6; i++) {
			comms[i] = new CommercialVehicle(plates[i+6], 10, owners[i+6], 750, false);
			allVehicles.add(comms[i]);
		}
		
		// inserció de vehicles 1 a 1. No s'esperen excepcions
		provar("Inserció amb enter(Vehicle)");
		try {
			Arrays.stream(privs).forEach(p->vp.enter(p));
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("Inserció amb enter(Vehicle) finalitzada");
		
		// inserció de vehicles amb Collection. No s'esperen excepcions
		provar("Inserció amb enter(Collection)");
		try {
			vp.enter(Arrays.asList(comms));
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("Inserció amb enter(Collection) finalitzada");

		
		// verifiquem que tenim totes les matricules que hem de tenir
		// no s'esperen excepcions
		provar("In park per a matrícules de vehicles inserits");
		try  {
			for (Plate p : plates) {
				if (!vp.inPark(p)) {
					notificarError("inPark ha retornat false quan s'esperava true: "+p, SORTIR);
				}
			}
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("In park per a matrícules de vehicles inserits aparentment ok");
		
		// verifiquem que inPark no respon true per matrícules inexistents
		provar("In park per a matrícules de vehicles NO inserits");
		try  {
			for (Plate p : unknown) {
				if (vp.inPark(p)) {
					notificarError("inPark ha retornat true quan s'esperava false: "+p, SORTIR);
				}
			}
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("In park per a matrícules de vehicles NO inserits aparentment ok");
		
		// provem leave amb matrícules que no hi són
		provar ("Leave amb paràmetre plate. Matrícules inexistents");
		try {
			for (Plate p: unknown) {
				if (vp.leave(p))
					notificarError("leave ha retornat true quan s'esperava false", SORTIR);
			}
		}
		catch(Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar ("Leave amb paràmetre plate. Matrícules inexistents. Aparentment correcte");
		
		
		// provem leave amb matrícules que sí hi son...
		provar ("Leave amb paràmetre plate. Matrícules existents");
		try {
			for (Vehicle v: comms) {
				if (!vp.leave((Plate)v.getPlate().clone()))
					notificarError("leave ha retornat false quan s'esperava true", SORTIR);
			}
		}
		catch (Exception e)  {
			notificarExcepcio(e, SORTIR);
		}
		informar ("Leave amb paràmetre plate. Matrícules existents. Aparentment correcte");
		
		// verificar amb InPark que les matrícules ja no hi són
		// verifiquem que inPark no respon true per matrícules inexistents
		provar("In park per a matrícules de vehicles ELIMINATS");
		try  {
			for (Vehicle v : comms) {
				if (vp.inPark(v.getPlate())) {
					notificarError("inPark ha retornat true quan s'esperava false: "+v.getPlate(), SORTIR);
				}
			}
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("In park per a matrícules de vehicles ELIMINATS aparentment ok");

		
		// retornem a l'estat anterior a leave...
		// inserció de vehicles amb Collection. No s'esperen excepcions
		provar("Inserció amb enter(Collection) segona volta");
		try {
					vp.enter(Arrays.asList(comms));
		}
		catch (Exception e) {
				notificarExcepcio(e, SORTIR);
		}
		informar("Inserció amb enter(Collection) segona volta finalitzada");
		
		// provem la versio "by owner" de leave
		provar("Leave amb paràmetre owner (String)");
		try {
			Collection<Vehicle> removed = Arrays.asList(vp.leave(new String("Joan".toCharArray())));
			// check that removed contains all the cars that belong to Joan...
			for (Vehicle v : allVehicles) {
				if (v.getOwner().equals("Joan")) {
					if (!removed.contains(v))
						notificarError("El contingut retornat per leave no és correcte", SORTIR);
				}
			}
			//... and nothing more
			if (removed.size()!=4)
				notificarError("La mida del resultat retornat per leave no és correcta", SORTIR);
			
			// check that the cars that belong to Joan are no longer in vp
			for (Vehicle v : allVehicles) {
				if (v.getOwner().equals("Joan") && vp.inPark(v.getPlate()))
						notificarError("Leave no ha fet les eliminacions escaients", SORTIR);
			}
			// check that the cars that do not belong to Joan are in vp
			for (Vehicle v : allVehicles) {
				if (!v.getOwner().equals("Joan") && !vp.inPark(v.getPlate()))
						notificarError("Leave ha fet eliminacions incorrectes", SORTIR);
			}
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("Leave amb paràmetre owner (String). Aparentment ok");
		
		// provem containsDangerousPayload
		provar ("containsDangerousPayload");
		try {
			if (vp.containsDangerousPayload()==true)
				notificarError("containsDangerousPayload ha retornat true quan s'esperava false", SORTIR);
			comms[2].setDangerousPayload(true);
			if (vp.containsDangerousPayload()==false)
				notificarError("containsDangerousPayload ha retornat false quan s'esperava true", SORTIR);
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("containsDangerousPayload aparentment correcte");
		
		// enter (v1) amb repeticions
		provar("Inserció (enter) amb repeticions");
		try {
			vp.enter(comms[2]);
			vp.enter(privs[0]);
			notificarError("enter no ha llançat AlreadySotoredException per a vehicles reptits", SORTIR);
		}
		catch(AlreadyStoredException e) {
			// aquest és el comportament esperat
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("enter (v1) amb repeticions, comportament aparentment correcte");
		
		// enter (v1) amb nulls
		provar("Inserció (enter) amb nulls");
		try {
			vp.enter((Vehicle)null);
			notificarError("enter no ha llançat NullPointerException per a vehicle null", SORTIR);
		} catch (NullPointerException e) {
			// aquest és el comportament esperat
		} catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar("enter (v1) amb nulls, comportament aparentment correcte");
		
		
		// enter v2 amb nulls, no vehicles i repeticions
		provar ("Inserció (enter) v2 amb 'patologies'");
		Collection col = new ArrayList();
		col.add(null); col.add(comms[0]);
		col.add("a string");
		col.add(aVehicle);
		try {
			int n = vp.enter(col);
			if (n!=1)
				notificarError("enter v2 resultat incorrecte. "+n+"quan s'esperava 1", SORTIR);
			n = vp.enter(col);
			if (n!=0)
				notificarError("enter v2 resultat incorrecte. "+n+"quan s'esperava 0", SORTIR);
		}
		catch (Exception e) {
			notificarExcepcio(e, SORTIR);
		}
		informar ("Inserció (enter) v2 amb 'patologies' aparentement correcte");
		
		//----------------------
		System.out.println();
		finalitzar();
		
	}
	
}
