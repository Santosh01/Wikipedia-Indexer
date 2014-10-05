package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.ACCENTS)
public class AccentRule implements TokenizerRule{

	
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		
		
		Pattern p;
		Matcher m;
		p = Pattern.compile("[^\\p{ASCII}]");
		
		if (stream != null) {
			String token;
			
			while(stream.hasNext()){
				
				token = stream.next();
				
				if(token != null){
					m = p.matcher(token);
					if(m.find()){

						if(token.length()<2)
							continue;
						token = Normalizer.normalize(token, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
						stream.previous();
						stream.set(token);
						stream.next();
						
						System.out.println(token);
//						if(stream.hasNext()){
//							stream.next();
//						}
//						else
//							break;
//						System.out.println("loop");
					}
//					else
//						if(stream.hasNext())
//						stream.next();// end inner if
					m.reset();
				}
				
				System.out.println("am looping");
				// end outer if
			}// end While
			stream.reset();
			System.out.println("lol");
		}
	}
	

}
