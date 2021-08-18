package org.openfuxml.renderer.html.head;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.structure.HtmlDocumentRenderer;
import org.openfuxml.renderer.html.util.HtmlAttribute;
import org.openfuxml.renderer.html.util.HtmlElement;

public class HtmlHead extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	private HtmlElement head;

	private List<HtmlElement> css; public List<HtmlElement> getCss(){if(css == null){css = new ArrayList<HtmlElement>();}return css;}
	private List<HtmlElement> metas; public List<HtmlElement> getMetas(){if(metas == null){metas = new ArrayList<HtmlElement>();}return metas;}
	private List<HtmlAttribute> metaAttr; public List<HtmlAttribute> getMetaAttr(){return metaAttr;}
	
	public HtmlHead(ConfigurationProvider cp)
	{
		super(cp);
		metaAttr = new ArrayList<HtmlAttribute>();
		metaAttr.add(new HtmlAttribute("charset", "UTF-8"));
	}

	public void render(List<String> cssFiles)
	{
		new HtmlDocumentRenderer(cp,"").init();
		render(cssFiles,"");
	}

	public void render(List<String> cssFiles, String pageTitle)
	{
		head = new HtmlElement("head");
		metaData(head);
		if(cssFiles != null){css(head,cssFiles);}
		pageTitle(head, pageTitle);
//		style(head);
		HtmlDocumentRenderer.getInstance().getHtml().getContent().add(head);

	}

	private void metaData(HtmlElement head)
	{
		for(HtmlAttribute a : metaAttr)
		{
			HtmlElement meta = new HtmlElement("meta");
			meta.setAttribute(a);
			getMetas().add(meta);
		}
		head.getChildren().addAll(metas);
	}

	private void css(HtmlElement head, List<String> cssFiles)
	{
		List<HtmlElement> links = new ArrayList<HtmlElement>();
		for(String s : cssFiles)
		{
			HtmlElement link = new HtmlElement("link");
			link.setAttribute("rel", "stylesheet");
			link.setAttribute("href", s);
			links.add(link);
		}
		head.getChildren().addAll(links);
	}

	private void pageTitle(HtmlElement parent, String pageTitle)
	{
		HtmlElement title = new HtmlElement("title");
		title.addContent(pageTitle);
		parent.addContent(title);
	}

	public static void style(HtmlElement parent)
	{
		HtmlElement style = new HtmlElement("style");
		style.setAttribute("type","text/css");
		parent.addContent(style);
	}
}