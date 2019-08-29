package com.example.ss.music.service;

import com.example.ss.music.DataSource;
import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Tool;
import com.example.ss.music.domain.SongDTO;
import com.example.ss.music.domain.SongListDTO;
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

    public List<SongListDTO> songListByPhone(@NotNull String phoneNumber, @NotNull String password) throws IOException {
        String uid=loginService.loginByPhone(phoneNumber,password);
        return songListsByUid(uid);
    }

    public List<SongListDTO> songListByEmail(@NotNull String email, @NotNull String password) throws IOException {
        String uid=loginService.loginByEmail(email,password);
        return songListsByUid(uid);
    }
    public List<SongListDTO> songListsByUid(@NotNull String uid) throws IOException {
        List<String> songListIds=songListBriefs(uid);
        List<SongListDTO> songLists=new ArrayList<>();
        for (String songListId:songListIds)
            songLists.add(songListById(songListId));
        return songLists;
    }

    private SongListDTO songListById(String songListId) throws IOException {
        JsonNode root=tool.getRoot(ApiBuilder.songList(songListId));
        JsonNode tracks=root.get("playlist").get("tracks");
        String uid=root.get("playlist").get("creator").get("userId").asText();
        String avatarUrl=root.get("playlist").get("coverImgUrl").asText();
        List<SongDTO> songs=new ArrayList<>();
        List<String> songIds=new ArrayList<>();
        for (JsonNode songTrack:tracks)
            songIds.add(songTrack.get("id").asText());

        JsonNode songUrlObjs=tool.getRoot(ApiBuilder.songs(songIds)).get("data");

        int size=tracks.size();
        for (int index=0;index<size;index++){
            SongDTO song=tool.getSong(tracks.get(index),songUrlObjs.get(index));
            song.setUid(uid);
            songs.add(song);
        }

        return SongListDTO.builder()
                .name(tool.getSongListName(root))
                .remoteId(songListId)
                .uid(uid)
                .source(DataSource.WangYiYun)
                .avatarUrl(avatarUrl)
                .songDTOs(songs)
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
