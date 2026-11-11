import java.io.*;
import java.util.*;

public class SalesReport {

    static void manualReport(String[] files) {
        System.out.println("\nManual Closing:");

        for (String file : files) {
            BufferedReader br = null;

            try {
                br = new BufferedReader(new FileReader(file));

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                System.out.println("Error reading " + file);

            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    System.out.println("Error closing file");
                }
            }
        }
    }

    static void safeReport(String[] files) {
        System.out.println("\nTry-with-resources:");

        for (String file : files) {
            try (BufferedReader br =
                    new BufferedReader(new FileReader(file))) {

                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                System.out.println("Error reading " + file);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of store files: ");
        int n = sc.nextInt();
        sc.nextLine();

        String[] files = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter file name: ");
            files[i] = sc.nextLine();
        }

        manualReport(files);
        safeReport(files);

        sc.close();
    }
}