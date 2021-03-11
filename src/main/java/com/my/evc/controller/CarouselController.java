package com.my.evc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.evc.common.Constant;
import com.my.evc.common.JsonResponse;
import com.my.evc.exception.BaseException;
import com.my.evc.model.Carousel;
import com.my.evc.service.CarouselService;
import com.my.evc.util.CommonUtil;

/**
 * 本类处理轮播相关的请求。
 */
@Controller
@RequestMapping("/carousel")
public class CarouselController extends BaseController {
	
	@Autowired
	private CarouselService carouselService;
	
	/**
	 * 查找所有轮播。
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse<List<Carousel>> findAll(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		List<Carousel> list = carouselService.findAll();
		return new JsonResponse<List<Carousel>>(SUCCESS, list);
	}

	/**
	 * 修改轮播信息。
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> updateCarousel(HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		String enabled = request.getParameter(Constant.PARAM_ENABLED);
		String imgUrl = request.getParameter(Constant.PARAM_IMG_URL);
		String linkUrl = request.getParameter(Constant.PARAM_LINK_URL);
		String order = request.getParameter(Constant.PARAM_ORDER);
		
		Carousel carousel = new Carousel();
		carousel.setId(Integer.parseInt(id));
		carousel.setEnabled(CommonUtil.strToBool(enabled));
		carousel.setImgUrl(imgUrl);
		carousel.setLinkUrl(linkUrl);
		carousel.setOrder(Integer.parseInt(order));
		carouselService.update(carousel);
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	/**
	 * 创建轮播。
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> createCarousel(HttpServletRequest request,
			HttpServletResponse response) throws BaseException, Exception {
		String enabled = request.getParameter(Constant.PARAM_ENABLED);
		String imgUrl = request.getParameter(Constant.PARAM_IMG_URL);
		String linkUrl = request.getParameter(Constant.PARAM_LINK_URL);
		String order = request.getParameter(Constant.PARAM_ORDER);
		
		Carousel carousel = new Carousel();
		carousel.setEnabled(CommonUtil.strToBool(enabled));
		carousel.setImgUrl(imgUrl);
		carousel.setLinkUrl(linkUrl);
		carousel.setOrder(Integer.parseInt(order));
		carouselService.create(carousel);
		return new JsonResponse<Object>(SUCCESS, null);
	}
	
	/**
	 * 删除轮播。
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse<Object> deleteById(HttpServletRequest request, 
			HttpServletResponse response) throws BaseException, Exception {
		String id = request.getParameter(Constant.PARAM_ID);
		carouselService.deleteByID(Integer.parseInt(id));
		return new JsonResponse<Object>(SUCCESS, null);
	}
}
