/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.*;

public class ComparatorByRelevance implements Comparator<PublicationRecord>{

	private ArrayList<String> queryStringList;

	public ComparatorByRelevance(ArrayList<String> aQueryListString){
		queryStringList = aQueryListString;
	}

	private int getOrder(int s1, int s2){
		return ((s1 < s2) ? 1 : ((s1 == s2) ? 0 : -1));
	}

	public int compare(PublicationRecord p1, PublicationRecord p2){
		ArrayList<String> stringList1 = new ArrayList<String>(Arrays.asList(p1.getTitle().toLowerCase().split(" ")));
		ArrayList<String> stringList2 = new ArrayList<String>(Arrays.asList(p2.getTitle().toLowerCase().split(" ")));
		int count1 = 0;
		int count2 = 0;
		for(String s : queryStringList){
			if(stringList1.contains(s)){
				count1 = count1 + 1;
			}
			if(stringList2.contains(s)){
				count2 = count2 + 1;
			}
		}
		return (count1 > count2) ? 1 : ((count1 < count2) ? -1 : getOrder(stringList1.size(), stringList2.size()));		
	}

}