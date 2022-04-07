package com.wencoder.demo.fill;

import com.wencoder.fill.FillObj;
import com.wencoder.fill.FillTarget;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserVO extends User {

    @FillTarget(value = "user", sourceField = "realName")
    private String nv;

    @FillObj
    private UserVO2 userVO2;
}

