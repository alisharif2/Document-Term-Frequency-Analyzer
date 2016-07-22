import java.util.*;
import java.io.*;

public class KeywordExtractor {
  public static void main(String[] args) {

    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = readDocumentFrequencies("freqs.txt");
    for(int i = 0;i < 40;++i) {
      System.out.println((i + 1) + ".txt");
      HashMap<String, Integer> tfs = computeTermFrequencies(dir + "/" + (i + 1) + ".txt");
      printTopKeywords(computeTFIDF(tfs, dfs, 40), 5);
      System.out.println();
    }
  }

  public static HashMap<String, Integer> computeTermFrequencies(String filename) {
    HashSet<String> wordList = DocumentFrequency.extractWordsFromDocument(filename);
    ArrayList<String> wordsFromFile = new ArrayList<String>();
    HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();

    String wordsRaw[] = DocumentFrequency.readFile(filename).split(" ");
    for(String word : wordsRaw) wordsFromFile.add(DocumentFrequency.normalize(word));
    for(String word : wordList) wordFrequencies.put(word, Collections.frequency(wordsFromFile, word));
    return wordFrequencies;
  }

  public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
    HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();
    try {
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
        String splitLine[] = line.split(" ");
        wordFrequencies.put(splitLine[0], Integer.parseInt(splitLine[1]));
			}
			fileReader.close();
			return wordFrequencies;
		} catch (IOException e) {
			e.printStackTrace();
      System.err.print("\nSomething went wrong trying to read file: " + filename);
      System.exit(1);
		}
    return wordFrequencies;
  }

  public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs, double nDocs) {
    // TODO
    Iterator<String> iter = tfs.keySet().iterator();
    HashMap<String, Double> output = new HashMap<String, Double>();
    while(iter.hasNext()) {
      String key = iter.next();
      output.put(key, tfs.get(key) * Math.log(nDocs/dfs.get(key)));
    }
    return output;
  }

  /**
   * This method prints the top K keywords by TF-IDF in descending order.
   */
  public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
    ValueComparator vc =  new ValueComparator(tfidfs);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
    sortedMap.putAll(tfidfs);

    int i = 0;
    for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();

      System.out.println(key + " " + value);
      i++;
      if (i >= k) {
        break;
      }
    }
  }
}

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {

    Map<String, Double> map;

    public ValueComparator(Map<String, Double> base) {
      this.map = base;
    }

    public int compare(String a, String b) {
      if (map.get(a) >= map.get(b)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys
    }
  }
