package com.lyzcw.dao;
 
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.lyzcw.BaseTest;
import com.lyzcw.entity.Appointment;
 
public class AppointmentDaoTest extends BaseTest {
 
    @Autowired
    private AppointmentDao appointmentDao;
 
    @Test
    public void testInsertAppointment() throws Exception {
        long bookId = 1001;
        long studentId = 12345678911L;
        int insert = appointmentDao.insertAppointment(bookId, studentId);
        System.out.println("insert=" + insert);
    }
 
    @Test
    public void testQueryByKeyWithBook() throws Exception {
        long bookId = 1000;
        long studentId = 12345678910L;
        Appointment appointment = appointmentDao.queryByKeyWithBook(bookId, studentId);
        System.out.println(appointment.getBook().getBookId());
        System.out.println(appointment.getBook().getName());
    }
 
}