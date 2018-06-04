package kdtree;

import java.util.List;

/**
 * data structure:
 * 1. leftNode
 * 2. rightNode
 * 3. dimension
 * 4. partitionValue
 * 5. max/min(rectangle)
 * 6. isLeaf
 * 7. if(isLeaf) dataItems(instances)
 * 
 * @author iterate7
 *
 */
public class KDTreeNode {

	//feature
	public int dimension;
	
	//the value split privolot base dimension
	public float partitionValue;
	
	public boolean isLeaf;
	
	public List<float[]> dataItems;
	
	public KDTreeNode left;
	public KDTreeNode right;
	
	//decide the range!
	public float[] min;
	
	public float[] max;
	
	public KDTreeNode() {
	}

	public static void main(String[] args) {

	}

}
