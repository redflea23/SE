package TPS_package;

import java.util.HashMap;
import java.util.Map.Entry;
import java.io.IOException;

public class participantCounting {
	
	protected stringConverter string_Converter = new stringConverter();

	
	protected int local_streetID;

	protected int[] oldValue = {0,0,0,0};
	protected int[] relayValues = {0, 0, 0, 0};
	protected int[] local_streetSensor1;
	protected int[] local_streetSensor2;
	
	protected HashMap<Integer, String> MemoryValues = new HashMap<Integer, String>();
	protected HashMap<Integer, int[]> oldValues = new HashMap<Integer, int[]>();
	protected HashMap<Integer, int[]> active_RFID_vehicles = new HashMap<Integer, int[]>();
	
	/**
	 *  Counts the number of participants per category on the street.
	 * @param streetID: the current streets ID
	 * @param videoFeed: the videoFeed from the current street
	 * @param streetSensorData: data from the pedestrians sensors
	 * @param activVeicles: a hashmap containg the active vehicles and ther RFID data as a string variable.
	 * @return A hashmap containg the streetID as key and a string representation of the array containg all the values.
	 * @throws IOException
	 */
	public HashMap<Integer, String> count(int streetID, int videoFeed, int[][] streetSensorData,HashMap<Integer, String> activVeicles) throws IOException {
		memoryHandling memory_Handling = new memoryHandling();
		
		//declaring of local variables
		local_streetID = streetID;
		local_streetSensor1 = streetSensorData[0]; 
		local_streetSensor2 = streetSensorData[1]; 
		
		//ensures that the array with the relay values are empty.
		for(int k = 0; k< 4;k++) {
			relayValues[k] = 0;
		}
		
		// Fetches the old participant values. and the active RFID veichles on the street.
		oldValues.putAll(string_Converter.StringToIntArrayHashMap(memory_Handling.readMemory(1)));	

		// if there exists old values add them to oldValue
		// else: just ceep oldValues as {0, 0, 0, 0}; 
		if(oldValues.get(local_streetID) != null) {
			oldValue = oldValues.get(local_streetID);
		}				
		
		//calculate the number of pedestrians on the streets. 
		pedestrianCounting();
		
		//Ensures thet the hashmap with the active vehicles are empty.
		active_RFID_vehicles.clear();
		
		//put the active vehicles data into the active_RFID_vehicles and convert the string value to an integer array. 
		for (Entry<Integer, String> entry : activVeicles.entrySet()){
			active_RFID_vehicles.put(entry.getKey(),string_Converter.stringToIntArray2(entry.getValue()));
		}
		
		//count the number of active emergency vehicles and public transport vehicles.
		if(active_RFID_vehicles.isEmpty() == false) {
			//Number of emergency vehicles and public transport vehicles.
			for (Entry<Integer, int[]> entry : active_RFID_vehicles.entrySet()){
				int[] array = entry.getValue();
				int rfid = array[0]/10000;
				if (rfid== local_streetID) {
					if((entry.getKey()/10000) < 4 && (entry.getKey()/10000)>0) {
						relayValues[2] += 1;
						}
					else if((entry.getKey()/10000)>0){
						relayValues[3] += 1;
						}	
					}
				
				}
			}
	
		relayValues[1] = oldValue[1] + videoFeed - relayValues[2] - relayValues[3];	
		// a if() for ensuring the that the number of vehicles becomes Zero then the videofeed is zero.
		if(videoFeed == 0 || relayValues[1] < 0 ) {
			relayValues[1] = 0;
		}
		// if the camera is broken.
		else if(videoFeed== -1) {
			relayValues[1]  = oldValue[1];
		}
				
		// Prepares the data of the number of participants per category for storage
		String ValueText = relayValues[0]+";"+relayValues[1]+";"+relayValues[2]+";"+relayValues[3];
		MemoryValues.put(local_streetID, ValueText);
		return MemoryValues;		
	} 
	
	/**
	 *  Calculates the number of pedestrians on the street.
	 * @throws IOException
	 */
	public void pedestrianCounting() throws IOException {
		//Adds the old number of pedestrians from 
		relayValues[0]  = oldValue[0];
		
		// check if sensor 1 is working, if so add the number of nr from sensor 1. 
		if(local_streetSensor1[0] != -1) {
			relayValues[0] += (local_streetSensor1[0]-local_streetSensor1[1]);
		}
		// check if sensor 2 is working, if so add the nr from sensor 2.  
		if(local_streetSensor2[0] != -1) {
			relayValues[0] += (local_streetSensor2[0]-local_streetSensor2[1]);
		}	
		// ensures that the number of pedestrians never becomes negative.
		if(relayValues[0] < 0){
			relayValues[0] = 0;
		} 
	}
}
