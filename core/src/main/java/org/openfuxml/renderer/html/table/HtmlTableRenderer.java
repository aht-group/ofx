package org.openfuxml.renderer.html.table;

import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.*;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlTableRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlTableRenderer.class);

	public HtmlTableRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}

	public void render(HtmlElement parent, Table tab)
	{
		HtmlElement table = new HtmlElement("table");
		table.setAttribute("id", tab.getId()); //benötigt für interne Referenzen!
		table.setAttribute("style", "width:100%");
		table.addContent(caption(tab.getTitle()));
		renderHead(table, tab.getContent().getHead());
		renderBody(table, tab.getContent().getBody());

		parent.addContent(table);

		if(tab.getSpecification() != null){HtmlElement.addStyleElement(styleProperties(tab.getSpecification().getColumns()), html);}
	}

	private void renderHead(HtmlElement table, Head head)
	{
		for(Row r : head.getRow())
		{
			HtmlElement tr = new HtmlElement("tr");
			int column = 0;
			for(Cell c : r.getCell())
			{
				HtmlElement th = new HtmlElement("th");
				th.setAttribute("class", "col" + ++column); //Jede Zelle einer Spalte bekommt die gleiche class. Benötigt für spaltenweises CSS formatieren.
				for(Object o : c.getContent())
				{
					if(o instanceof Paragraph){paragraphRenderer(th, (Paragraph)o);}
					else if(o instanceof String){th.addContent((String)o);}
					else if(o instanceof Image){imageRenderer(th, (Image)o);}
				}
				tr.addContent(th);
			}
			table.addContent(tr);
		}
	}

	private void renderBody(HtmlElement table, java.util.List<Body> bodyList)
	{
		for(Body body : bodyList)
		{
			for(Row r : body.getRow())
			{
				HtmlElement tr = new HtmlElement("tr");
				int column = 0;
				for(Cell c : r.getCell())
				{
					HtmlElement td = new HtmlElement("td");
					td.setAttribute("class", "col" + ++column); //s. renderHead()
					for(Object o : c.getContent())
					{
						if(o instanceof Paragraph){
							paragraphContentRenderer(td, (Paragraph)o);}
						else if(o instanceof String){td.addContent((String)o);}
						else if(o instanceof Image){imageRenderer(td, (Image)o);}
						else if(o instanceof List){listRenderer(td, (List)o);}
					}
					tr.addContent(td);
				}
				table.addContent(tr);
			}
		}
	}

	private String styleProperties(Columns columns)
	{
		String prop="";
		int i = 0;
		for(Column c : columns.getColumn())
		{
			prop += ".col" + ++i + "{";
			if(c.getAlignment() != null && c.getAlignment().getHorizontal() != null){ prop += "text-align: " + c.getAlignment().getHorizontal() + ";";}
			else{prop += "text-align: left;";}
			if(c.getWidth() != null && c.getWidth().getValue() != 0.0)
			{
				prop += "width: " + c.getWidth().getValue();
				if(c.getWidth().getUnit() != null)
				{
					if(c.getWidth().getUnit().equals("percentage")){prop += "%;";}
					else{prop += c.getWidth().getUnit()+";";}
				}
			}
			prop += "}\n";
		}
		return prop;
	}

	private HtmlElement caption(Title title)
	{
		HtmlElement caption =  new HtmlElement("caption");
		caption.addContent(TxtTitleFactory.build(title));
		return caption;
	}

	/*Rein optische Entscheidung Grafikelemente in einer Tabelle als inline Elemente zu verarbeiten */
	public void imageRenderer(HtmlElement p, Image i)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cmm, dsm);
		imgRenderer.renderInline(p, i);
	}
}
