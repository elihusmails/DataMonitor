package org.markwebb.datamonitor.config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class AddInputPanel extends JPanel implements ActionListener
{
  private static final long serialVersionUID = -3584860625198668037L;
  
  private static final String DELETE = "delete";
  
  private JTable table;
  private AddInputTableModel tablemodel;
  private JPopupMenu popup;
  
  public AddInputPanel()
  {
    super();
  
    tablemodel = new AddInputTableModel();
    table = new JTable( tablemodel );
    table.addMouseListener(new MousePopupListener(  ));
    
    JScrollPane scrollpane = new JScrollPane( table );
    add( scrollpane, BorderLayout.CENTER );
    
    popup = new JPopupMenu();
    JMenuItem delete = new JMenuItem( DELETE );
    delete.addActionListener( this );
    popup.add( delete );
  }

  public void addMetaData( String md )
  {
    tablemodel.addRow( md );
  }
  
  public ArrayList<String> getInputs()
  {
    return tablemodel.getMetadata();
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if( e.getActionCommand().equals(DELETE))
    {
      int selectedrow = table.getSelectedRow();
      if( selectedrow == -1 )
        return;
      
      tablemodel.deleteRow( selectedrow );
    }
  }
  
  class MousePopupListener extends MouseAdapter
  {
    public void mousePressed(MouseEvent e) { checkPopup(e); }
    public void mouseClicked(MouseEvent e) { checkPopup(e); }
    private void checkPopup(MouseEvent e) {
        if (e.isPopupTrigger(  )) {
            popup.show(table, e.getX(), e.getY());
        }
    }
  }
}
