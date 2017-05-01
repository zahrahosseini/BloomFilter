import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.File.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DistributedJoin {
	public ArrayList<String>ShouldInserted=new ArrayList<String>();
	
	public HashMap<String, String> hm1 = new HashMap<String, String>();
	public HashMap<String, String> hm2 = new HashMap<String, String>();
	public HashMap<String, String> hm3 = new HashMap<String, String>();
	
	
	public BloomFilterDet CreateBloomFilteronR1() throws Exception{
		//Read the First Relation from the file Relation1.txt
		BufferedReader br = new BufferedReader(new FileReader("Relation1.txt"));
		//
		String line = null;
		int numberofBit=0;
		while ((line = br.readLine()) != null) {
			String[]part=line.split("   ");
			if (part[0].length()>numberofBit)
			numberofBit=part[0].length();
			//System.out.println(part[0]);
			ShouldInserted.add(part[0]);
			//hashmap
			hm1.put(part[0], part[1]);			
		}
		
		// we define BFD as a BloomFilterDet
		BloomFilterDet BFD=new BloomFilterDet(ShouldInserted.size(), numberofBit);
		for(int i=0;i<ShouldInserted.size();i++){
			BFD.add(ShouldInserted.get(i));
		}
		
		br.close();
		return BFD;
	}
	
	public ArrayList<String> R3JoinString;
	public ArrayList<String> R3String;
	
	public void CreateNewTableR3(BloomFilterDet bFD) throws Exception{
		
		R3JoinString=new ArrayList<String>();//r1 field
		R3String=new ArrayList<String>();//r2 field

		//Read the Relation2.txt file
		BufferedReader br = new BufferedReader(new FileReader("Relation2.txt"));
		String line = null;
		while ((line = br.readLine()) != null) {
			String[]part=line.split("   ");
			if(bFD.appears(part[0])){
				R3JoinString.add(part[0]);
				R3String.add(part[1]);
				hm2.put(part[0], part[1]);
							
			}
		}
		br.close();
		//return TC;
		
	}
	private ArrayList<String> String1;
	private ArrayList<String> String2;
	private ArrayList<String> String3;

	public void JoinDoc() throws Exception{//r3xr1
		//ArrayList<TableCreator> NewTable = new ArrayList<TableCreator>();
		String1=new ArrayList<String>();
		String2=new ArrayList<String>();
		String3=new ArrayList<String>();
				
	    String R1F1, R1F2;
	    String R2F2;
	    
	    for(Map.Entry m:hm1.entrySet()){  
	    	 R1F1 =(String) m.getKey(); 
	    	 R1F2=(String) m.getValue();
	    	 R2F2 = hm2.get(R1F1); 
	    	 //Join the selected fileds from R2
	    	 if (hm2.get(R1F1)!= null){	    		 
	    		 String1.add(R1F1);
		    	 String2.add(R1F2);
		    	 String3.add(R2F2);	 
	    	 }
	    	 	    	 
	      }  
	    		
	}
	public void WriteFile() throws Exception{
		File file = new File("Output.txt");

		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		
		//This loop print all elements in Array1 ,2 and array 3 which represent fields in R1 and R2 
		for (int i = 0; i < String1.size(); i++) {
			fw.write(String1.get(i)+" -> "+String2.get(i)+" -> "+String3.get(i)+"\n");
		}
	 
		fw.close();
		System.out.println(" The Result of the join is in the Outpu.txt file");
		System.out.println("Size of the R3 is " + String1.size());
	}
	public static void main(String[] args) throws Exception {
		DistributedJoin d= new DistributedJoin();
		BloomFilterDet BFD=d.CreateBloomFilteronR1() ;
		d.CreateNewTableR3(BFD);
		d.JoinDoc();
		d.WriteFile();
	}
}
