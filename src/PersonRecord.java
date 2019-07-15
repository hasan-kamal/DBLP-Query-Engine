/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.ArrayList;
import java.util.List;

public class PersonRecord extends Record{
	private List<String> listNames;			// The list of names associated with this person record

	public PersonRecord(){
		listNames = new ArrayList<String>();
	}
	
	public PersonRecord(PersonRecord personRecord){
		listNames = new ArrayList<String>(personRecord.getListNames());
	}

	public List<String> getListNames(){
		return listNames;
	}
	
	public String toString(){
		String s = "";
		for(String x : listNames){
			s = s + x + "\n";
		}
		return s;
	}
}