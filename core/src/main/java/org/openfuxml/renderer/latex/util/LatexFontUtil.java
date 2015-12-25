package org.openfuxml.renderer.latex.util;

import org.openfuxml.content.layout.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexFontUtil
{
	final static Logger logger = LoggerFactory.getLogger(LatexFontUtil.class);
	
	public static String environmentBegin(Font font)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if(font.isSetRelativeSize())
		{
			if(font.getRelativeSize()==-4){sb.append("\\tiny");}
			else if(font.getRelativeSize()==-3){sb.append("\\scriptsize");}
			else if(font.getRelativeSize()==-2){sb.append("\\footnotesize");}
			else if(font.getRelativeSize()==-1){sb.append("\\small");}
			else if(font.getRelativeSize()==0){sb.append("\\normalsize");}
			else if(font.getRelativeSize()==1){sb.append("\\large");}
			else if(font.getRelativeSize()==2){sb.append("\\Large");}
			else if(font.getRelativeSize()==3){sb.append("\\LARGE");}
			else if(font.getRelativeSize()==4){sb.append("\\huge");}
			else if(font.getRelativeSize()==5){sb.append("\\Huge");}
		}
		return sb.toString();
	}
	
	public static String environmentEnd(){return "}";}
}
