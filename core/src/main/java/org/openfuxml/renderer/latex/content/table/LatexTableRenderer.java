package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTableRenderer.class);
	
	public static enum Type {grid,line}
	
	boolean preBlankLine;
	public void setPreBlankLine(boolean preBlankLine) {this.preBlankLine = preBlankLine;}
	
	@Deprecated private LatexTableRenderer(){}
	
	public LatexTableRenderer(CrossMediaManager cmm)
	{
		super(cmm);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{		
		if(!table.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!table.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
		
		if(!table.getSpecification().isSetFloat() || !table.getSpecification().getFloat().isSetValue())
		{
			table.getSpecification().setFloat(XmlFloatFactory.build(true));
		}
		
		OfxLatexTableRenderer tableRenderer = getRendererForType();
		
		if(preBlankLine){preTxt.add("");}
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a Latex table with: "+tableRenderer.getClass().getSimpleName()));
		if(table.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer();
			rComment.render(table.getComment());
			renderer.add(rComment);
		}
		
		LatexTabluarWidthCalculator tabularWidthCalculator = new LatexTabluarWidthCalculator(table.getSpecification().getColumns());
		boolean floating = table.getSpecification().getFloat().isValue();
		boolean flex = tabularWidthCalculator.isFlexTable();
		
		renderPre(table,floating);
		tableRenderer.render(table);
			
		renderer.add(new StringRenderer(tabularWidthCalculator.getLatexLengthCalculations()));
		
		renderer.add(new StringRenderer(""));
		StringBuffer sb = new StringBuffer();
		
		if(flex){sb.append("\\begin{tabularx}");}
		else{sb.append("\\begin{tabular}");}
		
		if(flex){sb.append(tabularWidthCalculator.buildTableWidth(table.getSpecification()));}
		sb.append(tableRenderer.buildTabularCols(tabularWidthCalculator, table.getSpecification()));
		renderer.add(new StringRenderer(sb.toString()));
		
		renderer.add(tableRenderer);
		
		if(flex){renderer.add(new StringRenderer("\\end{tabularx}"));}
		else{renderer.add(new StringRenderer("\\end{tabular}"));}
		
		renderPost(table,floating);
	}
	
	private void renderPre(Table table,boolean floating)
	{
		preTxt.add("");
		
		if(floating){preTxt.add("\\begin{table}[!htbp]");}
//		else{preTxt.add("\\begin{center}");}
		else{preTxt.add("\\begin{table}[H]");}
		
		alignment(table.getSpecification());
	}
	
	private void renderPost(Table table,boolean floating)
	{
		if(table.isSetTitle()) 
		{
			if(floating){postTxt.add("\\caption{"+table.getTitle().getValue()+"}");}
			else{postTxt.add("\\captionof{table}{"+table.getTitle().getValue()+"}");}
		}
		
		if(table.isSetId()) {postTxt.add("\\label{"+table.getId()+"}");}
		
		if(floating){postTxt.add("\\end{table}");}
	//	else{postTxt.add("\\end{center}");}
		else{postTxt.add("\\end{table}");}
	}
	
	private OfxLatexTableRenderer getRendererForType()
	{
		OfxLatexTableRenderer tableRenderer;
		Type type = Type.line;
		
		switch(type)
		{
			case line: tableRenderer = new LatexLineTableRenderer(cmm);break;
			default: tableRenderer = new LatexGridTableRenderer(cmm);break;
		}
		return tableRenderer;
	}
	
	private void alignment(Specification specification)
	{
		getRendererForType();
	}
}
