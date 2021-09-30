public class UseCommand extends GeneralCommand {

    public UseCommand()
    {
        this.name = "Use";
        this.mandatoryKeywords.add("USE");
        this.allKeywords.add("USE");
        this.minNumParameters = 2;
    }

    public void checkValidity() throws Exception
    {
        UseParse useParse = new UseParse(userCommand);
        validCommand = useParse.isUse();
    }

    public void executeCommand() throws Exception
    {
        if(validCommand){
            currentDB = new Database(userCommand.get(1));
        }
    }

}
