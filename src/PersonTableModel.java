/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.*;
import javax.swing.table.*;

public class PersonTableModel extends AbstractTableModel{
		private List<String> listStringToDisplay;
		
		public PersonTableModel(List<String> aListStringToDisplay){
			listStringToDisplay = aListStringToDisplay;
		}

		//override abstract methods
		public int getRowCount(){
			return listStringToDisplay.size();
		}

		public int getColumnCount(){
			return 2;
		}

		public Object getValueAt(int row, int column){
			//<s_no>, <authors>, <title>, <pages>, <year>, <volume>, <journal/Booktitle>,<url>
			//Format to display
			String name = listStringToDisplay.get(row);
			switch(column){
				case 0:	return row+1;
						//break;
				case 1:	return listStringToDisplay.get(row);
						//break;
				default:return "not found";
						//break;
			}
		}

		public String getColumnName(int column){
			switch(column){
				case 0:	return "S. No.";
						//break;
				case 1:	return "Author Name";
						//break;
				default:return "not found";
						//break;
			}
		}
	}