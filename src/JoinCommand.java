public class JoinCommand extends GeneralCommand {

    public JoinCommand()
    {
        this.name = "Join";
        this.mandatoryKeywords.add("JOIN");
        this.mandatoryKeywords.add("AND");
        this.mandatoryKeywords.add("ON");
        this.allKeywords.add("JOIN");
        this.allKeywords.add("AND");
        this.allKeywords.add("ON");
        this.minNumParameters = 8;
    }

    public void checkValidity() throws Exception
    {
        checkDBSelected(currentDB);
        JoinParse joinParse = new JoinParse(userCommand);
        validCommand = joinParse.isValidJoin(this.currentDB);
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            // does the first table named correspond to first attribute named?
            String table1Name = userCommand.get(1);
            String table2Name = userCommand.get(3);
            String table1Attribute = userCommand.get(5);
            String table2Attribute = userCommand.get(7);

            DataReader dataReader1 = new DataReader(currentDB, table1Name);
            Table table1 = dataReader1.getTable();

            DataReader dataReader2 = new DataReader(currentDB, table2Name);
            Table table2 = dataReader2.getTable();

            Table resultTable = table1.joinTable(table2, table1Attribute, table2Attribute);
            this.resultString = resultTable.toString();
        }
    }
}


