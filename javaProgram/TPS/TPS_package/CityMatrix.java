package TPS_package;



//class for the cities street intersections.
public class CityMatrix {
	
	// All the streetID:S in the city.
	protected int[] streetID = {0, 12,43,56,78,910,1112,1314,1516,1718,1920,2122,2324,2526,
			   2728,2930,3132,3334,3536,3738,3940,4142,4344,4546,4748,4950,
			   5152,5354,5556,5758,5960,6162,6364,6566,6768,6970,7172,7374,7576,
			   7778,7980,8182,8384,8586,8788,8990,9192,9394,9596};
	
	/**
	 * Fetches the street ID:s from the street thats are recuested. 
	 * @param streetID: the streetID of the street.
	 * @return A array with the sensor ID:s from the sensors at that street.
	 */
	public int[] getSensorID(int streetID){
		int[] sensirIDs = new int[2];	
			if(streetID < 910) {
				sensirIDs[0] = streetID/10;
				sensirIDs[1] = streetID%10;
				//System.out.println("i: "+i+" | SensorID: "+firstSensor);
			}
			else {
				sensirIDs[0] = streetID/100;
				sensirIDs[1]= streetID%100;
				//System.out.println("i: "+i+" | SensorID: "+firstSensor);
			}
		return sensirIDs;
	}
	/**
	 * returns the streetID with index x. Is used in Clear_Memory().
	 * @param x
	 * @return
	 */
	public int GetStreet(int x) {
		return streetID[x];
	}
}
