package org.openfuxml.renderer.latex.content.graph;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.xml.addon.graph.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTreeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTreeRenderer.class);
	
	private LatexTreeNodeRenderer treeNodeRenderer;
	
	public LatexTreeRenderer()
	{
		treeNodeRenderer = new LatexTreeNodeRenderer();
	}
	
	public void render(Tree tree) throws OfxAuthoringException
	{	
		preTxt.add("\\Tree ");
		treeNodeRenderer.render(tree.getNode());
		renderer.add(treeNodeRenderer);
	}
}