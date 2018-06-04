package kdtree;

import java.util.ArrayList;
import java.util.List;

public class KDTree {

	public KDTreeNode root = null;
	
	private int totalDimension = -1;
	public KDTree() {
	}
	
	public void build(List<float[]> data)
	{
		
		totalDimension = data.get(0).length;
	
		root = new KDTreeNode();
		root = createNode(root,data,totalDimension);
		
		return ;
		
	}
	
	/**
	 * 
	 * @param node 为node构建数据和关系
	 * @param data 匹配的数据
	 * @param m 数据的维度
	 */
	public KDTreeNode createNode(KDTreeNode node, List<float[]> data, int m)
	{
		if(data==null || data.isEmpty())
		{
			return null;
		}
		//不需要切分了
		if(data.size()==1) 
		{
			node.isLeaf = true;
			node.left = node.right = null;
			node.value = data.get(0);
		}
		
		//选择特征，方差最大的，区分大； 可以参考决策树
		node.partitionValue = -1;
		float var = -1;
		float tmpvar = 0;
		for(int i=0;i<m;i++)
		{
			tmpvar = KDTreeMaths.variance(KDTreeMaths.getCol(data, i));
			if(tmpvar>var)
			{
				var = tmpvar;
				node.partitionDimension = i;
			}
		}
		
		//var=0, all same
		if(var == 0)
		{
			node.isLeaf = true;
			node.value = data.get(0);
			return node;
		}
			
		//分隔数据构建leftNode，rightNode
		node.partitionValue = KDTreeMaths.median(KDTreeMaths.getCol(data,node.partitionDimension));
		float[][] maxmin = KDTreeMaths.maxmin(data, this.totalDimension);
		node.min = maxmin[0];
		node.max = maxmin[1];
		
		List<float[]> left = new ArrayList<float[]>();
		List<float[]> right = new ArrayList<float[]>();
		for(float[] item: data)
		{
			if (item[node.partitionDimension] < node.partitionValue) {
				left.add(item);
			} else {
				right.add(item);
			}
		}
		KDTreeNode leftNode = new KDTreeNode();
		KDTreeNode rightNode = new KDTreeNode();
		
		node.left = createNode(leftNode, left, m);;
		node.right = createNode(rightNode,right,m);
		
		return node;
		
	}
	

	
}
