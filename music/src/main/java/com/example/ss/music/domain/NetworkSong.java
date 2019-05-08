package com.example.ss.music.domain;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class NetworkSong implements Serializable {

    private static final long serialVersionUID=5L;

    /**
     * 歌曲名字，用于给用户看
     */
    private String totalName;

    /**
     * 歌曲名字
     */
    public String name;

    /**
     * 歌曲作者
     */
    public String author;

    /**
     * 歌曲的下载url
     */
    private String url;

}
