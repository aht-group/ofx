package org.openfuxml.addon.chart.renderer.generic;

import java.awt.Dimension;

import org.jfree.chart.JFreeChart;
import org.openfuxml.xml.addon.chart.Chart;

public interface OfxChartRenderer
{
	JFreeChart render(Chart ofxChart);
	Dimension getSuggestedSize();
}
