package org.openfuxml.renderer.latex.content.text;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(StringRenderer.class);
	
	private List<String> result;
	
	public StringRenderer()
	{
		result = new ArrayList<String>();
	}
	
	public StringRenderer(String string)
	{
		result = new ArrayList<String>();
		result.add(string);
	}
	
	public StringRenderer(String... string)
	{
		result = new ArrayList<String>();
		for(String s : string)
		{
			result.add(s);
		}
		
	}
	
	public StringRenderer(List<String> strings)
	{
		result = new ArrayList<String>();
		result.addAll(strings);
	}
	
	public void line(String text)
	{
		result.add(text);
	}

	@Override public List<String> getContent()
	{
		return result;
	}
	
	@Override public void write(Writer w) throws IOException
	{
		for(String s : getContent())
		{
			w.write(s+System.lineSeparator());
		}
		w.flush();
	}
	
}
