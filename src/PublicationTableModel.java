/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/
	
import java.util.*;
import javax.swing.table.*;

public class PublicationTableModel extends AbstractTableModel{
		private List<PublicationRecord> listPublications;
		
		public PublicationTableModel(List<PublicationRecord> aListPublications){
			listPublications = aListPublications;
		}

		//override abstract methods
		public int getRowCount(){
			return listPublications.size();
		}

		public int getColumnCount(){
			return 8;
		}

		public Object getValueAt(int row, int column){
			//<s_no>, <authors>, <title>, <pages>, <year>, <volume>, <journal/Booktitle>,<url>
			//Format to display
			PublicationRecord record = listPublications.get(row);
			switch(column){
				case 0:	return row+1;
						//break;
				case 1:	return record.getAuthorNameList();
						//break;
				case 2:	return record.getTitle();
						//break;
				case 3:	return record.getPages();
						//break;
				case 4:	return record.getYear();
						//break;
				case 5:	return record.getVolume();
						//break;
				case 6:	return record.getJournalBookTitle();
						//break;
				case 7:	return "url";
						//break;
				default:return "not found";
						//break;
			}
		}

		public String getColumnName(int column){
			switch(column){
				case 0:	return "S. No.";
						//break;
				case 1:	return "Authors";
						//break;
				case 2:	return "Title";
						//break;
				case 3:	return "Pages";
						//break;
				case 4:	return "Year";
						//break;
				case 5:	return "Volume";
						//break;
				case 6:	return "Journal/Booktitle";
						//break;
				case 7:	return "URL";
						//break;
				default:return "not found";
						//break;
			}
		}
	}