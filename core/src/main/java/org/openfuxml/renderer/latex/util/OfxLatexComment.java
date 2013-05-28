package org.openfuxml.renderer.latex.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLatexComment
{
	final static Logger logger = LoggerFactory.getLogger(OfxLatexComment.class);
	
	public static List<String> comment(String comment, boolean postLine)
	{
		List<String> result = new ArrayList<String>();
		result.add(line(comment));
		if(postLine){result.add("");}
		return result;
	}
	
	private static String line(String s)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("% ");
		sb.append(s);
		return sb.toString();
	}
}