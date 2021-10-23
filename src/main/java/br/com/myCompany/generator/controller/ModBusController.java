package br.com.myCompany.generator.controller;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.NumericLocator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ModBusController {

    private static final Logger logger = LogManager.getLogger(ModBusController.class);

    /**
     * Established connection of modbus with your devices.
     * Receiver via parameter ip and port of equipament for connect.
     * 
     * @param ipDevice
     * @param portDevice
     * @return
     */
    public ModbusMaster connectModBus(String ipDevice, int portDevice) {
        ModbusMaster master = null;
        try {            
            ModbusFactory factory = new ModbusFactory();
            IpParameters parameters = new IpParameters();
            parameters.setHost(ipDevice);
            parameters.setPort(portDevice);
            parameters.setEncapsulated(false);
            master = factory.createTcpMaster(parameters, true);
            master.setTimeout(10000);
            master.setRetries(0);
        } catch (Exception e) {
            logger.error("Failed of create connection modbus with your device: " + ipDevice + " in port " + portDevice);
        }
        return master;
    }

    public NumericLocator readModBusData(int idEquipamento, int idItem) {
		NumericLocator locator = new NumericLocator(idEquipamento, RegisterRange.HOLDING_REGISTER, idItem, DataType.TWO_BYTE_INT_UNSIGNED);
		return locator;
	}
}