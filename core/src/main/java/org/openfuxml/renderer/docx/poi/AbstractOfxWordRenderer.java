package org.openfuxml.renderer.docx.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxWordRenderer extends AbstractOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxWordRenderer.class);
	protected XWPFDocument doc;
		
	public AbstractOfxWordRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
}