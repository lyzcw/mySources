/**
 * 
 */
package com.lyzcw.service.impl;

/**
 * @author lyzcw
 *
 */
import static org.junit.Assert.fail;
 
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.lyzcw.BaseTest;
import com.lyzcw.dto.AppointExecution;
import com.lyzcw.service.BookService;
 
public class BookServiceImplTest extends BaseTest {
 
    @Autowired
    private BookService bookService;
 
    @Test
    public void testAppoint() throws Exception {
        long bookId = 1001;
        long studentId = 12345678910L;
        AppointExecution execution = bookService.appoint(bookId, studentId);
        System.out.println(execution.getStateInfo());
    }
 
}