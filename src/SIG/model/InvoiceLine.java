package SIG.model;

/**
 *
 * @author menna
 */
public class InvoiceLine {
    
     private InvoiceHeader invoice;
   private String itemname;
   private Double itemprice;
   private int count ;
   
   public InvoiceLine(){
   
   }

    public InvoiceLine( InvoiceHeader invoice,String itemname, Double itemprice, int count) {
        this.invoice = invoice;
        this.itemname = itemname;
        this.itemprice = itemprice;
        this.count = count;
     
    }

    
    public double getTotal(){
       return itemprice * count;
    }
  
    
    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }
    

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Double getItemprice() {
        return itemprice;
    }

    public void setItemprice(Double itemprice) {
        this.itemprice = itemprice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
   
    @Override
    public String toString(){
    
        return invoice.getNum() + "," + itemname + "," + itemprice + "," + count; 
    }
    
    
    
}
