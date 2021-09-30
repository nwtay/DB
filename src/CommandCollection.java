import java.util.HashMap;

public class CommandCollection {

    final HashMap<String, GeneralCommand> commandLog;

    public CommandCollection()
    {
        // in the hash map, we have the identifiers along with the commands;
        commandLog = new HashMap<>();
        commandLog.put("SELECT", new SelectCommand());
        commandLog.put("USE", new UseCommand());
        commandLog.put("CREATE", new CreateCommand());
        commandLog.put("DROP", new DropCommand());
        commandLog.put("ALTER", new AlterCommand());
        commandLog.put("INSERT", new InsertCommand());
        commandLog.put("UPDATE", new UpdateCommand());
        commandLog.put("DELETE", new DeleteCommand());
        commandLog.put("JOIN", new JoinCommand());
    }

    public GeneralCommand identifyCommand(String input)
    {
        return commandLog.get(input);
    }

}
