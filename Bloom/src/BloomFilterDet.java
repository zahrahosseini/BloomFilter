import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilterDet {
	private BitSet Table;
	private int k;
	private int numadd;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public BloomFilterDet(int setSize,int bitPerElement){
		Table=new BitSet(setSize*bitPerElement);
		//http://corte.si/posts/code/bloom-filter-rules-of-thumb/
		//number of hashes grows linearly with the number of bits per element (b):
		//k=b.log(2);
		k= (int) ((bitPerElement)*Math.log(2));
		numadd=0;
	}
	
	//add the string to the hash, Set all the bits returned by FNV hash function
	public void add(String S) throws NoSuchAlgorithmException{
		S=S.toLowerCase();
		numadd++;
		for(int i=1;i<=k;i++){
			int x=FNVHash.H_function(i, S);
			Table.set(x%Table.size());
		}
	}
	//Look for the string s to verify that it is in the bloomFilter or not
	public boolean appears(String S) throws NoSuchAlgorithmException{
		S=S.toLowerCase();
		for(int i=1;i<=k;i++){
			int x=FNVHash.H_function(i, S);
			if(!Table.get(x%Table.size()))
					return false;
		}
		return true;
	} 
	
	//returns the size of the BloomFilter
	public int filterSize(){
		return Table.size();
	}
	
	//Return the number of data that have been added to the BitSet
	public int dataSize(){
		return numadd;
	}
	
	//Returns the number of K hash functions
	public int numHash(){
		return k;
	}

}
