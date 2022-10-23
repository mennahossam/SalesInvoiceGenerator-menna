package SIG.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author menna
 */
public class LineTableModel extends AbstractTableModel{
     private ArrayList<InvoiceLine> LineArrayList;
    
    public LineTableModel(ArrayList<InvoiceLine> lineArrayList){
       
        this.LineArrayList = lineArrayList;
    }
    
    public LineTableModel (){
    
    }

    @Override
    public int getRowCount() {
        return LineArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return 5 ;
    }

    @Override
    public Object getValueAt(int i, int j) {
        InvoiceLine line = LineArrayList.get(i);
        switch(j){
            case 0 :
               return i+1;
            case 1 :
               return line.getItemname();
            case 2 :
               return line.getItemprice();
            case 3 :
               return line.getCount();
            case 4 :    
               return line.getTotal();
                
        }
        return "";
    }
    @Override
    public String getColumnName(int column){
        switch(column){
            case 0 :
                return "No.";
            case 1 :
                return "Item Name";
            case 2 :
                return "Item Price";
            case 3 :
                return "Count";
            case 4 :
                return "Total";
        }
        return "";
    }
    
}
