// AnySoftKeyboard

case KeyCodes.VOICE_INPUT:
    if (mVoiceRecognitionTrigger.isInstalled()) {
        mVoiceRecognitionTrigger.startVoiceRecognition(
                getCurrentAlphabetKeyboard().getDefaultDictionaryLocale());
    } else {
        Intent voiceInputNotInstalledIntent =
                new Intent(
                        getApplicationContext(), VoiceInputNotInstalledActivity.class);
        voiceInputNotInstalledIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(voiceInputNotInstalledIntent);
    }
    break;

case KeyCodes.ENTER:
    if (mShiftKeyState.isPressed() && ic != null) {
        // power-users feature ahead: Shift+Enter
        // getting away from firing the default editor action, by forcing newline
        ic.commitText("\n", 1);
        break;
    }

