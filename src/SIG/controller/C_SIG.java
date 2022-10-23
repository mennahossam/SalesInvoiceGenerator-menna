package SIG.controller;

import SIG.model.HeaderTableModel;
import SIG.model.InvoiceHeader;
import SIG.model.InvoiceLine;
import SIG.model.LineTableModel;
import SIG.view.InvoiceHeaderDialog;
import SIG.view.InvoiceLineDialog;
import SIG.view.SIGFramee;
import static SIG.view.SIGFramee.df;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author menna
 */
public class C_SIG implements ActionListener{
    
    private SIGFramee frame ;
    private InvoiceHeaderDialog InvoiceHeaderDialog;
    private InvoiceLineDialog InvoiceLineDialog;
    
    public C_SIG (SIGFramee frame){
    this.frame =frame;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       
        String action = e.getActionCommand();
        
        System.out.println("welcome "+ action);
        switch(action){
        
            case "New Invoice":
                newInvoice();
               break;
               
               
            case"Create Invoice":
                addnewinvoice();
               break;  
               
               
            case"Cancel Invoice":
                cancelinvoice();
                break;
                
                
            case"Delete Invoice":
                deleteInvoice();
               break;
               
            
            case"New Line":
                newLine();
                break; 
                
          
            case"Create Line":
                createline();
                break;
                
            case"Cancel Line":
                cancelline();
                break;
                
            case"Delete Line":
                deleteLine();
                break;
                
            case"Load File":
            {
                try {
                    load();
                } catch (ParseException ex) {
                    Logger.getLogger(C_SIG.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(C_SIG.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;

                
            case"Save File":
                save();
                break;
        }
        
    }
    
    
    //Adding New Invoice Method
    private void newInvoice() {
        
        InvoiceHeaderDialog = new InvoiceHeaderDialog(frame);
        InvoiceHeaderDialog.setVisible(true); 
        try{
        int invoiceNO= 0;
        for(InvoiceHeader header :frame.getInvoiceHeaderArrayList()){
        if(header.getNum()>invoiceNO)
           invoiceNO = header.getNum();
         }
        invoiceNO++;
        InvoiceHeaderDialog.getNumLabel().setText("" + invoiceNO);
        
        }catch(Exception e){
        
            JOptionPane.showMessageDialog(frame,"Please select Headers & Lines files!", "Files Were Not Selected",JOptionPane.ERROR_MESSAGE);
            InvoiceHeaderDialog.setVisible(false);
           
        }
    }
     //adding new invoice to the invoices Table
     private void addnewinvoice() {
      int invoiceNO = 0;
      for(InvoiceHeader header : frame.getInvoiceHeaderArrayList()){
          if(header.getNum()>invoiceNO){
          invoiceNO = header.getNum();
          }
          invoiceNO++;
          String customername = InvoiceHeaderDialog.getCustomernameField().getText();
          String date = InvoiceHeaderDialog.getInvoicedateField().getText();
          Date invoicedate = new Date();
          try{
              invoicedate = df.parse(date);
             }catch(ParseException p){
              JOptionPane.showMessageDialog(frame, "Please use dd-MM-yyyy format! \n Using today!", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
             }
          if("".equals(customername)){
              JOptionPane.showMessageDialog(frame, "Please enter customer name!", "Invalid Customer Name", JOptionPane.ERROR_MESSAGE);
          }else{
              InvoiceHeader h=new InvoiceHeader(invoiceNO,invoicedate ,customername);
              frame.getInvoiceHeaderArrayList().add(h);
              frame.getHeaderTableModel().fireTableDataChanged();
              InvoiceHeaderDialog.dispose();
              InvoiceHeaderDialog = null;
              
          }
      }
    }
     
     
    private void cancelinvoice() {
         InvoiceHeaderDialog.dispose();
         InvoiceHeaderDialog= null;
    }
    
    //Deleting New Invoice Method
    private void deleteInvoice() {
    int selectedinvoiceIndex = frame.getInvoicesTable().getSelectedRow();
      if(selectedinvoiceIndex != -1){
          frame.getInvoiceHeaderArrayList().remove(selectedinvoiceIndex);
          frame.getHeaderTableModel().fireTableDataChanged();
          frame.getInvoiceLineTable().setModel(new LineTableModel(new ArrayList<InvoiceLine>()));
          frame.getNoLabel().setText("");
          frame.getDateLabel().setText("");
          frame.getCustomerNameLabel().setText("");
          frame.getTotalLabel().setText("");
          
      }
      
    }

    //Adding New Invoice Item Method
    private void newLine() {
         InvoiceLineDialog = new InvoiceLineDialog(frame);
         InvoiceLineDialog.setVisible(true);
         }

    private void createline() {
        InvoiceLineDialog.setVisible(false);
        int selectedInvoice = frame.getInvoicesTable().getSelectedRow();
        String invoiceName =InvoiceLineDialog.getNameField().getText();
        String invoicePrice=InvoiceLineDialog.getPriceField().getText();
        String invoiceCount=InvoiceLineDialog.getCountField().getText();
        double price =0;
        int count = 0;
        try{
           price = Double.parseDouble(invoicePrice);
           }catch(NumberFormatException exception){
               JOptionPane.showMessageDialog(frame, "Please enter a Number for Price!", "Invalid Price", JOptionPane.ERROR_MESSAGE);
           }try{
            count =Integer.parseInt(invoiceCount);
           }catch(NumberFormatException exception){
               JOptionPane.showMessageDialog(frame, "Please enter an Integer for Count!", "Invalid Count", JOptionPane.ERROR_MESSAGE);
           }
           if(selectedInvoice != -1 && InvoiceLineDialog !=null ){
             InvoiceHeader invoiceNO =frame.getInvoiceHeaderArrayList().get(selectedInvoice);
             InvoiceLine line = new InvoiceLine(invoiceNO ,invoiceName,price, count );
             frame.getLineArrayList().add(line);
             frame.getHeaderTableModel().fireTableDataChanged();
             frame.getInvoicesTable().setRowSelectionInterval(selectedInvoice, selectedInvoice);
             frame.getLineTableModel().fireTableDataChanged();
             InvoiceLineDialog.dispose();
             InvoiceLineDialog = null;
           
             
           }
        
       
    }

    private void cancelline() {
         InvoiceLineDialog.dispose();
         InvoiceLineDialog= null;
        
    }
    //Deleting One Invoice Item Method
    private void deleteLine() {
        int selectedInvoice = frame.getInvoicesTable().getSelectedRow();
        int selectedLine = frame.getInvoiceLineTable().getSelectedRow();
        if (selectedLine != -1){
        frame.getLineArrayList().remove(selectedLine);
        frame.getHeaderTableModel().fireTableDataChanged();
        frame.getInvoicesTable().setRowSelectionInterval(selectedInvoice, selectedInvoice);
        frame.getLineTableModel().fireTableDataChanged();
        
        
        }
        
        
      

    }

   
    //Saving File Method
    private void save() {
        JOptionPane.showMessageDialog(frame, "Select location for Headers file saving.","Save Headers", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser filechooser = new JFileChooser();
        try{
            int response = filechooser.showSaveDialog(frame);
            if(response == JFileChooser.APPROVE_OPTION){
                File headerFile = filechooser.getSelectedFile();
                FileWriter fileHeaderWriter = new FileWriter(headerFile);
                ArrayList<InvoiceHeader> headerArrayList= frame.getInvoiceHeaderArrayList();
                String headers= "";
                String lines= "";
                
                for(InvoiceHeader header :headerArrayList){
                    headers += header.toString();
                    headers += "\n";
                    for(InvoiceLine line :header.getLines()){
                        lines += line.toString();
                        lines += "\n";
                    }
                }
     JOptionPane.showMessageDialog(frame, "Select location for Lines file saving.","Save Lines", JOptionPane.INFORMATION_MESSAGE);            
     response = filechooser.showSaveDialog(frame);
     File lineFile = filechooser.getSelectedFile();
     FileWriter fileLineWriter = new FileWriter(lineFile);
     
     headers = headers.substring(0, headers.length()-1);
     fileHeaderWriter.write(headers);
     fileHeaderWriter.close();
     
     lines = lines.substring(0, lines.length()-1);
     fileLineWriter.write(lines);
     fileLineWriter.close();
     
     
     
     JOptionPane.showMessageDialog(frame,"Files Were Saved Successfully","Files Saved", JOptionPane.INFORMATION_MESSAGE);
     if(headerArrayList == null){
        throw new Exception();
     }
       }
    }catch(IOException exception){
    JOptionPane.showMessageDialog(frame, "can't save this file","Invalid File", JOptionPane.ERROR_MESSAGE);
    }catch(Exception exception){
    JOptionPane.showMessageDialog(frame, "Nothing to save \n New files were added. \n Please load it to continue", "No Data", JOptionPane.ERROR_MESSAGE);
    }
             
    }
        
        
        
    
    
    //Loading File Method
    public void load() throws ParseException, IOException {
        JOptionPane.showMessageDialog(frame, "Select Location for Headers file Loading.","Load Headers", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser filechooser = new JFileChooser();
        try{
            int response = filechooser.showOpenDialog(frame);
            if(response == JFileChooser.APPROVE_OPTION){
                File headerfile = filechooser.getSelectedFile();
                Path headerpath = Paths.get(headerfile.getAbsolutePath());
               
                ArrayList<InvoiceHeader> headers = new ArrayList<>();
                List<String> allheaders = Files.readAllLines(headerpath);
                
                for(String oneheader :allheaders){
                    String[] array = oneheader.split(",");
                    String col1 =array[0];
                    String col2 =array[1];
                    String col3 =array[2];
                    
                    int NO = Integer.parseInt(col1);
                    Date date= df.parse(col2);
                    String customername = col3;
                    InvoiceHeader header =new InvoiceHeader(NO,date, customername);
                    headers.add(header);
                }
                frame.setInvoiceHeaderArrayList(headers);
                
                
                JOptionPane.showMessageDialog(frame, "Select Location for Lines file Loading.","Load Lines", JOptionPane.INFORMATION_MESSAGE);
                response =filechooser.showOpenDialog(frame);
                if(response == JFileChooser.CANCEL_OPTION){
                     JOptionPane.showMessageDialog(frame, "CSV File for Line not Selected!", "No Lines File", JOptionPane.ERROR_MESSAGE);
                    
                }else if (response == JFileChooser.APPROVE_OPTION){
                    File linefile = filechooser.getSelectedFile();
                    Path linepath =Paths.get(linefile.getAbsolutePath());
                    
                    ArrayList<InvoiceLine> lines = new ArrayList<>();
                    List<String> alllines = Files.readAllLines(linepath);
                    for(String oneline :alllines){
                        String[]array = oneline.split(",");
                        String col1 =array[0];
                        String col2 =array[1];
                        String col3 =array[2];
                        String col4 =array[3];
                        
                        int NO = Integer.parseInt(col1);
                        String itemname=col2;
                        double itemprice =Double.parseDouble(col3);
                        int count = Integer.parseInt(col4);
                        InvoiceHeader h = frame.getNO(NO);
                        InvoiceLine line =new InvoiceLine(h ,itemname,itemprice,count);
                        h.getLines().add(line);
                   
                    }
                    frame.setLineArrayList(lines);
                }
                HeaderTableModel headerTable = new HeaderTableModel(headers);
                frame.setHeaderTableModel(headerTable);
                frame.getInvoicesTable().setModel(headerTable);
            }
            
        }catch (IOException exception) {
                JOptionPane.showMessageDialog(frame, "Could not open this file! \n Not a CSV file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException exception) {
                JOptionPane.showMessageDialog(frame, "Could not open this file \n Not correctly formatted.", "Invalid File", JOptionPane.ERROR_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException exception){
                JOptionPane.showMessageDialog(frame, "Could not open this file \n Not correctly formatted.", "Invalid File", JOptionPane.ERROR_MESSAGE);
                frame.getInvoiceLineTable().removeAll();
            }
    }


    
}
