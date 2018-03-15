package org.openfuxml.renderer.word.util;

import java.awt.Color;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.Underline;

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

