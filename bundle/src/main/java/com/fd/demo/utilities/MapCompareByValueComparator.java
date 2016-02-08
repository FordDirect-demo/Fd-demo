package com.fd.demo.utilities;

import java.util.Comparator;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class MapCompareByValueComparator.
 */
public class MapCompareByValueComparator implements Comparator {
	public int compare(Object obj1, Object obj2) {
		Map<String, String> objMap1 = (Map<String, String>) obj1;
		Map<String, String> objMap2 = (Map<String, String>) obj2;
		return objMap1.get("word").compareTo(objMap2.get("word"));
	}
}