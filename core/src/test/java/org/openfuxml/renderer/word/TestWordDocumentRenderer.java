package org.openfuxml.renderer.word;

import java.io.File;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.DocumentProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordDocumentRenderer extends AbstractTestWordRenderer
{
	public static void main(String[] args) throws Exception
	{
		OfxCoreTestBootstrap.init();
	
		OfxDefaultSettingsManager dsm = new OfxDefaultSettingsManager();
		NoOpCrossMediaManager cmm = new NoOpCrossMediaManager();
		ConfigurationProvider cp = ConfigurationProviderFacotry.build(cmm,dsm);
	
		Document document = DocumentProvider.buildComplex(); 
		JaxbUtil.info(document);
	
		OfxWordRenderer owr = new OfxWordRenderer(cp);	
		owr.render(document, new File("target/ofx.docx"));
	}
}