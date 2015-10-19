package com.walmart.service;

public class Seat implements Comparable{
	Integer seatNumber;
	boolean available=true;
	Integer seatLevel;
	
	
	public Seat(Integer seatNumber, boolean available, Integer seatLevel) {
		super();
		this.seatNumber = seatNumber;
		this.available = available;
		this.seatLevel = seatLevel;
	}
	public Integer getSeatNumber() {
		return seatNumber;
	}
	public boolean isAvailable() {
		return available;
	}
	public Integer getSeatLevel() {
		return seatLevel;
	}
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public void setSeatLevel(Integer seatLevel) {
		this.seatLevel = seatLevel;
	}
		
	@Override
	public int compareTo(Object o) {
		if(this.seatNumber > ((Seat)o).getSeatNumber()){
			return 1;
		}else if(this.seatNumber < ((Seat)o).getSeatNumber()){
			return -1;
		}else return 0;
	}

}
