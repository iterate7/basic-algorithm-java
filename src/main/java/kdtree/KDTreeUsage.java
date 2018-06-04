package kdtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class KDTreeUsage {

	public KDTreeUsage() {
		
	}
	
	  public static float[] query(float[] input, KDTree tree){
	        KDTreeNode kdtNode = tree.root;
	        Stack<KDTreeNode> stack = new Stack<KDTreeNode>();
	        while(!kdtNode.isLeaf){
	            if(input[kdtNode.partitionDimension]<kdtNode.partitionValue){
	                stack.add(kdtNode.right);
	                kdtNode=kdtNode.left;
	            }else{
	                stack.push(kdtNode.left);
	                kdtNode=kdtNode.right;
	            }
	        }
	        /**
	         * 首先按树一路下来，得到一个想对较近的距离，再找比这个距离更近的点
	         */
	        float distance = KDTreeMaths.distance(input, kdtNode.value);
	        float[] nearest= queryRec(input, distance, stack);
	        return nearest==null? kdtNode.value:nearest;
	    }
	    
	    public static float[] queryRec(float[] input,float distance,Stack<KDTreeNode> stack){
	        float[] nearest = null;
	        KDTreeNode KDTreeNode = null;
	        float tdis;
	        while(stack.size()!=0){
	            KDTreeNode = stack.pop();
	            if(KDTreeNode.isLeaf){
	                 tdis= KDTreeMaths.distance(input, KDTreeNode.value);
	                 if(tdis<distance){
	                     distance = tdis;
	                     nearest = KDTreeNode.value;
	                 }
	            }else {
	                /*
	                 * 得到该节点代表的超矩形中点到查找点的最小距离mindistance
	                 * 如果mindistance<distance表示有可能在这个节点的子节点上找到更近的点
	                 * 否则不可能找到
	                 */
	                double mindistance = KDTreeMaths.minDistance(input, KDTreeNode.max, KDTreeNode.min);
	                if (mindistance<distance) {
	                    while(!KDTreeNode.isLeaf){
	                        if(input[KDTreeNode.partitionDimension]<KDTreeNode.partitionValue){
	                            stack.add(KDTreeNode.right);
	                            KDTreeNode=KDTreeNode.left;
	                        }else{
	                            stack.push(KDTreeNode.left);
	                            KDTreeNode=KDTreeNode.right;
	                        }
	                    }
	                    tdis=KDTreeMaths.distance(input, KDTreeNode.value);
	                    if(tdis<distance){
	                        distance = tdis;
	                        nearest = KDTreeNode.value;
	                    }
	                }
	            }
	        }
	        return nearest;
	    }
	    
	    /**
	     * 线性查找，用于和kdtree查询做对照
	     * 1.判断kdtree实现是否正确
	     * 2.比较性能
	     * @param input
	     * @param data
	     * @return
	     */
	    public static float[] nearestBaseBruteForce(float[] input,float[][] data){
	    	float[] nearest=null;
	    	float dis = Float.MAX_VALUE;
	    	float tdis;
	        for(int i=0;i<data.length;i++){
	            tdis = KDTreeMaths.distance(input, data[i]);
	            if(tdis<dis){
	                dis=tdis;
	                nearest = data[i];
	            }
	        }
	        return nearest;
	    }
	    
//	    /**
//	     * 运行100000次，看运行结果是否和线性查找相同
//	     */
//	    public static void correct(){
//	        int count = 100000;
//	        while(count-->0){
//	            int num = 100;
//	            double[][] input = new double[num][2];
//	            for(int i=0;i<num;i++){
//	                input[i][0]=Math.random()*10;
//	                input[i][1]=Math.random()*10;
//	            }
//	            double[] query = new double[]{Math.random()*50,Math.random()*50};
//	            
//	            KDTree tree=KDTree.build(input);
//	            double[] result = tree.query(query);
//	            double[] result1 = nearest(query,input);
//	            if (result[0]!=result1[0]||result[1]!=result1[1]) {
//	                System.out.println("wrong");
//	                break;
//	            }
//	        }
//	    }
//	    
	    public static void performance(int iteration,int datasize,int dimension){
	        
	        int num = datasize;
	        float[][] input = new float[num][dimension];
	        
	        List<float[]> data = new ArrayList<float[]>();
	        for(int i=0;i<num;i++){
	        	for(int k = 0;k<dimension;k++)
	            input[i][k]=(float)Math.random()*num;
	            data.add(input[i]);
	        }
	        
	        KDTree tree=new KDTree();
	        tree.build(data);
	        
	        
	        //开始对比
	        float[][] queryData = new float[iteration][dimension];
	        for(int i=0;i<iteration;i++){
	        	for(int k = 0;k<dimension;k++)
	        		queryData[i][k]=(float)(Math.random()*num*1.5);
	        }
	        
	        long start = System.currentTimeMillis();
	        for(int i=0;i<iteration;i++){
	            float[] result = query(queryData[i],tree);
	        }
	        long timekdtree = System.currentTimeMillis()-start;
	        
	        start = System.currentTimeMillis();
	        for(int i=0;i<iteration;i++){
	            float[] result = nearestBaseBruteForce(queryData[i],input);
	        }
	        long timelinear = System.currentTimeMillis()-start;
	        
	        System.out.println("datasize:"+datasize+";iteration:"+iteration);
	        System.out.println("kdtree:"+timekdtree);
	        System.out.println("linear:"+timelinear);
	        System.out.println("linear/kdtree:"+(timelinear*1.0/timekdtree));
	    }
	    
	    /**
	     * 运行100000次，看运行结果是否和线性查找相同
	     */
	    public static void correct(int dimension){
	        int count = 1000;
	        while(count-->0){
	            int num = 100;
	            float[][] input = new float[num][dimension];
	            for(int i=0;i<num;i++){
	                input[i][0]=(float) (Math.random()*10);
	                input[i][1]=(float) (Math.random()*10);
	            }
	            
	            List<float[]> data = new ArrayList<float[]>();
		        for(int i=0;i<num;i++){
		        	for(int k = 0;k<dimension;k++)
		            input[i][k]=(float)Math.random()*num;
		            data.add(input[i]);
		        }
		        KDTree tree=new KDTree();
		        tree.build(data);
		        //开始对比
		        float[][] queryData = new float[num][dimension];
		        for(int i=0;i<num;i++){
		        	for(int k = 0;k<dimension;k++)
		        		queryData[i][k]=(float)(Math.random()*num*1.5);
		        }
		        
		        for(float [] searchVec: queryData)
		        {
		            float[] result = query(searchVec,tree);
		            float[] result1 = nearestBaseBruteForce(searchVec,input);
		            if (result[0]!=result1[0]||result[1]!=result1[1]) {
		                System.out.println("wrong");
		                break;
		            }
		        }
	        }
	    }
	    
	    public static void main(String[] args) {
	        //correct();
	    	int iterate = 10000;
	    	int dataSize = 100000;
	    	int dimension = 10;
	         performance(iterate,dataSize,dimension);
	    //	correct(dimension);
	    }
//	 public static void performance(int iteration,int datasize){
//	        
//	        int num = datasize;
//	        float[][] input = new float[num][2];
//	        List<float[]> data = new ArrayList<float[]>();
//	        for(int i=0;i<num;i++){
//	            input[i][0]=(float)Math.random()*num;
//	            input[i][1]=(float)Math.random()*num;
//	            data.add(input[i]);
//	        }
//	       
//	        for(float [] item: data)
//	        {
//	        	System.out.println(Arrays.toString(item));
//	        }
//	        KDTree tree = new KDTree();
//	        tree.build(data);
//	        KDTUtil.printRec(tree.root,0);
//	        
//	     
//	    }

}
