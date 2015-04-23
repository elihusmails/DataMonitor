package org.markwebb.datamonitor.sensor;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;


public class ChartSensorPanel extends AbstractSensorPanel
{
  private static final long serialVersionUID = 6664958760800013868L;
  
  private XYPlot xyplot;
  private JFreeChart jfreechart;
  private HashMap<String, TimeSeries> series;
  private HashMap<String, StatisticsPanel> statsPanelMap;
  private LinkedList<SensorData> pausequeue;
  
  private final Color[] colors = { Color.red, Color.GREEN, Color.YELLOW, Color.BLUE, Color.CYAN,
  		Color.BLUE, Color.ORANGE, Color.PINK };
  
  public ChartSensorPanel( String t, ArrayList<String> metadata )
  {
    state = RUNNING;
    
    pausequeue = new LinkedList<SensorData>();
    statsPanelMap = new HashMap<String, StatisticsPanel>();
    
    this.title = t;

    series = new HashMap<String,TimeSeries>();
    
    XYLineAndShapeRenderer xyLineRenderer = new XYLineAndShapeRenderer( true, false );
    xyLineRenderer.setStroke(new BasicStroke(2F, 0, 2));
    TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
    
    for( int i=0; i<metadata.size(); i++ )
    {
      TimeSeries ts = new TimeSeries( metadata.get(i) );
      timeseriescollection.addSeries( ts );
      xyLineRenderer.setSeriesPaint( i, colors[i] );
      series.put( metadata.get(i), ts );
    }
    
    DateAxis dateaxis = new DateAxis( "Time" );
    dateaxis.setAutoRange( true );
    dateaxis.setLowerMargin( 0.0D );
    dateaxis.setUpperMargin( 0.0D );
    dateaxis.setTickLabelsVisible( true );
    
    NumberAxis numberaxis = new NumberAxis( "" );
    numberaxis.setAutoRange( true );
    numberaxis.setAutoRangeIncludesZero( false );
    
    xyplot = new XYPlot( timeseriescollection, dateaxis, numberaxis, xyLineRenderer );
    xyplot.setBackgroundPaint( Color.black );
    xyplot.setDomainGridlinePaint( Color.white );
    xyplot.setRangeGridlinePaint( Color.white );
    xyplot.setAxisOffset( new RectangleInsets( 5D, 5D, 5D, 5D ));
    
    ValueAxis valueaxis = xyplot.getDomainAxis();
    valueaxis.setAutoRange(true);
    valueaxis.setFixedAutoRange(60000D);

    // XXX: Doing some really inefficient GUI layout here, probably want to fix sometime
    
    jfreechart = new JFreeChart( title, new Font("SansSerif", 1, 16), xyplot, true );
    ChartPanel chartpanel = new ChartPanel( jfreechart );

    add( chartpanel, BorderLayout.CENTER );
    
    JPanel bottomPanel = new JPanel();
    bottomPanel.add( new SensorPanelButtons(this), BorderLayout.WEST );
    
    JPanel stats = new JPanel();
    StatisticsPanel cur = null;
    for(int i = 0; i < metadata.size(); ++i) {
    	cur = new StatisticsPanel(metadata.get(i), colors[i]);
    	statsPanelMap.put(metadata.get(i), cur);
    	stats.add( cur );
    }
    
    JScrollPane statsScroll = new JScrollPane(stats, 
    		JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
    		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    bottomPanel.add( statsScroll, BorderLayout.EAST );
    
    add( bottomPanel, BorderLayout.SOUTH );
  }
  
  public String getTitle()
  {
    return title;
  }

  public Dimension getSize()
  {
	return new Dimension( 500, 500);//700,550 );
  }
  
  @Override
  public void receiveUpdate( SensorData data )
  {
    if( state == STOPPED )
      return;
    
      if( state == PAUSED )
      {
    	  pausequeue.add( data );
    	  return;
      }
      
      StatisticsPanel sp = statsPanelMap.get(data.getMetadata());
      if(sp != null)
    	  sp.newValue( data.getData() );
      
      if( pausequeue.size() > 0 )
      {
    	  for( int i=0; i<pausequeue.size(); i++ )
    	  {
    		  SensorData sd = pausequeue.poll();
    		  receiveUpdate( sd );
    		  sd = null;
    	  }
      }
      
    TimeSeries ts = series.get( data.getMetadata() );
    
    if( ts != null )
    	ts.addOrUpdate( new Millisecond(new Date(data.getTime())), data.getData() );
//    else {}
//    	System.err.println("Invalid metadata --> " + data.getMetadata() );
  }
}
