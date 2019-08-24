package org.openfuxml.trancoder.xml;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Document;
import org.jdom2.Element;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.XmlCellFactory;
import org.openfuxml.factory.xml.table.XmlColumnFactory;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppEngineTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(AppEngineTranscoder.class);
	
	private MultiResourceLoader mrl;
	
	public AppEngineTranscoder()
	{
		mrl = new MultiResourceLoader();
	}
	
	public Table cron(String fileName) throws FileNotFoundException
	{
		Document doc = JDomUtil.load(mrl.searchIs(fileName));

		Table table = new Table();
		table.setSpecification(createSpecifications());
		table.setContent(createContent(doc));
		table.getContent().setHead(buildHead());
		
		table.setId("my.id");
		table.setComment(comment(table.getId()));
		
		table.setTitle(XmlTitleFactory.build("Cron Jobs"));
		
		return table;
	}
	
	private Content createContent(Document doc)
	{
		Body body = new Body();

		Element rootNode = doc.getRootElement();
		
		for(Element cron : rootNode.getChildren("cron"))
		{
			body.getRow().add(createRow(cron));
		}
		
		Content content = new Content();
		content.getBody().add(body);
		
		return content;
	}
	
	private Row createRow(Element cron) 
	{		
		Row row = new Row();
		
		row.getCell().add(XmlCellFactory.createParagraphCell(cron.getChildText("url")));
		row.getCell().add(XmlCellFactory.createParagraphCell(cron.getChildText("schedule")));
		row.getCell().add(XmlCellFactory.createParagraphCell(cron.getChildText("description")));
		return row;
	}
	
	private Head buildHead()
	{
		Row row = new Row();
		
		row.getCell().add(XmlCellFactory.createParagraphCell("URL"));
		row.getCell().add(XmlCellFactory.createParagraphCell("Schedule"));
		row.getCell().add(XmlCellFactory.createParagraphCell("Frequency"));
		
		Head head = new Head();
		head.getRow().add(row);
		return head;
	}
	
	private Specification createSpecifications()
	{
		int flexWidth=0;int w=0;
		Columns cols = new Columns();
		
		w=20;flexWidth=flexWidth+w;cols.getColumn().add(XmlColumnFactory.flex(w,true));
		w=20;flexWidth=flexWidth+w;cols.getColumn().add(XmlColumnFactory.flex(w,true));
		cols.getColumn().add(XmlColumnFactory.flex());
		
		Specification specification = new Specification();
		specification.setColumns(cols);
		return specification;
	}
	
	private Comment comment(String id)
	{
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.fixedId(comment, id);
		OfxCommentBuilder.doNotModify(comment);
		return comment;
	}
}