package technology.tabula;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class CellMock extends Cell  {

    private String text = "";
    
    
    public CellMock(String text)
    {
        super(0,0,0,0);
        this.text = text;
    }
    
    @Override
    public String getText()
    {
        return this.text;
    }
}
