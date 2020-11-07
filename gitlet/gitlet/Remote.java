package gitlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

/** This is the remote manager.
 *  @author Benjamin B.
 */
public class Remote {

    /** The name of the remote. */
    private String name;
    /** The name of the head commit hash. */
    private String directory;

    /** Constructor, takes in REMOTENAME and THEDIRECTORY. */
    public Remote(String remoteName, String theDirectory) {
        name = remoteName;
        directory = theDirectory;
    }

    /** Returns the name. */
    public String getName() {
        return name;
    }
    /** Returns the head commit hash. */
    public String getDirectory() {
        return directory;
    }

    /** Returns the remote with name REMOTENAME. */
    public static Remote load(String remoteName) {
        return load(remoteName, Main.GITLET_FOLDER);
    }

    /** Returns the remote with name REMOTENAME in directory GITDIR. */
    public static Remote load(String remoteName, File gitDir) {
        File remoteFile = new File(gitDir + "/"
                + Main.REMOTES_FOLDER + "/" +  remoteName);
        try {
            return new Remote(remoteName,
                Utils.readContentsAsString(remoteFile));
        } catch (IllegalArgumentException excp) {
            System.out.println("Remote could not be read properly.");
            return null;
        }
    }

    /** Saves the remote to a file. */
    public void save() {
        save(Main.GITLET_FOLDER);
    }

    /** Saves the remote to a file in directory GITDIR. */
    public void save(File gitDir) {
        File file = new File(gitDir + "/" + Main.REMOTES_FOLDER + "/" + name);

        try {
            file.createNewFile();
            BufferedOutputStream str =
                new BufferedOutputStream(Files
                    .newOutputStream(file.toPath()));

            str.write((directory).getBytes(StandardCharsets.UTF_8));
            str.close();
        } catch (IOException excp) {
            System.out.println("Branch could not be written properly.");
        }
    }

    /** Returns whether REMOTENAME exists. */
    public static boolean exists(String remoteName) {
        return exists(remoteName, Main.GITLET_FOLDER);
    }

    /** Returns whether REMOTENAME exists in directory GITDIR. */
    public static boolean exists(String remoteName, File gitDir) {
        File file = new File(gitDir + "/"
                + Main.REMOTES_FOLDER + "/" + remoteName);

        return gitDir.exists() && file.exists();
    }

    /** Deletes REMOTENAME. */
    public static void delete(String remoteName) {
        File file = new File(Main.GITLET_FOLDER
                + "/" + Main.REMOTES_FOLDER + "/" +  remoteName);

        file.delete();
    }

    /** Returns the list of all remotees. */
    public static String[] getBranchList() {
        File[] files = new File(Main.GITLET_FOLDER + "/"
                + Main.REMOTES_FOLDER).listFiles();
        String[] remoteNames = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            remoteNames[i] = files[i].getName();
        }

        Arrays.sort(remoteNames);
        return remoteNames;
    }

}
