package com.my.evc.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import com.my.evc.common.Constant;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
@SuppressWarnings("serial")
public class ValidateCode extends HttpServlet {
	//图片宽度
	private static final int width=80;
	//图片高度
	private static final int height=30;
	//字体大小
	private static final int fontsize=25;
	//干扰线数量
	private static final int lines=10;
	//验证码字符数
	private static final int nums=4;
	//随机数产生器
	private static Random random = new Random();
	private static byte[] generator(HttpSession session) throws Exception {
		//产生的字符串
		String randString="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		//创建一幅图像
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图像的图形对象
		Graphics g = image.getGraphics();
		//设置图形的背景色
		g.setColor(getRandColor(150, 100));
		g.fillRect(0,0, width, height);
		//画干扰线
		for(int i = 0;i<lines;i++){
			g.setColor(getRandColor(0, 250));
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			g.drawLine(x1, y1, x2, y2);
		}
		//设置验证码的字体
		g.setFont(new Font("宋体",Font.BOLD,fontsize));
		//验证码的位数
		StringBuffer code = new StringBuffer();
		//画验证码
		for(int i =0;i<nums;i++){
			g.setColor(getRandColor(0, 100));
			g.setFont(getRandFont(15, 15));
			int pos = random.nextInt(randString.length());
			char c = randString.charAt(pos);
			code.append(c);
			g.drawString(String.valueOf(c), i*20+3, random.nextInt(10)+20);
		}
		//画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width-1, height-1);
		//消亡图形对象
		g.dispose();
		//把BufferedImage对象中的图像信息编码后
		//向创建该对象(encoder)时指定的输出流输出
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
		encoder.encode(image);
		session.setAttribute(Constant.PARAM_VALIDATION_CODE, code.toString());
		return os.toByteArray();
	}
	/**
	 * 用于调用产生验证码的过程
	 */
	public static InputStream getCode(HttpSession session) throws Exception {
		return new ByteArrayInputStream(generator(session));
	}
	/**
	 * 产生一个随机的颜色
	 * @param bc 基础颜色
	 * @param rc 随机颜色(变化范围)
	 * @return Color
	 */
	private static Color getRandColor(int bc,int rc){
		int R = bc + random.nextInt(rc);
		int G = bc + random.nextInt(rc);
		int B = bc + random.nextInt(rc);
		return new Color(R,G,B);		
	}
	/**
	 * 产生一个随机的字体
	 * @param bf 基础大小
	 * @param rf 变化范围
	 * @return 一个新建的，随机的字体
	 */
	private static Font getRandFont(int bf,int rf){
		int fontsize=bf+random.nextInt(rf);
		String[] fontnames={"宋体","Arial","Verdana","Vrinda"};
		String fontname=fontnames[random.nextInt(fontnames.length)];
		return new Font(fontname,Font.BOLD,fontsize);
	}
}
