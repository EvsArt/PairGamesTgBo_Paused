package com.artevseev.PairGamesTgBot.service;

public class Funcs {

    static String reverseText(String text) {
        StringBuilder res = new StringBuilder();
        for(int i = text.length()-1; i >= 0; i--){
            res.append(text.charAt(i));
        }
        return res.toString();
    }

    static boolean isNumber(String str) {
        for(int i=0; i < str.length(); i++){
            char ch = str.charAt(i);
            if('0' > ch || ch > '9'){
                if("-+".contains(Character.toString(ch)) && i == 0){
                    continue;
                }
                return false;
            }
        }
        return true;
    }
}
