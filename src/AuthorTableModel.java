/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.*;
import javax.swing.table.*;

public class AuthorTableModel extends AbstractTableModel{
	private List<PublicationRecord> listPublications;
	
	public AuthorTableModel(List<PublicationRecord> aListPublications){
		listPublications = aListPublications;
	}

	//override abstract methods
	public int getRowCount(){
		return 0;
	}

	public int getColumnCount(){
		return 0;
	}

	public Object getValueAt(int row, int column){
		return null;
	}
}