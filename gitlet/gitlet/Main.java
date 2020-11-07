package gitlet;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/** Driver class for Gitlet, a version-control system similar to git.
 *  @author Benjamin B.
 */
public class Main {

    /** The command entered. */
    private static String command;
    /** The arguments of that command. */
    private static String[] arguments;
    /** The full commit length. */
    private static final int COMMIT_LENGTH = 40;

    /** The gitlet main folder. */
    static final File GITLET_FOLDER = new File(".gitlet");
    /** The staging folder.  */
    static final File STAGING_ROOT_FOLDER = new File(".gitlet/staging");
    /** The staging add folder. */
    static final File STAGING_ADD_FOLDER = new File(".gitlet/staging/add");
    /** The staging remove folder. */
    static final File STAGING_REMOVE_FOLDER =
            new File(".gitlet/staging/remove");

    /** The branches folder. */
    static final File BRANCHES_FOLDER = new File("branches");
    /** The remotes folder. */
    static final File REMOTES_FOLDER = new File("remotes");
    /** The commits folder. */
    static final File COMMITS_FOLDER = new File("commits");
    /** The blobs folder. */
    static final File BLOBS_FOLDER = new File("blobs");

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length < 1) {
            System.out.println("Please enter a command.");
            return;
        }

        command = args[0];
        arguments = Arrays.copyOfRange(args, 1, args.length);

        switch (command) {
        case "init":        initCommand();          break;
        case "add":         addCommand();           break;
        case "commit":      commitCommand();        break;
        case "rm":          rmCommand();            break;
        case "log":         logCommand();           break;
        case "global-log":  globalLogCommand();     break;
        case "find":        findCommand();          break;
        case "status":      statusCommand();        break;
        case "checkout":    checkoutCommand();      break;
        case "branch":      branchCommand();        break;
        case "rm-branch":   removeBranchCommand();  break;
        case "reset":       resetCommand();         break;
        case "merge":       mergeCommand();         break;
        case "add-remote":  addRemoteCommand();     break;
        case "rm-remote":   removeRemoteCommand();  break;
        case "push":        pushCommand();          break;
        case "fetch":       fetchCommand();         break;
        case "pull":        pullCommand();          break;
        default :
            System.out.println("No command with that name exists.");
            return;
        }
    }

    /** Initializes the repository with an inital empty
     * commit and master branch and creates the .gitlet directory.*/
    private static void initCommand() {
        if (isInGitletDir()) {
            System.out.println("Gitlet version-control "
                    + "system already exists in the current directory.");
            return;
        }
        if (!checkOperands(0)) {
            System.out.println("Incorrect operands.");
            return;
        }

        GITLET_FOLDER.mkdir();
        STAGING_ROOT_FOLDER.mkdir();
        STAGING_ADD_FOLDER.mkdir();
        STAGING_REMOVE_FOLDER.mkdir();

        new File(GITLET_FOLDER + "/" + REMOTES_FOLDER).mkdir();
        new File(GITLET_FOLDER + "/" + BRANCHES_FOLDER).mkdir();
        new File(GITLET_FOLDER + "/" + COMMITS_FOLDER).mkdir();
        new File(GITLET_FOLDER + "/" + BLOBS_FOLDER).mkdir();

        Commit initialCommit = new Commit(null,
                new Date(0), "initial commit");
        initialCommit.save();

        Branch masterBranch = new Branch("master", initialCommit.getHash());
        masterBranch.save();

        Status status = new Status(masterBranch.getName());
        status.save();

    }

    /** Adds a file to the stage.*/
    private static void addCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        Status status = Status.load();

        Branch branch = Branch.load(status.getCurrentBranchName());

        Commit head = Commit.load(branch.getHeadCommitHash());

        Staging.add(arguments[0], head);

    }

    /** Makes a new commit and clears the stage. */
    private static void commitCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory. ");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        if (Staging.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }

        if (arguments[0].equals("")) {
            System.out.println("Please enter a commit message.");
            return;
        }

        Status status = Status.load();

        Branch branch = Branch.load(status.getCurrentBranchName());

        Commit head = Commit.load(branch.getHeadCommitHash());

        Commit commit = new Commit(head, new Date(), arguments[0]);

        for (String fileName : Staging.getStagedAddList()) {
            String hash = BlobManager.moveBlobFromStage(fileName);

            commit.getBlobHashes().put(fileName, hash);
        }

        for (String fileName : Staging.getStagedRemoveList()) {
            commit.getBlobHashes().remove(fileName);

            File stagedRemoveFile = new File(
                    Main.STAGING_REMOVE_FOLDER + "/" + fileName);
            stagedRemoveFile.delete();
        }

        commit.save();

        branch.setHeadCommitHash(commit.getHash());
        branch.save();

    }

    /** Removes an item from the stage and WD. */
    private static void rmCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        Status status = Status.load();

        Branch branch = Branch.load(status.getCurrentBranchName());

        Commit head = Commit.load(branch.getHeadCommitHash());

        Staging.remove(arguments[0], head);

    }

    /** Logs the list of commits on this branch. */
    private static void logCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(0)) {
            System.out.println("Incorrect operands.");
            return;
        }

        Status status = Status.load();

        Branch branch = Branch.load(status.getCurrentBranchName());

        String commitHash = branch.getHeadCommitHash();

        while (commitHash != null) {

            Commit commit = Commit.load(commitHash);

            System.out.println("===");
            System.out.println(commit.toString());
            commitHash = commit.getParentHash();
            System.out.println();

        }

    }

    /** Log all commits in commits folder. */
    private static void globalLogCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        File commitFolder = new File(GITLET_FOLDER
                + "/" + COMMITS_FOLDER);
        for (File file : commitFolder.listFiles()) {
            Commit commit = Commit.load(file.getName());

            System.out.println("===");
            System.out.println(commit.toString());
            System.out.println();
        }

    }

    /** Finds a commit ID from the log message. */
    private static void findCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }
        boolean found = false;
        File commitFolder = new File(GITLET_FOLDER
                + "/" + COMMITS_FOLDER);
        for (String fileName : Utils.plainFilenamesIn(commitFolder)) {
            Commit commit = Commit.load(fileName);
            if (commit.getMessage().equals(arguments[0])) {
                System.out.println(fileName);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }

    /** Prints the branch and stage. */
    private static void statusCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(0)) {
            System.out.println("Incorrect operands.");
            return;
        }

        Status status = Status.load();

        System.out.println("=== Branches ===");
        for (String branchName : Branch.getBranchList()) {
            if (status.getCurrentBranchName().equals(branchName)) {
                System.out.print("*");
            }
            System.out.println(branchName);
        }

        System.out.println("\n=== Staged Files ===");
        String[] addedFileNames = Staging.getStagedAddList();
        for (String fileName : addedFileNames) {
            System.out.println(fileName);
        }

        System.out.println("\n=== Removed Files ===");
        String[] removedFilesNames = Staging.getStagedRemoveList();
        for (String fileName : removedFilesNames) {
            System.out.println(fileName);
        }

        System.out.println("\n=== Modifications Not Staged For Commit ===");

        Branch branch = Branch.load(status.getCurrentBranchName());
        Commit commit = Commit.load(branch.getHeadCommitHash());
        for (String name : commit.getTracked()) {
            if (Staging.isStaged(name)) {
                continue;
            }
            File file = new File(name);
            if (!file.exists()) {
                System.out.println(name + " (deleted)");
            } else if (!BlobManager.getFileHash(file)
                    .equals(commit.getBlobHashes().get(name))) {
                System.out.println(name + " (modified)");
            }
        }

        System.out.println("\n=== Untracked Files ===");
        for (String fileName : Utils.plainFilenamesIn("./")) {
            if (Staging.isStaged(fileName)
                    || commit.getBlobHashes().containsKey(fileName)) {
                continue;
            }
            System.out.println(fileName);
        }
    }

    /** Checks out a file or branch depending on params. */
    private static void checkoutCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        Status status = Status.load();
        Branch currentBranch = Branch.load(status.getCurrentBranchName());
        Commit headCommit = Commit.load(currentBranch.getHeadCommitHash());
        if (checkOperands(1)) {

            if (!Branch.exists(arguments[0])) {
                System.out.println("No such branch exists.");
                return;
            }
            if (arguments[0].equals(currentBranch.getName())) {
                System.out.println("No need to checkout the current branch.");
                return;
            }
            Branch specifiedBranch = Branch.load(arguments[0]);
            Commit specifiedCommit = Commit
                .load(specifiedBranch.getHeadCommitHash());

            BlobManager.checkoutCommits(headCommit, specifiedCommit);
            status.setCurrentBranchName(specifiedBranch.getName());
            status.save();
            Staging.clear();

        } else if (checkOperands(2) && arguments[0].equals("--")) {
            String fileName = arguments[1];
            BlobManager.checkoutFile(fileName, headCommit.getBlobHashes());

        } else if (checkOperands(3) && arguments[1].equals("--")) {
            String fileName = arguments[2];
            String commitID = arguments[0];

            if (commitID.length() < COMMIT_LENGTH) {
                File commitFolder = new File(GITLET_FOLDER
                        + "/" + COMMITS_FOLDER);
                for (File commitFile : commitFolder.listFiles()) {
                    String hash = commitFile.getName();
                    if (commitID.equals(hash
                            .substring(0, arguments[0].length()))) {
                        commitID = hash;
                        break;
                    }
                }
            }
            if (!Commit.exists(commitID)) {
                System.out.println("No commit with that id exists.");
                return;
            }
            Commit specifiedCommit = Commit.load(commitID);
            BlobManager.checkoutFile(fileName, specifiedCommit.getBlobHashes());
        } else {
            System.out.println("Incorrect operands.");
        }
    }

    /** Makes a new branch at current head. */
    private static void branchCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        String branchName = arguments[0];

        if (Branch.exists(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        }

        Status status = Status.load();

        Branch branch = Branch.load(status.getCurrentBranchName());

        Branch newBranch = new Branch(branchName, branch.getHeadCommitHash());
        newBranch.save();
    }

    /** Removes a branch. */
    private static void removeBranchCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        String branchName = arguments[0];

        if (!Branch.exists(branchName)) {
            System.out.println("A branch with that name does not exist.");
            return;
        }

        Status status = Status.load();

        if (status.getCurrentBranchName().equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }

        Branch.delete(branchName);
    }

    /** Resets the WD and stage to a given commit. */
    private static void resetCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        Status status = Status.load();

        Branch currentBranch = Branch.load(status.getCurrentBranchName());

        Commit headCommit = Commit.load(currentBranch.getHeadCommitHash());

        if (!Commit.exists(arguments[0])) {
            System.out.println("No commit with that id exists.");
            return;
        }

        Commit specifiedCommit = Commit.load(arguments[0]);

        BlobManager.checkoutCommits(headCommit, specifiedCommit);

        currentBranch.setHeadCommitHash(specifiedCommit.getHash());
        currentBranch.save();
        Staging.clear();
    }

    /** Merges to commits in to one and checks that new commit out. */
    private static void mergeCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }
        if (!Staging.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            return;
        }
        if (!Branch.exists(arguments[0])) {
            System.out.println("A branch with that name does not exist.");
            return;
        }
        Status status = Status.load();
        if (status.getCurrentBranchName().equals(arguments[0])) {
            System.out.println("Cannot merge a branch with itself.");
            return;
        }
        Branch currentBranch = Branch.load(status.getCurrentBranchName());
        Branch givenBranch = Branch.load(arguments[0]);
        Commit headCommit = Commit.load(currentBranch.getHeadCommitHash());
        Commit givenCommit = Commit.load(givenBranch.getHeadCommitHash());
        Commit splitPoint = Commit.findSplitPoint(headCommit, givenCommit);
        if (splitPoint == null) {
            System.out.println("ERROR: could not find split point");
            return;
        }
        String splitPointHash = splitPoint.getHash();
        if (givenBranch.getHeadCommitHash().equals(splitPointHash)) {
            System.out.println("Given branch is "
                    + "an ancestor of the current branch.");
            return;
        }
        if (currentBranch.getHeadCommitHash().equals(splitPointHash)) {
            System.out.println("Current branch fast-forwarded.");
            BlobManager.checkoutCommits(headCommit, givenCommit);
            currentBranch.setHeadCommitHash(givenCommit.getHash());
        } else {
            String mergeMessage = String.format("Merged %s into %s.",
                    givenBranch.getName(), currentBranch.getName());
            Commit merge = BlobManager.mergeCommits(headCommit,
                    givenCommit, splitPoint, mergeMessage);
            if (merge == null) {
                return;
            }
            if (merge.getBlobHashes().equals(givenCommit.getBlobHashes())) {
                System.out.println("No changes added to the commit.");
                return;
            }
            merge.save();
            BlobManager.checkoutCommits(headCommit, merge);
            currentBranch.setHeadCommitHash(merge.getHash());
        }
        currentBranch.save();
        Staging.clear();
    }

    /** Adds a remote directory to the table of remotes. */
    private static void addRemoteCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(2)) {
            System.out.println("Incorrect operands.");
            return;
        }

        String remoteName = arguments[0];
        String remoteDir = arguments[1];

        if (Remote.exists(remoteName)) {
            System.out.println("A remote with that name already exists.");
            return;
        }

        remoteDir = remoteDir.replace("/", File.separator);

        Remote remote = new Remote(remoteName, remoteDir);
        remote.save();

    }

    /** Removes a remote directory to the table of remotes. */
    private static void removeRemoteCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(1)) {
            System.out.println("Incorrect operands.");
            return;
        }

        String remoteName = arguments[0];

        if (!Remote.exists(remoteName)) {
            System.out.println("A remote with that name does not exist.");
            return;
        }

        Remote.delete(remoteName);

    }

    /** Attempts to append the current branch's commits to
     * the given remote branch. The remote branch's head
     * must be in the history of the current branch.*/
    private static void pushCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(2)) {
            System.out.println("Incorrect operands.");
            return;
        }
        String remoteName = arguments[0];
        String remoteBranchName = arguments[1];

        Branch.pushBranch(remoteName, remoteBranchName);

    }

    /** Copies the commits of a given branch from the
     * remote Gitlet repository to this Gitlet repository.
     * Copies all blobs and commits that do not exist in
     * the local Gitlet directory, and then creates a new
     * branch and points its head to the same (newly created
     * local) commit it pointed to in the remote directory.*/
    private static void fetchCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(2)) {
            System.out.println("Incorrect operands.");
            return;
        }

        String remoteName = arguments[0];
        String remoteBranchName = arguments[1];

        Branch.fetchBranch(remoteName, remoteBranchName);
    }

    /** Fetches a branch from the given remote, then merges
     * that new branch with the current local branch.*/
    private static void pullCommand() {
        if (!isInGitletDir()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }
        if (!checkOperands(2)) {
            System.out.println("Incorrect operands.");
            return;
        }

        String remoteName = arguments[0];
        String remoteBranchName = arguments[1];

        Branch.fetchBranch(remoteName, remoteBranchName);

        arguments = new String[1];
        arguments[0] =  remoteName + "/" + remoteBranchName;

        mergeCommand();
    }

    /** Returns whether a folder is in the gitlet directory. */
    private static boolean isInGitletDir() {
        return GITLET_FOLDER.isDirectory();
    }

    /** Returns whether arguments are equal to the EXPECTEDOPERANDS. */
    private static boolean checkOperands(int expectedOperands) {
        return arguments.length == expectedOperands;
    }

}
