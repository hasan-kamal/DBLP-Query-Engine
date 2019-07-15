/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

public abstract class Query{
	private DBLPQueryEngine engine;
	public String queryString;

	public Query(DBLPQueryEngine aEngine, String aQueryString){
		this.engine = aEngine;
		this.queryString = aQueryString;
	}

	public abstract void clearPreviousData();
	public abstract void makeNewData();

	public final void query(){
		clearPreviousData();
		makeNewData();
	}

	public DBLPQueryEngine getEngine(){
		return engine;
	}
}