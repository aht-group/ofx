package org.openfuxml.renderer.latex.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.openfuxml.content.layout.Width;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexWidthCalculator
{
	final static Logger logger = LoggerFactory.getLogger(LatexWidthCalculator.class);
	
	private DecimalFormat df;
	
	public LatexWidthCalculator()
	{
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		df = new DecimalFormat("0.00",otherSymbols);
	}
	
	public String buildWidth(Width width) throws OfxAuthoringException
	{
		if(!width.isSetValue()){throw new OfxAuthoringException("No width-value given");}
		
		StringBuffer sb = new StringBuffer();
		if(!width.isSetUnit()){width.setUnit("cm");}
		
		if(width.getUnit().equals("cm"))
		{
			sb.append(width.getValue());
			sb.append(width.getUnit());
		}
		else if(width.getUnit().equals("percentage"))
		{
			sb.append(df.format(width.getValue()/100));
			sb.append("\\linewidth");
		}
		else
		{
			throw new OfxAuthoringException("No valid width");
		}
		
		return sb.toString();
	}
}
