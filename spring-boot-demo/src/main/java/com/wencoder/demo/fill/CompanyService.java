package com.wencoder.demo.fill;


import com.wencoder.fill.FillService;
import com.wencoder.fill.FillSource;

import java.util.Arrays;
import java.util.List;

@FillService
public class CompanyService {

    @FillSource(value = "company1", sourceKey = "companyId")
    public Company getCompany(String companyId) {
        return new Company().setCompanyId(companyId).setCompanyName("飞鱼网络科技" + companyId);
    }

    @FillSource(value = "company2", sourceKey = "companyId")
    public List<Company> getCompanyList(List<String> companyIds) {
        System.out.println("companyIds： " + companyIds);
        Company company1 = new Company().setCompanyId("1001").setCompanyName("飞鱼网络科技2");
        Company company2 = new Company().setCompanyId("1002").setCompanyName("本无网络科技2");
        return Arrays.asList(company1, company2);
    }

}
