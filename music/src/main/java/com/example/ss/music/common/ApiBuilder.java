package com.example.ss.music.common;

import java.util.List;

public class ApiBuilder {

    private static String domain="http://localhost:3000";

    private static String loginPhone="/login/cellphone";
    private static String loginEmail="/login";
    private static String songListBrief="/user/playlist";
    private static String songList="/playlist/detail";
    private static String song="/song/url";

    private static String prefix(String prefix){
        return domain+prefix;
    }
    public static String loginPhone(String phoneNumber,String password){
        return prefix(loginPhone)+"?"+"phone="+phoneNumber+"&"+"password="+password;
    }

    public static String loginEmail(String email,String password){
        return prefix(loginEmail)+"?"+"email="+email+"&"+"password="+password;
    }

    public static String songListBrief(String uid){
        return prefix(songListBrief)+"?"+"uid="+uid;
    }

    public static String songList(String songListId){
        return prefix(songList)+"?"+"id="+songListId;
    }

    public static String songs(List<String> songIds){
        if (!songIds.isEmpty()){
            String parameters=songIds.get(0);
            songIds=songIds.subList(1,songIds.size());
            for (String songId:songIds)
                parameters+=(","+songId);
            return prefix(song)+"?"+"id="+parameters;
        }
        return null;
    }
}
