import java.util.BitSet;

public class BloomFilterRan {
	private BitSet Table;
	private int k;
	private int numadd;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	int[] RandNo={722162781, 220127576, 270972272, 662577060, 840092724, 758417020, 584865032, 271218959,
			213615927, 281501716, 344097344, 367420378, 710005842, 746383930, 575190932, 944141443, 
			191959829, 259831122, 992355097, 847017361, 346793861, 718030816, 329312996, 812969605, 
			175244378, 937553486, 501220743, 840767491, 803812384, 164637043, 809117437, 953505633, 
			938122198, 332039127, 358178995, 442057569, 648058868, 901846048, 430041374, 842019079, 
			910666982, 687323871, 293962393, 299287958, 303065928, 123568180, 835752329, 583320164, 
			261413674, 561350960, 511601576, 250514513, 904830179, 232610607, 426168875, 284378515, 
			867546435, 192390905, 130138399, 265434220, 745462545, 929522225, 256415091, 812592025, 
			611364196, 199095533, 405361350, 455460698, 333441061, 989102855, 326988378, 725171242, 
			373239010, 526266618, 661385511, 413243008, 499650145, 907354259, 511330551, 155607269, 
			699429178, 245815119, 538955089, 418493990, 299959012, 995515497, 891555807, 446979843, 
			768552644, 505313335, 807343409, 285237853, 243506602, 631989683, 250158936, 650216000,
			823312846, 724324333, 381303700, 310563439};
	public BloomFilterRan(int setSize,int bitPerElement){
		Table=new BitSet(setSize*bitPerElement);
		//http://corte.si/posts/code/bloom-filter-rules-of-thumb/
		//number of hashes grows linearly with the number of bits per element (b):
		//k=b.log(2);
		k= (int) ((bitPerElement)*Math.log(2));
		numadd=0;
	}
	public int randomHashFunction(int k, String S){
		//ax+b%p
		int a=711350805;
		int b=269435197;
		//get prime from http://www.primos.mat.br/2T_en.html
		int p=920184149;
		int x=S.hashCode();
		int hash =0;
		hash=(a*x+b)%p;
		hash^=RandNo[k%RandNo.length];
		return Math.abs(hash);
	}
	//Add string s to the BitSet, Set all bits that have been returned by randomHashFunction
	public void add(String S){
		S=S.toLowerCase();
		numadd++;
		for(int i=1;i<=k;i++){
			int x=randomHashFunction(i, S);
			Table.set(x%Table.size());
		}
	}
	
	//Verify if the specific string s has exists in the Table?
	public boolean appears(String S){
		S=S.toLowerCase();
		for(int i=1;i<=k;i++){
			int x=randomHashFunction(i, S);
			if(!Table.get(x%Table.size()))
					return false;
		}
		return true;
	} 
	//Return the size of the bloom filter
	public int filterSize(){
		return Table.size();
	}
	
	//returns the number of items that have been add to the table
	public int dataSize(){
		return numadd;
	}
	
	//returns the number of k hash functions
	public int numHash(){
		return k;
	}
}
