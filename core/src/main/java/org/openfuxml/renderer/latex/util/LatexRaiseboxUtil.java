package org.openfuxml.renderer.latex.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import org.openfuxml.content.layout.Height;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexRaiseboxUtil
{
	final static Logger logger = LoggerFactory.getLogger(LatexRaiseboxUtil.class);
	
	private static DecimalFormat df;
	
	private static DecimalFormat getDf()
	{
		if(df==null)
		{
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			df = new DecimalFormat("0.00",otherSymbols);
		}
		return df;
	}
	
	public static void minipageBegin(List<String> txt,Height height)
	{
		if(height!=null)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("\\raisebox{");
			sb.append(getDf().format(height.getValue()));
			sb.append("\\height}{");
			
			txt.add(sb.toString());
		}
	}
	
	public static void minipageEnd(List<String> txt,Height height)
	{
		if(height!=null)
		{
			txt.add("}");
		}
	}
}