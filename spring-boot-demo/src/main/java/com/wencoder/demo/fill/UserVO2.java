package com.wencoder.demo.fill;

import com.wencoder.fill.FillModel;
import com.wencoder.fill.FillParam;
import com.wencoder.fill.FillSource;
import com.wencoder.fill.FillTarget;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@FillModel({
        @FillSource(value = "userList", alias = {"user"})
})
public class UserVO2 {

    //  多字段, 如果字段相同可以不用写 sourceField
    @FillParam("user")
    private String memberId;
    @FillTarget("user")
    private String cardId;
    @FillTarget("user")
    private String realName;
    @FillTarget("user")
    private String avatar;
    @FillTarget("user")
    private String mobile;

}

