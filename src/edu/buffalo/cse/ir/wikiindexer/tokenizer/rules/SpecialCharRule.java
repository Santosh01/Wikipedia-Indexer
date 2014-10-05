package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.ArrayList;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

/**
 * /**
 * 
 * @author nikhillo
 * 
 */
// example of annotation, for classes you write annotate accordingly
@RuleClass(className = RULENAMES.SPECIALCHARS)
public class SpecialCharRule implements TokenizerRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerRule#apply(edu.buffalo
	 * .cse.ir.wikiindexer.tokenizer.TokenStream)
	 */

	public void apply(TokenStream stream) {
		if (stream != null) {
			String token;
			Stemmer s;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {

					// System.out.println("======test===    "+token.toString());
					String print = "";

					String sp[] = token.split("[~!@#<>$*&%^+=\\\\;:/_|()]");
				
					for (int i = 0; i < sp.length; i++) {
						if (sp[i].length() != 0) {

						/*	if (sp[i].contains("-")) {
								try {
									String a = sp[i].replaceAll("-", "");
									Integer.parseInt(a);

								} catch (Exception e) {
									sp[i] = sp[i].replaceAll("-", " ");
								}
							}*/ 
								
								sp[i]=sp[i].replaceAll("[~!@#<>$*&%^+=\\\\;:/_|()]", "");
							
						}
						print = print + " " + sp[i];
					}
					//if (print.length() != 0) {

						stream.previous();
						if(sp.length==0){
							stream.remove();
						}
						for (int i = 0; i < sp.length; i++) {
							if (sp[i].equals("") || sp[i].equals(null))
								stream.remove();
							else {
								stream.set(sp[i]);
								stream.next();
							}
						}

//						 System.out.println(print);

					//}
				}

			}

			stream.reset();
		}
	}
}
