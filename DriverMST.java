import java.io.*;
import java.util.*;

public class DriverMST {
	static String e;
	DriverMST(String args){
		this.e = args; //sets the -e command to be used in the printedge method
	}
	
	public static void main(String[] args) throws IOException {
		//https://www.cs.swarthmore.edu/~newhall/unixhelp/Java_files.html
		 Scanner reader = new Scanner(new FileInputStream(args[0])); 
		 ArrayList<Integer> mstTree = new ArrayList<Integer>();
		 if(args.length == 2) { //checks if there is a "-e" command
			DriverMST print = new DriverMST(args[1]);
		 }
		 while(reader.hasNextInt()) {
		 mstTree.add(reader.nextInt());
	
		 }
			int n = mstTree.get(0);
			int i = 3;
			int j = 0;
			 ArrayList<Integer> cost = new ArrayList<Integer>();
			 ArrayList<Integer> node1 = new ArrayList<Integer>();
			 ArrayList<Integer> node2= new ArrayList<Integer>();
		
			while( i < mstTree.size() ) {
				cost.add(mstTree.get(i)); //adds cost of trees to a new array list
				node1.add(mstTree.get(i - 2)); //adds first nodes to array list
				node2.add(mstTree.get(i - 1)); //adds second nodes to array list
				i = i+3; 
				j++;
			}
			kruskalalg(n,cost, node1, node2); 
		 primsalg(n,cost,node1,node2);
		 
	}
	
	public static void printedges (ArrayList<Integer> T) {
	
		if(e != null){ //if the -e command is specified the edges and cost will print
		for(int i = 0, j = 2; i < T.size(); i = i + 3, j = j + 3) {
		System.out.println("Edges = " + T.get(i) + "---" + T.get(i+1) + " cost = " + T.get(j));
		}
		}
	}

	
	public static void kruskalalg(int nodes, ArrayList<Integer> cost, ArrayList<Integer> node1, ArrayList<Integer> node2) {
		int i = 1;
			int j = 0;
			 ArrayList<Integer> cost2 = new ArrayList<Integer>(cost);
			 ArrayList<Integer> node12 = new ArrayList<Integer>(node1);
			 ArrayList<Integer> node22= new ArrayList<Integer>(node2);
			ArrayList<Integer> T1 = new ArrayList<>(); //empty set
		
		while(i < node12.size() + 1 ) { //inserts node 2 value after corresponding node 1 value
			node12.add(i, node22.get(j));
			i= i + 2;
			j++;
		}
		
		for(int p = 2, addcost = 0; p <node12.size()+ 1; p = p + 3, addcost++) {
			node12.add(p, cost2.get(addcost));
		
			//this will add the cost of the edge to the corresponding edges
		}
		long tick = System.currentTimeMillis();
		//this for loop is sorting the cost and the connected edges into non decreasing order
		  for (int k = 2; k < node12.size()+ 2; k = k + 3) {
	            for (int p = k + 3; p < node12.size()+ 2; p = p + 3) { 
	            	if(k == node12.size() || p == node12.size() + 2 ) {
	            		break;
	            	}
	                if (node12.get(k) > node12.get(p)) { 
	                    int temp = node12.get(k);
	                    Collections.swap(node12, p, k);
	                    Collections.swap(node12, p- 1, k- 1);
	                    Collections.swap(node12, p - 2, k - 2);
	              
	                }
	            }
	        }
	
		
		int edge1 = 0; //sets the index for the edges and the cost
		int edge2 = 1;
		int costcount = 2;
		while(nodes >= 2) { //start of the while loop shown in the diagram
			
		if(edge1 == node12.size() || edge2 == node12.size()) {
				break;
			}
		
		if(node12.get(edge1) != node12.get(edge1 + 3)) { //checks to see if the edges are in different components
				
				T1.add(node12.get(edge1));
				T1.add(node12.get(edge2));
				T1.add(node12.get(costcount));
				costcount = costcount + 3;
				edge1 = edge1 + 3;
				edge2 = edge2 + 3;
				nodes--;
				
			} 
	
			
		}
		long tock = System.currentTimeMillis();
		//end of runtime
		int temp = 0;
		
		for(int d = 2; d < T1.size() ; d = d + 3) {
			
			if(d == T1.size() + 3) { //getting total cost of tree
				break;
			}
			int totalcost = T1.get(d) + temp;
			 temp = totalcost;
		}
		
	
 System.out.println("Total cost Kruskal's = " + temp + "| runtime = " + (tock - tick) + " ms");

	printedges(T1);	


	}
	
	
	public static void primsalg(int nodes, ArrayList<Integer> cost, ArrayList<Integer> node1, ArrayList<Integer> node2) {
		int i = 1;
		int j = 0;
		ArrayList<Integer> T = new ArrayList<>(); //empty set
		ArrayList<Integer> visitednodes = new ArrayList<>();//set that will contain visited nodes
		
		while(i < node1.size() + 1) { //inserts node 2 value after corresponding node 1 value
			
			node1.add(i, node2.get(j));
			i= i + 2;
			j++;
		}
		
		for(int p = 2, addcost = 0; p <node1.size()+ 1; p = p + 3, addcost++) {
			node1.add(p, cost.get(addcost));
		
			//this will add the cost of the edge to the corresponding edges
		}
		//this for loop is sorting the cost and the connected edges into non decreasing order
		  for (int k = 2; k < node1.size()+ 2; k = k + 3) {
	            for (int p = k + 3; p < node1.size()+ 2; p = p + 3) { 
	            	if(k == node1.size() || p == node1.size() + 2 ) {
	            		break;
	            	}
	                if (node1.get(k) > node1.get(p)) {
	                    int temp = node1.get(k);
	                    Collections.swap(node1, p, k);
	                    Collections.swap(node1, p- 1, k- 1);
	                    Collections.swap(node1, p - 2, k - 2);
	                }
	            }
	        }
		  long tick = System.currentTimeMillis(); //start of runtime clock
		
		
		  visitednodes.add(node1.get(0)); //adding first node to list of visited nodes

		int index = 0; //setting i index for (i,j)
		int jdex = index + 1; //setting j index for (i,j)
	
		//checks if the node being checked has been visited 
		while(visitednodes.contains(node1.get(jdex)) == false || nodes >= 2) { 
			T.add(node1.get(index)); //adds the first node to the list
			T.add(node1.get(jdex)); //adds the second node to the empty list
			T.add(node1.get(jdex + 1)); //adds the cost of the edge to the list
			visitednodes.add(node1.get(index)); //adds node that was visited to the list
			jdex= jdex + 3;
			index = jdex - 1;
			nodes--;
		}
		
	
		int temp = 0;
		//gets the total cost of the list
	
		for(int d = 2; d < T.size() ; d = d + 3) {
			
			if(d == T.size() + 3) {
				break;
			}
			int totalcost = T.get(d) + temp;
			 temp = totalcost;
		}
		  long tock = System.currentTimeMillis();
		
		System.out.println("Total cost of Prim's alg = " + temp + "| runtime = " + (tock - tick) + " ms");
			printedges(T); 
		
		
	}

}
