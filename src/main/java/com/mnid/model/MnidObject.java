package com.mnid.model;

public class MnidObject {
	
	private long networkId;
	private String address;
	
	public MnidObject(){}
	
	public MnidObject(long networkId, String address){
		this.networkId = networkId;
		this.address = address;
	}
	
	public long getNetworkId() {
		return networkId;
	}
	public String getNetworkIdHex() {
		return Long.toHexString(networkId);
	}
	public void setNetworkId(long networkId) {
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
