package org.openfuxml.renderer.latex.content.structure;

import java.util.List;

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
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.openfuxml.renderer.latex.content.text.LatexEmphasisRenderer;
import org.openfuxml.renderer.latex.content.text.LatexSymbolRenderer;
import org.openfuxml.renderer.latex.structure.LatexReferenceRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class LatexParagraphRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexParagraphRenderer.class);
	
	public LatexParagraphRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm,boolean preBlankLine)
	{
		super(cmm,dsm);
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
				if(!image.isSetAlignment() || !image.getAlignment().isSetHorizontal())
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
		
		if(font!=null)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			if(font.isSetRelativeSize())
			{
				if(font.getRelativeSize()==-4){sb.append("\\tiny");}
				else if(font.getRelativeSize()==-3){sb.append("\\scriptsize");}
				else if(font.getRelativeSize()==-2){sb.append("\\footnotesize");}
				else if(font.getRelativeSize()==-1){sb.append("\\small");}
				else if(font.getRelativeSize()==0){sb.append("\\normalsize");}
				else if(font.getRelativeSize()==1){sb.append("\\large");}
				else if(font.getRelativeSize()==2){sb.append("\\Large");}
				else if(font.getRelativeSize()==3){sb.append("\\LARGE");}
				else if(font.getRelativeSize()==4){sb.append("\\huge");}
				else if(font.getRelativeSize()==5){sb.append("\\Huge");}
			}
			txt.add(sb.toString());
		}
		
		if(nonInlineCounter==1 && image!=null)
		{
			paragraph.getContent().remove(image);
			LatexImageRenderer rImage = new LatexImageRenderer(cmm,dsm);
			rImage.render(this,image);
			txt.add("\\begin{window}[0, r, "+rImage.getSingleLine()+", {}]");
		}
		
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append(TexSpecialChars.replace((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(sb, (Emphasis)o);}
			else if(o instanceof Image){logger.warn("INLINE Image NYI");}
			else if(o instanceof Reference){renderReference(sb,(Reference)o);}
			else if(o instanceof Marginalia){renderMarginalia(sb,(Marginalia)o);}
			else if(o instanceof Symbol){renderSymbol(sb,(Symbol)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
		
		if(nonInlineCounter==1 && image!=null)
		{
			txt.add("\\end{window}");
		}
		
		if(font!=null){txt.add("}");}
	}
	
	private void renderEmphasis(StringBuffer sb, Emphasis emphasis) throws OfxAuthoringException
	{
		LatexEmphasisRenderer stf = new LatexEmphasisRenderer(cmm,dsm);
		stf.render(emphasis);
		for(String s : stf.getContent()){sb.append(s);}
	}
	
	protected void renderReference(StringBuffer sb, Reference reference) throws OfxAuthoringException
	{
		LatexReferenceRenderer lrr = new LatexReferenceRenderer(cmm,dsm);
		lrr.render(reference);
		for(String s : lrr.getContent()){sb.append(s);}
	}
	
	protected void renderMarginalia(StringBuffer sb, Marginalia marginalia) throws OfxAuthoringException
	{
		LatexMarginaliaRenderer lmr = new LatexMarginaliaRenderer(cmm,dsm);
		lmr.render(marginalia);
		for(String s : lmr.getContent()){sb.append(s);}
	}
	
	protected void renderSymbol(StringBuffer sb, Symbol symbol) throws OfxAuthoringException
	{
		LatexSymbolRenderer lmr = new LatexSymbolRenderer(cmm,dsm);
		lmr.render(symbol);
		for(String s : lmr.getContent()){sb.append(s);}
	}
}