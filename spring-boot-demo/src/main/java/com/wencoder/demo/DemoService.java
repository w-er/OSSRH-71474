package com.wencoder.demo;

import com.wencoder.set.EnableSetValue;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DemoService {


    @EnableSetValue
    public Demo get() {
        Demo demo = new Demo();
        demo.setId(1);
        return demo;
    }

    public User getOne(Integer id) {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setNickname("MM");
        return user;
    }
    public List<User> getList(List<Integer> ids) {
        User user = new User();
        user.setId(1);
        user.setUsername("admin2");
        user.setNickname("MM2");
        return Arrays.asList(user);
    }


}
