package com.vlath.keyboard;

import java.util.HashMap;

public class Replacements {
    
    private static HashMap<String, String> data = new HashMap<>();
    
    public Replacements() {}

    // private static void changeKey(String oldKey, String newKey) {
    //     String value = data.remove(oldKey);
    //     data.put(newKey, value);
    // }

    // private static void prepData() {}

    private static void makeData() {

        data.put("\\bi", "I");
        data.put("\\byoy", "you");      data.put("\\byoyou", "you"); 
        data.put("\\bcant", "can't");   data.put("\\bcantt", "cant");
        data.put("\\bhell", "he'll");   data.put("\\bhelll", "hell");
        data.put("\\bid", "I'd");       data.put("\\bidd", "id");
        data.put("\\bill", "I'll");     data.put("\\billl", "ill");
        data.put("\\bits", "it's");     data.put("\\bitss", "its");
        data.put("\\blets", "let's");   data.put("\\bletss", "lets");
        data.put("\\bshed", "she'd");   data.put("\\bshedd", "shed");
        data.put("\\bshell", "she'll"); data.put("\\bshelll", "shell");
        data.put("\\bwed", "we'd");     data.put("\\bwedd", "wed");
        data.put("\\bwell", "we'll");   data.put("\\bwelll", "well");
        data.put("\\bwere", "we're");   data.put("\\bweree", "were");
        data.put("\\bwhore", "who're"); data.put("\\bwhoree", "whore");

        data.put("\\barent", "aren't");
        data.put("\\bdont", "don't");
        data.put("\\bhed", "he'd");
        data.put("\\bhes", "he's");
        data.put("\\bhows", "how's");
        data.put("\\bim", "I'm");
        data.put("\\bive", "I've");
        data.put("\\bshes", "she's");
        data.put("\\btis", "'tis");
        data.put("\\bweve", "we've");

        data.put("\\baint", "ain't");
        data.put("\\bamnt", "amn't");
        data.put("\\bcouldve", "could've");
        data.put("\\bcouldnt", "couldn't");
        data.put("\\bcouldntve", "couldn't've");
        data.put("\\bdarent", "daren't");
        data.put("\\bdaresnt", "daresn't");
        data.put("\\bdasnt", "dasn't");
        data.put("\\bdidnt", "didn't");
        data.put("\\bdoesnt", "doesn't");
        data.put("\\beveryones", "everyone's");
        data.put("\\bhadnt", "hadn't");
        data.put("\\bhasnt", "hasn't");
        data.put("\\bhavent", "haven't");
        data.put("\\bhowd", "how'd");
        data.put("\\bhowll", "how'll");
        data.put("\\bhowre", "how're");
        data.put("\\bisnt", "isn't");
        data.put("\\bitd", "it'd");
        data.put("\\bitll", "it'll");
        data.put("\\bmaam", "ma'am");
        data.put("\\bmaynt", "mayn't");
        data.put("\\bmayve", "may've");
        data.put("\\bmightnt", "mightn't");
        data.put("\\bmightve", "might've");
        data.put("\\bmustnt", "mustn't");
        data.put("\\bmustntve", "mustn't've");
        data.put("\\bmustve", "must've");
        data.put("\\bneednt", "needn't");
        data.put("\\boclock", "o'clock");
        data.put("\\boughtnt", "oughtn't");
        data.put("\\bshallnt", "shalln't");
        data.put("\\bshant", "shan't");
        data.put("\\bshouldve", "should've");
        data.put("\\bshouldnt", "shouldn't");
        data.put("\\bshouldntve", "shouldn't've");
        data.put("\\bsomebodys", "somebody's");
        data.put("\\bsomeones", "someone's");
        data.put("\\bsomethings", "something's");
        data.put("\\bthatll", "that'll");
        data.put("\\bthatre", "that're");
        data.put("\\bthats", "that's");
        data.put("\\bthatd", "that'd");
        data.put("\\bthered", "there'd");
        data.put("\\btherell", "there'll");
        data.put("\\btherere", "there're");
        data.put("\\btheres", "there's");
        data.put("\\bthesere", "these're");
        data.put("\\btheyd", "they'd");
        data.put("\\btheyll", "they'll");
        data.put("\\btheyre", "they're");
        data.put("\\btheyve", "they've");
        data.put("\\btwas", "'twas");
        data.put("\\bwasnt", "wasn't");
        data.put("\\bwedve", "we'd've");
        data.put("\\bwerent", "weren't");
        data.put("\\bwhatd", "what'd");
        data.put("\\bwhatll", "what'll");
        data.put("\\bwhatre", "what're");
        data.put("\\bwhats", "what's");
        data.put("\\bwhatve", "what've");
        data.put("\\bwhens", "when's");
        data.put("\\bwhered", "where'd");
        data.put("\\bwheres", "where's");
        data.put("\\bwhod", "who'd");
        data.put("\\bwhodve", "who'd've");
        data.put("\\bwholl", "who'll");
        data.put("\\bwhos", "who's");
        data.put("\\bwhove", "who've");
        data.put("\\bwhyd", "why'd");
        data.put("\\bwhyre", "why're");
        data.put("\\bwhys", "why's");
        data.put("\\bwont", "won't");
        data.put("\\bwouldve", "would've");
        data.put("\\bwouldnt", "wouldn't");
        data.put("\\byall", "y'all");
        data.put("\\byoud", "you'd");
        data.put("\\byoull", "you'll");
        data.put("\\byoure", "you're");
        data.put("\\byouve", "you've");

        // data.put("\\b...", "…");
        // data.put("\\b@gcom", "@gmail.com");
        // data.put("\\bafaik", "as far as I know");
        // data.put("\\bbc", "because");
        // data.put("\\bbday", "birthday");
        // data.put("\\bbrb", "be right back");
        // data.put("\\bbrt", "be right there");
        data.put("\\bcmu", "CMU");
        // data.put("\\bdef", "definitely");
        // data.put("\\beta", "ETA");
        // data.put("\\beu", "EU");
        // data.put("\\bfb", "facebook");
        // data.put("\\bffs", "FFS");
        data.put("\\bfiance", "fiancé");
        data.put("\\bfiancee", "fiancée");
        // data.put("\\bgn", "good night");
        // data.put("\\bidk", "I don't know");
        // data.put("\\biirc", "if I recall correctly");
        // data.put("\\bikr", "I know, right?");
        data.put("\\bipa", "IPA");
        data.put("\\bjfc", "JFC");
        data.put("\\bjk", "JK");
        // data.put("\\bk", "K");
        data.put("\\blmao", "LMAO");
        data.put("\\blmfao", "LMFAO");
        data.put("\\blol", "LOL");
        // data.put("\\bnrn", "not right now");
        // data.put("\\bok", "OK");
        data.put("\\bomg", "OMG");
        // data.put("\\bos", "OS");
        data.put("\\bpitt", "Pitt");
        data.put("\\bnyc", "NYC");
        data.put("\\bpittsburgh", "Pittsburgh");
        data.put("\\harrisburg", "Harrisburg");
        data.put("\\bpokemon", "Pokémon");
        data.put("\\bprc", "PRC");
        // data.put("\\bprob", "probably");
        data.put("\\bresume", "résumé");
        data.put("\\bresumee", "resume");
        // data.put("\\brn", "right now");
        data.put("\\brofl", "ROFL");
        // data.put("\\bse", "southeast");
        // data.put("\\bsry", "sorry");
        data.put("\\btho", "though");
        data.put("\\bthru", "through");
        // data.put("\\btil", "until");
        // data.put("\\btill", "til");
        // data.put("\\btilll", "till");
        // data.put("\\bttyl", "talk to you later");
        // data.put("\\bu", "you");
        // data.put("\\buk", "UK");
        // data.put("\\busa", "USA");
        // data.put("\\bw/", "with");
        // data.put("\\bw/o", "without"); 
        data.put("\\bwtf", "WTF");
        // data.put("\\b", "");
    }

    static HashMap<String, String> getData() {
        makeData();
        return data;
    }
}
