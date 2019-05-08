package com.example.ss.music.service;

import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Response;
import com.example.ss.music.common.Tool;
import com.example.ss.music.domain.NetworkSong;
import com.example.ss.music.domain.NetworkSongList;
import com.fasterxml.jackson.databind.JsonNode;
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
        String url=ApiBuilder.songList(songListId);
        JsonNode root=tool.getRoot(url);
        JsonNode tracks=root.get("playlist").get("tracks");

        List<NetworkSong> songs=new ArrayList<>();
        List<String> songIds=new ArrayList<>();
        for (JsonNode songTrack:tracks){
            String name=songTrack.get("name").asText();
            String author="";
            JsonNode authors=songTrack.get("ar");
            if (authors.size()>0){
                int size=authors.size();
                author+=authors.get(0).get("name").asText();
                for (int index=1;index<size;index++)
                    author+=("、"+authors.get(size).asText());
            }
            songs.add(NetworkSong.builder().name(name).author(author).build());
            songIds.add(songTrack.get("id").asText());
        }
        List<String> urls=songService.songUrlsByIds(songIds);
        int size=urls.size();
        for (int index=0;index<size;index++){
            String songUrl=urls.get(index);
            String extensionName=songUrl.substring(songUrl.lastIndexOf("."));
            NetworkSong song=songs.get(index);
            song.setUrl(songUrl);
            song.setTotalName(song.getAuthor()+" - "+song.getName()+extensionName);
        }
        return NetworkSongList.builder().name(tool.getSongListName(root)).networkSongs(songs).build();
    }

    private List<String> songListBriefs(String uid){
        try {
            return tool.getSongListIds(tool.getRoot(ApiBuilder.songListBrief(uid)));
        } catch (IOException e) {
            return null;
        }
    }

}
