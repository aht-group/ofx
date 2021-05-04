package org.openfuxml.util.configuration.settings.word;

import java.awt.Color;

import com.aspose.words.ParagraphAlignment;

public class OfxWordFont
{
	private boolean italic,italicDefault; public boolean isItalic() {return italic;}
	private boolean bold,boldDefault; public boolean isBold() {return bold;}
	private boolean underline,underlineDefault; public boolean isUnderline() {return underline;}
	private double size, sizeDefault; public double getSize() {return size;}
	private Color color, colorDefault; public Color getColor() {return color;}
	private double leftIdent, leftIdentDefault; public double getIdent() {return leftIdent;}
	private int spaceBefore,spaceBeforeDefault; public int getSpaceBefore() {return spaceBefore;}
	private int spaceAfter,spaceAfterDefault; public int getSpaceAfter() {return spaceAfter;}
	private int lineSpacing,lineSpacingDefault; public int getLineSpacing() {return lineSpacing;}
	private boolean keepTogether, keepTogetherDefault; public boolean isKeepTogether() {return keepTogether;}
	private int paragraphAlignment, paragraphAlignmentDefault; public int getParagraphAlignment() {return paragraphAlignment;}
	
	public static OfxWordFont instance() {return new OfxWordFont();}
	
	public OfxWordFont()
	{
		size = 10;
		bold = false;
		italic = false;
		underline = false;
		color = Color.BLACK;
		leftIdent = 0;
		spaceBefore = 3;
		spaceAfter = 3;
		lineSpacing = 12;
		keepTogether = false;
		paragraphAlignment = ParagraphAlignment.LEFT;
		save();
	}
	
	public void save()
	{
		sizeDefault = size;
		boldDefault = bold;
		italicDefault = italic;
		underlineDefault = underline;
		colorDefault = color;
		leftIdentDefault = leftIdent;
		spaceBeforeDefault = spaceBefore;
		spaceAfterDefault = spaceAfter;
		lineSpacingDefault = lineSpacing;
		keepTogetherDefault = keepTogether;
		paragraphAlignmentDefault = paragraphAlignment;
	}
	
	public OfxWordFont reset()
	{
		size = sizeDefault;
		bold = boldDefault;
		italic = italicDefault;
		underline = underlineDefault;
		color = colorDefault;
		leftIdent = leftIdentDefault;
		spaceBefore = spaceBeforeDefault;
		spaceAfter = spaceAfterDefault;
		lineSpacing = lineSpacingDefault;
		keepTogether = keepTogetherDefault;
		paragraphAlignment = paragraphAlignmentDefault;
		
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
	
	public OfxWordFont leftIdent(double value) {leftIdent=value;return this;}
	
	public OfxWordFont spaceBefore(int value) {spaceBefore=value;return this;}
	public OfxWordFont spaceAfter(int value) {spaceAfter=value;return this;}
	public OfxWordFont lineSpacing(int value) {lineSpacing=value;return this;}
	
	public OfxWordFont keepTogether() {keepTogether(true);return this;}
	public OfxWordFont keepTogether(boolean value) {keepTogether=value;return this;}
	
	public OfxWordFont paragraphAlignmentJustify() {paragraphAlignment(ParagraphAlignment.JUSTIFY);return this;}
	public OfxWordFont paragraphAlignmentCenter() {paragraphAlignment(ParagraphAlignment.CENTER);return this;}
	public OfxWordFont paragraphAlignmentRight() {paragraphAlignment(ParagraphAlignment.RIGHT);return this;}
	public OfxWordFont paragraphAlignment(int value) {paragraphAlignment=value;return this;}
}