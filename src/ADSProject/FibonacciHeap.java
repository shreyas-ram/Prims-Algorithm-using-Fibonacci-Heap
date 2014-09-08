package ADSProject;

import java.io.File;
import java.util.*;

public class FibonacciHeap<T> {
	HashMap<Integer,FibonacciHeapNode> entries=new HashMap<>();
	FibonacciHeapNode<T> minNode=null;
	int size=0;
	
	//Store a node with the key as vertices of a edge and the priority as the cost
	
	public FibonacciHeapNode<T> min() {
	        return this.minNode;
	 }
	 
	 public FibonacciHeapNode<T> insert(T[] value, int priority) {
	  //For insert, make a single Node heap and meld the two      
	        FibonacciHeapNode<T> result = new FibonacciHeapNode<T>(value, priority);
	        minNode = meldNodes(minNode, result);
	        ++size;
	       // HashMap<T,FibonacciHeapNode<T>> t=new HashMap();
	        //t.put(value[1],result);
	        entries.put( (Integer) value[0], result);
	        
	        return result;
	 }
	 
	 public void delete(FibonacciHeapNode<T> node) {
		 
	     	//Decrease key to min value and removeMin to delete a node
	        decreaseKey(node, Integer.MIN_VALUE);
	        removeMin();
	    }
	 
	 public void decreaseKey(FibonacciHeapNode<T> node, int p) {
	        /*Decrease the priority and call cascadingCut if node priority is less
		 than parent priority*/
	        node.priority = p;

	        if (node.parentNode != null &&  node.priority < node.parentNode.priority)
	            cascadingCut(node);

	        if (node.priority < minNode.priority)
	            minNode=node;
	    }
	 
	 
	 private void cascadingCut(FibonacciHeapNode<T> node) {
	        
		 	node.marked = false;
	        if (node.parentNode == null) return;

	        if (node.nextNode != node) {// If it has sibblings
	            node.nextNode.prevNode = node.prevNode;
	            node.prevNode.nextNode = node.nextNode;
	        }

	        if (node.parentNode.childNode == node) {//If the parent is pointing to this node
	            
	            if (node.nextNode != node) {
	                node.parentNode.childNode = node.nextNode;
	            }
	            else {
	                node.parentNode.childNode = null;
	            }
	        }
	        --node.parentNode.degree;
	        node.prevNode = node.nextNode = node;
	        minNode = meldNodes(minNode, node);

	        if (node.parentNode.marked)
	            cascadingCut(node.parentNode);
	        else
	            node.parentNode.marked = true;
	        	node.parentNode = null;
	    }
	 
	 public static <T> FibonacciHeap<T> meld(FibonacciHeap<T> firstHeap, FibonacciHeap<T> secondHeap) {
	        
	        FibonacciHeap<T> result = new FibonacciHeap<T>();
// Meld the root nodes of both the heaps
	        result.minNode = meldNodes(firstHeap.minNode, secondHeap.minNode);
	        result.size = firstHeap.size + secondHeap.size;

	        firstHeap.size = secondHeap.size = 0;
	        firstHeap.minNode  = null;
	        secondHeap.minNode  = null;
	        return result;
	    }
	 
	 private static <T> FibonacciHeapNode<T> meldNodes(FibonacciHeapNode<T> node1, FibonacciHeapNode<T> node2) {
	        
	        if (node1 == null && node2 == null) {
	            return null;
	        }
	        else if (node1 != null && node2 == null) {
	            return node1;
	        }
	        else if (node1 == null && node2 != null) { 
	            return node2;
	        }
	        else {// This is a simple pointer exchange to merge the nodes
	            FibonacciHeapNode<T> node1Next = node1.nextNode;
	            node1.nextNode = node2.nextNode;
	            node1.nextNode.prevNode = node1;
	            node2.nextNode = node1Next;
	            node2.nextNode.prevNode = node2;

	            
	            return node1.priority < node2.priority? node1 : node2;
	        }
	    }
	 
	 public FibonacciHeapNode<T> removeMin() {
	        
		 	if (minNode==null)
	            return null;
		 entries.remove(minNode.value[0]);
	        --size;
	        FibonacciHeapNode<T> minElem = minNode;

	        //if minNode has no siblings
	        if (minNode.nextNode == minNode) {
	            minNode = null;
	        }
	        else { //point previous node of minNode to next node of minNode
	            minNode.prevNode.nextNode = minNode.nextNode;
	            minNode.nextNode.prevNode = minNode.prevNode;
	            minNode = minNode.nextNode; 
	        }

	        if (minElem.childNode != null) {
	            
	            FibonacciHeapNode<?> curr = minElem.childNode;
	            do {
	                curr.parentNode = null;
	                curr = curr.nextNode;
	            } while (curr != minElem.childNode);
	        }
//To meld two heaps with the same degree, keep lists and traverse through them and meld when necessary
	        
	        minNode = meldNodes(minNode, minElem.childNode);

	       if (minNode == null) return minElem;

           List<FibonacciHeapNode<T>> treeNodes = new ArrayList<FibonacciHeapNode<T>>();
  	       List<FibonacciHeapNode<T>> toVisit = new ArrayList<FibonacciHeapNode<T>>();

	       for (FibonacciHeapNode<T> curr = minNode; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.nextNode)
	            toVisit.add(curr);

	        
	        for (FibonacciHeapNode<T> curr: toVisit) {
	            while (true) {
	                
	            	while (curr.degree >= treeNodes.size())
	                    treeNodes.add(null);

	                if (treeNodes.get(curr.degree) == null) {
	                    treeNodes.set(curr.degree, curr);
	                    break;
	                }

	                FibonacciHeapNode<T> other = treeNodes.get(curr.degree);
	                treeNodes.set(curr.degree, null);

	                FibonacciHeapNode<T> min = (other.priority <= curr.priority)? other : curr;
	                FibonacciHeapNode<T> max = (other.priority <= curr.priority)? curr  : other;

	                max.nextNode.prevNode = max.prevNode;
	                max.prevNode.nextNode = max.nextNode;

	                max.nextNode = max.prevNode = max;
	                min.childNode = meldNodes(min.childNode, max);
	                max.parentNode = min;
	                max.marked = false;
	                ++min.degree;
	                curr = min;
	            }

	            if (curr.priority <= minNode.priority) minNode = curr;
	        }
	        return minElem;
	    }
}
