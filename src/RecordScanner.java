/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import org.xml.sax.*;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;

public class RecordScanner{

	private RecordScannerListener recordScannerListener;
	private DBLPHandler dblpHandler;
	private SAXParser parser;

	public RecordScanner(){

		try {
		     SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		     parser = parserFactory.newSAXParser();
		     dblpHandler = new DBLPHandler();
	         parser.getXMLReader().setFeature(
		          "http://xml.org/sax/features/validation", true);
	      } catch (SAXException e) {
	         System.out.println("Error in parsing: " + e.getMessage());
	      } catch (ParserConfigurationException e) {
	         System.out.println("Error in XML parser configuration: " +
				    e.getMessage());
	      }

	}

	public void setRecordScannerListener(RecordScannerListener listener){
		this.recordScannerListener = listener;
	}

	public void startScan(String filename){
		try{
			//dblpHandler.reset();
			parser.parse(new File(filename), dblpHandler);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	//SAX handler inner class
	private class DBLPHandler extends DefaultHandler{

        private String val;
        private String key;
        private String recordTag;
    
        private PublicationRecord temporaryPublicationRecord;
        private PersonRecord temporaryPersonRecord;
        
        public DBLPHandler(){
        	temporaryPublicationRecord = new PublicationRecord("", "", "", "", "", "");
        	temporaryPersonRecord = new PersonRecord();
        }

		public void startElement(String namespaceURL, String lName, String qName, Attributes atts) throws SAXException{
			val = "";

            String k;
            if ((atts.getLength()>0) && ((k = atts.getValue("key"))!=null)) {
                //current Element has a "key" attribute, must be a publicationRecord or personRecord
                key = k;
                recordTag = qName;   
            }
		}

		public void endElement(String namespaceURL, String lName, String qName) throws SAXException{

			if(recordTag.equals("www") ){
				if(qName.equals("author")){
					temporaryPersonRecord.getListNames().add(val);
					return;
				}
				if(qName.equals(recordTag)){
					recordScannerListener.receivePersonRecord(temporaryPersonRecord);
					temporaryPersonRecord.getListNames().clear();
				}
			}
			else{
				if (qName.equals("author") || qName.equals("editor")) {
					// //ending a "author"/"editor" element
					temporaryPublicationRecord.getAuthorNameList().add(val);
	                return;
	            }

	            if(qName.equals("title")){
	            	temporaryPublicationRecord.setTitle(val);
	            }else if(qName.equals("pages")){
	            	temporaryPublicationRecord.setPages(val);
	            }else if(qName.equals("year")){
	            	temporaryPublicationRecord.setYear(val);
	            }else if(qName.equals("volume")){
	            	temporaryPublicationRecord.setVolume(val);
	            }else if(qName.equals("journal") || qName.equals("booktitle")){
	            	temporaryPublicationRecord.setJournalBookTitle(val);
	            }

	            if (qName.equals(recordTag)) {
	            	//notify the listener with the publication record
	                recordScannerListener.receivePublicationRecord(temporaryPublicationRecord);
	            	temporaryPublicationRecord.getAuthorNameList().clear();
	            }
			}
			
		}

        public void characters(char[] ch, int start, int length) throws SAXException{
        	val += new String(ch, start, length);
        }
	}
}