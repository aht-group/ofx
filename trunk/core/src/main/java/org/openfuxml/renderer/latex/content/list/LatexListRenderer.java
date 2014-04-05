package org.openfuxml.renderer.latex.content.list;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.list.XmlListFactory;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.LatexCommentRenderer;
import org.openfuxml.renderer.latex.content.table.LatexCellRenderer;
import org.openfuxml.xml.content.list.Item;
import org.openfuxml.xml.content.list.List;
import org.openfuxml.xml.content.list.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexListRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexListRenderer.class);

	public static enum ListType {description,list}
	
	private ListType listType;
	
	public LatexListRenderer()
	{

	}
	
	public void render(List list, OfxLatexRenderer parent) throws OfxAuthoringException
	{	
		if(!list.isSetType()){throw new OfxAuthoringException("<type> not defined for <list>");}
		estimateType(list.getType());
		
		String debugType=null;
		if(list.getType().isSetDescription() && list.getType().isDescription()){debugType=ListType.description.toString()+" "+List.class.getSimpleName();}
		else{debugType = "("+listType+") "+List.class.getSimpleName();}
		
		preTxt.add("");
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+debugType+" with: "+this.getClass().getSimpleName()));
		if(list.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer();
			rComment.render(list.getComment());
			renderer.add(rComment);
		}
		preTxt.add("");
		
		setEnvironment(list.getType(),parent);
		for(Item item : list.getItem())
		{
			LatexItemFactory f = new LatexItemFactory();
			f.render(listType,item);
			renderer.add(f);
		}
		postTxt.add("");
	}
	
	private void estimateType(Type xmlType) throws OfxAuthoringException
	{
		if(xmlType.isSetDescription() && xmlType.isDescription())
		{
			listType = ListType.description;
			if(xmlType.isSetOrdering()){throw new OfxAuthoringException("<type> is a description, but ordering is set!");}
		}
		else if(xmlType.isSetOrdering())
		{
			listType = ListType.list;
		}
		else {throw new OfxAuthoringException("<type> is not a description, but no ordering defined");}
	}
	
	private void setEnvironment(Type xmlType, OfxLatexRenderer parent) throws OfxAuthoringException
	{
		if(listType.equals(ListType.description))
		{
			setDescription();
		}
		else if(listType.equals(ListType.list))
		{
			XmlListFactory.Ordering ordering = XmlListFactory.Ordering.valueOf(xmlType.getOrdering());
			switch(ordering)
			{
				case unordered: setUnordered(parent);break;
				case ordered: setOrdered(parent);break;
				default: logger.warn("No Ordering defined NYI");break;
			}
		}
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
		postTxt.add("");
		postTxt.add("\\end{enumerate}");
	}
	
	private void setOrderedCompactNum()
	{
		preTxt.add("\\begin{compactenum}");
		postTxt.add("");
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
		postTxt.add("");
		postTxt.add("\\end{itemize}");
	}
	
	private void setUnorderedCompactItem()
	{
		preTxt.add("\\begin{compactitem}");
		postTxt.add("");
		postTxt.add("\\end{compactitem}");
	}
	
	private void setDescription()
	{
		preTxt.add("\\begin{description}");
		postTxt.add("");
		postTxt.add("\\end{description}");
	}
}