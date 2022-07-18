package com.igwfmc.customer.service.impl;

import com.igwfmc.customer.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author liusj
 * @version 1.0
 * @description: TODO
 * @date 2022/7/14 17:07
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String demo() {
        return "demo success";
    }
}
