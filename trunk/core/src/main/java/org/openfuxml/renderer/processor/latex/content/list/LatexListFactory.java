package org.openfuxml.renderer.processor.latex.content.list;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.content.table.LatexCellRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.openfuxml.xml.content.list.Item;
import org.openfuxml.xml.content.list.List;
import org.openfuxml.xml.content.list.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexListFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexListFactory.class);

	private static enum Ordering {ordered,unordered}
	public static enum ListType {description,list}
	
	private ListType listType;
	
	public LatexListFactory()
	{

	}
	
	public void render(List list, OfxLatexRenderer parent) throws OfxAuthoringException
	{	
		if(!list.isSetType()){throw new OfxAuthoringException("<type> not defined for <list>");}
		setEnvironment(list.getType(),parent);
		
		for(Item item : list.getItem())
		{
			LatexItemFactory f = new LatexItemFactory();
			f.render(listType,item);
			renderer.add(f);
		}
	}
	
	private void setEnvironment(Type xmlType, OfxLatexRenderer parent) throws OfxAuthoringException
	{
		preTxt.add("");
		postTxt.add("");
		if(xmlType.isSetDescription() && xmlType.isDescription())
		{
			listType = ListType.description;
			if(xmlType.isSetOrdering()){throw new OfxAuthoringException("<type> is a description, but ordering is set!");}
			setDescription();
		}
		else if(xmlType.isSetOrdering())
		{
			listType = ListType.list;
			
			Ordering ordering = Ordering.valueOf(xmlType.getOrdering());
			switch(ordering)
			{
				case unordered: setUnordered(parent);break;
				case ordered: setOrdered(parent);break;
				default: logger.warn("No Ordering defined NYI");break;
			}
		}
		else {throw new OfxAuthoringException("<type> is not a description, but no ordering defined");}
	}
	
	// Ordered List
	private void setOrdered(OfxLatexRenderer parent)
	{
		if   (parent instanceof LatexCellRenderer){setOrderedCompactNum();}
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
		if   (parent instanceof LatexCellRenderer){setUnorderedCompactItem();}
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
	
	// Description
	private void setDescription()
	{
		preTxt.add("\\begin{description}");
		postTxt.add("\\end{description}");
	}
}