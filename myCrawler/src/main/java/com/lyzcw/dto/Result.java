/**
 * 
 */
package com.lyzcw.dto;

/**
 * @author lyzcw
 *
 */
/**
 * 封装json对象，所有返回结果都使用它
 */
public class Result<T> {
 
    private boolean success;// 是否成功标志
 
    private T data;// 成功时返回的数据
 
    private String error;// 错误信息
 
    public Result() {
    }
 
    // 成功时的构造器
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
 
    // 错误时的构造器
    public Result(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
 
    
}