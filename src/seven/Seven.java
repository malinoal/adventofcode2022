package seven;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Seven {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(part_one("./src/seven/input.txt"));
        System.out.println(part_two("./src/seven/input.txt"));
    }


    public static int part_one(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        int result = 0;
        FileSystem fileSystem = new FileSystem();
        while (input.hasNext()) {
            String[] currentLine = input.nextLine().split(" ");
            if (currentLine[0].equals("$")) {
                if (currentLine[1].equals("cd")) {
                    fileSystem.cd(currentLine[2]);
                }
            } else if (currentLine[0].equals("dir")) {
                fileSystem.getCurrentDirectory().addDir(currentLine[1]);
            } else {
                fileSystem.getCurrentDirectory().addFile(new VFile(Integer.parseInt(currentLine[0]), currentLine[1]));
            }
        }
        fileSystem.cd("/");
        Stack<Directory> dirs = new Stack<>();
        dirs.add(fileSystem.getCurrentDirectory());
        while (!dirs.isEmpty()) {
            Directory currentDir = dirs.pop();
            int currentDirSize = currentDir.getSize();
            result += currentDirSize < 100000 ? currentDirSize : 0;
            dirs.addAll(currentDir.getSubdirectories());
        }
        return result;
    }

    public static int part_two(String filepath) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filepath));
        input.useDelimiter("\n");
        FileSystem fileSystem = new FileSystem();
        int TOTAL_SPACE = 70000000;
        int NEEDED_SPACE = 30000000;
        while (input.hasNext()) {
            String[] currentLine = input.nextLine().split(" ");
            if (currentLine[0].equals("$")) {
                if (currentLine[1].equals("cd")) {
                    fileSystem.cd(currentLine[2]);
                }
            } else if (currentLine[0].equals("dir")) {
                fileSystem.getCurrentDirectory().addDir(currentLine[1]);
            } else {
                fileSystem.getCurrentDirectory().addFile(new VFile(Integer.parseInt(currentLine[0]), currentLine[1]));
            }
        }
        fileSystem.cd("/");
        Stack<Directory> dirs = new Stack<>();
        dirs.add(fileSystem.getCurrentDirectory());
        Directory root = fileSystem.getCurrentDirectory();
        int takenUpSpace = root.getSize();
        int toDelete = NEEDED_SPACE - (TOTAL_SPACE - takenUpSpace);
        int result = takenUpSpace;
        while (!dirs.isEmpty()) {
            Directory currentDir = dirs.pop();
            int currentDirSize = currentDir.getSize();
            if (currentDirSize < result && currentDirSize > toDelete) {
                result = currentDirSize;
            }
            dirs.addAll(currentDir.getSubdirectories());
        }
        return result;
    }

    private static class FileSystem {
        Directory root;
        Directory currentDirectory;

        public FileSystem() {
            root = new Directory("/", null);
            currentDirectory = root;
        }

        public Directory getCurrentDirectory() {
            return currentDirectory;
        }

        public void cd(String target) {
            if (target.equals("/")) currentDirectory = root;
            else currentDirectory = currentDirectory.cd(target);
        }
    }

    private static class Directory {
        private final Set<Directory> subdirectories;
        private final Set<VFile> files;

        private final String name;

        private final Directory parent;

        public Directory(String name, Directory parent) {
            subdirectories = new HashSet<>();
            files = new HashSet<>();
            this.name = name;
            this.parent = parent;
        }

        public void addDir(Directory dir) {
            subdirectories.add(dir);
        }

        public void addDir(String dir) {
            subdirectories.add(new Directory(dir, this));
        }

        public void addFile(VFile file) {
            files.add(file);
        }

        public Directory getParent() {
            return parent;
        }

        public Directory cd(String target) {
            if (target.equals("..")) return getParent();
            List<Directory> targets = subdirectories.stream()
                    .filter(dir -> dir.getName().equals(target))
                    .toList();
            Directory result;
            if (targets.isEmpty()) {
                result = new Directory(target, this);
                addDir(result);
            } else {
                result = targets.get(0);
            }
            return result;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            int size = 0;
            for (Directory dir : subdirectories)
                size += dir.getSize();
            for (VFile file : files)
                size += file.getSize();
            return size;
        }

        public Set<Directory> getSubdirectories() {
            return subdirectories;
        }

    }

    private static class VFile {
        private final String name;
        private final int size;

        public VFile(int size, String name) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }
}