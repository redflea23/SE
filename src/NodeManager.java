import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class NodeManager {

	private int speedLimitPort;
	private int rfidPort;
	private Hashtable<Integer, Integer> rfid;
	private ArrayList<Process> procList;

	public NodeManager(int speedLimitPort, int rfidPort) {
		this.procList = new ArrayList<>();
		this.speedLimitPort = speedLimitPort;
		this.rfidPort = rfidPort;
		rfid = new Hashtable<>();
		initServers();
	}
	
	/* 
	 * Initiates the servers. The python files should be in the root of the java project.
	 */
	public void initServers() {
		
		ProcessBuilder rfid = new ProcessBuilder("python", getCurrentPath() + "\\RFIDSensor.py");
		ProcessBuilder speedLimit = new ProcessBuilder("python", getCurrentPath() + "\\SpeedLimit.py");
		
		try {
			procList.add(rfid.start());
			procList.add(speedLimit.start());
		} catch (IOException e) {
			System.out.println("Could not start python servers, please check the location of the .py files. Root of "
					+" the java procjet (next to src)");
			
			e.printStackTrace();
		}
	}
	/*
	 * This method shuts down the python servers. This is done to make testing easier 
	 * and more consistent. It should be called after a test is performed.
	 * [See ServerUnitTest.java for an example]
	 */
	public void shutDownServers() {
		procList.forEach(p -> p.destroy());
	}
	
	private String getCurrentPath() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
		
	}
	
	/*
	 * Initializes a Thread and updates the rfid HashTable when new data 
	 * is coming in. This has to be started at the beginning of every test session.
	 * [See ServerUnitTest.java for an example]
	 */
	public void initRFIDListener()  {

		new Thread(() -> {
			Socket socket;			
			BufferedReader in;
			try {
				socket = new Socket("127.0.0.1", rfidPort);				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
				
				boolean exit = false;
				while(!exit) {
					try {
						String s = in.readLine();
						
						String key = s.substring(0, 5);
						String value = s.substring(5);
						int k = Integer.parseInt(key);
						int v = Integer.parseInt(value);
						if (rfid.contains(key)) {
							rfid.replace(k, v);
						} else {
							rfid.put(k, v);
						}

					} catch (IOException e1) {
						System.out.println("RFIDServer down");
						exit = true;

					}
				}
			} catch (UnknownHostException e) {				
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			};
			
		}).start();

	}
	/*
	 * Returns the last known information an all RFID-Equipped vehicles.
	 * Returned data is structured as follows:
	 *  |Vehicle Class | Vehicle ID |   as keys. With lengths:   |X|XXXX|
	 *  |Sensor ID | Active | Route|    as values. With lengths: |XXXXX|X|XXX|
	 */
	public HashMap<Integer, Integer> getRFIDData() {
		HashMap<Integer, Integer> retMap = new HashMap<>();
		rfid.forEach((k, v) -> retMap.put(k, v));
		return retMap;
	}	
	
	/*
	 * Returns the current speed limit set at sensor id.
	 * [see cityGraph.jpg for more information.]
	 */
	public int getSpeedLimit(int id) {
		Connection conn = new Connection(speedLimitPort, "127.0.0.1");
		try {
			conn.connectSocket();
			conn.connectIN();
			conn.connectOUT();
		} catch (UnknownHostException e1) {
			System.out.println("Could not connect to host");
			e1.printStackTrace();
			return -1;
		} catch (IOException e1) {			
			e1.printStackTrace();
			return -1;
		}
		
		String limit = conn.transfer("0" + id, (msg) -> {
			conn.out.write(msg);
			conn.out.flush();
			String s;
			try {
				s = (String) conn.in.readLine();
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}

			return "-1";
		});

		try {
			conn.close();
		} catch (IOException e) {			
			e.printStackTrace();
			return -1;
		}

		return Integer.parseInt(limit);
	}
	/*
	 * 	Set the speed limit at sensor id to limit.
	 *  
	 */
	public boolean setSpeedLimit(int id, int limit) {
		Connection conn = new Connection(speedLimitPort, "127.0.0.1");
		try {
			conn.connectSocket();
			conn.connectIN();
			conn.connectOUT();
		} catch (UnknownHostException e1) {
			System.out.println("Could not connect to host");
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {			
			e1.printStackTrace();
			return false;
		}
		
		String limitString = String.format("%03d", limit);
		String idString = String.format("%05d", id);
		String payLoad = idString + limitString;
		
		
		
		String ret = conn.transfer("1" + payLoad, (msg) -> {
			conn.out.write(msg);
			conn.out.flush();
			String s;
			try {
				s = (String) conn.in.readLine();
				return s;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "-1";
		});

		try {
			conn.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		if(ret == "OK") return true;
		return false;
		
	}
	/*
	 * Returns die current flow of pedestrians in both directions
	 * at sensor id.
	 */
	
	public int[] getPedestrianData(int id) {
		// dummy for now.
		return new int[2];
	}

	public boolean setTrafficLight(int id, int state) {
		// dummy for now.
		return false;
	}
	
	public int getCameraFeed(int id) {
		// dummy 
		return -1;
	}
}
