package com.idea4j.common.nosql.ssdb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import com.idea4j.common.nosql.ssdb.SSDB;

public class SSDBProxyService {
	private List<SSDBProxy> masters = new ArrayList<SSDBProxy>();
	private List<SSDBProxy> slaves = new ArrayList<SSDBProxy>();
	public int poolsize = 5;

	public int getPoolsize() {
		return poolsize;
	}

	public void setPoolsize(int poolsize) {
		this.poolsize = poolsize;
	}

	private SSDBProxy getProxy(String ip, int port) {
		SSDB ssdb = null;
		try {
			ssdb = new SSDB(ip, port);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		SSDBProxy proxy = new SSDBProxy(ssdb);
		proxy.setIp(ip);
		proxy.setPort(port);
		return proxy;
	}

	public SSDBProxyService(String addressList) {
		this(addressList, true);
	}

	public void close() {
		stop.set(true);
		for (SSDBProxy proxy : this.masters) {
			proxy.close();
		}
		for (SSDBProxy proxy : slaves) {
			proxy.close();
		}
	}

	private void check() {
		checkevery(this.masters);
		checkevery(this.slaves);
	}

	private void checkevery( List<SSDBProxy> proxyList) {
		for (int i = 0; i < proxyList.size(); i++) {
			SSDBProxy ssdb = proxyList.get(i);
			try {
				ssdb.get("f");
			} catch (Exception e) {
				e.printStackTrace();
				try {
					ssdb.setConn(false);
					try {
						ssdb = this.getProxy(ssdb.getIp(), ssdb.getPort());
						proxyList.set(i, ssdb);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private AtomicBoolean stop = new AtomicBoolean(false);

	public SSDBProxyService(String addressList, boolean singleMaster) {
		String[] array = addressList.split(";");
		int counter = 0;
		for (String s : array) {
			String subArray[] = s.split(":");
			String ip = subArray[0];
			int port = Integer.parseInt(subArray[1]);
			if (singleMaster && counter == 0) {
				for (int i = 0; i < this.poolsize; i++) {
					masters.add(getProxy(ip, port));
				}
			} else {
				for (int i = 0; i < this.poolsize; i++) {
					slaves.add(getProxy(ip, port));
				}
			}
			counter++;
		}
		new Thread("SSDBProxyService-keepalive") {
			public void run() {
				while (!stop.get()) {
					try {
						check();
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}

	public SSDBProxy getSSDB() {
		SSDBProxy proxy = getSSDBProxy();
		while (proxy == null) {
			check();
			proxy = getSSDBProxy();
		}
		return proxy;
	}

	private int counter = 0;

	private SSDBProxy getSSDBProxy() {
		counter++;
		if (counter >= 100000) {
			counter = 0;
		}
		if (!this.masters.isEmpty()) {
			int start = counter % masters.size();
			for (int i = start; i < start + masters.size(); i++) {
				SSDBProxy proxy = masters.get(i % masters.size());
				if (!proxy.isConn()) {
					continue;
				}
				return proxy;
			}
		} else {
			int start = counter % slaves.size();
			for (int i = start; i < start + slaves.size(); i++) {
				SSDBProxy proxy = slaves.get(i % slaves.size());
				if (!proxy.isConn()) {
					continue;
				}
				return proxy;
			}
		}
		return null;
	}
}
