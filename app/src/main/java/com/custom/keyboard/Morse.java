package com.custom.keyboard;

class Morse {

    static String toChar(String buffer) {
        switch (buffer) {
            case "-": return "t";
            case "·": return "e";
            case "--": return "m";
            case "-·": return "n";
            case "·-": return "a";
            case "··": return "i";
            case "---": return "o";
            case "--·": return "g";
            case "-·-": return "k";
            case "-··": return "d";
            case "·--": return "w";
            case "·-·": return "r";
            case "··-": return "u";
            case "···": return "s";
            case "--·-": return "q";
            case "--··": return "z";
            case "-·--": return "y";
            case "-·-·": return "c";
            case "-··-": return "x";
            case "-···": return "b";
            case "·---": return "j";
            case "·--·": return "p";
            case "·-··": return "l";
            case "··-·": return "f";
            case "···-": return "v";
            case "····": return "h";
            case "-----": return "0";
            case "----·": return "9";
            case "---··": return "8";
            case "--···": return "7";
            case "-····": return "6";
            case "·----": return "1";
            case "··---": return "2";
            case "···--": return "3";
            case "····-": return "4";
            case "·····": return "5";
            // case "----": return "ch";
            // case "----": return "ĥ";
            case "----": return "š";
            case "---·": return "ø";
            // case "---·": return "ó";
            // case "---·": return "ö";
            // case "·-·-": return "ä";
            // case "·-·-": return "ą";
            case "·-·-": return "æ";
            case "··--": return "ü";
                // case "··--": return "ŭ";
            case "--·--": return "ñ";
            // case "--·--": return "ń";
            case "--·-·": return "ĝ";
            case "--··-": return "ż";
            case "-·--·": return "(";
            case "-·-··": return "ç";
            // case "-·-··": return "ć";
            // case "-·-··": return "ĉ";
            case "-··-·": return "/";
            case "-···-": return "=";
            case "·---·": return "ĵ";
            case "·--·-": return "à";
            // case "·--·-": return "å";
            case "·--··": return "þ";
            case "·-·-·": return "+";
            // case "·-··-": return "è";
            case "·-··-": return "ł";
            case "·-···": return "&";
            case "··--·": return "ð";
            // case "··-··": return "đ";
            case "··-··": return "é";
            // case "··-··": return "ę";
            case "···-·": return "ŝ";
            case "---···": return ":";
            case "--··--": return ",";
            case "--··-·": return "ź";
            case "-·--·-": return ")";
            case "-·-·--": return "!";
            case "-·-·-·": return ";";
            case "-····-": return "-";
            case "·----·": return "'";
            case "·--·-·": return "@";
            case "·-·-·-": return ".";
            case "·-··-·": return "\"";
            case "··--·-": return "_";
            case "··--··": return "?";
            case "···-··-": return "$";
            case "···-···": return "ś";
            default: return "";
        }
    }


    static String fromChar(String buffer) {
        switch (buffer) {
            case " ": return " ";
            case "e": return "·";
            case "t": return "-";
            case "a": return "·-";
            case "i": return "··";
            case "m": return "--";
            case "n": return "-·";
            case "d": return "-··";
            case "g": return "--·";
            case "k": return "-·-";
            case "o": return "---";
            case "r": return "·-·";
            case "s": return "···";
            case "u": return "··-";
            case "w": return "·--";
            case "b": return "-···";
            case "c": return "-·-·";
            case "f": return "··-·";
            case "h": return "····";
            case "j": return "·---";
            case "l": return "·-··";
            case "p": return "·--·";
            case "q": return "--·-";
            case "v": return "···-";
            case "x": return "-··-";
            case "y": return "-·--";
            case "z": return "--··";
            case "0": return "-----";
            case "1": return "·----";
            case "2": return "··---";
            case "3": return "···--";
            case "4": return "····-";
            case "5": return "·····";
            case "6": return "-····";
            case "7": return "--···";
            case "8": return "---··";
            case "9": return "----·";
            case "ä": return "·-·-";
            case "ą": return "·-·-";
            case "æ": return "·-·-";
            case "ĥ": return "----";
            case "ó": return "---·";
            case "ö": return "---·";
            case "ø": return "---·";
            case "š": return "----";
            case "ŭ": return "··--";
            case "ü": return "··--";
            case "(": return "-·--·";
            case "/": return "-··-·";
            case "&": return "·-···";
            case "+": return "·-·-·";
            case "=": return "-···-";
            case "à": return "·--·-";
            case "å": return "·--·-";
            case "ć": return "-·-··";
            case "ĉ": return "-·-··";
            case "ç": return "-·-··";
            case "ð": return "··--·";
            case "đ": return "··-··";
            case "è": return "·-··-";
            case "é": return "··-··";
            case "ę": return "··-··";
            case "ĝ": return "--·-·";
            case "ĵ": return "·---·";
            case "ł": return "·-··-";
            case "ń": return "--·--";
            case "ñ": return "--·--";
            case "ŝ": return "···-·";
            case "ż": return "--··-";
            case "þ": return "·--··";
            case "-": return "-····-";
            case ",": return "--··--";
            case ";": return "-·-·-·";
            case ":": return "---···";
            case "!": return "-·-·--";
            case "?": return "··--··";
            case ".": return "·-·-·-";
            case "'": return "·----·";
            case ")": return "-·--·-";
            case "@": return "·--·-·";
            case "\"": return "·-··-·";
            case "ź": return "--··-·";
            case "$": return "···-··-";
            case "ś": return "···-···";
            default:  return "";
        }
    }

}