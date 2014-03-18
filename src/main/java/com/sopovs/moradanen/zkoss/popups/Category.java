package com.sopovs.moradanen.zkoss.popups;

import java.util.LinkedList;
import java.util.List;

public class Category {
	private String name;
	private String description;
	private List<Category> children = new LinkedList<Category>();

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void addChild(Category child) {
		if(child != null)
			children.add(child);
	}

	public void removeChild(Category child) {
		if(child != null)
			children.remove(child);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
