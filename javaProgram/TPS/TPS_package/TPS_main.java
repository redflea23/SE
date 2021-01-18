package TPS_package;


import java.util.HashMap;
import java.io.IOException; 


// Main class
public class TPS_main {

	//Create objects of the different classes. 
	protected DataGet data_Get = new DataGet();
	
	protected participantCounting participant_Counting = new participantCounting();
	protected Matrixes matrixes = new Matrixes();
	protected rush_hour rush_hour = new rush_hour();
	protected RFID_Evaluation RFID_ToIntArray = new RFID_Evaluation();
	
	
	// All the streetID:s steet[0]=0 beacuse the program starts the count from position 1.
		protected int[] streetID = {0, 12,43,56,78,910,1112,1314,1516,1718,1920,2122,2324,2526,
							   2728,2930,3132,3334,3536,3738,3940,4142,4344,4546,4748,4950,
							   5152,5354,5556,5758,5960,6162,6364,6566,6768,6970,7172,7374,7576,
							   7778,7980,8182,8384,8586,8788,8990,9192,9394,9596};
		int steet_ID;
		// Variables	
	protected HashMap<Integer, Integer> RFID = new HashMap<Integer, Integer>();
	protected HashMap<Integer, Integer> onStreet_AEV = new HashMap<Integer, Integer>();// AEV = ACTIVE EMERGENCY VEHICLES.
	protected HashMap<Integer, Integer> onStreet_PTV = new HashMap<Integer, Integer>();// PTV = PUBLIC TRANSPORT VEHICLES.
		
	protected int NRStreets = 48;
	protected int[] sensorID = new int[2];
	protected HashMap<Integer, Integer>[] activeVehiclesArray;
	protected int[] videoFeedAndSensorData = new int[5];
	/*	videoFeedAndSensorData[0] = videoFeed;
		videoFeedAndSensorData[1] = sensorData[0][0];
		videoFeedAndSensorData[2] = sensorData[0][1];
		videoFeedAndSensorData[3] = sensorData[1][0];
		videoFeedAndSensorData[4] = sensorData[1][1]; 
	 */
	protected int[][] sensorData = new int[2][2];
	protected int videoFeed;
	
	
	public void get_RFIDdara(int x, int y) throws IOException{
		memoryHandling memory_Handling = new memoryHandling();
		// Fetch the new RFIDData for the whole city and store it in the memory.
		RFID.putAll(data_Get.getRFID(x, y));
		memory_Handling.createMemory(RFID.toString(), 0);
		
	}
		
	public boolean get_videoFeedAndsensorData(int ID, int x, int y) throws IOException{
		//Calls on the videFeed 
		videoFeed = data_Get.getVideoFeed(ID, x, y);
		sensorData[0] = data_Get.getNrPedestrians(sensorID[0], x, y);
		sensorData[1] = data_Get.getNrPedestrians(sensorID[1],x,y);
		return true;
	}
	
	public void rush_hour_check(int itteration) throws IOException{
		// decide if it is rush hour or not. HAS NOT IMPLEMENTED FULLY YETT
		rush_hour.isItRushHour(itteration);
	}
	
	public void store_Active_RFID(int x, int y) throws IOException{
		RFID_ToIntArray.sortAndStoreRFID(RFID,sensorID[0]);
	}
	
	public void store_NrParticipants(int x, int y) throws IOException{
		participant_Counting.count(steet_ID, videoFeed, sensorData);
	}
	
	

	// x and y is here for runing the test then runing the program with the other subsystems
	// set x to 1 and y to -1. Is currently used for testing( x=2 means uint test)
	public void main(int x, int y) throws IOException{
		
		
		// loops through all the streets in the city and calulate the number of participants per category.
		for(int ID: streetID) {
			steet_ID = ID;
			// Set the current streetID
			if(ID != 0) {
				sensorID= matrixes.getSensorID(ID);
				
				//only fetches the RFID data one time.
				if(ID == 12) {
					get_RFIDdara(x,y);
				}
				// fetch the videoFeed and the sensor data.
				get_videoFeedAndsensorData(ID, x,y);	
				
				//calculate the number of participants and store and store the active number of participants.
				store_Active_RFID(x,y);
				store_NrParticipants(x,y);	
			}			
		}
		rush_hour_check(1);
	
	}
	
//////////////////////////////////////////////////////////
		
		/**Fetches the sensor values on a street 
		 * @param street: a Array containg the sensors at the street. i.e the position in the array the current street has.
		 * 					Ex: street 12: [0]=1 & [1]=2, 910: [0]=9 & [1]=10 and so on. 
		 * @return
		 */
		public int[] getSensorID(int local_ID){
			int[] sensirIDs = new int[2];	
				if(local_ID < 910) {
					sensirIDs[0] = local_ID/10;
					sensirIDs[1] = local_ID%10;
					//System.out.println("i: "+i+" | SensorID: "+firstSensor);
				}
				else {
					sensirIDs[0] = local_ID/100;
					sensirIDs[1]= local_ID%100;
					//System.out.println("i: "+i+" | SensorID: "+firstSensor);
				}
			return sensirIDs;
		}
		public int GetStreet(int x) {
			return steet_ID;
		}

}

