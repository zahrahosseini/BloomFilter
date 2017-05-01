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
import java.util.Map;

public class DistributedJoin2 {
	public ArrayList<String>ShouldInserted=new ArrayList<String>();
	
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
				
			}
		}
		br.close();
		
	}
	private ArrayList<String> String1;
	private ArrayList<String> String2;
	private ArrayList<String> String3;

	public void JoinDoc() throws Exception{//r3xr1
		String1=new ArrayList<String>();
		String2=new ArrayList<String>();
		String3=new ArrayList<String>();
	
		
	    BufferedReader br = new BufferedReader(new FileReader("Relation1.txt"));
		String line = null;
		while ((line = br.readLine()) != null) {
			String[]part=line.split("   ");
			String P=part[0];
			for(int i=0; i<R3JoinString.size();i++){
				String R3=R3JoinString.get(i);
				if(R3.equals(P)){
					String1.add(part[1]);
					String2.add(R3String.get(i));
					String3.add(P);

				}
			}
			
		}
		br.close();
	}
	public void WriteFile() throws Exception{
		File file = new File("Output.txt");

		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		 
		for (int i = 0; i < String1.size(); i++) {
			fw.write(String1.get(i)+" -> "+String3.get(i)+" -> "+String2.get(i)+"\n");
		}
	 
		fw.close();
	}
	public static void main(String[] args) throws Exception {
		DistributedJoin2 d= new DistributedJoin2();
		BloomFilterDet BFD=d.CreateBloomFilteronR1() ;
		d.CreateNewTableR3(BFD);
		d.JoinDoc();
		d.WriteFile();
	}
}
