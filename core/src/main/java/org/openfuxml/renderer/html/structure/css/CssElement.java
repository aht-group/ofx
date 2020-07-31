package org.openfuxml.renderer.html.structure.css;

import java.util.List;
import java.util.stream.Collectors;

public class CssElement
{
	private String selector;
	private List<CssAttribute> attributes;
	
	public CssElement(String selector, List<CssAttribute> attributes) {
		this.selector = selector;
		this.attributes = attributes;
	}
	
	public String toString() {
		return String.join("", selector, "{", attributes.stream().map(attr -> attr.toString()).collect(Collectors.joining()), "}");
	}
}
