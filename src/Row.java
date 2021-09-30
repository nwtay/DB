import java.util.ArrayList;

public class Row {

    final ArrayList<Cell> cellList;

    public Row(ArrayList<Cell> cellList)
    {
        this.cellList = cellList;
    }

    public Row()
    {
        cellList = new ArrayList<>();
    }

    public void addCellEnd(Cell cell)
    {
        cellList.add(cell);
    }

    public Cell getCell(int index)
    {
        return cellList.get(index);
    }

    public ArrayList<Cell> getCellList()
    {
        return cellList;
    }

    public String toString()
    {
        StringBuilder string = new StringBuilder();
        int i = 0;
        while(i < cellList.size())
        {
            string.append(cellList.get(i));
            string.append("\t");
            i++;
        }
        return string.toString();
    }

    public int getRowLength()
    {
        return cellList.size();
    }

    public void removeCell(int columnIndex)
    {
        cellList.remove(columnIndex);
    }

    public void changeCell(int index, String newValue)
    {
        Cell cell = getCell(index);
        cell.updateCell(newValue);
    }

}
