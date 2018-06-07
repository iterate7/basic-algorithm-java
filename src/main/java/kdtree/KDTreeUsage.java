package kdtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;
/**
 * kdtree 不适合高维数据；
 * 
 * @author iterate7
 *
 */
public class KDTreeUsage {

	/** 最大的分叉个数**/
	private static int maxBranch = 1000;
	private static Random rnd = new Random();
	
	/** 维度的倍数，用于限制最大的回溯。 **/
	private static int timesOfDimension = 10;
	
	public KDTreeUsage() {
		
	}
	
	/**
	 * 实验1： 用kdtree，全搜索；100维； datasize:10000;iteration:10000；对比线性；结果一致。 时间比：0.21
	 * 
	 * 实验2：用kdtree，1000最大回溯；100维； datasize:10000;iteration:10000；对比线性；存在偏差了。 时间比：0.99
	 * 
	 * 实验3：用kdtree，3000最大回溯；100维； datasize:10000;iteration:10000；对比线性；基本一致。 时间比：0.35
	 * 
	 * 实验4： 用kdtree，3000最大回溯；100维； branch大小1000，排序；datasize:10000;iteration:10000；对比线性；数据一致！。 时间比：0.33
	 * 		结论： 增加了排序branch，没带来什么东西，3000的最大回溯貌似都跑完了。
	 * 
	 * 实验5：用kdtree，1000最大回溯；100维； branch大小1000，排序；datasize:10000;iteration:10000；对比线性；基本一致。 时间比：1.1
	 * 
	 * 实验6：
	 * 
	 * @param input
	 * @param tree
	 * @return
	 */
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
	        
	        float[] nearest= queryRecBaseBBF(input, distance, stack);
	        
	        return nearest==null? kdtNode.value:nearest;
	    }
	    
	    private static float[] queryRecBase(float[] input,float distance,Stack<KDTreeNode> stack){
	        float[] nearest = null;
	        KDTreeNode KDTreeNode = null;
	        float tdis;
	        float maxBackTracking = 1000;
	        
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
	                 * 否则不可能找到;
	                 * 如果维度特别大，那么相交的节点则特别多；那么子节点就会不停的加入到stack里！
	                 * 回溯的情景就特别多！
	                 */
//	            	maxBackTracking--;
//	            	if(maxBackTracking<0)
//	            		break;
	            
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
	    
	    
	    private static float[] queryRecBaseWithBackTracking(float[] input,float distance,Stack<KDTreeNode> stack){
	        float[] nearest = null;
	        KDTreeNode KDTreeNode = null;
	        float tdis;
	        float maxBackTracking = 3000;
	        
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
	                 * 否则不可能找到;
	                 * 如果维度特别大，那么相交的节点则特别多；那么子节点就会不停的加入到stack里！
	                 * 回溯的情景就特别多！
	                 */
	            	maxBackTracking--;
	            	if(maxBackTracking<0)
	            		break;
	            
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
	     * 选择优质的分叉（有的分叉和当前点最小距离更小，优先选择）
	     * 这个算法最大的问题就是搜索不到很多区域；选择最小的可能会限于局部最优。
	     * 实验1： 保留最近的分叉100个；看下效果。
	     * @param input
	     * @param distance
	     * @param stack
	     * @return
	     */
	    private static float[] queryRecBaseBBF(float[] input,float distance,Stack<KDTreeNode> stack){
	        float[] nearest = null;
	        KDTreeNode node = null;
	        float tdis;
	        float maxBackTracking = 3000;
	        TreeSet<NodeEntry> brances = new TreeSet<NodeEntry>();
	        while(stack.size()!=0)
	        {
	        	node = stack.pop();
	        	  if(node.isLeaf){
		                 tdis= KDTreeMaths.distance(input, node.value);
		                 if(tdis<distance){
		                     distance = tdis;
		                     nearest = node.value;
		                 }
		            }
	        	  else
	        	  {
	        		  double mindistance = KDTreeMaths.minDistance(input, node.max, node.min); 
	        		  brances.add(new NodeEntry(node, (float)mindistance));
	        		  if(brances.size()> maxBranch)
	        		  {
	        			  brances.pollLast();
	        		  }
	        	  }
	        }
	        
       // System.out.println(brances.size()+","+brances.first()+","+brances.last());
	        
	        while(brances.size()!=0){
	            
	        	NodeEntry ent = null;
	        	//如同基因突变的，多样性选择！
//	        	if(rnd.nextInt(10)==0)
//	        		ent = brances.pollFirst(); 
//	        	else
	        	ent = brances.pollFirst();
	        	
	        	 node = ent.name;
	            if(node.isLeaf){
	                 tdis= KDTreeMaths.distance(input, node.value);
	                 if(tdis<distance){
	                     distance = tdis;
	                     nearest = node.value;
	                 }
	            }else {
	                /*
	                 * 得到该节点代表的超矩形中点到查找点的最小距离mindistance
	                 * 如果mindistance<distance表示有可能在这个节点的子节点上找到更近的点
	                 * 否则不可能找到;
	                 * 如果维度特别大，那么相交的节点则特别多；那么子节点就会不停的加入到stack里！
	                 * 回溯的情景就特别多！
	                 */
	            	maxBackTracking--;
	            	if(maxBackTracking<0)
	            		break;
	            
	                double mindistance = KDTreeMaths.minDistance(input, node.max, node.min);
	                if (mindistance<distance) {
	                    while(!node.isLeaf){
	                        if(input[node.partitionDimension]<node.partitionValue){
	                        	brances.add(new NodeEntry(node.right,(float)mindistance));
	                        	node=node.left;
	                        }else{
	                        	brances.add(new NodeEntry(node.left,(float)mindistance));
	                        	node=node.right;
	                        }
	                    }
	                    tdis=KDTreeMaths.distance(input, node.value);
	                    if(tdis<distance){
	                        distance = tdis;
	                        nearest = node.value;
	                    }
	                    if(brances.size()>maxBranch)
	                    {
	                    	brances.pollLast();
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
	   private static float[] nearestBaseBruteForce(float[] input,List<float[]> data){
	    	float[] nearest=null;
	    	float dis = Float.MAX_VALUE;
	    	float tdis;
	        for(int i=0;i<data.size();i++){
	            tdis = KDTreeMaths.distance(input, data.get(i));
	            if(tdis<dis){
	                dis=tdis;
	                nearest = data.get(i);
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
	    /*
	    datasize:100000;iteration:100000, dimension=2
	    kdtree:324
	    linear:84936
	    linear/kdtree:262.14814814814815
	    
	    
	    
	    datasize:100000;iteration:1000000; dimension:3
		kdtree:2926
		linear:1591136
		linear/kdtree:543.7922077922078
	    */
	    public static void performance(int iteration,int datasize,int dimension){
	        
	        int num = datasize;
	        float[][] input = new float[num][dimension];
	        
	        List<float[]> data = new ArrayList<float[]>();
	        for(int i=0;i<num;i++){
	        	for(int k = 0;k<dimension;k++)
	            input[i][k]=(float)Math.random()*num;
	        	
	            data.add(input[i]);
	        }
	       data = KDTreeMaths.normal(data);
	        KDTree tree=new KDTree();
	       tree.build(data);
	        
	        //开始对比
	        List<float[]> querys = new ArrayList<float[]>();
	        float[][] queryData = new float[iteration][dimension];
	        for(int i=0;i<iteration;i++){
	        	for(int k = 0;k<dimension;k++)
	        		queryData[i][k]=(float)(Math.random());
	        	querys.add(queryData[i]);
	        }
	        querys = KDTreeMaths.normal(querys);
	        
	        long start = System.currentTimeMillis();
	        for(int i=0;i<iteration;i++){
	            float[] result = query(querys.get(i),tree);
	            if(i<10)
	            System.out.println(
	            		//"kdtree:\tquery:"+Arrays.toString(queryData[i])+"\t"+   Arrays.toString(result)+
	            	",distance:"+KDTreeMaths.distance(queryData[i], result));
	            
	        }
	        long timekdtree = System.currentTimeMillis()-start;
	        System.out.println();
	        start = System.currentTimeMillis();
	        for(int i=0;i<iteration;i++){
	            float[] result = nearestBaseBruteForce(querys.get(i),data);
	            if(i<10)
	            System.out.println(
	            		//"linear:\tquery:"+Arrays.toString(queryData[i])+"\t"+   Arrays.toString(result)+
	            		",distance:"+KDTreeMaths.distance(queryData[i], result));
	        }
	        long timelinear = System.currentTimeMillis()-start;
	        
	        System.out.println("datasize:"+datasize+";iteration:"+iteration);
	        System.out.println("kdtree:"+timekdtree);
	        System.out.println("linear:"+timelinear);
	        System.out.println("linear/kdtree:"+(timelinear*1.0/timekdtree));
	    }
	    
//	    /**
//	     * 运行100000次，看运行结果是否和线性查找相同
//	     */
//	    public static void correct(int dimension){
//	        int count = 1000;
//	        while(count-->0){
//	            int num = 100;
//	            float[][] input = new float[num][dimension];
//	            for(int i=0;i<num;i++){
//	                input[i][0]=(float) (Math.random()*10);
//	                input[i][1]=(float) (Math.random()*10);
//	            }
//	            
//	            List<float[]> data = new ArrayList<float[]>();
//		        for(int i=0;i<num;i++){
//		        	for(int k = 0;k<dimension;k++)
//		            input[i][k]=(float)Math.random()*num;
//		            data.add(input[i]);
//		        }
//		        KDTree tree=new KDTree();
//		        tree.build(data);
//		        //开始对比
//		        float[][] queryData = new float[num][dimension];
//		        for(int i=0;i<num;i++){
//		        	for(int k = 0;k<dimension;k++)
//		        		queryData[i][k]=(float)(Math.random()*num*1.5);
//		        }
//		        
//		        for(float [] searchVec: queryData)
//		        {
//		            float[] result = query(searchVec,tree);
//		            float[] result1 = nearestBaseBruteForce(searchVec,input);
//		            if (result[0]!=result1[0]||result[1]!=result1[1]) {
//		                System.out.println("wrong");
//		                break;
//		            }
//		        }
//	        }
//	    }
//	    
	    public static void main(String[] args) {
	        //correct();
	    	int iterate = 10000;
	    	int dataSize = 10000;
	    	int dimension = 100;
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
