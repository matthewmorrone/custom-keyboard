package com.custom.keyboard.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static void copyFile(File src, File dst) throws IOException {
        // Files.copy(src.toPath(), dest.toPath(), REPLACE_EXISTING);
        try (FileChannel in = new FileInputStream(src).getChannel();
             FileChannel out = new FileOutputStream(dst).getChannel()) {
            in.transferTo(0, in.size(), out);
        }
        catch (Exception ignored) {}
    }

    public String getPath(Context context, Uri uri) {
        String path = null;
        String[] projection = {
            MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            path = uri.getPath();
        }
        else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }
        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    public static String readFile(String path) {
        return readFile(path, StandardCharsets.US_ASCII);
    }

    public static String readFile(String path, Charset encoding) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }
        catch(IOException ignored) {}
        return "";
    }

}
