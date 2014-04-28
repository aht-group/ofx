package org.openfuxml.renderer.latex.content.table;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.layout.Width;
import org.openfuxml.content.table.Column;
import org.openfuxml.content.table.Columns;
import org.openfuxml.content.table.Specification;

public class LatexTabluarWidthCalculator
{
	static Log logger = LogFactory.getLog(LatexTabluarWidthCalculator.class);
	
	protected final static int muliplier = 1000;
	
	private DecimalFormat df;
	private Width widthTable;
	
	private Columns columns;
	private List<String> listDefinition;
	private List<String> listValue;
	
	private Map<Integer,String> mapCol;
	
	public LatexTabluarWidthCalculator(Columns columns)
	{
		this.columns=columns;
		
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		df = new DecimalFormat("0.00",otherSymbols);
		
		listDefinition = new ArrayList<String>();
		listValue = new ArrayList<String>();
		mapCol = new Hashtable<Integer,String>();
		
		setDefaultTableWidth();
		calculate();
	}
	
	private void calculate()
	{
		for(int i=1;i<=columns.getColumn().size();i++)
		{	
			Width width = columns.getColumn().get(i-1).getWidth();
			if(!width.isSetUnit()){width.setUnit("percentage");}
			
			byte[] b = {(byte)(i+64)};
			String var = "\\tabLen"+(new String(b));
			
			boolean flex = width.isSetFlex() && width.isFlex();
			
			if(flex)
			{
				mapCol.put(i, "X");
			}
			else
			{
				StringBuffer sbDef = new StringBuffer();
				sbDef.append("\\ifthenelse");
				sbDef.append("{\\isundefined{").append(var).append("}}");
				sbDef.append("{\\newlength{").append(var);
				sbDef.append("\\tabLen").append(new String(b)).append("}}");
				sbDef.append("{}");
				listDefinition.add(sbDef.toString());
				
				if(width.getUnit().equals("percentage"))
				{
					double colWidth = (widthTable.getValue()/100)*(width.getValue()/100);
					
					StringBuffer sbValue = new StringBuffer();
					sbValue.append("\\setlength{").append(var).append("}");
					sbValue.append("{").append(df.format(colWidth)).append("\\textwidth}");
					listValue.add(sbValue.toString());
					mapCol.put(i, "p{"+var+"}");
				}
				else
				{
					logger.warn("NYI unit "+width.getUnit());
				}
			}
			logger.info(i+" "+getColDefinition(i));
		}
	}
	
	public String getColDefinition(int i)
	{
		return mapCol.get(i);
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
	
	public List<String> getLatexLengthCalculations()
	{
		List<String> listLatexCalculation = new ArrayList<String>();
		listLatexCalculation.add("");
		listLatexCalculation.addAll(listDefinition);
		listLatexCalculation.add("");
		listLatexCalculation.addAll(listValue);
		return listLatexCalculation;
	}
	
	public String buildTableWidth(Specification specification)
	{
		if(specification.isSetWidth()){widthTable = specification.getWidth();}
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\\textwidth");
		sb.append("}");
		return sb.toString();
	}
	
	public String precentage(double d)
	{
		return df.format(d/100);
	}
	
	private void setDefaultTableWidth()
	{
		widthTable = new Width();
		widthTable.setUnit("percentage");
		widthTable.setValue(100);
	}
}
