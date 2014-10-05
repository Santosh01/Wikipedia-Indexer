package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.HYPHEN)
public class HyphenRule implements TokenizerRule {

	
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		
		if (stream != null) {
			String token;
			
			while(stream.hasNext()){
				
				token = stream.next();
				
				if(token != null){

					boolean flag = false;
					Pattern p = Pattern.compile("([a-zA-Z ]*)([-]+)([a-zA-Z ]*)");
//					
					Matcher m = p.matcher(token);
					
//					
//					System.out.println(token);
					if(m.matches()){
						
						
//						System.out.println(token);
						token = token.replaceAll("[-]+", " ");
//						System.out.println(token);
						flag = true;
						
					}
					token = token.trim();
					if(flag){
						
						if(token.equals("")){
							stream.previous();
							stream.remove();
						}
						else{
							stream.previous();
							stream.set(token);
						}
					}
					
				}
				
			}
				
			
			
			stream.reset();
		}

		
	}

}
