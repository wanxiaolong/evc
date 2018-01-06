package com.my.evc.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
public class ValidateCodeUtil extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//图片宽度
	private static final int width=80;
	//图片高度
	private static final int height=35;
	//字体大小
	private static final int fontsize=25;
	//干扰线数量
	private static final int lines=10;
	//验证码字符数
	private static final int nums=4;
	//随机数产生器
	private static Random random = new Random();
	
	private static final String IMAGE_FORMAT = "jpg";
	
	/**
	 * 生成验证码字符串。
	 */
	public static char[] getCode() {
		//产生的字符串
		String randString="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		//验证码的位数
		char[] chars = new char[nums];
		for(int i =0;i<nums;i++){
			int pos = random.nextInt(randString.length());
			char c = randString.charAt(pos);
			chars[i] = c;
		}
		return chars;
	}
	
	/**
	 * 根据生成的字符串，画验证码图像。
	 */
	public static byte[] getImage(char[] chars) throws Exception {
		//创建一幅图像
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图像的图形对象
		Graphics2D g2d = image.createGraphics();
		//设置图形的背景色
		g2d.setColor(getRandColor(150, 100));
		g2d.fillRect(0,0, width, height);
		//画干扰线
		for(int i = 0;i<lines;i++){
			g2d.setColor(getRandColor(0, 250));
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			g2d.drawLine(x1, y1, x2, y2);
		}
		//设置验证码的字体
		g2d.setFont(new Font("宋体",Font.BOLD,fontsize));
		//画验证码
		for(int i = 0; i < nums; i++){
			g2d.setColor(getRandColor(0, 100));
			g2d.setFont(getRandFont());
			int randRotate = getRandRotate();
			//旋转
			g2d.rotate(Math.toRadians(randRotate), width / 2, height / 2);
			g2d.drawString(String.valueOf(chars[i]), i * 20 + 3, random.nextInt(10)+20);
			//旋转回来
			g2d.rotate(Math.toRadians(-randRotate), width / 2, height / 2);
		}
		//画边框
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, width-1, height-1);
		//消亡图形对象
		g2d.dispose();
		//将BufferedImage写到字节输出流中。
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImageIO.write(image, IMAGE_FORMAT, output);
		return output.toByteArray();
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
	 */
	private static Font getRandFont(){
		int fontsize = 15 + random.nextInt(15);
		String[] fontNames={"Arial","Verdana","Courier New","Monospaced","Calibri","Consolas","Comic Sans MS"};
		String fontname=fontNames[random.nextInt(fontNames.length)];
		return new Font(fontname,Font.BOLD,fontsize);
	}
	
	/**
	 * 画字符时，随机倾斜的度数。范围：[-20,+20]
	 */
	private static int getRandRotate() {
		return random.nextInt(40) - 20;
	}
}
