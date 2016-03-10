package technology.tabula;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import technology.tabula.writers.AuctionsCSVWriter;

public class TestAuctionsCSVWriter {

    TableMock sampleTable = null;
    
    @Before
    public void SetUp()
    {
        sampleTable = new TableMock();
        sampleTable.addRow(new String[]{"Adelaide Auction Results","", "", "", "", ""});
        sampleTable.addRow(new String[]{"Saturday 5th March 2016", "", "", "", "", ""});
        sampleTable.addRow(new String[]{"Athelstone", "2 Christie Ct", "4 br h ", "$340,000", "S", "RW Brighton"});
    }
    
    @Test
    public void testRowThatIsNotAnAuctionRecord()
    {
        TableMock table = new TableMock();
        table.addRow(new String[]{"1", "2", "3", "4", "5", "6"});
        
        AuctionsCSVWriter writer = new AuctionsCSVWriter();
        StringBuilder output = new StringBuilder();
        
        try{ 
            writer.write(output, table);
        }catch(IOException e) {}
        
        assertEquals("", output.toString());
    }
    
    
    
    @Test
    public void testRowThatIsAValidAuctionRecordWithDateAndCity()
    {
         
        AuctionsCSVWriter writer = new AuctionsCSVWriter();
        StringBuilder output = new StringBuilder();
        
        try{ 
            writer.write(output, sampleTable);
        }catch(IOException e) {}
        
        assertEquals("Adelaide,05/03/2016,Athelstone,2 Christie Ct,\"4 br h \",\"$340,000\",S,RW Brighton", output.toString().trim());
    }
    

    
    @Test
    public void testRowBrokenDueToLongAgentNameAndAddress()
    {
      
        sampleTable.addRow(new String[]{"", "Address", "", "", "", "Agent"});       
        
        AuctionsCSVWriter writer = new AuctionsCSVWriter();
        StringBuilder output = new StringBuilder();
        
        try{ 
            writer.write(output, sampleTable);
        }catch(IOException e) {}
        
        assertEquals("Adelaide,05/03/2016,Athelstone,2 Christie Ct Address,\"4 br h \",\"$340,000\",S,RW Brighton Agent", output.toString().trim());
    }
    

}
