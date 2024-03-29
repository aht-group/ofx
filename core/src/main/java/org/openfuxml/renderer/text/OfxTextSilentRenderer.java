package org.openfuxml.renderer.text;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.XmlTableFactory;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTextSilentRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxTextSilentRenderer.class);
	
	private OfxTextRenderer renderer;
	
	public OfxTextSilentRenderer()
	{
		renderer = new OfxTextRenderer();
	}
	
	public static void table(ResultSet rs, OutputStream os)
	{
		try{OfxTextRenderer.silent(XmlTableFactory.build(rs),os);}
		catch (SQLException e) {logger.error(e.getMessage());}
	}
	
	public static void cell(Cell cell, OutputStream os)
	{
		try
		{
			OfxTextRenderer renderer = new OfxTextRenderer();
			renderer.render(cell, os);
		}
		catch (OfxAuthoringException e) {logger.error(e.getMessage());}
		catch (IOException e) {logger.error(e.getMessage());}
	}
	
	public String toString(Paragraph p)
	{
		try {return renderer.toString(p);}
		catch (OfxAuthoringException e) {e.printStackTrace();}
		return "";
	}
}