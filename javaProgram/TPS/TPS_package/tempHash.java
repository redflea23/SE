package TPS_package;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

	//Ignore this, its only for debugging.
public class tempHash {
	//initiate the random number generator r.
	Random r = new Random();
	stringConverter strConv = new stringConverter();
	//
	
	
	
	//FLYTTA DOM HÄR SAKERNA TILL TESTTOS_system.java!!!!!!!!!!!!!!!!!!!
	
	
	////////////////////
	public int TempVideoFeed(int streetID) throws IOException {
		//Initiating the variables
		int RegularVehicles = 0;
	
		int ex1, ex2, ex3, ex4;
		ex1 = 100;
		ex2 = 200;
		ex3 = 80;
		ex4 = -1;
		int[] temp = {ex1, ex2, ex3, ex4};
		RegularVehicles = temp[r.nextInt(4)];
		
		return RegularVehicles;
	}
	
	public int[] TempGetPedestrians() throws IOException {
		
		//Initiating the variables
		int[] test1_1= {120,20}, test1_2={25,20}, test1_3={15,10}, test1_4={-1,-1};
		int[][] temp = {test1_1, test1_2, test1_3, test1_4};
		//String[] temp = {test1_1, test1_4};
		int[] TempGetPedestrians =temp[r.nextInt(4)];
		return TempGetPedestrians;
	}
	
	//Generate a random number of RFID per street. 
	public HashMap<Integer, Integer> TempGetRFIDData() throws IOException{
		//Code to test the RFID functions of the program.
		 
		HashMap<Integer, Integer> RFID = new HashMap<Integer, Integer>();
		
		String test1_1 = "{10001=121000,10002=49501000, 20001=11121000, 20002=9101000, 30001=21220000, 40001=910001, 50001=11120191, 60001=120111}";


		String test1_2 = "{10001=120000,10002=49500000, 20001=120000, 20002=9100000, 30001=21220000, 40001=910000, 50001=11120000, 60001=120000}";
		String test1_3 = "{10001=561000,10002=25261000, 20001=69701000, 20002=23241000, 30001=93940000, 40001=77780001, 50001=53540191, 60001=73740000}";
		String[] testArray =  {test1_1, test1_2,test1_3}; 
		
		//String test = testArray[r.nextInt(3)];
		String test = test1_1;
		RFID = strConv.StringToIntHashMap(test);
		return RFID;
	}
	
	
	///OLD CODE////////////////////////////////////////////////////////////////////

	/*public int RandomID(int key, int firstStreet, int lastStreet) throws IOException {
		//initiate the variables. 
		int returnValue, X;
		String valueS = "";
		int[] StreetsAndSensor = new int[]{101, 102, 202, 203, 301, 303, 402, 404};
		
		// Street and sensorID
		X = StreetsAndSensor[r.nextInt(StreetsAndSensor.length)];
		valueS += Integer.toString(X);
			
		// Active
		if ( 10000 <= key && key < 40000) {
			valueS += Integer.toString(r.nextInt(2));	
		}
		else {
			valueS += "0";
		}
				
		//Route
		if ( 40000 <= key) {
			valueS += Integer.toString(r.nextInt(9));
			valueS += Integer.toString(r.nextInt(9));
			valueS += Integer.toString(r.nextInt(9));
		}
		else {
			valueS += "000";
		}
		 
		returnValue = Integer.parseInt(valueS);
		return returnValue;
	} */
	/*public int TempVideoFeed(int streetID) throws IOException {
		//Initiating the variables
		int RegularVehicles = 0;
		
		RegularVehicles = 30 + r.nextInt(200 - 30 + 1);
		
		/*For simulationg broken sensors
		if( r.nextInt(4) > 1) {
			if( r.nextInt(4) > 1) {
			//Randomize the number
			RegularVehicles = 30 + r.nextInt(200 - 30 + 1);
		}
		//simulated error
		else {
			RegularVehicles = -1;
		}
		
		return RegularVehicles;
	}*/
	
	/*public HashMap<Integer, Integer> TempGetPedestrians(int streetID) throws IOException {
		
		//Initiating the variables
		HashMap<Integer, Integer> TempGetPedestrians = new HashMap<>();
		int leaving, entering;
		
		/* simulationg broken sensors
		if( r.nextInt(11) > 4) {
			//Randomize the number
			entering = r.nextInt(201);
			leaving = r.nextInt(201);
		}
		//simulated error
		else {
			entering = -1;
			leaving = -1;
		}
	
		entering = r.nextInt(201);
		leaving = r.nextInt(201);
		TempGetPedestrians.put(entering, leaving);
		return TempGetPedestrians;
	}*/
	
	/**
	 * //initiate and create variables that will be used.
	HashMap<Integer, Integer> TempGetRFIDDataMyHashMap = new HashMap<>();
	int key, value, first, last;
	int [] TotalNrRFIDVehicles = new int[6];
	
	//set values for all vehicle types
	TotalNrRFIDVehicles[0] = NrPoliceVehicles;
	TotalNrRFIDVehicles[1] = NrFireTruck;
	TotalNrRFIDVehicles[2] = NrAmbulances;
	TotalNrRFIDVehicles[3] = NrAmbulances;
	TotalNrRFIDVehicles[4] = NrAmbulances;
	TotalNrRFIDVehicles[5] = NrAmbulances;
	
	// Initiate the start value  
	first = 1;
	
	//loop through all the vehicle types. 
	for(int i = 0; i < 6; ++i) {
		first += 10000;
		last = first;
		last += TotalNrRFIDVehicles[i];
		
		// Randomize the street for all the cars.
		for(int j = first; j < last; ++j) {
			key = j;
			value = RandomID(key, firstStreet, lastStreet );
			TempGetRFIDDataMyHashMap.put(key, value);
		}
	}
	return TempGetRFIDDataMyHashMap;*/

	
}
