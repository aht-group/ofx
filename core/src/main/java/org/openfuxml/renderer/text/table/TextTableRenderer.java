package org.openfuxml.renderer.text.table;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.renderer.text.AbstractOfxTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextTableRenderer extends AbstractOfxTextRenderer implements OfxTextRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TextTableRenderer.class);
	
	private List<OfxTextRenderer> rendererHeader;
	private List<List<OfxTextRenderer>> rendererBody;
	
	private int[] columnSize;
	
	public TextTableRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		rendererHeader = new ArrayList<OfxTextRenderer>();
		rendererBody = new ArrayList<List<OfxTextRenderer>>();
	}
	
	public void render(Table table) throws OfxAuthoringException
	{
		renderCells(table);
		renderSeparatorRow();
		renderHeader(rendererHeader);
		renderSeparatorRow();
		for(List<OfxTextRenderer> r : rendererBody)
		{
			renderRow(r);
		}
		renderSeparatorRow();
	}
	
	private void renderCells(Table table) throws OfxAuthoringException
	{
		if(table.isSetContent())
		{
			if(table.getContent().isSetHead())
			{
				for(Cell cell : table.getContent().getHead().getRow().get(0).getCell())
				{
					TextCellRenderer r = new TextCellRenderer(cmm,dsm);
					r.render(cell);
					rendererHeader.add(r);
				}
			}
			if(table.getContent().isSetBody())
			{
				for(Row row : table.getContent().getBody().get(0).getRow())
				{
					List<OfxTextRenderer> rowRenderer = new ArrayList<OfxTextRenderer>();
					for(Cell cell : row.getCell())
					{
						TextCellRenderer r = new TextCellRenderer(cmm,dsm);
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
	
	private void renderSeparatorRow()
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
			sb.append(StringUtils.rightPad(renderer.get(i).getSingleLine(),columnSize[i]));
			sb.append(" ");
		}
		sb.append("|");
		this.addString(sb.toString());
	}
	
}
