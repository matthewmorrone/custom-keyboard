package com.custom.keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class TrieNode implements Comparable<TrieNode> {
    char c;
    String word;
    TrieNode parent;
    HashMap<Character,TrieNode> children = new HashMap<>();
    boolean isLeaf;
    long value;

    public TrieNode() {}
    public TrieNode(char c) {
        this.c = c;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return this.word+"("+this.value+")";
    }

    @Override
    public int compareTo(TrieNode that) {
        if (that.word.length() == this.word.length()) {
            return (int)(that.value - this.value);
        }
        return this.word.length() - that.word.length();
    }
}

public class Trie {
    private TrieNode root;
    ArrayList<String> words;
    ArrayList<TrieNode> termini;
    TrieNode prefixRoot;
    String curPrefix;
 
    public Trie() {
        root = new TrieNode();
        words = new ArrayList<>();
        termini = new ArrayList<>();
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
            else {
                t = new TrieNode(c);
                t.word = crntparent.word != null ? crntparent.word+c : ""+c;
                t.parent = crntparent;
                children.put(c, t);
            }
         
            children = t.children;
            crntparent = t;

            if (i == word.length()-1) {
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
                children = t.children;
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
    
    ArrayList<String> getWords() {
        return words;
    }
    
    void wordsFinderTraversal(TrieNode node, int offset) {
        if (node.isLeaf) {
            termini.add(node);
            words.add(node.word);
        }
     
        Set<Character> kset = node.children.keySet();
        Iterator itr = kset.iterator();
        ArrayList<Character> aloc = new ArrayList<>();

        while(itr.hasNext()) {
            Character ch = (Character)itr.next();  
            aloc.add(ch);
        } 
     
        // here you can play with the order of the children
        int i;
        for(i = 0; i < aloc.size(); i++) {
            wordsFinderTraversal(node.children.get(aloc.get(i)), offset + 2);
        }
    }
}