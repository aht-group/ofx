package org.openfuxml.renderer;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.model.xml.core.ofx.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public abstract class OfxRenderer
{
	protected static Logger logger = LoggerFactory.getLogger(OfxRenderer.class);

	public void render(String ofxDocFileName) throws OfxAuthoringException, OfxConfigurationException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Document ofxdoc = JaxbUtil.loadJAXB(ofxDocFileName, Document.class);
			render(ofxdoc);
		}
		catch (FileNotFoundException e) {logger.error("",e);}
	}

	public abstract void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException;
}
