package com.igw.market.common.controller;

import com.igw.market.common.domain.AlmanacInfo;
import com.igw.market.common.domain.ResultMessage;
import com.igw.market.common.enums.NoticeTypeEnum;
import com.igw.market.common.service.AlmanacService;
import com.igw.market.common.util.PageUtil;
import com.igw.market.query.service.UserFundService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaotd
 * @date 2021/9/24
 */
@RestController
@RequestMapping("/almanac")
public class AlmanacController {

    @Autowired
    private AlmanacService almanacService;

    @Autowired
    private UserFundService userFundService;

    /**
     * 	通过证件号，证件类型查询公告日历信息
     */
    @GetMapping("/get_almanac_list_by_ide")
    public ResultMessage<List<AlmanacInfo>> getAlmanacListByIde(String identityNo ,String identityType) {
        Map<String, Object> map = new HashMap<>(1);
        if(!identityNo.equals("") && null !=identityNo && !identityType.equals("") && null !=identityType) {
        	 List<String> fundCodes = userFundService.userFundCodeByIde(identityNo,identityType);
             if (CollectionUtils.isNotEmpty(fundCodes)){
                 map.put("fundCodes",fundCodes);
             }else {
             	map.put("fundCode","NOTICE");
             }
        }
        List<AlmanacInfo> list = almanacService.getAlmanac(map);
        return ResultMessage.ok(list);
    }

    /**
     * 查询公告日历信息
     */
    @PostMapping("/get_almanac_list")
    public ResultMessage<PageUtil<AlmanacInfo>> getAlmanacList(@RequestBody Map<String, Object> map) {
        PageUtil<AlmanacInfo> pageUtil = almanacService.searchAlmanac(map);
        return ResultMessage.ok(pageUtil);
    }


    /**
     * 新增公告日历信息
     */
    @PostMapping("/add_almanac")
    public ResultMessage<String> addAlmanac(@RequestBody AlmanacInfo info, HttpServletRequest request) {
        if (null == info) {
            return ResultMessage.fail("提交对象为空");
        }
        // 操作人
        String userName = (String) request.getSession().getAttribute("userName");
        info.setCreatedUser(userName);
        int j = 0;
        try {
        	Date date = new Date();
        	final long timestamp = date.getTime();
        	String[] fundCode = info.getFundCode().split(",");
        	//String[] fundName = info.getFundName().split(",");
        	for(int i=0 ;i <= fundCode.length ; i++) {
        		info.setFundCode(fundCode[i]);
        		//info.setFundName(fundName[i]);
        		info.setNoticeId(timestamp+"");
        		j = almanacService.addAlmanac(info);
        	}
		} catch (Exception e) {
			return ResultMessage.ok("创建失败");
		}
      
        if (j > 0) {
            return ResultMessage.ok("创建成功");
        } else {
            return ResultMessage.ok("创建失败");
        }
    }

    /**
     * 修改公告日历信息
     */
    @PostMapping("/edit_almanac")
    public ResultMessage<String> editAlmanac(@RequestBody AlmanacInfo info, HttpServletRequest request) {
        if (null == info) {
            return ResultMessage.fail("提交对象为空");
        }
        // 判断是否有相同的信息存在
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("noticeId", info.getNoticeId());
        //同一个公告的多个产品
        List<AlmanacInfo> list = almanacService.getAlmanacList(queryMap);
        // 操作人
        String userName = (String) request.getSession().getAttribute("userName");
        info.setUpdatedUser(userName);
        info.setCreatedUser(userName);
        
        int j = 0;
        try {
        	// 前端传入多个基金
        	String[] fundCode = info.getFundCode().split(",");
        	
        	//先修改非基金参数
        	AlmanacInfo notfund =info;
        	notfund.setFundCode("");
        	int notfundcode = almanacService.editAlmanac(notfund);
        	if(notfundcode > 0 ) {
        		//新增的基金
            	for(int i=0 ;i < fundCode.length ; i++) {
            		
            		for(int k=0 ;k < list.size() ; k++) {
            			AlmanacInfo ai = list.get(k);
            			if(fundCode[i].equals(ai.getFundCode())) {
            				break ;
            			}
            			if(k == list.size()-1) {
            				info.setFundCode(fundCode[i]);
            				j = almanacService.addAlmanac(info);
            				break ;
            			}
            		}
            	}
        		//删除的基金
            	for(int k=0 ;k < list.size() ; k++) {
            		AlmanacInfo ai = list.get(k);
            		for(int i=0 ;i < fundCode.length ; i++) {
            			if(ai.getFundCode().equals(fundCode[i])) {
            				break;
            			}
            			if(i== fundCode.length -1) {
            				info.setFundCode(ai.getFundCode());
            				j = almanacService.deleteAlmanac(info);
            				break;
            			}
            		}
            	}
            	
        	}else {
        		return ResultMessage.ok("修改失败");
        	}
		} catch (Exception e) {
			return ResultMessage.ok("创建失败");
		}
        
        if (j > 0) {
            return ResultMessage.ok("修改成功");
        } else {
            return ResultMessage.ok("修改失败");
        }
    }

    /**
     * 删除公告日历信息
     */
    @PostMapping("/delete_almanac")
    public ResultMessage<String> deleteAlmanac(@RequestBody AlmanacInfo info, HttpServletRequest request) {
        if (null == info || StringUtils.isBlank(info.getNoticeId())) {
            return ResultMessage.fail("提交对象为空");
        }
        // 操作人
        String userName = (String) request.getSession().getAttribute("userName");
        info.setUpdatedUser(userName);
        info.setFundCode("");
        int i = almanacService.deleteAlmanac(info);
        if (i > 0) {
            return ResultMessage.ok("删除成功");
        } else {
            return ResultMessage.ok("删除失败");
        }
    }

    /**
     * 获取信息日历 公告类别
     */
    @GetMapping("/get_notice")
    public ResultMessage<List<Map<String, String>>> getNotice() {
        return ResultMessage.ok(NoticeTypeEnum.getNoticeList());
    }
    
}
