package ADSProject;

import java.io.*;
import java.util.*;



public class mst {
	static int numVertices=0;
	public static void main(String[] args){
		
		int density=0;
		//Check user Input
		if(args[0].equals("-f")||args[0].equals("-s")){
			String filename=args[1];
			//System.out.println("a");
			Graph graph1 = new Graph();
			graph1.Graph(filename);
		
			if(args[0].equals("-s")){
				primAL(graph1,1);//Call function to perform Prim's using Simple Scheme
					
			}
			if(args[0].equals("-f")){
				//	System.out.println(graph1.g1);
					primFH(graph1,1);//Call function to perform Prim's using Fibonacci heap
			}
			
			
		}
		if(args[0].equals("-r")){
			numVertices=Integer.parseInt(args[1]);
			density=Integer.parseInt(args[2]);
			Graph graph1=new Graph();
			//Graph graph2=new Graph(graph1);
					//graph1.copyGraph(graph2);
			graph1.makeRndGraph(numVertices, density); //Generate random Graph
			
			long start=System.currentTimeMillis();
			primFH(graph1,0); 
			System.out.print("Fibo Time: ");
			System.out.println(System.currentTimeMillis()-start);
			start=System.currentTimeMillis();
			primAL(graph1,0);
			System.out.print("Simple Time: ");
			System.out.println(System.currentTimeMillis()-start);
		}		
	}
	
		public static void primFH(Graph g,int printcode){
			Graph graph=g;
			//System.out.println(g.g1);
			Graph mst=new Graph();
			ArrayList<String> list=new ArrayList<String>();
			FibonacciHeap<Integer> heap=new FibonacciHeap<Integer>();//Fibo heap to hold vertices
			
			int count=0;
			int key=0; 
			int totalCost=0;
			Integer[] firstNode={0,0};
			heap.insert(firstNode,0);
			HashMap<Integer,Integer> p=new HashMap();
			p.put(0, 0);
			mst.g1.put(0, p); //Define 0 as the entry point
			do{
				
				FibonacciHeapNode<Integer> node=heap.minNode; //Access the min node
				totalCost=totalCost+node.priority;
				Integer[] val=node.value;
				key=val[0]; //Key specifies the destination vertex
				p=new HashMap();
				p.put(val[1], node.priority);
				mst.g1.put(key,p);
				list.add(val[1]+" "+key);
				heap.removeMin(); //Remove the min value
				count++;
			
				HashMap<Integer,Integer> hm=graph.get(key); //Iterate over the adjacency list of Key
				Iterator it=hm.entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry entry=(Map.Entry)it.next();
		
					Integer[] a={(Integer) entry.getKey(),key};
			/* The Prim's algorithm checks the neighbours.For each entry check if it is present in mst,
			 * if not, then check if it is present in the heap,if it is present in the heap, decrease key if the 
			 * priority of new node is lesser. If nor presesnt add to the heap		
			 */
				
					if(mst.g1.containsKey((Integer) entry.getKey())==false){
					if(heap.entries.containsKey((Integer) entry.getKey())==true ){
					//	System.out.println("Dec "+(Integer) entry.getKey()+ " "+(Integer) entry.getValue());
						if(heap.entries.get((Integer) entry.getKey()).priority>(Integer) entry.getValue()){
						heap.decreaseKey(heap.entries.get((Integer) entry.getKey()),(Integer) entry.getValue() );
						heap.entries.get((Integer) entry.getKey()).value[1]=key;
						}
					}else{
						heap.insert(a,(int) entry.getValue());
						//System.out.println("Inserted: "+(Integer) entry.getKey());
						}
				}
					//key=(Integer) entry.getKey();
						
					}	
				
					
		
				
			}while(count!=graph.size());
			
		if(printcode==1){
		System.out.println(totalCost);
		for(int i=1;i<list.size();i++)
		System.out.println(list.get(i));
		//System.out.println(mst.g1);
		}
		
	}
	
		
		public static void primAL(Graph g,int printcode){
			Graph g1=g;
			int totalCost=0;
			ArrayList<String> list=new ArrayList<String>();
				
			Set<Integer> minTree=new HashSet<Integer>();
			 minTree.add(0);
			 Set<String> msString=new HashSet<String>();
			//System.out.println("G size:"+g1.size()+" Tsize:"+minTree.size());
				
			do{
			 	Iterator iterator=minTree.iterator();
			  //  System.out.println("minTree: "+minTree);
			 	
			 	int minKey=0;
			 	int minCost=2000;
			 	int edgeKey=0;
				while(iterator.hasNext()){
					
					int presentKey=(Integer) iterator.next();
					//System.out.println("Key: "+presentKey);
					HashMap hm=g1.get(presentKey);
					if(!hm.isEmpty()){
					int temp=Collections.min(hm.values());
					if(temp<minCost){
						minCost=temp;
						minKey=presentKey;
					}
					}	
					}
				HashMap hm=g1.get(minKey);
				Iterator it=hm.entrySet().iterator();
				//System.out.println("minKey "+minKey);		
				while(it.hasNext()) {
					Map.Entry entry=(Map.Entry)it.next();
					if ((int)entry.getValue()==minCost) {
						edgeKey=(int)entry.getKey();
						int t=minTree.size();
						minTree.add(edgeKey);
						if(minTree.size()>t){
						msString.add(minKey+" "+edgeKey+" "+minCost);
						totalCost=totalCost+minCost;
						list.add(minKey+" "+edgeKey);
					//	System.out.println(minKey+" "+edgeKey+" "+minCost);
						}
						
						it.remove();
						
					}
					g1.get(edgeKey).remove(minKey);
				}
					
				
				}while(minTree.size()!=g1.size());
				if(printcode==1){
					System.out.println(totalCost);
					for(int i=0;i<list.size();i++)
						System.out.println(list.get(i));
					//System.out.println(mst.g1);
				}
			
			
		}
		

	
}