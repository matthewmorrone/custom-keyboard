package com.custom.keyboard;

import java.util.HashMap;

public class Edit {
    
    private static HashMap<String, String> replacements = new HashMap<>();

    private static HashMap<String, String> shortcuts = new HashMap<>();

    static char trigger = ' ';

    public Edit() {}
    public Edit(char trigger) {
        Edit.trigger = trigger;
    }

    private static void changeKey(String oldKey, String newKey) {
        String value = replacements.remove(oldKey);
        replacements.put(newKey, value);
    }

    private static void makeReplacements() {

        // replacements.put("^i", "I"); 
        replacements.put(" i", " I");
        replacements.put(" yoy", " you");      replacements.put(" yoyou", " you");
        replacements.put(" cant", " can't");   replacements.put(" cantt", " cant");
        replacements.put(" hell", " he'll");   replacements.put(" helll", " hell");
        // replacements.put("^id", "I'd");        replacements.put("^idd", "I'd");
        replacements.put(" id", " I'd");       replacements.put(" idd", " id");
        // replacements.put("^ill", "I'll");      replacements.put("^illl", " ill");
        replacements.put(" ill", " I'll");     replacements.put(" illl", " ill");
        // replacements.put("^its", "it's");      replacements.put("^itss", "its");
        replacements.put(" its", " it's");     replacements.put(" itss", " its");
        // replacements.put("^lets", "let's");    replacements.put("^letss", "lets");
        replacements.put(" lets", " let's");   replacements.put(" letss", " lets");
        replacements.put(" shed", " she'd");   replacements.put(" shedd", " shed");
        replacements.put(" shell", " she'll"); replacements.put(" shelll", " shell");
        replacements.put(" wed", " we'd");     replacements.put(" wedd", " wed");
        // replacements.put("^well", "we'll");   replacements.put("^welll", "well");
        replacements.put(" well", " we'll");   replacements.put(" welll", " well");
        replacements.put(" were", " we're");   replacements.put(" weree", " were");
        replacements.put(" whore", " who're"); replacements.put(" whoree", " whore");

        replacements.put(" arent", " aren't");
        replacements.put(" dont", " don't");
        // replacements.put("^hed", "he'd"); 
        replacements.put(" hed", " he'd");
        // replacements.put("^hes", "he's"); 
        replacements.put(" hes", " he's");
        // replacements.put("^hows", "how's"); 
        replacements.put(" hows", " how's");
        replacements.put("^im", " I'm");
        replacements.put(" im", " I'm");
        // replacements.put("^ive", "I've"); 
        replacements.put(" ive", " I've");
        // replacements.put("^shes", "she's"); 
        replacements.put(" shes", " she's");
        replacements.put(" tis", " 'tis");
        replacements.put(" weve", " we've");

        replacements.put(" aint", " ain't");
        replacements.put(" amnt", " amn't");
        replacements.put(" couldve", " could've");
        replacements.put(" couldnt", " couldn't");
        replacements.put(" couldntve", " couldn't've");
        replacements.put(" darent", " daren't");
        replacements.put(" daresnt", " daresn't");
        replacements.put(" dasnt", " dasn't");
        replacements.put(" didnt", " didn't");
        replacements.put(" doesnt", " doesn't");
        replacements.put(" everyones", " everyone's");
        replacements.put(" hadnt", " hadn't");
        replacements.put(" hasnt", " hasn't");
        replacements.put(" havent", " haven't");
        replacements.put(" howd", " how'd");
        replacements.put(" howll", " how'll");
        replacements.put(" howre", " how're");
        replacements.put(" isnt", " isn't");
        // replacements.put("^itd", "it'd"); 
        replacements.put(" itd", " it'd");
        // replacements.put("^itll", "it'll"); 
        replacements.put(" itll", " it'll");
        replacements.put(" maam", " ma'am");
        replacements.put(" maynt", " mayn't");
        replacements.put(" mayve", " may've");
        replacements.put(" mightnt", " mightn't");
        replacements.put(" mightve", " might've");
        replacements.put(" mustnt", " mustn't");
        replacements.put(" mustntve", " mustn't've");
        replacements.put(" mustve", " must've");
        replacements.put(" neednt", " needn't");
        replacements.put(" oclock", " o'clock");
        replacements.put(" oughtnt", " oughtn't");
        replacements.put(" shallnt", " shalln't");
        replacements.put(" shant", " shan't");
        replacements.put(" shouldve", " should've");
        replacements.put(" shouldnt", " shouldn't");
        replacements.put(" shouldntve", " shouldn't've");
        replacements.put(" somebodys", " somebody's");
        // replacements.put("^someones", "someone's"); 
        replacements.put(" someones", " someone's");
        replacements.put(" somethings", " something's");
        // replacements.put("^thatll", "that'll"); 
        replacements.put(" thatll", " that'll");
        replacements.put(" thatre", " that're");
        // replacements.put("^thats", "that's"); 
        replacements.put(" thats", " that's");
        replacements.put(" thatd", " that'd");
        replacements.put(" thered", " there'd");
        replacements.put(" therell", " there'll");
        replacements.put(" therere", " there're");
        // replacements.put("^theres", "there's"); 
        replacements.put(" theres", " there's");
        replacements.put(" thesere", " these're");
        // replacements.put("^theyd", "they'd"); 
        replacements.put(" theyd", " they'd");
        // replacements.put("^theyll", "they'll"); 
        replacements.put(" theyll", " they'll");
        // replacements.put("^theyre", "they're"); 
        replacements.put(" theyre", " they're");
        // replacements.put("^theyve", "they've"); 
        replacements.put(" theyve", " they've");
        replacements.put(" twas", " 'twas");
        replacements.put(" wasnt", " wasn't");
        replacements.put(" wedve", " we'd've");
        replacements.put(" werent", " weren't");
        replacements.put(" whatd", " what'd");
        replacements.put(" whatll", " what'll");
        replacements.put(" whatre", " what're");
        // replacements.put("^whats", "what's"); 
        replacements.put(" whats", " what's");
        replacements.put(" whatve", " what've");
        replacements.put(" whens", " when's");
        // replacements.put("^whered", "where'd"); 
        replacements.put(" whered", " where'd");
        // replacements.put("^wheres", "where's"); 
        replacements.put(" wheres", " where's");
        replacements.put(" whod", " who'd");
        replacements.put(" whodve", " who'd've");
        replacements.put(" wholl", " who'll");
        // replacements.put("^whos", "who's"); 
        replacements.put(" whos", " who's");
        replacements.put(" whove", " who've");
        // replacements.put("^whyd", "why'd"); 
        replacements.put(" whyd", " why'd");
        replacements.put(" whyre", " why're");
        // replacements.put("^whys", "why's"); 
        replacements.put(" whys", " why's");
        replacements.put(" wont", " won't");
        replacements.put(" wouldve", " would've");
        replacements.put(" wouldnt", " wouldn't");
        replacements.put(" yall", " y'all");
        // replacements.put("^youd", "you'd"); 
        replacements.put(" youd", " you'd");
        // replacements.put("^youll", "you'll"); 
        replacements.put(" youll", " you'll");
        // replacements.put("^youre", "you're"); 
        replacements.put(" youre", " you're");
        // replacements.put("^youve", "you've"); 
        replacements.put(" youve", " you've");

        /*
        replacements.put(" cmu", " CMU");
        replacements.put(" fiance", " fiancé");
        replacements.put(" fiancee", " fiancée");
        replacements.put(" ipa", " IPA");
        replacements.put(" jfc", " JFC");
        replacements.put(" jk", " JK");
        replacements.put(" lmao", " LMAO");
        replacements.put(" lmfao", " LMFAO");
        replacements.put(" lol", " LOL");
        replacements.put(" omg", " OMG");
        replacements.put(" pitt", " Pitt");
        replacements.put(" nyc", " NYC");
        replacements.put(" pittsburgh", " Pittsburgh");
        replacements.put(" harrisburg", " Harrisburg");
        replacements.put(" pokemon", " Pokémon");
        replacements.put(" prc", " PRC");
        replacements.put(" resume", " résumé");
        replacements.put(" resumee", " resume");
        replacements.put(" rofl", " ROFL");
        replacements.put(" wtf", " WTF");
        replacements.put(" eta", " ETA");
        replacements.put(" eu", " EU");
        replacements.put(" ffs", " FFS");
        replacements.put(" k", " K");
        replacements.put(" ok", " OK");
        replacements.put(" os", " OS");
        replacements.put(" til", " until");
        replacements.put(" till", " til");
        replacements.put(" tilll", " till");
        replacements.put(" uk", " UK");
        replacements.put(" usa", " USA");
        */
    }

    private static void makeShortcuts(char sigil) {
        shortcuts.put(""+sigil+"afaik", " as far as I know");
        shortcuts.put(""+sigil+"bc", " because");
        shortcuts.put(""+sigil+"bday", " birthday");
        shortcuts.put(""+sigil+"brb", " be right back");
        shortcuts.put(""+sigil+"brt", " be right there");
        shortcuts.put(""+sigil+"def", " definitely");
        shortcuts.put(""+sigil+"fb", " facebook");
        shortcuts.put(""+sigil+"gn", " good night");
        shortcuts.put(""+sigil+"idk", " I don't know");
        shortcuts.put(""+sigil+"iirc", " if I recall correctly");
        shortcuts.put(""+sigil+"ikr", " I know, right?");
        shortcuts.put(""+sigil+"nrn", " not right now");
        shortcuts.put(""+sigil+"prob", " probably");
        shortcuts.put(""+sigil+"rn", " right now");
        shortcuts.put(""+sigil+"se", " southeast");
        shortcuts.put(""+sigil+"sry", " sorry");
        shortcuts.put(""+sigil+"tho", " though");
        shortcuts.put(""+sigil+"thru", " through");
        shortcuts.put(""+sigil+"ttyl", " talk to you later");
        shortcuts.put(""+sigil+"u", " you");
        shortcuts.put(""+sigil+"w", "with");
        shortcuts.put(""+sigil+"wo", "without");
        shortcuts.put("@gcom", "@gmail.com");
    }

    static HashMap<String, String> getReplacements() {
        makeReplacements();
        return replacements;
    }

    static HashMap<String, String> getShortcuts() {
        makeShortcuts(trigger);
        return shortcuts;
    }
}
