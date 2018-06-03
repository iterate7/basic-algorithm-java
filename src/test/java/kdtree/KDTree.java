package kdtree;

import java.util.ArrayList;
import java.util.List;

public class KDTree {

	public static void main(String[] args) {
		KDTree kdt = new KDTree();
		kdt.makeData();
				
	}
	public void makeData()
	{
		float[] t1 = new float[] {2,3};
		float[] t2 = new float[] {5,4};
		float[] t3 = new float[] {9,6};
		float[] t4 = new float[] {4,7};
		float[] t5 = new float[] {8,1};
		float[] t6 = new float[] {7,2};
		List<float[]> data = new ArrayList<float[]>();
		data.add(t1);	data.add(t2);	data.add(t3);
		data.add(t4);	data.add(t5);	data.add(t6);
		sortFeaturesBaseVariance(data);
	}
	
	/**
	 * 根据方差排序维度
	 * @param data
	 * @return
	 */
	public List<Integer> sortFeaturesBaseVariance(List<float[]> data )
	{
		int feature = data.get(0).length;
		for(int i=0;i<feature;i++)
		{
			float[] col = getCol(data,i);
			float value = variance(col);
			System.out.println("col-"+i+",variance:"+value);
		}
		return null;
	}
	//获取某一列的数据
	private float[] getCol(List<float[]>data, int col)
	{
		float[] colData = new float[data.size()];
		for(int i=0;i<data.size();i++)
		{
			colData[i] = data.get(i)[col];
		}
		return colData;
	}
	//方差
	private float variance(float[] item)
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
	//train
	public KDTreeNode train(List<float[]> instance)
	{
		KDTreeNode root = new KDTreeNode();
		return root;
	}
	//search tree
	public void search(float[] item)
	{
		
	}

}
