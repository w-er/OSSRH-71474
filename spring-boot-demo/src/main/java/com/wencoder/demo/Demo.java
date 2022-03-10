package com.wencoder.demo;

import com.wencoder.set.SetValue;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class Demo {

    private Integer id;

    @SetValue(beanClass = DemoService, targetField = username + "")
    private String username;

    private String nickname;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
