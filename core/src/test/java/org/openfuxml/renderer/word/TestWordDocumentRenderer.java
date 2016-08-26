package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.word.structure.WordDocumentRenderer;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordDocumentRenderer extends AbstractTestWordRenderer{
	
	public static void main(String[] args) throws Exception{
	int lvl = 0;
	
	TestWordDocumentRenderer twdr = new TestWordDocumentRenderer();
	
	OfxDefaultSettingsManager dsm = new OfxDefaultSettingsManager();
	NoOpCrossMediaManager cmm = new NoOpCrossMediaManager();
	ConfigurationProvider cp = ConfigurationProviderFacotry.build(cmm, dsm);
	
	WordDocumentRenderer wdr = new WordDocumentRenderer(cp, lvl);	
	
	Document d = twdr.buildDocument(); 
	
//	XML Ausgabe
//	JaxbUtil.info(d);
	
	wdr.renderer(wdr.getDocument(), d);
	wdr.writeDocument(wdr.getDocument());
	
	}
}
