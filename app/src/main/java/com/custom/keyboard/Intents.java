package com.custom.keyboard;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.UserDictionary;


public class Intents {

    public void startIntent(Context context, Intent intent) {
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


    public void showSettings(Context context) {
        Intent intent = new Intent(context, Preference.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent(context, intent);
    }
    
    public void showClipboard(Context context) {
        try {
            // Intent intent = new Intent(getApplicationContext(), "com.samsung.android.ClipboardUIService");
            Intent intent = new Intent("com.samsung.android.ClipboardUIService")
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startIntent(context, intent);
        }
        catch (Exception e) {
            // toastIt(e.toString());
        }
    }

    public void showActivity(Context context, String id) {
        Intent intent = new Intent(id)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent(context, intent);
    }


    public void dialPhone(Context context, String phoneNumber) {
        if (!Util.isValidPhoneNumber(phoneNumber)) return;
        Intent intent = new Intent(Intent.ACTION_DIAL)
            .setData(Uri.parse("tel:" + phoneNumber));
        startIntent(context, intent);
    }

    public void openWebpage(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW)
             .setData(webpage);
        startIntent(context, intent);
    }

    public void searchWeb(Context context, String query) {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH)
            .putExtra(SearchManager.QUERY, query);
        startIntent(context, intent);
    }

    public void createAlarm(Context context, String message) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
             .putExtra(AlarmClock.EXTRA_MESSAGE, message);
        startIntent(context, intent);
    }

    public void createAlarm(Context context, String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
             .putExtra(AlarmClock.EXTRA_MESSAGE, message)
             .putExtra(AlarmClock.EXTRA_HOUR, hour)
             .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        startIntent(context, intent);
    }

    public void showAlarms(Context context) {
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        startIntent(context, intent);
    }

    public void startTimer(Context context, int seconds) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
             .putExtra(AlarmClock.EXTRA_LENGTH, seconds);
             // .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startIntent(context, intent);
    }

    public void startTimer(Context context) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
             .putExtra(AlarmClock.EXTRA_MESSAGE, "Timer")
             .putExtra(AlarmClock.EXTRA_LENGTH, 60)
             .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startIntent(context, intent);
    }

    public void showMap(Context context, Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
             .setData(geoLocation);
        startIntent(context, intent);
    }

    public void showLocationFromAddress(Context context, String address) {
        address = Util.encodeUrl(address);
        Uri location = Uri.parse("geo:0,0?q="+address);
        showMap(context, location);
    }
    public void showLocationFromAddress(Context context, String address, int zoom) {
        address = Util.encodeUrl(address);
        Uri location = Uri.parse("geo:0,0?q="+address+"?z="+zoom);
        showMap(context, location);
    }
    public void showLocationFromCoordinates(Context context, double latitude, double longitude) {
        Uri location = Uri.parse("geo:"+latitude+","+longitude+"?z=14");
        showMap(context, location);
    }
    public void showLocationFromCoordinates(Context context, double latitude, double longitude, int zoom) {
        Uri location = Uri.parse("geo:"+latitude+","+longitude+"?z="+zoom);
        showMap(context, location);
    }

    public void addCalendarEvent(Context context, String title) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
             .setData(CalendarContract.Events.CONTENT_URI)
             .putExtra(CalendarContract.Events.TITLE, title);
        startIntent(context, intent);
    }

    public void addCalendarEvent(Context context, String title, String location, long begin, long end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.Events.TITLE, title)
            .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        startIntent(context, intent);

        /*
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 10, 30);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
        */
    }

    public void insertContactByName(Context context, String name) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
            .setType(ContactsContract.Contacts.CONTENT_TYPE)
            .putExtra(ContactsContract.Intents.Insert.NAME, name);
        startIntent(context, intent);
    }
    public void insertContactByEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
             .setType(ContactsContract.Contacts.CONTENT_TYPE)
             .putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        startIntent(context, intent);
    }

    public void composeEmail(Context context, String address) {
        Intent intent = new Intent(Intent.ACTION_SEND)
             .setData(Uri.parse("mailto:")) // only email apps should handle this
             .putExtra(Intent.EXTRA_EMAIL, new String[] {address}); // .setType("*/*");
            // .putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            // .putExtra(Intent.EXTRA_TEXT, "Email message text")
            // // if the intent does not have a URI, so declare the "text/plain" MIME type
            // .setType(HTTP.PLAIN_TEXT_TYPE)
            // // You can also attach multiple items by passing an ArrayList of Uris
            // .putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        startIntent(context, intent);
    }

    public void composeEmail(Context context, String[] addresses) {
        Intent intent = new Intent(Intent.ACTION_SEND)
            .setData(Uri.parse("mailto:")) // only email apps should handle this
            .putExtra(Intent.EXTRA_EMAIL, addresses); // .setType("*/*");
            // .putExtra(Intent.EXTRA_SUBJECT, "Email subject")
            // .putExtra(Intent.EXTRA_TEXT, "Email message text")
            // // if the intent does not have a URI, so declare the "text/plain" MIME type
            // .setType(HTTP.PLAIN_TEXT_TYPE)
            // // You can also attach multiple items by passing an ArrayList of Uris
            // .putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        startIntent(context, intent);
    }

    public void composeEmail(Context context, String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND)
            .setData(Uri.parse("mailto:"))
            .putExtra(Intent.EXTRA_EMAIL, addresses)
            .putExtra(Intent.EXTRA_SUBJECT, subject)
            .putExtra(Intent.EXTRA_STREAM, attachment);
        startIntent(context, intent);
    }

    // // String	ACTION_APPEND_NOTE	Intent action for appending to an existing note.
    // // String	ACTION_CREATE_NOTE	Intent action for creating a note.
    // // String	ACTION_DELETE_NOTE	Intent action for removing an existing note.
    // // String	EXTRA_NAME	Intent extra specifying an optional title or subject for the note as a string.
    // // String	EXTRA_NOTE_QUERY	Intent extra specifying an unstructured query for a note as a string.
    // // String	EXTRA_TEXT	Intent extra specifying the text of the note as a string.
    // public void createNote(String subject, String text) {
    //     Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
    //          .putExtra(NoteIntents.EXTRA_NAME, subject)
    //          .putExtra(NoteIntents.EXTRA_TEXT, text);
    //     if (intent.resolveActivity(getPackageManager()) != null) {
    //         startActivity(intent);
    //     }
    // }

    public void capturePhoto(Context context) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startIntent(context, intent);

        /*
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
             Uri.withAppendedPath(locationForPhotos, targetFilename));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        */
    }

    public void selectImage(Context context) {
        // Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
            .setType("image/*");
            // .addCategory(Intent.CATEGORY_OPENABLE);
        startIntent(context, intent);
    }

    boolean flashlight = false;
    public void toggleFlashlight(Context context) {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) return;
        Camera cam = Camera.open();
        if (!flashlight) {
            Camera.Parameters p = cam.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(p);
            cam.startPreview();
        }
        else {
            cam.stopPreview();
            cam.release();
        }
        flashlight = !flashlight;
    }
}