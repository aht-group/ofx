package org.openfuxml.renderer.html.table;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.*;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.structure.HtmlDocumentRenderer;
import org.openfuxml.renderer.html.util.HtmlFontUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlTableRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlTableRenderer.class);

	public static int tblcount = 0;

	@Deprecated
	public HtmlTableRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}

	public HtmlTableRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, Table tab)
	{
		tblcount++;
		HtmlElement table = new HtmlElement("table");
		table.setAttribute("id", tab.getId()); //benötigt für interne Referenzen!
		table.setAttribute("style", "width:100%");
		if(tab.isSetComment()){commentRenderer(table, tab.getComment());}

		//Reihenfolge beachten: Caption, Head, Foot, Body. Foot ist optional
		table.addContent(caption(tab.getTitle()));
		renderHead(table, tab.getContent().getHead());
		if(tab.getContent().isSetFoot()){renderFoot(table, tab.getContent().getFoot());}
		renderBody(table, tab.getContent().getBody());

		parent.addContent(table);

		if(tab.isSetSpecification() && tab.getSpecification() != null){HtmlElement.addStyleElement(styleProperties(tab.getSpecification().getColumns()), HtmlDocumentRenderer.getInstance().getHtml());}
	}

	private void renderHead(HtmlElement table, Head head)
	{
		HtmlElement thead = new HtmlElement("thead");
		for(Row r : head.getRow())
		{
			HtmlElement tr = new HtmlElement("tr");
			int column = 0;
			for(Cell c : r.getCell())
			{
				HtmlElement th = new HtmlElement("th");
				//Jede Zelle einer Spalte bekommt das gleiche "class" Attribut. Benötigt für spaltenweises formatieren via CSS.
				th.setAttribute("class", "col" + ++column);
				for(Object o : c.getContent()){
					cellContent(o, th);}
				tr.addContent(th);
			}
			thead.addContent(tr);

		}
		table.addContent(thead);
	}

	private void renderFoot(HtmlElement table, Foot foot)
	{
		HtmlElement tfoot = new HtmlElement("tfoot");
			for(Row r : foot.getRow())
			{
				rowContent(tfoot, r);
			}
		table.addContent(tfoot);
	}

	private void renderBody(HtmlElement table, java.util.List<Body> bodyList)
	{
		for(Body body : bodyList)
		{
			HtmlElement tbody = new HtmlElement("tbody");
			for(Row r : body.getRow())
			{
				rowContent(tbody, r);
			}
			table.addContent(tbody);
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
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cp);
		imgRenderer.renderInline(p, i);
	}

	private void rowContent(HtmlElement part,Row row)
	{
		HtmlElement tr = new HtmlElement("tr");
		int column = 0;
		for(Cell c : row.getCell())
		{
			HtmlElement td = new HtmlElement("td");
			td.setAttribute("class", "col" + ++column); //s. renderHead()
			for(Object o : c.getContent()){
				cellContent(o, td);}
			tr.addContent(td);
		}
		part.addContent(tr);
	}

	private void cellContent(Object o, HtmlElement td)
	{
		if(o instanceof Paragraph){paragraphContentRenderer(td, (Paragraph)o);}
		else if(o instanceof String){td.addContent((String)o);}
		else if(o instanceof Image){imageRenderer(td, (Image)o);}
		else if(o instanceof List){listRenderer(td, (List)o);}
		else if(o instanceof Font){td.setStyleAttribute(HtmlFontUtil.fontSize(((Font)o)));}
	}
}
