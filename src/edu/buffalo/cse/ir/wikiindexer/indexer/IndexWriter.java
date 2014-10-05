/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;



/**
 * 
 * This class is used to write an index to the disk
 * 
 */

class Index implements Serializable{

	Integer docId;
	Integer numOcc;
	
	
	public Integer getDocId() {
		return docId;
	}
	public void setDocId(Integer docId) {
		this.docId = docId;
	}
	public Integer getNumOcc() {
		return numOcc;
	}
	public void setNumOcc(Integer numOcc) {
		this.numOcc = numOcc;
	}

}

public class IndexWriter implements Writeable {


	static int tempMapCount = 0;
	
	// this is my Index stored as a map
	Map<Integer,LinkedList<Index>> indexMap = new HashMap<Integer,LinkedList<Index>>();
 //	Map<Integer,LinkedList<Integer>> indexMap = new HashMap<Integer,LinkedList<Integer>>();
	
	Map<String, LinkedList<Index>> indexMap1 = new HashMap<String,LinkedList<Index>>();
	// this is my dictionary stored as a map
	Map<String,Integer> tempMap = new HashMap<String,Integer>();
	
	File file;
    FileOutputStream fos;
    ObjectOutputStream oos;
   
    Properties idxProps;
    INDEXFIELD idxValueField;
    INDEXFIELD idxKeyField;
    boolean isForward;
    int pNum;


	
	/**
	 * Constructor that assumes the underlying index is inverted
	 * Every index (inverted or forward), has a key field and the value field
	 * The key field is the field on which the postings are aggregated
	 * The value field is the field whose postings we are accumulating
	 * For term index for example:
	 * 	Key: Term (or term id) - referenced by TERM INDEXFIELD
	 * 	Value: Document (or document id) - referenced by LINK INDEXFIELD
	 * @param props: The Properties file
	 * @param keyField: The index field that is the key for this index
	 * @param valueField: The index field that is the value for this index
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField, INDEXFIELD valueField) {
		this(props, keyField, valueField, false);

		
	}
	
	/**
	 * Overloaded constructor that allows specifying the index type as
	 * inverted or forward
	 * Every index (inverted or forward), has a key field and the value field
	 * The key field is the field on which the postings are aggregated
	 * The value field is the field whose postings we are accumulating
	 * For term index for example:
	 * 	Key: Term (or term id) - referenced by TERM INDEXFIELD
	 * 	Value: Document (or document id) - referenced by LINK INDEXFIELD
	 * @param props: The Properties file
	 * @param keyField: The index field that is the key for this index
	 * @param valueField: The index field that is the value for this index
	 * @param isForward: true if the index is a forward index, false if inverted
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField, INDEXFIELD valueField, boolean isForward) {
		//TODO: Implement this method
		
		this.idxProps = props;
		this.idxKeyField = keyField;
		this.idxValueField = valueField;
		this.isForward = isForward;
		
	}
	
	/**
	 * Method to make the writer self aware of the current partition it is handling
	 * Applicable only for distributed indexes.
	 * @param pnum: The partition number
	 */
	public void setPartitionNumber(int pnum) {
		//TODO: Optionally implement this method
		
		
		this.pNum = pnum;
		
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param keyId: The id for the key field, pre-converted
	 * @param valueId: The id for the value field, pre-converted
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, int valueId, int numOccurances) throws IndexerException {
		//TODO: Implement this method
		
		LinkedList<Index> ls1;
		Index temp1 = new Index();
		Integer i = new Integer(keyId);
		String tempKey = i.toString();
//		temp1.docId = valueId;
//		temp1.numOcc = numOccurances;
		temp1.setDocId(valueId);
		temp1.setNumOcc(numOccurances);
		if(indexMap1.containsKey(tempKey)){
			
			ls1 = indexMap1.get(tempKey);
			ls1.add(temp1);
//			Collections.sort(ls1);
			indexMap1.put(tempKey, ls1);
		}
		
		else{
			
			ls1 = new LinkedList<Index>();
			ls1.add(temp1);
			indexMap1.put(tempKey, ls1);
			
		}
		//indexMap.put(keyId, valueId);
		
		
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param keyId: The id for the key field, pre-converted
	 * @param value: The value for the value field
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, String value, int numOccurances) throws IndexerException {
		//TODO: Implement this method
		
		
		
		
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param key: The key for the key field
	 * @param valueId: The id for the value field, pre-converted
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(String key, int valueId, int numOccurances) throws IndexerException {
		//TODO: Implement this method
		
		LinkedList ls1;
		Index temp1 = new Index();
		
		// Indexing for non-terms
		if(idxKeyField == INDEXFIELD.AUTHOR || idxKeyField == INDEXFIELD.CATEGORY){

			// Adding elements to the map

//			System.out.println(key);
			temp1.setDocId(valueId);
			temp1.setNumOcc(numOccurances);
			// If the element already exists in the Map, get its LinkedList add the new docId and numOfOcc to it and put it back in the Map
			if(indexMap1.containsKey(key)){ 
				ls1 = indexMap1.get(key);
				ls1.add(temp1);
				indexMap1.put(key, ls1);
			}
			// If the element is not present then create a new LL and add then add a new entry Temp1 to it.
			else{
				
				ls1 = new LinkedList<Index>();
				ls1.add(temp1);
				indexMap1.put(key, ls1);
				
			}
			
		}
		
		// Indexing for terms;
		else
		{
			// Adding elements to the map

						temp1.setDocId(valueId);
						temp1.setNumOcc(numOccurances);
						// If the element already exists in the Map, get its LinkedList add the new docId and numOfOcc to it and put it back in the Map
						if(indexMap1.containsKey(key)){ 
//							System.out.println("repeat key " + key + " : " + "DocID : " +valueId);
							ls1 = indexMap1.get(key);
							ls1.add(temp1);
							indexMap1.put(key, ls1);
						}
						// If the element is not present then create a new LL and add then add a new entry Temp1 to it.
						else{
							
//							System.out.println("new key " + key + " : " + "DocID : " +valueId);
							ls1 = new LinkedList<Index>();
							ls1.add(temp1);
							indexMap1.put(key, ls1);
							
						}
			
		}
		
		
		
		
	}
	
	/**
	 * Method to add a given key - value mapping to the index
	 * @param key: The key for the key field
	 * @param value: The value for the value field
	 * @param numOccurances: Number of times the value field is referenced
	 *  by the key field. Ignore if a forward index
	 * @throws IndexerException: If any exception occurs while indexing
	 */
	public void addToIndex(String key, String value, int numOccurances) throws IndexerException {
		//TODO: Implement this method
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method
		try{
			
/* Assign a proper filename on the basis of the INDEXFIELD type  */
			
			if(idxKeyField == INDEXFIELD.AUTHOR){
				file = new File("INDEX_AUTHOR.txt");
			}
			if(idxKeyField == INDEXFIELD.LINK){
				file = new File("INDEX_LINK.txt");
			}
			if(idxKeyField == INDEXFIELD.CATEGORY){
				file = new File("INDEX_CATEGORY.txt");
			}
			if(idxKeyField == INDEXFIELD.TERM){
				file = new File("INDEX_TERM_PARTITION_" +pNum + ".txt");
			}
			
			
			
			
			
//			file = new File("Term Index partition_" + idxKeyField +".txt");
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(indexMap1);
			oos.flush();
			oos.close();


			if(idxKeyField == INDEXFIELD.AUTHOR ||idxKeyField == INDEXFIELD.CATEGORY ||idxKeyField == INDEXFIELD.LINK )
				System.out.println("Number of Terms in " + idxKeyField  + " = " + indexMap1.size());
			else
				System.out.println("Number of Terms in Partition " + pNum + " = " + indexMap1.size());
			
			
/* Printing the data inside each map once the Index writing is done to the Disk */	
//			if(idxKeyField == INDEXFIELD.LINK)
//			printMap();
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method

	}
	
	public void printMap(){
//		System.out.println(idxKeyField);
		for (Entry<String, LinkedList<Index>> indexEntry : indexMap1.entrySet()) {
			
			System.out.print(indexEntry.getKey() + " : ");

			for (Index idx : indexEntry.getValue()) {
				
				System.out.print(idx.getDocId() + "," + idx.getNumOcc() + " ");
			}
			System.out.println();
		}
//		System.out.println();
	}

	
}

/* More of file read write crap just read this later may be useful*/

//file = new File("Term Index Dictionary.txt");
//fos = new FileOutputStream(file);
//oos = new ObjectOutputStream(fos);
//oos.writeObject(indexMap);
//oos.flush();
//
//FileInputStream fin=new FileInputStream(file);
//ObjectInputStream oin=new ObjectInputStream(fin);
//Map<String,LinkedList<Index>> indexMap2=(Map<String,LinkedList<Index>>)oin.readObject();
//System.out.println("After Deseralization :"+indexMap2);

//System.out.println("hih");
//
//System.out.println("Temp Map : " + tempMap);

//Iterator<Map.Entry<String, LinkedList<Index>>> i = indexMap2.entrySet().iterator();
//LinkedList ls ;
//
//Iterator j;
//while(i.hasNext()){
//	String key = i.next().getKey();
//	ls = indexMap2.get(key);
//	j= ls.iterator();
//	System.out.print(key + " : ");
//	while(j.hasNext()){
//		System.out.print(j.toString() + " ");
//	}
//	System.out.println();
//}
//



/* I donno what the following crap is */


//Integer i;
//
//if(tempMap.containsKey(key)){
//	i = tempMap.get(key);
//	
//}
//else{
//	i=tempMapCount;
//	tempMap.put(key, tempMapCount++);
//	
//}
//	
//LinkedList<Index> ls;
//Index temp = new Index();
//temp.docId = valueId;
//temp.numOcc = numOccurances;
//if(indexMap.containsKey(i)){
//	
//	ls = indexMap.get(i);
//	ls.add(temp);
//	
//	indexMap.put(i, ls);
//}
//
//else{
//	
//	ls = new LinkedList<Index>();
//	ls.add(temp);
//	indexMap.put(i, ls);
//	
//}

