package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        System.out.print("Folder to scan (Enter for this project): ");
        String input = sc.hasNextLine() ? sc.nextLine().trim() : "";
        File root = Path.of(input.isEmpty() ? "." : input).toAbsolutePath().normalize().toFile();
        if (!root.isDirectory()) {
            System.out.println("Not a folder: " + root);
            return;
        }

        // TODO Part 5a: print the tree, 2 levels deep
        System.out.println(FileScanner.tree(root, 2));

        // TODO Part 5b: print "Files: N   Total: X   Deepest nesting: N levels"
        System.out.println("Files: " + FileScanner.countFiles(root) + " Total: " + FileScanner.humanSize(FileScanner.totalSize(root)) + " Deepest nesting: " + FileScanner.maxDepth(root) + " levels");

        // TODO Part 5c: print the 5 largest files (size right-aligned in 10 columns, then path)
        System.out.println("\nLargest files: ");
        for(FileInfo file : Algorithms.largest(FileScanner.listAll(root), 5)){
            System.out.printf("%10s %s\n", FileScanner.humanSize(file.size()), file.path());
        }

        // TODO Part 5d: count files per extension in a TreeMap<String, Integer>
        //               (files without a '.' count as "(none)") and print the map
        TreeMap<String, Integer> extensionCounts = new TreeMap<>();

        for(FileInfo file : FileScanner.listAll(root)){
            String path = file.path();
            String fileName = path.substring(path.lastIndexOf('/') + 1);
            String fileExtension = "(none)";

            int dotIndex = fileName.lastIndexOf('.');

            if(dotIndex >= 0){
                fileExtension = fileName.substring(dotIndex + 1);
            }

            extensionCounts.merge(fileExtension, 1, Integer::sum);
        }

        System.out.println("\nFiles by extension: " + extensionCounts);

        List<FileInfo> files = Algorithms.mergeSort(FileScanner.listAll(root), Algorithms.BY_PATH);
        // TODO Part 5e: loop – 'quit' ends, 'race' calls sortRace(), anything else is a path
        //               to look up with binarySearchByPath (sort the list BY_PATH first!)
        while(running){
            System.out.print("\nFind path (or 'race' / 'quit'): ");
            input = sc.nextLine().trim();

            if(input.equalsIgnoreCase("quit")){
                System.out.println("\nExiting disk-usage application.");
                running = false;
                continue;
            }

            if(input.equalsIgnoreCase("race")){
                sortRace();
                continue;
            }

            int index = Algorithms.binarySearchByPath(files, input);

            if(index == -1){
                System.out.println("no file with that name was found.");
            }else{
                FileInfo matchedFile = files.get(index);
                System.out.println("Found at index" + index + ": " + FileScanner.humanSize(matchedFile.size()));
            }
        
        }
        

    }

    /** GIVEN: times both sorts on random data that doubles in size each round. */
    static void sortRace() {
        Random rnd = new Random(110);
        System.out.printf("%8s %14s %12s%n", "n", "selection ms", "merge ms");
        for (int n = 1_000; n <= 16_000; n *= 2) {
            List<FileInfo> data = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                data.add(new FileInfo("file" + rnd.nextInt(1_000_000), rnd.nextInt(10_000_000)));
            }
            long t0 = System.nanoTime();
            Algorithms.selectionSort(data, Algorithms.BY_SIZE_DESC);
            long t1 = System.nanoTime();
            Algorithms.mergeSort(data, Algorithms.BY_SIZE_DESC);
            long t2 = System.nanoTime();
            System.out.printf("%8d %14.1f %12.1f%n", n, (t1 - t0) / 1e6, (t2 - t1) / 1e6);
        }
    }
}
