import java.util.ArrayList;

public class JoinParse {

    final ArrayList<String> strArr;
    private int currentIndex;
    private Table table1;
    private Table table2;
    private Database thisDataBase;

    public JoinParse(ArrayList<String> strArr)
    {
        this.strArr = strArr;
        currentIndex = 0;
    }

    public boolean isValidJoin(Database inputDataBase) throws Exception
    {
        if(strArr.get(currentIndex).equals("JOIN")){
            this.thisDataBase = inputDataBase;
            currentIndex++;
            checkTableName(1);
            currentIndex++;
            checkCommand();
            currentIndex++;
            checkTableName(2);
            currentIndex++;
            checkCommand();
            currentIndex++;
            checkAttributeName(table1);
            currentIndex++;
            checkCommand();
            currentIndex++;
            checkAttributeName(table2);
            checkCommandLength();
            return true;
        }
        return false;
    }

    private void checkCommandLength() throws Exception
    {
        if(strArr.size() > 8){
            throw new Exception("Too many terms for Join Command.");
        }
    }

    private void checkTableName(int tableNumber) throws Exception
    {
        if(!thisDataBase.tableNameExists(strArr.get(currentIndex))){
            throw new Exception("Table doesn't exist in this database.");
        }
        if(tableNumber == 1){
            DataReader dataReader = new DataReader(thisDataBase, strArr.get(currentIndex));
            table1 = dataReader.getTable();
        }
        if(tableNumber == 2){
            DataReader dataReader2 = new DataReader(thisDataBase, strArr.get(currentIndex));
            table2 = dataReader2.getTable();
        }
    }

    private void checkAttributeName(Table table) throws Exception
    {
        if(!table.columnExists(strArr.get(currentIndex))){
            String errMsg = ": Attribute " + strArr.get(currentIndex) + " doesn't exist in this table: " + table.getName();
            throw new Exception(errMsg);
        }
    }

    private void checkCommand() throws Exception
    {
        if(currentIndex == 2 || currentIndex == 6){
            if(!strArr.get(currentIndex).equals("AND")){
                throw new Exception();
            }
        }

        if(currentIndex == 4){
            if(!strArr.get(currentIndex).equals("ON")){
                throw new Exception();
            }
        }
    }

}
