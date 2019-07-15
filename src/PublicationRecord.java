/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class PublicationRecord extends Record{
	private String author;					// Specifies the author names of the publication record
	private String title;					// Specifies the title of the publication
	private String pages;					// Specifies the number of pages of the publication
	private String year;					// Specifies the year of publication
	private String volume;					// Specifies the volume of publication
	private String journalBookTitle;		// Specifies whether journal/bookTitle
	private List<String> listAuthorNames;	// The list of authors who have authored this publication

	public static void mergeSort(List<PublicationRecord> list, int low, int high, Comparator<PublicationRecord> comparator){
		if(low<high){
			int mid = (low+high)/2;
			mergeSort(list, low, mid, comparator);
			mergeSort(list, mid+1, high, comparator);
			merge(list, low, mid, high, comparator);
		}
	}

	public static void merge(List<PublicationRecord> list, int p, int q, int r, Comparator<PublicationRecord> comparator){
		List<PublicationRecord> temp1 = new ArrayList<PublicationRecord>();
		for(int i=p; i<=q; i++)
			temp1.add(list.get(i));

		List<PublicationRecord> temp2 = new ArrayList<PublicationRecord>();
		for(int i=q+1; i<=r; i++)
			temp2.add(list.get(i));

		int n1 = q-p+1;
		int n2 = r-q;

		int i = p;
		int i1 = 0, i2 = 0;
		while(i1<n1 && i2<n2){
			
			if( comparator.compare(temp1.get(i1), temp2.get(i2))<0 ){
				list.set(i, temp1.get(i1));
				i1++;
			}else{
				list.set(i, temp2.get(i2));
				i2++;
			}

			i++;	
		}

		if(i1==n1){
			while(i2<n2){
				list.set(i, temp2.get(i2));
				i2++;
				i++;
			}
		}else{
			while(i1<n1){
				list.set(i, temp1.get(i1));
				i1++;
				i++;
			}
		}
		
	}

	PublicationRecord(String authorParam, String titleParam, String pagesParam, String yearParam, String volumeParam, String journalBookTitleParam){
		this.author = authorParam;
		this.title = titleParam;
		this.pages = pagesParam;
		this.year = yearParam;
		this.volume = volumeParam;
		this.journalBookTitle = journalBookTitleParam;

		this.listAuthorNames = new ArrayList<String>();
	}

	// overloaded constructor
	PublicationRecord(PublicationRecord pRecord){
		this.author = pRecord.getAuthorString();
		this.title = pRecord.getTitle();
		this.pages = pRecord.getPages();
		this.year = pRecord.getYear();
		this.volume = pRecord.getVolume();
		this.journalBookTitle = pRecord.getJournalBookTitle();

		this.listAuthorNames = new ArrayList<String>();
		for(String s : pRecord.getAuthorNameList())
			this.listAuthorNames.add(s);
	}

	public void display(){
		System.out.println("Publication:------");

		for(String s : listAuthorNames)
			System.out.println("Author(s): " + s);

		System.out.println("Title: " + getTitle());
		System.out.println("Pages: " + getPages());
		System.out.println("Year: " + getYear());
		System.out.println("Volume: " + getVolume());
		System.out.println("Journal/BookTitle: " + getJournalBookTitle());

		System.out.println("------------------");
		System.out.println();
	}

	public String toString(){
		String str = "Publication:------";

		for(String s : listAuthorNames)
			str = str + ("Author(s): " + s);

		str+=("Title: " + getTitle());
		str+=("Pages: " + getPages());
		str+=("Year: " + getYear());
		str+=("Volume: " + getVolume());
		str+=("Journal/BookTitle: " + getJournalBookTitle());

		str+=("------------------");
		//System.out.println();
		str+=("\n");
		return str;
	}

	// setters
	public void setAuthorString(String authorStringParam){
		author = authorStringParam;
	}

	public void setTitle(String titleParam){
		title = titleParam;
	}

	public void setPages(String pagesParam){
		this.pages = pagesParam;
	}

	public void setYear(String yearParam){
		this.year = yearParam;
	}

	public void setVolume(String volumeParam){
		this.volume = volumeParam;
	}

	public void setJournalBookTitle(String journalBookTitleParam){
		this.journalBookTitle = journalBookTitleParam;
	}

	public void setListAuthors(List<String> listAuthorsParam){
		this.listAuthorNames = listAuthorsParam;
	}

	// getters
	public String getAuthorString(){
		return author;
	}

	public String getTitle(){
		return title;
	}

	public String getPages(){
		return pages;
	}

	public String getYear(){
		return year;
	}

	public String getVolume(){
		return volume;
	}

	public String getJournalBookTitle(){
		return journalBookTitle;
	}

	public List<String> getAuthorNameList(){
		return listAuthorNames;
	}
}