package org.openfuxml.renderer.latex.content.graph;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTreeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTreeRenderer.class);
	
	@SuppressWarnings("unused")
	private LatexTreeNodeRenderer treeNodeRenderer;
	
	public LatexTreeRenderer(ConfigurationProvider cp)
	{
		super(cp);
		treeNodeRenderer = new LatexTreeNodeRenderer(cp);
	}
	
/*	public void render(Tree tree) throws OfxAuthoringException
	{	
		preTxt.add("\\Tree ");
		treeNodeRenderer.render(tree.getNode());
		renderer.add(treeNodeRenderer);
	}
	*/
}