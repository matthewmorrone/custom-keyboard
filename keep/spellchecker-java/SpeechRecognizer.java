public class CustomInputMethodService 
    extends InputMethodService
    implements <random stuff> {

    private SpeechRecognizer mSpeechRecognizer;
    private RecognitionListener mSpeechlistener;

    public void onCreate() {
        super.onCreate();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechlistener = new CustomRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(mSpeechlistener);
    }

    @Override
    public void onPress(int primaryCode) {
        if (primaryCode == KeyCodes.VOICE_INPUT) {
            mSpeechRecognizer.startListening(getSpeechIntent());
        }else if(..){
            ...
        }
    }

    private Intent getSpeechIntent() {
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        speechIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
        return speechIntent;
    }

}

@Override
public void onResults(Bundle results) {
    ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
    Log.d(TAG, "onResults: ----> " + matches.get(0));
    if(matches != null && matches.size() > 0) {
        writeText(matches.get(0));
    }
}

Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
try {
    startActivityForResult(voiceIntent, Constants.RESULT_SPEECH);
} catch (ActivityNotFoundException ex) {
    DebugLog.e(TAG, "Not found excpetion onKeyDown: " + ex);
}