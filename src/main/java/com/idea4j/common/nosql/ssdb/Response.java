package com.idea4j.common.nosql.ssdb;

import java.util.*;

/**
 *
 * @author markee
 * @date 2023-09-28 14:42 2023-09-28 14:45
 */
public class Response{
	public String status;
	public List<byte[]> raw;
	/**
	 * Indicates items' order
	 */
	public List<byte[]> keys = new ArrayList<byte[]>();
	/**
	 * key-value results
	 */
	public Map<byte[], byte[]> items = new LinkedHashMap<byte[], byte[]>();

	public Response(List<byte[]> raw){
		this.raw = raw;
		if(raw.size() > 0){
			status = new String(raw.get(0));
		}
	}

	public Object exception() throws Exception{
		if(raw.size() >= 2){
			throw new Exception(new String(raw.get(1)));
		}else{
			throw new Exception("");
		}
	}


	public boolean ok(){
		return "ok".equals(status);
	}

	public boolean not_found(){
		return "not_found".equals(status);
	}

	public void buildMap(){
		for(int i=1; i+1<raw.size(); i+=2){
			byte[] k = raw.get(i);
			byte[] v = raw.get(i+1);
			keys.add(k);
			items.put(k, v);
		}
	}

}
