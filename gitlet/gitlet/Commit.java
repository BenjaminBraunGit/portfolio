package gitlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;

/** This is the commit manager.
 *  @author Benjamin B.
 */
public class Commit implements Serializable {

    /** The parent hash. */
    private final String parentHash;
    /** The merge parent hash. */
    private final String mergeParentHash;
    /** The commit date. */
    private final Date commitDate;
    /** The commit log message. */
    private final String message;
    /** The list of all blobs in this commit. */
    private final HashMap<String, String> blobHashes = new HashMap<>();

    /** The Commit constructor, takes in PARENT,
     * THECOMMITDATE, and THEMESSAGE. */
    public Commit(Commit parent, Date theCommitDate, String theMessage) {
        this(parent, null, theCommitDate, theMessage);
    }

    /** The larger Commit constructor that takes in a PARENT,
     * THECOMMITDATE, THEMESSAGE, and SECONDPARENT. */
    public Commit(Commit parent, Commit secondParent,
                  Date theCommitDate, String theMessage) {

        if (parent != null) {
            parentHash = parent.getHash();
            blobHashes.putAll(parent.blobHashes);
        } else {
            parentHash = null;
        }

        if (secondParent != null) {
            mergeParentHash = secondParent.getHash();
            blobHashes.putAll(secondParent.blobHashes);
        } else {
            mergeParentHash = null;
        }

        commitDate = theCommitDate;
        message = theMessage;
    }

    /** Returns the parent hash. */
    public String getParentHash() {
        return parentHash;
    }
    /** Returns the merge parent hash. */
    public String getMergeParentHash() {
        return mergeParentHash;
    }
    /** Returns the message. */
    public String getMessage() {
        return message;
    }
    /** Returns the blob hashes. */
    public HashMap<String, String> getBlobHashes() {
        return blobHashes;
    }

    /** Returns commit loaded from NAME. */
    public static Commit load(String name) {
        return load(name, Main.GITLET_FOLDER);
    }

    /** Returns commit loaded from NAME in directory GITDIR. */
    public static Commit load(String name, File gitDir) {
        return Utils.readObject(new File(gitDir
                + "/" + Main.COMMITS_FOLDER
                + "/" + name), Commit.class);
    }

    /** Saves the commit to a file. */
    public void save() {
        save(Main.GITLET_FOLDER);
    }

    /** Saves the commit to a file in directory GITDIR. */
    public void save(File gitDir) {
        File file = new File(gitDir + "/" + Main.COMMITS_FOLDER
                + "/" +  getHash());

        try {
            file.createNewFile();
            Utils.writeObject(file, this);
        } catch (IOException ect) {
            System.out.println("Commit could not be written properly.");
        }
    }

    /** Returns the hash of the commit. */
    public String getHash() {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            stream.write(Utils.serialize(parentHash));
            stream.write(Utils.serialize(commitDate));
            stream.write(Utils.serialize(message));

            String[] names = new String[blobHashes.keySet().size()];
            blobHashes.keySet().toArray(names);
            Arrays.sort(names);
            stream.write(Utils.serialize(names));

            String[] hashes = new String[blobHashes.values().size()];
            blobHashes.values().toArray(hashes);
            Arrays.sort(hashes);
            stream.write(Utils.serialize(hashes));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Utils.sha1(stream.toByteArray());
    }

    /** Returns string representation of the commit. */
    public String toString() {
        return String.format("commit %s%nDate: %s%n%s",
                getHash(),
                new SimpleDateFormat("EEE MMM "
                        + "dd hh:mm:ss yyyy Z").format(commitDate),
                message);
    }

    /** Returns whether the COMMITNAME exists. */
    public static boolean exists(String commitName) {
        return exists(commitName, Main.GITLET_FOLDER);
    }

    /** Returns whether the COMMITNAME exists in directory GITDIR. */
    public static boolean exists(String commitName, File gitDir) {
        File file = new File(gitDir + "/" + Main.COMMITS_FOLDER
                + "/" +  commitName);

        return file.exists();
    }

    /** Returns whether the commit has a given FILENAME. */
    public boolean hasFile(String fileName) {
        return blobHashes.containsKey(fileName);
    }

    /** Returns all the tracked hashes. */
    public String[] getTracked() {
        String[] fileNames = new String[blobHashes.size()];
        blobHashes.keySet().toArray(fileNames);
        Arrays.sort(fileNames);
        return fileNames;
    }

    /** Unused method the gets the parents of a COMMIT
     * from LISTOFPARENTS. */
    private void getParents(Commit commit, ArrayList<String> listOfparents) {

        String parentHashs = commit.parentHash;

        while (parentHashs != null) {
            Commit parentCommit = Commit.load(parentHashs);
            listOfparents.add(parentHashs);
            parentHashs = parentCommit.parentHash;
            if (commit.mergeParentHash != null) {
                parentCommit = Commit.load(parentHashs);
                listOfparents.add(parentHashs);
                getParents(parentCommit, listOfparents);
            }
        }

    }

    /** Returns the split point of FIRST and SECOND commits. */
    public static Commit findSplitPoint(Commit first, Commit second) {

        HashSet<String> allAncestors = new HashSet<>();

        ArrayList<String> currentGeneration = new ArrayList<>();
        currentGeneration.add(first.getHash());
        currentGeneration.add(second.getHash());

        HashSet<String> matches = new HashSet<>();

        while (!currentGeneration.isEmpty() && matches.isEmpty()) {
            ArrayList<String> nextGeneration = new ArrayList<>();
            for (String commitHash : currentGeneration) {
                if (!allAncestors.contains(commitHash)) {
                    allAncestors.add(commitHash);
                } else {
                    matches.add(commitHash);
                }

                Commit commit = Commit.load(commitHash);
                if (commit.parentHash != null) {
                    nextGeneration.add(commit.parentHash);
                }
                if (commit.mergeParentHash != null) {
                    nextGeneration.add(commit.mergeParentHash);
                }
            }
            currentGeneration = nextGeneration;
        }
        if (matches.isEmpty()) {
            return null;
        }

        currentGeneration.clear();
        currentGeneration.add(first.getHash());

        while (!currentGeneration.isEmpty()) {
            ArrayList<String> nextGeneration = new ArrayList<>();
            for (String commitHash : currentGeneration) {

                if (matches.contains(commitHash)) {
                    return Commit.load(commitHash);
                }

                Commit commit = Commit.load(commitHash);
                if (commit.parentHash != null) {
                    nextGeneration.add(commit.parentHash);
                }
                if (commit.mergeParentHash != null) {
                    nextGeneration.add(commit.mergeParentHash);
                }
            }
            currentGeneration = nextGeneration;
        }
        return null;
    }
}
