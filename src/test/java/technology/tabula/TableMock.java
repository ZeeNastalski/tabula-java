package technology.tabula;

import java.util.ArrayList;
import java.util.List;

public class TableMock extends Table {

    List<List<CellMock>> rows = new ArrayList<List<CellMock>>();
    
   
    public TableMock()
    {
        
    }
    
    public void addRow(String[] row)
    {
        ArrayList<CellMock> tableRow = new ArrayList<CellMock>();
        
        for(String value: row)
        {
            CellMock cell = new CellMock(value);
            tableRow.add(cell);
        }
        
        rows.add(tableRow);       
    }
    
    @Override
    public List<List<RectangularTextContainer>> getRows() {
        return (List<List<RectangularTextContainer>>)(List<?>) rows;
    }
    
    
}
