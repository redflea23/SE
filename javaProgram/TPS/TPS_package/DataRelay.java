package TPS_package;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class DataRelay {
	stringConverter string_Converter = new stringConverter();
	
	/** A hashmap containing the number of participants per category and street. there the street
	 *  Is the key and the value an integer array with all the values.
	 * @return: A hashmap containing the number of participants per category and street.
	 * 		    relayArray[0] = NrPedestrians
	 *  		relayArray[1] = NrRegularVehicles
	 *  		relayArray[2] = NrEmergancyVehicles
	 *  		relayArray[3] = NrPublicTransport
	 */
	public HashMap<Integer, int[]> get_Nrparticipants() throws IOException{
		memoryHandling memory_Handling = new memoryHandling();
		
		HashMap<Integer, int[]> tempHashMap = new HashMap<Integer, int[]>();
		HashMap<Integer, int[]> relayNrParticipants = new HashMap<Integer, int[]>();
		
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
		 System.out.print("		relayNrParticipants:     {");	
		 relayNrParticipants.forEach((k,v) -> System.out.print(" "+k+"="+Arrays.toString(v)));
		 System.out.print("} ");
		 System.out.println(" ");
	
		return relayNrParticipants;
	}
	
	/**Returns a hashmap with all the active veichels.
	 * @return: A hashmap there the key is the vehicle ID:S and the value
	 *          are a integer array with the RFID.
	 * 	         	RFID [0]== complete RFID.	
	 * 	         	RFID [1]== Last detected sensorID.	
	 *  	     	RFID [2]== Activer boolean.
 	 *  	     	RFID [3]== Rout
 	 *  	    If there are no RFID vehicles active does it return null.
	 * @throws IOException
	 */
	public HashMap<Integer, int[]> get_RFID() throws IOException{
		memoryHandling memory_Handling = new memoryHandling();
		HashMap<Integer, int[]> RelayRFID = new HashMap<Integer, int[]>(); 
		String t = memory_Handling.readMemory(2);
		HashMap<Integer, int[]> tt = string_Converter.StringToIntArrayHashMap(t); 
		RelayRFID.putAll(tt);
		
		// if No RFID vehicles was active.
		if(RelayRFID.containsKey(-10000)) {
			return null;
		}
		System.out.print("		RelayRFID:     {");	
		 RelayRFID.forEach((k,v) -> System.out.print(" "+k+"="+Arrays.toString(v)));
		 System.out.print("} ");
		 System.out.println(" ");
		 
		
		return RelayRFID;
		
		
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
