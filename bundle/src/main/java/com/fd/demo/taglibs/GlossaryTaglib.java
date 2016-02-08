package com.fd.demo.taglibs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.demo.utilities.MapCompareByValueComparator;


// TODO: Auto-generated Javadoc
/**
 * The Class GlossaryTaglib.
 */
public class GlossaryTaglib {

	/** The Constant log. */
	private static final Logger logger = LoggerFactory.getLogger(GlossaryTaglib.class);

	/**
	 * Gets the pagination list.
	 *
	 * @param glossaryItems the glossary items
	 * @return the pagination list
	 */
	public static Map getPaginationList(final List<Map<String, String>> glossaryItems) {
		Map<String, String> glossaryListMap = new TreeMap<String, String>();
		if(glossaryItems!=null) {
			List<String> alphabetList = getAlphabetList();
			List<String> firsAlphabetList = getfirstAlphabetsForEachWord(glossaryItems);
			for (int i =0;i<alphabetList.size();i++) {
				if(firsAlphabetList.contains(alphabetList.get(i))) {
					glossaryListMap.put(alphabetList.get(i),"y");
				}
				else{
					glossaryListMap.put(alphabetList.get(i),"n");
				}

			}
		}
		return glossaryListMap;
	}

	/**
	 * Gets the alphabet list.
	 *
	 * @return the alphabet list
	 */
	private static List<String> getAlphabetList() {
		List<String> alphabetList = new ArrayList<String>();
		for(int i=65;i<=90;i++) {
			Character charStr = (char)i;
			alphabetList.add(charStr.toString());
		}
		Collections.sort(alphabetList);
		return alphabetList;
	}

	/**
	 * Gets the words list.
	 *
	 * @param glossaryItems the glossary items
	 * @return the words list
	 */
	private static List<String> getfirstAlphabetsForEachWord(List<Map<String, String>> glossaryItems) {
		List<String> alphabetList = new ArrayList<String>();
		Iterator iterator = glossaryItems.iterator();
		while(iterator.hasNext()) {
			Map<String,String> glossaryItemsMap = (Map)iterator.next();
			if(glossaryItemsMap.containsKey("word")) {
				Character firstChar = glossaryItemsMap.get("word").trim().charAt(0);
				alphabetList.add(firstChar.toString().toUpperCase());
			}
		}
		Set<String> hs = new HashSet<String>();
		hs.addAll(alphabetList);
		alphabetList.clear();
		alphabetList.addAll(hs);
		return alphabetList;
	}

	
	public static List<Map<String, String>> sortGlossaryItems(final List<Map<String, String>> glossaryItems) {
		MapCompareByValueComparator mapCompare = new MapCompareByValueComparator();
		Collections.sort(glossaryItems, mapCompare);
		 return glossaryItems;
	}
	
}



