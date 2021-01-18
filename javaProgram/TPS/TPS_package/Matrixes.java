package TPS_package;



//class for the cities street intersections.
public class Matrixes {
	
	
	protected int[] streetID = {0, 12,43,56,78,910,1112,1314,1516,1718,1920,2122,2324,2526,
			   2728,2930,3132,3334,3536,3738,3940,4142,4344,4546,4748,4950,
			   5152,5354,5556,5758,5960,6162,6364,6566,6768,6970,7172,7374,7576,
			   7778,7980,8182,8384,8586,8788,8990,9192,9394,9596};
	
	/**Fetches the sensor values on a street 
	 * @param street: a Array containg the sensors at the street. i.e the position in the array the current street has.
	 * 					Ex: street 12: [0]=1 & [1]=2, 910: [0]=9 & [1]=10 and so on. 
	 * @return
	 */
	public int[] getSensorID(int local_ID){
		int[] sensirIDs = new int[2];	
			if(local_ID < 910) {
				sensirIDs[0] = local_ID/10;
				sensirIDs[1] = local_ID%10;
				//System.out.println("i: "+i+" | SensorID: "+firstSensor);
			}
			else {
				sensirIDs[0] = local_ID/100;
				sensirIDs[1]= local_ID%100;
				//System.out.println("i: "+i+" | SensorID: "+firstSensor);
			}
		return sensirIDs;
	}
	public int GetStreet(int x) {
		return streetID[x];
	}
}
