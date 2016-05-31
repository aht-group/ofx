package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.word.structure.WordSectionRenderer;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordSectionRenderer extends AbstractTestWordRenderer {
	
public static void main(String[] args) throws Exception{
	int lvl = 0;
	
	TestWordSectionRenderer twsr = new TestWordSectionRenderer();
	
	OfxDefaultSettingsManager dsm = new OfxDefaultSettingsManager();
	NoOpCrossMediaManager cmm = new NoOpCrossMediaManager();
	ConfigurationProvider cp = ConfigurationProviderFacotry.build(cmm, dsm);
	
	WordSectionRenderer wsr = new WordSectionRenderer(cp, lvl);	
	
	Section s = twsr.buildSection(); 
	
	//XML Ausgabe
	JaxbUtil.info(s);
	
	wsr.renderSection(wsr.getDocument(), s, lvl);
	wsr.writeDocument(wsr.getDocument());
	
}

}
