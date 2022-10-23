package SIG.controller;


import SIG.model.InvoiceHeader;
import SIG.model.InvoiceLine;
import SIG.model.LineTableModel;
import SIG.view.SIGFramee;
import static SIG.view.SIGFramee.df;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 *
 * @author menna
 */
public class C_SIGselection  implements ListSelectionListener{
    
    private SIGFramee frame;
    
    public C_SIGselection (SIGFramee frame){
        this.frame =frame;
    }
    
    public C_SIGselection (){
    
    }

    @Override
    public void valueChanged(ListSelectionEvent e){
      int selectedInvoiceIndex = frame.getInvoicesTable().getSelectedRow();
      if(selectedInvoiceIndex != -1){
          InvoiceHeader selectedInvoice =frame.getInvoiceHeaderArrayList().get(selectedInvoiceIndex);
          ArrayList<InvoiceLine> lineArrayList = selectedInvoice.getLines();
          LineTableModel lineTable = new LineTableModel(lineArrayList);
          frame.setLineArrayList(lineArrayList);
          frame.getInvoiceLineTable().setModel(lineTable);
          frame.getNoLabel().setText(""+selectedInvoice.getNum());
          frame. getDateLabel().setText(df.format(selectedInvoice.getDate()));
          frame.getCustomerNameLabel().setText(selectedInvoice.getCustomername());
          frame.getTotalLabel().setText(""+selectedInvoice.getTotal());
      
      }
      
    }
}
