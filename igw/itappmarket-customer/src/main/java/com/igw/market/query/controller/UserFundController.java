package com.igw.market.query.controller;

import com.github.pagehelper.PageHelper;
import com.igw.base.dynamicdatasource.annotation.DBType;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.handler.CalendarHandler;
import com.igw.market.datasource.HangSengDataSource;
import com.igw.market.push.vo.UserPushVO;
import com.igw.market.query.domain.FundDividend;
import com.igw.market.query.domain.dto.FundDividendDTO;
import com.igw.market.query.domain.dto.FundInfoDTO;
import com.igw.market.query.domain.dto.IgwHttpEntity;
import com.igw.market.query.domain.dto.UserFund;
import com.igw.market.query.domain.vo.FundDividendVO;
import com.igw.market.query.domain.vo.FundInfoVO;
import com.igw.market.query.provider.FundInfoProvider;
import com.igw.market.query.service.FundDividendService;
import com.igw.market.query.service.UserFundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("userFund")
public class UserFundController {
    @Autowired
    private UserFundService userFundService;
    @Autowired
    private FundDividendService fundDividendService;
    @Autowired
    private FundInfoProvider fundInfoProvider;
    private static Logger logger = Logger.getLogger(UserFundController.class);

    @Autowired
    private CalendarHandler calendarHandler;

    /**
     * 获取所有微信用户基金资产
     *
     * @return
     */
    @GetMapping("userFundAssets")
    //切换数据源
    @DBType(value = HangSengDataSource.class)
    public ResultMessage<List<UserFund>> userFundAssets(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "30") Integer size) {
        PageHelper.startPage(page, size);
        List<UserFund> userFunds = userFundService.queryAllFund(new UserPushVO());
        return ResultMessage.ok(userFunds);
    }

    /**
     * 获取所有的基金名称和基金代码
     *
     * @return
     */
    @PostMapping("fundNameList")
    public ResultMessage fundNameList(@RequestBody FundInfoVO igwFundInfoVO) {
        //IgwHttpEntity<IgwHttpEntity<FundInfoVO>> entity = igwFundInfoVO.buildSuccessResponse(igwFundInfoVO);
        //logger.info("基金服务getFundBaseList接口参数:" + entity);
        IgwHttpEntity<List<FundInfoDTO>> fundBaseList = fundInfoProvider.getFundBaseList(IgwHttpEntity.buildSuccessResponse(igwFundInfoVO));
        List<FundInfoDTO> infoDTOList = fundBaseList.getData().getBody();
        /*List<Map<String, String>> dataList = new ArrayList<>();
        for (FundInfoDTO fundInfoDTO : infoDTOList) {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("fundCode", fundInfoDTO.getFundCode());
            dataMap.put("fundName", fundInfoDTO.getFundName());
            dataList.add(dataMap);
        }*/
        return ResultMessage.ok(infoDTOList);
    }

    /**
     * @param fundDividendVO
     * @return 添加分红信息
     */
    @PostMapping("insert_funddividend")
    public ResultMessage<String> insertFundDividend(@RequestBody FundDividendVO fundDividendVO) {
        int row = fundDividendService.insertFundDividend(fundDividendVO);
        return row > 0 ? ResultMessage.ok("分红模板添加成功") : ResultMessage.fail("分红模板添加失败");
    }

    /**
     * 修改分红信息
     *
     * @param fundDividendVO
     * @return
     */
    @PostMapping("update_funddividend")
    public ResultMessage<String> updateFundDividend(HttpServletRequest request, @RequestBody FundDividendVO fundDividendVO) {
        String userName = (String) request.getSession().getAttribute("userName");
        log.info("当前用户:{}",userName);
       return fundDividendService.saveFundDividend(userName,fundDividendVO);
    }

    /**
     * 删除分红信息
     *
     * @param
     * @return
     */
    @PostMapping("delete_funddividend")
    public ResultMessage<String> deleteFundDividend(@RequestBody List<String> ids) {
        int row = fundDividendService.deleteFundDividend(ids);
        return row > 0 ? ResultMessage.ok("分红模板删除成功") : ResultMessage.fail("分红模板删除失败");
    }

    /**
     * 查询分红信息
     *
     * @param
     * @return
     */
    @PostMapping("query_funddividend")
    public ResultMessage<List<FundDividendDTO>> queryFundDividend(@RequestBody FundDividend fundDividend) {
        List<FundDividendDTO> tFundDividends = fundDividendService.queryFundDividend(fundDividend);
        return ResultMessage.ok(tFundDividends);
    }
 }
