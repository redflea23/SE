import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ServerUnitTests {
	NodeManager manager;
	
	@Before
	public void startServers() {
		manager = new NodeManager(5600, 5601, 5602, 5603);
		manager.initRFIDListener();
	}
	
	@After
	public void tearDown() {
		manager.shutDownServers();
	}

	@Test
	public void TestSpeedLimit() {
		
		int oldLimit = manager.getSpeedLimit(12);
		assertEquals(50, oldLimit);
		manager.setSpeedLimit(12, 67);
		int newLimit = manager.getSpeedLimit(12);
		assertEquals(67, newLimit);		
	}
	
	@Test
	public void TestTrafficLights() {
		
		int currentPattern = manager.getTrafficLight(6);
		assertEquals(1, currentPattern);
		manager.setTrafficLight(6, 3);
		int newPattern = manager.getTrafficLight(6);
		assertEquals(3, newPattern);		
		
	}
	
	@Test
	public void TestPedestrianSensor() {
		int[] firstReading = manager.getPedestrianData(12);
		int[] secondReading = manager.getPedestrianData(12);
		assertEquals(firstReading[0], secondReading[0] - 1);
		assertEquals(firstReading[1], secondReading[1] - 1);
	}
	
	@Test
	public void TestRFIDSensor() {
		try {
			Thread.sleep(2000);
			HashMap<Integer, Integer> map = manager.getRFIDData();
			assertTrue(map.containsKey(10001));
			
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
}
