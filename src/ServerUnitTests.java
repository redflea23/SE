import static org.junit.Assert.*;

import org.junit.Test;


public class ServerUnitTests {

	@Test
	public void TestSpeedLimit() {
		NodeManager manager = new NodeManager(5600, 5601);
		manager.initRFIDListener();
		manager.setSpeedLimit(12, 67);
		int newLimit = manager.getSpeedLimit(12);
		assertEquals(67, newLimit);
		manager.shutDownServers();
	}
	
}
