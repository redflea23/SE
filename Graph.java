import java.io.*; 
import java.util.*; 
public class Graph{
    private int[][] matrix;
    int numOfNodes;


    public Graph(int numOfNodes){
        this.numOfNodes = numOfNodes;
        matrix = new int[numOfNodes][numOfNodes];
    }

    public void addEdge(int source, int destination, int weight) {

        int valueToAdd = weight;
    
        matrix[source][destination] = valueToAdd;
    }

    public static void main (String []args){
        Graph graph = new Graph(16);
        createGraph(graph);
        graph.printMatrix();

        /*DataRelay fetch = new DataRelay();
        HashMap <Integer, int[]> RFID =  fetch.get_RFID(); 
         /* hashmap there the key is the vehicle ID and the value
         are a integer array with the RFID.
	 	    RFID [0]== complete RFID.	
	 	    RFID [1]== Last detected sensorID.	
	   	    RFID [2]== Activer boolean.
         	RFID [3]== Route
      
      
        //for given lastDetectedOnSensorID two connected intersections are found 
        //on each intersection traffic light state is changed
        
        Integer key = null;
        int [] value = null;

        Iterator<Integer> iterator = RFID.keySet().iterator();
        while(iterator.hasNext()){
            key = iterator.next();
            value = RFID.get(key);
            if(value[2]==1){
                break;
            }
        } 

        lastDetectedOnSensorID = value[1]
        int [] trafficLightsToControl = graph.findIntersections(lastDetectedOnSensorID);

        */

        //example as test:

        int [] trafficLightsToControl = graph.findIntersections(13);
        System.out.println("Traffic lights with following ids need to be controled:");
        System.out.println(Arrays.toString(trafficLightsToControl));

        

    }

    public int [] findIntersections(int sensorID){
        int enter,exit;
        int i,j;
        int [] result = new int[2];
        for(i=0;i<numOfNodes;i++){
            for(j=0;j<numOfNodes;j++){
                enter =getEnterSensor(i,j);
                exit = getExitSensor(i,j);
                if(sensorID == enter){
                    result[0]=i;
                    result[1]=j;
                }
            }
           
        }
        return result;
    }

    public int getEnterSensor(int i, int j){
        if((matrix[i][j]/100)==0){
            return matrix[i][j]/10;
        }
        return matrix[i][j]/100;

    }

    public int getExitSensor(int i, int j){
        if((matrix[i][j]/100)==0){
            return matrix[i][j]%10;
        }
        return matrix[i][j]%100;
    }

    public void printMatrix() {
        for (int i = 0; i < numOfNodes; i++) {
            for (int j = 0; j < numOfNodes; j++) {               
                System.out.format("%8s", String.valueOf(matrix[i][j]));
            }
            System.out.println();
        }
    }

    public static void createGraph(Graph graph){
        graph.addEdge(0, 1, 12);
        graph.addEdge(1, 0, 34);
        graph.addEdge(1, 2, 56);
        graph.addEdge(2, 1, 78);
        graph.addEdge(2, 3, 910);
        graph.addEdge(3, 2, 1112);

        graph.addEdge(4, 5, 1314);
        graph.addEdge(5, 4, 1516);
        graph.addEdge(5, 6, 1718);
        graph.addEdge(6, 5, 1920);
        graph.addEdge(6, 7, 2122);
        graph.addEdge(7, 6, 2324);

        graph.addEdge(8, 9, 2526);
        graph.addEdge(9, 8, 2728);
        graph.addEdge(9, 10,2930);
        graph.addEdge(10, 9, 3132);
        graph.addEdge(10, 11,3334);
        graph.addEdge(11, 10,3536);

        graph.addEdge(12, 13, 3738);
        graph.addEdge(13, 12, 3940);
        graph.addEdge(13, 14, 4142);
        graph.addEdge(14, 13, 4344);
        graph.addEdge(14, 15,4546);
        graph.addEdge(15, 14,4748);

        graph.addEdge(0, 4, 4950);
        graph.addEdge(4, 0, 5152);
        graph.addEdge(1, 5,5354);
        graph.addEdge(5, 1, 5556);
        graph.addEdge(2, 6, 5758);
        graph.addEdge(6, 2,5960);
        graph.addEdge(3, 7, 6162);
        graph.addEdge(7, 3, 6364);

        graph.addEdge(4, 8, 6566);
        graph.addEdge(8, 4, 6768);
        graph.addEdge(5, 9,6970);
        graph.addEdge(9, 5, 7172);
        graph.addEdge(6, 10, 7374);
        graph.addEdge(10, 6,7576);
        graph.addEdge(7, 11, 7778);
        graph.addEdge(11, 7, 7980);

        graph.addEdge(8, 12, 8182);
        graph.addEdge(12, 8, 8384);
        graph.addEdge(9, 13, 8586);
        graph.addEdge(13, 9, 8788);
        graph.addEdge(10, 14, 8990);
        graph.addEdge(14, 10,9192);
        graph.addEdge(11, 15,9394);
        graph.addEdge(15, 11, 9596);

    }
    
}