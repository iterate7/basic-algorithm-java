package kdtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KDTreeUsage {

	public KDTreeUsage() {

	
		
	}
	public static void main(String args[])
	{
		performance(1, 10);
	}
	
	
	 public static void performance(int iteration,int datasize){
	        
	        int num = datasize;
	        float[][] input = new float[num][2];
	        List<float[]> data = new ArrayList<float[]>();
	        for(int i=0;i<num;i++){
	            input[i][0]=(float)Math.random()*num;
	            input[i][1]=(float)Math.random()*num;
	            data.add(input[i]);
	        }
	       
	        for(float [] item: data)
	        {
	        	System.out.println(Arrays.toString(item));
	        }
	        
	        KDTree tree = new KDTree();
	        tree.build(data);
	        
	        KDTUtil.printRec(tree.root,0);
	        
	     
	    }

}
