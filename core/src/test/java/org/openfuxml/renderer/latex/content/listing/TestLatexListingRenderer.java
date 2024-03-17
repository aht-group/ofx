package org.openfuxml.renderer.latex.content.listing;

import java.io.IOException;
import java.util.List;

import org.exlp.util.jx.JaxbUtil;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.ofx.Listing;
import org.openfuxml.model.xml.core.ofx.Raw;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexListingRenderer extends AbstractLatexListingTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexListingRenderer.class);
	
	private LatexListingRenderer rListing;
    private LatexSectionRenderer rSection;

    private Listing listing;
	
	@Before
	public void initRenderer()
	{
        rSection = new LatexSectionRenderer(cp,1, new LatexPreamble(cp));
        rListing = new LatexListingRenderer(cp);

        listing = new Listing();

        Raw raw = new Raw();
        raw.setValue(li.getWords(10));
        listing.setRaw(raw);

        JaxbUtil.info(listing);
	}

    @Test
    public void direct() throws IOException, OfxAuthoringException, OfxConfigurationException
    {
        rListing.render(listing);
        List<String> content = rListing.getContent();
        OfxContentDebugger.debug(content);
    }

    @Test
    public void section() throws IOException, OfxAuthoringException, OfxConfigurationException
    {
        Section section = new Section();
        section.getContent().add(XmlTitleFactory.build("test"));
        section.getContent().add(listing);

        rSection.render(section);
        List<String> content = rSection.getContent();
        OfxContentDebugger.debug(content);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
			
    	TestLatexListingRenderer.initLoremIpsum();
    	TestLatexListingRenderer test = new TestLatexListingRenderer();
    	test.setEnvironment(true);
    	
    	test.initRenderer();
    	test.direct();
        test.section();
    }
}