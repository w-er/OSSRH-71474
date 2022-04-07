package com.wencoder.demo.fill;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Company {

    private String companyId;
    private String companyName;

}
