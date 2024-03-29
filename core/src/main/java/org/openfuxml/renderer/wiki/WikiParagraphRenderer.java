package org.openfuxml.renderer.wiki;

import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.ofx.Reference;
import org.openfuxml.model.xml.core.text.Emphasis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiParagraphRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WikiParagraphRenderer.class);

	public WikiParagraphRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{	
		JaxbUtil.info(paragraph);
		
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append((String)o);}
			else if(o instanceof Emphasis){emphasisRenderer(sb, (Emphasis)o);}
			else if(o instanceof Reference){referenceRenderer(sb, (Reference)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
	}
	
	private void emphasisRenderer(StringBuffer sb, Emphasis emphasis) throws OfxAuthoringException
	{
		WikiEmphasisRenderer wer = new WikiEmphasisRenderer(cp);
		wer.render(emphasis);
		for(String s : wer.getContent()){sb.append(s);}
	}
	
	//TODO FIX
	private void referenceRenderer(StringBuffer sb, Reference reference) throws OfxAuthoringException
	{
//		WikiReferenceRenderer wrr = new WikiReferenceRenderer(cmm, dsm);
//		wrr.render(reference);
//		for(String s : wrr.getContent()){sb.append(s);}
	}
}
