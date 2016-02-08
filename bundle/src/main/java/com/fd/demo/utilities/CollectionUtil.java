package com.fd.demo.utilities;

import java.util.Collection;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectionUtil.
 */
public class CollectionUtil {
	
	/**
	 * This provides null checking as well as is Empty method for java.util.Collection.
	 * isEmpty
	 * @param collection the Collection object 
	 * @return true / false
	 */
	public static boolean isEmpty(Collection collection) {
		return (null == collection || collection.isEmpty());
	}
	
	
	/**
	 * Checks if is empty.
	 *
	 * @param collection the collection
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Map collection) {
		return (null == collection || collection.isEmpty());
	}

}
