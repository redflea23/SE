package TPS_package;

import java.io.IOException;

public class rush_hour {
	static int start = 2;
	static int end = 4;
	
	/**CURRENTLY NOT IMPLEMENTED FULLY.
	 * Checks if it is rush hour or not.
	 * @param x the Current iteration. 
	 * @throws IOException 
	 */
	public void isItRushHour(int x) throws IOException {
		memoryHandling memory_Handling = new memoryHandling();
		String itIs = "false";	
		if(start <= x && x <= end) {
			itIs = "true";
		}
		memory_Handling.createMemory(itIs,3);
	}
}
