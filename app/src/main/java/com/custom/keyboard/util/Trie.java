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
    public String currentPrefix;

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

        TrieNode currentParent = root;

        int i;
        char c;
        for (i = 0; i < word.length(); i++) {
            c = word.charAt(i);

            TrieNode trieNode;

            if (children.containsKey(c)) {
                trieNode = children.get(c);
            }
            else if (currentParent == null) {
                continue;
            }
            else {
                trieNode = new TrieNode(c);
                trieNode.word = currentParent.word != null ? currentParent.word+c : ""+c;
                trieNode.parent = currentParent;
                children.put(c, trieNode);
            }

            if (trieNode != null) {
                children = trieNode.children;
            }
            currentParent = trieNode;

            if (i == word.length()-1 && trieNode != null) {
                trieNode.isLeaf = true;
                trieNode.value = value;
            }
        }
    }

    public boolean search(String word) {
        TrieNode trieNode = searchNode(word);
        return trieNode != null && trieNode.isLeaf;
    }

    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    public TrieNode searchNode(String word) {
        Map<Character,TrieNode> children = root.children;
        TrieNode trieNode = null;
        int i;
        char c;
        for(i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            if (children.containsKey(c)) {
                trieNode = children.get(c);
                if (trieNode != null) {
                    children = trieNode.children;
                }
            }
            else {
                return null;
            }
        }

        prefixRoot = trieNode;
        currentPrefix = word;
        words.clear();
        termini.clear();
        return trieNode;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void findWords(TrieNode node, int offset) {
        if (node.isLeaf) {
            termini.add(node);
            words.add(node.word);
        }

        Set<Character> keySet = node.children.keySet();
        Iterator<Character> iterator = keySet.iterator();
        ArrayList<Character> chars = new ArrayList<>();

        while(iterator.hasNext()) {
            chars.add(iterator.next());
        }

        int i;
        for(i = 0; i < chars.size(); i++) {
            findWords(Objects.requireNonNull(node.children.get(chars.get(i))), offset + 2);
        }
    }
}