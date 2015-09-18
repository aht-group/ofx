package org.openfuxml.renderer.latex.content.editorial;

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

public class LatexGlossaryRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexGlossaryRenderer.class);
	
	public LatexGlossaryRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Glossary glossary) throws OfxAuthoringException
	{	
		if(glossary.isSetCode())
		{
			renderGlossaryItem(glossary);
		}
		else
		{
			renderGlossary(glossary);
		}
	}
	
	private void renderGlossaryItem(Glossary glossary) throws OfxAuthoringException
	{

	}
	
	private void renderGlossary(Glossary glossary) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Glossary.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		if(glossary.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cmm,dsm);
			rComment.render(glossary.getComment());
			preTxt.addAll(rComment.getContent());
		}
		
		for(Term t : glossary.getTerm())
		{
			renderGlossaryTerm(t);
		}
	}
	
	private void renderGlossaryTerm(Term term) throws OfxAuthoringException
	{
		if(!term.isSetCode()){throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a @code");}
		if(!term.isSetText()){throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a "+Text.class.getSimpleName());}
		if(!term.isSetParagraph()){throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a "+Paragraph.class.getSimpleName());}
		
		
		
		
		LatexTextRenderer tr = new LatexTextRenderer(cmm,dsm);
		tr.render(term.getText());
		
		txt.add("");
		txt.add("\\newglossaryentry{"+term.getCode()+"}");
		txt.add("{");		
		txt.add("\tname="+tr.getContentAsSingleLine(" ")+",");

		txt.add("\tdescription={");
		LatexParagraphRenderer pr = new LatexParagraphRenderer(cmm,dsm,false);
		pr.render(term.getParagraph());
		txt.addAll(pr.getContent());
		txt.add("}");
		txt.add("}");
	}
}