package org.openfuxml.renderer.processor.latex.content.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Entry;
import org.openfuxml.content.ofx.table.Group;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class GridTableFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(GridTableFactory.class);
	
	public GridTableFactory()
	{

	}
	
	public void render(Table table)
	{	
		renderPre();
		renderTabular(table.getGroup());
		renderPost(table.getTitle());
	}
	
	private void renderPre()
	{
		preTxt.add("");
		preTxt.add("\\begin{table}[htb]");
		preTxt.add("\\centering");
	}
	
	private void renderPost(Title title)
	{
		if(title!=null)
		{
			postTxt.add("\\caption{"+title.getValue()+"}");
			postTxt.add("\\label{tab:xx}");
		}
		postTxt.add("\\end{table}");
	}
	
	private void renderTabular(Group tgroup)
	{
		renderTableSpec(tgroup.getHead());
		renderTableHeader(tgroup.getHead());
		renderBody(tgroup.getBody());
		
		txt.add("\\hline");
		txt.add("\\end{tabular}");
	}
	
	private void renderTableSpec(Head thead)
	{
		txt.add("\\begin{tabular}{|l|l|}");
	}
	
	private void renderTableHeader(Head thead)
	{
		txt.add("\\hline Key &  Value \\\\");
		txt.add("\\hline \\hline");
	}
	
	private void renderBody(Body tbody)
	{
		for(Row row : tbody.getRow())
		{
			StringBuffer sb = new StringBuffer();
			for(Entry entry : row.getEntry())
			{
				sb.append(entry.getValue());
				sb.append(" & ");
			}
			sb.delete(sb.length()-2, sb.length());
			sb.append("\\\\");
			
			txt.add(sb.toString());
		}
	}
}
