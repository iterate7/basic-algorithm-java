package kdtree;

import java.util.ArrayList;
import java.util.List;

public class KDTreeMaths {

	public static float average(float[] item)
	{
		float average = 0f;
		for(float it: item)
			average+=it;
		average/=item.length;
		return average;
	}
	public static float[] getCol(List<float[]>data, int col)
	{
		float[] colData = new float[data.size()];
		for(int i=0;i<data.size();i++)
		{
			colData[i] = data.get(i)[col];
		}
		return colData;
	}
	
	
	public static float distance(float[]v1, float[]v2)
	{
		float sum = 0 ;
		for(int i=0;i<v1.length;i++)
		{
			sum+=Math.pow(v1[i]-v2[i],2);
		}
		return sum;
	}
	
	public static  float variance(float[] item)
	{
		float average = 0f,var = 0;
		for(float it: item)
			average+=it;
		average/=item.length;
		for(float it: item)
		{
			var+=(it-average)*(it-average);
		}
		return var/item.length;
	}
	
	
	public static List<float[]> normal(List<float[]>data)
	{
		 List<float[]>  ret = new ArrayList<float[]>();
		for(float [] vec: data)
		{
			float sum = 0;
			for(float s: vec)
				sum+=s*s;
			sum = (float)Math.sqrt(sum);
			for(int i=0;i<vec.length;i++)
			{
				vec[i] = vec[i]/sum;
			}
			ret.add(vec);
		}
		return ret;
	}
	
}
