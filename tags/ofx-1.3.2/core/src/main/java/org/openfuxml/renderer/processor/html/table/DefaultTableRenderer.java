package org.openfuxml.renderer.processor.html.table;

import org.jdom.Element;
import org.jdom.Text;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.renderer.processor.html.interfaces.OfxTableRenderer;
import org.openfuxml.renderer.processor.html.section.ParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTableRenderer implements OfxTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(DefaultTableRenderer.class);
	
	private Element table;
	private ParagraphRenderer paragraphRenderer;

	public DefaultTableRenderer()
	{

	}
	
	public Element render(Table ofxTable)
	{
		table = new Element("table");
		
		renderContent(ofxTable.getContent());
		
		return table;
	}
	
	private void renderContent(Content content)
	{
		renderHeader(content.getHead());
		for(Body body : content.getBody())
		{
			renderBody(body);
		}
	}
	
	private void renderHeader(Head head)
	{
		Element tr = new Element("tr");
		
		for(Cell cell : head.getRow().get(0).getCell())
		{
			Element tCell = renderCell("th", cell);
			tr.addContent(tCell);
		}
		
		Element thead = new Element("thead");
		thead.addContent(tr);
		table.addContent(thead);
	}
	
	private void renderBody(Body body)
	{
		Element tbody = new Element("tbody");
		
		for(Row row : body.getRow())
		{
			tbody.addContent(renderRow(row));
		}
		
		table.addContent(tbody);
	}
	
	private Element renderRow(Row row)
	{
		Element tr = new Element("tr");
		for(Cell cell : row.getCell())
		{
			Element tCell = renderCell("td", cell);
			tr.addContent(tCell);
		}
		return tr;
	}
	
	private Element renderCell(String eName, Cell cell)
	{
		Element tCell = new Element(eName);
		for(Object o : cell.getContent())
		{
			if(o instanceof String){tCell.addContent(new Text((String)o));}
			else if(o instanceof Paragraph){tCell.addContent(getParagraphRenderer().render((Paragraph)o));}
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		return tCell;
	}
	
	private ParagraphRenderer getParagraphRenderer()
	{
		if(paragraphRenderer==null){paragraphRenderer = new ParagraphRenderer();}
		return paragraphRenderer;
	}
}