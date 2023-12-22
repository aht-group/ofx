package org.openfuxml.renderer.latex.content.list;

import java.util.Objects;

import org.openfuxml.content.layout.Layout;
import org.openfuxml.content.layout.Spacing;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.list.XmlListFactory2;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.list.Type;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.table.LatexCellRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexListRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexListRenderer.class);

	public static enum ListType {description,list}
	
	private ListType listType;
	private boolean preBlankLine;
	
	public LatexListRenderer(ConfigurationProvider cp){this(cp,true);}
	public LatexListRenderer(ConfigurationProvider cp,boolean preBlankLine)
	{
		super(cp);
		this.preBlankLine=preBlankLine;
	}
	
	public void render(List list, OfxLatexRenderer parent) throws OfxAuthoringException
	{	
		if(Objects.isNull(list.getType())) {throw new OfxAuthoringException("<type> not defined for <list>");}
		estimateType(list.getType());
		
		String debugType=null;
		if(Objects.nonNull(list.getType().isDescription()) && list.getType().isDescription()){debugType=ListType.description.toString()+" "+List.class.getSimpleName();}
		else{debugType = "("+listType+") "+List.class.getSimpleName();}
		
		if(preBlankLine){preTxt.add("");}
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+debugType+" with: "+this.getClass().getSimpleName()));
		if(Objects.nonNull(list.getComment()))
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cp);
			rComment.render(list.getComment());
			preTxt.addAll(rComment.getContent());
		}
		preTxt.add("");
		
		setEnvironment(list.getType(),parent);
		if(Objects.nonNull(list.getLayout())) {layout(list.getLayout());}
		
		
		for(Item item : list.getItem())
		{
			LatexItemFactory f = new LatexItemFactory(cp);
			f.render(listType,item);
			renderer.add(f);
		}
		postTxt.add("");
	}
	
	private void estimateType(Type xmlType) throws OfxAuthoringException
	{
		if(Objects.nonNull(xmlType.isDescription()) && xmlType.isDescription())
		{
			listType = ListType.description;
			if(Objects.nonNull(xmlType.getOrdering())) {throw new OfxAuthoringException("<type> is a description, but ordering is set!");}
		}
		else if(Objects.nonNull(xmlType.getOrdering()))
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
			XmlListFactory2.Ordering ordering = XmlListFactory2.Ordering.valueOf(xmlType.getOrdering());
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
		preTxt.add("\\begin{minipage}[t]{\\linewidth}");
		preTxt.add("\\begin{itemize}[nosep,leftmargin=1em,labelwidth=*,align=left,parsep=0pt]");
		postTxt.add("");
		postTxt.add("\\end{itemize}");
		postTxt.add("\\end{minipage}");
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
		preTxt.add("\\begin{minipage}[t]{\\linewidth}");
		preTxt.add("\\begin{itemize}[nosep,leftmargin=1em,labelwidth=*,align=left,parsep=0pt]");
		postTxt.add("");
		postTxt.add("\\end{itemize}");
		postTxt.add("\\end{minipage}");
	}
	
	private void setDescription()
	{
		preTxt.add("\\begin{description}");
		postTxt.add("");
		postTxt.add("\\end{description}");
	}
	
	private void layout(Layout layout)
	{
		if(Objects.nonNull(layout.getSpacing()))
		{
			Spacing space = layout.getSpacing();
			preTxt.add("\\setlength{\\itemsep}{"+space.getValue()+space.getUnit()+"}");
			preTxt.add("\\setlength{\\parskip}{"+space.getValue()+space.getUnit()+"}");
			preTxt.add("\\setlength{\\parsep}{"+space.getValue()+space.getUnit()+"}");
		}
	}
}