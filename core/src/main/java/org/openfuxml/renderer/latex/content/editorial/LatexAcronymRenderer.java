package org.openfuxml.renderer.latex.content.editorial;

import java.util.Objects;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.editorial.Acronym;
import org.openfuxml.model.xml.core.editorial.Acronyms;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.model.xml.core.editorial.Term;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.text.Text;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.content.text.LatexTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexAcronymRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexAcronymRenderer.class);
	
	public LatexAcronymRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Acronym acronym) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\\acrshort{");
		sb.append(acronym.getCode());
		sb.append("}");
		
		txt.add(sb.toString());
	}
	
	public void render(Acronyms acronyms) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Acronyms.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		if(Objects.nonNull(acronyms.getComment()))
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cp);
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
		if(Objects.isNull(term.getCode())) {throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a @code");}
		if(Objects.isNull(term.getText())) {throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a "+Text.class.getSimpleName());}
		if(Objects.isNull(term.getParagraph())) {throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+" needs a "+Paragraph.class.getSimpleName());}	
		
		LatexTextRenderer tr = new LatexTextRenderer(cp);
		tr.render(term.getText());
		
		LatexParagraphRenderer pr = new LatexParagraphRenderer(cp,false);
		pr.render(term.getParagraph());
		
		txt.add("\\newacronym{"+term.getCode()+"}");
		txt.add("{"+tr.getContentAsSingleLine(" ")+"}");
		txt.add("{"+pr.getContentAsSingleLine(" ")+"}");
	}
}