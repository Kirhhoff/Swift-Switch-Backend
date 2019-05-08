package com.example.ss.music.service;

import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SongService {

    @Autowired Tool tool;

    public List<String> songUrlsByIds(List<String> songIds) throws IOException {
        return tool.getSongUrls(tool.getRoot(ApiBuilder.songs(songIds)));
    }
}
