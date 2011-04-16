package org.openfuxml.renderer.processor.latex.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class StringRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(StringRenderer.class);
	
	private List<String> result;
	
	public StringRenderer(String string)
	{
		result = new ArrayList<String>();
		result.add(string);
	}
	
	public StringRenderer(List<String> strings)
	{
		result = new ArrayList<String>();
		result.addAll(strings);
	}

	@Override
	public List<String> getContent()
	{
		return result;
	}
	
}
