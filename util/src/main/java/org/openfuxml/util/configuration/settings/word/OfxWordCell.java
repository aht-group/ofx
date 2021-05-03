package org.openfuxml.util.configuration.settings.word;

import java.awt.Color;

import com.aspose.words.CellVerticalAlignment;
import com.aspose.words.LineStyle;
import com.aspose.words.ParagraphAlignment;

public class OfxWordCell
{
	private	int paragraphAlignment; public int getParagraphAlignment() { return paragraphAlignment; }
	private	int verticalAlignment; public int getVerticalAlignment() { return verticalAlignment; }
	private	double cellWidth; public double getCellWidth() { return cellWidth; }
	
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
	
	private Color tableBackgroundColor; public Color getTableBackgroundColor() { return tableBackgroundColor;}
	private int textureIndex; public int getTextureIndex() {return textureIndex;}
	
	public static OfxWordCell instance() {return new OfxWordCell();}
	
	public OfxWordCell()
	{
		paragraphAlignment = ParagraphAlignment.LEFT;
		verticalAlignment = CellVerticalAlignment.TOP;
		cellWidth = 100;
		
		topLineStyle = LineStyle.NONE;
		topLineWidth = 0;
		topLineBorderColor = Color.BLACK;
		
		leftLineStyle = LineStyle.NONE;
		leftLineWidth = 0;
		leftLineBorderColor = Color.BLACK;
		
		bottomLineStyle = LineStyle.NONE;
		bottomLineWidth = 0;
		bottomLineBorderColor = Color.BLACK;
		
		rightLineStyle = LineStyle.NONE;
		rightLineWidth = 0;
		rightLineBorderColor = Color.BLACK;
		
		tableBackgroundColor = Color.WHITE;
		textureIndex = 0;
	}
	
	public OfxWordCell paragraphAlignmentCenter() {paragraphAlignment(ParagraphAlignment.CENTER);return this;}
	public OfxWordCell paragraphAlignmentRight() {paragraphAlignment(ParagraphAlignment.RIGHT);return this;}
	public OfxWordCell paragraphAlignment(int value) {paragraphAlignment=value;return this;}

	public OfxWordCell verticalAlignmentCenter() {verticalAlignment(CellVerticalAlignment.CENTER);return this;}
	public OfxWordCell verticalAlignmentBottom() {verticalAlignment(CellVerticalAlignment.BOTTOM);return this;}
	public OfxWordCell verticalAlignment(int value) {verticalAlignment=value;return this;}
	
	public OfxWordCell cellWidth(double value) {cellWidth=value;return this;}
	
	public OfxWordCell topLineStyleSingle() {topLineStyle(LineStyle.SINGLE);return this;}
	public OfxWordCell topLineStyle(int value) {topLineStyle=value; topLineWidth=1; return this;}
	public OfxWordCell topLineWidth(double value) {topLineWidth=value;return this;}
	public OfxWordCell topLineBorderColor(Color value) {topLineBorderColor=value;return this;}

	public OfxWordCell rightLineStyleSingle() {rightLineStyle(LineStyle.SINGLE);return this;}
	public OfxWordCell rightLineStyle(int value) {rightLineStyle=value; rightLineWidth=1;return this;}
	public OfxWordCell rightLineWidth(double value) {rightLineWidth=value;return this;}
	public OfxWordCell rightLineBorderColor(Color value) {rightLineBorderColor=value;return this;}
	
	public OfxWordCell bottomLineStyleSingle() {bottomLineStyle(LineStyle.SINGLE);return this;}
	public OfxWordCell bottomLineStyle(int value) {bottomLineStyle=value; bottomLineWidth=1; return this;}
	public OfxWordCell bottomLineWidth(double value) {bottomLineWidth=value;return this;}
	public OfxWordCell bottomLineBorderColor(Color value) {bottomLineBorderColor=value;return this;}

	public OfxWordCell leftLineStyleSingle() {leftLineStyle(LineStyle.SINGLE);return this;}
	public OfxWordCell leftLineStyle(int value) {leftLineStyle=value; leftLineWidth=1; return this;}
	public OfxWordCell leftLineWidth(double value) {leftLineWidth=value;return this;}
	public OfxWordCell leftLineBorderColor(Color value) {leftLineBorderColor=value;return this;}
	
	public OfxWordCell SetAllLineStyleSingle() { SetAllLineStyle(LineStyle.SINGLE);return this;}
	public OfxWordCell SetAllLineStyle(int value) {
		topLineStyle(value);
		rightLineStyle(value);
		bottomLineStyle(value);
		leftLineStyle(value);
		return this;}
	
	public OfxWordCell tableBackgroundColor(Color value) {tableBackgroundColor=value;return this;}
	public OfxWordCell textureIndex(int value) {textureIndex=value;return this;}
}