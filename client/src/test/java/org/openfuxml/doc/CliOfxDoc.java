package org.openfuxml.doc;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.OfxClientBootstrap;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.text.OfxTextSilentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class CliOfxDoc
{
	final static Logger logger = LoggerFactory.getLogger(CliOfxDoc.class);
	
	public CliOfxDoc()
	{
		
	}
	
	public void tlMgr() throws FileNotFoundException
	{
		Section section = JaxbUtil.loadJAXB("ofx/ofx/renderer/latex/packages.xml", Section.class);
		Table table = XmlSectionFactory.toTables(section).get(0);
		JaxbUtil.info(table);
		StringBuilder sb = new StringBuilder();
		sb.append("sudo tlmgr install ");
		for(Row row : table.getContent().getBody().get(0).getRow())
		{
			Cell cell = row.getCell().get(0);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OfxTextSilentRenderer.cell(cell, baos);
			sb.append(baos.toString().trim());
			sb.append(" ");
		}
		logger.info(sb.toString());
	}
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = OfxClientBootstrap.init();
		logger.info("TEST "+(config!=null));
		
		CliOfxDoc cli = new CliOfxDoc();
		cli.tlMgr();
	}
}