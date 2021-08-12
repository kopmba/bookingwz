package main.booking.waza.workflow.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.config.WewazDbState;
import database.factory.DaoFactory;
import main.booking.waza.workflow.IProducer;
import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.Producer;
import main.booking.waza.workflow.bean.RequestBooking;
import main.booking.waza.workflow.bean.RequestBooking.RequestBookingBuilder;

public class HandleProducer implements IProducer {
	
	Connection c = null;
	PreparedStatement ps = null;

	@Override
	public void ProduceRequest(WewazDbState db, String query, String status, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
		Connection c = null;
		PreparedStatement ps = null;
		DaoFactory<Producer> daoFactory = new DaoFactory<>();
		
		if(sqlParams[0] instanceof Connection) {
			c = (Connection) sqlParams[0];
		}
		
		if(sqlParams[1] instanceof PreparedStatement) {
			ps = (PreparedStatement) sqlParams[1];
		}
		daoFactory.requestWorkflow(query, db, status, c, ps);
	}

	@Override
	public String ProduceStatus(WewazDbState db, String query, String predicat, Object ...sqlParams) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException {
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
