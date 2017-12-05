package com.my.evc.model;

/**
 * 描述一个学生对象。
 */
public class Student extends BaseModel {
    
    private String name;            //姓名
    private String shortName;       //姓名缩写（拼音首字母）
    private String sex;             //性别
    private String grade;           //年级
    private int birthYear;          //出生年份（用于计算年龄）
    private int birthMonth;         //出生月份（用于计算年龄）
    private String description;     //备注
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public int getBirthYear() {
        return birthYear;
    }
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public int getBirthMonth() {
        return birthMonth;
    }
    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
