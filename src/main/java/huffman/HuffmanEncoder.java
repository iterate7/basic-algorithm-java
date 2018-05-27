package huffman;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

	//鏋勫缓huffman鏍戠殑杩囩▼锛佹瘡涓妭鐐规壘鍒扮埗浜茶妭鐐广��
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
	    Map<Character, Integer> map = new HashMap<Character, Integer>();  
	    for (char c : charArray) {  
	        map.put(c, map.getOrDefault(c, 0)+1);
	    }  
	    return map;  
	}  
	
	/**
	 * encode based on frequency.
	 * @param item2Fre
	 * @return
	 */
	public Map<T,String> encode(Map<T,Integer> item2Fre)
	{
		Map<T,String> result = new HashMap<T, String>();
		
		List<HuffmanNode> allLeafNodes = new ArrayList<HuffmanNode>(); 
		
		for(T c: item2Fre.keySet())
		{
			HuffmanNode node = new HuffmanNode((String)(c+""));
			node.setFrequence(item2Fre.get(c));
			node.setChars(c+"");
			
			allLeafNodes.add(node);
			System.out.println(node.toString());
		}
		//鎴戜滑鐨勭洰鏍囧氨鏄牴鎹笉鍚岀殑fre锛屾眰瑙ｅ嚭鍙跺瓙鑺傜偣鐨刢ode鍊�
		HuffmanEncoder he = new HuffmanEncoder();
		he.make(allLeafNodes);
		
		HashMap<String,String> char2Codes = new HashMap<String,String>();
		for(HuffmanNode node: allLeafNodes)
		{
			String c = (String)node.getChars();
			int fre = node.getFrequence();
			
			//浠庤繖涓妭鐐逛竴鐩村線涓婅蛋锛屽緱鍒拌矾寰勶紝閫嗗悜璺緞鍒欏氨鏄痗ode
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
		Map<String,Integer> item2Fre = new HashMap<String,Integer>();
		item2Fre.put("鎴戜滑", 15);
		item2Fre.put("鍠滄", 8);
		item2Fre.put("瑙傜湅", 6);
		item2Fre.put("宸磋タ", 5);
		item2Fre.put("瓒崇悆", 3);
		item2Fre.put("涓栫晫鏉�", 1);
		
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