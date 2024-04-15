package org.openfuxml.renderer.docx.aspose.util;

import java.awt.Color;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.Underline;

public class OfxAsposeDocxFont
{
	private String name; public String getName() {return name;}
	private int docxLocaleId;  public int getDocxLocaleId() {return docxLocaleId;}
	
	private boolean italic, italicDefault; public boolean isItalic() {return italic;}
	private boolean bold, boldDefault; public boolean isBold() {return bold;}
	private boolean underline, underlineDefault; public boolean isUnderline() {return underline;}
	private double size, sizeDefault; public double getSize() {return size;}
	private Color color, colorDefault; public Color getColor() {return color;}
	
	public static OfxAsposeDocxFont instance() {return new OfxAsposeDocxFont();}
	
	public OfxAsposeDocxFont()
	{
		size = 10;
		bold = false;
		italic = false;
		underline = false;
		color = Color.BLACK;
		save();
	}
	
	public void save()
	{
		sizeDefault = size;
		boldDefault = bold;
		italicDefault = italic;
		underlineDefault = underline;
		colorDefault = color;
	}
	
	public OfxAsposeDocxFont reset()
	{
		size = sizeDefault;
		bold = boldDefault;
		italic = italicDefault;
		underline = underlineDefault;
		color = colorDefault;
		
		return this;
	}
	
	public OfxAsposeDocxFont name(String value) {name=value; return this;}
	public OfxAsposeDocxFont docxLocaleId(int value) {docxLocaleId=value;return this;}
	public OfxAsposeDocxFont size(double value) {size=value;return this;}
	
	public OfxAsposeDocxFont italic() {italic(true);return this;}
	public OfxAsposeDocxFont italic(boolean value) {italic=value;return this;}
	
	public OfxAsposeDocxFont bold() {bold(true);return this;}
	public OfxAsposeDocxFont bold(boolean value) {bold=value;return this;}
	
	public OfxAsposeDocxFont underline() {underline(true);return this;}
	public OfxAsposeDocxFont underline(boolean value) {underline=value;return this;}
	
	public OfxAsposeDocxFont color(Color value) {color=value;return this;}
	
	public void apply(DocumentBuilder builder)
	{
		Font font = builder.getFont();
		font.setName("Arial");
		font.setLocaleId(docxLocaleId);
		font.setSize(size);
		
		font.setBold(bold);
		
		if(underline) {font.setUnderline(Underline.SINGLE);}
		else {font.setUnderline(Underline.NONE);}
		
		font.setColor(color);
	}
}