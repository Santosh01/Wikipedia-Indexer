/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.portable.Streamable;

/**
 * This class represents a stream of tokens as the name suggests.
 * It wraps the token stream and provides utility methods to manipulate it
 *
 *
 */
public class TokenStream implements Iterator<String>{
	
	private List<String> Tokens ;	


	Integer start_index = new Integer(0);
	Integer end_index = new Integer(0);
	public Integer current_index = new Integer(0);
	
	
	
	/**
	 * Default constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(StringBuilder bldr) {
		
		
		
		String temp_string = bldr.toString();
		Integer temp_value = new Integer(1);
		
		// Checking for Null or "" Strings
		if(bldr.toString().equalsIgnoreCase(""))
		{}
		else
		if(bldr == null)
		{}
		else
		if(bldr.toString().equals(null))
		{}
		
		// For all Strings we add a string in the List and in the Map
		else
		{
			// Instantiate the List and the Map
			Tokens = new LinkedList<String>();
			start_index = 0;
			end_index = 0;
			Tokens.add(temp_string);
			end_index++;

			
			
		}
		
		//TODO: Implement this method
	}
	
	/**
	 * Overloaded constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(String string) {
		//TODO: Implement this method
		
		Integer temp_value = new Integer(1);
		// Check for null or empty strings
		if(!(string == null || string.equalsIgnoreCase("") || string.equals(null))){
			
	
			
			// Instantiate the List and the Map
			Tokens = new LinkedList<String>();
//			Token_Map = new HashMap<String, Integer>();
//			Start_Iterator = Tokens.listIterator();
//			Stream_Iterator = Tokens.listIterator();
//			End_Iterator = Tokens.listIterator();
			
			Tokens.add(string);
			start_index = 0;
			end_index = 0;
			end_index++;
//			End_Iterator = Stream_Iterator;
//			Stream_Iterator = Start_Iterator;
			
//			Stream_Iterator = Tokens.listIterator();
//			Token_Map.put(string, temp_value);
			
			
			
		}			
	}
	
	/**
	 * Method to append tokens to the stream
	 * @param tokens: The tokens to be appended
	 */
	public void append(String... tokens) {
		
		if(tokens == null){
			return ;
		}
		
		if(tokens.equals("")){
			return ;
		}
		
		String temp_string ; 
//		Integer temp_value = new Integer(0);

		for (String iter : tokens){
			
			if(iter != null && !iter.equalsIgnoreCase("")){
				
				if(this.Tokens !=null){
					
					String temp;
					this.Tokens.add(iter);
					
					temp_string = iter;
					
				}


			}
			// Add to the List
			
			
			
			
		}
		
		//TODO: Implement this method
	}
	
	/**
	 * Method to retrieve a map of token to count mapping
	 * This map should contain the unique set of tokens as keys
	 * The values should be the number of occurrences of the token in the given stream
	 * @return The map as described above, no restrictions on ordering applicable
	 */
	public Map<String, Integer> getTokenMap() {
		//TODO: Implement this method
		
		if(Tokens == null){
			return null;
		}
		
		
		HashMap<String, Integer> TokenMap = new HashMap<String, Integer>();
		
		Integer temp = new Integer(1);
		
		for( String i : Tokens){
			
			if(TokenMap.containsKey(i)){
				
				temp = TokenMap.get(i);
				
				TokenMap.put(i, temp+1);
			}
			else{
				
				TokenMap.put(i, temp);
			}
			
		}
		
		return TokenMap;
	}
	
	/**
	 * Method to get the underlying token stream as a collection of tokens
	 * @return A collection containing the ordered tokens as wrapped by this stream
	 * Each token must be a separate element within the collection.
	 * Operations on the returned collection should NOT affect the token stream
	 */
	public Collection<String> getAllTokens() {
		//TODO: Implement this method
		
//		List<String> temp_tokens = new LinkedList<String>(Tokens);
//		return temp_tokens;
		
		return Tokens;
	}
	
	/**
	 * Method to query for the given token within the stream
	 * @param token: The token to be queried
	 * @return: THe number of times it occurs within the stream, 0 if not found
	 */
	public int query(String token) {
		//TODO: Implement this method
		
		if(token != null && (!token.equals(null)) && Tokens != null){
		
			int count = 0;
			for(String i : Tokens){
				
				if(i.equals(token)){
					count++;
				}
				
				
			}
			return count;
			
		}
		
		return 0;
		
	}
	
	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasNext() {
		// TODO: Implement this method
		
//		if(current_index == Tokens.size()-1){
//			return false;
//		}

		if(Tokens == null){
			return false;
		}
		
		if(current_index <= Tokens.size()-1)
			return true;
		else
			return false;
	}
	
	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasPrevious() {
		//TODO: Implement this method
		
//		if(start_index == current_index-1)
//		return false;
		
		if(Tokens == null)
		{
			return false;
		}
		
		
		if(current_index > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Iterator method: Method to get the next token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String next(){

		

		if(Tokens == null){
			return null;
		}

//		if(hasNext())
//			return Tokens.get(current_index++);
//		else
//			if(current_index == Tokens.size()-1){
//				
//				return Tokens.get(current_index++);
//				
//			}
//			else
//			return null;
		
		
		if(current_index < Tokens.size()){
			return Tokens.get(current_index++);
		}
		else
			return null;
		
		// TODO: Implement this method

	}
	
	/**
	 * Iterator method: Method to get the previous token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String previous() {
		//TODO: Implement this method
		

		if(Tokens == null){
			return null;
		}
		
		if(hasPrevious()){
			return Tokens.get(--current_index);
		}
		else
			return null;
	}
	
	/**
	 * Iterator method: Method to remove the current token from the stream
	 */
	public void remove() {

		if(Tokens == null){
			return ;
		}
		
		if(current_index == Tokens.size()){
			return ;
		}

		int linked_list_index = current_index.intValue();
		
		Tokens.remove(linked_list_index);

		// TODO: Implement this method
		
	}
	
	/**
	 * Method to merge the current token with the previous token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the previous one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithPrevious() {
		//TODO: Implement this method
		
		if(hasPrevious()){
			
			if(this.getAllTokens().size()<2){
				return false;
			}
			
				String temp1 = new String();
				String temp2 = new String();
				
				temp1 = Tokens.get(current_index);
				temp2 = Tokens.get(--current_index);
				
				Tokens.remove(current_index.intValue());
				Tokens.remove(current_index.intValue());
				
				String temp3 = temp2 + " " + temp1;
				Tokens.add(current_index, temp3);

				return true;
		}
		
		return false;
		
		
	}
	
	/**
	 * Method to merge the current token with the next token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the current one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithNext() {
		//TODO: Implement this method
		
		if(hasNext()){
			
			if(this.getAllTokens().size()<2){
				return false;
			}
			
			String temp1 = new String();
			String temp2 = new String();
			
			temp1 = Tokens.get(current_index);
			temp2 = Tokens.get(current_index+1);
			

			Tokens.remove(current_index.intValue());
			Tokens.remove(current_index.intValue());
			String temp3 = temp1+" "+temp2;
			Tokens.add(current_index, temp3);

			
			
			
			return true;
		}
		return false;
	}
	
	/**
	 * Method to replace the current token with the given tokens
	 * The stream should be manipulated accordingly based upon the number of tokens set
	 * It is expected that remove will be called to delete a token instead of passing
	 * null or an empty string here.
	 * The iterator should point to the last set token, i.e, last token in the passed array.
	 * @param newValue: The array of new values with every new token as a separate element within the array
	 */
	public void set(String... newValue) {
		//TODO: Implement this method
		
		if(Tokens == null){
			return;
		}
		if(newValue == null){
			return;
		}

		if(newValue[0]== null || newValue[0]=="" || newValue[0].equals(null)){
			return;
		}
		
		
		if(current_index == Tokens.size()){
//			System.out.println("end of stream");
			this.append(newValue);
			return;
		}
		
		if(current_index == 0){
			
//			System.out.println("start element");
//			System.out.println(this.getAllTokens());
			
			this.remove();
			
			for(int i=newValue.length-1; i>=0; i--){
				
				if(!newValue[i].equals(""))
				Tokens.add(0,newValue[i].trim());  // added a trim here you can remove this later if something breaks
			}
			
			current_index += newValue.length-1;
			return;
		}
		
		if(current_index == Tokens.size()-1){
			
//			System.out.println("last element");
			
			
			this.remove();
			
			for(String s : newValue){
				
				if(!s.equals("")){
					Tokens.add(s);
				}
			}
			
			current_index += newValue.length-1;
//			this.append(newValue);
//			System.out.println(this.getAllTokens());
			return;
		}

		if(newValue.length>0){
		
			boolean first_ele = true;
//			System.out.println(this.getAllTokens());
			int count = current_index;
			for(String s : newValue){
			
				
				if(!s.equals("")){

//					System.out.println("proper set");

						if(first_ele){
							this.remove();
							first_ele = false;
						}
							
						
						Tokens.add(count++,s);
						
					
						
						
				}
				
				
			}
			current_index = count-1;
//			System.out.println(this.getAllTokens() + "ii " + current_index);
			
			
			
					
		}
		else
			return;
		
//		
//		if(newValue.length>0){
//			
//			ArrayList<String> ls = new ArrayList<String>();
//			
//			for(int i=0;i<newValue.length;i++){
//				ls.add(newValue[i]);
//			}
//			if(ls.size()>0){
//				this.remove();
//				int count = current_index;
//				for(String x : ls){
//						Tokens.add(count++, x);
//						System.out.println(current_index + " " + x + " ");
//				}
//				current_index += ls.size()-1;
//			}
//		
//		}
//		else
//			return;
		
	}
	
	/**
	 * Iterator method: Method to reset the iterator to the start of the stream
	 * next must be called to get a token
	 */
	public void reset() {
		
		if(Tokens == null){
			return;
		}
		current_index = 0;
		//TODO: Implement this method
	}
	
	/**
	 * Iterator method: Method to set the iterator to beyond the last token in the stream
	 * previous must be called to get a token
	 */
	public void seekEnd() {
		
		if(Tokens == null){
			return;
		}
		
		current_index = Tokens.size();
	}
	
	/**
	 * Method to merge this stream with another stream
	 * @param other: The stream to be merged
	 */
	public void merge(TokenStream other) {
		
		if(other != null){
			
		
		
//		if(other.equals(null)){
//			return ;
//		}
//		
		if(Tokens == null){
			
			Tokens = new LinkedList<String>();
			
			
			while(other.hasNext()){
				
				Tokens.add(current_index, other.next());
			}
			
//			Tokens.addAll(other.Tokens);
			return ;
		}	
		
		
		while(other.hasNext()){
			
//			System.out.println(this.getAllTokens());
			Tokens.add(Tokens.size(),other.next());
			
		}
//		
//		String temp[] = new String[other.Tokens.size()];
//		
//		for(int i=0; i<other.Tokens.size() ; i++){
//			temp[i] = other.Tokens.get(i);
//		}
//		
//		for(String x : temp){
//			this.append(x);
//		}
//		

	}
		//TODO: Implement this method
	}
}
