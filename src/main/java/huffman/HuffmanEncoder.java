package huffman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * 
 * @author iterate7
 * @date 2018/05/30
 * @param <T>
 */
public class HuffmanEncoder<T> {

	public HuffmanEncoder() {
	}

	/**
	 * nodes to be processed.
	 */
	private TreeSet<HuffmanNode> set = new TreeSet<HuffmanNode>();

	/**
	 * 1. poll top-2 nodes.
	 * 2. merge them and generate one parent node.
	 * 3. insert parent node.
	 * @param neurons
	 */
	public void make(Collection<HuffmanNode> neurons) {
		set.addAll(neurons);
		while (set.size() > 1) {
			merger();
		}
	}

	
	/**
	 * 1. poll the minimum two nodes.
	 * 2. merge them and generate one parent which has its children's frequency sum.
	 * 3. set the left code of child as 0; set the right code of child as 1.
	 * 4. add the parent node into sorted collections by frequency. 
	 * 
	 */
	private void merger() {

		
		/** parent node has no content except leaf node. **/
		HuffmanNode hn = new HuffmanNode(); 
		
		HuffmanNode min1 = set.pollFirst();
		HuffmanNode min2 = set.pollFirst();
		hn.setFrequence(min1.getFrequence() + min2.getFrequence());
		//hn.setChars(min1.getChars()+"-"+min2.getChars());
		min1.setParent(hn);
		min2.setParent(hn);
		min1.setCode(0);
		min2.setCode(1);
		set.add(hn);
	}
	
	/**
	 * get the word-frequency map.
	 * @param charArray
	 * @return
	 */
	public static Map<Character, Integer> statistics(char[] charArray) {  
	    Map<Character, Integer> map = new HashMap<Character, Integer>(0);  
	    for (char c : charArray) {  
	        map.put(c, map.getOrDefault(c, 0)+1);
	    }  
	    return map;  
	}  
	
	/**
	 * encode based on frequency.
	 * algorithm:
	 * 1. make the huffman tree
	 * 2. encode 0 as left, 1 as right
	 * 3. transfer the tree from leaf to root to get the path-code as encoding.
	 * 
	 * @param item2Fre
	 * @return
	 */
	public Map<T,String> encode(Map<T,Integer> item2Fre)
	{
		Map<T,String> result = new HashMap<T, String>(0);
		
		List<HuffmanNode> allLeafNodes = new ArrayList<HuffmanNode>(); 
		
		for(T c: item2Fre.keySet())
		{
			HuffmanNode node = new HuffmanNode((String)(c+""));
			node.setFrequence(item2Fre.get(c));
			node.setChars(c+"");
			
			allLeafNodes.add(node);
			System.out.println(node.toString());
		}
		
		//encode the nodes 
		HuffmanEncoder he = new HuffmanEncoder();
		he.make(allLeafNodes);
		
		HashMap<String,String> char2Codes = new HashMap<String,String>(0);
		for(HuffmanNode node: allLeafNodes)
		{
			String c = (String)node.getChars();
			int fre = node.getFrequence();
			
			//transfer from leaf the root
			StringBuffer sb = new StringBuffer();
			HuffmanNode temp = node;
			while(temp.getParent()!=null)
			{
				sb.append(temp.getCode());
				temp = temp.getParent();
			}
			System.out.println(c+","+fre+","+sb.reverse());
			result.put((T)node.getChars(), sb.reverse().toString());
		}
		
		return result;
	}
	
	public static void main(String args[])
	{
//		String s = "Hailing the profound friendship between the two countries, Xi said China and Namibia stood side by side in their struggles against imperialism and colonialism in the past, and supported each other in their nation building.";
//		Map<Character,Integer> c2F = statistics(s.toCharArray());
		
		//data
		Map<String,Integer> item2Fre = new HashMap<String,Integer>(0);
		item2Fre.put("we", 15);
		item2Fre.put("love", 8);
		item2Fre.put("beatiful", 6);
		item2Fre.put("girl", 5);
		item2Fre.put("with", 3);
		item2Fre.put("you", 1);
		
		HuffmanEncoder<String> encoder = new HuffmanEncoder<String>();
		
		//encode
		Map<String,String> item2Code = encoder.encode(item2Fre);
		
		//print result
		for(String k: item2Code.keySet())
		{
			System.out.println(k+","+item2Code.get(k));
		}
	 
	}

}