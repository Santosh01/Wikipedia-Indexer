package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.CAPITALIZATION)
public class CapitalizationRule implements TokenizerRule{

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		
		if (stream != null) {
			String token;
			Pattern p = Pattern.compile("\\.");
			Matcher m ;
			int count = 0;
			while(stream.hasNext()){
				
				
				token = stream.next();
				m = p.matcher(token);
				char c;
				if(token != null){
					
					if(token.length()>1)
					if(Character.isUpperCase(token.charAt(1)))
						count = 1;
					
					if(count == 0){
						
						token = token.substring(0, 1).toLowerCase() + token.substring(1);
						stream.previous();
						stream.set(token);
						stream.next();
						count = 1;
					}
					
					if(m.find()){
						count = 0;
					}
					
					m.reset();
					
					
				}
			}
		}
	}
}