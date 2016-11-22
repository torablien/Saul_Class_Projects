package ContractAnalysis;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StringReader {
    public ArrayList<String> allStrings = new ArrayList<>();

    public StringReader(String fileName)  {
        Scanner scan;
        File file = new File(fileName);

        try {
            scan = new Scanner(file);
            while(scan.hasNext())
            {
                allStrings.add(scan.next());
            }

        } catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }

    }
}