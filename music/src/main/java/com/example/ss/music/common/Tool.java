package com.example.ss.music.common;

import com.example.ss.music.DataSource;
import com.example.ss.music.domain.SongDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Tool {

    private RestTemplate restTemplate=new RestTemplate();
    private ObjectMapper objectMapper=new ObjectMapper();

    public JsonNode getRoot(String url) throws IOException {
        ResponseEntity<String> responseEntity= restTemplate.getForEntity(url,String.class);
        if (responseEntity.getStatusCode()== HttpStatus.OK)
            return objectMapper.readTree(responseEntity.getBody());
        return null;
    }

    public String getUid(JsonNode root){
        return root.get("account").get("id").asText();
    }
    public List<String> getSongListIds(JsonNode root){
        List<String> songListIds=new ArrayList<>();
        JsonNode songlists=root.get("playlist");
        for (JsonNode songList:songlists)
            songListIds.add(songList.get("id").asText());
        return songListIds;
    }

    public List<String> getSongUrls(JsonNode root){
        JsonNode datas=root.get("data");
        List<String> urls=new ArrayList<>();
        for (JsonNode songData:datas)
            urls.add(songData.get("url").asText());
        return urls;
    }

    public SongDTO getSong(JsonNode songTrack, JsonNode songUrlObj){
        String songId=songTrack.get("id").asText();
        String name=songTrack.get("name").asText();
        String avatarUrl=songTrack.get("al").get("picUrl").asText();
        String author="";
        JsonNode authors=songTrack.get("ar");
        if (authors.size()>0){
            int size=authors.size();
            author+=authors.get(0).get("name").asText();
            for (int index=1;index<size;index++)
                author+=("、"+authors.get(size).asText());
        }
        String totalName=author+" - "+name;

        String songUrl=songUrlObj.get("url").asText();
        String extensionName="."+songUrlObj.get("type").asText();
        return SongDTO.builder()
                .name(name)
                .source(DataSource.WangYiYun)
                .totalName(totalName)
                .author(author)
                .avatarUrl(avatarUrl)
                .extensionName(extensionName)
                .remoteId(songId)
                .songUrl(songUrl)
                .build();
    }

    public String getSongListName(JsonNode root){
        return root.get("playlist").get("name").asText();
    }
}
