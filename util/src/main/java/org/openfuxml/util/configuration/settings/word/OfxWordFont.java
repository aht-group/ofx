package org.openfuxml.util.configuration.settings.word;

import java.awt.Color;

public class OfxWordFont
{
	private boolean italic,italicDefault; public boolean isItalic() {return italic;}
	private boolean bold,boldDefault; public boolean isBold() {return bold;}
	private boolean underline,underlineDefault; public boolean isUnderline() {return underline;}
	private double size, sizeDefault; public double getSize() {return size;}
	private Color color, colorDefault; public Color getColor() {return color;}
	private double ident, identDefault; public double getIdent() {return ident;}
	private int spaceBefore,spaceBeforeDefault; public int getSpaceBefore() {return spaceAfter;}
	private int spaceAfter,spaceAfterDefault; public int getSpaceAfter() {return spaceAfter;}
	private int lineSpacing,lineSpacingDefault; public int getLineSpacing() {return lineSpacing;}
	
	
	public static OfxWordFont instance() {return new OfxWordFont();}
	
	public OfxWordFont()
	{
		size = 10;
		bold = false;
		italic = false;
		underline = false;
		color = Color.BLACK;
		ident = 0;
		spaceBefore = 3;
		spaceAfter = 3;
		lineSpacing = 12;
		
		save();
	}
	
	public void save()
	{
		sizeDefault = size;
		boldDefault = bold;
		italicDefault = italic;
		underlineDefault = underline;
		colorDefault = color;
		identDefault = ident;
		spaceBeforeDefault = spaceBefore;
		spaceAfterDefault = spaceAfter;
		lineSpacingDefault = lineSpacing;
	}
	
	public OfxWordFont reset()
	{
		size = sizeDefault;
		bold = boldDefault;
		italic = italicDefault;
		underline = underlineDefault;
		color = colorDefault;
		ident = identDefault;
		spaceBefore = spaceBeforeDefault;
		spaceAfter = spaceAfterDefault;
		lineSpacing = lineSpacingDefault;
		
		return this;
	}
	
	public OfxWordFont size(double value) {size=value;return this;}
	
	public OfxWordFont italic() {italic(true);return this;}
	public OfxWordFont italic(boolean value) {italic=value;return this;}
	
	public OfxWordFont bold() {bold(true);return this;}
	public OfxWordFont bold(boolean value) {bold=value;return this;}
	
	public OfxWordFont underline() {underline(true);return this;}
	public OfxWordFont underline(boolean value) {underline=value;return this;}
	
	public OfxWordFont color(Color value) {color=value;return this;}
	
	public OfxWordFont ident(double value) {ident=value;return this;}
	
	public OfxWordFont spaceBefore(int value) {spaceBefore=value;return this;}
	public OfxWordFont spaceAfter(int value) {spaceAfter=value;return this;}
	public OfxWordFont lineSpacing(int value) {lineSpacing=value;return this;}
}