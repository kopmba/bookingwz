package sample;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import main.booking.waza.workflow.IBooking;
import main.booking.waza.workflow.bean.Consumer;
import main.booking.waza.workflow.bean.Consumer.ConsumerBuilder;
import main.booking.waza.workflow.bean.Producer;
import main.booking.waza.workflow.bean.Producer.ProducerBuilder;
import main.booking.waza.workflow.bean.RequestBooking;
import main.booking.waza.workflow.handler.HandleRequestBooking;

public class Main {

	public static void main(String[] args) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		// TODO Auto-generated method stub
		PassengerDAO pDao = new PassengerDAOImpl();
		DriverDAO dDao = new DriverDAOImpl();
				
		// Create booking
		
		Driver d = new Driver("Mard", "sa");
		Driver ud = new Driver("Mardets", "ets");
		Driver ud_ = new Driver("Dale", "sarl");
		
		ProducerBuilder<Driver> dbuilder = new Producer.ProducerBuilder<>(d);
		
		Producer<Driver> dr = new Producer<Driver>(dbuilder);
		
		ProducerBuilder<Driver> udbuilder = new Producer.ProducerBuilder<>(ud);
		
		Producer<Driver> udr = new Producer<Driver>(udbuilder);
		
		ProducerBuilder<Driver> udbuilder_ = new Producer.ProducerBuilder<>(ud_);
		
		Producer<Driver> udr_ = new Producer<Driver>(udbuilder_);
		
		Passenger p = new Passenger("jdoe", "john doe");
		Passenger p1_ = new Passenger("lking", "larry king");
		Passenger p_ = new Passenger("jdoe", "john doe beck");
		
		
		ConsumerBuilder<Passenger> pbuilder = new Consumer.ConsumerBuilder<>(p);
		ConsumerBuilder<Passenger> pbuilder_ = new Consumer.ConsumerBuilder<>(p_);
		ConsumerBuilder<Passenger> pbuilder1_ = new Consumer.ConsumerBuilder<>(p1_);
		
		Consumer<Passenger> pr = new Consumer<Passenger>(pbuilder);
		Consumer<Passenger> pr_ = new Consumer<Passenger>(pbuilder_);
		Consumer<Passenger> pr1_ = new Consumer<Passenger>(pbuilder1_);
		
		//dDao.create(dr);
		//dDao.update(udr, "Mard");
		
		Producer<Driver> found = dDao.findOne("Mardets");
		
		//dDao.create(udr_);
		//dDao.create(dr);
		
		List<Producer<Driver>> list = (List<Producer<Driver>>) dDao.findAll();
		
		dDao.delete("Dale");
		
		// Passenger Dao
		
		//pDao.create(pr);
		pDao.update(pr_, "larryking");
		
		Consumer<Passenger> found_ = pDao.findOne("jdoe");
		pDao.create(pr_);
		pDao.create(pr1_);
		
		List<Consumer<Passenger>> list_ = (List<Consumer<Passenger>>) pDao.findAll();
			
		// Request a booking
		IBooking handler = new HandleRequestBooking();
		int n = handler.requestBookingList().size();
		
		pDao.createBooking(pr, n+1, d.getDriverId());
		dDao.requestCreatedBooking(dr, n+1, p.getPassengerId(), "ACCEPTED");
	}

}
