package com.wencoder.demo.fill;


import com.wencoder.fill.FillEnable;
import com.wencoder.fill.FillService;
import com.wencoder.fill.FillSource;

import java.util.Arrays;
import java.util.List;


@FillService
public class TestService {

    @FillEnable
    public UserVO getUser(String id) {
        UserVO userVO = new UserVO();
        userVO.setMemberId(id)
                .setRealName("admin")
                .setCompanyId("1001")
                .setCompanyId1("1002");

        UserVO2 userVO2 = new UserVO2();
        userVO2.setMemberId(id);

        userVO.setUserVO2(userVO2);
        return userVO;
    }


    @FillSource(value = "userList", sourceKey = "memberId")
    public List<User> getCompanyList(List<String> ids) {
        System.out.println("userList： " + ids);
        User user1 = new User().setMemberId("1")
                .setRealName("飞鱼")
                .setCardId(ids.toString())
                .setAvatar("头像1")
                .setMobile("1850001");
        User user2 = new User().setMemberId("2")
                .setRealName("feiyu")
                .setCardId(ids.toString())
                .setAvatar("头像2")
                .setMobile("1850002");
        return Arrays.asList(user1, user2);
    }
}
