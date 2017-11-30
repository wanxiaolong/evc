package com.my.evc.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;

@Controller
@RequestMapping("/server")
public class ServerController extends BaseController {
    private static final String JSON_TYPE = "application/json";
    
    /**
     * Self service to check whether this service is up or down.
     */
    @ResponseBody
    @RequestMapping(value="/ping", method = RequestMethod.GET, produces=JSON_TYPE)
    public JsonResponse<String> ping(String name,
            HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {
        System.out.println("============" + name + "==================");
        return new JsonResponse<String>(SUCCESS, name);
    }
}
