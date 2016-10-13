package com.idea4j.common.nosql.ssdb.api;
import com.idea4j.common.nosql.ssdb.Response;
import com.idea4j.common.nosql.ssdb.SSDB;

public class SSDBProxy {
	private java.util.concurrent.locks.Lock lock = new java.util.concurrent.locks.ReentrantLock();
	private String ip;

	public String getIp() {
		return ip;
	}

	private boolean conn = true;

	public boolean isConn() {
		return conn;
	}

	public void setConn(boolean conn) {
		this.conn = conn;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private int port;

	public SSDBProxy(SSDB ssdb) {
		this.ssdb = ssdb;
	}

	private SSDB ssdb;

	public void multi_set(String... kvs) throws Exception {
		lock.lock();
		try {
			ssdb.multi_set(kvs);
		} finally {
			lock.unlock();
		}
	}

	public void h_set(String code, String key, String value) throws Exception {
		lock.lock();
		try {
			ssdb.hset(code, key, value);
		} finally {
			lock.unlock();
		}
	}

	public String h_get(String code, String key) throws Exception {
		lock.lock();
		try {
			byte[] value = ssdb.hget(code, key);
			if (value != null) {
				return new String(value);
			}
		} finally {
			lock.unlock();
		}
		return null;
	}

	public long hincr(String name, String key, long by) throws Exception {
		lock.lock();
		try {
			return ssdb.hincr(name, key, by);
		} finally {
			lock.unlock();
		}
	}

	public Response hscan(String name, String key_start, String key_end,
						  int limit) throws Exception {
		lock.lock();
		try {
			return ssdb.hscan(name, key_start, key_end, limit);
		} finally {
			lock.unlock();
		}
	}

	public void hset(String name, byte[] key, byte[] val) throws Exception {
		lock.lock();
		try {
			ssdb.hset(name, key, val);
		} finally {
			lock.unlock();
		}
	}

	public long incr(String key, long by) throws Exception {
		lock.lock();
		try {
			return ssdb.incr(key, by);
		} finally {
			lock.unlock();
		}
	}

	public void hset(String name, String key, byte[] val) throws Exception {
		this.hset(name, key.getBytes(), val);
	}

	public void hset(String name, String key, String val) throws Exception {
		this.hset(name, key, val.getBytes());
	}

	public Response hrscan(String name, String key_start, String key_end,
			int limit) throws Exception {
		lock.lock();
		try {
			return ssdb.hrscan(name, key_start, key_end, limit);
		} finally {
			lock.unlock();
		}
	}

	public void hdel(String name, byte[] key) throws Exception {
		lock.lock();
		try {
			ssdb.hdel(name, key);
		} finally {
			lock.unlock();
		}
	}

	public void hdel(String name, String key) throws Exception {
		this.hdel(name, key.getBytes());
	}

	public void hclear(String name) throws Exception {
		lock.lock();
		try {
			ssdb.hclear(name);
		} finally {
			lock.unlock();
		}
	}

	public Response multi_get(String... keys) throws Exception {
		lock.lock();
		try {
			return ssdb.multi_get(keys);
		} finally {
			lock.unlock();
		}
	}

	public Response scan(String startKey, String endKey, int lim)
			throws Exception {
		lock.lock();
		try {
			return ssdb.scan(startKey, endKey, lim);
		} finally {
			lock.unlock();
		}
	}

	public Response rscan(String startKey, String endKey, int lim)
			throws Exception {
		lock.lock();
		try {
			return ssdb.rscan(startKey, endKey, lim);
		} finally {
			lock.unlock();
		}

	}

	public void close() {
		lock.lock();
		try {
			ssdb.close();
		} finally {
			lock.unlock();
		}

	}

	public void del(byte[] key) throws Exception {
		lock.lock();
		try {
			ssdb.del(key);
		} finally {
			lock.unlock();
		}
	}

	public void del(String key) throws Exception {
		del(key.getBytes());
	}

	public void set(String key, String values) throws Exception {
		lock.lock();
		try {
			ssdb.set(key, values);
		} finally {
			lock.unlock();
		}
	}

	public byte[] get(byte key[]) throws Exception {
		lock.lock();
		try {
			return ssdb.get(key);
		} catch (Exception ex) {
			ssdb.close();
			throw new RuntimeException();
		} finally {
			lock.unlock();
		}
	}

	public String get(String key) throws Exception {
		lock.lock();
		try {
			return ssdb.get(key);
		} catch (Exception ex) {
			ssdb.close();
			throw new RuntimeException();
		} finally {
			lock.unlock();
		}
	}

	public Response multi_del(String... keys) throws Exception {
		lock.lock();
		try {
			return ssdb.multi_del(keys);
		} finally {
			lock.unlock();
		}
	}

	public Response multi_del(byte[]... keys) throws Exception {
		lock.lock();
		try {
			return ssdb.multi_del(keys);
		} finally {
			lock.unlock();
		}
	}

	public Response multi_get(byte[]... keys) throws Exception {
		lock.lock();
		try {
			return ssdb.multi_get(keys);
		} finally {
			lock.unlock();
		}
	}

	public void set(byte[] key, byte[] val) throws Exception {
		lock.lock();
		try {
			ssdb.set(key, val);
		} catch (Exception ex) {
			ssdb.close();
			throw new RuntimeException();
		} finally {
			lock.unlock();
		}
	}

	public void set(String key, byte[] val) throws Exception {
		set(key.getBytes(), val);
	}

	public void zdel(String name, byte[] key) throws Exception {
		lock.lock();
		try {
			ssdb.zdel(name, key);
		} finally {
			lock.unlock();
		}
	}

	public void zdel(String name, String key) throws Exception {
		this.zdel(name, key.getBytes());
	}

	/**
	 * 
	 * @param name
	 * @param key
	 * @return Double.NaN if not found.
	 * @throws Exception
	 */
	public double zget(String name, byte[] key) throws Exception {
		lock.lock();
		try {
			return ssdb.zget(name, key);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 
	 * @param name
	 * @param key
	 * @return Double.NaN if not found.
	 * @throws Exception
	 */
	public double zget(String name, String key) throws Exception {
		return zget(name, key.getBytes());
	}

	public long zincr(String name, String key, long by) throws Exception {
		lock.lock();
		try {
			return ssdb.zincr(name, key, by);
		} finally {
			lock.unlock();
		}
	}

	public Response zscan(String name, String key, Double score_start,
			Double score_end, int limit) throws Exception {
		lock.lock();
		try {
			return ssdb.zscan(name, key, score_start, score_end, limit);
		} finally {
			lock.unlock();
		}
	}

	public Response zrscan(String name, String key, Double score_start,
			Double score_end, int limit) throws Exception {
		lock.lock();
		try {
			return ssdb.zrscan(name, key, score_start, score_end, limit);
		} finally {
			lock.unlock();
		}
	}

	public void zset(String name, byte[] key, double score) throws Exception {
		lock.lock();
		try {
			ssdb.zset(name, key, score);
		} finally {
			lock.unlock();
		}
	}

	public void zset(String name, String key, double score) throws Exception {
		zset(name, key.getBytes(), score);
	}

	public void setx(String key, String val, long ttl) throws Exception {
		if (key != null && val != null) {
			lock.lock();
			try {
				ssdb.setx(key, val, ttl);
			} catch (Exception ex) {
				ssdb.close();
				throw new RuntimeException();
			} finally {
				lock.unlock();
			}
		}
	}

	public void multi_hset(byte[]... pairs) throws Exception {
		lock.lock();
		try {
			ssdb.multi_hset(pairs);
		} finally {
			lock.unlock();
		}
	}

	public void multi_hset(String... pairs) throws Exception {
		lock.lock();
		try {
			ssdb.multi_hset(pairs);
		} finally {
			lock.unlock();
		}
	}

	public String ttl(String key) throws Exception {
		lock.lock();
		try {
			byte[] value = ssdb.ttl(key);
			if (value != null) {
				return new String(value);
			}
		} finally {
			lock.unlock();
		}
		return null;
	}

	public String hsize(String key) throws Exception {
		lock.lock();
		try {
			byte[] value = ssdb.hsize(key);
			if (value != null) {
				return new String(value);
			}
		} finally {
			lock.unlock();
		}
		return null;
	}
}
