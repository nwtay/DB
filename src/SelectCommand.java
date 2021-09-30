import java.util.ArrayList;

public class SelectCommand extends GeneralCommand {

    private SelectParse selectParse;
    private Table table;
    private Table subTable;
    private ArrayList<String> conditionStatement;

    public SelectCommand()
    {
        this.name = "Select";
        this.mandatoryKeywords.add("SELECT");
        this.mandatoryKeywords.add("FROM");
        this.allKeywords.add("SELECT");
        this.allKeywords.add("FROM");
        this.allKeywords.add("WHERE");
        this.allKeywords.add("AND");
        this.allKeywords.add("OR");
        this.allKeywords.add("LIKE");
        this.minNumParameters = 4;
    }

    public void checkValidity() throws Exception
    {
        checkDBSelected(currentDB);
        selectParse = new SelectParse(currentDB, userCommand);
        validCommand = selectParse.isSelect();
    }

    public void executeCommand() throws Exception
    {
        ArrayList<String> wildAttrList;
        if(validCommand){
            wildAttrList = selectParse.getWildAttrList();

            if(wildAttrList.get(0).equals("*")){
                getWholeTable();
            }

            else{
                getSubSelectedTable(wildAttrList);
            }
        }
    }

    private void getWholeTable() throws Exception
    {
        DataReader dataReader = new DataReader(currentDB, userCommand.get(3));
        table = dataReader.getTable();
        if(userCommand.size() == 4){
            // whole table, no condition
            this.resultString = table.toString();
        }
        else{
            // whole table, with condition
            getSubTable();
            this.resultString = subTable.toString();
        }
    }

    private void getSubSelectedTable(ArrayList<String> wildAttrList) throws Exception
    {
        int indexTableName = wildAttrList.size() + 2;   // gets the index of command where table name is specified
        DataReader dataReader = new DataReader(currentDB, userCommand.get(indexTableName));
        table = dataReader.getTable();
        conditionStatement = selectParse.getConditionStatement();

        if(conditionStatement != null)
        {
            // gets specific cols of table with condition applied
            getSubTable();
            this.resultString = subTable.subTableString(wildAttrList);
        }
        else{
            // gets specific cols of table with no condition applied
            this.resultString = table.subTableString(wildAttrList);
        }
    }

    private void getSubTable() throws Exception
    {
        // this method stores the sub table based on the condition statement
        conditionStatement = selectParse.getConditionStatement();
        if(conditionStatement != null){
            if(conditionStatement.size() > 3){
                // For each condition, left and right, of the main conjunction (from condition class)
                // we get a subTable (recursively) and then join the left and right with an AND/OR join
                subTable = selectParse.getResultTable();
            }
            else{
                subTable = table.getSubTable(conditionStatement.get(1), conditionStatement.get(0), conditionStatement.get(2));
            }
        }
    }

}
