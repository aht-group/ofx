package org.openfuxml.renderer.html.util;
/**
 * Author: Rebecca Roblick
 */
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Text;
import org.openfuxml.content.layout.Height;
import org.openfuxml.content.layout.Width;
import org.openfuxml.renderer.html.head.HtmlHead;

/*Klasse erzeugt Objekte, welche letztendlich die einzelnen Tags
* im Html Dokument darstellen*/
public class HtmlElement extends Element
{
	private static final long serialVersionUID = 1L;

	public HtmlElement(String name)
	{
		super(name);
	}

	/*Zur internen Spezifizierung von CSS Formatierung einzelner HTML Elemente*/
	public void setStyleAttribute(String value){this.setAttribute("style", value);}

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

	/*Werte von Width oder Hight Objekte mit der dazugehörigen Einheit verbinden und in einen String einfügen.
	* String dient zum Einfügen in "style" Attribute oder tags.*/
	public static String evaluateSize(Object o)
	{
		String size="";
		if(o != null && o instanceof Width){size = "width: " + ((Width)o).getValue() +evaluateUnit(((Width)o).getUnit()) + ";";}
		if(o != null && o instanceof Height){size = "height: " + ((Height)o).getValue() + evaluateUnit(((Height)o).getUnit())+ ";";}
		return size;
	}

	public static String evaluateSize(Object o, double multiplier)
	{
		String size="";
		if(o != null && o instanceof Width){size = "width: " +
				(((Width)o).getUnit().equalsIgnoreCase("percentage") && ((Width)o).getValue()*multiplier > 100 ? 100: ((Width)o).getValue()*multiplier) + evaluateUnit(((Width)o).getUnit()) + ";";}
		if(o != null && o instanceof Height){size = "height: " + (((Height)o).getUnit().equalsIgnoreCase("percentage") && ((Height)o).getValue()*multiplier > 100 ? 100: ((Height)o).getValue()*multiplier) + evaluateUnit(((Height)o).getUnit())+ ";";}
		return size;
	}

	/*Umwandeln von "percentage" in das % - Zeichen. Ansonsten Einheit übernehmen wie sie ist.*/
	private static String evaluateUnit(String unit){return unit.equalsIgnoreCase("percentage") ? "%" : unit;}

	/*Für Elemente mit fester Formatierung, wo Zeilenumbrüche etc. erhalten bleiben müssen.*/
	public static HtmlElement preFormatted(){return new HtmlElement("pre");}

	/*<style> Element im <head> hinzufügen, sofern noch nicht vorhanden.
	* Inhalt als String zum <style> hinzufügen.*/
	public static void addStyleElement(String styleElement, HtmlElement html)
	{
		if(html.getChild("head").getChild("style") == null){HtmlHead.style((HtmlElement)html.getChild("head"));}
		HtmlElement style = (HtmlElement)html.getChild("head").getChild("style");
		style.addContent(styleElement);
	}
}