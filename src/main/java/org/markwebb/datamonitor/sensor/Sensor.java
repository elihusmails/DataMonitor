package org.markwebb.datamonitor.sensor;

import java.io.Serializable;
import java.util.ArrayList;

public class Sensor implements Serializable
{
	private static final long serialVersionUID = 1912893217848395101L;
	private String title;
  private String source;
  private int id;
  private ArrayList<String> metadata;
  private int type;
  
  public Sensor( String title, String source, int id, int type )
  {
    this.title = title;
    this.source = source;
    this.id = id;
    this.type = type;
    metadata = new ArrayList<String>();
  }

  public String getSource()
  {
    return source;
  }

  public void setSource(String source)
  {
    this.source = source;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String name)
  {
    this.title = name;
  }
  
  public int getType()
  {
    return type;
  }

  public void setType(int type)
  {
    this.type = type;
  }

  public ArrayList<String> getMetaData()
  {
    return metadata;
  }
  
  public void addMetaData( String md )
  {
    metadata.add( md );
  }
}
