package TPS_package;


import java.util.HashMap;
import java.util.Map.Entry;
import java.io.IOException; 


// Main class
public class TPS_main {

	//Create objects of the different classes. 
	static DataGet data_Get = new DataGet();
	static participantCounting participant_Counting = new participantCounting();
	static CityMatrix matrixes = new CityMatrix();
	static rush_hour rush_hour = new rush_hour();
	static RFID_Evaluation RFID_ToIntArray = new RFID_Evaluation();
	static DataRelay data_Relay = new DataRelay();
	
	// All the streetID:s steet[0]=0 beacuse the program starts the count from position 1.
		static int[] streetID = {12,43,56,78,910,1112,1314,1516,1718,1920,2122,2324,2526,
							   2728,2930,3132,3334,3536,3738,3940,4142,4344,4546,4748,4950,
							   5152,5354,5556,5758,5960,6162,6364,6566,6768,6970,7172,7374,7576,
							   7778,7980,8182,8384,8586,8788,8990,9192,9394,9596};
		
	
	static int NRStreets = 48;

	
	
	
	 public static void main(String args[]) throws IOException { 
		 
		 // The program will continue to fetch data and store it with with regular intervals
		 while(true){
			 try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 TPS_main(1,-1);
		 }
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	// x and y is here for runing the test then runing the program with the other subsystems
	// set x to 1 and y to -1. Is currently used for testing( x=2 means uint test)
	public static void TPS_main(int x, int y) throws IOException{
		// Variables
		
		
		memoryHandling memory_Handling = new memoryHandling();
		HashMap<Integer, Integer> RFID = new HashMap<Integer, Integer>();	
		HashMap<Integer, String> relayRFID = new HashMap<Integer, String> ();	
		HashMap<Integer, String> relayValues = new HashMap<Integer, String> ();
		
		int videoFeed;
		int[] sensorID = new int[2];
		int[][] sensorData = new int[2][2];
		

		// loops through all the streets in the city and calulate the number of participants per category.
		for(int ID: streetID) {
			
			sensorID= matrixes.getSensorID(ID);
				
			//Fetches the RFID-data for the whole city and store it in the memory one time.
			if(ID == 12) {
				RFID.putAll(data_Get.getRFID(x, y));
				memory_Handling.createMemory(RFID.toString(), 0);	
			}
			
			// fetch the videoFeed and the sensor data.
			videoFeed = data_Get.getVideoFeed(ID, x, y);
			sensorData[0] = data_Get.getNrPedestrians(sensorID[0], x, y);
			sensorData[1] = data_Get.getNrPedestrians(sensorID[1],x,y);	
				
			//calculate the number of participants and the active number of participants.
			HashMap<Integer, String> activVeicles = RFID_ToIntArray.sortAndStoreRFID(RFID,ID);
			HashMap<Integer, String> particpantsvalues = participant_Counting.count(ID, videoFeed, sensorData, activVeicles);
			if(activVeicles.isEmpty()==true) {
					
			}
			for (Entry<Integer, String> entry : activVeicles.entrySet()){
				relayRFID.put(entry.getKey(), entry.getValue());
			}
				
			for (Entry<Integer, String> entry : particpantsvalues.entrySet()){
				relayValues.put(entry.getKey(), entry.getValue());
			}
			
			
		}			
		
		// store data in memory
		memory_Handling.createMemory(relayRFID.toString(), 2);
		memory_Handling.createMemory(relayValues.toString(), 1);	
		
		// decide if it is rush hour or not. HAS NOT IMPLEMENTED FULLY YETT
		rush_hour.isItRushHour(1);
	
		//only here for tests
		//HashMap<Integer, int[]> t = data_Relay.get_RFID();
		//HashMap<Integer, int[]> t2 = data_Relay.get_Nrparticipants();
		
	}
	
//////////////////////////////////////////////////////////
		
}

