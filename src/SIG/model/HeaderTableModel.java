package SIG.model;


import SIG.model.InvoiceHeader;
import SIG.view.SIGFramee;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author menna
 */
public class HeaderTableModel extends AbstractTableModel {
    
     private ArrayList<InvoiceHeader> HeaderArrayList;
    
    public HeaderTableModel(ArrayList<InvoiceHeader> HeaderArrayList){
        this.HeaderArrayList =HeaderArrayList;
    
    }
    
    public HeaderTableModel(){
    
    }

    @Override
    public int getRowCount() {
        return HeaderArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;   
    }

    @Override
    public Object getValueAt(int i, int j) {
        InvoiceHeader header= HeaderArrayList.get(i);
        switch(j){
        
            case 0 :
                return header.getNum();
            
            case 1 :
                return SIGFramee.df.format(header.getDate());
                
            case 2 :  
                return header.getCustomername();
            
            case 3 :
                return header.getTotal();
                
        } 
        return "";
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0 :
                return "No.";
            case 1 :
                return "Date";
            case 2 :
                return "Name";
            case 4 :    
                return "Total";
        
        }
        return "";
 
 }
    
}
