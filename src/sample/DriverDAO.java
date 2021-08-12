package sample;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;

import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.Producer;

public interface DriverDAO {
	
	public void create(Producer<Driver> u) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException;
	
	public void delete(String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	public void update(Producer<Driver> u, String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	public Producer<Driver> findOne(String username) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;
	
	public Collection<Producer<Driver>> findAll() throws SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException;
	
	public void requestCreatedBooking(Producer<Driver> c, Object rbId, Object consumerId, String status) throws SecurityException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

}
