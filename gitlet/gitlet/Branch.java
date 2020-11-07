package gitlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

/** This is the branch manager.
 *  @author Benjamin B.
 */
public class Branch {

    /** The name of the branch. */
    private String name;
    /** The name of the head commit hash. */
    private String headCommitHash;

    /** Constructor, takes in BRANCHNAME and BRANCHHEADCOMMITHASH. */
    public Branch(String branchName, String branchHeadCommitHash) {
        name = branchName;
        headCommitHash = branchHeadCommitHash;
    }

    /** Returns the name. */
    public String getName() {
        return name;
    }
    /** Returns the head commit hash. */
    public String getHeadCommitHash() {
        return headCommitHash;
    }
    /** Mutator for the NEWHEADHASH.  */
    public void setHeadCommitHash(String newHeadHash) {
        headCommitHash = newHeadHash;
    }

    /** Returns CURRENTNAME as it should be as a branch name. */
    private static String fileToBranch(String currentName) {
        return currentName.replace("-", "/");
    }

    /** Returns CURRENTNAME as it should be as a file name. */
    private static String branchToFile(String currentName) {
        return currentName.replace("/", "-");
    }

    /** Returns the branch with name BRANCHNAME. */
    public static Branch load(String branchName) {
        return load(branchName, Main.GITLET_FOLDER);
    }

    /** Returns the branch with name BRANCHNAME in directory GITDIR. */
    public static Branch load(String branchName, File gitDir) {
        File branchFile = new File(gitDir + "/" + Main.BRANCHES_FOLDER
                + "/" +  branchToFile(branchName));
        try {
            return new Branch(branchName,
                Utils.readContentsAsString(branchFile));
        } catch (IllegalArgumentException excp) {
            System.out.println("Branch could not be read properly.");
            return null;
        }
    }

    /** Saves the branch to a file. */
    public void save() {
        save(Main.GITLET_FOLDER);
    }

    /** Saves the branch to a file in directory GITDIR. */
    public void save(File gitDir) {
        File file = new File(gitDir + "/" + Main.BRANCHES_FOLDER
                + "/" +  branchToFile(name));

        try {
            file.createNewFile();
            BufferedOutputStream str =
                new BufferedOutputStream(Files
                    .newOutputStream(file.toPath()));

            str.write((headCommitHash).getBytes(StandardCharsets.UTF_8));
            str.close();
        } catch (IOException excp) {
            System.out.println("Branch could not be written properly.");
        }
    }

    /** Returns whether BRANCHNAME exists. */
    public static boolean exists(String branchName) {
        return exists(branchName, Main.GITLET_FOLDER);
    }

    /** Returns whether BRANCHNAME exists in directory GITDIR. */
    public static boolean exists(String branchName, File gitDir) {
        File file = new File(gitDir + "/" + Main.BRANCHES_FOLDER
                + "/" +  branchToFile(branchName));

        return gitDir.exists() && file.exists();
    }

    /** Deletes BRANCHNAME. */
    public static void delete(String branchName) {
        File file = new File(Main.GITLET_FOLDER + "/" + Main.BRANCHES_FOLDER
                + "/" +  branchToFile(branchName));

        file.delete();
    }

    /** Returns the list of all branches. */
    public static String[] getBranchList() {
        File[] files = new File(Main.GITLET_FOLDER
                + "/" + Main.BRANCHES_FOLDER).listFiles();
        String[] branchNames = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            branchNames[i] = fileToBranch(files[i].getName());
        }

        Arrays.sort(branchNames);
        return branchNames;
    }

    /** Pushes to the REMOTEBRANCHNAME named
     *  REMOTENAME from the local directory. */
    public static void pushBranch(String remoteName, String remoteBranchName) {
        if (!Remote.exists(remoteName)) {
            System.out.println("A remote with that name does not exist.");
            return;
        }
        File remoteDirectory = new File(Remote.load(remoteName).getDirectory());
        if (!remoteDirectory.exists()) {
            System.out.println("Remote directory not found.");
            return;
        }
        Status status = Status.load();
        Branch currentBranch = Branch.load(status.getCurrentBranchName());
        Branch remoteBranch = Branch.load(remoteBranchName, remoteDirectory);
        String remoteCommitHash = remoteBranch.getHeadCommitHash();
        String commitHash = currentBranch.getHeadCommitHash();
        ArrayList<Commit> localCommits = new ArrayList<>();
        while (commitHash != null) {
            if (commitHash.equals(remoteCommitHash)) {
                break;
            }
            Commit commit = Commit.load(commitHash);
            localCommits.add(commit);
            commitHash = commit.getParentHash();
        }
        if (commitHash == null) {
            System.out.println(
                    "Please pull down remote changes before pushing.");
            return;
        }
        File localBlobsFolder = new File(Main.GITLET_FOLDER
                + "/" + Main.BLOBS_FOLDER);
        File remoteBlobsFolder = new File(remoteDirectory
                + "/" + Main.BLOBS_FOLDER);
        for (Commit commit : localCommits) {
            for (String blobHash : commit.getBlobHashes().values()) {
                File remoteFile = new File(remoteBlobsFolder
                        + "/" + blobHash);
                File localFile = new File(localBlobsFolder
                        + "/" + blobHash);
                if (!remoteFile.exists()) {
                    try {
                        Files.copy(localFile.toPath(), remoteFile.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            commit.save(remoteDirectory);
        }
        remoteBranch.setHeadCommitHash(currentBranch.getHeadCommitHash());
        remoteBranch.save(remoteDirectory);
    }

    /** Fetches the REMOTEBRANCHNAME from the
     * remote directory named REMOTENAME. */
    public static void fetchBranch(String remoteName, String remoteBranchName) {
        if (!Remote.exists(remoteName)) {
            System.out.println("A remote with that name does not exist.");
            return;
        }

        File remoteDirectory = new File(Remote.load(remoteName).getDirectory());

        if (!remoteDirectory.exists()) {
            System.out.println("Remote directory not found.");
            return;
        }

        if (!Branch.exists(remoteBranchName, remoteDirectory)) {
            System.out.println("That remote does not have that branch.");
            return;
        }

        Branch remoteBranch = Branch.load(remoteBranchName, remoteDirectory);
        String remoteCommitHash = remoteBranch.getHeadCommitHash();

        ArrayList<Commit> remoteCommits = new ArrayList<>();

        while (remoteCommitHash != null) {
            if (Commit.exists(remoteCommitHash)) {
                break;
            }
            Commit commit = Commit.load(remoteCommitHash, remoteDirectory);
            remoteCommits.add(commit);
            remoteCommitHash = commit.getParentHash();
        }

        File remoteBlobsFolder = new File(remoteDirectory
                + "/" + Main.BLOBS_FOLDER);
        File localBlobsFolder = new File(Main.GITLET_FOLDER
                + "/" + Main.BLOBS_FOLDER);

        for (Commit commit : remoteCommits) {
            for (String blobHash : commit.getBlobHashes().values()) {
                File localFile = new File(localBlobsFolder + "/" + blobHash);
                File remoteFile = new File(remoteBlobsFolder + "/" + blobHash);
                if (!localFile.exists()) {
                    try {
                        Files.copy(remoteFile.toPath(), localFile.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            commit.save();
        }

        Branch branch = new Branch(remoteName + "/"
                + remoteBranchName, remoteBranch.getHeadCommitHash());

        branch.save();
    }
}
