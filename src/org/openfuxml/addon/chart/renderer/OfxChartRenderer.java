package org.openfuxml.addon.chart.renderer;

import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.jaxb.Chart;

public interface OfxChartRenderer
{
	JFreeChart render(Chart ofxChart);
}
