/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

/**
 * 
 * THis class is responsible for assigning a partition to a given term.
 * The static methods imply that all instances of this class should 
 * behave exactly the same. Given a term, irrespective of what instance
 * is called, the same partition number should be assigned to it.
 */
public class Partitioner {
	/**
	 * Method to get the total number of partitions
	 * THis is a pure design choice on how many partitions you need
	 * and also how they are assigned.
	 * @return: Total number of partitions
	 */
	public static int getNumPartitions() {
		//TODO: Implement this method
		return 9;
	}
	
	/**
	 * Method to fetch the partition number for the given term.
	 * The partition numbers should be assigned from 0 to N-1
	 * where N is the total number of partitions.
	 * @param term: The term to be looked up
	 * @return The assigned partition number for the given term
	 */
	public static int getPartitionNumber (String term) {
		//TDOD: Implement this method
		
		term = term.toLowerCase();
		
		if(term.charAt(0) == 's' || term.charAt(0) == 'k' || term.charAt(0) == 'j' || term.charAt(0) == 'y')
			return 0;
		if(term.charAt(0) == 'p' || term.charAt(0) == 'n' || term.charAt(0) == 'o')
			return 1;
		if(term.charAt(0) == 'c' || term.charAt(0) == 'h')
			return 2;
		if(term.charAt(0) == 'd' || term.charAt(0) == 'e' || term.charAt(0) == 'f' || term.charAt(0) == 'q')
			return 3;
		if(term.charAt(0) == 'a' || term.charAt(0) == 'x' || term.charAt(0) == 'z' || term.charAt(0) == 'u'|| term.charAt(0) == 'w')
			return 4;
		if(term.charAt(0) == 'b' || term.charAt(0) == 'v' || term.charAt(0) == 'l' )
			return 5;
		if(term.charAt(0) == 'g' || term.charAt(0) == 'm' || term.charAt(0) == 'i' )
			return 6;
		if(term.charAt(0) == 'r' || term.charAt(0) == 't' )
			return 7;
		return 8;
	}
}
