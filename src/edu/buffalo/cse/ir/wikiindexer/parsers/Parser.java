/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * 
 *
 */
class XMLParsedDoc{
	
	int id;
	String timestamp;
	String username;
	String title;
	String text;
	
	XMLParsedDoc(){
		id = 0;
		timestamp = null;
		username = null;
		title = null;
		text = null;
		
	}
	
	XMLParsedDoc(StringBuilder id,StringBuilder timestamp,StringBuilder username,StringBuilder title, StringBuilder text){
		Integer i = new Integer(id.toString());
		
		this.id = i;
		this.timestamp = timestamp.toString();
		this.username = username.toString();
		this.title = title.toString();
		this.text = text.toString();
		
	}
	

	
}

public class Parser extends DefaultHandler {
	/* */
	private final Properties props;
	
	ArrayList<XMLParsedDoc> pages = new ArrayList<XMLParsedDoc>();
	
	 boolean FLAG_title=false;
	 boolean FLAG_text=false;
	 boolean FLAG_id=false;
	 boolean FLAG_first_id=false;
	 boolean FLAG_timestamp=false;
	 boolean FLAG_username=false;
	 boolean FLAG_ip=false;
	
	
	final String sep = "==============================================================================================";
	
	StringBuilder sb_title = new StringBuilder("");
	StringBuilder sb_text = new StringBuilder("");
	StringBuilder sb_id = new StringBuilder("");
	StringBuilder sb_timestamp = new StringBuilder("");
	StringBuilder sb_username = new StringBuilder("");
	StringBuilder sb_ip = new StringBuilder("");
	StringBuilder first_id = new StringBuilder("0");
	
	/**
	 * 
	 * @param idxConfig
	 * @param parser
	 */
	public Parser(Properties idxProps) {
		props = idxProps;
	}
	


	@Override
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException{
		
		//title
		if(elementName.equalsIgnoreCase("title")){
			sb_title.setLength(0);
			FLAG_title=true;
			FLAG_first_id=true;
		}
		//text
		if(elementName.equalsIgnoreCase("text")){
			sb_text.setLength(0);
			FLAG_text=true;
		}
		//id
		if(elementName.equalsIgnoreCase("id")){
			sb_id.setLength(0);
			
			FLAG_id=true;
			
		}
		//timestamp
		if(elementName.equalsIgnoreCase("timestamp")){
			sb_timestamp.setLength(0);
			FLAG_timestamp=true;
		}
		//username or ip
		if(elementName.equalsIgnoreCase("username")){
			sb_username.setLength(0);
			FLAG_username=true;
		}
		if(elementName.equalsIgnoreCase("ip")){
			sb_ip.setLength(0);
			FLAG_ip=true;
		}

		
	}
	
	public void endElement(String s, String s1, String element){
		//title
		if(FLAG_title){
//			System.out.println(sb_title.toString());
//			System.out.println(sep);
			FLAG_title=false;
			FLAG_first_id = true;
//			sb_title.setLength(0);
		}

		//text
		if(FLAG_text){
//			System.out.println(sb_text.toString());
//			System.out.println(sep);
			FLAG_text=false;
//			sb_text.setLength(0);
		}

		//id
		
		if(FLAG_id){
			
			if(FLAG_first_id){
//				System.out.println(sb_id.toString());
//				System.out.println(sep);
//				System.out.println(first_id);
				
				first_id = sb_id;
			}					
			FLAG_id=false;
			FLAG_first_id=false;
//			sb_id.setLength(0);
			
		}
		
		
		//timestamp
		if(FLAG_timestamp){
//			System.out.println(sb_timestamp.toString());
//			System.out.println(sep);
			FLAG_timestamp=false;
//			sb_timestamp.setLength(0);
		}
		
		//username or ip
		if(FLAG_username){
//			System.out.println(sb_username.toString());
//			System.out.println(sep);
			FLAG_username=false;
//			sb_username.setLength(0);
		}
		if(FLAG_ip){
//			System.out.println(sb_ip.toString());
//			System.out.println(sep);
			FLAG_ip=false;
//			sb_ip.setLength(0);
		}
		
		if(element.equalsIgnoreCase("page")){

			
			if(sb_username.toString()=="" || sb_username.length()==0)
				sb_username=sb_ip;
//			XMLParsedDoc dc = new XMLParsedDoc(sb_id,sb_timestamp,sb_username,sb_title,sb_text);
			XMLParsedDoc dc = new XMLParsedDoc(first_id,sb_timestamp,sb_username,sb_title,sb_text);
			pages.add(dc);

		}

		
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		
		if(FLAG_title){
		sb_title.append(ch, start, length);				
		}
		if(FLAG_text){
		sb_text.append(ch, start, length);				
		}
		if(FLAG_id){
		sb_id.append(ch, start, length);				
		}
		if(FLAG_timestamp){
		sb_timestamp.append(ch, start, length);				
		}
		if(FLAG_username){
		sb_username.append(ch, start, length);				
		}
		if(FLAG_ip){
		sb_ip.append(ch, start, length);				
		}
		
	}
	
	
	
	
	
	/* TODO: Implement this method */
	/**
	 * 
	 * @param filename
	 * @param docs
	 */
	public void parse(String filename, Collection<WikipediaDocument> docs) {
		
//=========================================================================================================

		
		System.out.println("Entering Parser");
		// Test Case #0
		if(filename == null){
			return;
		}
		
		//Test Case #1
		if(filename.equals("")){
			return;
		}
		
		//Test Case #2

		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			
			saxParser.parse(filename,this);
			
			//System.out.println(sb_text);
			
//			String time_start = String.valueOf(S());
			
			long timeStart;
			long endStart;
			
			timeStart = System.currentTimeMillis();
			
			WikipediaDocument wd; 
			WikipediaParser wp;
			
			java.util.Iterator<XMLParsedDoc> i = pages.iterator();
			int count =0;
			
			while(i.hasNext()){
			
				XMLParsedDoc current = i.next();
			
				 wp= new WikipediaParser(current.id,current.timestamp,current.username, current.title,current.text,docs);
				 count++;
			}

			System.out.println("Number of XMLParsedDoc : "  +  count);
			endStart = System.currentTimeMillis();
		
//			System.out.println(endStart - timeStart);
		 // end of DefaultHandler
	
			
			
			
			
		} // end try
		catch(java.io.FileNotFoundException e){
			return;
		}
		catch(Exception e)
		{
//			
//			String s = e.toString();
//			if(e.equals("java.io.FileNotFoundException"))
//				return;
			System.out.println(e);
			e.printStackTrace();
		}// end catch	
		
//=========================================================================================================
		
		
		System.out.println("Exiting Parser");
		
	}
	
	/**
	 * Method to add the given document to the collection.
	 * PLEASE USE THIS METHOD TO POPULATE THE COLLECTION AS YOU PARSE DOCUMENTS
	 * For better performance, add the document to the collection only after
	 * you have completely populated it, i.e., parsing is complete for that document.
	 * @param doc: The WikipediaDocument to be added
	 * @param documents: The collection of WikipediaDocuments to be added to
	 */
	private synchronized void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
		documents.add(doc);
	}
}
