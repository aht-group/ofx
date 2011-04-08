package org.openfuxml.renderer.processor.latex.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class StringRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(StringRenderer.class);
	
	private String s;
	
	public StringRenderer(String s)
	{
		this.s=s;
	}

	@Override
	public List<String> getContent()
	{
		List<String> result = new ArrayList<String>();
		result.add(s);
		return result;
	}
	
}
