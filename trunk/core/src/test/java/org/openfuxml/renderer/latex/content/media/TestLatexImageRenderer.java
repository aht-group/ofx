package org.openfuxml.renderer.latex.content.media;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.media.XmlMediaFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexImageRenderer extends AbstractLatexMediaTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexImageRenderer.class);
	
	private LatexCrossMediaManager cmm;
    private LatexSectionRenderer rSection;
    private LatexImageRenderer rImage;
    
    private Image image;
	
	@Before
	public void initRenderer()
	{
		File fTarget = new File("target");
		cmm = new LatexCrossMediaManager(new File(fTarget,"base"));
		
        rSection = new LatexSectionRenderer(cmm,1, new LatexPreamble());
        rImage = new LatexImageRenderer(new NoOpCrossMediaManager());
        
        image = new Image();
        image.setId("my.id");
        image.setAlignment(XmlAlignmentFactory.buildHorizontal(XmlAlignmentFactory.Horizontal.center));
        image.setTitle(XmlTitleFactory.build("My Test Title"));
        image.setMedia(XmlMediaFactory.build("mSrc.pdf", "mDst.pdf"));   
        
        JaxbUtil.info(image);
	}

    @Test
    public void direct() throws IOException, OfxAuthoringException
    {
    	rImage.render(this,image);
        List<String> content = rImage.getContent();
        OfxContentDebugger.debug(content);
    }

    @Test @Ignore
    public void section() throws IOException, OfxAuthoringException
    {
        Section section = new Section();
        section.getContent().add(XmlTitleFactory.build("test"));
        section.getContent().add(image);

        rSection.render(section);
        List<String> content = rSection.getContent();
        OfxContentDebugger.debug(content);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexImageRenderer.initLoremIpsum();
    	TestLatexImageRenderer test = new TestLatexImageRenderer();
    	test.setSaveReference(true);
    	
    	test.initRenderer();
    	test.direct();
//		test.section();
    }
}