package main.booking.waza.workflow;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import database.config.WewazDbState;
import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.Producer;
import main.booking.waza.workflow.bean.RequestBooking;

public interface IProducer {
	
	public void ProduceRequest(WewazDbState db, String query, String status, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException;
	
	public String ProduceStatus(WewazDbState db, String query, String predicat, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException;
	
}
