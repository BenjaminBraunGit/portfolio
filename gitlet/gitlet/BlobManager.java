package gitlet;

import java.nio.file.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/** The blob manager for gitlet, this moves the blobs.
 *  @author Benjamin B.
 */
public class BlobManager {

    /** This is the folder where the blobs are stored. */
    static final File FOLDER_LOCAL = new File(Main.GITLET_FOLDER
            + "/" + Main.BLOBS_FOLDER);

    /** Returns the hash of a blob from a file moved from the working
     * directory names FILENAME from the stage to the
     * collection of stored blobs in blobs_folder. */
    public static String moveBlobFromStage(String fileName) {
        File stagedFile = new File(Main.STAGING_ADD_FOLDER + "/" + fileName);

        String hash = getFileHash(stagedFile);

        File blob = new File(FOLDER_LOCAL + "/" + hash);

        if (!blob.exists()) {
            try {
                Files.move(stagedFile.toPath(), blob.toPath());
            } catch (IOException e) {
                System.out.println("Unable to move file");
            }
        } else {
            stagedFile.delete();
        }
        return hash;
    }

    /** Gets file FILE's hash hash and returns it. */
    public static String getFileHash(File file) {
        return Utils.sha1(Utils.readContents(file));
    }

    /** Checks out commit UPDATED to commit CURRENT. */
    public static void checkoutCommits(Commit current, Commit updated) {

        for (String fileName : updated.getBlobHashes().keySet()) {
            if (new File(fileName).exists()
                    && !current.getBlobHashes().containsKey(fileName)) {
                System.out.println("There is an untracked file "
                        + "in the way; delete it, or add and commit it first.");
                return;
            }
        }

        HashSet<String> nameList = new HashSet<>();
        nameList.addAll(updated.getBlobHashes().keySet());
        nameList.addAll(current.getBlobHashes().keySet());

        for (String fileName : nameList) {

            if (!updated.getBlobHashes().containsKey(fileName)) {
                Utils.restrictedDelete(new File(fileName));
            } else {
                checkoutFile(fileName, updated.getBlobHashes());
            }
        }
    }

    /** Checks out a given file FILENAME based on a given TOTABLE. */
    public static void checkoutFile(String fileName, HashMap toTable) {

        String toHash = (String) toTable.get(fileName);

        if (toHash == null) {
            System.out.println("File does not exist in that commit.");
            return;
        }

        File destFile = new File(fileName);

        if (!destFile.exists()
                || !BlobManager.getFileHash(destFile).equals(toHash)) {
            File sourceFile = new File(FOLDER_LOCAL + "/" + toHash);

            try {
                Files.copy(sourceFile.toPath(),
                        destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Failed to checkout file"
                        + fileName + " (overwrite)");
            }
        }

    }

    /** Returns the merge of commit GIVEN into
     * commit CURRENT given a commit SPLITPOINT and a
     * pre-determined commit message MESSAGE.*/
    public static Commit mergeCommits(Commit current, Commit given,
                                      Commit splitPoint, String message) {
        Commit mergeCommit = new Commit(current, given, new Date(), message);
        for (String fileName : mergeCommit.getBlobHashes().keySet()) {
            if (new File(fileName).exists()
                    && !current.getBlobHashes().containsKey(fileName)) {
                System.out.println("There is an untracked "
                        + "file in the way; delete it,"
                        + "or add and commit it first.");
                return null;
            }
        }
        boolean conflictEncountered = false;
        @SuppressWarnings("unchecked")
        Set<String> mergeBlobs = ((HashMap<String, String>)
                mergeCommit.getBlobHashes().clone()).keySet();
        for (String fileName : mergeBlobs) {
            if ((current.hasFile(fileName) && !given.hasFile(fileName))
                    && current.getBlobHashes().get(fileName).equals(splitPoint
                    .getBlobHashes().get(fileName))) {
                mergeCommit.getBlobHashes().remove(fileName);
                continue;
            } else if ((!current.hasFile(fileName) && given.hasFile(fileName))
                    && given.getBlobHashes().get(fileName)
                    .equals(splitPoint.getBlobHashes().get(fileName))) {
                mergeCommit.getBlobHashes().remove(fileName);
                continue;
            }
            boolean currentUnmodified = (!current.hasFile(fileName)
                && !splitPoint.hasFile(fileName))
                || ((current.hasFile(fileName) && splitPoint.hasFile(fileName))
                && current.getBlobHashes().get(fileName)
                .equals(splitPoint.getBlobHashes().get(fileName)));
            boolean givenUnmodified = (!given.hasFile(fileName)
                && !splitPoint.hasFile(fileName))
                || (given.hasFile(fileName) && splitPoint.hasFile(fileName))
                && given.getBlobHashes().get(fileName)
                .equals(splitPoint.getBlobHashes().get(fileName));
            if (!givenUnmodified && currentUnmodified) {
                mergeCommit.getBlobHashes().put(fileName,
                        given.getBlobHashes().get(fileName));
            } else if (givenUnmodified && !currentUnmodified) {
                mergeCommit.getBlobHashes().put(fileName,
                    current.getBlobHashes().get(fileName));
            } else if (!((!current.hasFile(fileName)
                    && !given.hasFile(fileName))
                || ((current.hasFile(fileName) && given.hasFile(fileName))
                && given.getBlobHashes().get(fileName).equals(current.
                getBlobHashes().get(fileName)))) && !givenUnmodified) {
                conflictEncountered = true;
                String currentFileHash = current.getBlobHashes().get(fileName);
                String givenFileHash = given.getBlobHashes().get(fileName);
                mergeCommit.getBlobHashes().put(fileName,
                        mergeFiles(currentFileHash, givenFileHash, fileName));
            }
        }
        if (conflictEncountered) {
            System.out.println("Encountered a merge conflict.");
        }
        return mergeCommit;
    }

    /** Returns the hash of the merged file conflict between
     * FILEHASHCURRENT and FILEHASHMERGING with name FILENAME. */
    private static String mergeFiles(String fileHashCurrent,
                                     String fileHashMerging, String fileName) {

        File currentFile = new File(FOLDER_LOCAL + "/" + fileHashCurrent);
        File givenFile = new File(FOLDER_LOCAL + "/" + fileHashMerging);

        File conflictFile = new File(fileName);

        String currentFileContents = fileHashCurrent == null
                ? "" : Utils.readContentsAsString(currentFile);
        String givenFileContents = fileHashMerging == null
                ? "" : Utils.readContentsAsString(givenFile);
        Utils.writeContents(conflictFile,
                "<<<<<<< HEAD\n",
                currentFileContents,
                "=======\n",
                givenFileContents,
                ">>>>>>>\n"
        );
        String conflictFileHash = BlobManager.getFileHash(conflictFile);
        File conflictDest = new File(FOLDER_LOCAL + "/" + conflictFileHash);
        try {
            Files.copy(conflictFile.toPath(), conflictDest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conflictFileHash;
    }

    /** An unused method for checking things out.
     * Uses KEY, FROMTABLE and TOTABLE. */
    private static void checkoutCommitHelper(String key,
                     HashMap fromTable, HashMap toTable) {

        String fromHash = (String) fromTable.get(key);
        String toHash = (String) toTable.get(key);
        File dest = new File(key);

        if (fromHash == null) {
            File source = new File(FOLDER_LOCAL + "/" + toHash);

            try {
                Files.copy(source.toPath(), dest.toPath());
            } catch (IOException e) {
                System.out.println("Failed to "
                        + "checkout file" + key + " (new file)");
            }

        } else if (toHash == null) {
            Utils.restrictedDelete(dest);
        } else if (!fromHash.equals(toHash)) {
            File source = new File(FOLDER_LOCAL + "/" + toHash);

            try {
                Files.copy(source.toPath(), dest.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Failed to checkout "
                        + "file" + key + " (overwrite)");
            }
        }
    }

}
