package org.openfuxml.renderer.word.structure;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;

public class WordDocumentRenderer extends AbstractOfxWordRenderer{
	
	int lvl;
	
	/**
	 * Rekursiver Aufruf des Sectionrenderers, um verschiedene Ebenen (z.B. Untertitel) mit lvl anzuzeigen
	 * @param lvl
	 */
	public WordDocumentRenderer(ConfigurationProvider cp, int lvl){
		super(cp);
		this.lvl = lvl;
	}
	
	/**
	 * Erstmal nur für einen Titel, da CV nur einen Titel hat, sonst TitelRenderer Klasse erstellen
	 * @param doc
	 * @param section
	 * @return
	 */
	public XWPFDocument renderer(XWPFDocument doc, Document docOfx){
		
		for (Object o : docOfx.getContent().getContent())
		{
			if(o instanceof Section){renderSection(doc,(Section)o);}
		}
				
		return doc;
	}
	
	/**
	 * Hilfsmethode, die den renderer Aufrufen
	 * @param doc
	 * @param section
	 */
	public void renderSection(XWPFDocument doc, Section section){
		WordSectionRenderer wsr = new WordSectionRenderer(cp, lvl++);
		wsr.renderer(doc, section);
	}
}
