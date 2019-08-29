package com.example.ss.music.service;

import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Tool;
import com.example.ss.music.domain.SongDTO;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {

    @Autowired Tool tool;

    public List<String> songUrlsByIds(List<String> songIds) throws IOException {
        return tool.getSongUrls(tool.getRoot(ApiBuilder.songs(songIds)));
    }

    public List<SongDTO> songsByIds(List<String> songIds) throws IOException {
        JsonNode songTrackRoot=tool.getRoot(ApiBuilder.songs(songIds));
        JsonNode songUrlRoot=tool.getRoot(ApiBuilder.songs(songIds));
        int size=songTrackRoot.size();
        List<SongDTO> songDTOS =new ArrayList<>();
        for (int i=0;i<size;i++)
            songDTOS.add(tool.getSong(songTrackRoot,songUrlRoot));
        return songDTOS;
    }
}
