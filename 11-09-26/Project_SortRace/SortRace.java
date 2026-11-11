import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class SortRace {
    public static void main(String[] args) {
        int[] randomArray = new int[100000];
        Random rand = new Random();
        
        for(int i = 0; i < randomArray.length; i++){
            randomArray[i] = rand.nextInt();
        }

        int[] selectionSortArray = Arrays.copyOf(randomArray, randomArray.length);
        int[] mergeSortArray = Arrays.copyOf(randomArray, randomArray.length);
        int[] arraySortArray = Arrays.copyOf(randomArray, randomArray.length);

        // Selection Sort
        Instant start = Instant.now();
        selectionSort(selectionSortArray);
        Instant end = Instant.now();

        Duration timeElapsed = Duration.between(start, end);

        System.out.println("selectionSort took " + timeElapsed.toMillis() + " ms.");

        // Merge Sort
        start = Instant.now();
        mergeSort(mergeSortArray);
        end = Instant.now();

        timeElapsed = Duration.between(start, end);

        System.out.println("mergeSort took " + timeElapsed.toMillis() + " ms.");

        // Arrays.sort
        start = Instant.now();
        Arrays.sort(arraySortArray);
        end = Instant.now();

        timeElapsed = Duration.between(start, end);

        System.out.println("Arrays.sort took " + timeElapsed.toMillis() + " ms.\n");
            
        // Just to verify the arrays got sorted
        System.out.println("selectionSort sorted? " + isSorted(selectionSortArray));
        System.out.println("mergeSort sorted? " + isSorted(mergeSortArray));
        System.out.println("Arrays.sort sorted? " + isSorted(arraySortArray));
    }

    public static void selectionSort(int[] array){
        for(int i = 0; i < array.length - 1; i++){
            int minIndex = i;

            // This finds the smallest remaining item
            for(int j = i + 1; j < array.length; j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }

            // This is storing the element at i
            int temp = array[i];

            // Now we are swapping the element at the current index
            // With the element at the minIndex
            array[i] = array[minIndex];

            // Then we change the element at the minIndex
            // And set that to our temp variable?
            array[minIndex] = temp;
        }
    }

    public static void mergeSort(int[] array){
        // Early return if array length is less than 2
        if(array.length < 2){
            return;
        }

        int middle = array.length / 2;

        // Split the array into left and right
        int[] left = Arrays.copyOfRange(array, 0, middle);
        int[] right = Arrays.copyOfRange(array, middle, array.length);

        // Recursively split each array until their lengths are 1
        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);
    }

    private static void merge(int[] array, int[] left, int[] right){
        int i = 0, j = 0, k = 0;

        // While we still have elements in both arrays
        while(i < left.length && j < right.length){
            // Compare the element at our current index
            // Return the element in that section
            // Insert it into our merged array
            // Increment our index
            array[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }

        // These two loops copy remaining elements
        // Since they are already sorted no need to compare
        while(i < left.length){
            array[k++] = left[i++];
        }

        while(j < right.length){
            array[k++] = right[j++];
        }
    }

    // Helper function to verify each array is actually sorted
    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
