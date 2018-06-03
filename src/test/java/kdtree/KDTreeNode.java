package kdtree;

import java.util.List;

public class KDTreeNode {
	
	/* left */
	private KDTreeNode left;
	/* right */
	private KDTreeNode right;
	
	/* range as rectangle */
	private float[] max;
	/* min */
	private float[] min;
	
	/* 第几维度 */ 
	private int dimensionNo = -1;
	/* 数据 */
	private List<float[]> items;

}
