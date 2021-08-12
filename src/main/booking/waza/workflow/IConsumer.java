package main.booking.waza.workflow;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import database.config.WewazDbState;

public interface IConsumer {
	
	public void ConsumeRequest(WewazDbState db, String query, String status, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException;
	
	public String ConsumeStatus(WewazDbState db, String query, String predicat, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException;

}
