package com.my.evc.model;

/**
 * 描述考试成绩。
 */
public class Score extends BaseModel {
    
    private int studentId;    //学生的主键
    private int examId;       //考试的主键
    private double score;     //成绩
    
    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getExamId() {
        return examId;
    }
    public void setExamId(int examId) {
        this.examId = examId;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
}
