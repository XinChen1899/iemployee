package org.zuel.iemployee.demo.dao;

import org.zuel.iemployee.demo.model.Company;

import java.util.List;

/**
 * create time: 2021/3/8 20:53
 * author: XinChen1899
 */

public interface CompanyDAO {

    boolean insertCompany(Company company);

    boolean deleteCompany(Company company);

    boolean updateCompany(Company company);

    Company selectCompanyById(int id);

    Company selectCompanyByName(String name);

    List<Company> selectCompanyWithNameLike(String model);
}
