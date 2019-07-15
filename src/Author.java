/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.ArrayList;
import java.util.List;

public class Author{
	private List<PublicationRecord> listPublications;		// The list of publications by this author
	private List<String> listAliasNames;					// List of equivalent names of this author
	private List<String> listURLs;							// List of URLs associated with this author

	public Author(){
		listPublications = new ArrayList<PublicationRecord>();
		listAliasNames = new ArrayList<String>();
		listURLs = new ArrayList<String>();
	}

	// getters
	public List<PublicationRecord> getListPublications(){
		return listPublications;
	}

	public List<String> getListAliasNames(){
		return listAliasNames;
	}

	public List<String> getListURLs(){
		return listURLs;
	}

	public void display(){
		System.out.println("Author:-----------");

		for(String s : listAliasNames)
			System.out.println("Name(s)" + s);

		for(PublicationRecord p : listPublications)
			System.out.println("Publication(s): " + p.getTitle());

		System.out.println("------------------");
		System.out.println();
	}

	public boolean isSameAuthor(String val){

		for(String s : listAliasNames)
			if(s.equals(val))
				return true;

		return false;
	}

}