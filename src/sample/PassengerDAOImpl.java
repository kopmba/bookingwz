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
import main.booking.waza.workflow.handler.HandleConsumer;

public class PassengerDAOImpl implements PassengerDAO {

	@Override
	public void create(Consumer<Passenger> u) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "insert into passenger(passengerid, fullname) values(?,?)";
		DaoFactory<Consumer<Passenger>> daoFactory = new DaoFactory<>();
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
		String query = "delete from passenger where passengerid = ?";
		DaoFactory<Consumer<Passenger>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ConsumerBuilder<Passenger> ud = new Consumer.ConsumerBuilder<>(new Passenger());
		Consumer<Passenger> udr = new Consumer<Passenger>(ud);
		WewazDbState dbState = udr.getBuilder(null);
		daoFactory.requestDeleteFactory(query, predicat, dbState, c, ps, params);
		
	}

	@Override
	public void update(Consumer<Passenger> u, String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "update passenger set passengerid=?, fullname=? where passengerid=?";
		DaoFactory<Consumer<Passenger>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		WewazDbState dbState = u.getBuilder(ps);
		daoFactory.requestUpdateFactory(query, predicat, dbState, c, ps, params);
		
	}

	@Override
	public Consumer<Passenger> findOne(String predicat) throws SQLException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "select * from passenger where passengerid = ?";
		DaoFactory<Consumer<Passenger>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		ConsumerBuilder<Passenger> ud = new Consumer.ConsumerBuilder<>(new Passenger());
		Consumer<Passenger> udr = new Consumer<Passenger>(ud);
		WewazDbState dbState = udr.getBuilder(null);
		Object result = daoFactory.requestFindOneFactory(query, predicat, dbState, c, ps, params);
		udr.setBuilder((ConsumerBuilder<Passenger>) result);
		return udr;
	}

	@Override
	public Collection<Consumer<Passenger>> findAll() throws SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "select * from passenger";
		DaoFactory<Consumer<Passenger>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		ConsumerBuilder<Passenger> ud = new Consumer.ConsumerBuilder<>(new Passenger());
		Consumer<Passenger> udr = new Consumer<Passenger>(ud);
		WewazDbState dbState = udr.getBuilder(null);
		List<Consumer<Passenger>> list = new ArrayList();
		return daoFactory.requestFindFactory(query, list, dbState, c, params);
	}

	@Override
	public void createBooking(Consumer<Passenger> p, Object rbId, Object producerId) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
		String[] params = {"jdbc:mariadb://localhost:3306/samplebooking", "root", ""};
		String query = "insert into booking(id, driverId, passengerId, status) values(?,?,?,?)";
		DaoFactory<Consumer<Passenger>> daoFactory = new DaoFactory<>();
		WewazDbConnection wewazDbConnection = new WewazDbConnection();
		wewazDbConnection.createConnection(params[0], params[1], params[2]);
		Connection c = wewazDbConnection.getConnection();
		PreparedStatement ps = c.prepareStatement(query);
		
		Object[] sqlParams = {c, ps}; 
				
		HandleConsumer handler = new HandleConsumer();
		
		Driver d = new Driver(producerId.toString(), null);
		ProducerBuilder<Driver> ud = new Producer.ProducerBuilder<>(d);
		Producer<Driver> udr = new Producer<Driver>(ud);
		
		RequestBookingBuilder rbBuilder = new RequestBooking.RequestBookingBuilder((int) rbId, ((Passenger) p.getEntity()).getPassengerId(), udr.getEntity().getDriverId(), "PENDING");
		RequestBooking rb = new RequestBooking(rbBuilder);
		
		WewazDbState dbState = rb.getBuilder(ps);
		handler.ConsumeRequest(dbState, query, "PENDING", sqlParams);
		
	}

}
