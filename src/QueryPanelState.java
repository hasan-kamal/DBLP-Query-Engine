/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

import javax.swing.*;

public abstract class QueryPanelState extends JPanel{
	private DBLPQueryEngine engine;

	public QueryPanelState(DBLPQueryEngine aEngine){
		engine = aEngine;
	}

	public DBLPQueryEngine getEngine(){
		return engine;
	}

	//state methods to be overridden by concrete subclasses
	public abstract void search();
	public abstract void reset();
}