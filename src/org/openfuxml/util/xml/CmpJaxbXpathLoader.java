package org.openfuxml.util.xml;

import java.util.List;
import java.util.NoSuchElementException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.data.jaxb.Merge;

public class CmpJaxbXpathLoader
{
	static Log logger = LogFactory.getLog(CmpJaxbXpathLoader.class);
	
	public static synchronized Merge getMerge(List<Merge> list, String code)
	{
		for(Merge merge : list)
		{
			if(merge.getCode().equals(code)){return merge;}
		}
		throw new NoSuchElementException(" <merge code="+code+"/> not found");
	}
}