package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.ofx.layout.Line;
import org.openfuxml.content.ofx.table.Body;
import org.openfuxml.content.ofx.table.Content;
import org.openfuxml.content.ofx.table.Head;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.content.ofx.table.Specification;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.table.util.LatexTabluarUtil;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexLineTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexLineTableRenderer.class);
	
	public LatexLineTableRenderer()
	{
		logger.trace("PostConstruct");
	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		if(!table.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!table.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
		renderPre();
		renderTabular(table.getSpecification(),table.getContent());
		renderPost(table);
	}
	
	private void renderPre()
	{		
		preTxt.add("");
		preTxt.add("\\begin{table}[htb]");
		preTxt.add("\\centering");
	}
	
	private void renderPost(Table table)
	{
		if(table.isSetTitle()) {postTxt.add("\\caption{"+table.getTitle().getValue()+"}");}
		if(table.isSetId())    {postTxt.add("\\label{"+table.getId()+"}");}
		postTxt.add("\\end{table}");
	}
	
	private void renderTabular(Specification specification, Content tgroup) throws OfxAuthoringException
	{
		if(tgroup.getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		
		openTablular(specification);
		renderTableHeader(tgroup.getHead());
		renderBody(tgroup.getBody().get(0));
		
		closeTablular();
	}
	
	private void openTablular(Specification spec)
	{
		LatexTabluarUtil latexTabular = new LatexTabluarUtil(spec.getColumns());
		
		renderer.add(new StringRenderer(latexTabular.getLatexLengthCalculations()));
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\begin{tabular}");
		sb.append("{");
		for(int i=0;i<spec.getColumns().getColumn().size();i++)
		{
			sb.append(latexTabular.getColDefinition(i));
			sb.append("");
		}
		sb.append("}");
		renderer.add(new StringRenderer(sb.toString()));
	}
	
	private void closeTablular()
	{
		renderer.add(new StringRenderer("\\bottomrule"));
		renderer.add(new StringRenderer("\\end{tabular}"));
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
				LatexRowRenderer f = new LatexRowRenderer(emphasis);
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
			
			LatexRowRenderer f = new LatexRowRenderer();
			f.render(row);
			renderer.add(f);
		}
	}
}