import java.util.ArrayList;

public class Table {

    final String name;
    final ArrayList<Row> rowList;
    final ArrayList<Column> colList;
    private Row updatedRow;
    protected String returnString;

    public Table(String name, ArrayList<Column> colList) throws Exception
    {
        this.name = name;
        checkName(name);
        rowList = new ArrayList<>();
        this.colList = colList;
    }

    // makes a combine column header for two tables (this table, and table2)
    private void joinColumns(ArrayList<Column> newColList, Table table2)
    {
        Column newCol;
        for(int i = 1; i < getNumberOfColumns(); i++)
        {
            String newColName = getName() + "." + colList.get(i).toString();
            newCol = new Column(newColName);
            newColList.add(newCol);
        }
        // start from x = 1 since generating our own id column, so ignore table2's id col
        for(int x = 1; x < table2.getNumberOfColumns(); x++)
        {
            String newColName = table2.getName() + "." + table2.colList.get(x).toString();
            newCol = new Column(newColName);
            newColList.add(newCol);
        }
    }

    // joins two tables - join command
    public Table joinTable(Table table2, String thisAttribute, String table2Attribute) throws Exception
    {
        // identify the index of the columns for which we need to join
        int indexAttr1 = getColumnIndex(thisAttribute);
        int indexAttr2 = table2.getColumnIndex(table2Attribute);

        ArrayList<Column> newColList = new ArrayList<>();
        Column newCol = new Column("id");
        newColList.add(newCol);
        joinColumns(newColList, table2);
        Table newTable = new Table("newTab", newColList);

        for(int leftRow = 0; leftRow < getNumberOfRows(); leftRow++)
        {
            String leftCell = rowList.get(leftRow).getCell(indexAttr1).toString();
            for(int rightRow = 0; rightRow < table2.getNumberOfRows(); rightRow++)
            {
                String rightCell = table2.rowList.get(rightRow).getCell(indexAttr2).toString();
                if(leftCell.equals(rightCell)){
                    addJoinedRow(newTable, rowList.get(leftRow), table2.rowList.get(rightRow));
                }
            }
        }
        return newTable;
    }

    // adds a row from two joined tables
    private void addJoinedRow(Table newTable, Row rowLeftTable, Row rowRightTable)
    {
        ArrayList<Cell> newCellList = new ArrayList<>();
        for(int i = 1; i < getNumberOfColumns(); i++)
        {
            Cell currCell = rowLeftTable.getCell(i);
            newCellList.add(currCell);
        }

        Row newRow = new Row(newCellList);
        // removing the id cell from the original table
        for(int i = 1; i < rowRightTable.getRowLength(); i++){
            newRow.addCellEnd(rowRightTable.getCell(i));
        }
        newTable.insertRow(newRow);
    }


    private int getColumnIndex(String columnName)
    {
        for(int i = 0; i < getNumberOfColumns(); i++)
        {
            if(colList.get(i).toString().equals(columnName)){
                return i;
            }
        }
        return -1;
    }

    public String getCell(int rowIndex, int colIndex)
    {
        return rowList.get(rowIndex).getCell(colIndex).toString();
    }

    public void replaceRow(String rowId, Row inRow)
    {
        Row outRow;
        for(int i = 0; i < getNumberOfRows(); i++)
        {
            if(rowList.get(i).getCell(0).toString().equals(rowId)){
                outRow = rowList.get(i);
                for(int j = 0; j < colList.size(); j++)
                {
                    Cell newCell = inRow.getCell(j);
                    outRow.changeCell(j, newCell.toString());
                }
            }
        }
    }

    public void removeRow(String rowId)
    {
        rowList.removeIf(row -> row.getCell(0).toString().equals(rowId));
    }

    public void updateCell(String columnName, String newValue)
    {
        // get the index of the column in which we need to change the cells
        int columnIndex = - 1;
        for(int i = 0; i < colList.size(); i++)
        {
            if(colList.get(i).toString().equals(columnName)){
                columnIndex = i;
            }
        }
        if(columnIndex > -1){
            for(int j = 0; j < getNumberOfRows(); j++)
            {
                updatedRow = getRow(j);
                updatedRow.changeCell(columnIndex, newValue);
            }
        }
    }

    public Row getUpdatedRow()
    {
        return updatedRow;
    }


    // gets a row in a column that satisfies a plain unbracketed condition
    public Table getSubTable(String operator, String columnName, String valueRight) throws Exception
    {
        Table subTable = new Table(name, colList);
        OperatorParse operatorParse = new OperatorParse(operator);
        Value rightValue = new Value(valueRight);

        for(int y = 0; y < getNumberOfRows(); y++){
            for(int x = 0; x < getNumberOfColumns(); x++){
                if(getColumn(x).toString().equals(columnName)){
                    Cell cell = new Cell(getCell(y, x));
                    Value leftValue = new Value(cell.toString());
                    // if the operation returns true for this cell, add it to sub table
                    if(validLikeCondition(operatorParse, leftValue, rightValue)){
                        if(leftValue.getValueType() == rightValue.getValueType()){
                            if(operatorParse.doOperation(leftValue, rightValue)){
                                Row row = getRow(y);
                                subTable.readRow(row);
                            }
                        }
                        else{
                            String errMsg = "Couldn't convert " + leftValue.getValueType() + " to " + rightValue.getValueType();
                            throw new Exception(errMsg);
                        }
                    }
                    else{
                        throw new Exception("LIKE conditions must be only used with string literals!");
                    }
                }
            }
        }

       return subTable;
    }

    private boolean validLikeCondition(OperatorParse operatorParse, Value leftValue, Value rightValue) throws Exception
    {
        if(operatorParse.getInput().equals("LIKE")){
            return leftValue.getValueType() == ValueType.STRINGLITERAL && rightValue.getValueType() == ValueType.STRINGLITERAL;
        }
        else{
            return true;
        }
    }

    public void readRow(Row row)
    {
        rowList.add(row);
    }

    public void insertRow(Row r)
    {
        ArrayList<Cell> cellList = new ArrayList<>();
        int id = 0;

        if(rowList.size() == 0){
            id = 1;
        }
        if(rowList.size() > 0){
            // at one to previous rows id
            id = getIdNewRow() + 1;
        }

        Cell idCell = new Cell(String.valueOf(id));
        cellList.add(idCell);

        for(int i = 0; i < r.getCellList().size(); i++){
            cellList.add(r.getCell(i));
        }

        Row rowWithId = new Row(cellList);
        rowList.add(rowWithId);
    }

    private int getIdNewRow()
    {
        Row row = getRow(getNumberOfRows() - 1);
        return Integer.parseInt(row.getCell(0).toString());
    }

    public void addRowArray(ArrayList<Row> rowArray)
    {
        for (Row row : rowArray) {
            readRow(row);
        }
    }

    public Column getColumn(int index)
    {
        if(colList.size() > 0){
            if(colList.get(index) != null)
            {
                return colList.get(index);
            }
        }
        return null;
    }

    public Row getRow(int rowIndex)
    {
        // shall i return an exception here if user enters bad index??
        if(rowIndex < getNumberOfRows())
        {
            return rowList.get(rowIndex);
        }
        return null;
    }

    public int getNumberOfRows()
    {
        return rowList.size();
    }

    public String getName()
    {
        return name;
    }

    private void checkName(String name) throws Exception
    {
        if(name.isEmpty())
        {
            throw new Exception();
        }
    }

    public String columnHeaderLine()
    {
        StringBuilder string = new StringBuilder();
        int i = 0;
        while(i < colList.size())
        {
            string.append(colList.get(i).toString());
            string.append("\t");
            i++;
        }
        return string.toString();
    }

    public boolean columnExists(String colName)
    {
        if(colList != null){
            for (Column column : colList) {
                if (column.toString().equals(colName)) {
                    return true;
                }
            }
        }
        return false;
    }


    public void addColumn(String newColName)
    {
        Column newCol = new Column(newColName);
        colList.add(newCol);

        Cell emptyCell = new Cell(" ");

        for(int i = 0; i < getNumberOfRows(); i++)
        {
            Row originalRow = rowList.get(i);
            originalRow.addCellEnd(emptyCell);
        }
    }

    public void removeColumn(String colName)
    {
        int indexToDrop = -1;
        for(int i = 0; i < colList.size(); i++)
        {
            if(colList.get(i).toString().equals(colName)){
                indexToDrop = i;
                colList.remove(indexToDrop);
            }
        }

        for(int i = 0; i < getNumberOfRows(); i++)
        {
            Row originalRow = rowList.get(i);
            originalRow.removeCell(indexToDrop);
        }
    }

    // gets result table from two sub tables in an AND condition
    public void andResultTable(Table rightTable)
    {
        // loop through, if the right table and left table share a row
        // then keep it in this table. If not remove the row

        for(int leftRow = 0; leftRow < getNumberOfRows(); leftRow++){
            for(int rightRow = 0; rightRow < rightTable.getNumberOfRows(); rightRow++){
                if(!rightTable.rowList.contains(rowList.get(leftRow))){
                    rowList.remove(leftRow);
                }
            }
        }
    }

    // gets result table from two sub tables in an OR condition
    public void orResultTable(Table rightTable)
    {
        // loop through, if the row in the right table is not found
        // in this table (left table), add it to left table

        for(int leftRow = 0; leftRow < getNumberOfRows(); leftRow++){
            for(int rightRow = 0; rightRow < rightTable.getNumberOfRows(); rightRow++){
                if(!rowList.contains(rightTable.rowList.get(rightRow))){
                    rowList.add(rightTable.getRow(leftRow));
                }
            }
        }
    }

    public int getNumberOfColumns()
    {
        return colList.size();
    }


    public String toString()
    {
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < getNumberOfColumns(); i++)
        {
            string.append(colList.get(i).toString());
            string.append("\t");
        }
        for(int y = 0; y < getNumberOfRows(); y++){
            string.append("\n");
            for(int x = 0; x < getNumberOfColumns(); x++){
                string.append(rowList.get(y).getCell(x).getPrintData());
                string.append("\t");
            }
        }
        returnString = string.toString();
        return returnString;
    }

    public String subTableString(ArrayList<String> attributeList)
    {
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < getNumberOfColumns(); i++)
        {
            if(attributeList.contains(colList.get(i).toString())){
                string.append(colList.get(i).toString());
                string.append("\t");
            }
        }
        for(int y = 0; y < getNumberOfRows(); y++){
            string.append("\n");
            for(int x = 0; x < getNumberOfColumns(); x++){
                if(attributeList.contains(colList.get(x).toString())){
                    string.append(rowList.get(y).getCell(x).getPrintData());
                    string.append("\t");
                }
            }
        }
        returnString = string.toString();
        return returnString;
    }
}
