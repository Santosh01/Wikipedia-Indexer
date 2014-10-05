package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
//example of annotation, for classes you write annotate accordingly

/* (non-Javadoc)
 * @see edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerRule#apply(edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream)
 */
@RuleClass(className = RULENAMES.DATES)
public class DateRule implements TokenizerRule {
		//@SuppressWarnings("unused")
		private String[] monthName = {"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};
		private String[] adBc = {"ad", "bc"};

		public String[] filter1_findNumbers(String str)
		{
			// find numbers in string
			String numStr = str.replaceAll("[^0-9]+", " ");
			if( numStr == null || numStr.length() <= 0 )
			{
				// no number found
				return null;
			}

			// remove invalid number if any
			String[] numArrStr = numStr.split(" ");
			String[] numArrValid = new String[numArrStr.length];
			int validCount = 0;
			for(int i = 0; i < numArrStr.length; i++)
			{
				String tmp = numArrStr[i];
				if(tmp.trim().isEmpty())
				{
					// invalid
					continue;
				}
				numArrValid[validCount++] = tmp;
			}
			
			return numArrValid;
		}

		public String[] filter2_findWords(String[] words, String keyNum)
		{
			String[] retWords = new String[3]; // leftWord + keyNum + rightWord
			retWords[1] = keyNum;

			for(int i = 0; i < words.length; i++)
			{
				if(words[i].equals(keyNum))
				{
					if( i != 0 )
					{
						// check for special characters like ","; ":"; "."
						int j = i;
						while(j != 0)
						{
							String str = words[--j];
							if(str.matches("[a-zA-Z0-9]+"))
							{
								retWords[0] = str;
								break;
							}
						}
					}

					if( i != words.length )
					{
						// check for special characters like ","; ":"; "."
						int j = i;
						while(j != words.length)
						{
							String str = words[++j];
							if(str.matches("[a-zA-Z0-9]+"))
							{
								retWords[2] = str;
								break;
							}
						}
					}
					
					break;
				}
			}
			
			return retWords;
		}

		public String filter3_parseForMonth(String word)
		{
			String strMonth = null;
			for(int i = 0; i < monthName.length; i++)
			{
				if(word.startsWith(monthName[i]))
				{
					strMonth = monthName[i];
				}
			}
			
			return strMonth;
		}

		public String filter4_checkIfDate(String[] words)
		{
			String retDate = null; // format: yyyymmdd; Default: 19000101 --> 1900 jan 01
			
			String[] tmpWords = new String[3];
			
			try
			{
				if(words[0] == null)
				{
					tmpWords[0] = "01";
					
				}
				else
				{
					int n = Integer.parseInt(words[0]);
					if(n < 10)
					{
						tmpWords[0] = "0" + n;
					}
				}
				
				if(words[1] == null)
				{
					tmpWords[1] = "01";
				}
				else
				{
					for(int i = 0; i < monthName.length; i++)
					{
						if(words[1].startsWith(monthName[i]))
						{
							if(i < 9)
							{
								tmpWords[1] = "0" + (i+1);
							}
							else
							{
								tmpWords[1] = "" + (i+1);
							}
							break;
						}
					}
				}
				
				if(words[2] == null)
				{
					tmpWords[2] = "1900";
				}
				else
				{
					tmpWords[2] = words[2];
				}
				
				String str = tmpWords[2] + tmpWords[1] + tmpWords[0];
				
				Date tempDate = new SimpleDateFormat("yyyymmdd").parse(str);
				retDate = tempDate.toString();
				retDate = str;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				retDate = null;
			}
			
			return retDate;
		}

		public void apply(TokenStream stream) throws TokenizerException {
			if (stream != null) {
				String token;
				Stemmer s;
				while (stream.hasNext()) { 
					token = stream.next();
					if (token != null) {
						if( token.length() < 0 )
						{
							continue;
						}

						String[] validNums = filter1_findNumbers(token);
						if(validNums == null || validNums.length <= 0)
						{
							continue;
						}
						
						// using valid numbers
						String lowToken = token.toLowerCase().trim();
						String[] words = lowToken.split(" ");
						
						String[] dateForm = new String[3]; // date-month-year
						for(int i = 0; i < validNums.length; i++)
						{
							if(validNums[i] == null || validNums[i].length() <= 0)
							{
								continue;
							}
							
							String[] threeWords = filter2_findWords(words, validNums[i]);
							if(threeWords != null)
							{
								// date & year
								if(dateForm[0] == null || dateForm[2] == null)
								{
									String date = threeWords[1];
									int dateNum = Integer.parseInt(date);
									if(dateNum < 31)
									{
										dateForm[0] = date;
									}
									else
									{
										dateForm[2] = date; // year
									}
								}
								
								// month
								if( dateForm[1] == null )
								{
									String month = filter3_parseForMonth(threeWords[0]);
									if(month == null)
									{
										month = filter3_parseForMonth(threeWords[2]);
									}
									
									if(month != null)
									{
										dateForm[1] = month;
									}
								}
							}
						}
						
						System.out.println(dateForm);
						String str = filter4_checkIfDate(dateForm);
						System.out.println(str);
						int idx1 = -1;
						if( dateForm[0] != null )
						{
							idx1 = lowToken.indexOf(dateForm[0]);
						}

						int idx2 = -1;
						if( dateForm[1] != null )
						{
							idx2 = lowToken.indexOf(dateForm[1]);
						}
						
						int idx3 = -1;
						if( dateForm[2] != null )
						{
							idx3 = lowToken.indexOf(dateForm[2]);
						}
						
						int[] intArry = {idx1, idx2, idx3};
						Arrays.sort(intArry);
						System.out.println(Arrays.asList(intArry));
						
						String str1 = token.substring(0, intArry[0]);
						String str2 = token.substring(intArry[2], token.length());
						int idxTmp = str2.indexOf(" ");
						String str3 = str2.substring(idxTmp, str2.length());
						String strResult = str1 + str + str3;
						System.out.println(strResult);
						stream.set(null);
						stream.set(strResult);
					}
				}
				

		}}
		}
