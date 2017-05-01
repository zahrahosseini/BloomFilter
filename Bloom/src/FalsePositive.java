import java.util.*;
public class FalsePositive {
	public static int NumberofBits=16;
	private ArrayList<String> RandString;
	private ArrayList<String> AddString;
	private int numString;
	public FalsePositive(int numOfstring) throws Exception{
		RandString= new ArrayList<String>();
		numString=numOfstring;
		RandomStringGenerator s=new RandomStringGenerator();
		for(int i=0;i<numString;i++){
			RandString.add(RandomStringGenerator.generateRandomString(16,RandomStringGenerator.Mode.ALPHA));
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FalsePositive test = new FalsePositive(1000);
		test.FalsePositiveDeterministicBloom();
		test.FalsePositiveRandomBloom();

	}
	
	public void FalsePositiveRandomBloom() throws Exception{
		AddString=new ArrayList<String>();
		BloomFilterRan BDF= new BloomFilterRan(numString, NumberofBits);
		//add generated string to Bloom!
		for(int i=0;i<numString;i++){
			String s=RandString.get(i);
			BDF.add(s);
			AddString.add(s);
		}
		int FP=0;
		int SearchString=0;
		for(int i=0;i<100000;i++){
			String s=RandomStringGenerator.generateRandomString(16,RandomStringGenerator.Mode.ALPHA);
			if(!AddString.contains(s)){
				if(BDF.appears(s))
					FP++;
					SearchString++;							
			}
		}
		System.out.println("theoritically: "+ Math.pow(0.618,NumberofBits));

		System.out.println("Random! Falsepositive: "+FP+" out of "+ SearchString);
	}
	
	public void FalsePositiveDeterministicBloom() throws Exception{
		AddString=new ArrayList<String>();
		BloomFilterDet BDF= new BloomFilterDet(numString, NumberofBits);
		//add generated string to Bloom!
		for(int i=0;i<numString;i++){
			String s=RandString.get(i);
			BDF.add(s);
			AddString.add(s);
		}
		int FP=0;
		int SearchString=0;
		for(int i=0;i<100000;i++){
			String s=RandomStringGenerator.generateRandomString(16,RandomStringGenerator.Mode.ALPHA);
			if(!AddString.contains(s)){
				if(BDF.appears(s))
					FP++;
					SearchString++;
				
				
			}
		}
		System.out.println("theoritically: "+ Math.pow(0.618,NumberofBits));

		System.out.println("Det! Falsepositive: "+FP+" out of "+ SearchString);
		
		 
	    
	}
}
