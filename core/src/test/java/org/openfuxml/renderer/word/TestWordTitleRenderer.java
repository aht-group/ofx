package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.word.structure.WordTitleRenderer;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordTitleRenderer extends AbstractTestWordRenderer {
	
public static void main(String[] args) throws Exception{
	
	TestWordTitleRenderer twtr = new TestWordTitleRenderer();
	WordTitleRenderer wtr = new WordTitleRenderer(new ConfigurationProvider() {
		
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
	});	
	
	Title t = twtr.buildTitle(); 
		
	JaxbUtil.info(t);
	
	wtr.renderTitle(wtr.getDocument(), t, 1);
	wtr.writeDocument(wtr.getDocument());
}

}
