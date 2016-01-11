package org.openfuxml.util;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.factory.xml.ofx.content.XmlRawFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCommentBuilder
{
	final static Logger logger = LoggerFactory.getLogger(OfxCommentBuilder.class);
	
	public static void fixedId(Comment comment, String id)
	{
		comment.getRaw().add(XmlRawFactory.build("The ID (label) for this element is fixed: "+id));
	}
	
	public static void doNotModify(Comment comment)
	{
		comment.getRaw().add(XmlRawFactory.build(""));
		comment.getRaw().add(XmlRawFactory.build("Do not modify this auto-generated file, it will be overwritten without warning!"));
	}
	
	public static void raw(Comment comment, String text)
	{
		comment.getRaw().add(XmlRawFactory.build(text));
	}
}