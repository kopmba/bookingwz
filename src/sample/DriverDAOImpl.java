package sample;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import database.config.WewazDbConnection;
import database.config.WewazDbState;
import database.factory.DaoFactory;
import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.Consumer.ConsumerBuilder;
import main.booking.waza.workflow.bean.Producer;
import main.booking.waza.workflow.bean.Producer.ProducerBuilder;
import main.booking.waza.workflow.bean.RequestBooking;
import main.booking.waza.workflow.bean.RequestBooking.RequestBookingBuilder;
import main.booking.waza.workflow.handler.HandleProducer;

public class DriverDAOImpl implements DriverDAO {

	@Override
	public void create(Producer<Driver> u) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "insert into driver(driverid, fullname) values(?,?)";
		DaoFactory<Producer<Driver>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		WewazDbState dbState = u.getBuilder(ps);
		daoFactory.requestCreateFactory(query, dbState, c, ps, params);
	}

	@Override
	public void delete(String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "delete from driver where driverid = ?";
		DaoFactory<Producer<Driver>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ProducerBuilder<Driver> ud = new Producer.ProducerBuilder<>(new Driver());
		Producer<Driver> udr = new Producer<Driver>(ud);
		WewazDbState dbState = udr.getBuilder(null);
		daoFactory.requestDeleteFactory(query, predicat, dbState, c, ps, params);
	}

	@Override
	public void update(Producer<Driver> u, String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "update driver set driverid=?, fullname=? where driverid=?";
		DaoFactory<Producer<Driver>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		WewazDbState dbState = u.getBuilder(ps);
		daoFactory.requestUpdateFactory(query, predicat, dbState, c, ps, params);
	}

	@Override
	public Producer<Driver> findOne(String driverid) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "select * from driver where driverid = ?";
		DaoFactory<Producer<Driver>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ProducerBuilder<Driver> ud = new Producer.ProducerBuilder<>(new Driver());
		Producer<Driver> udr = new Producer<Driver>(ud);
		WewazDbState dbState = udr.getBuilder(null);
		Object result = daoFactory.requestFindOneFactory(query, driverid, dbState, c, ps, params);
		udr.setBuilder((ProducerBuilder<Driver>) result);
		return udr;
	}

	@Override
	public Collection<Producer<Driver>> findAll() throws SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "select * from driver";
		DaoFactory<Producer<Driver>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		ProducerBuilder<Driver> ud = new Producer.ProducerBuilder<>(new Driver());
		Producer<Driver> udr = new Producer<Driver>(ud);
		WewazDbState dbState = udr.getBuilder(null);
		List<Producer<Driver>> list = new ArrayList();
		return daoFactory.requestFindFactory(query, list, dbState, c, params);
	}

	@Override
	public void requestCreatedBooking(Producer<Driver> cd, Object rbId, Object consumerId, String status) throws SecurityException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "update booking set status=? where id=?) values(?,?)";
		DaoFactory<Producer<Driver>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		
		Object[] sqlParams = {c, ps}; 
				
		HandleProducer handler = new HandleProducer();
		
		Passenger p = new Passenger(consumerId.toString(), null);
		ConsumerBuilder<Passenger> up = new Consumer.ConsumerBuilder<>(p);
		Consumer<Passenger> upr = new Consumer<Passenger>(up);
		
		RequestBookingBuilder rbBuilder = new RequestBooking.RequestBookingBuilder((int) rbId, ((Passenger) upr.getEntity()).getPassengerId().toString(), cd.getEntity().getDriverId(), status);
		RequestBooking rb = new RequestBooking(rbBuilder);
		
		WewazDbState dbState = rb.getBuilder(ps);
		handler.ProduceRequest(dbState, query, status, sqlParams);
		
	}

}
