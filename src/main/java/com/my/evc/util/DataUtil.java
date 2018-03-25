package com.my.evc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.my.evc.model.Subject;
import com.my.evc.vo.ExamVo;

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
}
