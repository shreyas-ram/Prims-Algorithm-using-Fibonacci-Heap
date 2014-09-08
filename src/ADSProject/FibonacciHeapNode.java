package ADSProject;

public class FibonacciHeapNode<T> {
	
	 int     degree = 0;       
     boolean marked = false; 
     T[]      value;     
     int priority;
     
     FibonacciHeapNode<T> nextNode;   
     FibonacciHeapNode<T> prevNode;

     FibonacciHeapNode<T> parentNode; 
     FibonacciHeapNode<T> childNode;  

     public FibonacciHeapNode(T[] elem, int p) {
         nextNode = prevNode = this;
         value = elem;
         priority = p;
     }
     
     public int getPriority() {
         return priority;
     }
     public T[] getValue() {
         return value;
     } 
     
     public void setValue(T[] val ) {
         value = val;
     }

 }
