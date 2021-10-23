package br.com.myCompany.generator.controller;

import java.io.IOException;
import io.github.hengyunabc.zabbix.sender.DataObject;
import io.github.hengyunabc.zabbix.sender.SenderResult;
import io.github.hengyunabc.zabbix.sender.ZabbixSender;

public class ZabbixSenderController {
	private ZabbixSender zabbixSender;

	public Boolean sendData(String ipZabbixServer, int portZabbix, String nameHost, String keyItem, Number itemValue) {
		
		zabbixSender = new ZabbixSender(ipZabbixServer, portZabbix);		
		DataObject dataObject = new DataObject();
		SenderResult result = null;
		dataObject.setHost(nameHost);
		dataObject.setKey(keyItem);
		dataObject.setValue(String.valueOf(itemValue));
		// TimeUnit is SECONDS.
		dataObject.setClock(System.currentTimeMillis()/1000);
		try {
			result = zabbixSender.send(dataObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.success();
	}
}
