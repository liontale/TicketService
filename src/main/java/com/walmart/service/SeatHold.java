package com.walmart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatHold {
	static final int HOLD_DURATION = 1*60*1000; //1 minute in milliseconds
	Long holdPlacementTime;
	String holdEmailAddress;
	List<Seat> seats = new ArrayList<Seat>();
	boolean releaseHold;
	int  holdId = 0;
	static int holdIdCounter =0;
	
	public SeatHold(Long holdPlacementTime, String holdEmailAddress, List<Seat> seats) {
		super();
		this.holdPlacementTime = holdPlacementTime;
		this.holdEmailAddress = holdEmailAddress;
		this.seats.addAll(seats);
		this.holdId = ++SeatHold.holdIdCounter;
	}
	
	public boolean isHoldValid(Long currentTime) {
		if ((currentTime - holdPlacementTime) < HOLD_DURATION) {
			return true;
		}
		return false;
	}

	public Long getHoldPlacementTime() {
		return holdPlacementTime;
	}

	public void setHoldPlacementTime(Long timeSeatHeldAt) {
		this.holdPlacementTime = timeSeatHeldAt;
	}

	public String getHoldEmailAddress() {
		return holdEmailAddress;
	}

	public void setHoldEmailAddress(String holdEmailAddress) {
		this.holdEmailAddress = holdEmailAddress;
	}

	public boolean isReleaseHold() {
		return releaseHold;
	}

	public void setReleaseHold(boolean dropHold) {
		this.releaseHold = dropHold;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public  int getHoldId() {
		return holdId;
	}

	public  void setHoldId(int holdId) {
		this.holdId = holdId;
	}
	
}
