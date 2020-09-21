package org.openfuxml.renderer.html.structure.css;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;

public class HtmlStyleRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{

	public HtmlStyleRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(HtmlElement parent)
	{
		HtmlElement style = new HtmlElement("style");
		render(style, Arrays.asList(new CssElement("*",
		                                           Arrays.asList(new CssAttribute("color", "hsla(0, 0%, 31%, 1)"),
		                                                         new CssAttribute("font-family", "Arial, sans-serif"),
		                                                         new CssAttribute("font-size", "11px"),
		                                                         new CssAttribute("line-height", "22px"))),
		                            new CssElement("body",
		                                           Arrays.asList(new CssAttribute("background", "hsla(0, 0%, 83%, 1)"))),
		                            new CssElement("body > div",
		                            			   Arrays.asList(new CssAttribute("background", "hsla(0, 0%, 100%, 1)"),
		                            			                 new CssAttribute("box-shadow", "0 0 10px #AAA"),
		                            			                 new CssAttribute("box-sizing", "border-box"),
		                            			                 new CssAttribute("margin", "10px auto"),
		                            			                 new CssAttribute("padding", "10px 10px 20px 10px"),
		                            			                 new CssAttribute("width", "982px"))),
		                            new CssElement("body > div:before",
		                                           Arrays.asList(new CssAttribute("background","linear-gradient(to bottom, #666698 0%, #666698 54%,#CC3366 72%,#CC3366 100%)"),
		                                                         new CssAttribute("content", "''"),
		                                                         new CssAttribute("display", "block"),
		                                                         new CssAttribute("height", "55px"),
		                                                         new CssAttribute("margin-bottom", "15px"),
		                                                         new CssAttribute("width", "100%"))),
		                            new CssElement("body > div > section",
		                                           Arrays.asList(new CssAttribute("background-color", "hsla(0, 0%, 95%, 1)"),
		                                                         new CssAttribute("border", "1px solid hsla(0, 0%, 66%, 1)"),
		                                                         new CssAttribute("border-radius", "3px"),
		                                                         new CssAttribute("color", "hsla(0, 0%, 31%, 1)"),
		                                                         new CssAttribute("padding", "1em"))),
		                            new CssElement("body > div > section > h1:first-child",
		                                           Arrays.asList(new CssAttribute("background", "hsla(0, 0%, 77%, 1) -webkit-gradient(linear,left top,left bottom,from(hsla(0, 0%, 100%, 0.8)),to(hsla(0, 0%, 100%, 0)))"),
		                                                         new CssAttribute("border", "1px solid hsla(0, 0%, 66%, 1)"),
		                                                         new CssAttribute("border-radius", "3px"),
		                                                         new CssAttribute("line-height", "12px"),
		                                                         new CssAttribute("margin", "-0.8em -0.8em 0 -0.8em"),
		                                                         new CssAttribute("padding", ".5em 1em .3em"),
		                                                         new CssAttribute("text-shadow", "0 1px 0 hsla(0, 0%, 100%, 0.7)"))),
		                            new CssElement("section",
		                                           Arrays.asList(new CssAttribute("border", "1px solid hsla(0, 0%, 66%, 1)"),
		                                                         new CssAttribute("border-width", "1px 0"),
		                                                         new CssAttribute("padding", "10px"))),
		                            new CssElement("section ~ section",
		                                           Arrays.asList(new CssAttribute("border-top", "none"))),
		                            new CssElement("section section > *:first-child",
		                                           Arrays.asList(new CssAttribute("margin-top", "0"))),
		                            new CssElement("section section > *:last-child",
		                                           Arrays.asList(new CssAttribute("margin-bottom", "0"))),
		                            new CssElement("table",
		                                           Arrays.asList(new CssAttribute("border-collapse", "collapse"))),
		                            new CssElement("table > caption",
		                                           Arrays.asList(new CssAttribute("background", "hsla(0, 0%, 77%, 1) -webkit-gradient(linear,left top,left bottom,from(rgba(255,255,255,0.8)),to(rgba(255,255,255,0)))"),
		                                                         new CssAttribute("border", "1px solid hsla(0, 0%, 66%, 1)"),
		                                                         new CssAttribute("border-bottom", "0"),
		                                                         new CssAttribute("border-radius", "3px 3px 0 0"),
		                                                         new CssAttribute("box-shadow", "inset 0 1px 0 hsla(0, 0%, 100%, 1)"),
		                                                         new CssAttribute("color", "#333"),
		                                                         new CssAttribute("font-weight", "bold"),
			                                                     new CssAttribute("padding", "4px 10px"),
			                                                     new CssAttribute("text-shadow", "0 1px 0 hsla(0, 0%, 100%, 0.7)"))),
		                            new CssElement("th",
		                                           Arrays.asList(new CssAttribute("background", "hsla(0, 0%, 77%, 1) -webkit-gradient(linear,left top,left bottom,from(rgba(255,255,255,0.8)),to(rgba(255,255,255,0)))"),
		                                                         new CssAttribute("border", "1px solid hsla(0, 0%, 66%, 1)"),
		                                                         new CssAttribute("box-shadow", "inset 0 1px 0 hsla(0, 0%, 100%, 1)"),
		                                                         new CssAttribute("padding", "4px 10px"))),
		                            new CssElement("tbody",
		                                           Arrays.asList(new CssAttribute("background", "hsla(0, 0%, 100%, 1)"))),
		                            new CssElement("tbody tr:nth-child(2n)",
		                                           Arrays.asList(new CssAttribute("background", "hsla(214, 37%, 96%, 1)"))),
		                            new CssElement("tbody td",
		                                           Arrays.asList(new CssAttribute("padding", "4px 10px"),
		                                                         new CssAttribute("overflow", "hidden"),
		                                                         new CssAttribute("border", "1px solid hsla(0, 0%, 66%, 1)")))));
		parent.addContent(style);
	}
	
	public void render(HtmlElement style, List<CssElement> cssElements)
	{
		style.addContent(cssElements.stream().map(Object::toString).collect(Collectors.joining()));
	}
}
