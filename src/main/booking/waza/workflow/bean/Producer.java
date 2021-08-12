package main.booking.waza.workflow.bean;

import java.sql.Statement;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.config.WewazDbState;

public class Producer<T> {
	
	T entity;
	ProducerBuilder<T> builder;

	public Producer(ProducerBuilder<T> pbuilder) {
		super();
		this.entity = (T) pbuilder.getEntity();
	}
	
	public T getEntity() {
		return entity;
	}
	
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public ProducerBuilder<T> getBuilder(PreparedStatement ps) throws SecurityException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ProducerBuilder<T> builder = new ProducerBuilder();
		builder.setEntity(ps, entity, null);
		return builder;
	}
	
	public void setBuilder(ProducerBuilder<T> builder) {
		this.builder = builder;
	}
	
	@Override
	public String toString() {
		return "Consumer [entity=" + entity + "]";
	}
	
	public static class ProducerBuilder<T> implements WewazDbState<T> {
		
		private Object entity;
		
		public ProducerBuilder() {
			// TODO Auto-generated constructor stub
		}

		public ProducerBuilder(T entity) {
			super();
			this.entity = entity;
		}
		
		@Override
		public Object getEntity() {
			return entity;
		}
		
		public void setEntity(PreparedStatement ps, Object clazz, Object property) throws SecurityException, SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
			if(clazz != null && property == null) {
				for (int i = 0; i < clazz.getClass().getDeclaredFields().length; i++) {
					
					Field f = clazz.getClass().getDeclaredFields()[i];
					
					f.setAccessible(true);
					String fValue = (String) f.get(clazz);
					
					if(ps != null) {
						this.sqlOnStateChange(ps, i+1, fValue);
					}
				}
			} 
			
			
			this.entity = clazz;
		}

		@Override
		public void sqlOnStateChange(PreparedStatement ps, int column, String propertyValue) throws SQLException {
			ps.setString(column, propertyValue);
		}

		@Override
		public void onCreate(PreparedStatement ps, WewazDbState db, Object o) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
			
			if(db != null) {
				ps.executeUpdate();
				ps.close();
			} else {
				setEntity(ps, db, null);
			}
			
		}

		@Override
		public void onReadOne(PreparedStatement ps, WewazDbState db, String param) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException {
			for (int i = 0; i < db.getEntity().getClass().getDeclaredFields().length; i++) {
				try {
					if(db.getEntity().getClass().getDeclaredMethods()[i].getName().toLowerCase().contains("Id".toLowerCase())
							&& db.getEntity().getClass().getDeclaredMethods()[i].getName().contains("get")) {
						
						this.sqlOnStateChange(ps, 1, param);
						System.out.println(db.getEntity());
						sqlResultSet(ps, null, db);
						
					}
				} catch (SecurityException | IllegalArgumentException | IllegalAccessException
						| InvocationTargetException | SQLException e) {
					e.printStackTrace();
				}
			}
			ps.close();
		}

		@Override
		public void onRead(Statement stmt, ResultSet rs, WewazDbState db, List<T> list) throws SQLException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
			sqlStatement(stmt, rs, db, list);
			
		}

		@Override
		public void onDelete(PreparedStatement ps, WewazDbState db, String param) throws SecurityException, IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
			if(db != null) {
				ps.setString(1, param);
				ps.executeUpdate();
				ps.close();
			}
		}

		@Override
		public void onUpdate(PreparedStatement ps, WewazDbState db, String param) throws SecurityException,
				IllegalArgumentException, IllegalAccessException, SQLException, InvocationTargetException {
			if(db != null) {
				ps.setString(db.getEntity().getClass().getDeclaredFields().length+1, param);
				ps.executeUpdate();
				ps.close();
			} else {
				setEntity(ps, db, null);
			}
			
		}

		@Override
		public void sqlResultSet(PreparedStatement ps, ResultSet rs, WewazDbState db) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException, SecurityException {
			rs = ps.executeQuery();
			if(rs.next()) {
				Object newObject = db.getEntity().getClass().newInstance();
				for (int i = 0; i < db.getEntity().getClass().getDeclaredFields().length; i++) {
					Field f = db.getEntity().getClass().getDeclaredFields()[i];
					String upper = String.valueOf(f.getName().charAt(0)).toUpperCase();
					String methodName = "set".concat(upper).concat(f.getName().substring(1));
					Method setNameMethod = db.getEntity().getClass().getMethod(methodName, String.class);
					f.setAccessible(true);
					setNameMethod.invoke(db.getEntity(), rs.getString(f.getName()));
					
				}
			}
		}

		@Override
		public void sqlStatement(Statement stmt, ResultSet rs, WewazDbState db, List<T> list) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, SQLException {
			while(rs.next()) {
				Object newObject = db.getEntity().getClass().newInstance();
				for (int i = 0; i < db.getEntity().getClass().getDeclaredFields().length; i++) {
					Field f = db.getEntity().getClass().getDeclaredFields()[i];
					String upper = String.valueOf(f.getName().charAt(0)).toUpperCase();
					String methodName = "set".concat(upper).concat(f.getName().substring(1));
					Method setNameMethod = db.getEntity().getClass().getMethod(methodName, String.class);
					f.setAccessible(true);
					setNameMethod.invoke(db.getEntity(), rs.getString(f.getName()));
					
				}
				list.add((T) db.getEntity());
			}
			
		}
		
		
	}
	
}
