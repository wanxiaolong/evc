package com.my.evc.controller.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    private final Logger LOGGER = Logger.getLogger(UserController.class);
    
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse<User> createUser(@RequestBody(required=true) User user,
            HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {
        try {
            userService.createUser(user);
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }
        return new JsonResponse<User>(SUCCESS, user);
    }
    
    @ResponseBody
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public JsonResponse<String> deleteUser(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws BaseException, Exception {
        try {
            userService.deleteUserByID(id);
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }
        return new JsonResponse<String>(SUCCESS, "Delete succeed!");
    }
    
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public JsonResponse<String> updateUser(@RequestBody(required=true) User user,
            HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {
        try {
            userService.updateUser(user);
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }
        return new JsonResponse<String>(SUCCESS, "Update succeed!");
    }
    
    @ResponseBody
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public JsonResponse<User> findUser(@PathVariable("id") int id, HttpServletRequest request,
            HttpServletResponse response) throws BaseException, Exception {
        User user = null;
        try {
            user = userService.findUserByID(id);
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }
        return new JsonResponse<User>(SUCCESS, user);
    }
    
}
