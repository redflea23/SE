package TPS_package;

import java.io.IOException;
import java.util.HashMap;

public class DataGet {
	
	/** Fetches the RFID for the.
	 * @return hashMap: A Hashmap of all the RFID.
	 * @throws IOException
	 */
	public HashMap<Integer, Integer> getRFID(int x, int y) throws IOException{
		TestTPS_system TestTPS_system = new TestTPS_system();
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		
		switch(x) {
		//Then running the program normally.
		case 1:
			 //hashMap.putAll(NodeManager.getRFIDData());
			hashMap.putAll(TestTPS_system.TempGetRFIDData(-1));//IS ONLY HERE FOR NOW
			break;
		//for testing the program.
		case 2:
			hashMap.putAll(TestTPS_system.TempGetRFIDData(y));
			break;
		}
			
		return hashMap;
	}
	
	/** Fetches the integer representation of the video feed.
	 * @param streetID: the current streetID 
	 * @return streetSensor: the integer representation 
	 * @throws IOException
	 */
	public int getVideoFeed(int streetID, int x, int y) throws IOException{
		TestTPS_system TestTPS_system = new TestTPS_system();
		int videoFeed = 0;
		
		switch(x) {
		//Then running the program normally.
		case 1:
			 //hashMap.putAll(NodeManager.getRFIDData());
			 videoFeed = TestTPS_system.TempVideoFeed(-1);	
			break;
		//for testing the program.
		case 2:
			videoFeed = TestTPS_system.TempVideoFeed(y);
			break;
		}
		return videoFeed;
	}
	
	/** Fetches the number of pedestrians of pedestrians form the street
	 * @param streetID: the current street.
	 * @return : A hasmap with one stored key and value. 
	 * 			 There the key is the number of pedestrians entering the street at
	 * 			 that sensor and the value is the number of people leaving. 
	 * @throws IOException
	 */
	public int[] getNrPedestrians(int sensorID, int x, int y) throws IOException{
		TestTPS_system TestTPS_system = new TestTPS_system();
		int[] streetSensor = {0,0};
	
		switch(x) {
		//Then running the program normally.
		case 1:
			// int[] nrPedesrians = NodeManager.getPedestrianData(sensorID);
			streetSensor = TestTPS_system.TempGetPedestrians(-1);	
			break;
		//for testing the program.
		case 2:
			streetSensor = TestTPS_system.TempGetPedestrians(y);
			break;
		} 

		return streetSensor;
		
	}
					
					
}
