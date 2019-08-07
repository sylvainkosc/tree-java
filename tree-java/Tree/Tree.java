package t_p_Tree;

public class Tree {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

	}
	public void add(String s) {
		
	}
	public void remove(String s) {
		
	}
	public boolean contains(String s) {
		return false;
		
	}

	public void display() {
		System.out.print("[");
		String first = null;
		String currentString = first;
		String comma = "";
		while(currentString!=null) {
			System.out.print(comma+currentString.valueOf(0));
			comma=",";
			currentString = currentString;
		}
		System.out.print("]\n");
	}

}
