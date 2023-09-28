package com.idea4j.common.nosql.ssdb.api;


import org.slf4j.LoggerFactory;

public class SSDBCheckThread extends Thread {
    public SSDBCheckThread() {
        DataStoreService.getServiceSet();
        this.setName("ssdb-check-thread");
    }

    @Override
    public void run() {
        // this.setDaemon(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        while (true) {
            try {
                LoggerFactory.getLogger(SSDBProxy.class).debug(
                        "service size "
                                + DataStoreService.getServiceSet().size());
                for (DataStoreService service : DataStoreService.getServiceSet()) {
                    service.check();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
