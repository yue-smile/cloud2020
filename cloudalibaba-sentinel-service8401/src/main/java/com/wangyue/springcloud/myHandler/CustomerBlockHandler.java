package com.wangyue.springcloud.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wangyue.springcloud.entities.CommonResult;

public class CustomerBlockHandler {
    public static CommonResult handleException(BlockException e){
        return new CommonResult(455,"客户自定义的全局global流控handler");
    }

    public static CommonResult handleException2(BlockException e){
        return new CommonResult(455,"客户自定义的全局global流控handler");
    }
}
