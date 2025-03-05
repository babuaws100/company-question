package com.test.question;

import java.util.ArrayList;
import java.util.List;

public abstract class Tree<T> {
	private Node<T> root;
	
	public Tree() {
		root = new Node<T>();
	}
	public Node<T> getRoot() {
		return root;
	}
	
	public abstract boolean isChild(T t1, T t2);
	
	public void addNode(T t) {
		
		// Initialize
		Node<T> newNode = new Node<T>(t);
		
		// Check if there is no children of root node
		if(root.getChildren() == null) {
			root.addChild(newNode);
			return;
		}
		
		// Find the parent node of the new data.
		Node<T> parent = findParentNode(newNode, root);
		
		// Check if parent is not found. In that case add it to child of root
		if(null == parent) {
			root.addChild(newNode);
			return;
		} else {
			
			// Add the data as child to the parent
			parent.addChild(newNode);
			
			// Also find children of new node from the unfound parent nodes of root
			List<Node<T>> newParentFoundNodes = new ArrayList<>();
			for(Node<T> node: root.getChildren()) {
				if(isChild(newNode.getData(), node.getData())) {
					newNode.addChild(node);
					newParentFoundNodes.add(node);
				}
			}
			
			// Remove the new parent found nodes from root node's children
			root.getChildren().removeAll(newParentFoundNodes);
		}
		
	}
	
	private Node<T> findParentNode(Node<T> newNode, Node<T> currentNode) {
		
		// Check if Node has any children
		if(null == currentNode.getChildren()) {
			return null;
		}
		
		// Check if t is a grandchild of the current node
		for(Node<T> node: currentNode.getChildren()) {
			
			// Check if node is parent
			Node<T> parent = null;
			if(isChild(node.getData(), newNode.getData())) {
				return node;
			
			// Check if any of the nodes children is the parent
			} else if (null != node.getChildren() && null != (parent = findParentNode(newNode, node))) {
				return parent;
			}
		}
		return null;
	}
	
	public String toString() {
		try {
			return prepareTreeString(root);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "";
		}
	}
	private String prepareTreeString(Node<T> currentNode) {
		String treeString = currentNode.toString();
		if (null != currentNode.getChildren()) {
			treeString = treeString + "->(";
			for(Node<T> node: currentNode.getChildren()) {
				String childString = prepareTreeString(node);
				treeString = treeString + "  " + childString + "  ";
			}
			treeString = treeString + ")";
		}
		return treeString;
	}
	
}
