/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.buffalo.cse.ir.wikiindexer.indexer.Partitioner;
import edu.buffalo.cse.ir.wikiindexer.indexer.Index;

/**
 * 
 * This class is used to introspect a given index
 * The expectation is the class should be able to read the index
 * and all associated dictionaries.
 */
public class IndexReader {
	
	Properties xProps;
	INDEXFIELD xField; 
	Integer mapSize = new Integer(0);
//	Map<String, LinkedList<Index>> xIndexMap;
	Map<String, LinkedList<Index>> xIndexMap_TERM_0;
	Map<String, LinkedList<Index>> xIndexMap_TERM_1;
	Map<String, LinkedList<Index>> xIndexMap_TERM_2;
	Map<String, LinkedList<Index>> xIndexMap_TERM_3;	
	Map<String, LinkedList<Index>> xIndexMap_TERM_4;
	Map<String, LinkedList<Index>> xIndexMap_TERM_5;
	Map<String, LinkedList<Index>> xIndexMap_TERM_6;	
	Map<String, LinkedList<Index>> xIndexMap_TERM_7;
	Map<String, LinkedList<Index>> xIndexMap_TERM_8;
	Map<String, LinkedList<Index>> xIndexMap_AUTHOR;
	Map<String, LinkedList<Index>> xIndexMap_CATEGORY;
	Map<String, LinkedList<Index>> xIndexMap_LINK;
	Map<String, LinkedList<Index>> xIndexMap_allTerms;
	
	Map<String, Integer> xIndexMap_DICTIONARY;
	Map<Integer, String> xIndexMap_DICTIONARY_REVERSE;
	
	File file;
	FileInputStream fin;
	ObjectInputStream  oin;
	
	
	
	
	/**
	 * Constructor to create an instance 
	 * @param props: The properties file
	 * @param field: The index field whose index is to be read
	 */
	public IndexReader(Properties props, INDEXFIELD field) {
		//TODO: Implement this method
		xProps = props;
		xField = field;
		xIndexMap_allTerms = new HashMap<String, LinkedList<Index>>();
		
		xIndexMap_DICTIONARY_REVERSE = new HashMap<Integer, String>();

		try{
		
				System.out.println("INSIDE constructor");

				if(field == INDEXFIELD.TERM){
					

					// All Partitions
					file = new File("INDEX_TERM_PARTITION_0.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_0=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 0");
					file = new File("INDEX_TERM_PARTITION_1.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_1=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 1");
					file = new File("INDEX_TERM_PARTITION_2.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_2=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 2");
					file = new File("INDEX_TERM_PARTITION_3.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_3=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 3");
					file = new File("INDEX_TERM_PARTITION_4.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_4=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 4");
					file = new File("INDEX_TERM_PARTITION_5.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_5=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 5");
					file = new File("INDEX_TERM_PARTITION_6.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_6=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 6");
					file = new File("INDEX_TERM_PARTITION_7.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_7=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 7");
					file = new File("INDEX_TERM_PARTITION_8.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_TERM_8=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
					System.out.println("finished reading partition 8");
					
					xIndexMap_allTerms.putAll(xIndexMap_TERM_0);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_1);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_2);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_3);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_4);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_5);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_6);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_7);
					xIndexMap_allTerms.putAll(xIndexMap_TERM_8);
					
					
				}
			
		// category	
				if(field == INDEXFIELD.CATEGORY){
					
					System.out.println("finished reading categories");
					file = new File("INDEX_CATEGORY.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_CATEGORY=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
				}
	
		// link
				if(field == INDEXFIELD.LINK){
					

					System.out.println("finished reading links");
					file = new File("INDEX_LINK.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_LINK=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
				}

		// author
				if(field == INDEXFIELD.AUTHOR){

					System.out.println("finished reading authors");
					file = new File("INDEX_AUTHOR.txt");
					fin=new FileInputStream(file);
					oin=new ObjectInputStream(fin);
					xIndexMap_AUTHOR=(HashMap<String,LinkedList<Index>>)oin.readObject();
					
				}

		// dictionary
				System.out.println("finished reading shared dictionary");
				file = new File("DICTIONARY.txt");
				fin=new FileInputStream(file);
				oin=new ObjectInputStream(fin);
				xIndexMap_DICTIONARY=(HashMap<String,Integer>)oin.readObject();
				
				Iterator it = xIndexMap_DICTIONARY.entrySet().iterator();
				while( it.hasNext()){
				
					Map.Entry<String, Integer> z = (Map.Entry<String,Integer>)it.next();
					xIndexMap_DICTIONARY_REVERSE.put(z.getValue(), z.getKey());
				}
				
				

		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Method to get the total number of terms in the key dictionary
	 * @return The total number of terms as above
	 */
	public int getTotalKeyTerms() {
		//TODO: Implement this method

		if(xField == INDEXFIELD.TERM){
				mapSize = (
						xIndexMap_TERM_0.size() +
						xIndexMap_TERM_1.size() +
						xIndexMap_TERM_2.size() +						
						xIndexMap_TERM_3.size() +
						xIndexMap_TERM_4.size() +						
						xIndexMap_TERM_5.size() +
						xIndexMap_TERM_6.size() +
						xIndexMap_TERM_7.size() +
						xIndexMap_TERM_8.size()
						);
			}
			if(xField == INDEXFIELD.CATEGORY){

				mapSize = xIndexMap_CATEGORY.size();
			}
			if(xField == INDEXFIELD.LINK){

				mapSize = xIndexMap_LINK.size();
			}
			if(xField == INDEXFIELD.AUTHOR){

				mapSize = xIndexMap_AUTHOR.size();
			}
		return mapSize;
	}
	
	/**
	 * Method to get the total number of terms in the value dictionary
	 * @return The total number of terms as above
	 */
	public int getTotalValueTerms() {
		
		mapSize = xIndexMap_DICTIONARY.size();

		//TODO: Implement this method
		return mapSize;
	}
	
	/**
	 * Method to retrieve the postings list for a given dictionary term
	 * @param key: The dictionary term to be queried
	 * @return The postings list with the value term as the key and the
	 * number of occurrences as value. An ordering is not expected on the map
	 */
	public Map<String, Integer> getPostings(String key) {
		
		Map<String,Integer> tempMap = new HashMap<String,Integer>();
		List<Index> tempList;
		
		if(xField == INDEXFIELD.AUTHOR){
			tempList = xIndexMap_AUTHOR.get(key);
			Integer tempDocId;
			Integer tempNumOcc;
			String tempDocName;
			
			for(Index i : tempList){
				tempDocId = i.getDocId();
				tempNumOcc  = i.getNumOcc();
				tempDocName = xIndexMap_DICTIONARY_REVERSE.get(tempDocId);
				tempMap.put(tempDocName, tempNumOcc);
			}
			
			return tempMap;
		}
		
		if(xField == INDEXFIELD.CATEGORY){
			
			tempList = xIndexMap_CATEGORY.get(key);
			Integer tempDocId;
			Integer tempNumOcc;
			String tempDocName;
			
			for(Index i : tempList){
				tempDocId = i.getDocId();
				tempNumOcc  = i.getNumOcc();
				tempDocName = xIndexMap_DICTIONARY_REVERSE.get(tempDocId);
				tempMap.put(tempDocName, tempNumOcc);
			}
			
			return tempMap;
		}
		
		if(xField == INDEXFIELD.LINK){
			
			Integer tempLinkId = xIndexMap_DICTIONARY.get(key);
			String tempDoc = tempLinkId.toString();
			
			tempList = xIndexMap_LINK.get(tempDoc);
			Integer tempDocId;
			Integer tempNumOcc;
			String tempDocName;
			
			for(Index i : tempList){
				tempDocId = i.getDocId();
				tempNumOcc  = i.getNumOcc();
				tempDocName = xIndexMap_DICTIONARY_REVERSE.get(tempDocId);
				tempMap.put(tempDocName, tempNumOcc);
			}
			
			return tempMap;
			
			
		}
		
		if(xField == INDEXFIELD.TERM){
			
			int partitionNum;
			
			partitionNum = Partitioner.getPartitionNumber(key);
			
			tempList = xIndexMap_TERM_0.get(key);
			
			switch(partitionNum){
			
			case 0 : tempList = xIndexMap_TERM_0.get(key); break;
			case 1 : tempList = xIndexMap_TERM_1.get(key); break;
			case 2 : tempList = xIndexMap_TERM_2.get(key); break;
			case 3 : tempList = xIndexMap_TERM_3.get(key); break;
			case 4 : tempList = xIndexMap_TERM_4.get(key); break;
			case 5 : tempList = xIndexMap_TERM_5.get(key); break;
			case 6 : tempList = xIndexMap_TERM_6.get(key); break;
			case 7 : tempList = xIndexMap_TERM_7.get(key); break;
			case 8 : tempList = xIndexMap_TERM_8.get(key); break;
				
			}
			
			Integer tempDocId;
			Integer tempNumOcc;
			String tempDocName;
			
			for(Index i : tempList){
				tempDocId = i.getDocId();
				tempNumOcc  = i.getNumOcc();
				tempDocName = xIndexMap_DICTIONARY_REVERSE.get(tempDocId);
				tempMap.put(tempDocName, tempNumOcc);
			}
			
			return tempMap;
			
			
			
		}
		
		
		
		//TODO: Implement this method
		return null;
	}
	
	/**
	 * Method to get the top k key terms from the given index
	 * The top here refers to the largest size of postings.
	 * @param k: The number of postings list requested
	 * @return An ordered collection of dictionary terms that satisfy the requirement
	 * If k is more than the total size of the index, return the full index and don't 
	 * pad the collection. Return null in case of an error or invalid inputs
	 */
	public Collection<String> getTopK(int k) {
		//TODO: Implement this method
		
		if(xField == INDEXFIELD.TERM){
			
			ArrayList<String> tempList = new ArrayList<String>();
			ArrayList<String> tempList2 = new ArrayList<String>();
			
			tempList2 = sortAllTerms(xIndexMap_allTerms);
			Iterator i = tempList.iterator();
			String temp;
			
			if(k>getTotalKeyTerms()){
				return tempList2;
			}
			else
				if(k>0){
					
					
					for(int j=0; j<k; j++){
						
						temp = tempList2.get(j);
						System.out.println(temp);
						tempList.add(temp);
					}
					
					return tempList;
					
				}
				else
					return null;
		}
		
		return null;

	}
	
	/**
	 * Method to execute a boolean AND query on the index
	 * @param terms The terms to be queried on
	 * @return An ordered map containing the results of the query
	 * The key is the value field of the dictionary and the value
	 * is the sum of occurrences across the different postings.
	 * The value with the highest cumulative count should be the
	 * first entry in the map.
	 */
	public Map<String, Integer> query(String... terms) {
		//TODO: Implement this method (FOR A BONUS)
		return null;
	}
	
	
	/**
	 * Method to get all the terms from the Term Index
	 * @return All the terms in the Term Index
	 */	
	public ArrayList<String> sortAllTerms(Map<String, LinkedList<Index>> x){
		
		ArrayList<sortItem> termList = new ArrayList<sortItem>();
		
		String tempString;
		LinkedList<Index> tempList;
		Integer tempInt;

		
		Iterator it = xIndexMap_allTerms.entrySet().iterator();
		while( it.hasNext()){
		
			Map.Entry<String,  LinkedList<Index>> z = (Map.Entry<String, LinkedList<Index>>)it.next();
			
			tempString = z.getKey();
			tempList = z.getValue();
			
			tempInt = tempList.size();
			sortItem si = new sortItem();
			si.setI(tempInt);
			si.setS(tempString);
			termList.add(si);
			
			
		}
		for(sortItem a : termList){
			System.out.print(a.getS() + " : "+ a.getI()+" ");
		}
		System.out.println();
		Collections.sort(termList, new CustomComparator());
		System.out.println("after sort");
		
		
		
		for(sortItem a : termList){
			System.out.print(a.getS() + " : "+ a.getI()+" ");
		}
		System.out.println();
		Collections.reverse(termList);
		for(sortItem a : termList){
			System.out.print(a.getS() + " : "+ a.getI()+" ");
		}
		System.out.println();
		ArrayList<String> stringList = new ArrayList<String>();
		
		for(sortItem s : termList){
			
			stringList.add(s.getS());
		}
		return stringList;
		
	}
}


class sortItem{
	
	public sortItem(){
		s = "";
		i = 0;
	}
	String s;
	Integer i;
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	public Integer getI() {
		return i;
	}
	public void setI(Integer i) {
		this.i = i;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		sortItem other = (sortItem) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		return true;
	}
	
}


class CustomComparator implements Comparator<sortItem> {
    @Override
    public int compare(sortItem o1, sortItem o2) {
        return o1.getI().compareTo(o2.getI());
    }
}

	
	



