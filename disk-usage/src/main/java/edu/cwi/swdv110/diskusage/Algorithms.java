package edu.cwi.swdv110.diskusage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Hand-written searching and sorting. Collections.sort / List.sort are off-limits here. */
public final class Algorithms {

    /** Largest first; equal sizes ordered by path A–Z. */
    public static final Comparator<FileInfo> BY_SIZE_DESC =
            Comparator.comparingLong(FileInfo::size).reversed().thenComparing(FileInfo::path);

    /** Path A–Z (the order binary search needs). */
    public static final Comparator<FileInfo> BY_PATH = Comparator.comparing(FileInfo::path);

    private Algorithms() { }

    /*
     * About <T>: these methods are GENERIC – they sort a List of any type T, as long as you
     * give them a Comparator<T> that says how to order two T's. Use cmp.compare(a, b)
     * exactly where the in-class version used a < b.
     */

    /** Returns a NEW sorted list (don't modify the input). O(n²). */
    public static <T> List<T> selectionSort(List<T> list, Comparator<T> cmp) {
        List<T> a = new ArrayList<>(list);
        // TODO
        for(int i = 0; i < a.size(); i++){
            int minIndex = i;

            // Smallest remaining item
            for(int j = i + 1; j < a.size(); j++){

                // This returns -1 if the first item comes before the second
                // So set minIndex to J if J comes bfore it
                if(cmp.compare(a.get(j), a.get(minIndex)) == -1){
                    minIndex = j;
                }
            }

            T temp = a.get(i);

            // Must call .set() to modify the data
            a.set(i, a.get(minIndex));
            a.set(minIndex, temp);
        }
        return a;
    }

    /**
     * Returns a NEW sorted list. O(n log n).
     *   base case:      size 0 or 1 -> a copy of the list
     *   recursive case: mergeSort each half (list.subList is handy), then merge them
     * The merge must be STABLE: when two items compare equal, take the one from the left half.
     */
    public static <T> List<T> mergeSort(List<T> list, Comparator<T> cmp) {
        // Base Case
        if(list.size() < 2){
            return new ArrayList<>(list);
        }

        int middle = list.size() / 2;

        List<T> left = new ArrayList<>(list.subList(0, middle));
        List<T> right = new ArrayList<>(list.subList(middle, list.size()));

        // Recursive Case
        return merge(mergeSort(left, cmp), mergeSort(right, cmp), cmp);
    }

    private static <T> List<T> merge(List<T> left, List<T> right, Comparator<T> cmp) {
        // Create a copy to modify and return
        List<T> mergedList = new ArrayList<>();

        int i = 0, j = 0;

        while(i < left.size() && j < right.size()){
            if(cmp.compare(left.get(i), right.get(j)) <= 0){
                mergedList.add(left.get(i++));
            }else{
                mergedList.add(right.get(j++));
            }
        }

        // These copy the remaining elements from each side
        // They are already sorted
        while(i < left.size()){
            mergedList.add(left.get(i++));
        }

        while(j < right.size()){
            mergedList.add(right.get(j++));
        }

        return mergedList;
    }

    /**
     * RECURSIVE binary search over a list already sorted BY_PATH.
     * Returns the index of the file with this exact path, or -1.
     */
    public static int binarySearchByPath(List<FileInfo> sortedByPath, String path) {
        // TODO call the helper with the whole range
        return binarySearch(sortedByPath, path, 0, sortedByPath.size() - 1);
    }

    private static int binarySearch(List<FileInfo> a, String path, int low, int high) {
        // Base Case
        if(low > high){
            return -1;
        }

        int middle = low + (high - low) / 2;

        if(a.get(middle).path().equals(path)){
            return middle;
        }

        // Recursive Case
        if(path.compareTo(a.get(middle).path()) < 0){
            return binarySearch(a, path, low, middle - 1);
        }

        return binarySearch(a, path, middle + 1, high);
    }

    /** The n largest files (fewer if there aren't n), using YOUR mergeSort. */
    public static List<FileInfo> largest(List<FileInfo> files, int n) {
        // TODO
        List<FileInfo> mergedFiles = mergeSort(files, BY_SIZE_DESC);

        if(n > mergedFiles.size()){
            return mergedFiles;
        }else{
            // I hate this naming convention I chose
            // But largestNElements also did not look good
            List<FileInfo> largestN_Elements = new ArrayList<>();

            for(int i = 0; i < n; i++){
                largestN_Elements.add(mergedFiles.get(i));
            }

            return largestN_Elements;
        }
    }
}
