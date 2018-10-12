package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.layout.Font;
import org.openfuxml.content.layout.Line;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Column;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.layout.XmlLineFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexTableRenderer;
import org.openfuxml.interfaces.renderer.util.TabluarWidthCalculator;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexTitleRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTabuRenderer extends AbstractOfxLatexRenderer implements OfxLatexTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTabuRenderer.class);
	
	public LatexTabuRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		boolean longTable = table.getSpecification().isLong();
		
		String tableType = null;
		if(longTable){tableType="longtabu";}
		else{tableType="tabu";}
		
		preTxt.add("\\setlength{\\extrarowheight}{1.5mm}");//konstante Reihenabstände in einer Tabelle
		preTxt.add("\\linespread{0.75}\\selectfont");        				//Zeilenabstand (Text allgemein)
		
		StringBuffer preSb = new StringBuffer();
		preSb.append("\\begin{").append(tableType).append("} to \\linewidth ");
		preSb.append(renderPreamble(table.getSpecification()));
		
		preTxt.add(preSb.toString());
		
		renderTabu(table.getSpecification(),table.getContent());
		
		StringBuffer postSb = new StringBuffer();
		postSb.append("\\end{").append(tableType).append("}");
		postSb.append("\\linespread{1}\\selectfont");
		if(longTable)
		{
			buildTitle(table);
			postTxt.add(postSb.toString());
		}
		else
		{
			postTxt.add(postSb.toString());
			buildTitle(table);
		}
	}
	
	private StringBuffer renderPreamble(Specification specification) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		
		for(Column c : specification.getColumns().getColumn())
		{
			if(c.isSetWidth() && c.getWidth().isSetFlex() && c.getWidth().isFlex())
			{
				int relative = (new Double(c.getWidth().getValue()*100)).intValue();
				
				sb.append("X[");
				if(c.getWidth().isSetNarrow() && c.getWidth().isNarrow()){sb.append("-");}
				sb.append(relative);
				sb.append("]");
			}
			else if(c.isSetAlignment())
			{
				if(c.getAlignment().getHorizontal().equals("left")){sb.append("l");}
				if(c.getAlignment().getHorizontal().equals("center")){sb.append("c");}
			}
			sb.append("<{\\strut}");
		}
		
		sb.append("}");		
		return sb;
	}
	
	private void renderTabu(Specification specification, Content tgroup) throws OfxAuthoringException
	{		
		renderTableHeader(tgroup.getHead());
		renderBody(tgroup.getBody().get(0));
	}
	
	private void renderTableHeader(Head head) throws OfxAuthoringException
	{	
		Emphasis emphasis = new Emphasis();
		emphasis.setBold(true);
		
		renderer.add(new StringRenderer("\\toprule"));
		if(head!=null)
		{
			for(Row row : head.getRow())
			{
				LatexRowRenderer f = new LatexRowRenderer(cp);
				f.setEmphasisOverride(emphasis);
				f.render(row);
				renderer.add(f);
			}
		}
		renderer.add(new StringRenderer("\\toprule"));
	}
	
	private void renderBody(Body tbody) throws OfxAuthoringException
	{
		Font font = null;
		if(tbody.isSetLayout())
		{
			if(tbody.getLayout().isSetFont()){font=tbody.getLayout().getFont();}
		}
		
		for(Row row : tbody.getRow())
		{
			horizontalLines(row,XmlLineFactory.Orientation.top);
			
			LatexRowRenderer f = new LatexRowRenderer(cp);
			f.setFont(font);
			f.render(row);
			renderer.add(f);
			
			horizontalLines(row,XmlLineFactory.Orientation.bottom);
		}
		renderer.add(new StringRenderer("\\bottomrule"));
	}
	
	@Override
	public String buildTabularCols(TabluarWidthCalculator tabularWidthCalculator, Specification spec)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(int i=1;i<=spec.getColumns().getColumn().size();i++)
		{
			sb.append(tabularWidthCalculator.getColDefinition(i));
			sb.append("");
		}
		sb.append("}");
		return sb.toString();
	}
	
	private void buildTitle(Table table)
	{
		if(table.isSetTitle())
		{
			LatexTitleRenderer stf = new LatexTitleRenderer(cp);
			stf.render(table);
			postTxt.addAll(stf.getContent());
		}
	}
	
	private void horizontalLines(Row row, XmlLineFactory.Orientation orientation) throws OfxAuthoringException
	{
		if(row.isSetLayout())
		{
			for(Line line : row.getLayout().getLine())
			{
				if(!line.isSetOrientation()){throw new OfxAuthoringException("Inside a "+Table.class.getSimpleName()+", the "+Row.class.getSimpleName()+" with a "+Line.class.getSimpleName()+" needs a orientation");}
				if(line.getOrientation().equals(orientation.toString()))
				{
					renderer.add(new StringRenderer("\\midrule"));
				}
			}
		}
	}
}