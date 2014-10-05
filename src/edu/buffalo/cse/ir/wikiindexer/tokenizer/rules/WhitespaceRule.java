/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

import java.util.ArrayList;
import java.util.Vector;

/**
 * @author nikhillo
 * 
 */
// example of annotation, for classes you write annotate accordingly

/*
 * (non-Javadoc)
 * 
 * @see
 * edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerRule#apply(edu.buffalo.
 * cse.ir.wikiindexer.tokenizer.TokenStream)
 */
@RuleClass(className = RULENAMES.WHITESPACE)
public class WhitespaceRule implements TokenizerRule {
	// @SuppressWarnings("unused")
	public void apply(TokenStream stream) throws TokenizerException {
		
		
		try{
			if (stream != null) {
				
//				System.out.println("inside whitespace");
				String token;
					while (stream.hasNext()) {
						
						token = stream.next();
						if (token != null) {
				
							token = token.replaceAll("\\r", "");
							token = token.replaceAll("\\t", "");
							token = token.replaceAll("\\n", "");
//							token = token.replaceAll("\\S+", "");
							
							String sp[] = token.split(" ");
							
							for(String i : sp){
								i = i.trim();
							}

							
							token = token.trim();
							// ninad code
							stream.previous();
							stream.set(sp);
							token = stream.next();

							
							if(stream.hasNext() == false){
								
								if(token != null){
									token = token.replaceAll("\\r", "");
									token = token.replaceAll("\\t", "");
									token = token.replaceAll("\\n", "");
//									token = token.replaceAll("\\S+", "");
									
									sp = token.split(" ");
									for(String i : sp){
										i = i.trim();
									}
									token = token.trim();
									stream.previous();
									stream.set(sp);
									break;
									
								}

							}

							// end code ninad
						}
						
					}
					stream.reset();
					


			}
			
		}
		catch(IndexOutOfBoundsException e){
			stream.getAllTokens();
			System.out.println(e);
			
		}
		

	}
	
}
	


/* santosh code 
ArrayList v = new ArrayList();
for (int i = 0; i < sp.length; i++) {
	if (sp[i].length() != 0) {
		v.add(sp[i]);

	}
}
stream.previous();

for (int i = 0; i < v.size(); i++) {    
stream.set(v.get(i)+"");
	stream.next();
	//stream.merge(stream);
//System.out.println(v.get(i));
	
	
	
}

System.out.println(stream.getAllTokens());
*/