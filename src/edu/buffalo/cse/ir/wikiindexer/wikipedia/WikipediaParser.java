                                        
/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *  This class implements Wikipedia markup processing. Wikipedia
 *         markup details are presented here:
 *         http://en.wikipedia.org/wiki/Help:Wiki_markup It is expected that all
 *         methods marked "todo" will be implemented by students. All methods
 *         are static as the class is not expected to maintain any state.
 */
public class WikipediaParser {
	/* TODO */
	/**
	 * Method to parse section titles or headings. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * 
	 * @param titleStr
	 *            : The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	WikipediaDocument wd;
	static int count =0;
	Collection<WikipediaDocument> docs;
	

	
/* ---------------- START -> WikipediaParser Constructor  ---------------- */
	public WikipediaParser(int id, String timestamp, String username, String title,String text,Collection<WikipediaDocument> docs){

		count++;
		this.docs = docs;
		try{
			
			
			/* Create New WikiDoc */
			wd= new WikipediaDocument(id, timestamp, username, title);
			ArrayList<String[]> wiki_links = new ArrayList<String[]>();
			String x[] = new String[2];
			
			String temp = text;
			StringBuffer sb = new StringBuffer();
			text = parseTextFormatting(text);
			text = parseTagFormatting(text);
			text = parseListItem(text);
			text = parseTemplates(text);
			

/* ----------------- START -> Links addition ----------------- */
			
			/* -------- START -> Wikipedia links -------- */

			Pattern p = Pattern.compile("(\\[\\[)([a-z|,()'\\- A-Z0-9]*)(\\]\\])");
			Matcher m = p.matcher(text);
			
			int count=0;
			while(m.find()){

				x= parseLinks(m.group(1)+m.group(2)+m.group(3));
				text = text.replaceFirst("\\[\\[([a-z|,()'\\- A-Z0-9]*)\\]\\]", x[0]);
				count++;
				
				if(x[1].equals("")){
					x[1]=LinkProc(x[0]);
				}
				
				wd.addLink(x[1]);
			}

			text = text.replaceAll("\\[\\[", "");
			text = text.replaceAll("\\]\\]", "");
			
			/* -------- END -> Wikipedia links -------- */
			
			/* -------- START -> External links -------- */
			p = Pattern.compile("(\\[)([a-z|()'.\\-_ A-Z0-9\\:\\/]*)(\\])");
			m = p.matcher(text);
			
			while(m.find()){

				x= parseLinks(m.group(1)+m.group(2)+m.group(3));
				text = text.replaceFirst("\\[([a-z|()'.\\-_ A-Z0-9\\:\\/]*)\\]", x[0]);

				wd.addLink(x[1]);
			}
			text = text.replaceAll("\\[", "");
			text = text.replaceAll("\\]", "");
			
			/* -------- END -> External Links -------- */
			
/* ---------------- END -> Links addition ---------------- */
			
/* --------------- START -> Categories addition --------------- */
			
			p = Pattern.compile("(Category:)([\\w\\ \\d]*)");
			m = p.matcher(text);
			while(m.find()){
				wd.addCategory(m.group(2));
				text = text.replaceAll("(Category:)([\\w\\ \\d\\W]*)","");
			}
			
/* ---------------- END -> Categories addition ---------------- */			
			
			
/* --------------- START -> Sections addition --------------- */
		
	try{
				
		text = text.replaceAll("[=]{2,6}","=\\$=");
			

			int start_index=0,end_index=text.length();
			
			String s_title ="";
			String s_content ="";
			while(start_index!=end_index && text.indexOf("=$")!=-1)
			{
				if(text.indexOf("=$=")==0)	
				{
					start_index = text.indexOf("=$=")+1;
					text=text.substring(start_index,end_index);
					end_index=text.length();

					s_title=text.substring(0,text.indexOf("$="));
					start_index=text.indexOf("=$=")+3;
					text=text.substring(start_index,end_index);
					end_index=text.length();

					if(text.indexOf("=$=")<0)
						s_content=text.substring(0,end_index);
					else

						s_content=text.substring(0,text.indexOf("=$"));
					s_content = s_content.trim();

					if(text.indexOf("=$=")>0)
						text=text.substring(text.indexOf("=$="),end_index);
					end_index=text.length();
					
					wd.addSection(s_title, s_content);
				}else
				{
					s_title="Default";
					s_content=text.substring(0,text.indexOf("=$=")-1);
					text=text.substring(text.indexOf("=$="),end_index);
					end_index=text.length();
					wd.addSection(s_title, s_content);
					}
			}
			
		}catch(Exception e){
			System.out.println(e);
		}
/* --------------- START -> Sections addition --------------- */			
		
			
/* Adding WikiDoc to the collection  */
			add(wd,docs); 
	
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception occured at doc # : " + count);
		}
}
	
	
/* ---------------- END -> WikipediaParser Constructor  ---------------- */
	
	public static String parseSectionTitle(String titleStr) {

		/* Santosh's code */

		if (titleStr == null)
			return null;
		if (titleStr == "")
			return "";

		String result = titleStr.replaceAll("====== ", "");
		result = result.replaceAll(" ======", "");
		result = result.replaceAll("===== ", "");
		result = result.replaceAll(" =====", "");
		result = result.replaceAll("==== ", "");
		result = result.replaceAll(" ====", "");
		result = result.replaceAll(" ===", "");
		result = result.replaceAll("=== ", "");
		result = result.replaceAll(" ==", "");
		result = result.replaceAll("== ", "");

		return result;
	}

	/* Santosh's code */

	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * 
	 * @param itemText
	 *            : The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		/* santosh's code */

		if (itemText == null)
			return null;
		if (itemText == "")
			return "";
		
		
		String result = new String();
		
		result = itemText.replaceAll("^[\\*]* ", "").trim();
		result = result.replaceAll("^[\\#]* ", "").trim();
		result = result.replaceAll("^[\\:]* ", "").trim();
		
		result = result.replaceAll("\n[\\*]* ", "\n").trim();
		result = result.replaceAll("\n[\\#]* ", "\n").trim();
		result = result.replaceAll("\n[\\:]* ", "\n").trim();
		
		result = result.replaceAll("\\*\\[", "\\[").trim();
		result = result.replaceAll("\\n\\*", "");
		result = result.trim();
		return result;

	}

	/* Santosh's code */

	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		/* Santosh's code */

		if (text == null)
			return null;
		if (text == "")
			return "";
		String result = text.replaceAll("'''''", "");
		result = result.replaceAll("''''", "");
		result = result.replaceAll("'''", "");
		result = result.replaceAll("''", "");

		if (result.indexOf("{{Smallcaps|") >= 0) {
			result = result.replaceAll("{{Smallcaps|", "");
			result = result.replaceAll("}}", "");
		}

		if (result.indexOf("[[Help:Template|") >= 0) {
			result = result.replaceAll("[[Help:Template|", "");
			result = result.replaceAll("]]", "");
		}

		result = result.replaceAll("<code>", "");
		result = result.replaceAll("</code>", "");
		int index = -1;

		index = result.indexOf("<syntaxhighlight");

		while (index >= 0) {
			for (int i = index; i < index + 40; i++) {
				if (result.charAt(i) == '>') {
					result = result.substring(index, i);
					break;

				}
			}
			index = result.indexOf("<syntaxhighlight");

		}
		result = result.replaceAll("</syntaxhighlight>", "");
		result = result.replaceAll("<small>", "");
		result = result.replaceAll("</small>", "");
		result = result.replaceAll("<big>", "");
		result = result.replaceAll("</big>", "");
		result = result.replaceAll("&nbsp;", " ");

		result = result.replaceAll("<tt>", "");
		result = result.replaceAll("</tt>", "");

		return result;
	}

	/* santosh's code */

	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz> For most
	 * cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed.
	 */

	public static String parseTagFormatting(String text) {
		/* santosh's code */
		if (text == null)
			return null;
		if (text == "")
			return "";
		String result = text.toString().replaceAll("\\<.*?>", "");
		result = result.replaceAll("&lt;.+?&gt;", "");
		result = result.replaceAll("&\\w+?;", "");
		result = result.trim();
		result = result.replaceAll("  ", " ");


		return result;
	}

	/* santosh's code */

	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags For
	 * most cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {
		
		String result=text;
		
		result = result.replaceAll("\\n\\|","");
		result = result.replaceAll("\\n\\}\\}","\\}\\}");
		result = result.replaceAll("\\n\\}","\\}");
		result = result.replaceAll("\\{\\{(.)*\\}\\}", "");
		result = result.replaceAll("\\{(.)*\\}", "");
		result = result.replaceAll("(\\n)+","\n");
		result = result.trim();

		return result;
	}

	/* TODO */
	/**
	 * Method to parse links and URLs. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return An array containing two elements as follows - The 0th element is
	 *         the parsed text as visible to the user on the page The 1st
	 *         element is the link url
	 */
	public static String LinkProc(String text){
		
		try{
			
			if(text == null)
				return "";

			if(text.equalsIgnoreCase(""))
				return "";
			//auto capatilization
			char temp;
			String x;
			text = text.replaceAll("\\[\\[", "");	
			
			//replacing ]] brackets	
			text = text.replaceAll("\\]\\]", "");
			text = text.replaceAll("\\|", "");
			temp = text.charAt(0);
			temp = Character.toUpperCase(temp);
			x = new String(temp+text.substring(1,text.length()));
			
			//replacing space with underscore
			x = x.replaceAll(" ","_");


			
			return x;
		}catch(StringIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String TextProc(String text){
		
		String array[] = new String[2];
		try{
			
			if(text == null)
				return "";
			
			if(text.equalsIgnoreCase(""))
				return "";
			//removing the Namespace
			text = text.replaceAll("\\[\\[(.*?)\\:","");
			

			//removing after parenthesis
			text = text.replaceAll("\\((.*?)\\)", "");
			
			
			//removing after the comma

			array = text.split("\\,");
			if(array.length>1)
				text = array[0];			

			//removing the no-wiki tag
			text = text.replaceAll("<nowiki />","");
			
			
			
			//replacing [[ brackets
			text = text.replaceAll("\\[\\[", "");	
			//replacing ]] brackets	
			text = text.replaceAll("\\]\\]", "");
			text = text.replaceAll(",", "");
			text = text.replaceAll("\\|", "");
			text = text.trim();

			return text;
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	public static String[] parseLinks(String text) {



		String Links[] = new String[2];
		Links[0]="";
		Links[1]="";
		String Temp[] = new String[2];
		Temp[0]="";
		Temp[1]="";
		
		Pattern p ;
		Matcher m ;
		
		//Section 0
		// if input string is null
		if(text == null){
			//System.out.println("Case 0");
			Links[0] = "";
			Links[1] = "";
			return Links;
		}
			
		if(text.equalsIgnoreCase("")){
			//System.out.println("Case 0");
			Links[0] = "";
			Links[1] = "";
			return Links;
		}
		
		
		// Section 0
		// check for external link
		
		if(text.charAt(0)=='[' && text.charAt(1)!='['){
			
			String array[] = new String[2];
			
			StringBuffer temp = new StringBuffer("");
			array = text.split("\\ ");
			
			
			if(array.length>1){
				

				
				int i=0;
				
				for(i=1;i<array.length;i++){
					temp.append(array[i]+" ");
				}
				
				String x = temp.toString();
				x = x.trim();
				Links[0] = x;
//				Links[0]= array[1];
				Links[0] = Links[0].replaceAll("\\]","");
				
				Links[1]="";
				return Links;
			}
			else{
				Links[0]="";
				Links[1]="";
				return Links;
			}
		}
		
		
		// Section 1
		// Check if colon : exists or not
		
		p = Pattern.compile("\\:");
		m = p.matcher(text);
		
		// Colon Found
		if(m.find()){
			//System.out.println("This has colon in it");
			
			//check for separator |
			p = Pattern.compile("\\|");
			m = p.matcher(text);
			
			// with No separator
			if(!m.find()){
				
				// match with Wiktionary: tag else discard
				
				p = Pattern.compile("Wiktionary\\:");
				m = p.matcher(text);
				
				if(m.find()){
					
					Links[0] = text;
					Links[1] = "";
					
					Links[0] = ("Wiktionary:") + TextProc(Links[0]);
					
					return Links;
					
				}
	
				
				// Categories
				p = Pattern.compile("Category\\:");
				m = p.matcher(text);
				
				if(m.find()){
					
					Links[0] = text;
					Links[1] = "";
					
					//System.out.println(text);
					Links[0] =  TextProc(Links[0]);
					//System.out.println(Links[0]);
					return Links;
					
				}

				
				// Categories links
				p = Pattern.compile("\\:Category\\:");
				m = p.matcher(text);
				
				if(m.find()){
					
					Links[0] = text;
					Links[1] = "";
					
					Links[0] = ("Category:") + TextProc(Links[0]);
					return Links;
					
				}
				
				//media
				p = Pattern.compile("media\\:");
				m = p.matcher(text);
				
				if(m.find()){
					
					Links[0] = "";
					Links[1] = "";
					return Links;
					
				}
				
				p = Pattern.compile("File\\:");
				m = p.matcher(text);
				
				if(m.find()){
					
					Links[0] = "";
					Links[1] = "";
					return Links;
					
				}
				
				
				String array[] = new String[2];
				array = text.split("\\:");
				
				//System.out.println(array[0]);
				Links[0] = text;
				
				Links[0] = TextProc(array[0]) + ":" +  TextProc(Links[0]);
				Links[1] = "";
				
				
				
				
				
					
			}
			// With a | separator
			else{
				
				String array[] = new String[2];
				array = text.split("\\|(.*?)");
				
				

				
				// no string after |
				if(array[1].charAt(0)==']'){
					
					 Links[1] = "";

					 //checking Wiltionary
					 p = Pattern.compile("Wiktionary\\:");
					 m = p.matcher(text);
					 
					 if(m.find()){
						 Links[0]=array[0];
						 Links[0] = TextProc(Links[0]);
						 return Links;
					 }
					 
					 //checking Wikipedia
					 p = Pattern.compile("Wikipedia\\:");
					 m = p.matcher(text);
					 
					 if(m.find()){
						 String array1[] = new String[2];
						 array1 = array[0].split("\\[\\[Wikipedia\\:");
						 Links[0]=array1[1];
						 Links[0] = TextProc(Links[0]);
						 
						 p = Pattern.compile("#");
						 m = p.matcher(text);
						 if(m.find()){
							 Links[0] = "Wikipedia:" + Links[0];
						 }
						 
						 return Links;
					 }
					 
					 Links[0]="";
					 Links[1]="";
					 return Links;
				}
				// a string is present after |
				else{
					
					String t_array[] = new String[2];
					t_array[0]="";
					t_array[1]="";
					
					String temp = array[array.length-1];
					//System.out.println(temp);
					
//					
//					System.out.println(array[1]);
//					t_array = array[1].split("\\|");
//					System.out.println(t_array.length);
//					while(t_array.length>1){
//						
//						System.out.println("inside while");
//						t_array = t_array[1].split("\\|");
//						//System.out.println(t_array[1]);
//						
//					}
//					
					
					Links[1]="";
					
					Links[0]=temp;
					
					Links[0]=TextProc(Links[0]);
					//System.out.println(Links[0]);
					return Links;
				}
					
				
				
			}
			return Links;
		}
		
		
		// ============================================================================================================================================================
		// handling cases where there is no colon in the text
		// Colon Not Found
		else{
			
			if(text.charAt(0)=='[' && text.charAt(1)=='['){
				
					//Sub Section 1
					// checking if pipe exists
					
					p = Pattern.compile("\\|");
					m = p.matcher(text);
					
					// Pipe Found
					if(m.find()){
						
						Temp = text.split("\\|");
						
						// A | B
						// checking if B is null 
		//				System.out.println(Temp[0]);
						if(Temp[1].equals("]]")){
							Links[0] = TextProc(text);
							Links[1] = LinkProc(text);
						}
						else{
							Links[0]=TextProc(Temp[1]);
							Links[1]=LinkProc(Temp[0]);
						}
					}
					// Pipe Not Found
					else{
						Links[0] = TextProc(text);
						Temp = text.split("\\[\\[");
						Links[1] = LinkProc(Temp[0]);
					
				}
			}
					
			else{
				
					// if no pipe
				p = Pattern.compile("\\|");
				m = p.matcher(text);
				
				// Pipe Not Found
				if(!m.find()){
					
					String array[] = new String[2];
					String array1[] = new String[2];
					
					array = text.split("\\[\\[");
					//System.out.println(array[0]+" "+array[1]);
					array1 = array[1].split("\\]\\]");
					//System.out.println(array1[0]+" "+array1[1]);
					
					
					if(array1.length==1){
						Links[0]=array[0]+array1[0];
						Links[1]=array1[0];
					}						
					else{
						Links[0] = array[0]+array1[0]+array1[1];
						Links[1] = array1[0];
					}
						
					
					Links[0] = TextProc(Links[0]);
					Links[1] = LinkProc(Links[1]);
					
//					System.out.println(Links[1]);
					//System.out.println(Links[0] + Links[1]);
					
				}
				else{
					String array[] = new String[2];
					String array1[] = new String[2];
					String array2[] = new String[2];
					
					array[0]="";array[1]="";
					array1[0]="";array1[1]="";
					array2[0]="";array2[1]="";
					
					array = text.split("\\[\\[");
					array1 = array[1].split("\\|");
					array2 = array1[1].split("\\]\\]");
					
					if(array2.length>1){
						Links[0]=array[0]+array2[0]+array2[1];
						Links[1]=array1[0];	
					}
					else{
						Links[0]=array[0]+array2[0];
						Links[1]=array1[0];
					}
					
					Links[0] = TextProc(Links[0]);
					Links[1] = LinkProc(Links[1]);
					
				}
					
					
					
				}
						
					
			}
			
			
			return Links;
		}
		
		
	
	// method to add documents
	
	private synchronized void add(WikipediaDocument doc, Collection<WikipediaDocument> documents) {
		documents.add(doc);
	}
		
		
		
	

}
