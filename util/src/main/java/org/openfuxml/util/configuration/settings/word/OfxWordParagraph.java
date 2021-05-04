package org.openfuxml.util.configuration.settings.word;

import com.aspose.words.ParagraphAlignment;

public class OfxWordParagraph
{
	private double leftIdent; public double getIdent() {return leftIdent;}
	private int spaceBefore; public int getSpaceBefore() {return spaceBefore;}
	private int spaceAfter; public int getSpaceAfter() {return spaceAfter;}
	private int lineSpacing; public int getLineSpacing() {return lineSpacing;}
	private boolean keepTogether; public boolean isKeepTogether() {return keepTogether;}
	private int paragraphAlignment; public int getParagraphAlignment() {return paragraphAlignment;}
	
	public static OfxWordParagraph instance() {return new OfxWordParagraph();}
	
	public OfxWordParagraph()
	{
		leftIdent = 0;
		spaceBefore = 3;
		spaceAfter = 3;
		lineSpacing = 12;
		keepTogether = false;
		paragraphAlignment = ParagraphAlignment.LEFT;
	}
		
	public OfxWordParagraph leftIdent(double value) {leftIdent=value;return this;}
	
	public OfxWordParagraph spaceBefore(int value) {spaceBefore=value;return this;}
	public OfxWordParagraph spaceAfter(int value) {spaceAfter=value;return this;}
	public OfxWordParagraph lineSpacing(int value) {lineSpacing=value;return this;}
	
	public OfxWordParagraph keepTogether() {keepTogether(true);return this;}
	public OfxWordParagraph keepTogether(boolean value) {keepTogether=value;return this;}
	
	public OfxWordParagraph paragraphAlignmentJustify() {paragraphAlignment(ParagraphAlignment.JUSTIFY);return this;}
	public OfxWordParagraph paragraphAlignmentCenter() {paragraphAlignment(ParagraphAlignment.CENTER);return this;}
	public OfxWordParagraph paragraphAlignmentRight() {paragraphAlignment(ParagraphAlignment.RIGHT);return this;}
	public OfxWordParagraph paragraphAlignment(int value) {paragraphAlignment=value;return this;}
}