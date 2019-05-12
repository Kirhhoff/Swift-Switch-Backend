# Swift-Switch-Backend
## 后端接口文档

- 部署在 
---
## 接口调用
1. 获取用户uid
    - 通过手机获取: **POST /uid/phone**
        + 参数
        1. account -- 手机号
        2. password -- 密码
    - 通过邮箱获取: **POST /uid/email**
        + 参数
        1. account -- 邮箱号
        2. password -- 密码

2. 获取歌单信息
    - 通过uid获取：**GET /songlist/uid**
        + 参数
        1. uid -- 用户id
    - 通过手机获取: **POST /songlist/phone**
        + 参数
        1. account -- 手机号
        2. password -- 密码
    - 通过邮箱获取: **POST /songlist/email**
        + 参数
        1. account -- 邮箱号
        2. password -- 密码
