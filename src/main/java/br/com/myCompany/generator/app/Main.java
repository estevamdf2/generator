package br.com.myCompany.generator.app;

import java.util.List;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.NumericLocator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.myCompany.generator.controller.ModBusController;
import br.com.myCompany.generator.controller.ZabbixSenderController;
import br.com.myCompany.generator.model.Board;
import br.com.myCompany.generator.model.Generator;
import br.com.myCompany.generator.model.ItensBoard;
import br.com.myCompany.generator.model.ItensGenerator;
import br.com.myCompany.generator.model.Zabbix;
import br.com.myCompany.generator.util.Configuration;

public class Main {

	private static final Logger logger = LogManager.getLogger(Main.class);
	private static final ModBusController modBusController = new ModBusController();

	public static void main(String[] args) {

		Configuration configuration = new Configuration();
		Zabbix zabbix = new Zabbix();
		zabbix = configuration.loadZabbixObject();

		for (Board board : zabbix.getBoards()) {
			try {
				readItensBoards(board, zabbix.getItensBoards());
				readItensGenerators(board, zabbix.getItensGenerator());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void readItensBoards(Board board, List<ItensBoard> itensBoard) {
		String ipPlaca = board.getIp();
		int porta = board.getPortBoard();
		int portaZabbix = board.getPortZabbix();
		int idEquipamento = board.getId();
		ZabbixSenderController zabbixSenderController = new ZabbixSenderController();
		ModbusMaster master = modBusController.connectModBus(ipPlaca, porta);
		Boolean resultado;
		Number leitura = null;

		try {
			master.init();
			master.setConnected(true);
			logger.info("Send datas of itens boards " + board.getName() + " ip " + board.getIp());

			for (ItensBoard item : itensBoard) {
				NumericLocator locator = modBusController.readModBusData(idEquipamento, item.getId());
				leitura = master.getValue(locator);
				logger.info("IP Board " + ipPlaca + " item/key " + item.getKey() + " value " + leitura);

				resultado = zabbixSenderController.sendData(board.getIpZabbixServer(), portaZabbix, board.getName(),
						item.getKey(), leitura);

				if (resultado) {
					logger.info("Value of item " + item.getKey() + " sent to Zabbix.");
				} else {
					logger.error("Failed to sent item " + item.getKey() + " of Zabbix.");
				}
			}
		} catch (ModbusInitException e) {
			logger.error("Failed to estabilished modbus connection for send datas of board. IP: " + ipPlaca + ". Error " + e);
		} catch (ModbusTransportException e) {
			logger.error("ModBusTransport failed " + e);
		} catch (ErrorResponseException e) {
			logger.error("Error responseException " + e);
			logger.error("Failed of communication with device " + ipPlaca + " Board name " + board.getName() + " ID Board " + board.getId());
		} finally {
			logger.info("Close modbus connection. IP " + ipPlaca);
			master.destroy();
		}
	}

	public static void readItensGenerators(Board board, List<ItensGenerator> itensGenerator) {
		String ipPlaca = board.getIp();
		int porta = board.getPortBoard();
		int portaZabbix = board.getPortZabbix();
		ZabbixSenderController zabbixSenderController = new ZabbixSenderController();
		ModbusMaster master = modBusController.connectModBus(ipPlaca, porta);
		Boolean resultado;
		Number leitura = null;
		Generator generator = null;

		try {
			master.init();
			master.setConnected(true);

			for (Generator generatorAux : board.getGenerators()) {
				generator = generatorAux;
				logger.info("Send datas of itens generators " + generatorAux.getNameHost() + " IP " + board.getIp()
				+ "\n \n");
				for (ItensGenerator item : itensGenerator) {
					NumericLocator locator = modBusController.readModBusData(generatorAux.getId(), item.getId());
					leitura = master.getValue(locator);
					logger.info("Generator ip " + generatorAux.getId() + " name of generator " + generatorAux.getName()
								+ " name of zabbix host " + generatorAux.getName()
								+ " item/key " + item.getKey() + " value " + leitura);

					resultado = zabbixSenderController.sendData(board.getIpZabbixServer(), portaZabbix,
							generatorAux.getNameHost(), item.getKey(), leitura);

					if (resultado) {
						logger.info("Value of item " + item.getKey() + " sent to Zabbix.");
					} else {
						logger.error("Failed to sent item" + item.getKey() + " of Zabbix.");
					}
				}
			}
		} catch (ModbusInitException e) {
			logger.error("Failed to estabilished modbus connection for send datas of board. IP: " + ipPlaca + ". Error " + e);			
		} catch (ModbusTransportException e) {
			logger.error("ModBusTransport failed " + e);			
		} catch (ErrorResponseException e) {			
			logger.error("Failed of communication with device " + ipPlaca + " generator name " + generator.getNameHost() + " ID Generator " + generator.getId());			
		} finally {
			logger.info("Close modbus connection. IP " + ipPlaca + "\n \n");
			master.destroy();
		}
	}
}
