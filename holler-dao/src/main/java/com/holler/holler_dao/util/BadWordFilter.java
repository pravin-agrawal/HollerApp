package com.holler.holler_dao.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pravina on 10/09/16.
 */
public class BadWordFilter{

    public static String[] swearWords = {
            "f.*?u.*?k",
            "s.*?h.*?t",
            "bi.*?h",
            "bas.*?d",
            "m.*?f.*?",
            "c.*?nt",
            "as.*?s",
            "s.*?ck",
            "w.*?nk",
            "co.*?on",
            "wo.*?g",
            "ni.*?g.*?r",
            "c.*?c.*?k",
            "penis",
            "vagina",
            "c.*?um",
            "p.*?i.*?s",
            "p.*?orn",
            "ar.*?se",
            "nexon",
            "ho.*?r.*?ny",
            "dil.*?do",
            "doggystyle",
            "cl.*?it",
            "fann.*?y",
            "ho.*?re.*?",
            "kn.*?ob",
            "mastur.*?",
            "hitler",
            "n.*?uts",
            "sob.*?",
            "shag.*?",
            "sl.*?ut.*?",
            "testi.*?",
            "t.*?wa.*?t",
            "viagr.*?a",
            "wil.*?ly",
            "wil.*?lie",
            "jism",
            "dog.*?gy",
            "donkeyri.*?b",
            "breas.*?t",
            "bl.*?wjo.*?b",
            "b.*?b",
            "beastiality",
            "an.*?al",
            "cawk",
            "pus.*?s.*?",
            "rim.*?m",
            "ejaculate",
            "ejakulate",
            "er.*?ct",
            "horni",
            "horna",
            "se.*?x",
            "se.*?ck",
            "ga.*?y",
            "fk",
            "we*?nis"
    };


    public static String filter(String in) {
        for (String swearWord : swearWords) {
            String stars;
            Pattern pat = Pattern.compile(swearWord, Pattern.CASE_INSENSITIVE);
            Matcher mat = pat.matcher(in);
            while (mat.find()) {
                char[] haha = new char[mat.end() - mat.start()];
                for (int i = 0; i < mat.end() - mat.start(); i++) {
                    haha[i] = '*';
                }
                stars = new String(haha);
                in = mat.replaceFirst(stars);
            }
        }
        return in;
    }

    public static void main(String a[]){
        String s = "sexy";
        String filteredWord = BadWordFilter.filter((s));
        System.out.println(filteredWord);
    }
}
