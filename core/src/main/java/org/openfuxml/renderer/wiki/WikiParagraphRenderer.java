package org.openfuxml.renderer.wiki;

import org.openfuxml.content.editorial.Acronym;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.content.text.Symbol;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.wiki.OfxWikiRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.util.LatexFontUtil;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class WikiParagraphRenderer extends AbstractOfxLatexRenderer implements OfxWikiRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WikiParagraphRenderer.class);

	public WikiParagraphRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm) {
		super(cmm, dsm);
	}
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{	
		JaxbUtil.info(paragraph);
		
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
				//TODO Sepa
			else if(o instanceof String)
			{
				txt.add((String)o);
			}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		
	}
	
	
}
