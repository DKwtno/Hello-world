/**
 * 
 * @author DKwtno
 * print the items of list L according to list p
 */
public class PrintLots {
	public static void printLots(int[] L, int[] p){
		for (int i:p) {
			System.out.println(L[i - 1]);
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] list={11,22,3,4};
		int[] p={1,2};
		printLots(list,p);
		
	}
	
}

	
