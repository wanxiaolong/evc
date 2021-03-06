package com.my.evc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.my.evc.model.Student;

@MapperScan
public interface StudentMapper extends BaseMapper<Student> {
	/**
	 * 通过学号查找学生。
	 * @param idNumber 学号。
	 */
	public Student findByIdNumber(int idNumber);
	
	/**
	 * 通过姓名首字母查询学生。
	 * @param pinyin 姓名首字母，比如wxl
	 */
	public List<Student> findByPinYin(String pinyin);
	
	/**
	 * 查找所有学生。
	 */
	public List<Student> findAll();
	
	/**
	 * 通过姓名和生日来查找学生。
	 * @param name 姓名
	 * @param birthday 生日
	 */
	public Student findByNameAndBirthday(
			@Param("name")String name, 
			@Param("birthday")String birthday);
	
	/**
	 * 批量插入以提高数据库性能。<br>
	 * @param studentList 待插入的学生列表。
	 */
	public int createBatch(List<Student> studentList);
	
	/**
	 * 批量更新以提高数据库性能。<br>
	 * @param studentList 待更新的学生列表。
	 */
	public int updateBatch(List<Student> studentList);
}
