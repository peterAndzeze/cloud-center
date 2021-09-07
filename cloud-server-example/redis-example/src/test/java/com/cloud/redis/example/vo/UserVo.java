package com.cloud.redis.example.vo;

import java.io.Serializable;

/**
 * @className: UserVo
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/1
 **/
public class UserVo implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
