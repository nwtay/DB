import java.io.*;
import java.net.*;
import java.security.spec.ECField;
import java.util.*;
import java.lang.System;

class DBServer
{
    private MainSystem mainSystem;

    public DBServer(int portNumber)
    {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            while(true) processNextConnection(serverSocket);
        } catch(IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processNextConnection(ServerSocket serverSocket)
    {
        try {
            mainSystem = new MainSystem();
            Socket socket = serverSocket.accept();
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Connection Established");
            while(true) processNextCommand(socketReader, socketWriter);
        } catch(IOException ioe) {
            System.err.println(ioe);
        } catch(NullPointerException npe) {
            System.out.println("Connection Lost");
        }
    }

    private void processNextCommand(BufferedReader socketReader, BufferedWriter socketWriter) throws IOException, NullPointerException
    {
        String incomingCommand = socketReader.readLine();

        // catch exceptions and store response (tables as strings + [OK]/[ERROR] in here)

        try{
            mainSystem.nextCommand(incomingCommand);
            if(mainSystem.getResultString().length()>0){
                socketWriter.write("[OK]\n" + mainSystem.getResultString());
            }
            else{
                socketWriter.write("[OK]");
            }
            socketWriter.write("\n" + ((char)4) + "\n");
            socketWriter.flush();
        }
        catch (Exception E)
        {
            socketWriter.write("[ERROR]: " + E.toString());
            socketWriter.write("\n" + ((char)4) + "\n");
            socketWriter.flush();
        }
    }

    public static void main(String args[])
    {
        DBServer server = new DBServer(8888);
    }

}
