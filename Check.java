import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Program Check will ask the user to enter a line of text. It will then process 
 * through each line in the users text and determine which words were spelled 
 * incorrectly. It will then check for all possible spellings for the misspelled 
 * word and return and report those words to the user. 
 * @author Joe Bisesto
 *
 */

public class Check {
	public static void main(String[] args) {

		ArrayList<String> dictionary = loadDictionary(); // the dictionary of
															// words
		ArrayList<String> mispelled = new ArrayList<String>(); // a list of
																// mispelled
																// words
		String userText; // the line of text that the user will enter

		System.out
				.println("Enter in a line of text and I will check for mispelled words");
		// get the users line of text
		userText = TextIO.getlnString();
		// split it into different words and put into an array
		String[] words = userText.split(" ");
		

		// add all misspelled words in string to misspelled ArrayList

		for (String s : words) {
			boolean mispelledOrNot = checkSpelling(s, dictionary);
			if (!mispelledOrNot) {
				mispelled.add(s);

			}
		}

		System.out.println("Mispelled words:");
		//arraylist for each function
		ArrayList<String> check; 
		ArrayList<String> delete;
		ArrayList<String> change;
		ArrayList<String> trans;

		for (int i = 0; i < mispelled.size(); i++) {
			System.out.println();
			System.out.println(i + 1 + ":" + mispelled.get(i));
			System.out.println("Suggested spellings: ");
			// get spellings for checkAddLetter
			check = checkAddLetter(mispelled.get(i), dictionary);
			for (String c : check) {
				System.out.print(c + " ");
			}

			delete = deletePositions(mispelled.get(i), dictionary);
			for (String d : delete) {
				System.out.print(d + " ");
			}

			change = changeLetters(mispelled.get(i), dictionary);
			for (String ch : change) {
				System.out.print(ch + " ");
			}

			trans = transpose(mispelled.get(i), dictionary);
			for (String t : trans) {
				System.out.println(t + " ");
			}

		}

	}

	/**
	 * Will load in a dictionary of words
	 * 
	 * @return an ArrayList containing every word in the dictionary
	 * 
	 */
	public static ArrayList<String> loadDictionary() {
		ArrayList<String> dictionary = new ArrayList<String>();
		Scanner dictFile = null;

		try {
			dictFile = new Scanner(new File("manyManyWords.dat"));

		} catch (FileNotFoundException e) {
			System.out.println("Dictionary file not found");

		}

		while (dictFile.hasNext()) {
			dictionary.add(dictFile.next());

		}

		return dictionary;
	}

	/**
	 * Will give suggestions for a misspelled word
	 * 
	 * @param worda
	 * @param dictionary
	 * @return
	 * 
	 */
	public static ArrayList<String> getSuggestions(String word,
			ArrayList<String> dictionary) {

		ArrayList<String> Suggestions = new ArrayList<String>(); // to Return

		ArrayList<String> trans = transpose(word, dictionary);
		Suggestions.addAll(trans);

		ArrayList<String> check = checkAddLetter(word, dictionary);
		Suggestions.addAll(check);

		ArrayList<String> delete = deletePositions(word, dictionary);
		Suggestions.addAll(delete);

		ArrayList<String> change = changeLetters(word, dictionary);
		Suggestions.addAll(change);

		return Suggestions;
	}

	/**
	 * 1. Will check all words that can be obtained from the incorrect word by
	 * adding a single letter at some position
	 * 
	 * @param Word
	 * @param dictionary
	 * @return
	 */
	public static ArrayList<String> checkAddLetter(String Word,
			ArrayList<String> dictionary) {
		ArrayList<String> suggestions = new ArrayList<String>();
		StringBuffer wordBuff = new StringBuffer("");
		char[] alpha = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z' }; // array containing every letter
												// of alphabet

		wordBuff.setLength(Word.length()); // string buffer will have length
											// 1 more so that you can add in
											// letters

		// add each letter in Word to wordBuff
		for (int i = 0; i < Word.length(); i++) {
			wordBuff.setCharAt(i, Word.charAt(i));
		}

		// add each letter of alphabet to every position in word checking if
		// that is a word
		// if it is add it to suggestion

		for (int i = 0; i < alpha.length; i++) {
			for (int y = 0; y <= wordBuff.length(); y++) {

				wordBuff.insert(y, alpha[i]);
				String wordChecking = wordBuff.toString();
				if (dictionary.contains(wordChecking)) {
					suggestions.add(wordChecking);
				}
				wordBuff.deleteCharAt(y);

			}

		}
		
		return suggestions;
	}

	/**
	 * 2. Will check all words that can be obtained from the incorrect word by
	 * deleting a single letter from some position
	 * 
	 * @param Word
	 * @param dictionary
	 * @return
	 */
	public static ArrayList<String> deletePositions(String Word,
			ArrayList<String> dictionary) {
		ArrayList<String> suggestions = new ArrayList<String>();
		// turn the word into a string buffer
		StringBuffer wordBuff = new StringBuffer(""); // stringBuffer
		wordBuff.setLength(Word.length()); // set its length
		// create it with Word
		for (int i = 0; i < Word.length(); i++) {
			wordBuff.setCharAt(i, Word.charAt(i));
		}

		// delete characters at each position to see if it is a word

		for (int i = 0; i < Word.length(); i++) {
			// remove character
			char currentRemove = wordBuff.charAt(i); // the character to be
														// removed
			wordBuff.deleteCharAt(i);
			// if it is a word then add it to suggestions
			if (dictionary.contains(wordBuff.toString())) {
				suggestions.add(wordBuff.toString());
			}

			wordBuff.insert(i, currentRemove); // add the character back

		}
		// remove duplicates from suggestions
		// for (String s : suggestions) {
		// if (suggestions.contains(s)) {
		// suggestions.remove(s);
		// }
		// }

		return suggestions;
	}

	/**
	 * 3. Will get all words that can be obtained from the incorrect word by
	 * changing a single letter at some position
	 * 
	 * @param Word
	 * @param dictionary
	 * @return
	 */
	public static ArrayList<String> changeLetters(String Word,
			ArrayList<String> dictionary) {
		ArrayList<String> suggestions = new ArrayList<String>();
		char[] alpha = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
				'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z' }; // array containing every letter
												// of alphabet

		// turn the word into a string buffer
		StringBuffer wordBuff = new StringBuffer(""); // stringBuffer
		wordBuff.setLength(Word.length()); // set its length
		// create it with Word
		for (int i = 0; i < Word.length(); i++) {
			wordBuff.setCharAt(i, Word.charAt(i));
		}

		// delete and replace
		for (int i = 0; i < Word.length(); i++) {
			// delete ith position in wordBuff but remember it to add back in
			char removing = wordBuff.charAt(i);
			wordBuff.deleteCharAt(i);

			for (int y = 0; y < alpha.length; y++) {
				// insert ever letter of alphabet into the empty position

				wordBuff.insert(i, alpha[y]);

				// check if word
				if (dictionary.contains(wordBuff.toString())) {
					suggestions.add(wordBuff.toString());
				}
				// delete that letter
				wordBuff.deleteCharAt(i);

			}

			// add back in
			wordBuff.insert(i, removing);
		}

		return suggestions;
	}

	/**
	 * will check for all words that can be obtained from the incorrect word by
	 * transposing a single pair of letters
	 * 
	 * @param Word
	 * @param dictionary
	 * @return
	 */
	public static ArrayList<String> transpose(String Word,
			ArrayList<String> dictionary) {
		ArrayList<String> suggestions = new ArrayList<String>();

		// turn the word into a string buffer
		StringBuffer wordBuff = new StringBuffer(""); // stringBuffer
		wordBuff.setLength(Word.length()); // set its length
		// create it with Word
		for (int i = 0; i < Word.length(); i++) {
			wordBuff.setCharAt(i, Word.charAt(i));
		}

		// start transposing each position in word

		for (int i = 0; i < Word.length() - 1; i++) {
			char first = wordBuff.charAt(i);
			char second = wordBuff.charAt(i + 1);
			wordBuff.setCharAt(i, second);
			wordBuff.setCharAt(i + 1, first);

			if (dictionary.contains(wordBuff.toString())) {
				suggestions.add(wordBuff.toString());
			}

			// set back
			wordBuff.setCharAt(i, first);
			wordBuff.setCharAt(i + 1, second);

		}

		return suggestions;
	}

	/**
	 * Will check if a word is spelled correctly or not
	 * 
	 * @param Word
	 * @param dictionary
	 * @return
	 */
	
	public static boolean checkSpelling(String Word,
			ArrayList<String> dictionary) {

		//if word is in dictionary then the word is spelled correctly
		//if not then it is not spelled correctly
		if (dictionary.contains(Word)) {
			return true;
		} else {
			return false;
		}

	}

}