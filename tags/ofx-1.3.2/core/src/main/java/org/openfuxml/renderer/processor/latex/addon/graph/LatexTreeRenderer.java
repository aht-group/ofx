package org.openfuxml.renderer.processor.latex.addon.graph;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
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