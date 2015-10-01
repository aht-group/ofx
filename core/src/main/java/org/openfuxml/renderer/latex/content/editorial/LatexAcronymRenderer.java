package org.openfuxml.renderer.latex.content.editorial;

import org.openfuxml.content.editorial.Acronym;
import org.openfuxml.content.editorial.Acronyms;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.editorial.Term;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Text;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.content.text.LatexTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexAcronymRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexAcronymRenderer.class);
	
	public LatexAcronymRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void renderGlossaryItem(Acronym acronym) throws OfxAuthoringException
	{

	}
	
	public void render(Acronyms acronyms) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Acronyms.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		if(acronyms.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cmm,dsm);
			rComment.render(acronyms.getComment());
			preTxt.addAll(rComment.getContent());
		}
		
		for(Term t : acronyms.getTerm())
		{
			render(t);
		}
	}
	
	private void render(Term term) throws OfxAuthoringException
	{
		if(!term.isSetCode()){throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a @code");}
		if(!term.isSetText()){throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a "+Text.class.getSimpleName());}
		if(!term.isSetParagraph()){throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a "+Paragraph.class.getSimpleName());}	
		
		LatexTextRenderer tr = new LatexTextRenderer(cmm,dsm);
		tr.render(term.getText());
		
		LatexParagraphRenderer pr = new LatexParagraphRenderer(cmm,dsm,false);
		pr.render(term.getParagraph());
		
		txt.add("\\newacronym{"+term.getCode()+"}");
		txt.add("{"+tr.getContentAsSingleLine(" ")+"}");
		txt.add("{");
		txt.addAll(pr.getContent());
		txt.add("}");
	}
}