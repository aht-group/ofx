package org.openfuxml.renderer.text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.text.structure.TextParagraphRenderer;
import org.openfuxml.renderer.text.structure.TextSectionRenderer;
import org.openfuxml.renderer.text.table.TextCellRenderer;
import org.openfuxml.renderer.text.table.TextTableRenderer;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTextRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxTextRenderer.class);
	
	private OfxConfigurationProvider cp;
	
	private final TextParagraphRenderer paragraphRenderer;
	
	public OfxTextRenderer()
	{
		cp = new OfxConfigurationProvider();
		cp.setCrossMediaManager(new NoOpCrossMediaManager());
		cp.setDefaultSettingsManager(new OfxDefaultSettingsManager());
		
		paragraphRenderer = new TextParagraphRenderer(cp,false);
	}
	
	public void render(Document document, Writer writer) throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		for(Serializable s : document.getContent().getContent())
		{
			if(s instanceof Section){renderSection((Section)s,writer);}
		}
	}
	
	private void renderSection(Section s,  Writer writer) throws OfxAuthoringException, IOException
	{
		TextSectionRenderer r = new TextSectionRenderer(cp,true);
		r.render(s);
		for(String line : r.getContent()) {writer.write(line+System.lineSeparator());}
	}
	
	
	
	public static void table(ResultSet rs, OutputStream os) throws OfxAuthoringException, IOException
	{
		try{table(XmlTableFactory.build(rs),os);}
		catch (SQLException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	public static void table(Table table, OutputStream os) throws OfxAuthoringException, IOException
	{
		OfxTextRenderer renderer = new OfxTextRenderer();
		renderer.render(table, os);
	}
	public static void silent(Table table, OutputStream os)
	{
		try
		{
			OfxTextRenderer renderer = new OfxTextRenderer();
			renderer.render(table, os);
		}
		catch (OfxAuthoringException e) {logger.error(e.getMessage());}
		catch (IOException e) {logger.error(e.getMessage());}
	}
	public void render(Table table, OutputStream os) throws OfxAuthoringException, IOException
	{
		TextTableRenderer renderer = new TextTableRenderer(cp);
		renderer.render(table);
		PrintWriter w = new PrintWriter(os,true);
		for(String s : renderer.getContent()){w.println(s);}
	}
	
	public void render(Cell cell, OutputStream os) throws OfxAuthoringException, IOException
	{
		TextCellRenderer renderer = new TextCellRenderer(cp);
		renderer.render(cell);
		PrintWriter w = new PrintWriter(os,true);
		for(String s : renderer.getContent()){w.println(s);}
	}
	
	public String toString(Paragraph p) throws OfxAuthoringException
	{
		return paragraphRenderer.toText(p);
	}
}