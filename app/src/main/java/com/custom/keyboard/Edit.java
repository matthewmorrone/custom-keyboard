package com.custom.keyboard;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Edit {

    static Trie trie = new Trie();

    public Edit(Context context) {
        buildTrie(context, R.raw.words_82765);
    }

    public static boolean inTrie(String word) {
        return trie.search(word);
    }
    
    public static boolean isPrefix(String word) {
        return trie.startsWith(word);
    }
    
    public static ArrayList<String> getCompletions(String word) {
        TrieNode tn = trie.searchNode(word);
        trie.wordsFinderTraversal(tn, 0);
        ArrayList<TrieNode> result = trie.termini;
        Collections.sort(result);
        if (result.size() > 10) {
            result = new ArrayList<>(result.subList(0, 10));
        }

        ArrayList<String> strings = new ArrayList<>();
        for (TrieNode trieNode : result) {
            String trieNodeWord = trieNode.getWord();
            strings.add(trieNodeWord);
        }
        return strings;
    }

    private void buildTrie(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            String[] pair;
            while ((string = bufferedReader.readLine()) != null) {
                pair = string.split(" ");
                trie.insert(pair[0], Long.parseLong(pair[1]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    /*
    static Map<String,String> typos = new HashMap<>();

    public Edit(Context context) {
        readFile(context, R.raw.typos);
        readFile(context, R.raw.typos_more);
    }

    private void readFile(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            String[] pair;
            while ((string = bufferedReader.readLine()) != null) {
                pair = string.split(",");
                if (pair.length > 1 && typos.get(pair[0]) == null) {
                    typos.put(pair[0], pair[1]);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    /*
    public void add(String src, String trg) {
        src = src.trim();
        trg = trg.trim();
        typos.put(src, trg);
    }

    public String check(String word) {
        word = word.trim();
        String repl = typos.get(word);

        int minScore = 1024, score;
        String result = "";
        for (String line : list) {
            line = line.trim();
            score = damerauLevenshtein(word, line);
            if (score < minScore) {
                minScore = score;
                result = line;
            }
        }
        return result;
    }

    private static void changeKey(String oldKey, String newKey) {
        String value = typos.remove(oldKey);
        typos.put(newKey, value);
    }
    
    public static int damerauLevenshtein(CharSequence source, CharSequence target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Parameter must not be null");
        }
        int sourceLength = source.length();
        int targetLength = target.length();
        if (sourceLength == 0) return targetLength;
        if (targetLength == 0) return sourceLength;
        int[][] dist = new int[sourceLength + 1][targetLength + 1];
        for (int i = 0; i < sourceLength + 1; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j < targetLength + 1; j++) {
            dist[0][j] = j;
        }
        for (int i = 1; i < sourceLength + 1; i++) {
            for (int j = 1; j < targetLength + 1; j++) {
                int cost = source.charAt(i - 1) == target.charAt(j - 1) ? 0 : 1;
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + cost);
                if (i > 1 && j > 1 && source.charAt(i - 1) == target.charAt(j - 2) && source.charAt(i - 2) == target.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + cost);
                }
            }
        }
        return dist[sourceLength][targetLength];
    }
    */
}
