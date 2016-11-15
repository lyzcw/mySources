/**
 * 
 */
package com.lyzcw.entity;

/**
 * @author lyzcw
 *
 */
import java.util.Date;

/**
 * 预约图书实体
 */
public class Appointment {
 
    private long bookId;// 图书ID
 
    private long studentId;// 学号
 
    private Date appointTime;// 预约时间
 
    // 多对一的复合属性
    private Book book;// 图书实体

	/**
	 * @return the bookId
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the studentId
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the appointTime
	 */
	public Date getAppointTime() {
		return appointTime;
	}

	/**
	 * @param appointTime the appointTime to set
	 */
	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
 
   

}
