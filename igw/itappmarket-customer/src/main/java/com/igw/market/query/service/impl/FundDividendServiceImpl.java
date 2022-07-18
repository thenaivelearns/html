package com.igw.market.query.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.util.PageUtil;
import com.igw.market.push.vo.DividendNoticeVO;
import com.igw.market.query.dao.FundDividendDao;
import com.igw.market.query.domain.FundDividend;
import com.igw.market.query.domain.dto.FundDividendDTO;
import com.igw.market.query.domain.vo.FundDividendVO;
import com.igw.market.query.service.FundDividendService;
import com.igw.market.systemInfo.domain.Management;
import com.igw.market.systemInfo.service.ManagementService;
import com.igw.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FundDividendServiceImpl implements FundDividendService {
    @Autowired
    private FundDividendDao fundDividendDao;
    private static Logger logger = Logger.getLogger(FundDividendServiceImpl.class);

    @Autowired
    private ManagementService managementService;

    @Override
    public int insertFundDividend(FundDividendVO fundDividendVO) {
        if (fundDividendVO.getFundCode() != null && !(fundDividendVO.getFundCode().equals(""))) {
            FundDividend fundDividend = new FundDividend();
            BeanUtils.copyProperties(fundDividendVO,fundDividend);
            fundDividend.setCreatedDate(new Date());
            fundDividend.setSentStatus("1");
            fundDividend.setReviewStatus("1");
            fundDividend.setValidFlag("0");
            int row = fundDividendDao.insertFundDividend(fundDividend);
            if (row > 0) {
                logger.info("添加成功");
            }
            return row;
        }
        return 0;
    }


    @Override
    public ResultMessage saveFundDividend(String userName,FundDividendVO fundDividendVO) {
        if (StringUtil.isNotEmpty(fundDividendVO.getOperatorType()) && "1".equals(fundDividendVO.getOperatorType())){
            //复核
            FundDividend fundDividend = new FundDividend();
            fundDividend.setSysId(fundDividendVO.getSysId());
            List<FundDividendDTO> fundDividends = fundDividendDao.queryFundDividend(fundDividend);
            if (fundDividends == null || fundDividends.size() == 0){
                return ResultMessage.fail("数据不存在");
            }
            //复核通过
            fundDividendVO.setUpdatedUser(userName);
            fundDividendVO.setUpdatedDate(new Date());
            fundDividendVO.setReviewStatus("2");
            int row = updateFundDividend(fundDividendVO);
            return row > 0 ? ResultMessage.ok("分红模板复核通过") : ResultMessage.fail("分红模板复核失败");
        }else {
            if(StringUtil.isNotEmpty(fundDividendVO.getSysId())){
                //修改
                FundDividend fundDividend = new FundDividend();
                fundDividend.setSysId(fundDividendVO.getSysId());
                List<FundDividendDTO> fundDividends = fundDividendDao.queryFundDividend(fundDividend);
                if (fundDividends == null || fundDividends.size() == 0){
                    return ResultMessage.fail("数据不存在");
                }
                fundDividendVO.setCreatedUser(userName);
                int row = updateFundDividend(fundDividendVO);
                return row > 0 ? ResultMessage.ok("分红模板修改成功") : ResultMessage.fail("分红模板修改失败");
            }else {
                fundDividendVO.setCreatedUser(userName);
                fundDividendVO.setSysId(UUID.randomUUID().toString().replace("-",""));
                int row = insertFundDividend(fundDividendVO);
                return row > 0 ? ResultMessage.ok("分红模板添加成功") : ResultMessage.fail("分红模板添加失败");
            }
        }
    }

    @Override
    public int updateFundDividend(FundDividendVO fundDividendVO) {
        String sysId = fundDividendVO.getSysId();
        FundDividend fund = new FundDividend();
        fund.setSysId(sysId);
        List<FundDividendDTO> fundDividends = fundDividendDao.queryFundDividend(fund);
        if (!ObjectUtils.isEmpty(fundDividends.get(0))) {
            int row = fundDividendDao.updateFundDividend(fundDividendVO);
            if (row > 0) {
                logger.info("修改成功");
            }
            return row;
        }
        logger.info("修改失败");
        return 0;
    }

    @Override
    public int deleteFundDividend(List<String> ids) {
        int row = fundDividendDao.deleteFundDividend(ids);
        if (row > 0) {
            logger.info("删除成功");
        } else {
            logger.info("删除失败");
        }
        return row;
    }

    @Override
    public List<FundDividendDTO> queryFundDividend(FundDividend fundDividend) {
        return fundDividendDao.queryFundDividend(fundDividend);
    }

    @Override
    public PageUtil searchFundDividend(String userName,String systemId, DividendNoticeVO dividendNoticeVO) {
        PageHelper.startPage(dividendNoticeVO.getPageNumber(), dividendNoticeVO.getPageSize());
        FundDividend fundDividend = new FundDividend();
        BeanUtils.copyProperties(dividendNoticeVO,fundDividend);
        List<FundDividendDTO> fundDividends = fundDividendDao.queryFundDividend(fundDividend);

        //获取用户角色信息
        List<Management> userRolelist = managementService.getUserRoleList(systemId);
        List<Management> loginUserRoles = userRolelist.stream().filter(a -> a.getUserId().equals(userName)).collect(Collectors.toList());;
        if (fundDividends != null && fundDividends.size() > 0){
            for (FundDividendDTO c:fundDividends){
                //创建分红通知的用户角色
                List<Management> createNoticeUserRoles = userRolelist
                        .stream()
                        .filter(n -> n.getUserId().equals(c.getCreatedUser()))
                        .collect(Collectors.toList());;
                //将分红通知创建者角色放入set
                Set<String> ids = createNoticeUserRoles
                        .stream()
                        .map(Management::getRoleId)
                        .collect(Collectors.toSet());
                c.setIsShowCheck("2");
                if (c.getCreatedUser().equals(userName)){
                    //不显示复核
                    c.setIsShowCheck("2");
                }else {
                    if ("1".equals(c.getReviewStatus())){
                        if (loginUserRoles != null && loginUserRoles.size() > 0){
                            //判断当前登录用户角色是否和创建者角色一致
                            List<Management> hasUser = loginUserRoles
                                    .stream()
                                    .filter(b -> ids.contains(b.getRoleId()))
                                    .collect(Collectors.toList());
                            if (hasUser != null && hasUser.size() > 0){
                                //显示复核
                                c.setIsShowCheck("1");
                            }
                        }
                    }
                }
            }
        }
        PageInfo<FundDividendDTO> pageInfo = PageInfo.of(fundDividends);
        return new PageUtil(pageInfo.getTotal(), fundDividends);
    }
}
