package br.com.myCompany.generator.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import br.com.myCompany.generator.model.Zabbix;

import com.google.gson.Gson;

public class Configuration {

	private Zabbix zabbix;
	private static final Logger logger = LogManager.getLogger(Configuration.class);
	private String message;
	private String ENVIRONMENT = null;
	
	
	private String loadConfigurationFile(){
		String arquivo = null;
		ENVIRONMENT = System.getenv("GENERATOR_ENVIRONMENT");
		
		if((ENVIRONMENT == null || ENVIRONMENT.equalsIgnoreCase("development"))){
			arquivo = Paths.get("").toAbsolutePath().toString() + "/configuration/config-devel.json";
		} else if(ENVIRONMENT.equalsIgnoreCase("production")) {
			arquivo = Paths.get("").toAbsolutePath().toString() + "/configuration/config.json";
		}
		return arquivo;
	}

	private JSONObject loadJSONConfigurations() {

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		Object obj = null;
		String destFile = loadConfigurationFile();

		try {

			obj = jsonParser.parse(new FileReader(destFile));
			jsonObject = (JSONObject) obj;

		} catch (FileNotFoundException e) {
			message = "File of configuration not found. Environment " + ENVIRONMENT;
			logger.error(message, e);
		} catch (IOException e) {
			message = "Failure of read/write for configuration file. Environment " + ENVIRONMENT;
			logger.error(message, e);
		} catch (org.json.simple.parser.ParseException e) {
			message = "Error in parse of configuration file.";
			logger.error(message, e);
		}

		if(jsonObject !=null) {
			message = "Configuration file loading with sucessfuly. Environment " + ENVIRONMENT;					
			logger.info(message);
		} else {
			message = "Failed of load configuration file. Try again.";
			logger.info(message);
		}

		return jsonObject;
	}

	public Zabbix loadZabbixObject() {

		try {
			JSONObject jsonObject = loadJSONConfigurations();
			Gson gson = new Gson();
			zabbix = gson.fromJson(jsonObject.toString(), Zabbix.class);
		} catch (Exception e) {
			message = "Erro ao carregar o objeto PlacaObject com os dados dos geradores ";
			logger.error(message, e);
		}
		return zabbix;
	}
}
