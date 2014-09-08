package ADSProject;

import java.io.File;
import java.util.*;

public class Graph {
	//g1 is the Adjacency list of the graph
	public HashMap<Integer, HashMap<Integer,Integer>> g1 =new HashMap<Integer, HashMap<Integer, Integer>>();
	
	Graph(Graph g){
		g.g1=this.g1;
	}
	
	Graph(){
		this.g1=new HashMap<Integer, HashMap<Integer,Integer>>();
	}
	void makeRndGraph(int ver, int den){
		/* To generate random graph, generate random edges with costs and add them to the adjacency list
		 * g1. As this is undirected, two entries are made at a time to specify both directions
		 */
		
		
		 //int[] visited=new int[g1.size()];
		 do{
			 for(int i=0;i<ver;i++){
				 HashMap<Integer,Integer> m=new HashMap<Integer, Integer>();
				 g1.put(i,m);
				 			 
			 }
		 
			 for(int i=0;i<(den*ver*(ver-1)/200);i++){
				 Integer rnd1=null;
				 Integer rnd2=null;
				 do{
					 rnd1=(int) (Math.random()*ver);
					 rnd2=(int) (Math.random()*ver);
				 }while(rnd1==rnd2);
				 
				
				 Integer cost=(int) (Math.random()*1001);
				// System.out.println(cost);
				 HashMap<Integer, Integer> h1=g1.get(rnd1);
				 h1.put(rnd2,cost);
				 g1.put(rnd1, h1);
			 
				 HashMap<Integer, Integer> h2=g1.get(rnd2);
			 	 h2.put(rnd1, cost);
			 	 g1.put(rnd2, h2);
			 	 
			 	 			 
			 }
		 }
		 while(!performDFS(g1));
	   	
	 }
	 /* This is a Depth First Search algorithm implemented using the recurssion method
	  * while keeping track of the visited nodes
	  */
	 public boolean performDFS(HashMap<Integer, HashMap<Integer,Integer>> g){
		 int[]visited=new int[g.size()];
		 visited[0]=1;
		 dfs(g,0,visited);
		 for(int i=0;i<visited.length;i++){
			 if(visited[i]==0){
				 return false;
			 }
		 }
		 return true;
	 }
		 
	 public void dfs(HashMap<Integer, HashMap<Integer,Integer>> g,int node,int[] visited){
		 
		 for (int j = 1; j < g.size(); j++)
	            if (g.get(node).containsKey(j) && visited[j] == 0) {
	                visited[j] = 1;
	                dfs(g, j, visited);
	            }
	    }
		 
	 public void Graph(String s){
			
			try {
				
				Scanner scanner = new Scanner(new File(s));
				int vertices=scanner.nextInt();
				int edges=scanner.nextInt();
				
				for(int i=0;i<vertices;i++){
					 HashMap<Integer,Integer> m=new HashMap<Integer, Integer>();
					 g1.put(i,m);
				 
				 }
				
				while(scanner.hasNextInt()){
				   int ver1=scanner.nextInt();
				   if(scanner.hasNext()){
					   int ver2=scanner.nextInt();
					   if(scanner.hasNext()){
						   int cost=scanner.nextInt();
						   
						   HashMap<Integer, Integer> h1=g1.get(ver1);
							 h1.put(ver2,cost);
							 g1.put(ver1, h1);
						 
							 HashMap<Integer, Integer> h2=g1.get(ver2);
						 	 h2.put(ver1, cost);
						 	 g1.put(ver2, h2);
					   }
				   }
				}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	 
	 

	 public int size(){
		 return g1.size();
	 }
	 public HashMap<Integer, Integer> get(Object key){
		 return g1.get(key);
	 }
	 public  void put(Integer key, HashMap<Integer,Integer> v){
		 g1.put(key, v);
	 }
	 public void output(){
		 System.out.println(g1);
	 }
}
