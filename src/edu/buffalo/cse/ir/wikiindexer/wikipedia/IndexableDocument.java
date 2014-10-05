/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.HashMap;
import java.util.Map;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;

/**
 * A simple map based token view of the transformed document
 *
 *
 */
public class IndexableDocument {
	/**
	 * Default constructor
	 */
	
	public static int doc_count = 0;
	private int doc_num;
	private String doc_id;
	private Map<INDEXFIELD,TokenStream> map ;
	
	
	
	public static int getDoc_count() {
		return doc_count;
	}

	public IndexableDocument() {
		
		
		map = new HashMap<INDEXFIELD,TokenStream>();
		doc_num = doc_count++;
		//TODO: Init state as needed
	}
	
	/**
	 * MEthod to add a field and stream to the map
	 * If the field already exists in the map, the streams should be merged
	 * @param field: The field to be added
	 * @param stream: The stream to be added.
	 */
	public void addField(INDEXFIELD field, TokenStream stream) {
		//TODO: Implement this method
		
		map.put(field, stream);
	}
	
	/**
	 * Method to return the stream for a given field
	 * @param key: The field for which the stream is requested
	 * @return The underlying stream if the key exists, null otherwise
	 */
	public TokenStream getStream(INDEXFIELD key) {
		//TODO: Implement this method
		
		if(key !=null)
		{
			TokenStream stream = map.get(key);
			return stream;
		}
		
		return null;
}
	
	/**
	 * Method to return a unique identifier for the given document.
	 * It is left to the student to identify what this must be
	 * But also look at how it is referenced in the indexing process
	 * @return A unique identifier for the given document
	 */
	public String getDocumentIdentifier() {
		
		return doc_id;
		//TODO: Implement this method
		
	}



	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	
}
