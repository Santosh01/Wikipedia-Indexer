package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer.Stemmer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

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
@RuleClass(className = RULENAMES.PUNCTUATION)
public class PunctuationRule implements TokenizerRule {
	// @SuppressWarnings("unused")

	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			Stemmer s;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					while (token.charAt(token.length() - 1) == '.' 	|| token.charAt(token.length() - 1) == '?' || token.charAt(token.length() - 1) == '!') {
						token = token.substring(0, token.length() - 1);
					}

					token = deleteMiddle(token, ".");
					token = deleteMiddle(token, "?");
					token = deleteMiddle(token, "!");
					token.replaceAll("([a-z]+)[?:!.,;]*", "$1");
//					System.out.println("============before previous=====" + token);
//					System.out.println();
					stream.previous();
					stream.set(token);
					stream.next();
					
				}
			}
			stream.reset();
		}
	}

	private String deleteMiddle(String text, String sign) {
		String result = text;
		do {
			int index = text.indexOf(sign);
			if (index >= 0) {
				if (text.charAt(index + 1) == ' ') {
					result = text.substring(0, index)+ text.substring(index + 1);
				}

				text = text.substring(index + 1);
				//System.out.println(text);
			}
		} 
				
		while (text.contains(sign));
//		System.out.println(result);
		return result;
	}

}
