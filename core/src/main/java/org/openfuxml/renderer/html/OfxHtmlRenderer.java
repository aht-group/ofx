package org.openfuxml.renderer.html;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.html.structure.HtmlSectionRenderer;
import org.openfuxml.renderer.html.table.HtmlTableRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class OfxHtmlRenderer extends OfxRenderer
{
	Logger logger = LoggerFactory.getLogger(OfxHtmlRenderer.class);

	private ConfigurationProvider cp;
	private List<String> txt; public List<String> getContent() {return txt;}

	public OfxHtmlRenderer(ConfigurationProvider cp, String pageTitle)
	{
		this.cp=cp;
		txt = new ArrayList<String>();
	}

	@Override
	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{

	}
	
	public void render(Writer writer, Section section) throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		HtmlElement root = new HtmlElement("div");
		HtmlSectionRenderer renderer = new HtmlSectionRenderer(cp,1);
        renderer.render(root,section);
//      JDomUtil.debug(root);
        XMLOutputter xmlOutput = new XMLOutputter(OfxHtmlRenderer.ownPrettyFormat());
        xmlOutput.output(root, writer);
	}
	
	public void render(Writer writer, Table table) throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		HtmlElement root = new HtmlElement("div");
		HtmlTableRenderer renderer = new HtmlTableRenderer(cp);
        renderer.render(root,table);
        JDomUtil.debug(root);
        XMLOutputter xmlOutput = new XMLOutputter(OfxHtmlRenderer.ownPrettyFormat());
        xmlOutput.output(root, writer);
	}
	
	public static Format ownPrettyFormat()
	{
		Format f = Format.getPrettyFormat();
//		f.setTextMode(Format.TextMode.PRESERVE);
		f.setTextMode(Format.TextMode.TRIM);
		f.setIndent("\t");
		f.setOmitDeclaration(true);
		return f;
	}
	
	public static void silent(Table table, OutputStream os)
	{
		OfxConfigurationProvider cp = new OfxConfigurationProvider();
		cp.setCrossMediaManager(new NoOpCrossMediaManager());
		cp.setDefaultSettingsManager(new OfxDefaultSettingsManager());
		
		PrintWriter pw = new PrintWriter(os);
		
		OfxHtmlRenderer renderer = new OfxHtmlRenderer(cp,"test");
		try {
			renderer.render(pw,table);
		} catch (OfxAuthoringException | OfxConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}