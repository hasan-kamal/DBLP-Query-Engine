/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/
import java.util.*;

public class Query2 extends Query{

	public Query2(DBLPQueryEngine aEngine, String queryString){
		super(aEngine, queryString);
	}

	//overriding abstract methods
	public void clearPreviousData(){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();

		listPublicationRecord.clear();
		listPersonRecords.clear();
	}

	public void makeNewData(){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();
		String filename = getEngine().getFilename();
		RecordScanner recordScanner = getEngine().getRecordScanner();
		OutputBox outputBox = getEngine().getOutputBox();
		TreeMap<String, Integer> authorTree = getEngine().getAuthorTree();
		TreeMap<String, String> aliasMap = getEngine().getAliasMap();

		int queryNumber = Integer.parseInt(queryString);
		outputBox.showLoadingBar();
		if(!getEngine().getMapExistsAlready()){
			createMapData();
		}

		List<String> listAuthorsToDisplay = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : authorTree.entrySet()){
		    if(entry.getValue() > queryNumber){
		    	listAuthorsToDisplay.add(entry.getKey());
		    }
		 }
		outputBox.showAuthors(listAuthorsToDisplay);
	}

	private void createMapData(){
		List<PublicationRecord> listPublicationRecord = getEngine().getListPublicationRecord();
		List<PersonRecord> listPersonRecords = getEngine().getListPersonRecord();
		String filename = getEngine().getFilename();
		RecordScanner recordScanner = getEngine().getRecordScanner();
		OutputBox outputBox = getEngine().getOutputBox();
		TreeMap<String, Integer> authorTree = getEngine().getAuthorTree();
		TreeMap<String, String> aliasMap = getEngine().getAliasMap();

		recordScanner.setRecordScannerListener(new RecordScannerListener(){
				public void receivePublicationRecord(PublicationRecord publicationRecord){}
				public void receivePersonRecord(PersonRecord personRecord){
					if(!personRecord.getListNames().isEmpty())
						authorTree.put(personRecord.getListNames().get(0), 0);

					int size = personRecord.getListNames().size();
					for(int i=1;i<size;i++){
						aliasMap.put(personRecord.getListNames().get(i),personRecord.getListNames().get(0));
					}
				}
			});

			outputBox.showLoadingBar();
			recordScanner.startScan(filename);
			recordScanner.setRecordScannerListener(new RecordScannerListener(){
				public void receivePublicationRecord(PublicationRecord publicationRecord){
					for(String s : publicationRecord.getAuthorNameList()){
						if(aliasMap.containsKey(s)){
							String currentAuthor = aliasMap.get(s);
							int currentValue = authorTree.get(currentAuthor);
							authorTree.put(currentAuthor, currentValue + 1);
						}
						else if(authorTree.containsKey(s)){
							int currentValue = authorTree.get(s);
							authorTree.put(s, currentValue + 1);
						}
						else{
							authorTree.put(s, 0);
						}
					}
				}

				public void receivePersonRecord(PersonRecord personRecord){}
			});
			recordScanner.startScan(filename);
			getEngine().setMapExistsAlready(true);
	}

}