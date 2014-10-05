/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerFactory;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;

/**
 * A Callable document transformer that converts the given WikipediaDocument object
 * into an IndexableDocument object using the given Tokenizer
 * 
 *
 */

	

public class DocumentTransformer implements Callable<IndexableDocument> {
	
	
	private Date tempPublishDate;
	private String tempAuthor;
	private int tempId;
	private String tempTitle;
	private List<Section> tempSections;
	private Set<String> tempLinks;
	private List<String> tempCategories;

	TokenStream author;
	TokenStream terms;
	TokenStream links;
	TokenStream categories;
	
	Tokenizer tr;
	Map<INDEXFIELD, Tokenizer> map;
	
	/**
	 * Default constructor, DO NOT change
	 * @param tknizerMap: A map mapping a fully initialized tokenizer to a given field type
	 * @param doc: The WikipediaDocument to be processed
	 */
	public DocumentTransformer(Map<INDEXFIELD, Tokenizer> tknizerMap, WikipediaDocument doc) {
		//TODO: Implement this method
		
//		TokenStream link_stream = new TokenStream();
		map = tknizerMap;
		tempAuthor = doc.getAuthor().toString();
//		System.out.println(tempAuthor);
		tempCategories =  doc.getCategories();
		tempPublishDate = doc.getPublishDate();
		tempId = doc.getId();
		tempTitle = doc.getTitle();
		tempSections = doc.getSections();
		tempLinks = doc.getLinks();
		
		// Terms addition
		terms = new TokenStream(tempTitle);
		for (int j = 0; j < tempSections.size(); j++) {
			Section i = tempSections.get(j);
			terms.append(i.getTitle());
			terms.append(i.getText());
		}

//		System.out.println(terms.getAllTokens());
//		System.out.println("terms added");
		// Links addition
//		String[] tlinks = new String[tempLinks.size()];
		
		Iterator i = tempLinks.iterator();
//		System.out.println(tempLinks.size());
		
		int j=0;
		String temp;
		while(i.hasNext()){
			temp = i.next().toString();
			if(temp !=null && !temp.equals("")){
				links = new TokenStream(temp);
				break;
			}
			
			
		}
		
		
		int count =0;
		while(i.hasNext()){
			
			temp = i.next().toString();
//			System.out.println(temp);
			links.append(temp);
//			System.out.println(count++);
//			System.out.println(i.next());

		}
//		System.out.println(links.getAllTokens());
		
//		System.out.println("links added");
		
		// Author addition
		
		author = new TokenStream(tempAuthor);
//		System.out.println(author.getAllTokens());
//		System.out.println("author added");
		// Category addition
		categories = new TokenStream(tempCategories.get(0));
		
		for( int k=1 ; k<tempCategories.size();k++){
			categories.append(tempCategories.get(k));
		}
//		System.out.println(categories.getAllTokens());
//		System.out.println("cat added");
	}
	
	/**
	 * Method to trigger the transformation
	 * @throws TokenizerException Inc ase any tokenization error occurs
	 */
	public IndexableDocument call() throws TokenizerException {
		// TODO Implement this method
		
		IndexableDocument idoc = new IndexableDocument();
		
//		Tokenizer termTokenizer=tknizerMap.get(INDEXFIELD.TERM); 
//		Applying Tokenizer
//		termTokenizer.tokenize(termStream); 
		
		tr = map.get(INDEXFIELD.AUTHOR);
		if(tr != null)
		tr.tokenize(author);

		tr = map.get(INDEXFIELD.LINK);
		if(tr != null)
		tr.tokenize(links);
		
		tr = map.get(INDEXFIELD.CATEGORY);
		if(tr != null)
		tr.tokenize(categories);
		
		tr = map.get(INDEXFIELD.TERM);
		if(tr != null)
		tr.tokenize(terms);
		
		idoc.addField(INDEXFIELD.AUTHOR, author);
		idoc.addField(INDEXFIELD.LINK, links);
		idoc.addField(INDEXFIELD.CATEGORY, categories);
		idoc.addField(INDEXFIELD.TERM, terms);
		idoc.setDoc_id(tempTitle);
//		idoc.addField(field, stream);
		
		return idoc;
	}
	
}
