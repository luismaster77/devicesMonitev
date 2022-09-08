package com.monitev.devices.services.devicesData;

import java.util.Map;

public interface IDeviceData {

	Map<String, Object> generarBackup(String deviceId) throws Exception;

}
