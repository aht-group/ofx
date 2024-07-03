package org.openfuxml.renderer.docx.aspose.util;

import java.awt.Color;
import java.util.Objects;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.PreferredWidth;

public class OfxAsposeDocxCell
{
	private Color color, colorDefault; public Color getColor() {return color;}
	private Double width, widthDefault; public Double getWidth() {return width;}
	
	public static OfxAsposeDocxCell instance() {return new OfxAsposeDocxCell();}
	
	public OfxAsposeDocxCell()
	{
		color = Color.WHITE;
		width = null;
		save();
	}
	
	public void save()
	{
		colorDefault = color;
		widthDefault = width;
	}
	
	public OfxAsposeDocxCell reset()
	{
		color = colorDefault;
		width = widthDefault;
		
		return this;
	}
	
	public OfxAsposeDocxCell clone()
	{
		OfxAsposeDocxCell f = OfxAsposeDocxCell.instance();
		
		f.color(color);
		f.width(width);
		
		return f;
	}
	
	
	
	public OfxAsposeDocxCell color(Color value) {color=value; return this;}
	public OfxAsposeDocxCell width(Integer value) {if(Objects.isNull(value)) {width=null;} else {width = value.doubleValue();} return this;}
	public OfxAsposeDocxCell width(Double value) {width=value; return this;}
	
	public void apply(DocumentBuilder builder)
	{
		builder.getCellFormat().getShading().setBackgroundPatternColor(color);
		if(Objects.nonNull(width)) {builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(width));}
	}
}