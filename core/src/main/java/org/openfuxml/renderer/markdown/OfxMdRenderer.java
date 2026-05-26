package org.openfuxml.renderer.markdown;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.markdown.structure.MdDocumentRenderer;
import org.openfuxml.renderer.markdown.table.MdTableRenderer;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.LoggerFactory;

public class OfxMdRenderer extends OfxRenderer
{
	private List<String> txt;
	private MdDocumentRenderer rDocument;

	private OfxConfigurationProvider cp;

	
	public static OfxMdRenderer instance() {return new OfxMdRenderer();}
	public OfxMdRenderer()
	{
		cp = new OfxConfigurationProvider();
		cp.setCrossMediaManager(new NoOpCrossMediaManager());
		cp.setDefaultSettingsManager(new OfxDefaultSettingsManager());
	}
	
	@Deprecated
	public OfxMdRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		logger = LoggerFactory.getLogger(OfxMdRenderer.class);
		txt = new ArrayList<String>();
		rDocument = new MdDocumentRenderer(cmm, dsm);
	}

	public OfxMdRenderer(ConfigurationProvider cp)
	{
		logger = LoggerFactory.getLogger(OfxMdRenderer.class);
		txt = new ArrayList<String>();
		rDocument = new MdDocumentRenderer(cp);
	}

	public List<String> getContent(){return txt;}

	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		rDocument.render(ofxDocument.getContent());
		txt.addAll(rDocument.getContent());
	}
	
	public void render(Table table, Path path) throws OfxAuthoringException, IOException
	{
		try (OutputStream os = Files.newOutputStream(path,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE))
		{
			this.render(table,os);
	    }
	} 
	
	public void render(Table table, OutputStream os) throws OfxAuthoringException, IOException
	{
		MdTableRenderer renderer = new MdTableRenderer(cp);
		renderer.render(table);
		PrintWriter w = new PrintWriter(os,true);
		for(String s : renderer.getContent()) {w.println(s);}
	}
}