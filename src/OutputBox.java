/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.event.*;

public class OutputBox extends JPanel{
	private JTable table; //View of the model
	private TableModel currentModel; //Model that holds our data
	private boolean loadingVisible = false;
	private JLabel loadingLabel;
	private Thread loadingThread;
	private int currentPage = 0; //variable to keep track of current page number
	private JButton buttonPrevious, buttonNext;
	
	public OutputBox(){
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		setLayout(new BorderLayout());
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				//table
				table = new JTable();
				JScrollPane scrollPane = new JScrollPane(table);
				table.setFillsViewportHeight(true);
				OutputBox.this.add(scrollPane, BorderLayout.CENTER);
				//loading label
				loadingLabel = new JLabel("...");
				loadingLabel.setFont(new Font("SansSerif", Font.ITALIC, 15));
				loadingLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				OutputBox.this.add(loadingLabel, BorderLayout.NORTH);

				//button - next and prev
				JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				buttonNext = new JButton(" Next ");
				buttonNext.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						nextClicked();
					}
				});
				buttonPrevious = new JButton("Previous");
				buttonPrevious.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						previousClicked();
					}
				});
				buttonPanel.add(buttonPrevious);
				buttonPanel.add(buttonNext);
				OutputBox.this.add(buttonPanel, BorderLayout.SOUTH);
			}
		});
	}	

	public void showPageNumber(int n){
		int numResultsInModel = ((currentModel!=null)?currentModel.getRowCount():-1);
		int lastValidPageNumber = (numResultsInModel%20==0)?(numResultsInModel/20-1):(numResultsInModel/20);
		if(currentPage==0)
			buttonPrevious.setEnabled(false);
		else
			buttonPrevious.setEnabled(true);

		if(currentPage==lastValidPageNumber)
			buttonNext.setEnabled(false);
		else
			buttonNext.setEnabled(true);

		RowFilter<Object, Object> filterFirstTwenty = new RowFilter<Object, Object>() {
		      public boolean include(Entry entry) {
		        Integer serialNumber = (Integer) entry.getValue(0);
		        return ( (20*n+1)<=serialNumber && serialNumber<=(20*(n+1)) );
		      }
	    };
	    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(currentModel);
	    sorter.setRowFilter(filterFirstTwenty);
	    table.setRowSorter(sorter);
	}

	public void showPublications(List<PublicationRecord> listPublicationsToDisplay){
		EventQueue.invokeLater(new Runnable(){
			public void run(){

				if(listPublicationsToDisplay.size()==0){
					showNoResultModel();
					hideLoadingBar(true);
					currentPage = 0;
					buttonPrevious.setEnabled(false);
					showPageNumber(0);
					return;
				}
				//set model of table view
				currentModel = new PublicationTableModel(listPublicationsToDisplay);
				table.setModel(currentModel);
				currentPage = 0;
				showPageNumber(0);
				buttonPrevious.setEnabled(false);
				//set column widths properly
				TableColumn column = null;
				for(int i=0; i<8; i++){
					column = table.getColumnModel().getColumn(i);
					if(i==0)
						column.setPreferredWidth(50);
					else if(i==1 || i==2)
						column.setPreferredWidth(300);
					else
						column.setPreferredWidth(70);
				}
				//hide loading bar
				hideLoadingBar(false);

			}
		});	
	}

	public void showAuthors(List<String> listStringToDisplay){
		EventQueue.invokeLater(new Runnable(){
			public void run(){

				if(listStringToDisplay.size()==0){
					showNoResultModel();
					hideLoadingBar(true);
					currentPage = 0;
					buttonPrevious.setEnabled(false);
					showPageNumber(0);
					return;
				}
				//set model of table view
				currentModel = new PersonTableModel(listStringToDisplay);
				table.setModel(currentModel);
				currentPage = 0;
				showPageNumber(0);
				buttonPrevious.setEnabled(false);

				//hide loading bar
				hideLoadingBar(false);
			}
		});
	}

	private void clearModel(){
		Object[][] data = {};
		Object[] columnNames = {};

		currentModel = new DefaultTableModel(data, columnNames);
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				table.setModel(currentModel);
			}
		});
	}

	private void showNoResultModel(){
		Object[][] data = {{1, "No results found. Please search again!"}};
		Object[] columnNames = {"S. No", "Message"};

		currentModel = new DefaultTableModel(data, columnNames);
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				table.setModel(currentModel);
			}
		});
	}

	public void showLoadingBar(){
		if(!loadingVisible){
			clearModel();
			loadingLabel.setText("Loading");
			loadingVisible = !loadingVisible;
			loadingThread = new Thread(new LoadingRunnable(loadingLabel), "loading thread");
			loadingThread.start();
		}
	}
	public void hideLoadingBar(boolean gotNoResult){
		if(loadingVisible){
			loadingThread.interrupt();
			loadingLabel.setText("Number of results returned: " + (!gotNoResult?((currentModel!=null)?currentModel.getRowCount():-1):0) );
			loadingVisible = !loadingVisible;
		}
	}
	public void nextClicked(){
		int numResultsInModel = ((currentModel!=null)?currentModel.getRowCount():-1);
		int lastValidPageNumber = (numResultsInModel%20==0)?(numResultsInModel/20-1):(numResultsInModel/20);
		if(currentPage<lastValidPageNumber){
			currentPage++;
			showPageNumber(currentPage);
		}
	}
	public void previousClicked(){
		if(currentPage>0){
			currentPage--;
			showPageNumber(currentPage);
		}
	}
}