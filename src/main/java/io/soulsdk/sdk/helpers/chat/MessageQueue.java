package io.soulsdk.sdk.helpers.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author kitttn
 */
public class MessageQueue<T> implements Iterable<T> {
	private ArrayList<T> list = new ArrayList<>();

	@Override
	public Iterator<T> iterator() {
		List<T> data = list.subList(0, list.size());
		Collections.reverse(data);
		return data.iterator();
	}

	public void addAll(List<T> data) {
		list.addAll(data);
	}

	public void add(T elem) {
		list.add(elem);
	}

	public long size() {
		return list.size();
	}

	public T getNewest() {
		return list.get(0);
	}

	public void addNewest(T elem) {
		list.add(0, elem);
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
