package org.openfuxml.renderer.wiki;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.model.xml.core.ofx.Reference;

/**
 * Rendering references(hyperlink) to wiki syntax
 * @author yannkruger
 *
 */
public class WikiReferenceRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer{

	//TODO REMOVE
	public WikiReferenceRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	//TODO FIX
//	public WikiReferenceRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm) {
//		super(cmm, dsm);
//	}
	
	public void render(Reference reference) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[[");
		sb.append(reference.getTarget());
		sb.append("]]");
		
		txt.add(sb.toString());
	}
}
