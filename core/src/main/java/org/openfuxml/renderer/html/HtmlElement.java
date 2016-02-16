package org.openfuxml.renderer.html;

import org.jdom2.*;

/*Klasse erzeugt Objekte, welche letztendlich die einzelnen Tags
* im Html Dokument darstellen*/
public class HtmlElement extends Element
{
	public HtmlElement(String name)
	{
		super(name);
	}

	/*Zur internen Spezifizierung von CSS Formatierung einzelner HTML Elemente*/
	public void setStyleAttribute(HtmlElement element, String value){element.setAttribute("style", value);}

	/*HTML spezifischer Zeilenumbruch. */
	public HtmlElement explicitLinebreak() {return new HtmlElement("br");}

	/*Horizontale Trennlinie, z.B. zur optischen Trennung von Inhalten*/
	public HtmlElement HorizontalLine() {return new HtmlElement("hr");}

	public HtmlElement addContent(String s)
	{
		return this.addContent(new Text(s));
	}

	public HtmlElement addContent(Content c)
	{
		this.getContent().add(c);
		return this;
	}

	public boolean isSetAttribute (){return getAttributes() != null;}

}