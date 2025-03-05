package com.test.question;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private T data;
	private List<Node<T>> children;

	public Node() {
		this.data = null;
	}

	public Node(T t) {
		this.data = t;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void addChild(Node<T> child) {
		if (null == children) {
			children = new ArrayList<>();
		}
		children.add(child);
	}

	public T getData() {
		return data;
	}
	
	public boolean equals(Object other) {
		if(!(other instanceof Node<?>)) {
			return false;
		}
		
		Object otherData = ((Node<?>)other).getData();
		if(data.equals(otherData)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int hashCode() {
		return data.hashCode();
	}
	
	public String toString() {
		if (null != data) {
			return data.toString();
		} else {
			return "root";
		}
	}

}
