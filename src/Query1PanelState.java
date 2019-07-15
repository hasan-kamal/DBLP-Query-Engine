/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import javax.swing.*;
import java.awt.*;

public class Query1PanelState extends QueryPanelState{

	private static Query1PanelState soleInstance = null;

	private JComboBox<String> searchByBox;
	private JTextField textInputField;
	private JTextField sinceYearField;
	private JTextField[] rangeField = new JTextField[2];
	private JRadioButton sortyByYear, sortByRelevance;
	
	private Query1PanelState(DBLPQueryEngine aEngine){
		super(aEngine);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		String[] searchByStrings = {"Publication Title", "Author Name"};
		searchByBox = new JComboBox<String>(searchByStrings);
		textInputField = new JTextField();
		sinceYearField = new JTextField();
		rangeField[0] = new JTextField(4);
		rangeField[1] = new JTextField(4);

		JLabel nameTitleLabel = new JLabel("Name/Title: ");
		JPanel nameTitlePanel = new JPanel(new BorderLayout());
		nameTitlePanel.add(nameTitleLabel, BorderLayout.WEST);
		nameTitlePanel.add(textInputField, BorderLayout.CENTER);

		JLabel sinceYearLabel = new JLabel("Since Year: ");
		JPanel sinceYearPanel = new JPanel(new BorderLayout());
		sinceYearPanel.add(sinceYearLabel, BorderLayout.WEST);
		sinceYearPanel.add(sinceYearField, BorderLayout.CENTER);

		JLabel rangeLabel = new JLabel("Custom Range: ");
		JPanel rangeLabelPanel = new JPanel(new BorderLayout());
		rangeLabelPanel.add(rangeLabel, BorderLayout.CENTER);

		JPanel rangePanel = new JPanel();
			JLabel hyphenLabel = new JLabel("-");
			rangePanel.add(rangeField[0]);
			rangePanel.add(hyphenLabel);
			rangePanel.add(rangeField[1]);
		sortyByYear = new JRadioButton("Sort by Year");
		sortyByYear.setSelected(true);
		sortByRelevance = new JRadioButton("Sort by Relevance");
		ButtonGroup group = new ButtonGroup();
		group.add(sortyByYear);
		group.add(sortByRelevance);
		JPanel radioYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		radioYearPanel.add(sortyByYear);
		JPanel radioRelevancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		radioRelevancePanel.add(sortByRelevance);
		add(searchByBox);
		add(nameTitlePanel);
		add(sinceYearPanel);
		add(rangeLabelPanel);
		add(rangePanel);
		add(radioYearPanel);
		add(radioRelevancePanel);
	}

	public static Query1PanelState getInstance(DBLPQueryEngine aEngine){
		if(soleInstance==null)
			soleInstance = new Query1PanelState(aEngine);
		return soleInstance;
	}

	private boolean isValidYear(String  s){
		try{
			Integer.parseInt(s);
			if(s.length()==4)
				return true;
			else
				return false;
		}catch(Exception ex){
			return false;
		}
	}

	//overriding state methods
	public void search(){

		if(textInputField.getText().equals("") ){
			JOptionPane.showMessageDialog(this.getEngine().getFrame(), "Name/Title field cannot be left blank");
			return;
		}

		if( (!sinceYearField.getText().equals("")) && !isValidYear(sinceYearField.getText()) ){
			JOptionPane.showMessageDialog(this.getEngine().getFrame(), "Since Year must be empty or a valid year(4) digits");
			return;	
		}

		boolean firstEmpty = rangeField[0].getText().equals("");
		boolean secondEmpty = rangeField[1].getText().equals("");
		boolean bothEmpty = firstEmpty && secondEmpty;

		boolean firstValid = isValidYear(rangeField[0].getText());
		boolean secondValid = isValidYear(rangeField[1].getText());

		System.out.println("firstValid " + firstValid);
		System.out.println("secondValid " + secondValid);

		boolean bothFilled = (!firstEmpty) && (!secondEmpty);

		if( (firstEmpty && !secondEmpty) || (!firstEmpty && secondEmpty) || (bothFilled && ( (firstValid&&!secondValid)||(secondValid&&!firstValid) || (!firstValid&&!secondValid) ) ) ){
			JOptionPane.showMessageDialog(this.getEngine().getFrame(), "Please enter a valid custom range");
			return;	
		}

		Thread queryThread = new Thread(new Query1Runnable(this.getEngine(), textInputField.getText(), sortyByYear.isSelected(), searchByBox.getSelectedIndex()==0), "query 1 thread");
		queryThread.start();
	}

	public void reset(){
		textInputField.setText("");
		sinceYearField.setText("");
		rangeField[0].setText("");
		rangeField[1].setText("");
		sortyByYear.setSelected(true);
	}

	public class Query1Runnable implements Runnable{
		DBLPQueryEngine engine;
		String queryString;
		boolean sortYearOption;
		boolean searchByPublicationTitle;

		public Query1Runnable(DBLPQueryEngine aEngine, String aQueryString, boolean aSortyByYearOption, boolean aSearchByPublicationTitle){
			engine = aEngine;
			queryString = aQueryString;
			sortYearOption = aSortyByYearOption;
			searchByPublicationTitle = aSearchByPublicationTitle;
		}

		public void run(){
			System.out.println("Query 1 thread running");
			if(searchByPublicationTitle)
				engine.performQuery1PublicationTitle(queryString, sortYearOption);
			else
				engine.performQuery1AuthorName(queryString, sortYearOption);
			System.out.println("Query 1 thread exiting");
		}
	}

}