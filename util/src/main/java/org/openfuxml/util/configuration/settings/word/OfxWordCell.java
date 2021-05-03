package org.openfuxml.util.configuration.settings.word;

import java.awt.Color;

public class OfxWordCell
{
	private	int paragraphAlignment; public int getParagraphAlignment() { return paragraphAlignment; }
	private	int verticalAlignment; public int getVerticalAlignment() { return verticalAlignment; }
	private	double cellWidth; public double getCellWith() { return cellWidth; }
	
	private	int topLineStyle; public int getTopLineStyle() { return topLineStyle; }
	private	double topLineWidth; public double getTopLineWidth() { return topLineWidth; 	}
	private	Color topLineBorderColor; public Color getTopLineBorderColor() { return topLineBorderColor; }

	private	int rightLineStyle; public int getRightLineStyle() { return rightLineStyle; }
	private	double rightLineWidth; public double getRightLineWidth() { return rightLineWidth; 	}
	private	Color rightLineBorderColor; public Color getRightLineBorderColor() { return rightLineBorderColor; }
	
	private	int bottomLineStyle; public int getBottomLineStyle() { return bottomLineStyle; }
	private	double bottomLineWidth; public double getBottomLineWidth() { return bottomLineWidth; 	}
	private	Color bottomLineBorderColor; public Color getBottomLineBorderColor() { return bottomLineBorderColor; }
	
	private	int leftLineStyle; public int getLeftLineStyle() { return leftLineStyle; }
	private	double leftLineWidth; public double getLeftLineWidth() { return leftLineWidth; 	}
	private	Color leftLineBorderColor; public Color getLeftLineBorderColor() { return leftLineBorderColor; }
	
	private Color tableBackgroundColor; public Color getTablBackgroundColor() { return tableBackgroundColor;}
	private int textureIndex; public int getTextureIndex() {return textureIndex;}
	
	public static OfxWordCell instance() {return new OfxWordCell();}
	
	public OfxWordCell()
	{
		paragraphAlignment = 0;
		verticalAlignment = 0;
		cellWidth = 100;
		
		topLineStyle = 0;
		topLineWidth = 1;
		topLineBorderColor = Color.BLACK;
		
		leftLineStyle = 0;
		leftLineWidth = 1;
		leftLineBorderColor = Color.BLACK;
		
		bottomLineStyle = 0;
		bottomLineWidth = 1;
		bottomLineBorderColor = Color.BLACK;
		
		rightLineStyle = 0;
		rightLineWidth = 1;
		rightLineBorderColor = Color.BLACK;
		
		tableBackgroundColor = Color.WHITE;
		textureIndex = 0;
	}
	
	public OfxWordCell paragraphAlignmentCenter() {paragraphAlignment(1);return this;}
	public OfxWordCell paragraphAlignmentRight() {paragraphAlignment(2);return this;}
	public OfxWordCell paragraphAlignment(int value) {paragraphAlignment=value;return this;}

	public OfxWordCell verticalAlignmentCenter() {verticalAlignment(1);return this;}
	public OfxWordCell verticalAlignmentBottom() {verticalAlignment(2);return this;}
	public OfxWordCell verticalAlignment(int value) {verticalAlignment=value;return this;}
	
	public OfxWordCell cellWith(double value) {cellWidth=value;return this;}
	
	public OfxWordCell topLineStyleSingle() {topLineStyle(1);return this;}
	public OfxWordCell topLineStyle(int value) {topLineStyle=value;return this;}
	public OfxWordCell topLineWidth(double value) {topLineWidth=value;return this;}
	public OfxWordCell topLineBorderColor(Color value) {topLineBorderColor=value;return this;}

	public OfxWordCell rightLineStyleSingle() {rightLineStyle(1);return this;}
	public OfxWordCell rightLineStyle(int value) {rightLineStyle=value;return this;}
	public OfxWordCell rightLineWidth(double value) {rightLineWidth=value;return this;}
	public OfxWordCell rightLineBorderColor(Color value) {rightLineBorderColor=value;return this;}
	
	public OfxWordCell bottomLineStyleSingle() {bottomLineStyle(1);return this;}
	public OfxWordCell bottomLineStyle(int value) {bottomLineStyle=value;return this;}
	public OfxWordCell bottomLineWidth(double value) {bottomLineWidth=value;return this;}
	public OfxWordCell bottomLineBorderColor(Color value) {bottomLineBorderColor=value;return this;}

	public OfxWordCell leftLineStyleSingle() {leftLineStyle(1);return this;}
	public OfxWordCell leftLineStyle(int value) {leftLineStyle=value;return this;}
	public OfxWordCell leftLineWidth(double value) {leftLineWidth=value;return this;}
	public OfxWordCell leftLineBorderColor(Color value) {leftLineBorderColor=value;return this;}
	
	public OfxWordCell tableBackgroundColor(Color value) {tableBackgroundColor=value;return this;}
	public OfxWordCell textureIndex(int value) {textureIndex=value;return this;}
}