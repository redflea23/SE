package TPS_package;



import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class TestTPS_system {
	
	TPS_main TPS_Main = new TPS_main();
	
	stringConverter string_Converter = new stringConverter();
	
	/*@Test
	void miona() throws IOException {
		//memoryHandling memory_Handling = new memoryHandling();
//		memory_Handling.Clear_Memory();
		TPS_Main.main(2, 2);
		DataRelay fetch = new DataRelay();
	    HashMap <Integer, int[]> RFID =  fetch.get_RFID(); 
int i = 0;
	}*/
	

	
	@Test
	void DataGet_test() throws IOException {
		DataGet Data_Get = new DataGet();
		memoryHandling memory_Handling = new memoryHandling();
		memory_Handling.Clear_Memory();
			
		for(int i = 1; i<=3;i++) {
			//test RFID
			HashMap<Integer, Integer> DataGetRFID_Test = Data_Get.getRFID(2, i);
				
			HashMap<Integer, Integer> DataGetRFID_TestCompare = TempGetRFIDData(i); 
		
			for (Entry<Integer, Integer> entry : DataGetRFID_Test.entrySet()){
				assertEquals(DataGetRFID_TestCompare.get(entry.getKey()),entry.getValue());
			}
			
			//test sensor
			int[] dataGetSensor_Test = Data_Get.getNrPedestrians(12, 2, i);
			int[]  dataGetSensor_TestCompare = TempGetPedestrians(i);
			
			for(int j=0; j<2;j++) {
				assertEquals(dataGetSensor_Test[j],dataGetSensor_TestCompare[j]);
			}
			// test video feed.
			int dataGetVideoFeed_Test = Data_Get.getVideoFeed(12, 2, i);
			int dataGetVideoFeed_TestCompare = TempVideoFeed(i); 
			assertEquals(dataGetVideoFeed_Test,dataGetVideoFeed_TestCompare);		
		}
		
	}
	
	
	@Test
	void Datarelay_test() throws IOException {
		DataRelay data_Relay = new DataRelay();
		memoryHandling memory_Handling = new memoryHandling();
		
		memory_Handling.Clear_Memory();
		
		HashMap<Integer, int[]> DataRelayRFID_Test = new HashMap<Integer, int[]>();
		HashMap<Integer, Integer> DataRelayRFID_TestCompare = new HashMap<Integer, Integer>();
		HashMap<Integer, int[]> DataRelayValues_Test = new HashMap<Integer, int[]>();
		HashMap<Integer, int[]> DataRelayValues_TestCompare = new HashMap<Integer, int[]>();
		int videoFeedT=0;
		int[] returnedRFID;
		int[] sensor1T= {0,0},sensor2T= {0,0};
		
		// clear out the memory.
		
	
		
		for(int i=1 ;i<=3; i++) {
			System.out.println("I: "+i);
			TPS_Main.TPS_main(2, i);
			
			//test the returned RFID values
			DataRelayRFID_Test = data_Relay.get_RFID();
			DataRelayRFID_TestCompare.putAll(TempGetRFIDData(i));
			if(DataRelayRFID_Test != null) {
				for (Entry<Integer, int[]> entry : DataRelayRFID_Test.entrySet()){
					returnedRFID = entry.getValue();
					int t = DataRelayRFID_TestCompare.get(entry.getKey());
					int t2 = returnedRFID[0];
					assertEquals(t,t2);
				}
			//test 
			}
			
			DataRelayValues_Test = data_Relay.get_Nrparticipants();
		}
		
	}

	
//-------------PRIVATE FUNCTIONS---------------------------------------
	
	public HashMap<Integer, Integer> TempGetRFIDData(int y) throws IOException{
		//Code to test the get RFID functions of the program.
		HashMap<Integer, Integer> testHashMap = new HashMap<Integer, Integer>();
		String test = null;
		int[] cases = {1,2,3,4};
		
		if( y==-1) {
			y = 1;
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
			test = "{10001=121000, 60001=120111}";
			break;
		}
		
		testHashMap = string_Converter.StringToIntHashMap(test);
		return testHashMap;	
	}
	
	public int TempVideoFeed(int y) throws IOException {
		//Initiating the variables
		int test = 0;
		
		if( y==-1) {
			y = 1;
		}
		
		switch(y) {
		case 1:
			test = 100;
			break;
		// test for then the there are no vehicles on the street.
		case 2:
			test = 0;
			break;
		//Broken camera test.
		case 3:
			test = -1;
			break;
		}	
				
		return test;
	}
	

	public int[] TempGetPedestrians(int y) throws IOException {
		//Initiating the variables
		int[] test = {0,0};
		
		if( y==-1) {
			y = 1;
			}
		switch(y) {
			case 1:
				test[0] = 25;
				test[1] = 20;
				break;
			case 2:
				test[0] = 0;
				test[1] = 5;
				break;
			//Broken SENSOR test.
			case 3:
				test[0] = -1;
				test[1] = -1;
				break;
		}	
						
				return test;
	}
}
