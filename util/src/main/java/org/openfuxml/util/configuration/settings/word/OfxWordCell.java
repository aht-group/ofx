package org.openfuxml.util.configuration.settings.word;

import java.awt.Color;

public class OfxWordCell
{
	private	int paragraphAlignment; public int getParagraphAlignment() { return paragraphAlignment; }
	private	int verticalAlignment; public int getVerticalAlignment() { return verticalAlignment; }
	private	double cWith; public double getcWith() { return cWith; }
	private	int topLineStyle; public int getTopLineStyle() { return topLineStyle; }
	private	double topLineWidth; public double getTopLineWidth() { return topLineWidth; 	}
	private	Color topLineBorderColor; public Color getTopLineBorderColor() { return topLineBorderColor; }
	
//	ToDo: indentify all default values / getter / setter / etc 
	
//	private	int rightLineStyle;
//	private	double rightLineWidth;
//	private	Color rightLineBorderColor;
//	private	int bottomLineStyle;
//	private	double bottomLineWidth;
//	private	Color bottomLineBorderColor;
//	private	int leftLineStyle;
//	private	double leftLineWidth;
//	private	Color leftLineBorderColor;
//	private int spaceBefore;
//	private int spaceAfter;
//	private int lineSpacing;
//	private double leftIdent;
//	private Color tableBackgroundColor;
//	private int textureIndex;
	
	public static OfxWordCell instance() {return new OfxWordCell();}
	
	public OfxWordCell()
	{
		paragraphAlignment = 0;
		verticalAlignment = 0;
		cWith = 100;
	}
	
	public OfxWordCell paragraphAlignment(int value) {paragraphAlignment=value;return this;}
	public OfxWordCell verticalAlignment(int value) {verticalAlignment=value;return this;}
}