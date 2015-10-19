package com.walmart.service;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TicketServiceImpTest extends TestCase {

	public void testRemoveExpiredHold(){
		TicketServiceImp ts = new TicketServiceImp();
		ts.populateSeats(1);
		try {
			ts.findAndHoldSeats(10, 1, 1, "foo@bar.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(ts.numSeatsAvailable(1), ts.ORCHESTRA_MAX_SEATS - 10);
		ts.removeExpiredHold(System.currentTimeMillis() + SeatHold.HOLD_DURATION);
		assertEquals(ts.numSeatsAvailable(1), (int)ts.ORCHESTRA_MAX_SEATS);
		
	}
	
	public void testNumSeatsAvailable(){
		TicketServiceImp ts = new TicketServiceImp();
		ts.populateSeats(1);
		ts.populateSeats(2);
		ts.populateSeats(3);
		ts.populateSeats(4);
		//Test when all seats are available 
		assertEquals(1250,ts.numSeatsAvailable(1));
		assertEquals(2000,ts.numSeatsAvailable(2));
		assertEquals(1500,ts.numSeatsAvailable(3));
		assertEquals(1500,ts.numSeatsAvailable(4));
		
	}
	
	public void testFindAndHoldSeats(){
		TicketServiceImp ts = new TicketServiceImp();
		ts.populateSeats(1);
		ArrayList heldSeats = new ArrayList<Seat>();
		heldSeats.add(new Seat(1, false, 1));
		heldSeats.add(new Seat(2, false, 1));
		heldSeats.add(new Seat(3, false, 1));
		heldSeats.add(new Seat(4, false, 1));
		
		assertEquals(1250, ts.numSeatsAvailable(1));
		SeatHold holdDetails = null;
		try {
			holdDetails = ts.findAndHoldSeats(4, 1, 1, "Foo@walmart.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals("SHOULD NOT GET HERE", e.getMessage());
		}
		assertEquals("Foo@walmart.com", holdDetails.getHoldEmailAddress());
		assertEquals(4, holdDetails.getSeats().size());
		assertEquals(1250-4, ts.numSeatsAvailable(1));
		
		//try to book more seats than available, should get Exception
		
			try {
				 ts.findAndHoldSeats(1400, 1, 1, "Foo@walmart.com");
			} catch (Exception e) {
				assertEquals(ts.NOT_AVAILABLE, e.getMessage());
			}
			//populate level 2, asks for seats > capacity for level 1
			//make sure seats held are from both level 1 and 2
			try {
				 ts.populateSeats(2);
				 ts.findAndHoldSeats(1400, 1, 2, "Foo@walmart.com");
			} catch (Exception e) {
				assertEquals("SHOULD NOT GET HERE", e.getMessage());
			}
	}

	public void testReserveSeats() {
		TicketServiceImp ts = new TicketServiceImp();
		ts.populateSeats(1);
		try {
			ts.findAndHoldSeats(10, 1, 1, "foo@bar.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			String cc = ts.reserveSeats(1, "BADEMAIL@bar.com");
//			assertTrue(cc.contains("fo"));//should not get here
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			assertEquals(ts.RESERVATION_INVALID +ts.PHONE_NUMBER, e.getMessage());
//		}
		
		try {
			String cc = ts.reserveSeats(1, "foo@bar.com");
			assertTrue(cc.contains("fo"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String cc = ts.reserveSeats(1, "foo@bar.com");
			assertTrue(cc.contains("fo")); //should not get here
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertEquals(ts.RESERVATION_EXP + ts.PHONE_NUMBER, e.getMessage());
		}
		
	}

	public void testPopulateSeats() {
		TicketServiceImp ts = new TicketServiceImp();
		
		assertEquals(1250, ts.populateSeats(1));
		assertEquals(2000+1250, ts.populateSeats(2));
		assertEquals(1500+2000+1250, ts.populateSeats(3));
		assertEquals(1500+1500+2000+1250, ts.populateSeats(4));
	}
	
	
}
