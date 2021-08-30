package org.openfuxml.renderer.html;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.OfxRenderer;
import org.openfuxml.renderer.html.structure.HtmlSectionRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class OfxHtmlRenderer extends OfxRenderer
{
	Logger logger = LoggerFactory.getLogger(OfxHtmlRenderer.class);

	private List<String> txt; public List<String> getContent(){return txt;}
	
//	private HtmlDocumentRenderer document;

	public OfxHtmlRenderer(ConfigurationProvider cp, String pageTitle)
	{
		txt = new ArrayList<String>();
		logger = LoggerFactory.getLogger(OfxHtmlRenderer.class);
//		document = new HtmlDocumentRenderer(cp,pageTitle);
	}

	@Override
	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{

	}
	
	public void render(Writer writer, Section section) throws OfxAuthoringException, OfxConfigurationException, IOException
	{
		HtmlElement root = new HtmlElement("div");
		HtmlSectionRenderer renderer = new HtmlSectionRenderer(new OfxConfigurationProvider(),1);
        renderer.render(root,section);
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
}