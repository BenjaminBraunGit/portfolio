package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/** This is the staging manager.
 *  @author Benjamin B.
 */
public class Staging {

    /** The add folder. */
    static final File ADD_FOLDER = Main.STAGING_ADD_FOLDER;
    /** The remove folder. */
    static final File REMOVE_FOLDER = Main.STAGING_REMOVE_FOLDER;

    /** Adds FILENAME to the HEAD commit. */
    public static void add(String fileName, Commit head) {
        File workingFile = new File(fileName);
        if (!workingFile.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        File stagingAddFile = new File(ADD_FOLDER + "/" + fileName);
        File stagingRemoveFile = new File(REMOVE_FOLDER + "/" + fileName);

        if (stagingRemoveFile.exists()) {
            stagingRemoveFile.delete();
        }

        if (head.getBlobHashes().containsKey(fileName)
                && BlobManager.getFileHash(workingFile)
                .equals(head.getBlobHashes().get(fileName))) {
            if (stagingAddFile.exists()) {
                stagingAddFile.delete();
            }
        } else {
            try {
                Files.copy(workingFile.toPath(),
                        stagingAddFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Unable to add file.");
                return;
            }
        }
    }

    /** Removes FILENAME from the HEAD commit. */
    public static void remove(String fileName, Commit head) {

        File workingFile = new File(fileName);
        File stagingAddFile = new File(ADD_FOLDER + "/" + fileName);
        File stagingRemoveFile = new File(REMOVE_FOLDER + "/" + fileName);

        if (!head.getBlobHashes().containsKey(fileName)
                && !stagingAddFile.exists()) {
            System.out.println("No reason to remove the file.");
            return;
        }

        if (stagingAddFile.exists()) {
            stagingAddFile.delete();
        }

        if (head.getBlobHashes().containsKey(fileName)
                && !stagingRemoveFile.exists()) {
            try {
                stagingRemoveFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to add dummy remover");
            }
        }

        if (workingFile.exists()
                && head.getBlobHashes().containsKey(fileName)) {
            Utils.restrictedDelete(workingFile);
        }
    }

    /** Returns the string array of all the items on the add stage. */
    public static String[] getStagedAddList() {
        File[] files = ADD_FOLDER.listFiles();
        String[] fileNames = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }

        Arrays.sort(fileNames);
        return fileNames;
    }

    /** Returns the string array of all the items on the remove stage. */
    public static String[] getStagedRemoveList() {
        File[] files = REMOVE_FOLDER.listFiles();
        String[] fileNames = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }

        Arrays.sort(fileNames);
        return fileNames;
    }

    /** Returns whether FILENAME is staged. */
    public static boolean isStaged(String fileName) {
        File stagingAddFile = new File(ADD_FOLDER + "/" + fileName);
        File stagingRemoveFile = new File(REMOVE_FOLDER + "/" + fileName);

        return stagingAddFile.exists() || stagingRemoveFile.exists();
    }

    /** Clears the stage. */
    public static void clear() {

        for (File file : ADD_FOLDER.listFiles()) {
            file.delete();
        }
        for (File file : REMOVE_FOLDER.listFiles()) {
            file.delete();
        }
    }

    /** Returns whether the stage is empty. */
    public static boolean isEmpty() {
        return ADD_FOLDER.listFiles().length == 0
                && REMOVE_FOLDER.listFiles().length == 0;
    }

}
