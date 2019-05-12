package com.example.ss.music.service;

import com.example.ss.music.DataSource;
import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Response;
import com.example.ss.music.common.Tool;
import com.example.ss.music.domain.NetworkSong;
import com.example.ss.music.domain.NetworkSongList;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.catalina.webresources.JarResourceSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongListService {

    @Autowired Tool tool;
    @Autowired SongService songService;
    @Autowired LoginService loginService;

    public Response songListByPhone(@NotNull String phoneNumber,@NotNull String password){
        Response response=loginService.loginByPhone(phoneNumber,password);
        if(response.getStatus()== Response.Status.Success){
            String uid=(String)response.getBody();
            return songListsByUid(uid);
        }
        return Response.error(response.getBody(),null);
    }

    public Response songListByEmail(@NotNull String email,@NotNull String password){
        Response response=loginService.loginByEmail(email,password);
        if(response.getStatus()== Response.Status.Success){
            String uid=(String)response.getBody();
            return songListsByUid(uid);
        }
        return Response.error(response.getBody(),null);
    }
    public Response songListsByUid(@NotNull String uid){
        List<String> songListIds=songListBriefs(uid);
        List<NetworkSongList> songLists=new ArrayList<>();
        try {
            for (String songListId:songListIds)
                songLists.add(songListById(songListId));
            return Response.success(songLists,null);
        }catch (IOException e){
            return Response.error(null,"IO错误");
        }
    }

    private NetworkSongList songListById(String songListId) throws IOException {
        JsonNode root=tool.getRoot(ApiBuilder.songList(songListId));
        JsonNode tracks=root.get("playlist").get("tracks");
        String uid=root.get("playlist").get("creator").get("userId").asText();
        String avatarUrl=root.get("playlist").get("coverImgUrl").asText();
        List<NetworkSong> songs=new ArrayList<>();
        List<String> songIds=new ArrayList<>();
        for (JsonNode songTrack:tracks)
            songIds.add(songTrack.get("id").asText());

        JsonNode songUrlObjs=tool.getRoot(ApiBuilder.songs(songIds)).get("data");

        int size=tracks.size();
        for (int index=0;index<size;index++){
            NetworkSong song=tool.getSong(tracks.get(index),songUrlObjs.get(index));
            song.setUid(uid);
            songs.add(song);
        }

        return NetworkSongList.builder()
                .name(tool.getSongListName(root))
                .remoteId(songListId)
                .uid(uid)
                .source(DataSource.WangYiYun)
                .avatarUrl(avatarUrl)
                .networkSongs(songs)
                .build();
    }

    private List<String> songListBriefs(String uid){
        try {
            return tool.getSongListIds(tool.getRoot(ApiBuilder.songListBrief(uid)));
        } catch (IOException e) {
            return null;
        }
    }

}
