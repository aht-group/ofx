package org.openfuxml.renderer.word.structure;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
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
	 * @param cp x
	 * @param lvl x
	 */
	public WordSectionRenderer(ConfigurationProvider cp, int lvl)
	{
		super(cp);
		this.lvl = lvl;
	}
	
	/**
	 * Erstmal nur für einen Titel, da CV nur einen Titel hat, sonst TitelRenderer Klasse erstellen
	 * @param doc x 
	 * @param section x
	 * @return x
	 */
	public XWPFDocument renderer(XWPFDocument doc, Section section)
	{
		for (Object o : section.getContent())
		{
			if(o instanceof Title)
			{
				renderTitle(doc,(Title)o,lvl);
			}
		}
		
		for (Object o : section.getContent()){
			if(o instanceof Section){renderSection(doc,(Section)o);}
			else if(o instanceof Paragraph){renderParagraph(doc,(Paragraph)o);}
			else if(o instanceof Table){renderTable(doc,(Table)o);}
		}
		
		return doc;
	}
	
	/**
	 * Hilfsmethode, die den renderer Aufrufen
	 * @param doc x
	 * @param section x
	 */
	public void renderSection(XWPFDocument doc, Section section)
	{
		WordSectionRenderer wsr = new WordSectionRenderer(cp, lvl++);
		wsr.renderer(doc, section);
	}	
	
	/**
	 * Hilfsmethode, die den renderer Aufrufen
	 * @param doc x
	 * @param title x
	 */
	public void renderTitle(XWPFDocument doc, Title title, int lvl)
	{
		WordTitleRenderer wtr = new WordTitleRenderer(cp);
		wtr.renderer(doc, title, lvl);
	}
	
	public void renderTable(XWPFDocument doc, Table table)
	{
		WordTableRenderer wtr = new WordTableRenderer(cp);
		wtr.renderer(doc,table);
	}
}