package com.idea4j.common.nosql.ssdb.api;


import com.google.common.base.Objects;
import com.idea4j.common.nosql.ssdb.ConcurrentHashSet;
import com.idea4j.common.nosql.ssdb.Response;
import com.idea4j.common.nosql.ssdb.SSDB;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
public class DataStoreService {
	private SSDBProxy[] ssdbs;
	private static ConcurrentHashSet<DataStoreService> serviceSet = new ConcurrentHashSet<DataStoreService>();
	static {
		serviceSet = new ConcurrentHashSet<DataStoreService>();
	}

	public static ConcurrentHashSet<DataStoreService> getServiceSet() {
		if (serviceSet == null) {
			serviceSet = new ConcurrentHashSet<DataStoreService>();
		}
		return serviceSet;
	}

	private static Thread checkthread = new SSDBCheckThread();

	public DataStoreService(String ip, int port) {
		this.init(ip, port);
	}

	public DataStoreService() {

	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public int getServerport() {
		return serverport;
	}

	public void setServerport(int serverport) {
		this.serverport = serverport;
	}

	public int POOLSIZE = 20;
	private String serverip;
	private int serverport;

	public void init() {
		this.init(serverip, serverport);
	}

	private void init(String ip, int port) {
		if (ssdbs == null) {
			ssdbs = new SSDBProxy[POOLSIZE];
			serverip = ip;
			serverport = port;
			try {
				for (int i = 0; i < POOLSIZE; i++) {
					ssdbs[i] = new SSDBProxy(getNewSSDB());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!checkthread.isAlive()) {
				checkthread.start();
			}
			serviceSet.add(this);
		}
	}

	private SSDB getNewSSDB() throws Exception {
		return new SSDB(serverip, serverport, 10000);
	}

	private static final Random r = new Random();

	public void save(String code, String archtype, String datetime,
			String values) throws Exception {
		String key = getKey(code, archtype, datetime);
		int index = r.nextInt(POOLSIZE) % POOLSIZE;
		SSDBProxy ssdb = ssdbs[index];
		try {
			ssdb.set(key, values);
		} catch (Exception ex) {
			ex.printStackTrace();
			ssdb.close();
			ssdbs[index] = new SSDBProxy(getNewSSDB());
			throw ex;
		}
	}

	public SSDBProxy getSSDBProxy() {
		int index = r.nextInt(POOLSIZE) % POOLSIZE;
		return ssdbs[index];
	}

	public void check() {
		for (int index = 0; index < POOLSIZE; index++) {
			SSDBProxy ssdb = ssdbs[index];
			try {
				long curr = System.currentTimeMillis();
				ssdb.set("test", "TS"+curr + "");
				long v = Long.parseLong(ssdb.get("test").replace("TS", ""));
				if (v < curr - 60 * 1000 * 5) {
					throw new RuntimeException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					ssdb.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					ssdbs[index] = new SSDBProxy(getNewSSDB());
					LoggerFactory.getLogger(DataStoreService.class)
							.info("reconnected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private final String getKey(String code, String archtype, String datetime) {
		return code + "." + archtype + "." + datetime;
	}

	public String get(String code, String archtype, String datetime)
			throws Exception {
		String key = getKey(code, archtype, datetime);
		int index = r.nextInt(POOLSIZE) % POOLSIZE;
		SSDBProxy ssdb = ssdbs[index];
		try {
			return ssdb.get(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			ssdb.close();
			ssdbs[index] = new SSDBProxy(getNewSSDB());
			throw ex;
		}
	}

	/**
	 * 根据时间范围获得数据.
	 *
	 * @param code
	 *            代码
	 * @param archtype
	 *            档位类型
	 * @param starttime
	 *            开始时间
	 * @param endtime
	 *            结束时间
	 * @param limit
	 *            条数 (+表示数间从小到大,-表示时间倒序)
	 * @return
	 * @throws Exception
	 */
	public List<String> getValues(String code, String archtype,
			String starttime, String endtime, int limit) throws Exception {
		String startKey = getKey(code, archtype, starttime);
		String endKey = getKey(code, archtype, endtime);
		Response res = null;
		int index = r.nextInt(POOLSIZE) % POOLSIZE;
		SSDBProxy ssdb = ssdbs[index];
		try {
			if (limit > 0) {
				res = ssdb.scan(startKey, endKey, limit);
			} else {
				res = ssdb.rscan(endKey, startKey, -limit);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ssdb.close();
			ssdbs[index] = new SSDBProxy(getNewSSDB());
			throw ex;
		}
		List<String> rtnList = new ArrayList<String>();
		Map<byte[], byte[]> items = res.items;
		for (byte[] key : res.keys) {
			byte[] value = items.get(key);
			rtnList.add(new String(value, "utf8"));
		}
		return rtnList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DataStoreService that = (DataStoreService) o;
		return POOLSIZE == that.POOLSIZE && serverport == that.serverport && Objects.equal(ssdbs, that.ssdbs) && Objects.equal(serverip, that.serverip);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(ssdbs, POOLSIZE, serverip, serverport);
	}

	public static void main(String[] args) {
		final DataStoreService dataStoreService = new DataStoreService(
				"192.168.50.35", 8888);
		try {
			String amount = dataStoreService.getSSDBProxy().get("wkDealCount");
			log.info("amount:{}",amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
