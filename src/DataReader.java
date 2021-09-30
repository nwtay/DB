import java.lang.System;
import java.io.*;
import java.util.ArrayList;

public class DataReader {

    final ArrayList<String[]> fileText = new ArrayList<>();
    final FileReader reader;
    final BufferedReader buffReader;
    final String fileName;
    private int numberOfColumns;
    private File file;
    final ArrayList<Column> colList;
    private Database thisDataBase;

    public DataReader(Database thisDataBase, String fileName) throws Exception
    {
        this.fileName = fileName;
        checkInputString(fileName);
        checkDataBaseExists(thisDataBase.getName());
        this.thisDataBase = thisDataBase;
        checkFileExists(fileName);
        this.thisDataBase = thisDataBase;
        checkFilePermissions(file);
        reader = new FileReader(file);
        buffReader = new BufferedReader(reader);
        colList = new ArrayList<>();
        readLines();
    }

    public void checkFileExists(String fileName) throws Exception
    {
        String path = "Databases/" + thisDataBase.getName() + "/" + fileName;
        file = new File(path);
        if(file.exists()){
            if(!file.isFile())
            {
                throw new Exception("This is not a File");
            }
        }
    }

    private void checkDataBaseExists(String databaseName) throws Exception
    {
        String path = "Databases/" + databaseName;
        File file = new File(path);
        if(!file.exists()){
            throw new Exception("This database does not exist.");
        }
    }

    private void lineToColumnList()        // convert string array to column list
    {
        String[] stringArray = fileText.get(0);
        for(int i = 0; i < stringArray.length; i++){
            Column c = new Column(stringArray[i]);
            colList.add(i, c);
        }
    }

    public Table getTable() throws Exception
    {
        ArrayList<Column> columnList = getColumnHeader();
        Table table = new Table(fileName, columnList);
        table.addRowArray(linesToRows());
        return table;
    }

    public ArrayList<Column> getColumnHeader()
    {
        lineToColumnList();
        return colList;
    }

    private Row arrayToRow(String[] array)
    {
        Row row = new Row();
        for (String stringAtIndex : array) {
            Cell cell = new Cell(stringAtIndex);
            row.addCellEnd(cell);
        }
        return row;
    }

   public ArrayList<Row> linesToRows()
    {
        ArrayList<Row> rowArray = new ArrayList<>();
        //i cant be 0 as used this row for column header
        for(int i = 1; i < fileText.size(); i++)
        {
            String[] stringArray = fileText.get(i);
            rowArray.add(arrayToRow(stringArray));
        }
        return rowArray;
    }

    private void readLines() throws Exception
    {
        String lineChecker;
        String[] stringArray;
        int row = 0;
        while((lineChecker = buffReader.readLine()) != null) {
            stringArray = lineChecker.split("\t");
            if(row == 0){
                numberOfColumns = stringArray.length;
            }
            checkStringArr(stringArray);
            fileText.add(stringArray);
            row++;
        }
        buffReader.close();
    }

    public void checkStringArr(String[] stringArray) throws Exception
    {
        if(stringArray.length > numberOfColumns)
        {
            for (String stringAtIndex : stringArray) {
                System.out.print(stringAtIndex + " ");
            }

            throw new Exception("stringArray.length, numberOfColumns");
        }
    }

    public void checkInputString(String fileName) throws Exception
    {
        if(fileName.isEmpty()){
            throw new Exception("Empty Filename");
        }
        // Check path from input string????
    }

    public void checkFilePermissions(File file) throws Exception
    {
        if(!file.canRead())
        {
            throw new Exception("Cannot Read From This File");
        }
    }

}
