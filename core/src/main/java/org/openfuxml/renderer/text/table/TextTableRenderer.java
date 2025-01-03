package org.openfuxml.renderer.text.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.model.xml.core.ofx.Title;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.text.AbstractOfxTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextTableRenderer extends AbstractOfxTextRenderer implements OfxTextRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TextTableRenderer.class);
	
	protected List<OfxTextRenderer> rendererHeader;
	protected List<List<OfxTextRenderer>> rendererBody;

	private int[] columnSize;
	
	public TextTableRenderer(ConfigurationProvider cp)
	{
		super(cp);
		rendererHeader = new ArrayList<OfxTextRenderer>();
		rendererBody = new ArrayList<List<OfxTextRenderer>>();
	}
	
	public void render(Table table) throws OfxAuthoringException
	{
		prepareCells(table);
		
		if(Objects.nonNull(table.getTitle()))
		{
			int space = renderTitleDashes();
			renderTitle(space, table.getTitle());
		}		
		
		renderSeparatorDashes();
		renderHeader(rendererHeader);
		renderSeparatorDashes();
		for(List<OfxTextRenderer> r : rendererBody)
		{
			renderRow(r);
		}
		renderSeparatorDashes();
	}
	
	protected void prepareCells(Table table) throws OfxAuthoringException
	{
		if(Objects.nonNull(table.getContent()))
		{
			if(Objects.nonNull(table.getContent().getHead()))
			{
				for(Cell cell : table.getContent().getHead().getRow().get(0).getCell())
				{
					TextCellRenderer r = new TextCellRenderer(cp);
					r.render(cell);
					rendererHeader.add(r);
				}
			}
			if(ObjectUtils.isNotEmpty(table.getContent().getBody()))
			{
				for(Row row : table.getContent().getBody().get(0).getRow())
				{
					List<OfxTextRenderer> rowRenderer = new ArrayList<OfxTextRenderer>();
					for(Cell cell : row.getCell())
					{
						TextCellRenderer r = new TextCellRenderer(cp);
						r.render(cell);
						rowRenderer.add(r);
					}
					rendererBody.add(rowRenderer);
				}
			}
		}
		
		columnSize = new int[rendererHeader.size()];
		
		for(int i=0;i<rendererHeader.size();i++)
		{
			int length = rendererHeader.get(i).getSingleLine().length();
			if(length>columnSize[i]){columnSize[i] = length;}
		}
		for(List<OfxTextRenderer> rRow : rendererBody)
		{
			for(int i=0;i<rRow.size();i++)
			{
				int length = rRow.get(i).getSingleLine().length();
				if(length>columnSize[i]){columnSize[i] = length;}
			}		
		}
	}
	
	private void renderHeader(List<OfxTextRenderer> renderer) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<columnSize.length;i++)
		{
			sb.append("| ");
			sb.append(StringUtils.center(renderer.get(i).getSingleLine(),columnSize[i]));
			sb.append(" ");
		}
		sb.append("|");
		this.addString(sb.toString());
	}
	
	private void renderRow(List<OfxTextRenderer> renderer) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<columnSize.length;i++)
		{
			sb.append("| ");
			if(renderer.size()>i)
			{
				sb.append(StringUtils.rightPad(renderer.get(i).getSingleLine(),columnSize[i]));
			}
			else
			{
				sb.append(StringUtils.rightPad("",columnSize[i]));
			}
			sb.append(" ");
		}
		sb.append("|");
		this.addString(sb.toString());
	}
	
	private void renderTitle(int space, Title title)
	{
		String text = TxtTitleFactory.build(title);
		
		StringBuffer sb = new StringBuffer();
		sb.append("|");
		sb.append(StringUtils.center(text,space));
		sb.append("|");
		this.addString(sb.toString());
	}
	
	private int renderTitleDashes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("+");
		for(int i=0;i<columnSize.length;i++)
		{
			if(sb.length()>1) {sb.append("-");}
			sb.append(StringUtils.repeat("-",columnSize[i]+2));
		}
		sb.append("+");
		this.addString(sb.toString());
		return sb.length()-2;
	}
	
	private void renderSeparatorDashes()
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<columnSize.length;i++)
		{
			sb.append("+");
			sb.append(StringUtils.repeat("-",columnSize[i]+2));
		}
		sb.append("+");
		this.addString(sb.toString());
	}
}