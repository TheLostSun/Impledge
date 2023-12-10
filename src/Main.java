import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args)  {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Text file Name with extension!");
        String fileName=sc.next();
        String[] results =compoundedWordsFinder("src\\"+fileName);
        System.out.println("Longest Compound Word: " + results[0]);
        System.out.println("Second Longest Compound Word: " + results[1]);
        System.out.println("Time taken to process file " + fileName + ": " + results[2] + " milli seconds\n");
    }
    public static String[] compoundedWordsFinder(String fileName)  {
        long startTime = System.nanoTime();

        Set<String> wordSet = new HashSet<>();
        List<String> words = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim();
                words.add(word);
                wordSet.add(word);
            }
        }
        catch(IOException e){
            System.out.println("File Not Found.");
        }

        words.sort(Comparator.comparing(String::length)); // Sort words by length ascending

        String longestCompound = "";
        String secondLongestCompound = "";

        for (int i = words.size() - 1; i >= 0; i--) {
            if (isCompound(words.get(i), wordSet)) {
                if (longestCompound.isEmpty()) {
                    longestCompound = words.get(i);
                } else if (secondLongestCompound.isEmpty()) {
                    secondLongestCompound = words.get(i);
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        long processingTime = (endTime - startTime) / 1_000_000; // in milliseconds

        return new String[]{longestCompound, secondLongestCompound, String.valueOf(processingTime)};
    }

    public static boolean isCompound(String word, Set<String> wordSet) {
        for (int i = 1; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);
            if (wordSet.contains(prefix) && (wordSet.contains(suffix) || isCompound(suffix, wordSet))) {
                return true;
            }
        }
        return false;
    }
}
