/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

public class DBLPQueryEngine{
	private RecordScanner recordScanner;
	private String filename = "./dblp.xml";
	private List<PublicationRecord> listPublicationRecord;
	private List<PersonRecord> listPersonRecords;
	//GUI instance variables
	private OutputBox outputBox;
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel queryPanel;
	private QueryPanelState currentQueryPanelState;
	private JComboBox<String> querySelectionBox;
	private List<String> frequentWords;
	private TreeMap<String, Integer> authorTree;
	private TreeMap<String, String> aliasMap;
	private boolean mapExistsAlready = false;

	public TreeMap<String, Integer> getAuthorTree(){
		return authorTree;
	}

	public TreeMap<String, String> getAliasMap(){
		return aliasMap;
	}

	public List<PublicationRecord> getListPublicationRecord(){
		return listPublicationRecord;
	}

	public List<PersonRecord> getListPersonRecord(){
		return listPersonRecords;
	}

	public RecordScanner getRecordScanner(){
		return recordScanner;
	}

	public OutputBox getOutputBox(){
		return outputBox;
	}

	public List<String> getFrequentWords(){
		return frequentWords;
	}

	public String getFilename(){
		return filename;
	}

	public void setMapExistsAlready(boolean b){
		this.mapExistsAlready = b;
	}

	public boolean getMapExistsAlready(){
		return mapExistsAlready;
	}

	DBLPQueryEngine(){
		listPublicationRecord = new ArrayList<PublicationRecord>();
		listPersonRecords = new ArrayList<PersonRecord>();
		recordScanner = new RecordScanner();
		frequentWords = new ArrayList<String>();
		frequentWords.add("and");
		frequentWords.add("the");
		frequentWords.add("with");
		frequentWords.add("in");
		frequentWords.add("&");
		authorTree = new TreeMap<String, Integer>();
		aliasMap = new TreeMap<String, String>();
		initializeGUI();
	}

	private void initializeGUI(){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(800, 600);

				//mainPanel
				mainPanel = new JPanel();
				mainPanel.setLayout(new BorderLayout());
				mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

				//top panel
				JPanel topPanel = new JPanel();
				JLabel label = new JLabel("DBLP Query Engine");
				label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				label.setFont(new Font("SansSerif", Font.PLAIN, 25));
				topPanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
				topPanel.add(label);

				JPanel leftPanel = leftPanelInitialize();
				//center panel
				outputBox = new OutputBox();

				//add all child panels to main panel
				mainPanel.add(topPanel, BorderLayout.NORTH);
				mainPanel.add(leftPanel, BorderLayout.WEST);
				mainPanel.add(outputBox, BorderLayout.CENTER);

				frame.getContentPane().add(mainPanel);
				frame.setVisible(true);
			}
		});
	}
	private JPanel leftPanelInitialize(){
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));

		String[] queryStrings = {"Query 1", "Query 2"};
		querySelectionBox = new JComboBox<String>(queryStrings);
		querySelectionBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox cBox = (JComboBox)e.getSource();
				String s = (String)cBox.getSelectedItem();
				if(s.equals("Query 1"))
					showQueryPanel(1);
				else if(s.equals("Query 2"))
					showQueryPanel(2);
			}
		});
		leftPanel.add(querySelectionBox);
			//queryPanel
			queryPanel = new JPanel();
			queryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			currentQueryPanelState = Query1PanelState.getInstance(DBLPQueryEngine.this);
			queryPanel.add(currentQueryPanelState);
			leftPanel.add(queryPanel);
			//buttonPanel
			JPanel buttonPanel = new JPanel();
			JButton searchButton = new JButton("Search");
			searchButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					currentQueryPanelState.search();
				}
			});
			JButton resetButton = new JButton("Reset");
			resetButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					currentQueryPanelState.reset();
				}
			});
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
			buttonPanel.add(searchButton);
			buttonPanel.add(resetButton);

		leftPanel.add(buttonPanel);
		return leftPanel;
	}

	public void showQueryPanel(int queryType){
		queryPanel.removeAll();
		if(queryType==1)
			currentQueryPanelState = Query1PanelState.getInstance(this);
		else if(queryType==2)
			currentQueryPanelState = Query2PanelState.getInstance(this);
		queryPanel.add(currentQueryPanelState);
		queryPanel.revalidate();
	}

	public void performQuery1PublicationTitle(String queryString, boolean sortYear){
		Query query = new Query1(this, true, queryString, sortYear);
		query.query();
	}

	public void performQuery1AuthorName(String aQueryString, boolean sortYear){
		Query query = new Query1(this, false, aQueryString, sortYear);
		query.query();
	}

	public void performQuery2(String queryString){
		Query query = new Query2(this, queryString);
		query.query();
	}

	public static void main(String[] args){
		System.setProperty("jdk.xml.entityExpansionLimit", "0");
		DBLPQueryEngine engine = new DBLPQueryEngine();
	}

	public JFrame getFrame(){
		return frame;
	}
}