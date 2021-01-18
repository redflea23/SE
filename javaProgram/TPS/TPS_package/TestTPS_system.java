package TPS_package;



import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class TestTPS_system {
	
	TPS_main TPS_Main = new TPS_main();
	DataRelay data_Relay = new DataRelay();
	stringConverter string_Converter = new stringConverter();

	Random r = new Random();

	HashMap<Integer, Integer> DataGetRFID_Test = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> DataGetRFID_TestCompare = new HashMap<Integer, Integer>();
	
	HashMap<Integer, int[]> DataRelayRFID_Test = new HashMap<Integer, int[]>();
	HashMap<Integer, int[]> DataRelayValues_Test = new HashMap<Integer, int[]>();
	HashMap<Integer, Integer> DataRelayRFID_TestCompare = new HashMap<Integer, Integer>();

	
	@Test
	void DataGet_test() throws IOException {
		TPS_Main.main(2, 2);
		memoryHandling memory_Handling = new memoryHandling();
		// test getRFID
		TPS_Main.get_RFIDdara(2, 1);
		DataGetRFID_TestCompare.putAll(TempGetRFIDData(1));
		
		DataGetRFID_Test.putAll(string_Converter.StringToIntHashMap(memory_Handling.readMemory(0))); 
		for (Entry<Integer, Integer> entry : DataGetRFID_Test.entrySet()){
			assertEquals(DataGetRFID_TestCompare.get(entry.getKey()),entry.getValue());
		}
		
		// test getVideoFeed and sensor data.
		assertTrue(TPS_Main.get_videoFeedAndsensorData(12,2,1));
		
		}
	
	
	@Test
	void Datarelay_test() throws IOException {
		TPS_Main.main(2, 2);
		
			TPS_Main.main(2, 1);
		DataRelayValues_Test = data_Relay.get_Nrparticipants(); 
			/*for (Entry<Integer, int[]> entry : DataRelayValues_Test.entrySet()){
				int[] k = entry.getValue();
				assertEquals(200,k[0]);
				assertEquals(93,k[1]);
				assertEquals(4,k[2]);
				assertEquals(33,k[3]);	
			}*/
			DataRelayRFID_Test = data_Relay.get_RFID();
			/*DataRelayRFID_TestCompare.putAll(TempGetRFIDData(1));
			for (Entry<Integer, int[]> entry : DataRelayRFID_Test.entrySet()){
				int[] k = entry.getValue();
				int  Compare = DataRelayRFID_TestCompare.get(entry.getKey()), RFID = k[0];
				assertEquals(Compare,RFID);
			}	*/
	}
	
	
//-------------PRIVATE FUNCTIONS---------------------------------------
	public HashMap<Integer, Integer> TempGetRFIDData(int y) throws IOException{
		//Code to test the get RFID functions of the program.
		HashMap<Integer, Integer> testHashMap = new HashMap<Integer, Integer>();
		String test = null;
		int[] cases = {1,2,3,4};
		
		if( y==-1) {
			y = 1;
			//y = cases[r.nextInt(4)];
		}
		switch(y) {
		case 1:
			test = "{10001=121000, 20002=9101000, 30001=21220000, 40001=910001, 50001=11120191, 60001=120111}";
			break;
		// test for the all the vehicles are inactive.
		case 2:
			test = "{10001=120000,10002=49500000, 20001=11120000, 20002=9100000, 30001=21220000, 40001=910000, 50001=11120000, 60001=120000}";
			break;
		case 3:
			test = "{10001=121000,10002=49501000, 20001=11121000, 20002=9101000, 30001=21220000, 40001=910001, 50001=11120191, 60001=120111}";
			break;
		case 4:
			test = "{10001=121000,10002=49501000, 20001=11121000, 20002=9101000, 30001=21220000, 40001=910001, 50001=11120191, 60001=120111}";
			break;
		}
		
		testHashMap = string_Converter.StringToIntHashMap(test);
		return testHashMap;	
	}
	
	public int TempVideoFeed(int y) throws IOException {
		//Initiating the variables
		int test = 0;

		int[] cases = {1,2,3,4};
		
		if( y==-1) {
			y = 1;
			//y = cases[r.nextInt(4)];
		}
		
		switch(y) {
		case 1:
			test = 100;
			break;
		// test for the all the vehicles are inactive.
		case 2:
			test = 0;
			break;
		case 3:
			test = 20;
			break;
		//Broken camera test.
		case 4:
			test = -1;
			break;
		}	
				
		return test;
	}
	

	public int[] TempGetPedestrians(int y) throws IOException {
		//Initiating the variables
		int[] test = {0,0};

		int[] cases = {1,2,3,4};
				
		if( y==-1) {
			y = 1;
		//y = cases[r.nextInt(4)];
				}
		switch(y) {
			case 1:
				test[0] = 120;
				test[1] = 20;
				break;
			case 2:
				test[0] = 0;
				test[1] = 0;
				break;
			case 3:
				test[0] = 25;
				test[1] = 20;
				break;
			//Broken SENSOR test.
			case 4:
				test[0] = -1;
				test[1] = -1;
				break;
		}	
						
				return test;
	}
}
