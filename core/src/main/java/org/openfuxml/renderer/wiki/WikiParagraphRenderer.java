package org.openfuxml.renderer.wiki;

import org.openfuxml.content.editorial.Acronym;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.content.text.Symbol;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.wiki.OfxWikiRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
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
			else if(o instanceof String){txt.add((String)o);}
			else if(o instanceof Emphasis){emphasisRenderer(sb, (Emphasis)o);}
			else if(o instanceof Reference){}
			else if(o instanceof Marginalia){}
			else if(o instanceof Symbol){}
			else if(o instanceof Glossary){}
			else if(o instanceof Acronym){}
			else if(o instanceof Image){logger.warn("INLINE Image NYI");}
			else if(o instanceof Font){logger.warn("INLINE Image NYI");}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
	}
	
	private void emphasisRenderer(StringBuffer sb, Emphasis emphasis) throws OfxAuthoringException
	{
		WikiEmphasisRenderer wer = new WikiEmphasisRenderer(cmm, dsm);
		wer.render(emphasis);
		for(String s : wer.getContent()){sb.append(s);}
	}
}