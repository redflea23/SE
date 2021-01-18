package TPS_package;

import java.io.FileNotFoundException; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;




public class memoryHandling{ 
	
	/**FOR TESTING THE SYSTEM 
	 * constructor that nullifies the memory 
	 * @throws IOException 
	 */
/*public memoryHandling() throws IOException {
		Matrixes matrix = new Matrixes();
		String adress = "";
		// Nullifies the value memory.
		int streetID;
		String startValues = "{";
		for(int j = 1; j <= 6; j++) {
			streetID = matrix.GetStreet(j);
			if(j==1) {
				startValues+= streetID+"=0;0;0;0";
			}
			else {
				startValues+= ", "+streetID+"=0;0;0;0";
			}	
		}
		startValues+= "}";
		adress = memory_address(1);
		 FileWriter memoryValueNullifie = new FileWriter(adress);
			for (int i = 0; i < startValues.length(); i++) {
				memoryValueNullifie.write(startValues.charAt(i));
			}
		 memoryValueNullifie.write(startValues); 
		 memoryValueNullifie.close(); 
		
		// Nullifies the allRFID memory.
		String startRFID = "";
		int x;
		for(int i = 10000; i <= 60000; i +=10000) {
			if(i == 10000 || i == 30000 || i == 40000 || i == 60000) {
				for(int j = 1; j <= 1; j++){
					x = i+j;
					if(x == 10001) { startRFID += "{"+x+"=121000";}
					else{ startRFID += ", "+x+"=120000";}
				}
			}
		}
		startRFID += "}";
		adress = memory_address(0);
		FileWriter memoryRFID_Nullifie = new FileWriter(adress);
		for (int i = 0; i < startRFID.length(); i++) {
			memoryRFID_Nullifie.write(startRFID.charAt(i));
		}
		memoryRFID_Nullifie.close();
		
		// Nullifies the all the active vehicles in  memory.
				String startActiveRFID = "";
				int ij;
				for(int i = 10000; i <= 60000; i +=10000) {
					if(i == 10000 || i == 30000 || i == 40000 || i == 60000) {
						for(int j = 1; j <= 1; j++){
							ij = i+j;
							if(ij == 10001) { startActiveRFID += "{"+ij+"=0;0;0;0";}
							else{ startActiveRFID += ", "+ij+"=0;0;0;0";}
						}
					}
				}
				startActiveRFID += "}";
				adress = memory_address(0);
				FileWriter memoryActiveRFID_Nullifie = new FileWriter(adress);
				for (int i = 0; i < startActiveRFID.length(); i++) {
					memoryActiveRFID_Nullifie.write(startActiveRFID.charAt(i));
				}
				memoryActiveRFID_Nullifie.close();
		
	}*/
	
	/**
	 * sets the address of the memory
	 * @param k: Decides if it is RFID or the number of participants per street that will be stored.
	 * @return a String version of the address of the memory
	 * @throws IOException if k is null
	 */
	public String memory_address(int k) throws IOException {
		
		//Then changing the place of javaProgram folder change this text to the directory of the javaProgram file.
		String address = "F:\\Utlandstudier_2020\\Utlandskurser\\Software_Engineering\\UE_SE_343.303\\Milestones\\Milestone_3\\Milstone 3.1\\javaProgram\\memory";
		
		// text for the different kind of memories.
		//Sores the latest hashmap of all the RFID.
		if( k == 0) {
			address += "\\Memory_All_RFID.txt.";
		}
		// Stores the number of participants 
		else if( k == 1) {
			address += "\\Memory_Values.txt.";
		}
		// Stores the RFID of all the active vehicles. 
		else if( k == 2) {
			address += "\\Memory_Active_RFID.txt.";
		}
		// Stores the variable that tells if it is rushhour or not.
				else if( k == 3) {
					address += "\\Memory_rushHour.txt.";
				}
		return address;
	}
	
	/**
	 * creates/updates the memory
	 * @param Hashmap: the map that will be stored.
	 * @param j: the address of the memory 
	 * @throws IOException if any of the variables is null.
	 */
	public void createMemory(String Hashmap, int j) throws IOException{ 
		// set address.
		String adress = memory_address(j);
		
		// store the hashmap as an string  
		String hashMapStr = Hashmap; 
		
		// attach a the memory file to FileWriter  
		FileWriter memoryFw = new FileWriter(adress); 

		// read character wise from string and write  
		// into FileWriter  
		for (int i = 0; i < hashMapStr.length(); i++) 
			memoryFw.write(hashMapStr.charAt(i)); 

	//System.out.println("Writing successful"); 
			//close the file  
			memoryFw.close(); 
	} 

	/**
	 * Reads from the memory
	 * @param j: The memory address
	 * @return: A string version of the hasmap.
	 * @throws IOException: If j is null
	 */
	public String readMemory(int j) throws IOException{ 
		
		// set address.
		String address = memory_address(j);
		
		// variable declaration 
	    int ch; 
	  
	    // check if File exists or not 
	    FileReader memoryFr = null; 
	    try{ 
	    	memoryFr = new FileReader(address); 
	        } 
	        catch (FileNotFoundException fe){ 
	            System.out.println("File not found"); 
	        } 
	    	String map = "";
	    	
	        // read from FileReader till the end of file 
	        while ((ch=memoryFr.read())!=-1) { 
	            	map += (char)ch;
	        }
	      
	        // close the file 
	        memoryFr.close();
	        return map;
	    }  
	
}
