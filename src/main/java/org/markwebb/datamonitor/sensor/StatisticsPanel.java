package org.markwebb.datamonitor.sensor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class StatisticsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int TEXTFIELD_SIZE = 12;
	
	private JProgressBar progressBar;
	private JTextField maxField, currentField, averageField;
	
	private double max;
	private double average;
	private double current;
	private double datacount;
	private double total;
	private String metadata;
	private Color color;
	
	public StatisticsPanel(String metadata, Color color)
	{
		super();
		
		this.metadata = metadata;
		this.color = color;
		
		progressBar = new JProgressBar( 0, 100 );
		progressBar.setStringPainted( true );
		progressBar.setForeground(color);
		
		max = 0;
		average = 0;
		current = 0;
		
		setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.WEST;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets( 5,2,5,2 );
		
		c.gridx = 0;
		c.gridy = 0;
		add( new JLabel("% of Max "), c );
		c.gridx = 1;
		add( progressBar, c );
		
		c.gridx = 0;
		c.gridy = 1;
		add( new JLabel("Max " ), c );
		c.gridx = 1;
		maxField = new JTextField( TEXTFIELD_SIZE );
		maxField.setEditable( false );
		
		progressBar.setPreferredSize(maxField.getPreferredSize());
		
		add( maxField, c );
		
		c.gridx = 0;
		c.gridy = 2;
		add( new JLabel("Current " ), c );
		c.gridx = 1;
		currentField = new JTextField( TEXTFIELD_SIZE );
		currentField.setEditable( false );
		
		add( currentField, c );
		
		c.gridx = 0;
		c.gridy = 3;
		add( new JLabel("Average " ), c );
		c.gridx = 1;
		averageField = new JTextField( TEXTFIELD_SIZE );
		averageField.setEditable( false );
		
		add( averageField, c );
	}
	
	public void newValue( double value )
	{
		datacount++;
		total += value;
		
		if( current != value )
			currentField.setText( String.valueOf(current));
		
		current = value;
		
		if( max < value )
		{ 
			max = value;
			maxField.setText( String.valueOf(max) );
		}
		
		average = total / datacount;
		averageField.setText( String.valueOf(average));
		
		int pctmax = ((int)((current/max)*100));
		progressBar.setValue( pctmax );
	}	
}
