package org.openfuxml.renderer.text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.text.table.TextCellRenderer;
import org.openfuxml.renderer.text.table.TextTableRenderer;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTextRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxTextRenderer.class);
	
	private CrossMediaManager cmm;
	private DefaultSettingsManager dsm;
	
	public OfxTextRenderer()
	{
		cmm = new NoOpCrossMediaManager();
		dsm = new OfxDefaultSettingsManager();
	}
	
	public static void table(ResultSet rs, OutputStream os) throws OfxAuthoringException, IOException
	{
		try{table(OfxTableFactory.build(rs),os);}
		catch (SQLException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	public static void table(Table table, OutputStream os) throws OfxAuthoringException, IOException
	{
		OfxTextRenderer renderer = new OfxTextRenderer();
		renderer.render(table, os);
	}
	public void render(Table table, OutputStream os) throws OfxAuthoringException, IOException
	{
		TextTableRenderer renderer = new TextTableRenderer(cmm,dsm);
		renderer.render(table);
		PrintWriter w = new PrintWriter(os,true);
		for(String s : renderer.getContent()){w.println(s);}
	}
	
	public void render(Cell cell, OutputStream os) throws OfxAuthoringException, IOException
	{
		TextCellRenderer renderer = new TextCellRenderer(cmm,dsm);
		renderer.render(cell);
		PrintWriter w = new PrintWriter(os,true);
		for(String s : renderer.getContent()){w.println(s);}
	}
}