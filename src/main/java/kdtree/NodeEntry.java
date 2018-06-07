package kdtree;


public class NodeEntry implements Comparable<NodeEntry> {
    public KDTreeNode name;
    public float score;
    public boolean isconcrete = true;
     
        public NodeEntry(KDTreeNode name, float score) {
        	   this.name = name;
        this.score = score;
    }
 

    @Override
    public String toString() {
        return this.name +" "+((int) (score*100))/100.00;
    }

    public int compareTo(NodeEntry o) {
        if (this.score > o.score) {
            return 1;
        } else {
            return -1;
        }
    }

}