package kdtree;

import java.util.ArrayList;
import java.util.List;

public class KDTreeMaths {

	public static float average(float[] item) {
		float average = 0f;
		for (float it : item)
			average += it;
		average /= item.length;
		return average;
	}

	public static float[] getCol(List<float[]> data, int col) {
		float[] colData = new float[data.size()];
		for (int i = 0; i < data.size(); i++) {
			colData[i] = data.get(i)[col];
		}
		return colData;
	}

	public static float distance(float[] v1, float[] v2) {
		float sum = 0;
		for (int i = 0; i < v1.length; i++) {
			sum += Math.pow(v1[i] - v2[i], 2);
		}
		return sum;
	}

	public static float variance(float[] item) {
		float average = 0f, var = 0;
		for (float it : item)
			average += it;
		average /= item.length;
		for (float it : item) {
			var += (it - average) * (it - average);
		}
		return var / item.length;
	}

	public static List<float[]> normal(List<float[]> data) {
		List<float[]> ret = new ArrayList<float[]>();
		for (float[] vec : data) {
			float sum = 0;
			for (float s : vec)
				sum += s * s;
			sum = (float) Math.sqrt(sum);
			for (int i = 0; i < vec.length; i++) {
				vec[i] = vec[i] / sum;
			}
			ret.add(vec);
		}
		return ret;
	}
	
	public static float median(float[] col)
	{
		return findPos(col,0, col.length-1, col.length/2);
	}
	 /**
     * 使用快速排序，查找排序后位置在point处的值
     * 比Array.sort()后去对应位置值，大约快30%
     * @param data 数据
     * @param low 参加排序的最低点
     * @param high 参加排序的最高点
     * @param point 位置
     * @return
     */
    private static float findPos(float[] data,int low,int high,int point){
        int lowt=low;
        int hight=high;
        float v = data[low];
        ArrayList<Integer> same = new ArrayList<Integer>((int)((high-low)*0.25));
        while(low<high){
            while(low<high&&data[high]>=v){
                if(data[high]==v){
                    same.add(high);
                }
                high--;
            }
            data[low]=data[high];
            while(low<high&&data[low]<v)
                low++;
            data[high]=data[low];
        }
        data[low]=v;
        int upper = low+same.size();
        if (low<=point&&upper>=point) {
            return v;
        }
        
        if(low>point){
            return findPos(data, lowt, low-1, point);
        }
        
        int i=low+1;
        for(int j:same){
            if(j<=low+same.size())
                continue;
            while(data[i]==v)
                i++;
            data[j]=data[i];
            data[i]=v;
            i++;
        }
        
        return findPos(data, low+same.size()+1, hight, point);
    }
    /**
     * 在max和min表示的超矩形中的点和点a的最小距离（欧氏距离）
     * 需要好好理解一下；其余的情况为何不加？ 被包括了！！！
     * @param a 点a
     * @param max 超矩形各个维度的最大值
     * @param min 超矩形各个维度的最小值
     * @return 超矩形中的点和点a的最小距离
     * 
     * 以后可以考虑： 曼哈顿距离 abs(x1[0]-x2[0])+ abs(x1[1]-x2[1])+ ....+...
     */
   public static float minDistance(float[] a,float[] max,float[] min){
	   float sum = 0;
        for(int i=0;i<a.length;i++){
            if(a[i]>max[i])
                sum += Math.pow(a[i]-max[i], 2);
            else if (a[i]<min[i]) {
                sum += Math.pow(min[i]-a[i], 2);
            }
        }
        
        return sum;
    }
    
    //all range for each dimension.
    public static float[][] maxmin(List<float[]>data, int dimension)
    {
    	float[][] max_min = new float[2][dimension];
    	for(int i=0;i<dimension;i++)
    	{
    		max_min[0][i] = data.get(0)[i];
    		max_min[1][i] = data.get(0)[i];
    		
    		for(int j=1; j<data.size();j++)
    		{
    			float[] d = data.get(j);
    			if(d[i]<max_min[0][i]) //i-dimension min
    			{
    				max_min[0][i] = d[i];
    			}
    			else
    			{
    				if(d[i]> max_min[1][i])
    				{
    					max_min[1][i] = d[i];
    				}
    			}
    		}
    	}
    	
    	return max_min;
    }
    
}
