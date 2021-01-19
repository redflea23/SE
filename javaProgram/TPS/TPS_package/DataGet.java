package TPS_package;

import java.io.IOException;
import java.util.HashMap;

public class DataGet {
	
	/**
	 * ** Fetches the RFID for the.
	 * @param x & y. Variables that used to determine if the program is running normally or if it`s being tested.
	 * @return A Hashmap containg all the public transports and emergency vehicles RFID data. 
	 * @throws IOException
	 */
	public HashMap<Integer, Integer> getRFID(int x, int y) throws IOException{
		TestTPS_system TestTPS_system = new TestTPS_system();
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		
		switch(x) {
		//Then running the program normally.
		case 1:
			 //hashMap.putAll(NodeManager.getRFIDData());
			break;
		//for testing the program.
		case 2:
			hashMap.putAll(TestTPS_system.TempGetRFIDData(y));
			break;
		}
		
		//stores the values.
		
		return hashMap;
	}
	
	/**
	 * Fetches the integer representation of the video feed.
	 * @param streetID: the streetID of the street that the videoFeed will be fetched from.
	 * @param x & y. Variables that used to determine if the program is running normally or if it`s being tested.
	 * @return A int variable that represents the video feed.
	 * @throws IOException
	 */
	public int getVideoFeed(int streetID, int x, int y) throws IOException{
		TestTPS_system TestTPS_system = new TestTPS_system();
		int videoFeed = 0;
		
		switch(x) {
		//Then running the program normally.
		case 1:
			 //hashMap.putAll(NodeManager.getRFIDData());
			break;
		//for testing the program.
		case 2:
			videoFeed = TestTPS_system.TempVideoFeed(y);
			break;
		}
		return videoFeed;
	}
	
	/**
	 *  Fetches the number of pedestrians of pedestrians form the street
	 * @param sensorID: the ID of the sensor that will be called uppon
	 * @param x & y. Variables that used to determine if the program is running normally or if it`s being tested.
	 * @return An array with two values. [0] = nr pedestrians that has enter the street, [1] = nr pedestrians that has left the street. 
	 * @throws IOException
	 */
	public int[] getNrPedestrians(int sensorID, int x, int y) throws IOException{
		TestTPS_system TestTPS_system = new TestTPS_system();
		int[] streetSensor = {0,0};
	
		switch(x) {
		//for running the program normally.
		case 1:
			// int[] nrPedesrians = NodeManager.getPedestrianData(sensorID);
			break;
		//for testing the program.
		case 2:
			streetSensor = TestTPS_system.TempGetPedestrians(y);
			break;
		} 

		return streetSensor;
		
	}
					
					
}
