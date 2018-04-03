package org.openfuxml.renderer.word.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveUnwantedRegx
{
	final static Logger logger = LoggerFactory.getLogger(RemoveUnwantedRegx.class);
	
	public RemoveUnwantedRegx(){}
			
	public String removeUnwantedFrom(String s)
	{
		StringBuffer sb = new StringBuffer(s);
		for(int i=0; i<sb.length(); i++)
        {
           if(sb.charAt(i) == '\n')sb.deleteCharAt(i);
           if(sb.charAt(i) == '\r')sb.deleteCharAt(i);
           if(sb.charAt(i) == '\t')sb.deleteCharAt(i);           
        }
		return sb.toString().replaceAll("\\s+", " ");
	}
}

