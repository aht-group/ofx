package org.openfuxml.addon.chart.renderer.gantt;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.exlp.util.DateUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.Year;
import org.jfree.data.xy.IntervalXYDataset;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public class GanttChartRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Logger logger = Logger.getLogger(GanttChartRenderer.class);
	
	public static enum OfxChartTimePeriod {Hour,Day,Month};
	protected OfxChartTimePeriod ofxTimePeriod;
	
	public GanttChartRenderer()
	{
		
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		setTimePeriod();
		
		IntervalXYDataset dataset;
		//dataset = new XYTaskDataset(createTasks(ofxChart.getContainer()));
		dataset = new XYTaskDataset(createTasksDummy());
		
		chart = ChartFactory.createXYBarChart(
				ChartLabelResolver.getTitle(ofxChart),
				ChartLabelResolver.getYaxis(ofxChart),
				false,
				ChartLabelResolver.getXaxis(ofxChart),
				dataset,
                PlotOrientation.HORIZONTAL,
                ofxChart.isLegend(),
                false, false);

        chart.setBackgroundPaint(Color.white);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        SymbolAxis yAxis = new SymbolAxis(ChartLabelResolver.getYaxis(ofxChart), new String[] {"Team A",
                "Team B", "Team C", "Team D"});
        yAxis.setGridBandsVisible(false);
        plot.setDomainAxis(yAxis);
        
        Map<String,Color> colorMap = ChartColorFactory.getColorMap(ofxChart.getColors(), "task");
        plot.setRenderer(new ColorTaskXYBarRenderer(colorMap));
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setUseYInterval(true);
        
        setRangeAxis();
        
        setColors();
        setGrid();
        
        return chart;
	}
	
	private TaskSeriesCollection createTasksDummy()
	{
		Date from,to;
		SimpleTimePeriod stp;
        TaskSeriesCollection dataset = new TaskSeriesCollection();
  
/*        TaskSeries s1 = new TaskSeries("Team A");
        s1.add(new Task("T1a", new Hour(11, new Day())));
        s1.add(new Task("T1b", new Hour(14, new Day())));
        s1.add(new Task("T1c", new Hour(16, new Day())));
        dataset.add(s1);
        
        TaskSeries s2 = new TaskSeries("Team B");
        s2.add(new Task("T2a", new Hour(13, new Day())));
        s2.add(new Task("T2b", new Hour(19, new Day())));
        s2.add(new Task("T2c", new Hour(21, new Day())));
        dataset.add(s2);
        
        TaskSeries s3 = new TaskSeries("Team C");
        s3.add(new Task("T3a", new Hour(13, new Day())));
        s3.add(new Task("T2b", new Hour(19, new Day())));
        s3.add(new Task("T3c", new Hour(21, new Day())));
        dataset.add(s3);
*/
        TaskSeries s4 = new TaskSeries("Team D");
       
        from = DateUtil.getDateFromInt(2010, 1, 1);
		to = DateUtil.getDateFromInt(2010, 1, 15);
		stp = new SimpleTimePeriod(from,to);
        s4.add(new Task("na", stp));
        
        from = DateUtil.getDateFromInt(2010, 1, 17);
		to = DateUtil.getDateFromInt(2010, 1, 21);
		stp = new SimpleTimePeriod(from,to);
        s4.add(new Task("holiday", stp));
        
        dataset.add(s4);
               
        return dataset;
    }
	
	private TaskSeriesCollection createTasks(List<Container> container)
	{
		TaskSeriesCollection dataset = new TaskSeriesCollection();
		
		for(Container c: container)
		{	
			TaskSeries ts = new TaskSeries(c.getLabel());
			for(Data d : c.getData())
			{
				Date from = DateUtil.getDate4XmlGc(d.getFrom());
				Date to = DateUtil.getDate4XmlGc(d.getTo());
				SimpleTimePeriod stp = new SimpleTimePeriod(from,to);
				Task t = new Task(d.getCategory(),stp);
				ts.add(t);
			}
			dataset.add(ts);
		}
		return dataset;
	}
}
