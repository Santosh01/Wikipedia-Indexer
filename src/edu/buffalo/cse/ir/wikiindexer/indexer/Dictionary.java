/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * An abstract class that represents a dictionary object for a given index
 */
public abstract class Dictionary implements Writeable {
	
	Map<String,Integer> dict = new HashMap<String,Integer>();
	Properties xprops;
	INDEXFIELD xfield;
	
	File file = new File("DICTIONARY.txt");
	FileOutputStream fos;
	ObjectOutputStream oos;
	
	public Dictionary (Properties props, INDEXFIELD field) {
		
		this.xprops = props;
		this.xfield = field;
		
		//TODO Implement this method
	}
	
	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method
		
		try{
		

			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(dict);
			oos.flush();
			oos.close();

		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method
		
		
	}
	
	/**
	 * Method to check if the given value exists in the dictionary or not
	 * Unlike the subclassed lookup methods, it only checks if the value exists
	 * and does not change the underlying data structure
	 * @param value: The value to be looked up
	 * @return true if found, false otherwise
	 */
	public boolean exists(String value) {
		//TODO Implement this method
		if(dict.containsKey(value))
			return true;
		else
			return false;
	}
	
	/**
	 * MEthod to lookup a given string from the dictionary.
	 * The query string can be an exact match or have wild cards (* and ?)
	 * Must be implemented ONLY AS A BONUS
	 * @param queryStr: The query string to be searched
	 * @return A collection of ordered strings enumerating all matches if found
	 * null if no match is found
	 */
	public Collection<String> query(String queryStr) {
		//TODO: Implement this method (FOR A BONUS)
		
		List<String> tempList = new ArrayList<String>();
		
		return null;
	}
	
	/**
	 * Method to get the total number of terms in the dictionary
	 * @return The size of the dictionary
	 */
	public int getTotalTerms() {
		//TODO: Implement this method
		return dict.size();
	}
}
