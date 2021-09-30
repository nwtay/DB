import java.io.*;

public class DataWriter {

    private BufferedWriter buffWriter;
    private Table table;

    public DataWriter()
    {
    }

    public void writeData(String filePath, Table table) throws Exception
    {
        FileWriter writer;
        File file = new File(filePath);

        if(!file.exists()) {
            checkFileCreation(file);
        }
        writer = new FileWriter(file);
        buffWriter = new BufferedWriter(writer);
        this.table = table;
        writeTable();
        buffWriter.close();
    }

    public void writeTable() throws Exception
    {
        buffWriter.write(table.columnHeaderLine());
        buffWriter.newLine();
        for(int i = 0; i < table.getNumberOfRows(); i++){
            String s = table.getRow(i).toString();
            buffWriter.write(s);
            buffWriter.newLine();
        }
    }

    public void checkFileCreation(File file) throws Exception
    {
        if(!file.createNewFile())
        {
            throw new Exception("Failed to Create File");
        }
    }

}



