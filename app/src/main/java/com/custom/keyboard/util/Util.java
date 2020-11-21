package com.custom.keyboard.util;

import androidx.annotation.NonNull;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Util {

    public static void show2Darray(String [][] arr) {
        for (String[] ar : arr) {
            if (ar == null) continue;
            for (String a: ar) {
                System.out.print(" " + a);
            }
            System.out.println();
        }
    }



    public static void print(@NonNull Object... a) {
        for (Object i : a) System.out.print(i + " ");
        System.out.println();
    }

    public static Document toXmlDocument(String str) {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document document = null;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            document = docBuilder.parse(new InputSource(new StringReader(str)));
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return document;
    }

    // general
    public static void noop() {}

    public static <T> T notNull(T value) {
        return Objects.requireNonNull(value);
    }
    public static <T> boolean isNull(T value) {
        return (value != null);
    }
    public static <T> T orNull(T value, T defaultValue) {
        return Optional.ofNullable(value).orElse(defaultValue);
    }
    public static String[][] to2Darray(String source, String outerdelim, String innerdelim) {
        String[][] result = new String[source.replaceAll("[^" + outerdelim + "]", "").length() + 1][];
        int count = 0;
        for (String line : source.split ("[" + outerdelim + "]")) {
            result [count++] = line.split(innerdelim);
        }
        return result;
    }



    public static String getClassName() {
        Class<?> enclosingClass = Util.class.getEnclosingClass();
        String className;
        if (enclosingClass != null) {
            className = enclosingClass.getName();
        }
        else {
            className = Util.class.getName();
        }
        try {
            className = className.split("\\.")[0];
        }
        catch (Exception ignored) {}
        return className;
    }
    public static int getLineNumber() {
        return ___8drrd3148796d_Xaf();
    }
    public static int ___8drrd3148796d_Xaf() {
        boolean thisOne = false;
        int thisOneCountDown = 1;
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            String methodName = element.getMethodName();
            int lineNum = element.getLineNumber();
            if (thisOne && (thisOneCountDown == 0)) {
                return lineNum;
            }
            else if (thisOne) {
                thisOneCountDown--;
            }
            if (methodName.equals("___8drrd3148796d_Xaf")) {
                thisOne = true;
            }
        }
        return -1;
    }
    public static String methodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }
    public String getMethodName() {
        return new Throwable().getStackTrace()[1].getMethodName();
    }
    public String getMethodName(int depth) {
        return new Throwable().getStackTrace()[depth].getMethodName();
    }
}
