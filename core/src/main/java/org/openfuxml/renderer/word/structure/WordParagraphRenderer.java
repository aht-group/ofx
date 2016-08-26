package org.openfuxml.renderer.word.structure;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;

public class WordParagraphRenderer extends AbstractOfxWordRenderer {

	public WordParagraphRenderer(ConfigurationProvider cp) throws FileNotFoundException, IOException
	{
		super(cp);
	}
	
	/**
	 * Erstellt einen leeren Paragraphen am Anfang, für alle weiteren Informationen
	 * @param doc x
	 * @param paragraph x
	 * @return x
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public XWPFDocument renderer(XWPFDocument doc, Paragraph paragraph) throws FileNotFoundException, IOException{
		
		XWPFParagraph p = doc.createParagraph();
		
		for (Object o : paragraph.getContent())
		{
			if(o instanceof Paragraph)
			{
				renderParagraph(doc,(Paragraph)o);
			}
			else if(o instanceof String)
			{
				XWPFRun r = p.createRun();
		        r.setText((String)o);
			}
		}
		
		return doc;
	}
}