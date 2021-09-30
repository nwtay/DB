

public class Cell {

    private String cellData;

    public Cell(String data)
    {
        this.cellData = data;
    }

    public String toString()
    {
        return cellData;
    }

    public void updateCell(String data)
    {
        this.cellData = data;
    }

    // same as get data, but prints string literals without the punctuation marks
    public String getPrintData()
    {
        if(cellData.charAt(0) == '\''){
            return cellData.substring(1, cellData.length()-1);
        }
        return cellData;
    }
}
