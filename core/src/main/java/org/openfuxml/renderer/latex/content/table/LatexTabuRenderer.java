package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.layout.Line;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTabuRenderer extends AbstractOfxLatexRenderer implements OfxLatexTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTabuRenderer.class);
	
	public LatexTabuRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{			
		boolean longTable = table.getSpecification().isLong();
		
		String tableType = null;
		if(longTable){tableType="longtabu";}
		else{tableType="tabu";}
		
		StringBuffer preSb = new StringBuffer();
		preSb.append("\\begin{").append(tableType).append("} to \\linewidth");
		preSb.append("{");
		
		for(int i=0;i<table.getSpecification().getColumns().getColumn().size();i++)
		{
			preSb.append("X");
		}
		
		preSb.append("}");
		
		preTxt.add(preSb.toString());
		
		renderTabu(table.getSpecification(),table.getContent());
		
		StringBuffer postSb = new StringBuffer();
		postSb.append("\\end{").append(tableType).append("}");
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
				LatexRowRenderer f = new LatexRowRenderer(cmm,dsm);
				f.setEmphasisOverride(emphasis);
				f.render(row);
				renderer.add(f);
			}
		}
		renderer.add(new StringRenderer("\\toprule"));
	}
	
	@SuppressWarnings("unused")
	private void renderBody(Body tbody) throws OfxAuthoringException
	{
		for(Row row : tbody.getRow())
		{
			if(row.isSetLayout())
			{
				for(Line line : row.getLayout().getLine())
				{
					renderer.add(new StringRenderer("\\midrule"));
				}
			}
			
			LatexRowRenderer f = new LatexRowRenderer(cmm,dsm);
			f.render(row);
			renderer.add(f);
		}
		renderer.add(new StringRenderer("\\bottomrule"));
	}
	
	@Override
	public String buildTabularCols(LatexTabluarWidthCalculator tabularWidthCalculator, Specification spec)
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
			postTxt.add("\\caption{"+table.getTitle().getValue()+"}");
		}
	}
}