package org.openfuxml.renderer.latex.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.renderer.latex.content.structure.LatexMarginaliaRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexWidthCalculator extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexWidthCalculator.class);

	private LatexWidthCalculator lwc;
	private Width width;
	private LatexSectionRenderer sectionRenderer;
	private LatexMarginaliaRenderer marginaliaRenderer;

	@BeforeEach
	public void init()
	{
		lwc = new LatexWidthCalculator();
		width = new Width();
		
		sectionRenderer = new LatexSectionRenderer(cp,1,new LatexPreamble(cp));
		marginaliaRenderer = new LatexMarginaliaRenderer(cp);
	}
	
	@Test
	public void cm() throws OfxAuthoringException
	{
		width.setUnit("cm");
		width.setValue(12);
		
		String expected = "12.0cm";
        String actual = lwc.buildWidth(sectionRenderer,width);
        Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void percentage() throws OfxAuthoringException
	{
		width.setUnit("percentage");
		width.setValue(12);
		
        Assertions.assertEquals("0.12\\linewidth", lwc.buildWidth(sectionRenderer,width));
        Assertions.assertEquals("0.12\\marginparwidth", lwc.buildWidth(marginaliaRenderer,width));
	}
}