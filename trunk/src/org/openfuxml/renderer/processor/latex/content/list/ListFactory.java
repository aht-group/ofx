package org.openfuxml.renderer.processor.latex.content.list;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.list.Item;
import org.openfuxml.content.ofx.list.List;
import org.openfuxml.content.ofx.list.Type;
import org.openfuxml.renderer.data.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.table.CellFactory;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class ListFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(ListFactory.class);

	private static enum Ordering {ordered,unordered}
	
	public ListFactory()
	{

	}
	
	public void render(List list, OfxLatexRenderer parent) throws OfxAuthoringException
	{	
		if(!list.isSetType()){throw new OfxAuthoringException("<type> not defined for <list>");}
		setEnvironment(list.getType(),parent);
		
		for(Item item : list.getItem())
		{
			ItemFactory f = new ItemFactory();
			f.render(item);
			renderer.add(f);
		}
	}
	
	private void setEnvironment(Type type, OfxLatexRenderer parent)
	{
		preTxt.add("");
		postTxt.add("");
		
		Ordering ordering = Ordering.valueOf(type.getOrdering());
		switch(ordering)
		{
			case unordered: setUnordered(parent);break;
			case ordered: setOrdered(parent);break;
			default: logger.warn("No Ordering defined NYI");break;
		}
	}
	
	// Ordered List
	private void setOrdered(OfxLatexRenderer parent)
	{
		if   (parent instanceof CellFactory){setOrderedCompactNum();}
		else {setOrderedEnumerate();}
	}
	
	private void setOrderedEnumerate()
	{
		preTxt.add("\\begin{enumerate}");
		postTxt.add("\\end{enumerate}");
	}
	
	private void setOrderedCompactNum()
	{
		preTxt.add("\\begin{compactenum}");
		postTxt.add("\\end{compactenum}");
	}
	
	// Unordered List
	private void setUnordered(OfxLatexRenderer parent)
	{
		if   (parent instanceof CellFactory){setUnorderedCompactItem();}
		else {setUnorderedItemize();}
	}
	
	private void setUnorderedItemize()
	{
		preTxt.add("\\begin{itemize}");
		postTxt.add("\\end{itemize}");
	}
	
	private void setUnorderedCompactItem()
	{
		preTxt.add("\\begin{compactitem}");
		postTxt.add("\\end{compactitem}");
	}
}