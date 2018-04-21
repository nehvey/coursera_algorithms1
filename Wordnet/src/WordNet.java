import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {

    private final Map<String, List<Integer>> synsetsMap;
    private final Map<Integer, String> synsetIdToWord;
    private final Digraph hypernymsDigraph;
    private int synsetsSize = 0;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        synsetsMap = new HashMap<>();
        synsetIdToWord = new HashMap<>();
        readSynsets(new In(synsets));

        hypernymsDigraph = new Digraph(synsetsSize);
        readHypernyms(new In(hypernyms));
    }

    private void readSynsets(In in) {
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] split = line.split(",");
            String[] synset = split[1].split(" ");
            Integer synsetId = Integer.valueOf(split[0]);
            for (String s : synset) {
                synsetsSize++;
                synsetIdToWord.put(synsetId, s);
                if (synsetsMap.containsKey(s)) {
                    synsetsMap.get(s).add(synsetId);
                } else {
                    List<Integer> synsetIds = new ArrayList<>();
                    synsetIds.add(synsetId);
                    synsetsMap.put(s, synsetIds);
                }
            }
        }
    }

    private void readHypernyms(In in) {
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] split = line.split(",");
            final int synsetId = Integer.valueOf(split[0]);
            for (int n = 1; n < split.length; n++) {
                hypernymsDigraph.addEdge(synsetId, Integer.valueOf(split[n]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        SAP sap = new SAP(hypernymsDigraph);
        return sap.length(synsetsMap.get(nounA), synsetsMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP sap = new SAP(hypernymsDigraph);
        int ancestorId = sap.ancestor(synsetsMap.get(nounA), synsetsMap.get(nounB));
        // TODO!!!
        return synsetIdToWord.get(ancestorId);
    }

    // do unit testing of this class
    public static void main(String[] args) {
//		In in = new In(args[0]);
//		Digraph G = new Digraph(in);
//		SAP sap = new SAP(G);
//		while (!StdIn.isEmpty()) {
//			int v = StdIn.readInt();
//			int w = StdIn.readInt();
//			int length = sap.length(v, w);
//			int ancestor = sap.ancestor(v, w);
//			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//		}

        WordNet wordNet = new WordNet(args[0], args[1]);
        System.out.println(wordNet.distance("white_marlin", "mileage"));
        System.out.println(wordNet.distance("Makaira_albida", "mileage"));

    }
}
