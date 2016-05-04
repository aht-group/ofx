package org.openfuxml.renderer.word.structure;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;

/**
 * 
 * @author yannkruger
 *
 */
public class WordSectionRenderer extends AbstractOfxWordRenderer{
	
	int lvl;
	
	/**
	 * Rekursiver Aufruf des Sectionrenderers, um verschiedene Ebenen (z.B. Untertitel) mit lvl anzuzeigen
	 * @param lvl
	 */
	public WordSectionRenderer(ConfigurationProvider cp, int lvl){
		super(cp);
		this.lvl = lvl;
	}
	
	public XWPFDocument renderer(XWPFDocument doc, Section section){
		
		for (Object o : section.getContent())
		{
			if(o instanceof Title)
			{
//				renderTitle((Title)o,lvl);
			}
		}
		
		for (Object o : section.getContent()){
			if(o instanceof Section){renderSection(doc,(Section)o);}
			else if(o instanceof Paragraph){renderParagraph(doc,(Paragraph)o);}
		}
		
		return doc;
	}
		
	public void renderSection(XWPFDocument doc, Section section){
		WordSectionRenderer wsr = new WordSectionRenderer(cp, lvl++);
		wsr.renderer(doc, section);
	}	
	

}
