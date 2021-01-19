package TPS_package;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class RFID_Evaluation { 
		
	protected int local_streetID;
	protected HashMap<Integer, Integer> local_RFID;

	protected HashMap<Integer, Integer> temp = new HashMap<Integer, Integer>();
	protected HashMap<Integer, Integer> onStreet_AEV;// AEV = ACTIVE EMERGENCY VEHICLES.
	protected HashMap<Integer, Integer> onStreet_PTV;// PTV = PUBLIC TRANSPORT VEHICLES.
	protected HashMap<Integer, Integer> Sorted_RFID_EV;// EV =  EMERGENCY VEHICLES.
	protected HashMap<Integer, Integer> Sorted_RFID_PTV;// PTV = PUBLIC TRANSPORT VEHICLES.
	
 	/** Sorts and stores the active RFID vehicles.
 	 * @param RFID the RFID hashmap for the whole city.
 	 * @param streetID the current streetID
 	 * @return: a array with the number of 
 	 * @throws IOException
 	 */
 	public HashMap<Integer, String> sortAndStoreRFID(HashMap<Integer, Integer> RFID, int streetID) throws IOException {
 		memoryHandling memory_Handling = new memoryHandling();
 		// assigne the local variables.
 		local_RFID = RFID;
 		local_streetID = streetID;
 		
 		//sort out the active vehicles that are on the street.
 		Sorted_RFID_EV = sortVehicles(1, 3);
 		Sorted_RFID_PTV = sortVehicles( 4, 6);
 		onStreet_AEV = vehicleIsOnsStreet(1);
		onStreet_PTV = vehicleIsOnsStreet(2);
		
		for (Entry<Integer, Integer> entry : onStreet_AEV.entrySet()){
			temp.put(entry.getKey(), entry.getValue());
		}
		for (Entry<Integer, Integer> entry : onStreet_PTV.entrySet()){
			temp.put(entry.getKey(), entry.getValue());
		}
		// ensures that the temp hashmap is empty if the there are no active vehicles on the street. 
		if(onStreet_AEV.isEmpty() == true || onStreet_PTV.isEmpty() == true  ) {
			 temp.clear();
		}
		return convertRFIDToArray();
		
 	}
 	
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	
	/**Splits the RFID into an array. 
 	 * @return: An hashmap there the streetID is the key and the value is an array containing:
	 * 	         intArray [0]== complete RFID.	
	 * 	         intArray [1]== Last detected sensorID.	
	 *  	     intArray [2]== Activer boolean.
 	 *  	     intArray [3]== Rout
 	 * @throws IOException
 	 */
	public HashMap<Integer, String> convertRFIDToArray() throws IOException{
		int[] intArray = {0,0,0,0};
		HashMap<Integer, String> returnHashMap = new HashMap<Integer, String>();
		String stringText = "";
		// if there are no active vehicles scip this part.
		if(temp.isEmpty() == false) {
			//Splits up the RFID data and stores it in a array.
			for (Entry<Integer, Integer> entry : temp.entrySet()){
				intArray[0] = entry.getValue();
				intArray[1] = getSensorIDFromRFID(entry.getValue());
				intArray[2] = (entry.getValue()/1000)%10;
				String route ="";
						route += entry.getValue()%10;
						route += (entry.getValue()/10)%10;
						route += (entry.getValue()/100)%10;
				intArray[3] = Integer.parseInt(route);
				stringText = intArray[0]+";"+intArray[1]+";"+intArray[2]+";"+intArray[3];
				returnHashMap.put(entry.getKey(), stringText);
			}	
		}
		
		/*sets all the values to zero if no vehicles vas active.
		else {
			//stringText = intArray[0]+";"+intArray[1]+";"+intArray[2]+";"+intArray[3];
			//returnHashMap.put(-10000, stringText);
			/*for(int i = 10000; i<=60000; i += 10000) {
				for(int j = 1; j<=1; j++) {
				
				}	
			}
		}*/
		return returnHashMap;
	}
	
	/**
	 * Fetches the last sensor that the vehicle has passed. 
	 * @param RFID: the vehicles RFID data.
	 * @return: the last sensor that the vehcile passed. i.e for streetID= 12 => returns 1, streetID 910 => returns 9 and so on. 
	 */
	public int getSensorIDFromRFID(int RFID){
		int street_id = RFID/10000;
		int sensor;
		
		if(street_id < 100) {
			sensor = street_id/10;		
		}
		else {
			sensor = street_id/100;
		}
		return sensor;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////Code for sorting out the vechiles on the street./////////////////////////////////
	/**
	 * Sorts out the vehicles that is on the street.
	 * @param c: variable to decide witch of the hashmaps Sorted_RFID_EV and
	 * 			 Sorted_RFID_PTV we wan`t to sort. 
	 * @return: A hashmap with the RFID vehicles that currently are active and on the street.
	 * @throws IOException
	 */
	public HashMap<Integer, Integer> vehicleIsOnsStreet(int c) throws IOException{
		
		HashMap<Integer, Integer> onStreetHashMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();	
		
		// Decide if it is emergency or public transport vehicles that will be checked.
		switch(c) {
		case 1:
			hashMap = Sorted_RFID_EV;
			break;
		case 2:
			hashMap = Sorted_RFID_PTV;
			break;
		}

		for (Entry<Integer, Integer> entry : hashMap.entrySet()){
			// Examine if the vehicle is on the street.
			if(isItOnStreet(entry.getValue())) {
				//Register if the emergency vehicles are active
				if(typeExamination(entry.getKey(), 1, 3)) 
					// Examine if the vehicle is active.
					{if(isActive(entry.getKey(), entry.getValue())){
						onStreetHashMap.put(entry.getKey(), entry.getValue());
					}
				}
				//Register the public transport vehicles
				else if(typeExamination(entry.getKey(), 4, 6)){
					// Examine if the vehicle is active ( i.e it is driving a route).
					if(isActive(entry.getKey(), entry.getValue())){
						onStreetHashMap.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
		return onStreetHashMap;
	}
	
	/**Examine if the vehicle is on the street. 
	 * @param RFIDData: The vehicles RFID data  
	 * @return: true if vehicle is on the street and return false otherwise 
	 * @throws IOException
	 */
    public boolean isItOnStreet(int RFIDData) throws IOException {
    	// get the streetID from the RFID
    	int rfid_streetID = RFIDData/10000; 
    	boolean returnValue = false;
    	// check if the steetID from the RFID matches the current streetID.
    	if(rfid_streetID == local_streetID) {
    		returnValue = true;  	
        }
        	
    	return returnValue;    
    }
    

	/**Examine the type of the vehicle
	 * @param The vehicle identification. 
	 * @param a: 1 for emergency vehicles and 4 for public transport vehicles 
	 * @param b: 3 for emergency vehicles and 6 for public transport vehicles 
	 * @return: true if a <= key <= b. returns false otherwise.
	 * @throws IOException
	 */
    public boolean typeExamination(int key, int a, int b) throws IOException {
    	int type = key/10000;
        if( a <= type && type <= b)
        	{return true;}
    	return false;    
    }

    /**
     *  Check if the emergency vehicle are active (or if the public transports are on a route).
     * @param key: the vehicle ID
     * @param value: the RFID data of the vehicle.
     * @return: true if active is equal to 1.
     * @throws IOExceptio
     * @param value
     * @return
     * @throws IOException
     */
    public boolean isActive(int key, int value) throws IOException {
    	//determine if it is an emergency vehicle or if it is an public transport vehicle.
    	int k = key/10000;
    	if(1 <= k && k < 4) {
    		//gets the variable what tells says if the vehicle is active or not.
    		int active = (value/1000)%10;
    			if( active == 1){
    				return true;
    				}
    	}
    	// if the last 3 digit in the RFID code all are 0 then are the public transport 
    	// vehicle currently not driving any rout an is there for inactive.  
    	else{
    		if((value%10)==0 && (value%100)==0 && (value%1000)==0){
    			return false;
    		}
    		else{
    			return true;
    		}
    	}
    	return false;    
    }
	
	/**Sorts out all the emergency/ public transport vehicles on a street.
	 * @param a: 1 for emergency vehicles and 4 for public transport vehicles 
	 * @param b: 3 for emergency vehicles and 6 for public transport vehicles 
	 * @return: A hasmap with only the emergency vehicles.
	 * @throws IOException
	 */
	public HashMap<Integer, Integer> sortVehicles(int a, int b) throws IOException {
		HashMap<Integer, Integer> sortedHashMap = new HashMap<Integer, Integer>();	
		//loop through all the vehicles.
		for (Entry<Integer, Integer> entry : local_RFID.entrySet()){
			if(typeExamination(entry.getKey(), a, b)){
				sortedHashMap.put(entry.getKey(), entry.getValue());
			}	
		}	
		return sortedHashMap;
	}
						
}
