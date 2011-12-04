package org.openfuxml.renderer.processor.latex.content.table.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.table.Column;
import org.openfuxml.content.ofx.table.Columns;

public class LatexTabluar
{
	static Log logger = LogFactory.getLog(LatexTabluar.class);
	
	private final static int muliplier = 1000000;
	
	private Columns columns;
	private List<String> listLatexCalculations;
	
	public LatexTabluar(Columns columns)
	{
		this.columns=columns;
		listLatexCalculations = new ArrayList<String>();
		createCalculations();
	}
	
	private void createCalculations()
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
		
		double relativeSum=0;
		for(int i=0;i<columns.getColumn().size();i++)
		{			
			Column col = columns.getColumn().get(i);
			relativeSum=relativeSum+col.getWidth().getValue();
		}
		relativeSum=relativeSum*muliplier;
		
		listLatexCalculations.add("");
		for(int i=0;i<columns.getColumn().size();i++)
		{	
			byte[] b = {(byte)(i+65)};
			Column col = columns.getColumn().get(i);
			
			int mul = (int)(col.getWidth().getValue()*muliplier);
			
			StringBuffer sb = new StringBuffer();
			sb.append("\\divide");
			sb.append("\\tabLen").append(new String(b));
			sb.append(" by ").append((int)relativeSum);

			listLatexCalculations.add(sb.toString());
			
			sb = new StringBuffer();
			sb.append("\\multiply");
			sb.append("\\tabLen").append(new String(b));
			sb.append(" by ").append(mul);
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
		logger.warn("NYI");
		System.exit(-1);
/*		Alignment alignment = Alignment.valueOf(sAlignment.toUpperCase());
		switch(alignment)
		{
			case LEFT: return "l";
			case RIGHT: return "r";
			case CENTER: return "c";
		}
*/		
		return "l";
	}
	
	public List<String> getLatexLengthCalculations(){return listLatexCalculations;}
}
