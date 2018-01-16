package com.mnid.model;

public class MnidObject {
	
	private byte networkId;
	private String address;
	
	public MnidObject(){}
	
	public MnidObject(byte networkId, String address){
		this.networkId = networkId;
		this.address = address;
	}
	
	public byte getNetworkId() {
		return networkId;
	}
	public void setNetworkId(byte networkId) {
		this.networkId = networkId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString(){
		return "{networkId: '" + networkId + "', address: '" + address + "'}";
	}
	
}
