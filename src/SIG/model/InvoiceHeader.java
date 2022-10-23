package SIG.model;

import SIG.view.SIGFramee;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author menna
 */
public class InvoiceHeader {
    
     private int num;
    private Date date;
    private String customername;
    private ArrayList<InvoiceLine> lines;
    
    public InvoiceHeader(){
    
    }

    public InvoiceHeader(int num,Date date ,String customername) {
        this.num = num;
        this.date = date;
        this.customername = customername;
       
    }
    
    public double getTotal(){
        double total = 0.0;
        
        for(InvoiceLine line :getLines()){
            total += line.getTotal(); 
        }
        return total;
    }

    public ArrayList<InvoiceLine> getLines() {
        if (lines == null){
            lines= new ArrayList<>();
        }
        return lines;
    }

    @Override 
    public String toString(){
        return num + "," + SIGFramee.df.format(date)+ "," + customername;

    }
    
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }
    
}
