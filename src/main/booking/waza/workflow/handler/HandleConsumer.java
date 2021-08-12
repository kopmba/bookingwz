package main.booking.waza.workflow.handler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.config.WewazDbState;
import database.factory.DaoFactory;
import main.booking.waza.workflow.IConsumer;
import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.RequestBooking;
import main.booking.waza.workflow.bean.RequestBooking.RequestBookingBuilder;

public class HandleConsumer implements IConsumer {
	
	Connection c = null;
	PreparedStatement ps = null;

	@Override
	public void ConsumeRequest(WewazDbState db, String query, String status, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
		// Write Query jdbc to create a requestBooking as
		// a request to some producer
		DaoFactory<Consumer> daoFactory = new DaoFactory<>();
		
		if(sqlParams[0] instanceof Connection) {
			c = (Connection) sqlParams[0];
		}
		
		if(sqlParams[1] instanceof PreparedStatement) {
			ps = (PreparedStatement) sqlParams[1];
		}
		
		daoFactory.requestWorkflow(query, db, status, c, ps);
	}

	@Override
	public String ConsumeStatus(WewazDbState db, String query, String predicat, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		DaoFactory<RequestBooking> daoFactory = new DaoFactory<>();
		if(sqlParams[0] instanceof Connection) {
			c = (Connection) sqlParams[0];
		}
		
		if(sqlParams[1] instanceof PreparedStatement) {
			ps = (PreparedStatement) sqlParams[1];
		}
		Object result = daoFactory.requestFindOneFactory(query, predicat, db, c, ps);
		RequestBooking r = new RequestBooking((RequestBookingBuilder) result);
		return r.getStatus();
	}

}
