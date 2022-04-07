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
//        @FillSource(value = "company1", alias = {"company1", "company2"}),
        @FillSource(value = "company2", alias = {"company1", "company2"}),
        @FillSource(value = "userList", alias = {"user"}),
})
public class User {

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

    // 单查询
    @FillParam("company1")
    private String companyId;
    @FillTarget(value = "company1", sourceField = "companyName")
    private String companyName;

    // 集合查询
    @FillParam("company2")
    private String companyId1;
    @FillTarget(value = "company2", sourceField = "companyName")
    private String companyName2;

}

