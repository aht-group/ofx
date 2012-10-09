package org.openfuxml.renderer.processor.latex.content.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.StringRenderer;
import org.openfuxml.renderer.processor.latex.content.table.util.LatexTabluar;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class GridTableFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(GridTableFactory.class);
	
	public GridTableFactory()
	{

	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		renderPre();
		renderTabular(table.getSpecification(),table.getContent());
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
	
	private void renderTabular(Specification specification, Content tgroup) throws OfxAuthoringException
	{
		renderSpecification(specification);
		renderTableHeader(tgroup.getHead());
		
		logger.warn("Only one body");
		renderBody(tgroup.getBody().get(0));
		
		renderer.add(new StringRenderer("\\hline"));
		renderer.add(new StringRenderer("\\end{tabular}"));
	}
	
	private void renderSpecification(Specification spec)
	{
		LatexTabluar latexTabular = new LatexTabluar(spec.getColumns());
		
		renderer.add(new StringRenderer(latexTabular.getLatexLengthCalculations()));
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\begin{tabular}");
		sb.append("{");
		for(int i=0;i<spec.getColumns().getColumn().size();i++)
		{
			sb.append(latexTabular.getColDefinition(i));
		}
		sb.append("}");
		renderer.add(new StringRenderer(sb.toString()));
	}
	
	private void renderTableHeader(Head thead)
	{
		renderer.add(new StringRenderer("\\hline Key &  Value \\\\"));
		renderer.add(new StringRenderer("\\hline \\hline"));
	}
	
	private void renderBody(Body tbody) throws OfxAuthoringException
	{
		for(Row row : tbody.getRow())
		{
			RowFactory f = new RowFactory();
			f.render(row);
			renderer.add(f);
		}
	}
}