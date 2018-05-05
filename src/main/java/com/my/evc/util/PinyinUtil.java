package com.my.evc.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 处理拼音的类。导入学生姓名的时候，会自动抽取拼音首字母（因为查成绩的时候需要支持按拼音首字母过滤学生姓名）。
 *
 * @author 万小龙
 */
public class PinyinUtil {

	public static final String CHINESE_UNICODE_REGEX = "[\u4e00-\u9fa5]+";
	private static HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
	
	static {
		//初始化默认的配置信息
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
		defaultFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);// 不带声调
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE); //ü用unicode字符来表示
	}
	
	/**
	 * 将文字转为汉语拼音（全拼）。
	 * <p>注意：</p>
	 * <ol>
	 * <li>不能转换的字符将原样输出。</li>
	 * <li>如果有多音字，则只取第一个音节。</li>
	 * </ol>
	 */
	public static String toPinyin(String chinese) {
		char[] cn_chars = chinese.trim().toCharArray();
		String pinyin = "";
		try {
			for (int i = 0; i < cn_chars.length; i++) {
				// 如果字符是中文,则将中文转为汉语拼音
				if (String.valueOf(cn_chars[i]).matches(CHINESE_UNICODE_REGEX)) {
					//获取当前字符的所有拼音（一个字有可能由多个拼音）TODO 组建多音字词典，就能处理多音字了
					String[] pyArray = PinyinHelper.toHanyuPinyinStringArray(cn_chars[i], defaultFormat);
					pinyin += pyArray[0];
				} else {
					// 如果字符不是中文,则不转换
					pinyin += cn_chars[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinyin;
	}

	/**
	 * 取第一个汉字的第一个拼音字符（即声母的第一个，小写）。
	 */
	public static String getFirstLetterInLowerCase(String chinese) {
		char[] cn_chars = chinese.trim().toCharArray();
		String pinyin = "";
		try {
			for (int i = 0; i < cn_chars.length; i++) {
				// 如果字符是中文,则将中文转为汉语拼音
				if (String.valueOf(cn_chars[i]).matches(CHINESE_UNICODE_REGEX)) {
					//获取当前字符的所有拼音（一个字有可能由多个拼音）TODO 组建多音字词典，就能处理多音字了
					String[] pyArray = PinyinHelper.toHanyuPinyinStringArray(cn_chars[i], defaultFormat);
					pinyin += pyArray[0].substring(0, 1);
				} else {
					// 如果字符不是中文,则不转换
					pinyin += cn_chars[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinyin;
	}
	
	public static void main(String[] args) {
		String s = "中华人民共和国";
		System.out.println(toPinyin(s));
		System.out.println(getFirstLetterInLowerCase(s));
	}
}