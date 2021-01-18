package TPS_package;

import java.util.HashMap;
import java.util.Map.Entry;
import java.io.IOException;

public class participantCounting {
	
	protected stringConverter string_Converter = new stringConverter();

	
	protected int local_streetID;
	// stores the previous values. 
	protected int[] oldValue = {0,0,0,0};
	
	// stores the last number of each pedestrian type.
	protected int[] relayValues = {0, 0, 0, 0};
	protected int[] local_streetSensor1;
	protected int[] local_streetSensor2;
	
	protected HashMap<Integer, String> MemoryValues = new HashMap<Integer, String>();
	protected HashMap<Integer, int[]> oldValues = new HashMap<Integer, int[]>();
	protected HashMap<Integer, int[]> active_RFID_vehicles = new HashMap<Integer, int[]>();
	
	/**
	 * Counts the number of participants per category on the street.
	 * @param street_ID: the number of the street
	 * @param videoFeed: the number of regular vehicles.
	 * @param oldValues: the previous calculated numbers from the last iteration. 
	 * @param emergencyVehicles: A hasmap containing the emergency vehicles on the street.
	 * @param publicTransport: A hasmap containing the public transport vehicles on the street.
	 * @param streetSensor1: data from sensor1 in form of a hashmap.
	 * @param streetSensor2: data from sensor2 in form of a hashmap.
	 * 											key = the number that has entered the street at the sensor
	 * 											value = the number that has left the street at the sensor.
	 * @return a integer list with the values:
	 *		returnList[0] = NrPedestrians
	 *  	returnList[1] = NrRegularVehicles
	 *  	returnList[2] = NrEmergancyVehicles
	 *  	returnList[3] = NrPublicTransport 
	 * @throws IOException
	 */
	public void count(int streetID, int videoFeed, int[][] streetSensorData) throws IOException {
		memoryHandling memory_Handling = new memoryHandling();
		
		local_streetID = streetID;
		local_streetSensor1 = streetSensorData[0]; 
		local_streetSensor2 = streetSensorData[1]; 
		
		// Ensures that the relayValues is cleared.
		relayValues[0] = 0;
		relayValues[1] = 0;
		relayValues[2] = 0;
		relayValues[3] = 0;
		oldValue[0] = 0;
		oldValue[1] = 0;
		oldValue[2] = 0;
		oldValue[3] = 0;
		
		// Get the old participant values. and the active RFID veichles on the street.

		oldValues.putAll(string_Converter.StringToIntArrayHashMap(memory_Handling.readMemory(1)));	
		active_RFID_vehicles.putAll(string_Converter.StringToIntArrayHashMap(memory_Handling.readMemory(2)));
		
		// if there exists old values add them to oldValue
		// else: just ceep oldValues as {0, 0, 0, 0}; 
		if(oldValues.get(local_streetID) != null) {
			oldValue = oldValues.get(local_streetID);
		}				
		
		//calculate the number of pedestrians on the streets
		pedestrianCounting();
		//Number of emergency vehicles and public transport vehicles.
		for (Entry<Integer, int[]> entry : active_RFID_vehicles.entrySet()){
			if(entry.getKey()==1920 || entry.getKey()==8384) {
				int p = 0;
			}
			if((entry.getKey()/10000) < 4 && (entry.getKey()/10000)>0) {relayValues[2] += 1;}
			else if((entry.getKey()/10000)>0) 						   {relayValues[3] += 1;}
		}
		relayValues[1] = oldValue[1] + (videoFeed - relayValues[2] - relayValues[3]);	
		
			// Prepares the data of the number of participants per category for storage
			String ValueText = relayValues[0]+";"+relayValues[1]+";"+relayValues[2]+";"+relayValues[3];
			MemoryValues.put(local_streetID, ValueText);
					
		//Stores data in memory.
		memory_Handling.createMemory(MemoryValues.toString(), 1);
		
	} 
	
	/**
	 * Calculates the number of pedestrians on the street.
	 * @param street_nr: the number of the street
	 * @param oldValues: the previous calculated numbers from the last iteration. 
	 * @param streetSensor1: data from sensor1 in form of a hashmap.
	 * @param streetSensor2: data from sensor2 in form of a hashmap.
	 * 											key = the number that has entered the street at the sensor
	 * 											value = the number that has left the street at the sensor.
	 * @return: the number of pedestrians on the street.
	 * @throws IOException
	 */
	public void pedestrianCounting() throws IOException {
		//Adds the old number of pedestrians from 
		relayValues[0]  = oldValue[0];
		
		
		// check if sensor 1 is working, if so add the nr from sensor 1. 
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
