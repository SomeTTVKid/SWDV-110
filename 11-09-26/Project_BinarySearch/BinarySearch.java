public class BinarySearch {
    static int totalCalls = 0;
    public static void main(){
        totalCalls = 0;

        // Create our array and propogate it 
        int[] sortedArray = new int[1000];

        for(int i = 0; i < sortedArray.length; i++){
            sortedArray[i] = i;
        }

        int target = 842;

        int result = binarySearch(sortedArray, target, 0, sortedArray.length - 1);

        if(result != -1){
            System.out.println("Target of: " + target + " was found at index " + result);
        }else{
            System.out.println("Target of: " + target + " was not found in the array.");
        }
        System.out.println("Total function calls: " + totalCalls);
    }

    private static int binarySearch(int[] array, int target, int low, int high){
        // Increment out total function calls
        totalCalls++;

        // Do our base cases first
        if(low > high){
            return -1;
        }

        // Constantly adjust to get middle of array
        int middle = low + (high - low) / 2;

        // Target found 
        if(array[middle] == target){
            return middle;
        }

        // Search the left half of the array
        if(target < array[middle]){
            return binarySearch(array, target, low, middle - 1);
        }

        // Search the right half of the array
        return binarySearch(array, target, middle + 1, high);
    }
}
