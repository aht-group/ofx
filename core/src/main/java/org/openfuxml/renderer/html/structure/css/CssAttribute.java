package org.openfuxml.renderer.html.structure.css;

public class CssAttribute
{
	private String name;
	private String value;
	
	public CssAttribute(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String toString() {
		return String.join("", name, ": ", value, ";");
	}
}
