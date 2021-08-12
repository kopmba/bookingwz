package sample;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;

import main.booking.waza.workflow.bean.Consumer;

public interface PassengerDAO {
	
	public void create(Consumer<Passenger> u) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	public void delete(String predicat) throws SQLException, SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	public void update(Consumer<Passenger> u, String predicat) throws SQLException, SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	public Consumer<Passenger> findOne(String predicat) throws SQLException, SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;
	
	public Collection<Consumer<Passenger>> findAll() throws SQLException, SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException;
	
	public void createBooking(Consumer<Passenger> p, Object rbId, Object producerId) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException;

}
