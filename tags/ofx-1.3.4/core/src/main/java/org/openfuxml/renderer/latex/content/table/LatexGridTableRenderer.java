package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.StringRenderer;
import org.openfuxml.renderer.latex.content.table.util.LatexTabluarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexGridTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexGridTableRenderer.class);
	
	public LatexGridTableRenderer()
	{

	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		if(!table.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!table.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
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
		
		if(tgroup.getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		renderBody(tgroup.getBody().get(0));
		
		renderer.add(new StringRenderer("\\end{tabular}"));
	}
	
	private void renderSpecification(Specification spec)
	{
		LatexTabluarUtil latexTabular = new LatexTabluarUtil(spec.getColumns());
		
		renderer.add(new StringRenderer(latexTabular.getLatexLengthCalculations()));
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\begin{tabular}");
		sb.append("{|");
		for(int i=0;i<spec.getColumns().getColumn().size();i++)
		{
			sb.append(latexTabular.getColDefinition(i));
			sb.append("|");
		}
		sb.append("}");
		renderer.add(new StringRenderer(sb.toString()));
	}
	
	private void renderTableHeader(Head head) throws OfxAuthoringException
	{
		renderer.add(new StringRenderer("\\hline"));
		if(head!=null)
		{
			for(Row row : head.getRow())
			{
				LatexRowRenderer f = new LatexRowRenderer();
				f.render(row);
				renderer.add(f);
				renderer.add(new StringRenderer("\\hline"));
			}
			renderer.add(new StringRenderer("\\hline"));
		}
	}
	
	private void renderBody(Body tbody) throws OfxAuthoringException
	{
		for(Row row : tbody.getRow())
		{
			LatexRowRenderer f = new LatexRowRenderer();
			f.render(row);
			renderer.add(f);
			renderer.add(new StringRenderer("\\hline"));
		}
	}
}