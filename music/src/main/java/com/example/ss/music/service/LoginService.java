package com.example.ss.music.service;

import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Tool;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService {

    @Autowired Tool tool;

    public String loginByPhone(String phoneNumber,String password) throws IOException {
        return login(ApiBuilder.loginPhone(phoneNumber,password));
    }

    public String loginByEmail(String email,String password) throws IOException {
        return login(ApiBuilder.loginEmail(email,password));
    }

    private String login(String url) throws IOException {
        JsonNode root=tool.getRoot(url);
        if(root==null){
            //TODO 请求失败
        }
        return tool.getUid(root);
    }
}
