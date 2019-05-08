package com.example.ss.music.common;

import com.example.ss.music.domain.NetworkSong;
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

    public String getSongListName(JsonNode root){
        return root.get("playlist").get("name").asText();
    }
}
