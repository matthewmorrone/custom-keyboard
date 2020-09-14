// on mic tap we call
public void startVoiceListening() {
    InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
    String voiceExists = voiceExists(imeManager);
    if (voiceExists != null) {
        final IBinder token = getWindow().getWindow().getAttributes().token;
        imeManager.setInputMethod(token,voiceExists);
    }
}

private String voiceExists(InputMethodManager imeManager) {
    List<InputMethodInfo> list = imeManager.getInputMethodList();
    for (InputMethodInfo el : list) {
        // do something to check whatever IME we want.
        // in this case "com.google.android.googlequicksearchbox"
    }
    return null;
}