/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/
import java.util.*;

public class ComparatorByYear implements Comparator<PublicationRecord>{

	public int compare(PublicationRecord p1, PublicationRecord p2){
		return p1.getYear().compareTo(p2.getYear());
	}

}