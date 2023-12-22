package org.openfuxml.renderer.latex.content.table;

import java.util.Objects;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.interfaces.renderer.latex.OfxLatexTableRenderer;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class LatexTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTableRenderer.class);
	
	public static enum Type {grid,line}
	
	boolean preBlankLine;
	public void setPreBlankLine(boolean preBlankLine) {this.preBlankLine = preBlankLine;}
	
	public LatexTableRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{		
		if(Objects.isNull(table.getSpecification())) {throw new OfxAuthoringException("<table> without <specification>");}
		if(Objects.isNull(table.getContent())) {throw new OfxAuthoringException("<table> without <content>");}
		if(table.getContent().getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		
		if(Objects.isNull(table.getSpecification().isLong())) {table.getSpecification().setLong(false);}
		if(Objects.isNull(table.getSpecification().getFloat())) {table.getSpecification().setFloat(XmlFloatFactory.build(false));}
		
		OfxLatexTableRenderer tableRenderer = new LatexTabuRenderer(cp); //getRendererForType();
		
		if(preBlankLine){preTxt.add("");}
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a Latex table with: "+tableRenderer.getClass().getSimpleName()));
		if(Objects.nonNull(table.getComment()))
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cp);
			rComment.render(table.getComment());
			preTxt.addAll(rComment.getContent());
//			renderer.add(rComment);
		}
		
		boolean longTable = table.getSpecification().isLong();
		boolean floating = table.getSpecification().getFloat().isValue();
		
		if(!longTable){renderPre(table,floating);}

		tableRenderer.render(table);
		renderer.add(tableRenderer);
		
		if(!longTable){renderPost(table,floating);}
	}
	
	private void renderPre(Table table,boolean floating)
	{
		preTxt.add("");
		
		if(floating){preTxt.add("\\begin{table}[!htbp]");}
		else{preTxt.add("\\begin{table}[H]");}
	}
	
	private void renderPost(Table table,boolean floating)
	{
		JaxbUtil.trace(table);
		
		
		if(Objects.nonNull(table.getId())) {postTxt.add("\\label{"+table.getId()+"}");}
		
		postTxt.add("\\end{table}");
	}
	
	@SuppressWarnings("unused")
	private OfxLatexTableRenderer getRendererForType()
	{
		OfxLatexTableRenderer tableRenderer;
		Type type = Type.line;
		
		switch(type)
		{
			case line: tableRenderer = new LatexLineTableRenderer(cp);break;
			default: tableRenderer = new LatexGridTableRenderer(cp);break;
		}
		return tableRenderer;
	}
}
