package org.openfuxml.renderer.latex.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

import org.openfuxml.content.layout.Height;
import org.openfuxml.content.layout.Width;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.structure.LatexMarginaliaRenderer;
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
	
	public String buildWidth(Object parentRenderer, Width width) throws OfxAuthoringException
	{
		if(Objects.isNull(width.getValue())) {throw new OfxAuthoringException("No width-value given");}
		
		StringBuffer sb = new StringBuffer();
		if(Objects.isNull(width.getUnit())) {width.setUnit("cm");}
		
		if(width.getUnit().equals("cm"))
		{
			sb.append(width.getValue());
			sb.append(width.getUnit());
		}
		else if(width.getUnit().equals("percentage"))
		{
			sb.append(df.format(width.getValue()/100));
			if(parentRenderer instanceof LatexMarginaliaRenderer){sb.append("\\marginparwidth");}
			else{sb.append("\\linewidth");}
		}
		else
		{
			throw new OfxAuthoringException("No valid width");
		}
		
		return sb.toString();
	}
	public String buildHeight(Height height) throws OfxAuthoringException
	{
		if(Objects.isNull(height.getValue())) {throw new OfxAuthoringException("No height-value given");}
		
		StringBuffer sb = new StringBuffer();
		if(Objects.isNull(height.getUnit())) {height.setUnit("cm");}
		
		if(height.getUnit().equals("cm") || height.getUnit().equals("em"))
		{
			sb.append(height.getValue());
			sb.append(height.getUnit());
		}
		else
		{
			throw new OfxAuthoringException("No valid height");
		}
		
		return sb.toString();
	}
}
