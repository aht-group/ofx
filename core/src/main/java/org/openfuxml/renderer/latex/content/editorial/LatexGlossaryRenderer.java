package org.openfuxml.renderer.latex.content.editorial;

import java.util.List;
import java.util.Objects;

import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.editorial.Term;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Text;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
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
	
	public LatexGlossaryRenderer(ConfigurationProvider cp)
	{
		super(cp);
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
		StringBuffer sb = new StringBuffer();
		sb.append("\\gls{");
		sb.append(glossary.getCode());
		sb.append("}");
		
		txt.add(sb.toString());
	}
	
	private void renderGlossary(Glossary glossary) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Glossary.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		if(glossary.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cp);
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
				
//		LatexTextRenderer tr = new LatexTextRenderer(cmm,dsm);
//		tr.render(term.getText());
		
		LatexParagraphRenderer pr = new LatexParagraphRenderer(cp,false);
		pr.render(term.getParagraph());
		
		List<String> c = pr.getContent();
		if(c.size()>0)
		{
			c.set(0, "\tdescription={"+c.get(0));
			
			int indexLast = c.size()-1;
			c.set(indexLast, c.get(indexLast)+"}");
			
		}
		else{c.add("}");}
		
		txt.add("");
		txt.add("\\newglossaryentry{"+term.getCode()+"}");
		txt.add("{");
		for(Text t : term.getText())
		{
			if(Objects.isNull(t.getClassifier())) {throw new OfxAuthoringException(Glossary.class.getSimpleName()+"."+Term.class.getSimpleName()+"."+Text.class.getSimpleName()+" needs a classifier! Code:"+term.getCode());}
			LatexTextRenderer tr = new LatexTextRenderer(cp);
			tr.render(t);
			txt.add("\t"+t.getClassifier()+"="+tr.getContentAsSingleLine(" ")+",");
		}
		
		txt.addAll(c);
		txt.add("}");
	}
}