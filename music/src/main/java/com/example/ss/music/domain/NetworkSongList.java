package com.example.ss.music.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class NetworkSongList implements Serializable {

    private static final long serialVersionUID=4L;

    private String name;

    List<NetworkSong> networkSongs;
}
