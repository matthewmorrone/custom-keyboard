package com.custom.keyboard;

class Hexagram {
    
    static String buildDigram(String monograms) {
        switch (monograms) {
            case "⚊⚊":   return "⚌";
            case "⚋⚊":   return "⚍";
            case "⚊⚋":   return "⚎";
            case "⚋⚋":   return "⚏";
            default:       return "";
        }
    }
    
    static String buildTrigram(String monograms) {
        switch (monograms) {
            case "⚊⚌":
            case "⚌⚊":
            case "⚊⚊⚊": return "☰";
            case "⚍⚊":
            case "⚋⚌":
            case "⚋⚊⚊": return "☱";
            case "⚊⚍":
            case "⚎⚊":
            case "⚊⚋⚊": return "☲";
            case "⚏⚊":
            case "⚋⚍":
            case "⚋⚋⚊": return "☳";
            case "⚌⚋":
            case "⚊⚎":
            case "⚊⚊⚋": return "☴";
            case "⚍⚋":
            case "⚋⚎":
            case "⚋⚊⚋": return "☵";
            case "⚎⚋":
            case "⚊⚏":
            case "⚊⚋⚋": return "☶";
            case "⚏⚋":
            case "⚋⚏":
            case "⚋⚋⚋": return "☷";
            default:      return "";
        }
    }

    static String buildHexagram(String trigrams) {
        switch(trigrams) {
            case "☰☰": return "䷀";
            case "☰☱": return "䷸";
            case "☰☲": return "䷰";
            case "☰☳": return "䷐";
            case "☰☴": return "䷨";
            case "☰☵": return "䷘";
            case "☰☶": return "䷠";
            case "☰☷": return "䷈";
            case "☱☰": return "䷇";
            case "☱☱": return "䷿";
            case "☱☲": return "䷷";
            case "☱☳": return "䷗";
            case "☱☴": return "䷯";
            case "☱☵": return "䷟";
            case "☱☶": return "䷧";
            case "☱☷": return "䷏";
            case "☲☰": return "䷆";
            case "☲☱": return "䷾";
            case "☲☲": return "䷶";
            case "☲☳": return "䷖";
            case "☲☴": return "䷮";
            case "☲☵": return "䷞";
            case "☲☶": return "䷦";
            case "☲☷": return "䷎";
            case "☳☰": return "䷂";
            case "☳☱": return "䷺";
            case "☳☲": return "䷲";
            case "☳☳": return "䷒";
            case "☳☴": return "䷪";
            case "☳☵": return "䷚";
            case "☳☶": return "䷢";
            case "☳☷": return "䷊";
            case "☴☰": return "䷅";
            case "☴☱": return "䷽";
            case "☴☲": return "䷵";
            case "☴☳": return "䷕";
            case "☴☴": return "䷭";
            case "☴☵": return "䷝";
            case "☴☶": return "䷥";
            case "☴☷": return "䷍";
            case "☵☰": return "䷃";
            case "☵☱": return "䷻";
            case "☵☲": return "䷳";
            case "☵☳": return "䷓";
            case "☵☴": return "䷫";
            case "☵☵": return "䷛";
            case "☵☶": return "䷣";
            case "☵☷": return "䷋";
            case "☶☰": return "䷄";
            case "☶☱": return "䷼";
            case "☶☲": return "䷴";
            case "☶☳": return "䷔";
            case "☶☴": return "䷬";
            case "☶☵": return "䷜";
            case "☶☶": return "䷤";
            case "☶☷": return "䷌";
            case "☷☰": return "䷁";
            case "☷☱": return "䷹";
            case "☷☲": return "䷱";
            case "☷☳": return "䷑";
            case "☷☴": return "䷩";
            case "☷☵": return "䷙";
            case "☷☶": return "䷡";
            case "☷☷": return "䷉";
            default:    return "";
        }
    }

}