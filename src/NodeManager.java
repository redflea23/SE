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
	private int trafficLightsPort;
	private int pedestrianPort;
	private Hashtable<Integer, Integer> rfid;
	private ArrayList<Process> procList;

	public NodeManager(int speedLimitPort, int rfidPort, int trafficLightsPort, int pedestrianPort) {
		this.procList = new ArrayList<>();
		this.speedLimitPort = speedLimitPort;
		this.rfidPort = rfidPort;
		this.trafficLightsPort = trafficLightsPort;
		this.pedestrianPort = pedestrianPort;
		rfid = new Hashtable<>();
		initServers();
	}
	
	/* 
	 * Initiates the servers. The python files should be in the root of the java project.
	 */
	private void initServers() {
		
		ProcessBuilder rfid = new ProcessBuilder("python", getCurrentPath() + "/RFIDSensor.py");
		ProcessBuilder speedLimit = new ProcessBuilder("python", getCurrentPath() + "/SpeedLimit.py");
		ProcessBuilder trafficLight = new ProcessBuilder("python", getCurrentPath() + "/TrafficLight.py");
		ProcessBuilder pedestrian = new ProcessBuilder("python", getCurrentPath() + "/PedestrianSensor.py");
		
		try {
			procList.add(rfid.start());
			procList.add(speedLimit.start());
			procList.add(trafficLight.start());
			procList.add(pedestrian.start());
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
	 * Returns the last known information an all RFID-Equipped vehicles or null if an error was encountered.
	 * Returned data is structured as follows:
	 *  |Vehicle Class | Vehicle ID |   as keys. With lengths:   |X|XXXX|
	 *  |Sensor ID | Active | Route|    as values. With lengths: |XXXXX|X|XXX|
	 *  See cityGraph.jpg for a mapping from IDs to streets.
	 */
	public HashMap<Integer, Integer> getRFIDData() {
		HashMap<Integer, Integer> retMap = new HashMap<>();
		rfid.forEach((k, v) -> retMap.put(k, v));
		return retMap;
	}	
	
	/*
	 * Takes the id of the speed limit sign in question.
	 * Returns the current speed limit set at sensor-id or -1 if an error was encountered.
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
	 * Takes the id of the speed limit sign and a new speed limit to display.
	 * Returns true if the display was successfully change, false otherwise.
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
	 * Takes the ID of the pedestrian sensor in question.
	 * Returns an Integer Array of size 2. The entry at index 0 is the number of pedestrians that walked in road direction
	 * over the sensor in the last 30 seconds. The entry at index 1 are the pedestrians that walked against the road direction
	 * in the last 30 seconds.
	 * [see cityGraph.jpg for a layout and understanding of road directions.]
	 */
	
	public int[] getPedestrianData(int id) {
		Connection conn = new Connection(pedestrianPort, "127.0.0.1");
		try {
			conn.connectSocket();
			conn.connectIN();
			conn.connectOUT();
		} catch (UnknownHostException e1) {
			System.out.println("Could not connect to host");
			e1.printStackTrace();
			return new int[]{-1, -1};
		} catch (IOException e1) {			
			e1.printStackTrace();
			return new int[]{-1, -1};
		}
		
		String pattern = conn.transfer("0" + id, (msg) -> {
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
			return new int[]{-1, -1};
		}
		String first = pattern.substring(0, 3);
		String second = pattern.substring(3, 6);
		
		return new int[] {Integer.parseInt(first), Integer.parseInt(second)};
	}
	
	
	/*
	 * Takes the ID of a traffic light and the state this traffic light should be switched to.
	 *  state 0 would be the default light switching pattern.
	 *  1 would be a green light only on the northern edge of the intersection [in the graph, see cityGraph.jpg]
	 *  2 would be a green light only on the eastern side of the intersection.
	 *  3 would be a green light only on the southern side of the intersection.
	 *  4 would be a green light only on the western side of the intersection.
	 */
	public boolean setTrafficLight(int id, int state) {
		Connection conn = new Connection(trafficLightsPort, "127.0.0.1");
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
		
		String patternString = String.valueOf(state);
		String idString = String.format("%05d", id);
		String payLoad = idString + patternString;		
		
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
	 * Takes an ID of a traffic light.
	 * Returns the current state this traffic light is in.
	 */
	
	public int getTrafficLight(int id) {
		Connection conn = new Connection(trafficLightsPort, "127.0.0.1");
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
		
		String pattern = conn.transfer("0" + id, (msg) -> {
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

		return Integer.parseInt(pattern);
	}
	
	public int getCameraFeed(int id) {
		// dummy, not implemented 
		return -1;
	}
}
