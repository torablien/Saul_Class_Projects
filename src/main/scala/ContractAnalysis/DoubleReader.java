package ContractAnalysis;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DoubleReader {
    public ArrayList<Double> doubles = new ArrayList<>();

    public DoubleReader(String fileName)  {
        Scanner scan;
        File file = new File(fileName);

        try {
            scan = new Scanner(file);
            while(scan.hasNextDouble())
            {
                doubles.add(scan.nextDouble());
            }

        } catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }

    }
}