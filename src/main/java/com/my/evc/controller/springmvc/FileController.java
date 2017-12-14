package com.my.evc.controller.springmvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.File;
import com.my.evc.service.FileService;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
    
    @Autowired
    private FileService fileService;
    
    private final Logger LOGGER = Logger.getLogger(FileController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse<String> createFile(@RequestBody(required=true) File file,
            HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {
        System.out.println("===============Add File===================");
        try {
            fileService.createFile(null);
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }
        return new JsonResponse<String>(SUCCESS, "Created succeed!");
    }
    
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ModelAndView listFiles(HttpServletRequest request, HttpServletResponse response)
            throws BaseException, Exception {
        System.out.println("===============List Files===================");
        List<File> files = null;
        try {
            files = fileService.getFiles();
        } catch (BaseException e) {
            LOGGER.error(e.getErrorCode() + e.getErrorMessage());
            throw new BaseException();
        } catch (Exception e) {
            LOGGER.error(e);
            throw new Exception();
        }
        ModelAndView mav = new ModelAndView("file");
        mav.addObject("model", files);
        return mav;
    }
}
