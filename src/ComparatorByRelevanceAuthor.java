/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.*;

public class ComparatorByRelevanceAuthor implements Comparator<PublicationRecord>{

	private List<String> listAuthorNames;

	public ComparatorByRelevanceAuthor(List<String> aListAuthorNames){
		this.listAuthorNames = new ArrayList<String>(aListAuthorNames);
	}

	private void addWords(List<String> arr, List<String> a){
		for(String s : a){
			String[] tempName = s.split(" ");
			for(String x : tempName){
				arr.add(x.toLowerCase());
			}
		}
		return;
	}

	public int compare(PublicationRecord p1, PublicationRecord p2){
		//List<String> listAuthorNames;
		List<String> authorList1 = new ArrayList<String>(p1.getAuthorNameList());
		List<String> authorList2 = new ArrayList<String>(p2.getAuthorNameList());
		int authorList1Size = authorList1.size(), authorList2Size = authorList2.size();
		for(int i=0;i<authorList1Size;i++)
			authorList1.set(i,authorList1.get(i).toLowerCase());
		for(int i=0;i<authorList2Size;i++)
			authorList2.set(i,authorList2.get(i).toLowerCase());
		int count1 = 0, count2 = 0;
		for(String s : listAuthorNames){
			if(authorList1.contains(s)){
				count1++;
			}
			if(authorList2.contains(s)){
				count2++;
			}
		}
		if(count1 > count2)
			return 1;
		else if(count1 < count2)
			return -1;
		else{
			count1 = count2 = 0;
			List<String> authorWords1 = new ArrayList<String>();
			List<String> authorWords2 = new ArrayList<String>();
			List<String> words = new ArrayList<String>();
			addWords(authorWords1, authorList1);
			addWords(authorWords2, authorList2);
			addWords(words, listAuthorNames);
			for(String s : words){ // assuming no word is repeated twice in the search
				if(authorWords1.contains(s)){
					count1++;
				}
				if(authorWords2.contains(s)){
					count2++;
				}
			}
			if(count1 >= count2){
				return 1;
			}
			else{
				return -1;
			}
		}
	}

}