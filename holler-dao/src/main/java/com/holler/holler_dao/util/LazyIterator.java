/*
 * Copyright (c) 2011 Fidelis, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Fidelis, Inc.
 * Use is subject to license terms.
 */

package com.holler.holler_dao.util;

import java.util.Collection;
import java.util.Iterator;

public class LazyIterator {

	public static <E> void  iter(Collection<E> collection){
		if(collection==null) return;
		collection.size();
		Iterator<E> itr = collection.iterator();
		while(itr.hasNext()){
			itr.next();
		}
	}

}
