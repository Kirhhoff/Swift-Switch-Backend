package com.example.ss.music.controller;


import com.example.ss.music.domain.SongListDTO;
import com.example.ss.music.service.LoginService;
import com.example.ss.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired LoginService loginService;
    @Autowired SongListService songListService;
    @PostMapping("/uid/phone")
    String uidByPhone(@NotNull @RequestParam String account, @NotNull @RequestParam String password) throws IOException {
        return loginService.loginByPhone(account,password);
    }

    @PostMapping("/uid/email")
    String uidByEmail(@NotNull @RequestParam String account, @NotNull @RequestParam String password) throws IOException {
        return loginService.loginByEmail(account,password);
    }

    @GetMapping("/songlist/uid")
    List<SongListDTO> songListByUid(@NotNull @RequestParam String uid) throws IOException {
        return songListService.songListsByUid(uid);
    }

    @PostMapping("/songlist/phone")
    List<SongListDTO> songListByPhone(@NotNull @RequestParam String account, @NotNull @RequestParam String password) throws IOException {
        return songListService.songListByPhone(account,password);
    }

    @PostMapping("/songlist/email")
    List<SongListDTO> songListByEmail(@NotNull @RequestParam String account, @NotNull @RequestParam String password) throws IOException {
        return songListService.songListByEmail(account,password);
    }

}
