package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

/**
 * @author nikhillo
 *
 */
//example of annotation, for classes you write annotate accordingly

/* (non-Javadoc)
 * @see edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerRule#apply(edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream)
 */
@RuleClass(className = RULENAMES.APOSTROPHE)
public class ApostropheRule implements TokenizerRule {
//@SuppressWarnings("unused")

public void apply(TokenStream stream) throws TokenizerException {
	if (stream != null) {
		String token;
		//Stemmer s;
		while (stream.hasNext()) { 
			token = stream.next();
		//	if (token != null) {
				if (token.isEmpty() == false) {
					
					if(token.length()>2 &&  token.charAt(token.length()-2) == '\'' &&  token.charAt(token.length()-1) == '\'' && token.charAt(token.length()-1) != ' ')
				       {
				         token=token.substring(0, token.length()-2);
				         
				       }else if(token.length()>2 && token.length()>1 && token.contains("' ") == true)
				       {
				         token=token.replaceAll("' ", " ");
				       }
//					if (token.charAt(token.length() - 1) == '\'') {
//						token = token.substring(0, token.length() - 1);
//					}
//					else if (token.charAt(0) == '\'') {
//						token = token.substring(1, token.length());
//					}
//					
				         @SuppressWarnings("unused")
						String word2 = "";

					if (token.indexOf("let's") >= 0) {
						token = "let";
						word2 = "us";
					}
					
				
					if (token.indexOf("'s") >= 0) {
						token = token.substring(0, token.length() - 2);
					}

					token = token.replace("' ", " ");
					if (token.contains("won't")) {
						word2 = "not";
						token = "will";
					}

					if (token.contains("shan't")) {
						word2 = "not";
						token = "shall";
					}

					if (token.contains("I'm")) {
						word2 = "am";
						token = "I";
					}

					if (token.contains("i'm")) {
						word2 = "am";
						token = "i";
					}

					int index = token.indexOf("n't");
					if (index >= 1) {
						word2 = "not";
						token = token.substring(0, index);
					}

					index = token.indexOf("'re");
					if (index >= 0) {
						word2 = "are";
						token = token.substring(0, index);
					}

					index = token.indexOf("'ve");
					if (index >= 0) {
						word2 = "have";
						token = token.substring(0, index);
					}

					index = token.indexOf("'d");
					if (index >= 0) {
						word2 = "would";
						token = token.substring(0, index);
					}

					index = token.indexOf("'ll");
					if (index >= 0) {
						word2 = "will";
						token = token.substring(0, index);
					}
					
					index = token.indexOf("'em");
					if (index >= 0) {
						word2 = "put";
						token="them";
						token = token.substring(0,index);
					}
										
					token=token.replaceAll("'", "");
					//token=token.replaceAll("(.*?)*\'", "");
					token=token.replaceAll("'$'", "");
					token=token.replaceAll("'$", "");
					
//					System.out.println(token);
					   if(token.length() !=0)
				        {
				    	   stream.previous();
							stream.set(token);
						stream.next();
						stream.set(word2);
						stream.next();
				        }
						     
//					if (word2.equals("")) {
//						stream.previous();
//						stream.set(token);
//						stream.next();
//					} else {
//						stream.previous();
//						stream.set(token);
//						stream.next();
//						stream.set(word2);
//						stream.next();
//						System.out.println(stream.getAllTokens());
					}

				}
			}
			stream.reset();
		}
}





//}
//
