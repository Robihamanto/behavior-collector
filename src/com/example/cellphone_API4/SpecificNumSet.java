package com.example.cellphone_API4;

public class SpecificNumSet {
	private int totalSessionNum;
	private int totalSpecificNum;
	private int[][] specificNumMetrix;

	public SpecificNumSet(){
		
	}
	
	public SpecificNumSet(int totalSessionNum,int totalSpecificNum,int[][] specificNumMetrix){
		this.totalSessionNum = totalSessionNum;
		this.totalSpecificNum = totalSpecificNum;
		this.specificNumMetrix = specificNumMetrix;
	}
	
	public int getTotalSessionNum() {
		return totalSessionNum;
	}

	public void setTotalSessionNum(int totalSessionNum) {
		this.totalSessionNum = totalSessionNum;
	}

	public int getTotalSpecificNum() {
		return totalSpecificNum;
	}

	public void setTotalSpecificNum(int totalSpecificNum) {
		this.totalSpecificNum = totalSpecificNum;
	}

	public int[][] getSpecificNumMetrix() {
		return specificNumMetrix;
	}

	public void setSpecificNumMetrix(int[][] specificNumMetrix) {
		this.specificNumMetrix = specificNumMetrix;
	}

}
