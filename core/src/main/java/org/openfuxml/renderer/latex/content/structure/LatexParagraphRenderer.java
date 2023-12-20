package org.openfuxml.renderer.latex.content.structure;

import java.util.List;
import java.util.Objects;

import org.openfuxml.content.editorial.Acronym;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.editorial.Index;
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
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.editorial.LatexAcronymRenderer;
import org.openfuxml.renderer.latex.content.editorial.LatexGlossaryRenderer;
import org.openfuxml.renderer.latex.content.editorial.LatexIndexRenderer;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.openfuxml.renderer.latex.content.text.LatexEmphasisRenderer;
import org.openfuxml.renderer.latex.content.text.LatexSymbolRenderer;
import org.openfuxml.renderer.latex.structure.LatexReferenceRenderer;
import org.openfuxml.renderer.latex.util.LatexFontUtil;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class LatexParagraphRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexParagraphRenderer.class);
	
	public LatexParagraphRenderer(ConfigurationProvider cp, boolean preBlankLine)
	{
		super(cp);
		if(preBlankLine){preTxt.add("");}
	}
	
	public void render(List<Paragraph> paragraphs) throws OfxAuthoringException
	{	
		for(int i=0;i<paragraphs.size();i++)
		{
			render(paragraphs.get(i));
			if(i!=paragraphs.size()-1 && paragraphs.size()>0){txt.add("");}
		}
	}
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{	
		JaxbUtil.trace(paragraph);
		int nonInlineCounter = 0;
		
		Image image=null;
		Font font = null;
		for(Object o : paragraph.getContent())
		{
			if(o instanceof Image)
			{
				image = (Image)o;
				if(Objects.isNull(image.getAlignment()) || Objects.isNull(image.getAlignment().getHorizontal()))
				{
					throw new OfxAuthoringException(Image.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" needs an horizontal "+Alignment.class.getSimpleName());
				}
				XmlAlignmentFactory.Horizontal horizontal = XmlAlignmentFactory.Horizontal.valueOf(image.getAlignment().getHorizontal());
				if(!horizontal.equals(XmlAlignmentFactory.Horizontal.inline)){nonInlineCounter++;}
			}
			if(o instanceof Font)
			{
				if(font!=null){throw new OfxAuthoringException("More than one "+Font.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" not allowed");}
				font = (Font)o;
			}
		}
		if(nonInlineCounter>1){throw new OfxAuthoringException("More than one non-inline "+Image.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" not allowed");}
		
		if(font!=null){txt.add(LatexFontUtil.environmentBegin(font));}
		
		if(nonInlineCounter==1 && image!=null)
		{
			paragraph.getContent().remove(image);
			LatexImageRenderer rImage = new LatexImageRenderer(cp);
			rImage.render(this,image);
			txt.add("\\begin{window}[0, r, "+rImage.getSingleLine()+", {}]");
		}
		
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append(TexSpecialChars.replace((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(sb, (Emphasis)o);}
			else if(o instanceof Reference){renderReference(sb,(Reference)o);}
			else if(o instanceof Marginalia){renderMarginalia(sb,(Marginalia)o);}
			else if(o instanceof Symbol){renderSymbol(sb,(Symbol)o);}
			else if(o instanceof Glossary){renderGlossary(sb, (Glossary)o);}
			else if(o instanceof Acronym){renderAcronym(sb, (Acronym)o);}
			else if(o instanceof Image){logger.warn("INLINE Image NYI");}
			else if(o instanceof Index){renderIndex(sb,(Index)o);}
			else if(o instanceof Font){}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
		
		if(nonInlineCounter==1 && image!=null)
		{
			txt.add("\\end{window}");
		}
		
		if(font!=null){txt.add(LatexFontUtil.environmentEnd());}
	}
	
	private void renderEmphasis(StringBuffer sb, Emphasis emphasis) throws OfxAuthoringException
	{
		LatexEmphasisRenderer stf = new LatexEmphasisRenderer(cp);
		stf.render(emphasis);
		for(String s : stf.getContent()){sb.append(s);}
	}
	
	private void renderGlossary(StringBuffer sb, Glossary glossary) throws OfxAuthoringException
	{
		LatexGlossaryRenderer stf = new LatexGlossaryRenderer(cp);
		stf.render(glossary);
		for(String s : stf.getContent()){sb.append(s);}
	}
	
	private void renderAcronym(StringBuffer sb, Acronym acronym) throws OfxAuthoringException
	{
		LatexAcronymRenderer stf = new LatexAcronymRenderer(cp);
		stf.render(acronym);
		for(String s : stf.getContent()){sb.append(s);}
	}

	private void renderIndex(StringBuffer sb, Index index) throws OfxAuthoringException
	{
		LatexIndexRenderer stf = new LatexIndexRenderer(cp);
		stf.render(index);
		for(String s : stf.getContent()){sb.append(s);}
	}
	
	protected void renderReference(StringBuffer sb, Reference reference) throws OfxAuthoringException
	{
		LatexReferenceRenderer lrr = new LatexReferenceRenderer(cp);
		lrr.render(reference);
		for(String s : lrr.getContent()){sb.append(s);}
	}
	
	protected void renderMarginalia(StringBuffer sb, Marginalia marginalia) throws OfxAuthoringException
	{
		LatexMarginaliaRenderer lmr = new LatexMarginaliaRenderer(cp);
		lmr.render(marginalia);
		for(String s : lmr.getContent()){sb.append(s);}
	}
	
	protected void renderSymbol(StringBuffer sb, Symbol symbol) throws OfxAuthoringException
	{
		LatexSymbolRenderer lmr = new LatexSymbolRenderer(cp);
		lmr.render(symbol);
		for(String s : lmr.getContent()){sb.append(s);}
	}
}