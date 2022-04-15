/** This program has 4 different types of Maximum Subsequence Sums, also known
* as MSS. These MSS will be structurally different, and have different run
* times, which we will calculate and graph on our project writeup.
*
* Names: Matthew Gloriani and Kevin Nhu
*
* DLM: March 21, 2021
*
*/

import java.util.*;
import java.math.*;
import java.io.*;
  
 public class mss {
     public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        while (true) {
            // this gets the array
            int[] array = FileReader();
 
            // finds the smallest number and sets the number as the maxSum
            int maxSum = array[0];
            for (int i = 0; i < array.length; i++) {
                if (array[i] < maxSum) {
                    maxSum = array[i];
                  }
            }
 
            // instructions for running program
            int userInt = 0;
            while (userInt < 1 || userInt > 5) {
                System.out.println("Please enter a number 1-5, where 1-4 prints out individual "
                                    + "mss methods and 5 prints out all 5 mss methods." + '\n'
                                    + "For example, '1' prints out mss1 information.");
                userInt = kb.nextInt();
            }
            RunMethods(array, maxSum, userInt);
 
            // asks user if want to run again
            System.out.println("Do you want to run the program again? (y for yes and n for no)");
            String decision = kb.next();
            System.out.println();
            if (decision.charAt(0) == 'n') {
                System.exit(1);
            }
        }
    }
 
    public static void RunMethods(int[] array, int maxSum, int userInt) {
        // Finds sums and finds runtime
        long startTime, endTime;
        long mss1Time, mss2Time, mss3Time, mss4Time;
        int sum1, sum2, sum3, sum4;
 
        // MSS1
        if (userInt == 1 || userInt == 5) {
            startTime = System.nanoTime();
            sum1 = mss1(array, maxSum);
            endTime = System.nanoTime();
            System.out.println("MSS1 sum is " + sum1 + " and the time elapsed is " + (endTime - startTime));
        }
 
        // MSS2
        if (userInt == 2 || userInt == 5) {
            startTime = System.nanoTime();
            sum2 = mss2(array, maxSum);
            endTime = System.nanoTime();
            System.out.println("MSS2 sum is " + sum2 + " and the time elapsed is " + (endTime - startTime));
        }
 
        // MSS3
        if (userInt == 3 || userInt == 5) {
            startTime = System.nanoTime();
            sum3 = mss3(array, 0, array.length-1, maxSum);
            endTime = System.nanoTime();
            System.out.println("MSS3 sum is " + sum3 + " and the time elapsed is " + (endTime - startTime));
        }
        // MSS4
        if (userInt == 4 || userInt == 5) {
            startTime = System.nanoTime();
            sum4 = mss4(array, maxSum);
            endTime = System.nanoTime();
            System.out.println("MSS4 sum is " + sum4 + " and the time elapsed is " + (endTime - startTime));
        }
    }
    /** This method returns the array from the file being read
     * @return - the array
     */
    public static int[] FileReader() {
        while (true) {
            System.out.println("Please enter the name of an ASCII file that "
                            + "contains a sorted list of integer numbers "
                            + "all in one line, separated by a comma: ");
            Scanner scan = new Scanner(System.in);
            String userFile = scan.nextLine();
            File infile;
            BufferedReader input;
            StringTokenizer tokens;
            String inputLine;
            try {
                infile = new File(userFile);
                input = new BufferedReader(new FileReader(infile));
                inputLine = input.readLine();
                tokens = new StringTokenizer(inputLine, ",");
                while ((inputLine = input.readLine()) != null) {
                    tokens = new StringTokenizer(inputLine, ",");
                }
                input.close();
                int[] array = new int[tokens.countTokens()];
                int counter = 0;
                while (tokens.hasMoreTokens()) {
                    array[counter] = Integer.parseInt(tokens.nextToken());
                    counter++;
                }
                return array;
            }
            catch (IOException e) {
                System.out.println("Please enter an existing file name.");
            }
        }
    }

    /**This is for O(n^3), derived from notes
     * @param array - the array given to us
     * @param maxSum - starts with the smallest number in the array
     * @return - the maximum subsequence sum
     */
    public static int mss1(int[] array, int maxSum) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += array[k];
                }
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
        }
        return maxSum;
    }

    /**This is for O(n^2), derived from notes
     * @param array - the array given to us
     * @param maxSum - starts with the smallest number in the array
     * @return - the maximum subsequence sum
     */
    public static int mss2(int[] array, int maxSum) {
        for (int i = 0; i < array.length; i++) {
            int sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
        }
        return maxSum;
    }

    /**This is for O(nlogn) - based off of a binary search, but isn't a binary
     * serach
     * @param array - the array given to us
     * @param start - the starting point of the recursiveness and starter
     * @param end - the ending point of the recursiveness and starter
     * @param maxSum - starts with the smallest number in the array
     * @return - the maximum subsequence sum
     */
    public static int mss3(int[] array, int start, int end, int maxSum) {
        // base case, basically if there's only one element left
        if (start == end) {
            return array[start]; // returns the value if only one element in array
        }

        // Now finding the middle of the partial arrays
        int mid = (start + end) / 2;

        // returns the max value of the arrays
        return Math.max(Math.max(mss3(array, start, mid, maxSum), mss3(array, mid+1, end, maxSum)),
                        mss3Helper(array, start, mid, end, maxSum));
    }

    /**This is the helper fuction for doing mss3, calculates left and right
     * sums of each side; the for loops are based off of mss4, so that we can
     * find the max sum in the left and right sides.
     * @param array - the array given to us
     * @param start - the starting point of the recursiveness
     * @param mid - the middle point of the recursiveness
     * @param end - the end point of the recursiveness
     * @param maxSum - starts with the smallest number in the array
     * @return - the max value of the left and right sums
     */
    public static int mss3Helper(int[] array, int start, int mid, int end, int maxSum) {
        // calculates left sum
        int sum = 0;
        int leftSum = maxSum;
        for (int i = mid; i >= start; i--) {
            sum += array[i];
            if (sum >= leftSum) {
                leftSum = sum;
            }
        }

        // calculates right sum
        sum = 0;
        int rightSum = maxSum;
        for (int i = mid + 1; i <= end; i++) {
            sum += array[i];
            if (sum >= rightSum) {
                rightSum = sum;
            }
        }

        // got these returns value from lecture notes:
        // "Return max (maxLeftSum, maxRightSum, 
        // maxLeftBoundSum + maxRightBoundSum)
        return Math.max(Math.max(leftSum, rightSum), leftSum + rightSum);
    }

    /** This is for O(n), derived from notes
     * @param array - the array given to us
     * @param maxSum - starts with the smallest number in the array
     * @return - the maximum subsequence sum
     */
    public static int mss4(int[] array, int maxSum) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (sum > maxSum) {
                maxSum = sum;
            }
            else {
                if (sum < 0) {
                    sum = 0;
                }
            }
        }
        return maxSum;
    }
}
