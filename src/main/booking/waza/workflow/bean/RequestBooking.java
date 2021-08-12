package main.booking.waza.workflow.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import database.config.WewazDbState;
import main.booking.waza.workflow.bean.Consumer.ConsumerBuilder;

public class RequestBooking {
	
	private int rbId;
	
	private Object consumerId;
	
	private Object producerId;
	
	private String status;
	
	RequestBookingBuilder builder;
	
	public RequestBooking() {
	}
	
	public RequestBooking(RequestBookingBuilder builder) {
		this.rbId = builder.getRbId();
		this.consumerId = ((RequestBookingBuilder) builder).getConsumerId();
		this.producerId = ((RequestBookingBuilder) builder).getProducerId();
		this.status = builder.getStatus();
	}
	
	public int getRbId() {
		return rbId;
	}
	
	public void setRbId(int rbId) {
		this.rbId = rbId;
	}
	
	public Object getConsumerId() {
		return consumerId;
	}
	
	public void setConsumerId(Object consumerId) {
		this.consumerId = consumerId;
	}
	
	public Object getProducerId() {
		return producerId;
	}
	
	public void setProducerId(Object producerId) {
		this.producerId = producerId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public RequestBookingBuilder getBuilder(PreparedStatement ps) throws SecurityException, SQLException {
		RequestBookingBuilder builder = new RequestBookingBuilder();
		builder.setRbId(ps, this.getRbId());
		builder.setProducerId(ps, this.getProducerId());
		builder.setConsumerId(ps, this.getConsumerId());
		builder.setStatus(ps, this.getStatus());
		return builder;
	}
	
	public void setBuilder(RequestBookingBuilder builder) {
		this.builder = builder;
	}

	@Override
	public String toString() {
		return "RequestBooking [consumer=" + consumerId + ", producer=" + producerId + "]";
	}
	
	public static class RequestBookingBuilder implements WewazDbState {
		
		private int rbId;
		
		private Object consumerId;
		
		private Object producerId;
		
		private String status;
		
		public RequestBookingBuilder() {
		}
		
		public RequestBookingBuilder(int id, Object c, Object p, String st) {
			rbId = id;
			consumerId = c;
			producerId = p;
			status = st;
		}
		
		public Integer getRbId() {
			return rbId;
		}
		
		public void setRbId(PreparedStatement ps, Integer rbId) throws SQLException {
			this.rbId = rbId;
			this.sqlOnStateChange(ps, 1, rbId.toString());
		}
		
		public Object getConsumerId() {
			return consumerId;
		}
		
		public void setConsumerId(PreparedStatement ps, Object consumer) throws SecurityException, SQLException {
			this.consumerId = consumer;
			this.sqlOnStateChange(ps, 3, consumer.toString());
		}
		
		public Object getProducerId() {
			return producerId;
		}
		
		public void setProducerId(PreparedStatement ps, Object producer) throws SecurityException, SQLException {
			this.producerId = producer;
			this.sqlOnStateChange(ps, 2, producer.toString());
		}
		
		public String getStatus() {
			return status;
		}
		
		public void setStatus(PreparedStatement ps, String status) throws SQLException {
			this.status = status;
			this.sqlOnStateChange(ps, 4, status);
		}
		
		

		@Override
		public void sqlOnStateChange(PreparedStatement ps, int column, String propertyValue) throws SQLException {
			ps.setString(column, propertyValue);
			
		}

		@Override
		public void onCreate(PreparedStatement ps, WewazDbState db, Object o)
			throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException {
			if(db != null) {
				ps.executeUpdate();
				ps.close();
			} else {
				setRbId(ps, ((RequestBookingBuilder) db).getRbId());
				setConsumerId(ps, ((RequestBookingBuilder) db).getConsumerId());
				setProducerId(ps, ((RequestBookingBuilder) db).getProducerId());
				setStatus(ps, ((RequestBookingBuilder) db).getStatus());
				ps.executeUpdate();
				ps.close();
			}
			
			
		}

		@Override
		public void onReadOne(PreparedStatement ps, WewazDbState db, String param)
			throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException {
			
			setRbId(ps, Integer.valueOf(param));
			sqlResultSet(ps, null, db);
			ps.close();
			
		}

		@Override
		public void onDelete(PreparedStatement ps, WewazDbState db, String param)
				throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException {
			setRbId(ps, Integer.valueOf(param));
			ps.executeUpdate();
			ps.close();
			
		}

		@Override
		public void onUpdate(PreparedStatement ps, WewazDbState db, String param) throws SecurityException,
				IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
			if(db != null) {
				ps.setString(db.getEntity().getClass().getDeclaredFields().length+1, param);
				ps.executeUpdate();
				ps.close();
			} else {
				setRbId(ps, ((RequestBookingBuilder) db).getRbId());
				setConsumerId(ps, ((RequestBookingBuilder) db).getConsumerId());
				setProducerId(ps, ((RequestBookingBuilder) db).getProducerId());
				setStatus(ps, ((RequestBookingBuilder) db).getStatus());
				ps.executeUpdate();
				ps.close();
			}
			
		}

		@Override
		public Object getEntity() {
			return this;
		}

		@Override
		public void sqlResultSet(PreparedStatement ps, ResultSet rs, WewazDbState db)
				throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException,
				InstantiationException, NoSuchMethodException, SecurityException {
			rs = ps.executeQuery();
			if(rs.next()) {
				Object newObject = db.getEntity().getClass().newInstance();
				for (int i = 0; i < db.getEntity().getClass().getDeclaredFields().length; i++) {
					Field f = db.getEntity().getClass().getDeclaredFields()[i];
					String upper = String.valueOf(f.getName().charAt(0)).toUpperCase();
					String methodName = "set".concat(upper).concat(f.getName().substring(1));
					Method setNameMethod = db.getEntity().getClass().getMethod(methodName, Object.class);
					f.setAccessible(true);
					setNameMethod.invoke(db.getEntity(), rs.getString(f.getName()));
					
				}
			}
			
		}

		@Override
		public void sqlStatement(Statement stmt, ResultSet rs, WewazDbState db, List list)
				throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException,
				IllegalArgumentException, InvocationTargetException, SQLException {
			while(rs.next()) {
				Object newObject = db.getEntity().getClass().newInstance();
				for (int i = 0; i < db.getEntity().getClass().getDeclaredFields().length; i++) {
					Field f = db.getEntity().getClass().getDeclaredFields()[i];
					String upper = String.valueOf(f.getName().charAt(0)).toUpperCase();
					String methodName = "set".concat(upper).concat(f.getName().substring(1));
					Method setNameMethod = db.getEntity().getClass().getMethod(methodName, Object.class);
					f.setAccessible(true);
					setNameMethod.invoke(db.getEntity(), rs.getString(f.getName()));
				}
				list.add(newObject);
			}
			
		}

		@Override
		public void onRead(Statement stmt, ResultSet rs, WewazDbState db, List list)
				throws SQLException, InstantiationException, IllegalAccessException, SecurityException,
				NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
			sqlStatement(stmt, rs, db, list);
			
		}
		
	}

}
