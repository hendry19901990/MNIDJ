package com.mnid;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import com.mnid.model.MnidObject;
import com.mnid.util.Base58;

import java.math.BigInteger;
import org.apache.commons.lang3.ArrayUtils;

public class Mnid {
			
	/*
	 * Receive id of Network and Ethereum Address
	 * return mnid
	 */
	public static String encode(byte[] id, String address){
		
		byte[] version   = {1};
		byte[] networkId = id;
		String addressFormat = (address.contains("0x")) ? address.substring(2) : address;
		byte[] addressArray    = hexStringToByteArray(addressFormat);
		
		byte[] concat2Array    = (byte[])ArrayUtils.addAll(version, networkId);
		byte[] concatAllArray  = (byte[])ArrayUtils.addAll(concat2Array, addressArray);
		
		byte[] digestChecksum = checksum(version, networkId, addressArray);
		byte[] result = (byte[])ArrayUtils.addAll(concatAllArray, digestChecksum);
		
		return Base58.encode(result);
	}
	
	/*
	 * Convert mnid to MnidObject
	 */
	public static MnidObject decode(String mnid) throws Exception{
		 
		byte[] data    = Base58.decode(mnid);
		int netLength  = data.length - 24;
		byte[] version = ArrayUtils.subarray(data, 0, 1);
		byte[] network = ArrayUtils.subarray(data, 1, netLength);
		byte[] addressArray = ArrayUtils.subarray(data, netLength, 20 + netLength);
		byte[] check = ArrayUtils.subarray(data, netLength + 20, data.length);
		
		StringBuffer result = new StringBuffer();
		for(byte n : network)
			result.append(String.format("%02X", n));
		
		long networkId = new BigInteger(result.toString(), 16).longValue();
		
		if (ArrayUtils.isEquals(check, checksum(version, network, addressArray))){
			return new MnidObject(networkId, "0x" + Hex.toHexString(addressArray));
		}else{
			throw new Exception("Invalid address checksum");
		}
	}
	
	/*
	 * Receive hexString and convert To ByteArray
	 */
	private static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/*
	 * validate if is a valid mnid
	 */
	public static boolean isMNID(String mnid){
		try {
			byte[] data = Base58.decode(mnid);
			return data.length > 24 && data[0] == 1;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	private static byte[] checksum(byte[] version, byte[] networkId, byte[] address){
		byte[] concat2Array    = (byte[])ArrayUtils.addAll(version, networkId);
		byte[] concatAllArray  = (byte[])ArrayUtils.addAll(concat2Array, address);
		
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
		byte[] digest = digestSHA3.digest(concatAllArray);
		
		return ArrayUtils.subarray(digest, 0, 4);
	}

}
