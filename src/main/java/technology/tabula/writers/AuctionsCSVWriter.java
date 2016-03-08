package technology.tabula.writers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;

import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;

/*
 * This class implements an algorithm for processing tables specific to
 * Australian Auction Results pdf file available at 
 * http://www.domain.com.au/auction-results
 * 
 */
public class AuctionsCSVWriter extends CSVWriter {
    
    
    private String city = "";
    private Date date = null;
    private String dateString="";
    

    public AuctionsCSVWriter() {
        super();
    }
    
    @Override
    public void write(Appendable out, Table table) throws IOException {
        
        this.createWriter(out);
        

        DateFormat format = new SimpleDateFormat("E d MMMM yyyy", Locale.ENGLISH);
        DateFormat outFormat = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<List<String>> rows = new ArrayList<List<String>>(); 
        
        
 
        for (List<RectangularTextContainer> row: table.getRows()) {
            List<String> cells = new ArrayList<String>(row.size());
            for (RectangularTextContainer tc: row) {
                cells.add(tc.getText());
            }
        
            StringBuilder builder = new StringBuilder();
            for(String s : cells)
            {
                builder.append(s);
            }
            String rowString = builder.toString();
            
            if(rowString.contains("Auction Results") && city.equals(""))
            {
                
                String[] split = rowString.split(" ");
                if(split.length >0)
                {
                    city = rowString.split(" Auction Results")[0];
                    continue;
                }
            }
            
            if(date == null && rowString.split(" ").length == 4)
            {
                //try to parse date
                try {
                    // remove the th, nd, st if they are next to a digit or two 
                    String dateRow = rowString.replaceFirst("(\\d\\d?)..", "$1");
                    date = format.parse(dateRow);
                    dateString = rowString;
                    continue;
                }catch (Exception e) {};
            }


            
            if(rowString.contains("Total Sales:") || rowString.contains("Median") || rowString.contains("sold after auction")  ) continue;
            
            if(rowString.contains("$") || rowString.contains("N/A"))
            {            
                ArrayList<String> cellsArray = new ArrayList<String>();
                cellsArray.add(city);
                
                
                if(date != null)
                {
                    cellsArray.add(outFormat.format(date));
                }
                else
                {
                    cellsArray.add("");
                }
                
                cellsArray.addAll(cells);
                
                rows.add(cellsArray); 
                
               
               
            }
            
            //merge rows broken to multiple lines into single line
            if(cells.size()==6 && cells.get(2).equals("") && cells.get(3).equals("") && cells.get(4).equals("")   )
            {
                
                if(rowString.contains("Results") || rowString.contains("duplex") || rowString.equals(dateString) || rowString.contains("Auction")) continue;
                
                for(int i=0; i<=5; i++)
                {
                    if(!cells.get(i).equals(""))
                    {
                      List<String> lastRow = rows.get(rows.size()-1);
                      lastRow.set(i+2, lastRow.get(i+2) + " " + cells.get(i));
                    }
                }
            }
            
        }
                
        for(List<String> row : rows)
        {
            this.printer.printRecord(row);
        }

        printer.flush();
    }



}
