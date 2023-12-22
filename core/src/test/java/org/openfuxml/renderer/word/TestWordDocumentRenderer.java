package org.openfuxml.renderer.word;

import java.io.File;

import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.old.TestDocumentProvider;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordDocumentRenderer extends AbstractTestWordRenderer
{
	public static void main(String[] args) throws Exception
	{
		OfxCoreBootstrap.init();
	
		OfxDefaultSettingsManager dsm = new OfxDefaultSettingsManager();
		NoOpCrossMediaManager cmm = new NoOpCrossMediaManager();
		ConfigurationProvider cp = ConfigurationProviderFacotry.build(cmm,dsm);
	
		Document document  = TestDocumentProvider.buildComplex();
		Document document1 = TestDocumentProvider.buildComplexWithTableSimple();
		Document document2 = TestDocumentProvider.buildComplexWithTable();
		Document document3 = TestDocumentProvider.buildWithSubcontent();
		Document document4 = TestDocumentProvider.buildComplexWith2Table();
		Document document5 = TestDocumentProvider.buildComplexWith3Table();
		Document document6 = TestDocumentProvider.buildComplexWith3TableWithParagraphInBetween();
		Document document7 = TestDocumentProvider.buildComplexWithImage();
		Document document8 = TestDocumentProvider.buildComplexWithList();
		Document document9 = TestDocumentProvider.buildComplexALL();
		Document document10 = TestDocumentProvider.buildComplexWithEmphasis();
		
		JaxbUtil.info(document);
		JaxbUtil.info(document1);
		JaxbUtil.info(document2);
		JaxbUtil.info(document3);
		JaxbUtil.info(document4);
		JaxbUtil.info(document5);
		JaxbUtil.info(document6);
		JaxbUtil.info(document7);
		JaxbUtil.info(document8);
		JaxbUtil.info(document9);
		JaxbUtil.info(document10);
		
		OfxWordRenderer owr = new OfxWordRenderer(cp);
		
		owr.render(document, new File("target/ofx_buildComplex.docx"));
		owr.render(document1, new File("target/ofx_buildComplexWithTableSimple.xml"));
		owr.render(document2, new File("target/ofx_buildComplexWithTable.pdf"));
		owr.render(document3, new File("target/ofx_buildWithSubcontent.epub"));
		owr.render(document4, new File("target/ofx_buildComplexWith2Table.docx"));
		owr.render(document5, new File("target/ofx_buildComplexWith3Table.xml"));
		owr.render(document6, new File("target/ofx_buildComplexWith3TableWithParagraphInBetween.docx"));
		owr.render(document7, new File("target/ofx_buildComplexWithImage.docx"));
		owr.render(document8, new File("target/ofx_buildComplexWithList.docx"));
		owr.render(document9, new File("target/ofx_buildComplexALL.docx"));
		owr.render(document10, new File("target/ofx_buildComplexWithEmphasis.html"));
	
	}
}