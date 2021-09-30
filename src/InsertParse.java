import java.util.ArrayList;

public class InsertParse {

    final ArrayList<String> strArr;
    private int currentIndex;
    private ValueList valueList;
    private Database inputDatabase;
    private ArrayList<String> subArray;

    public InsertParse(ArrayList<String> strArr)
    {
        this.strArr = strArr;
        currentIndex = 0;
    }

    public boolean isInsert(Database inputDatabase) throws Exception
    {
        if(strArr.get(currentIndex).equals("INSERT")){
            this.inputDatabase = inputDatabase;
            currentIndex++;
            checkCommand();
            currentIndex++;
            checkTableName(inputDatabase);
            currentIndex++;
            checkCommand();
            currentIndex++;
            checkCommand();
            // first check that the list ends in )
            // if it does, make substring up to this index
            checkListEnd();
            checkListSize();
            return true;
        }
        return false;
    }

    private void checkListSize() throws Exception
    {
        DataReader dataReader = new DataReader(inputDatabase, strArr.get(2));
        Table table = dataReader.getTable();
        // subtract 1, as can't insert into id column
        int maxSize = table.getNumberOfColumns() - 1;
        int numberValues = 0;

        for (String s : subArray) {
            if (!s.equals(",")) {
                numberValues++;
            }
        }

        if(numberValues > maxSize){
            throw new Exception("Trying to insert too many values for one row in this table.");
        }
    }

    private void checkListEnd() throws Exception
    {
        int indexLast = strArr.size() - 1;
        if(strArr.get(indexLast).equals(")")){
            currentIndex++;
            subArray = new ArrayList<>(strArr.subList(currentIndex, indexLast));
            valueList = new ValueList(subArray);
            if(!valueList.isValueList()){
                throw new Exception("Invalid Value list!");
            }
        }
        else{
            throw new Exception("Expected ) at end of Insert statement.");
        }
    }

    public ArrayList<String> getPlainValueList()
    {
        return valueList.getPlainValueList();
    }


    private void checkTableName(Database database) throws Exception
    {
        if(!database.tableNameExists(strArr.get(currentIndex))){
            throw new Exception(strArr.get(currentIndex) + " doesn't exist in this database!");
        }
    }


    private void checkCommand() throws Exception
    {
        if(currentIndex == 1)
        {
            if(!strArr.get(currentIndex).equals("INTO")){
                throw new Exception("Expected INTO");
            }
        }

        if(currentIndex == 3)
        {
            if(!strArr.get(currentIndex).equals("VALUES")){
                throw new Exception("Expected VALUES");
            }
        }

        if(currentIndex == 4)
        {
            if(!strArr.get(currentIndex).equals("(")){
                throw new Exception("Expected (");
            }
        }

    }
}
