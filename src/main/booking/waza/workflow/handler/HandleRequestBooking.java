package main.booking.waza.workflow.handler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import database.config.WewazDbState;
import database.factory.DaoFactory;
import main.booking.waza.workflow.IBooking;
import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.RequestBooking;
import main.booking.waza.workflow.bean.RequestBooking.RequestBookingBuilder;

public class HandleRequestBooking implements IBooking {

	@Override
	public Collection<RequestBooking> requestBookingList() throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		// List all the collection 
		DaoFactory<RequestBooking> daoFactory = new DaoFactory<>();
		String query = "select * from booking";
		List<RequestBooking> list = new ArrayList<>();
		RequestBookingBuilder rbBuilder = new RequestBooking.RequestBookingBuilder();
		RequestBooking rb = new RequestBooking(rbBuilder);
		
		WewazDbState dbState = rb.getBuilder(null);
		return daoFactory.requestFindFactory(query, list, dbState, null, null);
	}

	@Override
	public RequestBooking findRequestBooking(String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		DaoFactory<RequestBooking> daoFactory = new DaoFactory<>();
		String query = "select * from booking where id=?";
		List<RequestBooking> list = new ArrayList<>();
		RequestBookingBuilder rbBuilder = new RequestBooking.RequestBookingBuilder();
		rbBuilder.setRbId(null, Integer.valueOf(predicat));
		RequestBooking rb = new RequestBooking(rbBuilder);
		
		WewazDbState dbState = rb.getBuilder(null);
		Object result = daoFactory.requestFindOneFactory(query, predicat, dbState, null, null, null);
		
		rb.setBuilder((RequestBookingBuilder) result);
		return rb;
	}

	@Override
	public List<RequestBooking> filter(String predicat) throws SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, SQLException {
		List<RequestBooking> list = (List<RequestBooking>) requestBookingList();
		List<RequestBooking> result = null;
		for (RequestBooking requestBooking : list) {
			if(requestBooking.getConsumerId().toString().contains(predicat)) {
				result.add(requestBooking);
			}
		}
		return result;
	}

}
