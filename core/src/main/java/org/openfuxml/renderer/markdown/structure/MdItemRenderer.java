package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdItemRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdItemRenderer.class);

	@Deprecated
	public MdItemRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public MdItemRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(Item item)
	{
		MdListRenderer.item += item.getName() + "\n";
		for(Object s : item.getContent())
		{
			if(s instanceof String){MdListRenderer.item +=((String)s).replaceAll("\t|\n", "");}
			if(s instanceof Paragraph){paragraphRenderer((Paragraph)s);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
	}

	public void paragraphRenderer(Paragraph paragraph)
	{
		MdParagraphRenderer paraR = new MdParagraphRenderer(cp);
		paraR.render(paragraph);
		for(String s : paraR.getContent()){MdListRenderer.item += s.replaceAll("\n", "");}
	}
}
