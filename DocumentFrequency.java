import java.util.*;
import java.io.*;
import java.nio.file.*;

public class DocumentFrequency {

  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = extractDocumentFrequencies(dir, 40);
    writeDocumentFrequencies(dfs, "freqs.txt");
  }

  public static HashMap<String, Integer> extractDocumentFrequencies(String directory, int nDocs) {
    ArrayList<HashSet<String>> documentWordLists = new ArrayList<HashSet<String>>();
    HashMap<String, Integer> wordFrequencies = new HashMap<String, Integer>();
    HashSet<String> checkedWords = new HashSet<String>();

    for(int i = 0;i < nDocs;++i) {
      documentWordLists.add(extractWordsFromDocument(directory + "/" + (i + 1) + ".txt"));
    }

    for(int i = 0;i < documentWordLists.size();++i) {
      Iterator<String> iter = documentWordLists.get(i).iterator();
      while(iter.hasNext()) {
        String word = "" + iter.next();
        int occurrences = getFreqInSets(word, documentWordLists);
        if(wordFrequencies.containsKey(word) && !checkedWords.contains(word)) wordFrequencies.put(word, wordFrequencies.get(word) + occurrences);
        else {
          wordFrequencies.put(word, occurrences);
          checkedWords.add(word);
        }
      }
    }

    return wordFrequencies;
  }

  public static int getFreqInSets(String str, ArrayList<HashSet<String>> sets) {
    int occurrences = 0;
    for(int i = 0;i < sets.size();++i) {
      if(sets.get(i).contains(str)) ++occurrences;
    }
    return occurrences;
  }

  public static HashSet<String> extractWordsFromDocument(String filename) {
    HashSet<String> wordList = new HashSet<String>();
    String[] tmpWordList = readFile(filename).split(" ");
    for(String word : tmpWordList) {
      if(!normalize(word).equals("")) wordList.add(normalize(word));
    }
    return wordList;
  }

  public static String readFile(String filename) {
    try {
			File file = new File(filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append(" ");
			}
			fileReader.close();
			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
      System.err.print("\nSomething went wrong trying to read file: " + filename);
      System.exit(1);
		}
    return "";
  }

  public static ArrayList<String> hashMapKeysToArrayList(HashMap<String, Integer> set) {
    ArrayList<String> keys = new ArrayList<String>();
    for( String key : set.keySet()) keys.add(key);
    return keys;
  }

  public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {
    ArrayList<String> keyList = hashMapKeysToArrayList(dfs);
    Collections.sort(keyList);
    try {
      PrintWriter writer = new PrintWriter(filename, "UTF-8");
      while(!keyList.isEmpty()) {
        writer.println(keyList.get(0) + " " + dfs.get(keyList.get(0)));
        keyList.remove(0);
      }
      writer.close();
    } catch(IOException e) {
      System.err.println("\n Something went wrong while writing the word document frequencies");
      System.exit(1);
    }
  }

  /*
   * This method "normalizes" a word, stripping extra whitespace and punctuation.
   * Do not modify.
   */
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }

}
