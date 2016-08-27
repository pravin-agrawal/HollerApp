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
