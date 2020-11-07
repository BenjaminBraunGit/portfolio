package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/** This is the status manager.
 *  @author Benjamin B.
 */
public class Status implements Serializable {

    /** The current branch. */
    private String currentBranchName;

    /** The fixed status file name. */
    private static final String STATUS_FILE_NAME =  "status";
    /** The status constructor that takes in THECURRENTBRANCHNAME. */
    public Status(String theCurrentBranchName) {
        currentBranchName = theCurrentBranchName;
    }

    /** Returns the current branch name. */
    public String getCurrentBranchName() {
        return currentBranchName;
    }

    /** Sets the current branch to NEWBRANCHNAME.*/
    public void setCurrentBranchName(String newBranchName) {
        currentBranchName = newBranchName;
    }

    /** Returns the status from a file. */
    public static Status load() {
        return load(Main.GITLET_FOLDER);
    }

    /** Returns the status from a file in directory GITDIR. */
    public static Status load(File gitDir) {
        return Utils.readObject(new File(gitDir
                + "/" + STATUS_FILE_NAME), Status.class);
    }

    /** Saves the status to a file. */
    public void save() {
        save(Main.GITLET_FOLDER);
    }

    /** Saves the status to a file in directory GITDIR. */
    public void save(File gitDir) {
        File file = new File(gitDir + "/" + STATUS_FILE_NAME);

        try {
            file.createNewFile();
            Utils.writeObject(file, this);
        } catch (IOException exc) {
            System.out.println("Status could not be written properly.");
        }
    }
}
