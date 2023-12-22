package org.openfuxml.renderer.util;

import java.util.Objects;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.ofx.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxDocumentStructureVerifier
{
	final static Logger logger = LoggerFactory.getLogger(OfxDocumentStructureVerifier.class);
	
	public static void checkForContent(Document ofxdoc) throws OfxAuthoringException
	{
		if(Objects.isNull(ofxdoc.getContent())) {throw new OfxAuthoringException("No content available");}
	}
}