package org.openfuxml.addon.chart.interfaces;

import java.awt.Dimension;

import org.jfree.chart.JFreeChart;
import org.metachart.xml.chart.Chart;

public interface ChartRenderer
{
	JFreeChart render(Chart ofxChart);
	Dimension getSuggestedSize();
}
