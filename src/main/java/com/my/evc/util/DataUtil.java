package com.my.evc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.evc.vo.ScoreVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import com.my.evc.model.Score;
import com.my.evc.model.Subject;
import com.my.evc.vo.ExamVo;

@Slf4j
public class DataUtil {
	
	/**
	 * 将考试科目ID转换成考试名称
	 * @param exams： 要填入的考试信息
	 * @param subjects 数据库中的所有科目
	 */
	public static void setSubjectNames(List<ExamVo> exams, List<Subject> subjects) {
		//将查到的科目信息放在Map中便于查询
		Map<String, String> idNameMap = new HashMap<String, String>();
		for (Subject subject : subjects) {
			//key=id, value=name
			idNameMap.put(String.valueOf(subject.getId()), subject.getName());
		}
		
		//遍历所有的考试信息
		for (ExamVo examVo : exams) {
			List<String> subjectNames = new ArrayList<String>();
			String subjectIDs = examVo.getSubjectIds();
			if (!StringUtils.isEmpty(subjectIDs)) {
				String[] ids = subjectIDs.trim().split(",");
				for (String id : ids) {
					subjectNames.add(idNameMap.get(id));
				}
			}
			examVo.setSubjectNames(subjectNames);
		}
	}
	
	/**
	 * 有的科目成绩是数值，可以排序，但有的是等级，比如A1， B2， A2，这些是不能排序的。
	 * 因此在排序之前有一个检查。
	 */
	private static boolean isScoreSortable(String value) {
		if (StringUtil.isEmpty(value)) {
			return false;
		}
		try {
			Double.parseDouble(value);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 为某科成绩设置排名
	 * @param list 待排名的成绩列表
	 * @param fieldName 待排名的字段
	 */
	public static void setRank(List<Score> list, String fieldName) {
		Score score = list.get(0);
		try {
			//用反射获取字段的值
			final Field field = score.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			String value = (String)field.get(score);
			
			//用第一个对象的字段值来判定该字段是否可以排序
			if (isScoreSortable(value)) {
				Collections.sort(list, new Comparator<Score>(){
					//创建一个匿名内部类来排序，排序的规则是按照字段的数值倒序，字段的值同样通过反射获取
					public int compare(Score o1, Score o2) {
						double s1 = 0, s2 = 0;
						try {
							s1 = Double.parseDouble((String)field.get(o1));
							s2 = Double.parseDouble((String)field.get(o2));
						} catch (Exception e) {
							e.printStackTrace();
						}
						return s1 > s2 ? (-1) : (s1 == s2 ? 0 : 1);
					}
				});
				
				//用反射获取score.setXxxRank方法，用来为排序后的对象设置默认排名
				Method setRank = score.getClass().getDeclaredMethod(getSetRankMethodName(fieldName), int.class);
				Method getRank = score.getClass().getDeclaredMethod(getGetRankMethodName(fieldName));
				for(int i = 0; i < list.size(); i++) {
					setRank.invoke(list.get(i), i + 1);
				}
				
				//用反射获取score.getXxx方法，用来比较当前元素的成绩是否和前一个相等
				Method getter = score.getClass().getDeclaredMethod(getGetterName(fieldName));
				for(int i = 1; i < list.size(); i++) {
					String currScore = (String)getter.invoke(list.get(i));
					String prevScore = (String)getter.invoke(list.get(i - 1));
					if (currScore.equals(prevScore)) {
						int newRank = (Integer)getRank.invoke(list.get(i-1));
						setRank.invoke(list.get(i), newRank);
					}
				}
			}
		} catch (Exception e) {
			log.error("getFieldError", e);
			return;
		}
	}
	
	/**
	 * 将所有的科目进行单科排序，并将各科的排名保存到指定的key中。
	 * @param list 从excel中读取到的带排序的数据。
	 * @return 包含单科成绩排名的列表
	 */
	public static List<Score> getAllSubjectRank(List<Score> list) {
		setRank(list, "chinese");
		setRank(list, "math");
		setRank(list, "english");
		setRank(list, "physics");
		setRank(list, "chemistry");
		setRank(list, "biologic");
		setRank(list, "politics");
		setRank(list, "history");
		setRank(list, "geography");
		setRank(list, "physical");
		setRank(list, "experiment");
		setRank(list, "score1");
		setRank(list, "score2");
		setRank(list, "total");
		resumeOrder(list);
		return list;
	}
	
	/**
	 * 把乱序的order列表按照Excel中出现的顺序排序。
	 */
	public static void resumeOrder(List<Score> list) {
		Collections.sort(list, new Comparator<Score>(){
			//创建一个匿名内部类来排序，排序的规则是按照order字段顺序
			public int compare(Score o1, Score o2) {
				int s1 = o1.getOrder();
				int s2 = o2.getOrder();
				return s1 > s2 ? 1 : (s1 == s2 ? 0 : -1);
			}
		});
	}

	/**
	 * 去掉以".0"结尾的分数，比如：89.0 => 89
	 */
	public static void removeScoreSuffix0(ScoreVo vo) {
		vo.setChinese(trimSuffix0(vo.getChinese()));
		vo.setMath(trimSuffix0(vo.getMath()));
		vo.setEnglish(trimSuffix0(vo.getEnglish()));
		vo.setPolitics(trimSuffix0(vo.getPolitics()));
		vo.setHistory(trimSuffix0(vo.getHistory()));
		vo.setGeography(trimSuffix0(vo.getGeography()));
		vo.setPhysics(trimSuffix0(vo.getPhysics()));
		vo.setChemistry(trimSuffix0(vo.getChemistry()));
		vo.setBiologic(trimSuffix0(vo.getBiologic()));
		vo.setExperiment(trimSuffix0(vo.getExperiment()));
		vo.setPhysical(trimSuffix0(vo.getPhysical()));
		vo.setScore1(trimSuffix0(vo.getScore1()));
		vo.setScore2(trimSuffix0(vo.getScore2()));
		vo.setTotal(trimSuffix0(vo.getTotal()));
	}

	private static String trimSuffix0(String score) {
		if (score!= null && score.endsWith(".0")) {
			score = score.substring(0, score.lastIndexOf(".0"));
		}
		return score;
	}
	
	/**
	 * 获取字段的设置排名的方法。
	 * 比如字段为chinese，则返回setChineseRank
	 */
	private static String getSetRankMethodName(String fieldName) {
		String firstChar = fieldName.substring(0, 1).toUpperCase();
		String setterName = "set" + firstChar + fieldName.substring(1) + "Rank";
		return setterName;
	}

	/**
	 * 获取字段的获取排名的方法。
	 * 比如字段为chinese，则返回getChineseRank
	 */
	private static String getGetRankMethodName(String fieldName) {
		String firstChar = fieldName.substring(0, 1).toUpperCase();
		String setterName = "get" + firstChar + fieldName.substring(1) + "Rank";
		return setterName;
	}

	/**
	 * 获取字段的getter方法。
	 * 比如字段为chinese，则返回getChinese
	 */
	private static String getGetterName(String fieldName) {
		String firstChar = fieldName.substring(0, 1).toUpperCase();
		String setterName = "get" + firstChar + fieldName.substring(1);
		return setterName;
	}
}
