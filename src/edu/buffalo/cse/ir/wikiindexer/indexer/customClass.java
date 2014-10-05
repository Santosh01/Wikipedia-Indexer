package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.util.Properties;

import edu.buffalo.cse.ir.wikiindexer.FileUtil;
import edu.buffalo.cse.ir.wikiindexer.indexer.IndexReader;

public class customClass {
	
	
	public static void main(String args[]){
		

		try{
		

			Properties p = FileUtil.loadProperties("F:\\Workspace_eclipse\\wikiindexer-master_01_oct\\files\\properties.config");
			
			IndexReader iterm;
			IndexReader iauth;
			IndexReader icat;
			IndexReader ilink;
			
//			IndexReader myReader = new IndexReader(p, INDEXFIELD.TERM);
			
			iterm = new IndexReader(p, INDEXFIELD.TERM);
			iauth = new IndexReader(p, INDEXFIELD.AUTHOR);
			icat = new IndexReader(p, INDEXFIELD.CATEGORY);
			ilink = new IndexReader(p, INDEXFIELD.LINK);
			
			
			System.out.println("number of terms : "+iterm.getTotalKeyTerms());
			System.out.println("number of authors : "+iauth.getTotalKeyTerms());
			System.out.println("number of categories: "+icat.getTotalKeyTerms());
			System.out.println("number of links: "+ilink.getTotalKeyTerms());
			System.out.println("number of links in the dictionary " + iterm.getTotalValueTerms());
			
			System.out.println(iterm.getPostings("career"));
			System.out.println(iterm.getTopK(4));
			
//			System.out.println(iauth.getPostings("impresario"));
//			System.out.println(iauth.getTopK(1000));
			
//			System.out.println(ilink.getPostings("Largs"));
//			System.out.println(ilink.getTopK(1000));
			
		}catch(Exception e){
			
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	

}
