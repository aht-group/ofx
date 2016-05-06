package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.word.structure.WordParagraphRenderer;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordParagraphRenderer extends AbstractTestWordRenderer {
		
	public static void main(String[] args) throws Exception{
		
		TestWordParagraphRenderer twpr = new TestWordParagraphRenderer();
		WordParagraphRenderer wpr = new WordParagraphRenderer(new ConfigurationProvider() {
			
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
		
		Paragraph p = twpr.buildParagraph();
		
		JaxbUtil.info(p);
		
		wpr.renderParagraph(wpr.getDocument(), p);
		wpr.writeDocument(wpr.getDocument());
	}
}
