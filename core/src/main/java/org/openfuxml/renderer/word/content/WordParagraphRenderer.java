package org.openfuxml.renderer.word.content;


import java.io.Serializable;

import org.openfuxml.content.editorial.Acronym;
import org.openfuxml.content.editorial.Glossary;
import org.openfuxml.content.editorial.Index;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.content.text.Symbol;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.text.LatexEmphasisRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.openfuxml.renderer.word.structure.WordSectionRenderer;
import org.openfuxml.renderer.word.util.SetAlignment;
import org.openfuxml.renderer.word.util.SetAlignment.setAlignmentEnum;
import org.openfuxml.renderer.word.util.SetFont;

import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphFormat;


public class WordParagraphRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(WordParagraphRenderer.class);

	Document doc;
	DocumentBuilder builder;
	
	public WordParagraphRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}
	
	public void render(org.openfuxml.content.ofx.Paragraph ofxParagraph, boolean withEmptyLineAfter) throws OfxAuthoringException
	{
		SetFont sF = new SetFont(doc, builder);sF.setFont(setFontEnum.text);
		
		StringBuffer sb = new StringBuffer();
		for(Object o : ofxParagraph.getContent())
		{
			SetAlignment sA = new SetAlignment(doc, builder);
			sA.setAlignment(setAlignmentEnum.left);
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setFirstLineIndent(0);
			paragraphFormat.setKeepTogether(true);
			
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append(TexSpecialChars.replace((String)o));}
			else if(o instanceof Emphasis){renderEmphasis((Emphasis)o);}
			else if(o instanceof Reference){renderReference(sb,(Reference)o);}
			else if(o instanceof Marginalia){renderMarginalia(sb,(Marginalia)o);}
			else if(o instanceof Symbol){renderSymbol(sb,(Symbol)o);}
			else if(o instanceof Glossary){renderGlossary(sb, (Glossary)o);}
			else if(o instanceof Acronym){renderAcronym(sb, (Acronym)o);}
			else if(o instanceof Image){logger.warn("INLINE Image NYI");}
			else if(o instanceof Index){renderIndex(sb,(Index)o);}
			else if(o instanceof Font){}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
			
			builder.write(sb.toString());
		}
		builder.writeln();
		if (withEmptyLineAfter) {builder.writeln();}
	}
	
	private void renderIndex(StringBuffer sb, Index o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderAcronym(StringBuffer sb, Acronym o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderGlossary(StringBuffer sb, Glossary o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderSymbol(StringBuffer sb, Symbol o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderMarginalia(StringBuffer sb, Marginalia o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderReference(StringBuffer sb, Reference o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderEmphasis(org.openfuxml.content.text.Emphasis ofxEmphasis) throws OfxAuthoringException
	{
		WordEmphasisRenderer sf = new WordEmphasisRenderer(doc,builder);
		sf.render(ofxEmphasis);
		
	}
}
