import java.io.*;

public class FileIO {
    private static String workdingDir = System.getProperty("user.dir") + "\\";
    private static String folderAddress = workdingDir + "src\\database\\";
    private static String filename = "database.ser";


    // save the data by serializing the database
    public static void save(DataBase database) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(database);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        moveToFolder(); // put the serialized database to folder 'database'
    }


    // Read in the data from the serialized database file to create the DataBase class
    public static DataBase read() {
        // check if the address for database.ser is existed
        // if it is, then we read data from it for our DataBase class
        if (new File(folderAddress + filename).exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(folderAddress + filename);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                DataBase database = (DataBase) objectIn.readObject();
                objectIn.close();
                return database;
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // if address was not existed, create a new database
        return new DataBase();
    }


    // move the ser file created to folder 'database'
    private static void moveToFolder() {
        String root = workdingDir + filename;
        String target = folderAddress + filename;
        
        // Create the folder if it was not created
        File folder = new File(folderAddress);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                System.out.println("Failed to create directory for data");
            }
        }
        // move the ser file to the data folder
        File file = new File(root);
        File targetFile = new File(target);
        // if the file exists then delete and resave it
        if (targetFile.exists()) {
            targetFile.delete();
        }
        if (file.renameTo(targetFile)) {
            file.delete();
        }
        else {
            System.out.println("Failed to move " + filename + " to folder");
        }
    }
}