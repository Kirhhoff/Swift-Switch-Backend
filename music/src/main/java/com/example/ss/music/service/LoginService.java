package com.example.ss.music.service;

import com.example.ss.music.common.ApiBuilder;
import com.example.ss.music.common.Response;
import com.example.ss.music.common.Tool;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService {

    @Autowired Tool tool;

    public Response loginByPhone(String phoneNumber,String password){
        return login(ApiBuilder.loginPhone(phoneNumber,password));
    }

    public Response loginByEmail(String email,String password){
        return login(ApiBuilder.loginEmail(email,password));
    }

    private Response login(String url){
        try {
            JsonNode root=tool.getRoot(url);
            if(root==null)
                return Response.error(null,"登陆失败");
            return Response.success(tool.getUid(root),null);

        } catch (IOException e) {
            return Response.error(null,"Jackson异常");
        }
    }
}
