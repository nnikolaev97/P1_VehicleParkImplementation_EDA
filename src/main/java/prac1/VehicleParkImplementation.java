package prac1;

import java.util.*;

public class VehicleParkImplementation implements VehiclePark{

	private Collection infrastructure;
	// do not add more attributes. This is all you really need
	
	public VehicleParkImplementation () {
		infrastructure = new ArrayList<>();/* COMPLETE */
	}
	
	
	@Override
	public int numVehicles() {
		/* Returns the number of vehicles currently in the vehicle park */
		/* COMPLETE */
		return infrastructure.size();
	}
	
	@Override
	public boolean isEmpty() {
		/* Returns true if the vehicle park is empty, false otherwise */
		/* COMPLETE */
		return infrastructure.isEmpty();
	}


	@Override
	public int numPrivate() {
		/* Returns the number of private vehicles in the Vehicle Park */
		/* COMPLETE */
		int count = 0;

		for (Object vehicle : infrastructure) {
			if(vehicle instanceof PrivateVehicle){
				count++;
			}
		}
		return count;
	}

	@Override
	public boolean inPark (Plate p) {
		/* Returns true if the vehicle park contains a vehicle with plate p */
		/* COMPLETE */
		Vehicle vehicleT;

		for (Object vehicle : infrastructure) {
			vehicleT = (Vehicle) vehicle;
			if (vehicleT.getPlate().equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void enter(Vehicle v) {
		/* Stores c in the VehiclePark, if possible.
		   Throws (and does not store v):
		   	- a NullPointerException if the parameter is null
		   	- a AlreadyStoredException if the VehiclePark already contains a
		   	  vehicle "like" v
		 */
		/* COMPLETE */
		if(v == null)
			throw new NullPointerException("Null parameter");
		if(inPark(v.getPlate()))
			throw new AlreadyStoredException("Already there");

		infrastructure.add(v);
	}

	
	
	@Override
	public int enter(Collection vehicles) {
		/* Stores in the VehiclePark the vehicles contained in the parameter
		   Beware: the parameter may contain objects that are not vehicles
		   Beware: the parameter may contain null objects
		   Beware: the parameter may contain repetition
		   
		   Returns: the number of vehicles effectively stored 
		   
		   This method does not throw exceptions
		 */
		
		/* base this methods in the previous one 
		 (make it call the previous one) */
		
		/* COMPLETE */
		int successfullyAdded = 0;
		Vehicle vehicleT;

		for (Object vehicle : vehicles) {
			if(vehicle instanceof Vehicle){
				try{
					vehicleT = (Vehicle) vehicle;
					enter(vehicleT);
					successfullyAdded++;
				}
				catch (NullPointerException | AlreadyStoredException e){
					System.err.println("Failed to add a vehicle: " + e.getMessage());
				}

			}
		}
		return successfullyAdded;
	}

	@Override
	public boolean leave(Plate p) {
		/* 
	 	Removes from VehiclePark all vehicles with Plate p
	 	Returns true if a vehicle has been removed. False otherwise
		 */
		
		/* You can take into account that Vehicle equality 
		   is based on Plate equality */
		
		/* COMPLETE */
		Vehicle vehicleT;

		for (Object vehicle : infrastructure) {
			vehicleT = (Vehicle) vehicle;
			if (vehicleT.getPlate().equals(p)) {
				infrastructure.remove(vehicleT);
				return true;
			}
		}
		return false;
	}

	
	@Override
	public Vehicle[] leave(String owner) {
		/* 
 		Removes from VehiclePark all vehicles that "belong" to the given owner
 		(parameter)
 		Returns an array containing all the removed vehicles. This array:
 			- has the exact length (no empty -null- positions)
 			- has length 0 if no vehicles has been removed
 			- is sorted according to the natural ordering of the vehicles (ascending)
	 */
	  /* COMPLETE */
		List<Vehicle> removedVehicles = new ArrayList<>();
		Vehicle vehicleT;

		for (Object vehicle : infrastructure) {
			vehicleT = (Vehicle) vehicle;
			if (vehicleT.getOwner().equals(owner)) {
				removedVehicles.add(vehicleT);
			}
		}
		infrastructure.removeAll(removedVehicles);
		Vehicle[] removedVehiclesArray = removedVehicles.toArray(new Vehicle[0]);
		Arrays.sort(removedVehiclesArray);

		return removedVehiclesArray;
	}

	@Override
	public boolean containsDangerousPayload() {
		/*
	 	Returns true if the VehiclePark contains any CommercialVehicle 
	 	the payload of which is dangerous. False otherwise;
		 */
		/* COMPLETE */

		CommercialVehicle vehicleT;

		for (Object vehicle : infrastructure) {
			if(vehicle instanceof CommercialVehicle){
				vehicleT = (CommercialVehicle) vehicle;
				if(vehicleT.containsDangerousPayload())
					return true;
			}
		}
		return false;
	}

	
	
}
