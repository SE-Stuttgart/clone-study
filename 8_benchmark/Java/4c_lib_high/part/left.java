import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class left {

    private FileReader fileReader = null;
    private BufferedReader bufferedReader = null;

    private FileWriter fileWriter = null;
    private BufferedWriter bufferedWriter = null;

    public void init(String inputFileName, String outputFileName)
            throws IOException {
        reset();

        fileReader = new FileReader(inputFileName);
        bufferedReader = new BufferedReader(fileReader);

        fileWriter = new FileWriter(outputFileName);
        bufferedWriter = new BufferedWriter(fileWriter);
    }


    public void reset() {
        closeCloseable(bufferedReader);
        closeCloseable(fileReader);

        closeCloseable(bufferedWriter);
        closeCloseable(fileWriter);

        bufferedReader = null;
        fileReader = null;
        bufferedWriter = null;
        fileWriter = null;
    }


    public void execute()
            throws IOException {
        String line = bufferedReader.readLine();
        int numCases = Integer.parseInt(line.trim());

        for (int caseNum = 0; caseNum < numCases; caseNum++) {
            line = bufferedReader.readLine().trim();

            int p = Integer.parseInt(line);

            int size = 1 << p;
            int[] contraints = new int[size];

            line = bufferedReader.readLine().trim();
            String[] split = line.split(" ");
            for (int i = 0; i < size; i++) {
                contraints[i] = Integer.parseInt(split[i]);
            }

            int[][] pricingList = new int[size][size];
            for (int i = 0; i < p; i++) {
                line = bufferedReader.readLine().trim();
                split = line.split(" ");

                for (int j = 0; j < split.length; j++) {
                    pricingList[i][j] = Integer.parseInt(split[j]);
                }
            }

            long totalCost = 0;
            for (int ind = 0; ind < size; ind++) {
                int index = ind / (int) Math.pow(2, contraints[ind]);

                for (int i = contraints[ind]; i < p; i++) {
                    index = index / 2;

                    if (pricingList[i][index] >= 0) {
                        totalCost += pricingList[i][index];
                        pricingList[i][index] = -1;
                    } else {
                        break;
                    }
                }
            }

            bufferedWriter.write("Case #" + (caseNum + 1) + ": " + totalCost + "\n");
            System.out.println("Case #" + (caseNum + 1) + ": " + totalCost);
        }

        bufferedWriter.flush();
    }

    private void closeCloseable(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                System.err.println("Failed to close closeable: " + e);
            }
        }
    }

    public static void main(String[] args) {
        left prog = new left();

        try {
            String pathPrefix = "C:\\";
            String filename = "B";
            prog.init(pathPrefix + filename + ".in", pathPrefix + filename + ".out");

            long startTime = System.currentTimeMillis();

            prog.execute();

            System.out.println("Runtime: " + ((System.currentTimeMillis() - startTime) / 1000));
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            prog.reset();
        }
    }
}
