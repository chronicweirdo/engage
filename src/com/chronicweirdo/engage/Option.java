package com.chronicweirdo.engage;

public class Option implements Comparable<Option> {

	public static enum Type {
		FILE, FOLDER, RETURN;
	}
	
	private Type type;
	private String name;
	private String path;

	public Option(String name, Type type, String path) {
		this.path = path;
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	@Override
	public int compareTo(Option o) {
		if (this.name != null) {
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
		} else throw new IllegalArgumentException();
	}

}
