package com.my.evc.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.User;
import com.my.evc.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    
    private static final String JSON_TYPE = "application/json";
    
    @Autowired
    private UserService userService;
    
    private final Logger LOGGER = Logger.getLogger(UserController.class);

    @ResponseBody
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public JsonResponse<String> createUser(@RequestBody(required=true) User user,
            HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {
        System.out.println("===============Add User===================");
        try {
            userService.createUser(user);
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }

        return new JsonResponse<String>(SUCCESS, "Created succeed!");
    }
    
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
