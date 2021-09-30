import java.util.ArrayList;

public class UpdateCommand extends GeneralCommand {

    private ArrayList<String> conditionStatement;
    private UpdateParse updateParse;
    private Table table;
    private Table subTable;

    public UpdateCommand()
    {
        this.name = "Update";
        this.mandatoryKeywords.add("UPDATE");
        this.mandatoryKeywords.add("SET");
        this.mandatoryKeywords.add("WHERE");
        this.allKeywords.add("WHERE");
        this.allKeywords.add("SET");
        this.allKeywords.add("UPDATE");
        this.allKeywords.add("AND");
        this.allKeywords.add("OR");
        this.allKeywords.add("LIKE");
        this.minNumParameters = 8;
    }

    public void checkValidity() throws Exception
    {
        checkDBSelected(currentDB);
        updateParse = new UpdateParse(this.currentDB, userCommand);
        validCommand = updateParse.isUpdate();
    }

    public void executeCommand() throws Exception
    {
        Row updatedRow;
        ArrayList<String> nameValueList;
        if(validCommand){
            DataReader dataReader = new DataReader(currentDB, userCommand.get(1));
            table = dataReader.getTable();
            conditionStatement = updateParse.getConditionStatement();
            nameValueList = updateParse.getNameValueList();
            getSubTable();
            subTable.updateCell(nameValueList.get(0), nameValueList.get(2));
            updatedRow = subTable.getUpdatedRow();
            // if there was a row that matched the condition, update the correct row in the table
            if(updatedRow != null){
                table.replaceRow(updatedRow.getCell(0).toString(), updatedRow);
                String filePath = "Databases/" + this.currentDB.getName() + "/" + table.getName();
                DataWriter dataWriter = new DataWriter();
                dataWriter.writeData(filePath, table);
            }
        }
    }

    private void getSubTable() throws Exception
    {
        subTable = table.getSubTable(conditionStatement.get(1), conditionStatement.get(0), conditionStatement.get(2));
    }
}
