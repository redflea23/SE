package TPS_package;

import java.io.IOException;
import java.util.HashMap;

/**Code to convert string to other data types.
 * 
 * @author fritz
 *
 */
public class stringConverter {
	
	/**
	 * 
	 * @param HashMap a string representation of an array.
	 * @return an Integer array.
	 * @throws IOException
	 */
	public int[] stringToIntArray(String HashMap)throws IOException {
	        int [] intList = new int[2];
		    String textHashMap = HashMap;
	        	        
	        // removes the "{" "}" from the string to enable the conversion
	        textHashMap = textHashMap.replace("{", "");
	        textHashMap = textHashMap.replace("}", "");
	        
	        //split the String by a comma
	        String parts[] = textHashMap.split(",");
	        
	        //iterate the parts and add them to a hashmap
	        for(String part : parts){
	            
	        	//split the data by : to get key and data
	            String empdata[] = part.split("=");

	            //add values to list
	            intList[0] = Integer.parseInt(empdata[0].trim());
	            intList[1] = Integer.parseInt(empdata[1].trim());
	        }
	        return intList; 
	       
	    }
	
	/**
	 * Class to fetch the stored RFID data and convert it from a string to an hashmap
	 * @param HashMap: a string representation of an hashmap.
	 * @return: a HashMap<Integer, Integer> hashmap.
	 * @throws IOException
	 */
	public HashMap<Integer, Integer>  StringToIntHashMap(String HashMap) throws IOException{
		
		String textHashMap = HashMap;
		 
		//new HashMap object
		HashMap<Integer, Integer> hMapData = new HashMap<Integer, Integer>();
	        
	    // removes the "{" "}" from the string to enable the conversion.
	    textHashMap = textHashMap.replace("}", "");
	    textHashMap = textHashMap.replace("{", "");
	            
	    //split the String by , to get the different Hashes comma
	    String parts[] = textHashMap.split(",");
	        
	    //iterate the parts and add them to a map
	    for(String part : parts){
	    	//split the data by : to get key and data
	        String empdata[] = part.split("=");
	            
	        int intKey = Integer.parseInt(empdata[0].trim());	          
	        int intData = Integer.parseInt(empdata[1].trim());	
	            
	        //add to hashMap
	        hMapData.put(intKey,intData );
	     }
	     return hMapData;    
	}
	
	/**
	 * Class to fetch the stored number of pedestrians per category.
	 * @param HashMap: a string representation of an hashmap.
	 * @return: a HashMap<Integer, int[]> hashmap.
	 * @throws IOException
	 */
	public HashMap<Integer, int[]> StringToIntArrayHashMap(String HashMap) throws IOException{
		 HashMap<Integer, int[]> hMapData = new HashMap<Integer, int[]>();
		 HashMap<Integer, int[]> zHMapData = new HashMap<Integer, int[]>();
		 String textHashMap = HashMap;
	
		 
	       int[] noText = {0,0,0,0};
	     // removes the "{" "}" from the string to enable the conversion
	     textHashMap = textHashMap.replace("}", "");
	     textHashMap = textHashMap.replace("{", "");
	     
	     if(textHashMap.isEmpty() == true) {
	    	 zHMapData.put(-10000, noText);
	    	 return zHMapData;
	  
	      }
	     else {
	    	//split the String by a comma
		     String parts[] = textHashMap.split(",");
		        
		     //iterate the parts and add them to a map
		     for(String part : parts){
		    	 
		    	 //split the data by : to get key and data
		         String empdata[] = part.split("=");
		            
		         int intKey = Integer.parseInt(empdata[0].trim());
		         
		         //Split the values by ; to get the different values.
		         String valueArray[] = empdata[1].split(";"); 
		        
		         int[] intData = new int[valueArray.length];
		         
		         //Insert values into the array.
		         for(int i=0; i < valueArray.length;++i){
			        	 intData[i] = Integer.parseInt(valueArray[i].trim());
			     }
		         //add to hashMap
		         hMapData.put(intKey,intData );
		      }
	      }
	     
	      return hMapData;   
	}
	
	/**
	 * Converts a String representation of an array and converts it into an integer array.
	 * @param array: a string representation of an array. ( ex: RFID;last passed sensor;active;route)
	 * @return a integer array.
	 * @throws IOException
	 */
	public int[] stringToIntArray2(String array)throws IOException {
         
	    String textHashMap = array;
        	        
        // removes the "{" "}" from the string to enable the conversion
        textHashMap = textHashMap.replace("{", "");
        textHashMap = textHashMap.replace("}", "");
        
      //Split the values by ; to get the different values.
        String valueArray[] = textHashMap.split(";"); 
       
        int[] intData = new int[valueArray.length];
        
        //Insert values into the array.
        for(int i=0; i < valueArray.length;++i){
	        	 intData[i] = Integer.parseInt(valueArray[i].trim());
	     }
        //add to hashMap
        int [] intList = intData;
        return intList; 
       
    }
	
}
