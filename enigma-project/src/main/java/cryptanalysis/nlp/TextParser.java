/**
 * TextParser.java
 * @author - Walter Adolph
 * @author - Team Enigma
 * @date - Dec 1, 2013
 * This parser accumulates statistical data from a corpus on individual characters.
 * 1, 2, 3 and 4 character grams are analyzed.
 * A word table is also generated.
 * Punctuation is ignored.
 */

package main.java.cryptanalysis.nlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextParser {
	private Corpus database;

	public TextParser(Corpus newCorpus) {
		database = newCorpus;
	}
	
	public void parseFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			
			char firstgram = '\0';
			char secondgram = '\0';
			char thirdgram = '\0';
			char fourthgram = '\0';
			
			// Parse words.
			while (scanner.hasNext()) {
				char[] characters = scanner.next().toUpperCase().toCharArray();
				String word = "";
				
				for (char character: characters) {
					if (Character.isAlphabetic(character)) {	// Skip punctuation.
						word += "" + character;
						
						fourthgram = thirdgram;
						thirdgram = secondgram;
						secondgram = firstgram;
						firstgram = character;
						
						database.addUnigram(String.valueOf(firstgram));
						
						if (secondgram != '\0') {
							String phrase = "" + secondgram + firstgram;
							
							database.addBigram(phrase);
							
							if (thirdgram != '\0') {
								phrase = "" + thirdgram + secondgram + firstgram;
								
								database.addTrigram(phrase);
								
								if (fourthgram != '\0') {
									phrase = "" + fourthgram + thirdgram + secondgram + firstgram;
									
									database.addQuadgram(phrase);
								} // End quadgram if
							} // End trigram if
						} // End bigram if
					} // End punctuation if
				} // End character for
				
				database.addWord(word);
			} // End while
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Parsing complete; sort for ready use.
		database.sortDatabase();
	}
}
