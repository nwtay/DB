import java.util.ArrayList;

public class DeleteCommand extends GeneralCommand {

    private Table table;
    private ArrayList<String> conditionStatement;
    private DeleteParse deleteParse;
    private Table subTable;

    public DeleteCommand()
    {
        this.name = "Delete";
        this.mandatoryKeywords.add("DELETE");
        this.mandatoryKeywords.add("FROM");
        this.mandatoryKeywords.add("WHERE");
        this.allKeywords.add("WHERE");
        this.allKeywords.add("FROM");
        this.allKeywords.add("DELETE");
        this.allKeywords.add("AND");
        this.allKeywords.add("OR");
        this.allKeywords.add("LIKE");
        this.minNumParameters = 7;
    }

    public void checkValidity() throws Exception
    {
        checkDBSelected(currentDB);
        deleteParse = new DeleteParse(this.currentDB, userCommand);
        validCommand = deleteParse.isDelete();
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            DataReader dataReader = new DataReader(currentDB, userCommand.get(2));
            table = dataReader.getTable();
            conditionStatement = deleteParse.getConditionStatement();
            getSubTable();

            for(int i = 0; i < subTable.getNumberOfRows(); i++){
                Row r = subTable.getRow(i);
                table.removeRow(r.getCell(0).toString());
            }

            String filePath = "Databases/" + this.currentDB.getName() + "/" + table.getName();
            DataWriter dataWriter = new DataWriter();
            dataWriter.writeData(filePath, table);
        }
    }

    private void getSubTable() throws Exception
    {
        subTable = table.getSubTable(conditionStatement.get(1), conditionStatement.get(0), conditionStatement.get(2));
    }

}
