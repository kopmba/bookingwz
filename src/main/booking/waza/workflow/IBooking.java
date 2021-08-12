package main.booking.waza.workflow;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import main.booking.waza.workflow.bean.RequestBooking;

public interface IBooking {
	
	public Collection<RequestBooking> requestBookingList() throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException;
	
	public RequestBooking findRequestBooking(String predicat) throws NumberFormatException, SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;
	
	public List<RequestBooking> filter(String predicat) throws SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, SQLException;

}
