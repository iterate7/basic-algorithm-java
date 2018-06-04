package kdtree;

import java.util.Arrays;

public class KDTUtil {

	public KDTUtil() {
	}

	 
    
    public static void printRec(KDTreeNode node,int lv){
        if(!node.isLeaf){
            printRec(node.left,lv+1);
            for(int i=0;i<lv;i++)
                System.out.print("\t");
            System.out.println(node.partitionDimension+":"+node.partitionValue+","+node.value);
            printRec(node.right,lv+1);
        }else {
            for(int i=0;i<lv;i++)
                System.out.print("\t");
            StringBuilder s = new StringBuilder();
            s.append('(');
            for(int i=0;i<node.value.length;i++){
                s.append((node.value[i])).append(',');
            }
            s.append((')'));
            System.out.println(s);
        }
    }
}
