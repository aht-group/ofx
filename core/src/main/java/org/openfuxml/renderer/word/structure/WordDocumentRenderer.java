package org.openfuxml.renderer.word.structure;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;

public class WordDocumentRenderer extends AbstractOfxWordRenderer
{	
	int lvl;
	
	/**
	 * Rekursiver Aufruf des Sectionrenderers, um verschiedene Ebenen (z.B. Untertitel) mit lvl anzuzeigen
	 * @param cp x
	 * @param lvl x
	 */
	public WordDocumentRenderer(ConfigurationProvider cp, int lvl)
	{
		super(cp);
		this.lvl = lvl;
	}
	
	/**
	 * Rekursiver Aufruf des Sectionrenderers, um verschiedene Ebenen (z.B. Untertitel) mit lvl anzuzeigen
	 * @param cp x
	 */
	public WordDocumentRenderer(ConfigurationProvider cp)
	{
		super(cp);
		this.lvl = 0;
	}
	
	/**
	 * Erstmal nur für einen Titel, da CV nur einen Titel hat, sonst TitelRenderer Klasse erstellen
	 * @param doc x
	 * @param docOfx x
	 * @return x
	 */
	public XWPFDocument renderer(XWPFDocument doc, Document docOfx)
	{	
		for (Object o : docOfx.getContent().getContent())
		{
			if(o instanceof Section){renderSection(doc,(Section)o);}
		}
				
		return doc;
	}
	
	/**
	 * Hilfsmethode, die den renderer Aufrufen
	 * @param doc x
	 * @param section x
	 */
	public void renderSection(XWPFDocument doc, Section section){
		WordSectionRenderer wsr = new WordSectionRenderer(cp, lvl++);
		wsr.renderer(doc, section);
	}
}
