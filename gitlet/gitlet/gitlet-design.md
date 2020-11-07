# Gitlet Design Doc

# Classes and Data Structures
## Command (Abstract Class) ?

**Classes that extend** **(most likely individual methods within multiple classes)**

## Repo Class
## Repo

This class defines a gitlet repository, including methods and instance variables.

**Fields**
— Tree<Version> versions: The tree of versions (commits) in this repository.

— Init (Constructor of repo class)
Makes a new repository and initializes gitlet within that repo.
Our structure makes the implementation of init much simpler. 
Can possibly be implemented using some sort of constructor. Will most likely be the constructor of the Repo class. Essentially, it will initialize an empty tree of versions first. Then, we create the first commit by calling the commit function.

— Add
Takes in a file directory and adds that to the current commit, which will then be added to the series of versions for the repository.

— Commit
Creates a new version object and forms a tree from the current working directory, pointing to either older unchanged files or to new files added by the user to the current commit.

— Rm
Removes a file from the version tree, deleting the pointers to changed files so as to unstage them from the current commit.

— Log
Prints out all commit information starting at head pointer all the way up to initial commit.

— Global-log
Same as log, but displays all commit information.

— Find
Displays the commit info for all the commits with the user inputted commit message.

— Status
Prints to the terminal the status of the current commit, including what files are added, changed but not staged, and untracked altogether.

— Checkout
Depending on the input following the command, does one of three things:


1. “--[filename]” Given just a file name, checkout will attempt to overwrite the file in the working directory with the file with the same name in the head commit.
2. “[commit id] --[filename]” Given a file name preceded by a commit ID, checkout will attempt to overwrite the file in the working directory with the file with the same name in the commit with the corresponding ID.
3. “[branch name]” Given just a branch name, checkout will overwrite all the files in the current branch with the given one, deleting any files not tracked in the given branch. This will then set the current branch to the given one, making all pointers to files and blobs the same as the checked out branch.

— Branch (possibly in the version class)
Creates a new branch with the given branch name.

— RmBranch (possibly in the version class)
Opposite functionality of branch command.

— Reset
Will most likely call the checkout command on the provided commit id.


— **Merge** (class still undecided where we will implement this command … )


## Version

This object defines a directory of files.

**Fields**
— final BlobTree mainDirectory: ← NEVER CHANGED


## BlobFile (extends BlobTree) ?


## BlobTree (implements Comparable) ??

This is a representation of an object being tracked by Gitlet, it can be either a file of a folder (directory). If it’s a folder, than it will have other BlobTrees in its children variable.

**Fields**
— ArrayList<BlobTree> children: Empty if this is a single file; has children if it’s a directory.
— BlobTree parent: The directory this BlobTree is under; empty if main repo directory.
— File contents: Empty if it’s a directory.
— String name ← Same name between many different Blobs
— int compareTo: method that compares the “contents” of this blob with another (or children if comparing directories).


# Algorithms
## Gitlet Directory (Parent directory)

— Contains all the files in our repository. 

## Command Interface

The command interface encapsulates all the possible commands a user could input after [java gitlet.Main].

## Hashing

Need a way to securely hash a file’s contents to have different hashes with the same name. For this we’ll use SHA-1 hashing, this has a hashing algorithm that hashes the files contents into a 40 character hexadecimal string has an extremely small chance of resulting in a hash conflict, so small we don’t have to worry about it.


# Persistence 

We need to make sure that we remember the commit/version tree across multiple commands specified by the user. Therefore, we definitely need a parallel representation of our files within the .gitlet directory (main directory).

The main implementation of persistence would be the use of serialization of files. Most of the implementation of serialization is provided in the Utils function provided in the spec. However, we do need to make sure that for private instance variables where we want to have redundant pointers to commits to save execution time on looking them up, we declare these variables as transient.

