package org.openfuxml.renderer.docx.aspose.content;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.editorial.Acronym;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.model.xml.core.editorial.Index;
import org.openfuxml.model.xml.core.layout.Font;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.ofx.Marginalia;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.ofx.Reference;
import org.openfuxml.model.xml.core.text.Emphasis;
import org.openfuxml.model.xml.core.text.Symbol;
import org.openfuxml.renderer.word.util.RemoveUnwantedRegx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.ParagraphFormat;

public class AsposeDocxParagraphRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(AsposeDocxParagraphRenderer.class);

	private DocumentBuilder builder;
	
	public AsposeDocxParagraphRenderer(DocumentBuilder builder) {this.builder=builder;}
	
	public void render(org.openfuxml.model.xml.core.ofx.Paragraph ofxParagraph, int paragraphCount, int paragraphCurrent) throws OfxAuthoringException
	{
		//SetFont sF = new SetFont(doc, builder);sF.setFont(setFontEnum.text);
		
		
		ParagraphFormat paragraphFormat = builder.getParagraphFormat();
		paragraphFormat.setFirstLineIndent(0);
		paragraphFormat.setKeepTogether(true);
		paragraphFormat.setAlignment(ParagraphAlignment.LEFT);
		paragraphFormat.setLeftIndent(0);
		paragraphFormat.setRightIndent(0);

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
	
	public void render(org.openfuxml.model.xml.core.ofx.Paragraph ofxParagraph) throws OfxAuthoringException
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
		AsposeDocxEmphasisRenderer sf = new AsposeDocxEmphasisRenderer(builder);
		sf.render(ofxEmphasis);
		
	}
}
