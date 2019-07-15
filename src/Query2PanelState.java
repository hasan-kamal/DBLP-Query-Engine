/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import javax.swing.*;
import java.awt.*;

public class Query2PanelState extends QueryPanelState{
	
	private static Query2PanelState soleInstance = null;
	private JTextField textInputField;
	
	private Query2PanelState(DBLPQueryEngine aEngine){
		super(aEngine);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		textInputField = new JTextField();
		
		JLabel numPublicationLabel = new JLabel("No. of Publications: ");
		add(numPublicationLabel);
		add(textInputField);
	}

	public static Query2PanelState getInstance(DBLPQueryEngine aEngine){
		if(soleInstance==null)
			soleInstance = new Query2PanelState(aEngine);
		return soleInstance;
	}

	private boolean isInteger(String  s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	//overriding methods
	public void search(){
		if(!isInteger(textInputField.getText())){
			JOptionPane.showMessageDialog(this.getEngine().getFrame(), "Please enter a valid value");
			return;
		}

		Thread queryThread = new Thread(new Query2Runnable(this.getEngine(), textInputField.getText()), "query 2 thread");
		queryThread.start();
	}

	public void reset(){
		textInputField.setText("");
	}

	public class Query2Runnable implements Runnable{
		DBLPQueryEngine engine;
		String queryString;
		
		public Query2Runnable(DBLPQueryEngine aEngine, String aQueryString){
			engine = aEngine;
			queryString = aQueryString;
		}

		public void run(){
			System.out.println("Query 2 thread running");
			engine.performQuery2(queryString);
			System.out.println("Query 2 thread exiting");
		}
	}
}