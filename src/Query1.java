/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import java.util.*;

public class Query1 extends Query{
	private boolean byPublicationTitle;
	private boolean sortYear;

	public Query1(DBLPQueryEngine aEngine, boolean aByTitle, String queryString, boolean aSortYear){
		super(aEngine, queryString);
		this.byPublicationTitle = aByTitle;
		this.sortYear = aSortYear;
	}

	//overriding abstract methods
	public void clearPreviousData(){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();

		getEngine().getListPublicationRecord().clear();
		getEngine().getListPersonRecord().clear();
	}

	public void makeNewData(){
		if(byPublicationTitle)
			makeNewDataByPublicationTitle();
		else
			makeNewDataByAuthorName();
	}

	public void makeNewDataByPublicationTitle(){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();
		String filename = getEngine().getFilename();
		RecordScanner recordScanner = getEngine().getRecordScanner();
		OutputBox outputBox = getEngine().getOutputBox();

		queryString = queryString.toLowerCase();
		ArrayList<String> queryStringList = new ArrayList<String>(Arrays.asList(queryString.split(" ")));
			
		List<String> frequentWords = getEngine().getFrequentWords();
		for(String s : frequentWords){
			while(queryStringList.contains(s)){
				queryStringList.remove(s);
			}
		}

		recordScanner.setRecordScannerListener(new RecordScannerListener(){
			public void receivePublicationRecord(PublicationRecord publicationRecord){
				String tempString = publicationRecord.getTitle().toLowerCase();
				ArrayList<String> tempStringList = new ArrayList<String>(Arrays.asList(tempString.split(" ")));
				for(String s : tempStringList){
					if (queryStringList.contains(s)) {
						listPublicationRecord.add(new PublicationRecord(publicationRecord));
						break;	
					}
				}
			}
			public void receivePersonRecord(PersonRecord personRecord){}

		});

		ComparatorByRelevance comparatorRelevance = new ComparatorByRelevance(queryStringList);
		ComparatorByYear comparatorYear = new ComparatorByYear();

		outputBox.showLoadingBar();
		recordScanner.startScan(filename);
		//Collections.sort(listPublicationRecord, comparator);
		PublicationRecord.mergeSort(listPublicationRecord, 0, listPublicationRecord.size()-1, sortYear?comparatorYear:comparatorRelevance);
		Collections.reverse(listPublicationRecord);
		outputBox.showPublications(listPublicationRecord);
	}

	public void makeNewDataByAuthorName(){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();
		String filename = getEngine().getFilename();
		RecordScanner recordScanner = getEngine().getRecordScanner();
		OutputBox outputBox = getEngine().getOutputBox();

		//final String queryString = aQueryString.toLowerCase();
		queryString = queryString.toLowerCase();
		List<String> queryStringList = new ArrayList<String>(Arrays.asList(queryString.split(" ")));
		
		recordScanner.setRecordScannerListener(new RecordScannerListener(){

			public void receivePublicationRecord(PublicationRecord publicationRecord){}

			public void receivePersonRecord(PersonRecord personRecord){
				for(String s : personRecord.getListNames()){
					if(queryString.equals(s.toLowerCase())){
						//System.out.println(personRecord);
						listPersonRecords.add(new PersonRecord(personRecord));
						break;
					}
				}
			}
		});

		outputBox.showLoadingBar();
		recordScanner.startScan(filename);
		if(listPersonRecords.size() == 0){
			PersonRecord tempPerson = new PersonRecord();
			tempPerson.getListNames().add(queryString);
			listPersonRecords.add(tempPerson);
		}
		
		List<String> listAuthorNames = new ArrayList<String>();
		for(PersonRecord p : listPersonRecords)
			for(String s : p.getListNames())
				listAuthorNames.add(s.toLowerCase());

		
		createPublications(queryStringList, listAuthorNames);
	}

	private void addWords(List<String> arr, List<String> a){
		for(String s : a){
			String[] temp = s.split(" ");
			for(String x : temp){
				arr.add(x.toLowerCase());
			}
		}
	}

	private void createPublications(List<String> queryStringList, List<String> listAuthorNames){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();
		String filename = getEngine().getFilename();
		RecordScanner recordScanner = getEngine().getRecordScanner();
		OutputBox outputBox = getEngine().getOutputBox();

		recordScanner.setRecordScannerListener(new RecordScannerListener(){
			public void receivePublicationRecord(PublicationRecord publicationRecord){
				List<String> tempAuthorList = new ArrayList<String>(publicationRecord.getAuthorNameList());
				int flag = 0;
				for(String s : listAuthorNames){
					for(String x : tempAuthorList){
						if(s.equals(x.toLowerCase())){
							flag = 1;
							listPublicationRecord.add(new PublicationRecord(publicationRecord));
							break;
						}
					}
				}
				if(flag == 0){
					List<String> words = new ArrayList<String>();
					List<String> tempRecordWords = new ArrayList<String>();
					addWords(words, listAuthorNames);
					addWords(tempRecordWords, publicationRecord.getAuthorNameList());
					for(String s : words){
						if(tempRecordWords.contains(s)){
							listPublicationRecord.add(new PublicationRecord(publicationRecord));
							//System.out.println(publicationRecord);
							break;
						}
					}
							
				}
			}
			public void receivePersonRecord(PersonRecord personRecord){}

		});
		

		ComparatorByRelevanceAuthor comparatorRelevanceAuthor = new ComparatorByRelevanceAuthor(listAuthorNames);
		ComparatorByYear comparatorYear = new ComparatorByYear();

		recordScanner.startScan(filename);
		//System.out.println(listPublicationRecord);
		PublicationRecord.mergeSort(listPublicationRecord, 0, listPublicationRecord.size()-1, sortYear?comparatorYear:comparatorRelevanceAuthor);
		Collections.reverse(listPublicationRecord);
		outputBox.showPublications(listPublicationRecord);
	}
}