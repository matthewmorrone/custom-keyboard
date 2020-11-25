package com.custom.keyboard.util;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.custom.keyboard.PreferenceActivity;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class IntentUtils {

    public static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfo.size() != 1) return null;

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        Intent explicitIntent = new Intent(implicitIntent);

        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public static void startIntent(Context context, Intent intent) {
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void showActivity(Context context, String id) {
        Intent intent = new Intent(id).setFlags(FLAG_ACTIVITY_NEW_TASK);
        startIntent(context, intent);
    }

    public static void dialPhone(Context context, String phoneNumber) {
if (phoneNumber.isEmpty()) return;
        if (!StringUtils.isValidPhoneNumber(phoneNumber)) {
            ToastIt.text(context, phoneNumber+" is not a valid phone number.");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + phoneNumber));
        startIntent(context, intent);
    }

    public static void openWebpage(Context context, String url) {
if (url.isEmpty()) return;
        if (!StringUtils.isValidUrl(url)) url = "https://"+url;
        if (!StringUtils.isValidUrl(url)) {
            ToastIt.text(context, url+" is not a valid URL.");
            return;
        }
        Uri webpage = Uri.parse(url);
        ToastIt.text(context, "Opening "+url+" in browser…");
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(webpage);
        startIntent(context, intent);
    }

    public static void searchWeb(Context context, String query) {
if (query.isEmpty()) return;
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH).putExtra(SearchManager.QUERY, query);
        ToastIt.text(context, "Searching for: "+query);
        startIntent(context, intent);
    }

    public static void showMap(Context context, Uri geoLocation) {
        // System.out.println("showMap");
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(geoLocation);
        startIntent(context, intent);
    }

    public static void showLocationFromAddress(Context context, String address) {
if (address.isEmpty()) return;
        // Util.toastIt(context, address);
        // System.out.println("showLocationFromAddress");
        Uri location = Uri.parse("geo:0,0?q=" + StringUtils.encodeUrl(address));
        showMap(context, location);
    }

    public static void showLocationFromAddress(Context context, String address, int zoom) {
if (address.isEmpty()) return;
        // System.out.println("showLocationFromAddress zoom");
        address = StringUtils.encodeUrl(address);
        Uri location = Uri.parse("geo:0,0?q=" + address + "?z=" + zoom);
        showMap(context, location);
    }


    public static void showClipboard(Context context) {
        Intent intent = new Intent("com.samsung.android.ClipboardUIService");
        startIntent(context, intent);
    }

    public static void showSettings(Context context) {
        Intent intent = new Intent(context, PreferenceActivity.class);
        startIntent(context, intent);
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) view = new View(activity);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }








    public static void showLocationFromCoordinates(Context context, double latitude, double longitude) {
        Uri location = Uri.parse("geo:" + latitude + "," + longitude + "?z=14");
        showMap(context, location);
    }

    public static void showLocationFromCoordinates(Context context, double latitude, double longitude, int zoom) {
        Uri location = Uri.parse("geo:" + latitude + "," + longitude + "?z=" + zoom);
        showMap(context, location);
    }

    public static void createAlarm(Context context, String message) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        startIntent(context, intent);
    }

    public static void createAlarm(Context context, String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
.putExtra(AlarmClock.EXTRA_MESSAGE, message)
.putExtra(AlarmClock.EXTRA_HOUR, hour)
.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        startIntent(context, intent);
    }

    public static void showAlarms(Context context) {
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        startIntent(context, intent);
    }

    public static void startTimer(Context context, int seconds) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
.putExtra(AlarmClock.EXTRA_LENGTH, seconds);
        // .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startIntent(context, intent);
    }

    public static void startTimer(Context context) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
.putExtra(AlarmClock.EXTRA_MESSAGE, "Timer")
.putExtra(AlarmClock.EXTRA_LENGTH, 60)
.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startIntent(context, intent);
    }

    public static void addCalendarEvent(Context context, String title) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
.setData(CalendarContract.Events.CONTENT_URI)
.putExtra(CalendarContract.Events.TITLE, title);
        startIntent(context, intent);
    }

    public static void addCalendarEvent(Context context, String title, String location, long begin, long end) {
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

    public static void insertContactByName(Context context, String name) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
.setType(ContactsContract.Contacts.CONTENT_TYPE)
.putExtra(ContactsContract.Intents.Insert.NAME, name);
        startIntent(context, intent);
    }

    public static void insertContactByEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
.setType(ContactsContract.Contacts.CONTENT_TYPE)
.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        startIntent(context, intent);
    }

    public static void composeEmail(Context context, String address) {
        Intent intent = new Intent(Intent.ACTION_SEND)
.setData(Uri.parse("mailto:")) // only email apps should handle this
            .putExtra(Intent.EXTRA_EMAIL, new String[] {
                address
            }); // .setType("*/*");
        // .putExtra(Intent.EXTRA_SUBJECT, "Email subject")
        // .putExtra(Intent.EXTRA_TEXT, "Email message text")
        // // if the intent does not have a URI, so declare the "text/plain" MIME type
        // .setType(HTTP.PLAIN_TEXT_TYPE)
        // // You can also attach multiple items by passing an ArrayList of Uris
        // .putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        startIntent(context, intent);
    }

    public static void composeEmail(Context context, String[] addresses) {
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

    public static void composeEmail(Context context, String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND)
.setData(Uri.parse("mailto:"))
.putExtra(Intent.EXTRA_EMAIL, addresses)
.putExtra(Intent.EXTRA_SUBJECT, subject)
.putExtra(Intent.EXTRA_STREAM, attachment);
        startIntent(context, intent);
    }
}
