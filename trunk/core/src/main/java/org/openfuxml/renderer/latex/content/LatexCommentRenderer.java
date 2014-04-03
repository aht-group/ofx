package org.openfuxml.renderer.latex.content;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCommentRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexCommentRenderer.class);
	
	public LatexCommentRenderer()
	{

	}
	
	public void render(Comment comment) throws OfxAuthoringException
	{
		for(Raw raw : comment.getRaw())
		{
			txt.addAll(comment(raw.getValue()));
		}
	}
	
	public static List<String> line(){return comment("******************************************",false);}
	public static List<String> comment(String comment){return comment(comment,false);}
	public static List<String> comment(String comment, boolean postLine)
	{
		List<String> result = new ArrayList<String>();
		result.add(line(comment));
		if(postLine){result.add("");}
		return result;
	}
	
	private static String line(String s)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("% ");
		sb.append(s);
		return sb.toString();
	}
}