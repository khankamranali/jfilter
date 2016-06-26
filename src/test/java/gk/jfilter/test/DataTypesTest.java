package gk.jfilter.test;

import static org.junit.Assert.assertEquals;
import gk.jfilter.JFilter;
import gk.jfilter.impl.filter.parser.Operator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

public class DataTypesTest {
	private static List<Dummy> dummies = new ArrayList<Dummy>();
	private static UUID uuid = UUID.randomUUID();
	@BeforeClass
	public static void  insertObjects() {
		Dummy d = new Dummy();
		d.setId(1);
		d.setEnumVar(Operator.$and);
		d.setIntVar(10);
		d.setStringVar("string");
		d.setJavaUtilDateVar(new Date(System.currentTimeMillis()));
		d.setJavaSqlDateVar(new java.sql.Date(System.currentTimeMillis()));
		d.setUuid(uuid);
		d.setCalendarVar(Calendar.getInstance());
		d.setJavaSqlTimestampVar(new java.sql.Timestamp(System.currentTimeMillis()));
		
		dummies.add(d);
	}

	@Test
	public void testString() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		assertEquals(filter.filter("{'stringVar':'?1'}", "string").getFirst().getStringVar(), "string");
	}
	
	@Test
	public void testEnum() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		assertEquals(filter.filter("{'enumVar':'?1'}", Operator.$and).getFirst().getEnumVar(), Operator.$and);
	}
	
	@Test
	public void testJavaUtilDate() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		assertEquals(filter.filter("{'javaUtilDateVar':{'$gt':'?1'}}", new Date(System.currentTimeMillis()-1000)).getFirst().getId(), 1);
	}
	
	@Test
	public void testJavaSqlDate() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		assertEquals(filter.filter("{'javaSqlDateVar':{'$gt':'?1'}}", new Date(System.currentTimeMillis()-1000)).getFirst().getId(), 1);
	}
	
	@Test
	public void testUuid() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		assertEquals(filter.filter("{'uuid':'?1'}", uuid).getFirst().getId(), 1);
	}
	
	@Test
	public void testSqlTimestamp() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		assertEquals(filter.filter("{'javaSqlTimestampVar':{'$gt':'?1'}}", new Timestamp(System.currentTimeMillis()-1000)).getFirst().getId(), 1);
	}
	
	@Test
	public void testCalendar() {
		JFilter<Dummy> filter = new JFilter<Dummy>(dummies, Dummy.class);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(System.currentTimeMillis()-1000));
		
		assertEquals(filter.filter("{'calendarVar':{'$gt':'?1'}}", calendar).getFirst().getId(), 1);
	}

	public static class Dummy {
		private int id;
		private String stringVar;
		private int intVar;
		private Operator enumVar;
		private java.util.Date javaUtilDateVar;
		private java.sql.Date javaSqlDateVar;
		private UUID uuid;
		private Calendar calendarVar;
		private java.sql.Timestamp javaSqlTimestampVar;
		
		public java.sql.Timestamp getJavaSqlTimestampVar() {
			return javaSqlTimestampVar;
		}

		public void setJavaSqlTimestampVar(java.sql.Timestamp javaSqlTimestampVar) {
			this.javaSqlTimestampVar = javaSqlTimestampVar;
		}

		public Calendar getCalendarVar() {
			return calendarVar;
		}

		public void setCalendarVar(Calendar calendarVar) {
			this.calendarVar = calendarVar;
		}

		public String getStringVar() {
			return stringVar;
		}

		public void setStringVar(String stringVar) {
			this.stringVar = stringVar;
		}

		public int getIntVar() {
			return intVar;
		}

		public void setIntVar(int intVar) {
			this.intVar = intVar;
		}

		public Operator getEnumVar() {
			return enumVar;
		}

		public void setEnumVar(Operator enumVar) {
			this.enumVar = enumVar;
		}
		
		public java.util.Date getJavaUtilDateVar() {
			return javaUtilDateVar;
		}

		public void setJavaUtilDateVar(java.util.Date javaUtilDateVar) {
			this.javaUtilDateVar = javaUtilDateVar;
		}

		public java.sql.Date getJavaSqlDateVar() {
			return javaSqlDateVar;
		}

		public void setJavaSqlDateVar(java.sql.Date javaSqlDateVar) {
			this.javaSqlDateVar = javaSqlDateVar;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		public UUID getUuid() {
			return uuid;
		}

		public void setUuid(UUID uuid) {
			this.uuid = uuid;
		}

		@Override
		public String toString() {
			return "Dummy {id=" + id + "}";
		}
		
	}
}
