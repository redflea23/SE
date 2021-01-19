package TPS_package;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class DataRelay {
	stringConverter string_Converter = new stringConverter();
	
	/**
	 * fetches the active vehicles RFID data from the memory.
	 * @return A Hashmap there the vehicle IDs are the keys. and 
	 * the values are integers arrays where 
	 * 										RFID [0]== complete RFID.	
	 * 	         							RFID [1]== Last detected sensorID.	
	 *  	     							RFID [2]== Activer boolean.
 	 *  	     							RFID [3]== Rout
	 * @throws IOException
	 */
	public HashMap<Integer, int[]> get_RFID() throws IOException{
		memoryHandling memory_Handling = new memoryHandling();
		
		//creates the Relay hashmap and fetches the data from the memory.
		HashMap<Integer, int[]> RelayRFID = new HashMap<Integer, int[]>(); 
		RelayRFID.putAll(string_Converter.StringToIntArrayHashMap(memory_Handling.readMemory(2)));
		
		 // if No RFID vehicles was active. return .
		if(RelayRFID.containsKey(-10000)) {
			return null;
		}
		/*
		 * System.out.print("		RelayRFID:     {");	
		 RelayRFID.forEach((k,v) -> System.out.print(" "+k+"="+Arrays.toString(v)));
		 System.out.print("} ");
		 System.out.println(" ");
		 */
		return RelayRFID;
	
	}
	
	
	/** 
	 * fetches the number of particpants per category per street and relays them
	 *  Is the key and the value an integer array with all the values.
	 * @return: A hashmap ther the streetID:s are the keys and the values are integer arrays that consists of:
	 * 		    relayArray[0] = NrPedestrians
	 *  		relayArray[1] = NrRegularVehicles
	 *  		relayArray[2] = NrEmergancyVehicles
	 *  		relayArray[3] = NrPublicTransport
	 */
	public HashMap<Integer, int[]> get_Nrparticipants() throws IOException{
		memoryHandling memory_Handling = new memoryHandling();
		
		HashMap<Integer, int[]> tempHashMap = new HashMap<Integer, int[]>();
		HashMap<Integer, int[]> relayNrParticipants = new HashMap<Integer, int[]>();
		
		//fetch the data and relay it.
		tempHashMap.putAll(string_Converter.StringToIntArrayHashMap(memory_Handling.readMemory(1)));
		
		for (Entry<Integer, int[]> entry : tempHashMap.entrySet()){
			int[] temp = entry.getValue();
			int[] relayArray = {-1,-1,-1,-1};
			relayArray[0] = temp[0];
			relayArray[1] = temp[1];
			relayArray[2] = temp[2];
			relayArray[3] = temp[3];
			relayNrParticipants.put(entry.getKey(), relayArray);
			
		}
		
		/*System.out.print("		relayNrParticipants:     {");	
		 relayNrParticipants.forEach((k,v) -> System.out.print(" "+k+"="+Arrays.toString(v)));
		 System.out.print("} ");
		 System.out.println(" ");
		 System.out.println(" ");
		 * 
		 */
	
		return relayNrParticipants;
	}
	
	
	/** Tells if it is rush hour or not.
	 * @return: a boolean variable that is true if it is rush hour false otherwise.
	 * @throws IOException
	 */
	public boolean get_roushHour() throws IOException{
		memoryHandling memory_Handling = new memoryHandling();
		String trueFalse = memory_Handling.readMemory(3);
		switch(trueFalse) {
		case "true":
			System.out.println("		Rushour: true");
			return true;
		case "false":
			System.out.println("		Rushour: false");
			return false;
		}
		return false;
	}

}
