package com.walmart.service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TicketServiceImp implements TicketService {
	
	final protected Integer MAX_SEATS = new Integer(6250);
	final protected Integer ORCHESTRA_MAX_SEATS = new Integer(1250);
	final protected Integer MAIN_MAX_SEATS = new Integer(2000);
	final protected Integer BALCONY1_MAX_SEATS = new Integer(1500);
	final protected Integer BALCONY2_MAX_SEATS = new Integer(1500);
	
	final protected Integer VL_O = 1;
	final protected Integer VL_M = 2;
	final protected Integer VL_B1 = 3;
	final protected Integer VL_B2 = 4;
	
	final protected String NOT_AVAILABLE = "Number of seats at the requested level not available";
	final protected String RESERVATION_EXP = "The hold is no longer valid, this transaction cannot be completed, either hold duration has expired or this reservation has already been processed, plese call ";
	final protected String RESERVATION_INVALID = "Email you provided does not match our records, this reservation cannot be processed, plese call ";
	final protected String PHONE_NUMBER = "1800-RESERVE";
	
	private Set<Seat> seats = new TreeSet<Seat>();
	List<SeatHold> holdList = new ArrayList<SeatHold>();
	private Random generator = new Random(System.currentTimeMillis());
/*
 * (non-Javadoc)
 * @see org.walmart.service.ticketservice.TicketService#numSeatsAvailable(java.lang.Integer)
 */
	public int numSeatsAvailable(Integer venueLevel) {
		int numOfAvailableSeats = 0;
		
//		switch (venueLevel){
//		case 1: removeExpiredHold(orchestra);
//				numOfAvailableSeats = ORCHESTRA_MAX_SEATS - orchestra.size();
//				break;
//		case 2: removeExpiredHold(main);
//				numOfAvailableSeats = MAIN_MAX_SEATS - main.size();
//				break;
//		case 3: removeExpiredHold(balcony1);
//				numOfAvailableSeats = BALCONY1_MAX_SEATS - balcony1.size();
//				break;
//		case 4: removeExpiredHold(balcony2);
//				numOfAvailableSeats = BALCONY2_MAX_SEATS - balcony2.size();
//				break;
//		default:
//			numOfAvailableSeats = -1;
//				break;
//		}
		
		List<Seat> availableSeat = seats.stream()
				    .filter(s -> (s.isAvailable() == true) && (s.getSeatLevel() == venueLevel)).collect(Collectors.toList());
			return (availableSeat.size());
	}

	//Assumption min_level = 1 best available, max_level =4 lest desirable 
	public SeatHold findAndHoldSeats(int numSeats, Integer minLevel, Integer maxLevel, String customerEmail) throws Exception {
		
		SeatHold holdDetails = null;
		List<Seat> toBeHeld = null;
		// Find all available seats on minLevel
		List<Seat> availableSeats = seats.stream().filter(
				s -> (s.isAvailable() == true) && (s.getSeatLevel() >= minLevel) && (s.getSeatLevel() <= maxLevel))
				.collect(Collectors.toList());
		if (availableSeats.size() >= numSeats) {
			// mark seats as unavailable
			toBeHeld = availableSeats.subList(0, numSeats);
			for (Seat seat : toBeHeld) {
				seat.setAvailable(false);
			}
			// create SeatHold object, insert availableSeats into SeatHold
			// object
			// check if value is changed to unavailable in the Tree
			holdDetails = new SeatHold(System.currentTimeMillis(), customerEmail, toBeHeld);
			holdList.add(holdDetails);
		} else {
			throw new Exception(NOT_AVAILABLE);
		}
		return holdDetails;
	}

	



	public String reserveSeats(int seatHoldId, String customerEmail) throws Exception {
		//Make sure to purge all expired holds before making reservation
		removeExpiredHold(System.currentTimeMillis());
		
		//Find the SeatHold by holdId
		List<SeatHold> holdBeingProcessed = holdList.stream().filter(
				s -> (s.getHoldId() == seatHoldId))
				.collect(Collectors.toList());
		if(holdBeingProcessed.isEmpty()){
			throw new Exception (RESERVATION_EXP + PHONE_NUMBER);
		}
		if (!holdBeingProcessed.get(0).getHoldEmailAddress().equals(customerEmail)){
			throw new Exception (RESERVATION_INVALID + PHONE_NUMBER);
		}
		String confirmCode = customerEmail.substring(0, 2).concat(String.valueOf((randomGenerator())));
		//hold has been processed, remove it, based on current requirements there is no need to
		//maintain a processed hold. Removing it ensure the same reservation is not being processed twice
		//if a future requirement requires maintaining  processed reservation, add a member variable
		//to SeatHold class namely processed, this could be set to ture when the reservation has been processed
		holdList.remove(holdBeingProcessed);
		return confirmCode;
	}

	void removeExpiredHold(Long currTime) {
		for(SeatHold seatHoldInfo : holdList){
			if(!seatHoldInfo.isHoldValid(currTime)){
				holdList.remove(seatHoldInfo);
				//mark seats as available
				for(Seat heldSeat : seatHoldInfo.getSeats()){
					heldSeat.setAvailable(true);
				}
			}
			//Since ArrayList maintains insertion order
			//if hold is valid for for i, then it must be valid for i+1
			//so exit loop
			break;
		}
	}
	
//	Map<Integer, SeatHold> getLevel(Integer level) {
//		if(level.equals(1)){
//			return orchestra;
//		}else if (level.equals(2)){
//			return main;
//		}else if (level.equals(3)){
//			return balcony1;
//		}else {
//			return balcony2;
//		}
//	}
	protected int populateSeats(int venueLevel) {
		int maxNumberOfSeats=1;
		
		if(venueLevel == VL_O){
			maxNumberOfSeats = ORCHESTRA_MAX_SEATS;
		}else if(venueLevel == VL_M){
			maxNumberOfSeats = MAIN_MAX_SEATS + ORCHESTRA_MAX_SEATS;
		}else if (venueLevel == VL_B1){
			maxNumberOfSeats = BALCONY1_MAX_SEATS + MAIN_MAX_SEATS + ORCHESTRA_MAX_SEATS;
		}else if (venueLevel == VL_B2){
			maxNumberOfSeats = BALCONY2_MAX_SEATS +BALCONY1_MAX_SEATS + MAIN_MAX_SEATS + ORCHESTRA_MAX_SEATS;
		}
		for(int i= seats.size()+1; i <= maxNumberOfSeats; i++){
			seats.add(new Seat(i, true, venueLevel));
		}
		return seats.size();
	}
	
	int randomGenerator() {
        return generator.nextInt(50000);
	}
}
