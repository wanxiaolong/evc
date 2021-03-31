package com.my.evc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 主要用于输出系统信息
 */
@Controller
@Slf4j
@RequestMapping("/system")
public class SystemController extends BaseController {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public String status() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateTime = sdf.format(new Date());
        log.info("测试日志。时间=" + dateTime);
        throw new Exception("");
    }
}
