package lab08.search;

import java.util.ArrayList;

/**
 * This class represents a keyword and a set of associated web pages. The
 * associated web pages are those pages that should be returned when this
 * keyword is searched for.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version Apr 13, 2010
 */
public class KeyWord implements Comparable<KeyWord> {

	private String word;
	private ArrayList<PageInfo> pageList;

	/**
	 * Construct a new KeyWord for the specified word. Initially there are no
	 * web pages associated with the keyword.
	 * 
	 * @param word
	 *            the keyword.
	 */
	public KeyWord(String word) {
		this.word = word;
		pageList = new ArrayList<PageInfo>();
	}

	/**
	 * Get the keyword.
	 * 
	 * @return the keyword.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Add a web page to the list of pages associated with this keyword. Web
	 * pages should only be able to be added once. If a page is added more than
	 * once it will still only appear in the list once.
	 * 
	 * @param page
	 *            the page to be added.
	 */
	public void addPage(PageInfo page) {
		boolean contain = false;
		for (int i = 0; i < pageList.size(); i++) {
			if (page.equals(pageList.get(i))) {
				contain = true;
			}
		}
		if (!contain) {
			pageList.add(page);
		} else {
			System.out.println("Page already existed.");
		}
	}

	/**
	 * Get the list of web pages associated with this keyword. If there are no
	 * pages associated with the keyword then this method returns an empty
	 * ArrayList.
	 * 
	 * @return the web pages associated with this keyword.
	 */
	public ArrayList<PageInfo> getPages() {
		return pageList;
	}

	/**
	 * KeyWord objects should be ordered alphabetically. The alphabetization
	 * should be case insensitive.
	 */
	public int compareTo(KeyWord kw) {
		if ((getWord().compareTo(kw.getWord()) > 0)) { // using compareTo of String class
			return 1;
		} else if ((getWord().compareTo(kw.getWord()) == 0)) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * Determine if this KeyWord represents the same web page as Object o. Two
	 * KeyWord objects represent the same web page if they have the same
	 * keyword, case sensitive.
	 */
	public boolean equals(Object o) {
		if (o instanceof KeyWord) {
			if (((KeyWord) o).getWord().equals(getWord())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
