package org.openfuxml.renderer.word.content;

import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.editorial.Acronym;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.model.xml.core.editorial.Index;
import org.openfuxml.model.xml.core.layout.Font;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.text.Emphasis;
import org.openfuxml.model.xml.core.text.Symbol;
import org.openfuxml.renderer.word.util.RemoveUnwantedRegx;
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

	private Document doc;
	private DocumentBuilder builder;
	
	public WordParagraphRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}
	
	public void render(org.openfuxml.content.ofx.Paragraph ofxParagraph, int paragraphCount, int paragraphCurrent) throws OfxAuthoringException
	{
		SetFont sF = new SetFont(doc, builder);sF.setFont(setFontEnum.text);
		
		SetAlignment sA = new SetAlignment(doc, builder);
		sA.setAlignment(setAlignmentEnum.left);
		ParagraphFormat paragraphFormat = builder.getParagraphFormat();
		paragraphFormat.setFirstLineIndent(0);
		paragraphFormat.setKeepTogether(true);

		for(Object o : ofxParagraph.getContent())
		{			
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String)
			{
				
				String s =(String)o;
				if(s.length()>0)
				{
					RemoveUnwantedRegx rUr = new RemoveUnwantedRegx();
					try { builder.write(rUr.removeUnwantedFrom(s));	} catch (Exception e) {	builder.write(s); }
			    }
			}
			else if(o instanceof Emphasis){renderEmphasis((Emphasis)o);}
			else if(o instanceof Reference){renderReference((Reference)o);}
			else if(o instanceof Marginalia){renderMarginalia((Marginalia)o);}
			else if(o instanceof Symbol){renderSymbol((Symbol)o);}
			else if(o instanceof Glossary){renderGlossary( (Glossary)o);}
			else if(o instanceof Acronym){renderAcronym( (Acronym)o);}
			else if(o instanceof Image){logger.warn("INLINE Image NYI");}
			else if(o instanceof Index){renderIndex((Index)o);}
			else if(o instanceof Font){}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		if ((paragraphCount!=paragraphCurrent)) {builder.writeln();}
		else if (paragraphCount!=paragraphCurrent) {}
	}
	
	public void render(org.openfuxml.content.ofx.Paragraph ofxParagraph) throws OfxAuthoringException
	{
		for(Object o : ofxParagraph.getContent())
		{			
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String)
			{
				
				String s =(String)o;
				if(s.length()>0)
				{
					RemoveUnwantedRegx rUr = new RemoveUnwantedRegx();
					try { builder.write(rUr.removeUnwantedFrom(s));	} catch (Exception e) {	builder.write(s); }
			    }
			}
			else if(o instanceof Emphasis){renderEmphasis((Emphasis)o);}
			else if(o instanceof Reference){renderReference((Reference)o);}
			else if(o instanceof Marginalia){renderMarginalia((Marginalia)o);}
			else if(o instanceof Symbol){renderSymbol((Symbol)o);}
			else if(o instanceof Glossary){renderGlossary( (Glossary)o);}
			else if(o instanceof Acronym){renderAcronym( (Acronym)o);}
			else if(o instanceof Image){logger.warn("INLINE Image NYI");}
			else if(o instanceof Index){renderIndex((Index)o);}
			else if(o instanceof Font){}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
	}
	
	private void renderIndex( Index o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderAcronym(Acronym o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderGlossary(Glossary o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderSymbol(Symbol o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderMarginalia(Marginalia o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderReference(Reference o)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderEmphasis(org.openfuxml.model.xml.core.text.Emphasis ofxEmphasis) throws OfxAuthoringException
	{
		WordEmphasisRenderer sf = new WordEmphasisRenderer(doc,builder);
		sf.render(ofxEmphasis);
		
	}
}
