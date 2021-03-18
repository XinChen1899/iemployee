package org.zuel.iemployee.demo.service;

import org.zuel.iemployee.demo.constant.CompanyConstant;
import org.zuel.iemployee.demo.dao.CompanyDAO;
import org.zuel.iemployee.demo.dao.impl.CompanyDAOImpl;
import org.zuel.iemployee.demo.model.Company;
import org.zuel.iemployee.demo.model.Post;
import org.zuel.iemployee.demo.model.ResponseObj;
import org.zuel.iemployee.demo.model.User;
import org.zuel.iemployee.demo.util.ResponseUtil;

import java.util.List;

/**
 * create time: 2021/3/8 20:42
 * author: XinChen1899
 */

public class CompanyService {

    private static CompanyDAO companyDAO = new CompanyDAOImpl();

    /**
     * 插入企业
     * @param company
     * @return
     */
    public static ResponseObj insertCompany(Company company){
        if(company == null){
            return ResponseUtil.badArgument();
        }
        if(companyDAO.insertCompany(company)){
            return ResponseUtil.fail(CompanyConstant.INSERT_COMPANY_UNSUCCESSFULLY_CODE, CompanyConstant.INSERT_COMPANY_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("企业插入成功", company);
    }

    /**
     * 删除企业
     * @param company
     * @return
     */
    public static ResponseObj delCompany(Company company){
        if(company == null){
            return ResponseUtil.badArgument();
        }
        if(companyDAO.deleteCompany(company)){
            return ResponseUtil.fail(CompanyConstant.DELETE_COMPANY_UNSUCCESSFULLY_CODE, CompanyConstant.DELETE_COMPANY_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("企业删除成功", company);
    }

    /**
     * 修改企业信息
     * @param company
     * @return
     */
    public static ResponseObj updateCompany(Company company){
        if(company == null){
            return ResponseUtil.badArgument();
        }
        if(companyDAO.updateCompany(company)){
            return ResponseUtil.fail(CompanyConstant.UPDATE_COMPANY_UNSUCCESSFULLY_CODE, CompanyConstant.UPDATE_COMPANY_UNSUCCESSFULLY);
        }
        return ResponseUtil.ok("企业秀改成功", company);
    }

    /**
     * 按名字找企业
     * @param name
     * @param companyList
     * @return
     */
    public static ResponseObj searchCompanyByName(String name, List<Company> companyList){
        if(name == null || name.length() == 0){
            return ResponseUtil.badArgument();
        }
        companyList = companyDAO.selectCompanyWithNameLike(name);
        if(companyList == null || companyList.size() == 0){
            return ResponseUtil.fail(CompanyConstant.COMPANY_NOT_EXIST_CODE, CompanyConstant.COMPANY_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", companyList);
    }

    /**
     * 按帖子找企业
     * @param post
     * @param company
     * @return
     */
    public static ResponseObj searchCompanyByPost(Post post, Company company){
        if(post == null){
            return ResponseUtil.badArgument();
        }
        company = companyDAO.selectCompanyById(post.getCompany());
        if(company == null){
            return ResponseUtil.fail(CompanyConstant.COMPANY_NOT_EXIST_CODE, CompanyConstant.COMPANY_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", company);
    }

    /**
     * 找用户的企业
     * @param user
     * @param company
     * @return
     */
    public static ResponseObj searchCompanyByUser(User user, Company company){
        if(user == null){
            return ResponseUtil.badArgument();
        }
        company = companyDAO.selectCompanyById(user.getCompany());
        if(company == null){
            return ResponseUtil.fail(CompanyConstant.COMPANY_NOT_EXIST_CODE, CompanyConstant.COMPANY_NOT_EXIST);
        }
        return ResponseUtil.ok("操作成功", company);
    }
}
