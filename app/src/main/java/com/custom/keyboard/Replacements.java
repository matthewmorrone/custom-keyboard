package com.custom.keyboard;

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

        data.put(" i", " I");
        data.put(" yoy", " you");      data.put(" yoyou", " you"); 
        data.put(" cant", " can't");   data.put(" cantt", " cant");
        data.put(" hell", " he'll");   data.put(" helll", " hell");
        data.put(" id", " I'd");       data.put(" idd", " id");
        data.put(" ill", " I'll");     data.put(" illl", " ill");
        data.put(" its", " it's");     data.put(" itss", " its");
        data.put(" lets", " let's");   data.put(" letss", " lets");
        data.put(" shed", " she'd");   data.put(" shedd", " shed");
        data.put(" shell", " she'll"); data.put(" shelll", " shell");
        data.put(" wed", " we'd");     data.put(" wedd", " wed");
        data.put(" well", " we'll");   data.put(" welll", " well");
        data.put(" were", " we're");   data.put(" weree", " were");
        data.put(" whore", " who're"); data.put(" whoree", " whore");

        data.put(" arent", " aren't");
        data.put(" dont", " don't");
        data.put(" hed", " he'd");
        data.put(" hes", " he's");
        data.put(" hows", " how's");
        data.put(" im", " I'm");
        data.put(" ive", " I've");
        data.put(" shes", " she's");
        data.put(" tis", " 'tis");
        data.put(" weve", " we've");

        data.put(" aint", " ain't");
        data.put(" amnt", " amn't");
        data.put(" couldve", " could've");
        data.put(" couldnt", " couldn't");
        data.put(" couldntve", " couldn't've");
        data.put(" darent", " daren't");
        data.put(" daresnt", " daresn't");
        data.put(" dasnt", " dasn't");
        data.put(" didnt", " didn't");
        data.put(" doesnt", " doesn't");
        data.put(" everyones", " everyone's");
        data.put(" hadnt", " hadn't");
        data.put(" hasnt", " hasn't");
        data.put(" havent", " haven't");
        data.put(" howd", " how'd");
        data.put(" howll", " how'll");
        data.put(" howre", " how're");
        data.put(" isnt", " isn't");
        data.put(" itd", " it'd");
        data.put(" itll", " it'll");
        data.put(" maam", " ma'am");
        data.put(" maynt", " mayn't");
        data.put(" mayve", " may've");
        data.put(" mightnt", " mightn't");
        data.put(" mightve", " might've");
        data.put(" mustnt", " mustn't");
        data.put(" mustntve", " mustn't've");
        data.put(" mustve", " must've");
        data.put(" neednt", " needn't");
        data.put(" oclock", " o'clock");
        data.put(" oughtnt", " oughtn't");
        data.put(" shallnt", " shalln't");
        data.put(" shant", " shan't");
        data.put(" shouldve", " should've");
        data.put(" shouldnt", " shouldn't");
        data.put(" shouldntve", " shouldn't've");
        data.put(" somebodys", " somebody's");
        data.put(" someones", " someone's");
        data.put(" somethings", " something's");
        data.put(" thatll", " that'll");
        data.put(" thatre", " that're");
        data.put(" thats", " that's");
        data.put(" thatd", " that'd");
        data.put(" thered", " there'd");
        data.put(" therell", " there'll");
        data.put(" therere", " there're");
        data.put(" theres", " there's");
        data.put(" thesere", " these're");
        data.put(" theyd", " they'd");
        data.put(" theyll", " they'll");
        data.put(" theyre", " they're");
        data.put(" theyve", " they've");
        data.put(" twas", " 'twas");
        data.put(" wasnt", " wasn't");
        data.put(" wedve", " we'd've");
        data.put(" werent", " weren't");
        data.put(" whatd", " what'd");
        data.put(" whatll", " what'll");
        data.put(" whatre", " what're");
        data.put(" whats", " what's");
        data.put(" whatve", " what've");
        data.put(" whens", " when's");
        data.put(" whered", " where'd");
        data.put(" wheres", " where's");
        data.put(" whod", " who'd");
        data.put(" whodve", " who'd've");
        data.put(" wholl", " who'll");
        data.put(" whos", " who's");
        data.put(" whove", " who've");
        data.put(" whyd", " why'd");
        data.put(" whyre", " why're");
        data.put(" whys", " why's");
        data.put(" wont", " won't");
        data.put(" wouldve", " would've");
        data.put(" wouldnt", " wouldn't");
        data.put(" yall", " y'all");
        data.put(" youd", " you'd");
        data.put(" youll", " you'll");
        data.put(" youre", " you're");
        data.put(" youve", " you've");

        // data.put("...", "…");
        // data.put("@gcom", "@gmail.com");
        // data.put(" afaik", " as far as I know");
        // data.put(" bc", " because");
        // data.put(" bday", " birthday");
        // data.put(" brb", " be right back");
        // data.put(" brt", " be right there");
        data.put(" cmu", " CMU");
        // data.put(" def", " definitely");
        // data.put(" eta", " ETA");
        // data.put(" eu", " EU");
        // data.put(" fb", " facebook");
        // data.put(" ffs", " FFS");
        data.put(" fiance", " fiancé");
        data.put(" fiancee", " fiancée");
        // data.put(" gn", " good night");
        // data.put(" idk", " I don't know");
        // data.put(" iirc", " if I recall correctly");
        // data.put(" ikr", " I know, right?");
        data.put(" ipa", " IPA");
        data.put(" jfc", " JFC");
        data.put(" jk", " JK");
        // data.put(" k", " K");
        data.put(" lmao", " LMAO");
        data.put(" lmfao", " LMFAO");
        data.put(" lol", " LOL");
        // data.put(" nrn", " not right now");
        // data.put(" ok", " OK");
        data.put(" omg", " OMG");
        // data.put(" os", " OS");
        data.put(" pitt", " Pitt");
        data.put(" nyc", " NYC");
        data.put(" pittsburgh", " Pittsburgh");
        data.put(" harrisburg", " Harrisburg");
        data.put(" pokemon", " Pokémon");
        data.put(" prc", " PRC");
        // data.put(" prob", " probably");
        data.put(" resume", " résumé");
        data.put(" resumee", " resume");
        // data.put(" rn", " right now");
        data.put(" rofl", " ROFL");
        // data.put(" se", " southeast");
        // data.put(" sry", " sorry");
        data.put(" tho", " though");
        data.put(" thru", " through");
        // data.put(" til", " until");
        // data.put(" till", " til");
        // data.put(" tilll", " till");
        // data.put(" ttyl", " talk to you later");
        // data.put(" u", " you");
        // data.put(" uk", " UK");
        // data.put(" usa", " USA");
        // data.put(" w/", "with");
        // data.put(" w/o", "without"); 
        data.put(" wtf", " WTF");
        // data.put("", "");
    }

    static HashMap<String, String> getData() {
        makeData();
        return data;
    }
}
