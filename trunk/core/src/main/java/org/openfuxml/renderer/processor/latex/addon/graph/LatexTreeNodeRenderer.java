package org.openfuxml.renderer.processor.latex.addon.graph;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.openfuxml.xml.addon.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTreeNodeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTreeNodeRenderer.class);
	
	public LatexTreeNodeRenderer()
	{
		
	}
	
	public void render(Node node) throws OfxAuthoringException
	{	
		StringBuffer sb = new StringBuffer();
		sb.append(" [");
		if(node.isSetLabel())
		{
			sb.append(".{");
			sb.append(node.getLabel());
			sb.append("}" );
		}
		
		preTxt.add(sb.toString());
		for(Node child : node.getNode())
		{
			LatexTreeNodeRenderer childRenderer = new LatexTreeNodeRenderer();
			childRenderer.render(child);
			renderer.add(childRenderer);
		}
		postTxt.add("]");
	}
}