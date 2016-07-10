package com.home.shield.sensors;

import java.util.HashMap;
import java.util.Map;

public class SensorsStore {

    private static SensorsStore instance = new SensorsStore();

    private Map<String, Sensor> store;

    private SensorsStore() {
        store = new HashMap<>();
    }

    public static SensorsStore getInstance() {
        return instance;
    }

    public Sensor getSensor(String rfCode) {
        return store.get(rfCode);
    }

    public void registerSensor(String rfCode, Sensor sensor) {
        store.put(rfCode, sensor);
    }

    public Map getAll() {
        return store;
    }



}
