import java.util.Random;

// we use this class to test our FalsePositive 
public class MyRandomGenerator {
	public static void main(String[] args) {
		Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
		for(int i=0;i<100;i++){
	    int randomNum = rand.nextInt((999999999 - 111111111) + 1) + 111111111;
		System.out.print(randomNum+", ");
		}
		// TODO Auto-generated method stub

	}
	
}
