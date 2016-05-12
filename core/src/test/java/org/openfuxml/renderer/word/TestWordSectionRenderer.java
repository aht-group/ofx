package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.word.structure.WordSectionRenderer;

import com.sun.media.jfxmedia.logging.Logger;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordSectionRenderer extends AbstractTestWordRenderer {
	
public static void main(String[] args) throws Exception{
	int lvl = 0;
	
	TestWordSectionRenderer twsr = new TestWordSectionRenderer();
	WordSectionRenderer wsr = new WordSectionRenderer(new ConfigurationProvider() {
		
		@Override
		public DefaultSettingsManager getDefaultSettingsManager() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public CrossMediaManager getCrossMediaManager() {
			// TODO Auto-generated method stub
			return null;
		}
	}, lvl);	
	
	Section s = twsr.buildSection(); 
	
	//XML Ausgabe
	JaxbUtil.info(s);
	
	wsr.renderSection(wsr.getDocument(), s, lvl);
	wsr.writeDocument(wsr.getDocument());
	
}

}
