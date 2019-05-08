package com.example.ss.music.controller;


import com.example.ss.music.common.Response;
import com.example.ss.music.service.LoginService;
import com.example.ss.music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired LoginService loginService;
    @Autowired SongListService songListService;
    @PostMapping("/uid/phone")
    Response uidByPhone(@NotNull @RequestParam String account, @NotNull @RequestParam String password){
        return loginService.loginByPhone(account,password);
    }

    @PostMapping("/uid/email")
    Response uidByEmail(@NotNull @RequestParam String account, @NotNull @RequestParam String password){
        return loginService.loginByEmail(account,password);
    }

    @GetMapping("/songlist/uid")
    Response songListByUid(@NotNull @RequestParam String uid){
        return songListService.songListsByUid(uid);
    }

    @PostMapping("/songlist/phone")
    Response songListByPhone(@NotNull @RequestParam String account, @NotNull @RequestParam String password){
        return songListService.songListByPhone(account,password);
    }

    @PostMapping("/songlist/email")
    Response songListByEmail(@NotNull @RequestParam String account, @NotNull @RequestParam String password){
        return songListService.songListByEmail(account,password);
    }

}
