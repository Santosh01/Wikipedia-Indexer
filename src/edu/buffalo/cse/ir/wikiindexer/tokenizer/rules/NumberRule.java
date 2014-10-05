package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.NUMBERS)
public class NumberRule implements TokenizerRule{

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		
		if (stream != null) {
			String token;
			Pattern p = Pattern.compile("[0-9{8}]");
			Pattern p1 = Pattern.compile("[0-9,.]+");
			Matcher m ;
			Matcher m1;
			int count = 0;
			while(stream.hasNext()){
				
				token = stream.next();
				m = p.matcher(token);
				m1 = p1.matcher(token);
				
				if(token != null){
					
					if(!m.find()){
//						System.out.println("passed date rule");
						if(m1.find()){
//							System.out.println("inside number");
							stream.remove();
						}
						m.reset();
					}
				}
					

			}
		}
	}
}
