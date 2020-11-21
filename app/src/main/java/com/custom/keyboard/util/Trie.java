package com.custom.keyboard.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Trie {
    public TrieNode root;
    public ArrayList<String> words;
    public ArrayList<TrieNode> termini;
    public TrieNode prefixRoot;
    public String curPrefix;

    public Trie() {
        root = new TrieNode();
        words = new ArrayList<>();
        termini = new ArrayList<>();
    }

    public void insert(String word) {
        insert(word, 1);
    }

    public void insert(String word, long value) {
        HashMap<Character,TrieNode> children = root.children;

        TrieNode crntparent;

        crntparent = root;

        int i;
        char c;
        for (i = 0; i < word.length(); i++) {
            c = word.charAt(i);

            TrieNode t;

            if (children.containsKey(c)) {
                t = children.get(c);
            }
            else if (crntparent == null) {
                continue;
            }
            else {
                t = new TrieNode(c);
                t.word = crntparent.word != null ? crntparent.word+c : ""+c;
                t.parent = crntparent;
                children.put(c, t);
            }

            if (t != null) {
                children = t.children;
            }
            crntparent = t;

            if (i == word.length()-1 && t != null) {
                t.isLeaf = true;
                t.value = value;
            }
        }
    }

    public boolean search(String word) {
        TrieNode t = searchNode(word);
        return (t != null && t.isLeaf);
    }

    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    public TrieNode searchNode(String str) {
        Map<Character,TrieNode> children = root.children;
        TrieNode t = null;
        int i;
        char c;
        for(i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                if (t != null) {
                    children = t.children;
                }
            }
            else {
                return null;
            }
        }

        prefixRoot = t;
        curPrefix = str;
        words.clear();
        termini.clear();
        return t;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void wordsFinderTraversal(TrieNode node, int offset) {

        if (node.isLeaf) {
            termini.add(node);
            words.add(node.word);
        }

        Set<Character> kset = node.children.keySet();
        Iterator<Character> itr = kset.iterator();
        ArrayList<Character> aloc = new ArrayList<>();

        while(itr.hasNext()) {
            Character ch = (Character)itr.next();
            aloc.add(ch);
        }

        // here you can play with the order of the children
        int i;
        for(i = 0; i < aloc.size(); i++) {
            wordsFinderTraversal(Objects.requireNonNull(node.children.get(aloc.get(i))), offset + 2);
        }
    }
}