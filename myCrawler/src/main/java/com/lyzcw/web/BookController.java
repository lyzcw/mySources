/**
 * 
 */
package com.lyzcw.web;

/**
 * @author lyzcw
 *
 */
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
 
import com.lyzcw.dto.AppointExecution;
import com.lyzcw.dto.Result;
import com.lyzcw.entity.Book;
import com.lyzcw.service.BookService;
 
@Controller
@RequestMapping("/book") // url:/模块/资源/{id}/细分 /seckill/list
public class BookController {
 
    private Logger logger = LoggerFactory.getLogger(this.getClass());
 
    @Autowired
    private BookService bookService;
 
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    private String list(Model model) {
        List<Book> list = bookService.getList();
        model.addAttribute("list", list);
        // list.jsp + model = ModelAndView
        return "list";// WEB-INF/jsp/"list".jsp
    }
 
    // ajax json
    @RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
    private String detail(@PathVariable("bookId") Long bookId, Model model) {
        if (bookId == null) {
            return "redirect:/book/list";
        }
        Book book = bookService.getById(bookId);
        if (book == null) {
            return "forward:/book/list";
        }
        model.addAttribute("book", book);
        return "detail";
    }
 
    @RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
            "application/json; charset=utf-8" })
    @ResponseBody
    private Result appoint(@PathVariable("bookId") Long bookId, @Param("studentId") Long studentId) {
        if (studentId == null || studentId.equals("")) {
            return new Result<>(false, "学号不能为空");
        }
        AppointExecution execution = bookService.appoint(bookId, studentId);
        return new Result(true, execution);
    }
    
    @RequestMapping(value = "/{bookId}/{studentId}/appoint", method = RequestMethod.GET, produces = {
	    "application/json; charset=utf-8" })
    @ResponseBody 
	private Result appointGet(@PathVariable("bookId") Long bookId, @PathVariable("studentId") Long studentId) {
		if (studentId == null || studentId.equals("")) {
		    return new Result<>(false, "学号不能为空");
		}
		AppointExecution execution = bookService.appoint(bookId, studentId);
		return new Result(true, execution);
	}
}
