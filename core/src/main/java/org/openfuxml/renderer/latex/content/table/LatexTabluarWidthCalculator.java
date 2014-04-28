package org.openfuxml.renderer.latex.content.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.table.Column;
import org.openfuxml.content.table.Columns;

public class LatexTabluarWidthCalculator
{
	static Log logger = LogFactory.getLog(LatexTabluarWidthCalculator.class);
	
	protected final static int muliplier = 1000;
	
	private Columns columns;
	private List<String> listLatexCalculations;
	
	public LatexTabluarWidthCalculator(Columns columns)
	{
		this.columns=columns;
		listLatexCalculations = new ArrayList<String>();
		calculate();
	}
	
	private void calculate()
	{
		listLatexCalculations.add("");
		for(int i=0;i<columns.getColumn().size();i++)
		{			
			byte[] b = {(byte)(i+65)};
			
			StringBuffer sb = new StringBuffer();
			sb.append("\\ifthenelse{\\isundefined{");
			sb.append("\\tabLen").append(new String(b));
			sb.append("}}{\\newlength{");
			sb.append("\\tabLen").append(new String(b));
			sb.append("}}{}");
			
			listLatexCalculations.add(sb.toString());
		}
		
		listLatexCalculations.add("");
		for(int i=0;i<columns.getColumn().size();i++)
		{			
			byte[] b = {(byte)(i+65)};
			
			StringBuffer sb = new StringBuffer();
			sb.append("\\setlength{");
			sb.append("\\tabLen").append(new String(b));
			sb.append("}{\\textwidth}");
			
			listLatexCalculations.add(sb.toString());
		}
		
		int relativeSum = getDivide();
		
		listLatexCalculations.add("");
		for(int i=0;i<columns.getColumn().size();i++)
		{	
			byte[] b = {(byte)(i+65)};
			
			
			StringBuffer sb = new StringBuffer();
			sb.append("\\divide");
			sb.append("\\tabLen").append(new String(b));
			sb.append(" by ").append(relativeSum);

			listLatexCalculations.add(sb.toString());
			
			sb = new StringBuffer();
			sb.append("\\multiply");
			sb.append("\\tabLen").append(new String(b));
			sb.append(" by ").append(getMultiplier(i));
			listLatexCalculations.add(sb.toString());
		}
	}
	
	public String getColDefinition(int i)
	{
		byte[] b = {(byte)(i+65)};
		
		StringBuffer sb = new StringBuffer();
		sb.append("p{");
		sb.append("\\tabLen").append(new String(b));
		sb.append("}");
		
		return sb.toString();
	}
	
	public static String getTableAlignment(String sAlignment)
	{
		logger.warn("NYI OFX-26");	
		return "l";
	}
	
	protected int getDivide()
	{
		double relativeSum=0;
		for(int i=0;i<columns.getColumn().size();i++)
		{			
			Column col = columns.getColumn().get(i);
			relativeSum=relativeSum+col.getWidth().getValue();
		}
		return (int)relativeSum*muliplier;
	}
	
	protected int getMultiplier(int i)
	{
		return (int)(columns.getColumn().get(i).getWidth().getValue()*muliplier);
	}
	
	public List<String> getLatexLengthCalculations(){return listLatexCalculations;}
}
