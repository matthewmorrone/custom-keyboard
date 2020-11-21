package com.custom.keyboard;

import android.content.Context;
import android.service.textservice.SpellCheckerService;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

import java.util.ArrayList;
import java.util.List;

public class CustomSpellCheckerService extends SpellCheckerService {
    @Override
    public Session createSession() {
        return new CustomSpellingSession(); // return createSession();
    }

    static class CustomSpellingSession extends SpellCheckerService.Session {
        SpellChecker spellChecker;

        @Override
        public void onCreate() {

        }
        public void setContext(Context context) {
            spellChecker = new SpellChecker(context, true);
        }

        public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {
            String word = textInfo.getText();
            // System.out.println(SpellChecker.getCommon(word));
            // System.out.println(SpellChecker.getCompletions(word));
            // System.out.println(SpellChecker.getSuggestions(word));

            String[] suggestions = new String[]{};
            SuggestionsInfo suggestionsInfo = new SuggestionsInfo(SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO, suggestions);
            return suggestionsInfo;
        }

        @Override
        public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) {
            List<SuggestionsInfo> suggestionsInfos = new ArrayList<>();

            for (TextInfo cur : textInfos) {
                // Convert the sentence into an array of words
                String[] words = cur.getText().split("\\s+");
                for (String word : words) {
                    TextInfo tmp = new TextInfo(word);
                    // Generate suggestions for each word
                    suggestionsInfos.add(onGetSuggestions(tmp, suggestionsLimit));
                }
            }
            return new SentenceSuggestionsInfo[]{
                new SentenceSuggestionsInfo(suggestionsInfos.toArray(new SuggestionsInfo[0]), new int[suggestionsInfos.size()], new int[suggestionsInfos.size()])
            };
        }
    }
}



