package org.openfuxml.renderer.docx.aspose.util;

public class OfxDocxPointConverter {

	private double cmFactor = 28.3464566929;
	private double pointFactor = 0.0352777778;
	
	public double getCmFactor() { return cmFactor; } 
	public void setCmFactor(double cmFactor) { this.cmFactor = cmFactor; }
	
	public double getPointFactor() { return pointFactor; } 
	public void setPointFactor(double pointFactor) { this.pointFactor = pointFactor; }
	
	public double cmToPoint(double cm) { return cm * cmFactor; }
	public double pointToCM(double point) {	return point * pointFactor;	}
	
}
