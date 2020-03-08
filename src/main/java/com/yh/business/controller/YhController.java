package com.yh.business.controller;


import com.yh.business.vo.ConditionInVo;
import com.yh.business.vo.ResultOutVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by idea China
 * Author: YH007
 * Time: 19:49 2020/1/22
 * Description:
 */
@RestController
@RequestMapping("/api")
public class YhController
{


    @PostMapping("/getDetail")
    public Map<String, Object> get(@RequestBody ConditionInVo conditionIn)
    {

        Map<String, Object> resultMap = new HashMap<>();

        if ("yehan".equals(conditionIn.getName()) && "007".equals(conditionIn.getPwd()))
        {
            ResultOutVo resultOutVo = new ResultOutVo();
            resultOutVo.setAccount("叶晗");
            resultOutVo.setPhone("17702703197");
            resultOutVo.setPart("中国区");
            resultOutVo.setAddress("wuhan");
            resultMap.put("resultCode", "200");
            resultMap.put("desc", "请求成功");
            resultMap.put("data", resultOutVo);
        }

        return resultMap;
    }


}
