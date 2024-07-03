package org.openfuxml.renderer.docx.aspose.util;

import java.awt.Color;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.Underline;

public class OfxAsposeDocxCell
{
	private Color color, colorDefault; public Color getColor() {return color;}
	
	public static OfxAsposeDocxCell instance() {return new OfxAsposeDocxCell();}
	
	public OfxAsposeDocxCell()
	{
		color = Color.WHITE;
		save();
	}
	
	public void save()
	{
		colorDefault = color;
	}
	
	public OfxAsposeDocxCell reset()
	{
		color = colorDefault;
		
		return this;
	}
	
	public OfxAsposeDocxCell clone()
	{
		OfxAsposeDocxCell f = OfxAsposeDocxCell.instance();
		
		f.color(color);
		
		return f;
	}
	
	
	
	public OfxAsposeDocxCell color(Color value) {color=value; return this;}
	
	public void apply(DocumentBuilder builder)
	{
		builder.getCellFormat().getShading().setBackgroundPatternColor(color);
	}
}