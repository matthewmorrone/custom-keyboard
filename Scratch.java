r.getDimensionPixelSize(R.dimen.candidate_vertical_padding);
popup3.setOrder(0);
getKey(32).popupResId
// preference has getIntent for opening custom key editors
// preference also has getOrder for possible use in recycler view for layout order

public void Scratch() {
     if (layouts.size() <=  5) {layoutLayout = R.layout.layouts_1;}
     else if (layouts.size() <= 10) {layoutLayout = R.layout.layouts_2;}
     else if (layouts.size() <= 15) {layoutLayout = R.layout.layouts_3;}
     else if (layouts.size() <= 20) {layoutLayout = R.layout.layouts_4;}
     else        {layoutLayout = R.layout.layouts_5;}

     else {
     String bg = sharedPreferences.getString("bg", "#FF000000");
     int[] bgc = Util.fromColor(bg);
     float[] sCustomColorArray = {
     1.0f,      0,      0,       0,      bgc[1], // red
     0,   1.0f,      0,       0,      bgc[2], // green
     0,      0,   1.0f,       0,      bgc[3], // blue
     0,      0,      0,    1.0f,      bgc[0]  // alpha
     };
     mDefaultFilter = sCustomColorArray;
     kv.invalidateAllKeys();
     kv.draw(new Canvas());
     }

     try {
     if (currentKeyboard.name.equals("Morse")) {
     if (!Util.morseToChar(getLastWord()).equals("")) {
     if (primaryCode == 32) {
     toastIt(getLastWord()+" "+Util.morseToChar(getLastWord()));
     ic.commitText(Util.morseToChar(getLastWord()), 0);
     }
     }
     kv.draw(new Canvas());
     return;
     }

     }
     catch (Exception ignored) {}
     }

public String getLastWord() {
     ic = getCurrentInputConnection();
     String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
     if (words.length < 2) {
     return ic.getTextBeforeCursor(MAX, 0).toString();
     }
     return words[words.length - 1];
     }

public String getNextWord() {
     ic = getCurrentInputConnection();
     String[] words = ic.getTextAfterCursor(MAX, 0).toString().split(" ");
     if (words.length < 2) {
     return ic.getTextAfterCursor(MAX, 0).toString();
     }
     return words[0];
     }

public String[] getWords() {
     ic = getCurrentInputConnection();
     String[] words = ic.getTextBeforeCursor(MAX, 0).toString().split(" ");
     return words;
     }


public String[] getLines() {
     ic = getCurrentInputConnection();
     String[] lines = ic.getTextBeforeCursor(MAX, 0).toString().split("\n");
     return lines;
     }




public void lineLast(int n) {
     try {
     ic = getCurrentInputConnection();
     for(int i = 0; i < n; i++) {
     String[] lines = ic.getTextBeforeCursor(MAX, 0).toString().split("\n");
     String lastLine = lines[lines.length - 1];
     if (lines.length > 1) {
     lastLine = lines[1];
     }
     int position = getSelectionStart() + lastLine.length() + 1;
     if (Variables.isSelect()) {
     ic.setSelection(Variables.cursorStart, position);
     }
     else {
     ic.setSelection(position, position);
     }
     }
     }
     catch (Exception ignored) {}
     }

public void lineNext(int n) {
     try {
     ic = getCurrentInputConnection();
     for(int i = 0; i < n; i++) {
     String[] lines = ic.getTextAfterCursor(MAX, 0).toString().split("\n");
     String nextLine = lines[0];
     if (lines.length > 1) {
     nextLine = lines[1];
     }
     int position = getSelectionStart() + nextLine.length() + 1;
     if (Variables.isSelect()) {
     ic.setSelection(Variables.cursorStart, position);
     }
     else {
     ic.setSelection(position, position);
     }
     }
     }
     catch (Exception ignored) {}
     }

