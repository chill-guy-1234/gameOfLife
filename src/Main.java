import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.lang.Thread;
import java.security.MessageDigest;

public class Main {
    public static boolean checkLeftTop(int x, int y) {
        if ((x - 1 < 0 || y - 1 < 0)) return false;
        return true;
    }

    public static boolean checkTop(int x) {
        if (x - 1 < 0) return false;
        return true;
    }

    public static boolean checkRightTop(int x, int y, int dimension) {
        if (x - 1 < 0 || y + 1 >= dimension) return false;
        return true;
    }

    public static boolean checkBottomLeft(int x, int y, int dimension) {
        if (x + 1 >= dimension || y - 1 < 0) return false;
        return true;
    }

    public static boolean checkBottom(int x, int dimension) {
        if (x + 1 >= dimension) return false;
        return true;
    }

    public static boolean checkBottomRight(int x, int y, int dimension) {
        if (x + 1 >= dimension || y + 1 >= dimension) return false;
        return true;
    }

    public static boolean checkLeft(int y) {
        if (y - 1 < 0) return false;
        return true;
    }

    public static boolean checkRight(int y, int dimension) {
        if (y + 1 >= dimension) return false;
        return true;
    }

    public static int liveCount(int x, int y, int dimension, int[][] arr) {
        int count = 0;
        if (checkLeftTop(x, y) && arr[x - 1][y - 1] != 0) count++;
        if (checkTop(x) && arr[x - 1][y] != 0) count++;
        if (checkRightTop(x, y, dimension) && arr[x - 1][y + 1] != 0) count++;
        if (checkLeft((y)) && arr[x][y - 1] != 0) count++;
        if (checkRight(y, dimension) && arr[x][y + 1] != 0) count++;
        if (checkBottomLeft(x, y, dimension) && arr[x + 1][y - 1] != 0) count++;
        if (checkBottom(x, dimension) && arr[x + 1][y] != 0) count++;
        if (checkBottomRight(x, y, dimension) && arr[x + 1][y + 1] != 0) count++;
        return count;
    }

    public static boolean shouldLiveNextIter(int liveCount, int isAlive){
        if (isAlive != 0){
            if (liveCount < 2 || liveCount > 4) return false;
        }
        if(liveCount == 3) return true;
        return false;
    }

    public static void printGrid(int dimension, int [][] grid){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }


    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the dimension of the  grid");
        int dimension = scanner.nextInt();
        int[][] grid = new int[dimension][dimension];

        String hashInput = "";
        String [] hashes = new String[999];
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        System.out.println("Enter the initial state of grid");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = scanner.nextInt();
            }
            System.out.println();
        }

        System.out.println("The inital state is\n");
        printGrid(dimension, grid);

        int i = 0;
        while(true){

            hashInput= "";
            int [][] bkp_grid = Arrays.stream(grid)
                    .map(int[]::clone)
                    .toArray(int[][]::new);

            for (int x = 0;x < dimension; x++){
                for (int y = 0; y< dimension ; y++){
                    if(shouldLiveNextIter(liveCount(x,y,dimension,bkp_grid),bkp_grid[x][y])) grid[x][y] = 1;
                    else grid[x][y] = 0;
                    hashInput = hashInput.concat(String.valueOf(x + y + grid[x][y])) ;
                }
            }


            System.out.println("iteration" + (i+1));
            printGrid(dimension, grid);

            byte[] hash = md.digest(hashInput.getBytes());
            String hashString = new String(hash);

            hashes [i] = hashString;
            if (i > 0 && (Objects.equals(hashes[i], hashes[i - 1]))){
                System.out.println("Terminate state. No more iterations");
                break;
            }
            i++;
            Thread.sleep(3000);
        }

    }
}
