package com.custom.keyboard;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
// import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class SpellChecker {

    public static class Entry {
        private int score;
        private String word;

        public Entry(int score, String word) {
            this.score = score;
            this.word = word;
        }

        public int getScore() { return score; }
        public String getWord() { return word; }
        public void setScore(int score) { this.score = score; }
        public void setWord(String word) { this.word = word; }
    }

    static Comparator<String> byLength = new Comparator<String>(){
        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    };

    static Comparator<TrieNode> comparator = new Comparator<TrieNode>() {
        @Override
        public int compare(TrieNode t1, TrieNode t2) {
            return (int)(t2.value - t1.value);
        }
    };

    static Comparator<Entry> prioritizer = new Comparator<Entry>() {
        @Override
        public int compare(Entry e1, Entry e2) {
            if (e1.getScore() == e2.getScore()) {
                return e2.getWord().length() - e1.getWord().length();
            }
            return e2.score - e1.score;
        }
    };

    static Trie trie = new Trie();
    static Map<String,String> typos = new HashMap<>();
    static ArrayList<String> list = new ArrayList<>();

    public SpellChecker(Context context) {
        this.buildTrie(context, R.raw.words_82765);
    }

    public SpellChecker(Context context, boolean typoCheck) {
        this.buildTrie(context, R.raw.words_82765);
        if (typoCheck) {
            this.readFile(context, R.raw.typos);
        }
    }

    public void addToTrie(String text) {
        trie.insert(text, 1);
    }

    public static boolean inTrie(String word) {
        return trie.search(word);
    }
    
    public static boolean isPrefix(String word) {
        return trie.startsWith(word);
    }

    public static ArrayList<String> getCompletions(String word) {
        return getCompletions(word, 10);
    }

    public static ArrayList<String> getCompletions(String word, int limit) {
        TrieNode tn = trie.searchNode(word);
        trie.wordsFinderTraversal(tn, 0);
        ArrayList<TrieNode> result = trie.termini;

        result.sort(comparator);
        if (result.size() > limit) {
            result = new ArrayList<>(result.subList(0, limit));
        }
        ArrayList<String> strings = new ArrayList<>();
        for (TrieNode trieNode : result) {
            strings.add(trieNode.getWord());
        }
        strings.remove(word);
        // strings.sort(byLength);
        return strings;
    }

    public static ArrayList<String> getSuggestions(String word) {
        return getSuggestions(word, 1);
    }

    public static ArrayList<String> getCommon(String word) {
        word = word.trim();
        ArrayList<String> result = new ArrayList<>();
        if (typos.get(word) != null) {
            result.add(typos.get(word));
        }
        return result;
    }

    public static ArrayList<String> getSuggestions(String word, int limit) {

        word = word.trim();
        ArrayList<String> result = new ArrayList<>();

        if (typos.size() < 1) {
            return result;
        }

        if (typos.get(word) != null) {
            result.add(typos.get(word));
            return result;
        }

        PriorityQueue<Entry> topN = new PriorityQueue<>(limit, prioritizer);

        int score;
        for (String line : list) {
            line = line.trim();
            score = damerauLevenshtein(word, line);
            topN.add(new Entry(score, line));
            if (topN.size() > limit) topN.poll();
        }

        for(Entry entry : topN) {
            result.add(entry.getWord());
        }
        return result;
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

    private void buildTrie(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            String[] pair;
            while ((string = bufferedReader.readLine()) != null) {
                pair = string.split(" ");
                trie.insert(pair[0], Long.parseLong(pair[1]));
                list.add(pair[0]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile(Context context, int id) {
        InputStream inputStream = context.getResources().openRawResource(id);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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

}

