package lab06.authors;

import java.io.*;
import java.util.*;

/**
 * A "signature" of a text based on extracted "features". The features included
 * in the signature are:
 * <UL>
 * <LI>The average length of the words used
 * <LI>The average number of words per sentence
 * <LI>The Type-Token Ratio
 * <LI>The Hapax Legomana Ratio
 * <LI>The Sentence Complexity
 * </UL>
 * 
 * <p>
 * Each of these features are described in detail in the lab assignment and in
 * the comments in the class below.
 * 
 * @author Grant Braught
 * @version 8 March 2015
 */
public class TextSignature {
	/*
	 * Weights of each of the features to be used when computing the difference
	 * between two signatures.
	 */
	public static final double AVE_WORD_LENGTH_WEIGHT = 4.38;
	public static final double AVE_WORDS_PER_SENTENCE_WEIGHT = 1.0;
	public static final double TYPE_TOKEN_RATIO_WEIGHT = 147.0;
	public static final double HAPAX_LEGOMANA_RATIO_WEIGHT = 271.0;
	public static final double SENTENCE_COMPLEXITY_WEIGHT = 6.35;

	private String text;

	private String authorName;
	private double aveWordLength;
	private double aveWordsPerSentence;
	private double typeTokenRatio;
	private double hapaxLegomanaRatio;
	private double sentenceComplexity;

	/**
	 * Construct a new TextSignature for a work by the indicated author with the
	 * provided feature values.
	 * 
	 * @param authorName
	 *            the author's name.
	 * @param aveWordLength
	 *            the average word length in the text.
	 * @param aveWordsPerSentence
	 *            the average number of words per sentence in the text.
	 * @param typeTokenRatio
	 *            the type token ratio in the text.
	 * @param hapaxLegomanaRatio
	 *            the Hapax Legomana Ratio of the text.
	 * @param sentenceComplexity
	 *            the sentence complexity of the text.
	 */
	public TextSignature(String authorName, double aveWordLength,
			double aveWordsPerSentence, double typeTokenRatio,
			double hapaxLegomanaRatio, double sentenceComplexity) {
		this.authorName = authorName;
		this.aveWordLength = aveWordLength;
		this.aveWordsPerSentence = aveWordsPerSentence;
		this.typeTokenRatio = typeTokenRatio;
		this.hapaxLegomanaRatio = hapaxLegomanaRatio;
		this.sentenceComplexity = sentenceComplexity;
	}

	/**
	 * Construct a new TextSignature for the indicated author (possibly
	 * "unknown") using the text in the indicated file.
	 * 
	 * @param authorName
	 *            the author's name.
	 * @param filename
	 *            the name of the file containing the text from which the
	 *            signature will be computed.
	 * @throws IOException
	 *             if the file cannot be found or there is an error reading the
	 *             file.
	 */
	public TextSignature(String authorName, String filename) throws IOException {
		this.authorName = authorName;

		readFile(filename);

		this.aveWordLength = computeAveWordLength();
		this.aveWordsPerSentence = computeAveWordsPerSentence();
		this.typeTokenRatio = computeTypeTokenRatio();
		this.hapaxLegomanaRatio = computeHapaxLegomanaRatio();
		this.sentenceComplexity = computeSentenceComplexity();

		/*
		 * No need to keep the text in memory after we are done processing it.
		 */
		text = null;
	}

	/*
	 * Read the text from the indicated file one line at a time and concatenate
	 * all of the lines into one big long string stored in the field "text".
	 */
	private void readFile(String filename) throws IOException {
		String str = "";
		try {
			Scanner scr = new Scanner(new FileInputStream(filename));

			while (scr.hasNext()) {
				String line = scr.nextLine();
				str = str + " " + line;
			}
			scr.close();
		} catch (IOException e) {
			System.out.println("Error opening the file:" + filename);
		}

		text = str;

	}

	/*
	 * Compute the average length of the words in text. The text should be split
	 * into Words using the TextTools class. Words should be cleaned using the
	 * TextTools class before their length is calculated.
	 */
	private double computeAveWordLength() {
		double average = 0;
		double total = 0;
		String[] words = TextTools.getWords(text);
		double size = words.length;
		for (int index = 0; index < words.length; index++) {
			String word = words[index];
			TextTools.cleanString(word);
//			System.out.println(word);
			;
			total = total + word.length();
		}
//		System.out.println(total);
//		System.out.println(size);
		average = total / size;
		return average;

	}

	/*
	 * Compute the average number of words per sentence in the text. The text
	 * should be split into sentences using the TextTools class. Each sentence
	 * should be cleaned using the TextTools class before the number of words is
	 * counted.
	 */
	private double computeAveWordsPerSentence() {
		double average = 0;
		double totalWords = 0;
		String[] words = TextTools.getWords(text);
		String[] sentences = TextTools.getSentences(text);
		for (int index = 0; index < sentences.length; index++) {
			String sentence = sentences[index];
			TextTools.cleanString(sentence);
		}
		totalWords = words.length;
		average = totalWords / sentences.length;
		return average;
	}

	/*
	 * Compute the Type Token Ratio of the text. The Type Token Ratio is the
	 * ratio of the number of different words used in the text to the total
	 * number of words in the text. When computing the Type Token Ratio the
	 * capitalization of words is ignored (i.e. Book, BOOK and book are
	 * considered to be the same word). The TextTools class should be used to
	 * split the text into words. The TextTools class should be used to clean
	 * each word before it is used (this will ensure that capitalization and
	 * other meaningless differences are ignored).
	 */
	private double computeTypeTokenRatio() {

		double totalDiffWords = 0;
		double totalWords = 0;
		double ratio = 0;
		ArrayList<String> diffWords = new ArrayList<String>();
		String[] words = TextTools.getWords(text);
		totalWords = words.length;
		for (int index = 0; index < words.length; index++) {
			String currWord = words[index];

			if (!diffWords.contains(currWord)) {
				diffWords.add(currWord);
			}

		}

		totalDiffWords = diffWords.size();
		ratio = totalDiffWords / totalWords;

		return ratio;
	}

	/*
	 * Compute the Hapax Legomana Ratio for the text. The Hapax Legomana Ratio
	 * is the ratio of the number of words used exactly once in the text to the
	 * total number of words in the text. When computing the Hapax Legomana
	 * Ratio the capitalization of words is ignored (i.e. Book, BOOK and book
	 * are be considered to be the same word). The TextTools class should be
	 * used to split the text into words. The TextTools class should be used to
	 * clean each word before it is used (this will ensure that capitalization
	 * and other meaningless differences are ignored).
	 */
	private double computeHapaxLegomanaRatio() {

		String[] words = TextTools.getWords(text);
		double totalWords = words.length;
		ArrayList<String> onceWords = new ArrayList<String>();
		for (int index = 0; index < words.length; index++) {
			
			TextTools.cleanString(words[index]);

			if (!(onceWords.contains(words[index]))) {
				onceWords.add(words[index]);
			}
		}
		
//		System.out.println(onceWords.size());
		for (int index = 0; index < words.length; index++) {
			String curr = words[index];			
			for (int d = index+1; d < words.length; d++) {
				String next = words[d];
				if (curr.equals(next)) {
					onceWords.remove(curr);
				}

			}

		}

//		for (String w : onceWords) {
//			System.out.println("list is: " + w);
//		}

		double ratio = onceWords.size() / totalWords;
		return ratio;

	}

	/*
	 * Compute the Sentence Complexity of the text. The Sentence Complexity is
	 * simply the average number of phrases per sentence. The TextTools class
	 * should be used to split the text into sentences and to split each
	 * sentence into phrases. Note that phrases are identified by the
	 * punctuation in the sentences, thus the sentences must NOT be cleaned
	 * before they are split into phrases.
	 */
	private double computeSentenceComplexity() {
		double numPhrases = 0;
		double totalPhraPerSen = 0;
		double totalPhrases = 0;
		ArrayList<Double> phrasesList = new ArrayList<Double>();
		String[] sentences = TextTools.getSentences(text);
		for (int index = 0; index < sentences.length; index++) {
			String sentence = sentences[index];
			String[] phrases = TextTools.getPhrases(sentence);
			totalPhraPerSen = phrases.length;
			phrasesList.add(totalPhraPerSen);
		}
		for (int d = 0; d < phrasesList.size(); d++) {
			totalPhrases = totalPhrases + phrasesList.get(d);
		}

		numPhrases = totalPhrases / sentences.length;
		return numPhrases;
	}

	/**
	 * Get the name of the author associated with this signature.
	 * 
	 * @return the author's name.
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * Get the average length of the words used in the text.
	 * 
	 * @return the average word length.
	 */
	public double getAveWordLength() {
		return aveWordLength;
	}

	/**
	 * Get the average number of words per sentence in the text.
	 * 
	 * @return the average number of words per sentence.
	 */
	public double getAveWordsPerSentence() {
		return aveWordsPerSentence;
	}

	/**
	 * Get the type token ratio for the text.
	 * 
	 * @return the type token ratio.
	 */
	public double getTypeTokenRatio() {
		return typeTokenRatio;
	}

	/**
	 * Get the Hapax Legomana Ratio for the text.
	 * 
	 * @return the Hapax Legomana Ratio.
	 */
	public double getHapaxLegomanaRatio() {
		return hapaxLegomanaRatio;
	}

	/**
	 * Get complexity score for the text.
	 * 
	 * @return the sentence complexity.
	 */
	public double getSentenceComplexity() {
		return sentenceComplexity;
	}

	/**
	 * Compute a difference score between this text and that of another text.
	 * The difference score is computed as the sum of weighted absolute
	 * differences of the feature values. For each feature the absolute value of
	 * the difference between this text's value and the other text's value is
	 * found. This absolute difference is multiplied by the corresponding weight
	 * and totaled across all features. The smaller this value, the more similar
	 * the two texts are with respect to these features.
	 * 
	 * @param other
	 *            the TextSignature of a text to which this one should be
	 *            compared.
	 * @param aveWordLengthWeight
	 *            the weight to be given to the average word length feature.
	 * @param aveWordsPerSentenceWeight
	 *            the weight to be given to the words per sentence feature.
	 * @param typeTokenRatioWeight
	 *            the weight to be given to the token ratio feature.
	 * @param hapaxLegomanaRatioWeight
	 *            the weight to be given to the Hapax Legomana Ratio feature.
	 * @param sentenceComplexityWeight
	 *            the weight to be given to the sentence complexity feature.
	 * @return the difference score between this author and the other author.
	 */
	public double computeDifference(TextSignature other) {
		double aveWordLengthWeight = Math.abs(this.getAveWordLength()
				- other.getAveWordLength());
		double aveWordsPerSentenceWeight = Math.abs(this
				.getAveWordsPerSentence() - other.getAveWordsPerSentence());
		double typeTokenRatioWeight = Math.abs(this.getTypeTokenRatio()
				- other.getTypeTokenRatio());
		double hapaxLegomanaRatioWeight = Math.abs(this.getHapaxLegomanaRatio()
				- other.getHapaxLegomanaRatio());
		double sentenceComplexityWeight = Math.abs(this.getSentenceComplexity()
				- other.getSentenceComplexity());
		double diff1 = AVE_WORD_LENGTH_WEIGHT * aveWordLengthWeight;
		double diff2 = AVE_WORDS_PER_SENTENCE_WEIGHT
				* aveWordsPerSentenceWeight;
		double diff3 = TYPE_TOKEN_RATIO_WEIGHT * typeTokenRatioWeight;
		double diff4 = HAPAX_LEGOMANA_RATIO_WEIGHT * hapaxLegomanaRatioWeight;
		double diff5 = SENTENCE_COMPLEXITY_WEIGHT * sentenceComplexityWeight;
		double total = diff1 + diff2 + diff3 + diff4 + diff5;
		return total;
	}

	public static void main(String[] args) throws Exception {
//		String filename = "src/lab06/authors/documents/testfile2.txt";
//		// String filename1 = "src/lab06/authors/documents/james.joyce.txt";
//		Scanner src = new Scanner(new FileInputStream(filename));
//		try {
//			TextSignature text = new TextSignature("A", filename);
//			// TextSignature text1 = new TextSignature("B", filename1);
////			System.out.println(text.getAveWordLength());
////			System.out.println(text.getAveWordsPerSentence());
////			System.out.println(text.getTypeTokenRatio());
//			System.out.println(text.getHapaxLegomanaRatio());
////			System.out.println(text.getSentenceComplexity());
//			// System.out.println(text.computeDifference(text1));
//		} catch (FileNotFoundException e) {
//			System.out.println("File cannot not be found");
//
//		}
//		src.close();
		String test = "THIS";
		System.out.println(test.toLowerCase());
//		System.out.println(test);
	}
}