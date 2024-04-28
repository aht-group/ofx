package org.openfuxml.trancoder;

import java.awt.Color;
import java.util.List;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxColorTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(OfxColorTranscoder.class);
	
	public static Color blend(List<Color> colors)
	{	
		MutableDouble r = new MutableDouble(0);
		MutableDouble g = new MutableDouble(0);
		MutableDouble b = new MutableDouble(0);
		MutableDouble a = new MutableDouble(0);
		
		for(Color c : colors)
		{
			r.add(c.getRed()/colors.size());
			g.add(c.getGreen()/colors.size());
			b.add(c.getBlue()/colors.size());
			a.add(c.getAlpha()/colors.size());
		}
		return new Color(r.intValue(),g.intValue(),b.intValue(),a.intValue());
	}
}