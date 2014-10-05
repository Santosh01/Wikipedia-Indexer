/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.util.Properties;

/**
 *
 * This class represents a subclass of a Dictionary class that is
 * shared by multiple threads. All methods in this class are
 * synchronized for the same reason.
 */
public class SharedDictionary extends Dictionary {
	
	/**
	 * Public default constructor
	 * @param props: The properties file
	 * @param field: The field being indexed by this dictionary
	 */
	
	static int count=0;
	public SharedDictionary(Properties props, INDEXFIELD field) {
		super(props, field);
		// TODO Add more code here if needed
	}
	
	/**
	 * Method to lookup and possibly add a mapping for the given value
	 * in the dictionary. The class should first try and find the given
	 * value within its dictionary. If found, it should return its
	 * id (Or hash value). If not found, it should create an entry and
	 * return the newly created id.
	 * @param value: The value to be looked up
	 * @return The id as explained above.
	 */
	public synchronized int lookup(String value) {
		//TODO Implement this method
		
		value = LinkProc(value);
		
		if(super.exists(value)){
			return super.dict.get(value);
		}else{
			super.dict.put(value, count++);
			return super.dict.get(value);
		}
		
	}
	
	public static String LinkProc(String text){
		
		try{
			
			if(text == null)
				return "";

			if(text.equalsIgnoreCase(""))
				return "";
			//auto capatilization
//			char temp;
//			String x;
//			temp = text.charAt(0);
//			temp = Character.toUpperCase(temp);
//			x = new String(temp+text.substring(1,text.length()));
			
			//replacing space with underscore
			text = text.replaceAll(" ","_");


			
			return text;
		}catch(StringIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return null;
	}
	

}
