package org.openfuxml.renderer.text;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTextSilentRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxTextSilentRenderer.class);
	
	public static void table(Table table, OutputStream os) 
	{
		try
		{
			OfxTextRenderer renderer = new OfxTextRenderer();
			renderer.render(table, os);
		}
		catch (OfxAuthoringException e) {logger.error(e.getMessage());}
		catch (IOException e) {logger.error(e.getMessage());}
	}
	
	public static void table(ResultSet rs, OutputStream os)
	{
		try{table(OfxTableFactory.build(rs),os);}
		catch (SQLException e) {logger.error(e.getMessage());}
	}
}