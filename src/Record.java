/**
*	@author Hasan Kamal
*	@author Luv Sharma
*/

public abstract class Record{
	private String key;				// The unique dblp key of each entry
	private String mDate;			// The date this record has been last modified
	private String publType;		// Specifies type of publication - informal, encyclopedia, editorial, etc

	// setters
	public void setKey(String keyParam){
		this.key = keyParam;
	}

	public void setMDate(String mDateParam){
		this.mDate = mDateParam;
	}

	public void setPublType(String publTypeParam){
		this.publType = publTypeParam;
	}

	// getters
	public String getKey(){
		return this.key;
	}

	public String getMDate(){
		return this.mDate;
	}

	public String getPublType(){
		return this.publType;
	}
}