package org.openfuxml.addon.chart.renderer.generic;

import java.awt.Dimension;

import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.data.jaxb.Chart;

public interface OfxChartRenderer
{
	JFreeChart render(Chart ofxChart);
	Dimension getSuggestedSize();
}
