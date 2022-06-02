package com.fabrick.esercizio.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fabrick.esercizio.beans.BonificoRequest;
import com.fabrick.esercizio.beans.ResponseItem;
import com.fabrick.esercizio.beans.Transaction;
import com.fabrick.esercizio.beans.TransazioniResponseItem;
import com.fabrick.esercizio.utils.FabrickUtils;

@RestController
public class FabrickRestController {

	private static final Logger logger = LoggerFactory.getLogger(FabrickRestController.class);
	
	/*
	 * Inizializzo le costanti utili
	 */
	public static final String API_BANKING_PATH = "/api/gbs/banking/v4.0/accounts/";
	public static final String AUTH_SCHEMA = "Auth-Schema";
	public static final String API_KEY = "apikey";

	/*
	 * Recupero le proprietà necessarie
	 */
	@Value("${api.key}")
	private String apikey;

	@Value("${auth.schema}")
	private String authSchema;
	
	@Value("${account.id}")
	private String accountId;
	
	@Value("${base.url}")
	private String baseUrl;
	
	
	/**
	 * Service per la lettura del saldo
	 */
	@GetMapping(value="/leggiSaldo")
	public String leggiSaldo() {
		String saldo = null;
		String msg = "";
		ResponseEntity<ResponseItem> response = null;
		
		String uri = baseUrl + API_BANKING_PATH + accountId + "/balance";

		//Creazione del Header
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTH_SCHEMA, authSchema);
		headers.set(API_KEY, apikey);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		logger.info("Leggi saldo url: " + uri);
		try {
			response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, ResponseItem.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = "Tentativo di lettura saldo non andato a buon fine. Dettaglio: " + e.getLocalizedMessage();
		}

		if(response != null) {
			ResponseItem result = response.getBody();
			if (result != null) {
				if(result.getStatus().equalsIgnoreCase("ok")) {
					saldo = result.getPayload() != null ? result.getPayload().get("balance") : "";
					logger.info("Il saldo recuperato è di €: " + saldo);
					msg = "Il tuo saldo è di €" + saldo;
				} else {
					logger.info("Tentativo di lettura saldo non andato a buon fine. Status: KO");
					msg = "Tentativo di lettura saldo non andato a buon fine. Status: KO";
				}
			}
		}
		return msg;
	}
	
	/*
	 * Service per creazione di un nuovo bonifico
	 * Durante lo svolgimento del test, la chiamata torna uno  "status": 500, "error": "Internal Server Error", 
	 * sia attraverso l'end point /bonifico, sia attraverso la chiamata all'API diretta.
	 */
	@PostMapping(value="/bonifico")
	public String effettuaBonifico() {
		String msg = "";
		ResponseEntity<ResponseItem> response = null;
		
		String uri = baseUrl + API_BANKING_PATH + accountId + "/payments/money-transfers";

		//Creazione del Header
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTH_SCHEMA, authSchema);
		headers.set(API_KEY, apikey);
		
		//Creazione dell'oggetto 'Bonifico' fantoccio con dati statici
		BonificoRequest bonificoData = FabrickUtils.creaStaticBonificoRequest();

		HttpEntity<?> requestEntity = new HttpEntity<>(bonificoData, headers);
		RestTemplate restTemplate = new RestTemplate();
		logger.info("Bonifico url: " + uri);
		try {
			response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, ResponseItem.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = "Bonifico non effettuato. \nDettaglio: " + e.getLocalizedMessage();
		}

		if(response != null) {
			ResponseItem result = response.getBody();
			if (result != null) {
				if(result.getStatus().equalsIgnoreCase("ok")) {
					logger.info("Il bonifico è stato correttamente effettuato");
					msg = "Il bonifico è stato correttamente effettuato";
				} else {
					logger.info("Tentativo di esecuzione bonifico non andato a buon fine. Status: KO");
					msg = "Bonifico non effettuato. Status: KO";
				}
			}
		}
		return msg;
	}

	
	/*
	 * Service per la lettura delle transazioni effettuate in un determinato arco temporale
	 */
	@GetMapping(value="/leggiTransazioni")
	public String leggiTransazioni() {
		Map<String, List<Transaction>> payLoad = null;
		String msg = "";
		ResponseEntity<TransazioniResponseItem> response = null;
		
		String uri = baseUrl + API_BANKING_PATH + accountId + "/transactions";

		//Setting dei parametri  
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri)
		.queryParam("fromAccountingDate", "{fromAccountingDate}")
		.queryParam("toAccountingDate", "{toAccountingDate}").encode().toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("fromAccountingDate", "2019-01-01");
		params.put("toAccountingDate", "2019-12-01");
		
		//Creazione del Header
		HttpHeaders headers = new HttpHeaders();
		headers.set(AUTH_SCHEMA, authSchema);
		headers.set(API_KEY, apikey);

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		logger.info("Transazioni url: " + urlTemplate);
		try {
			response = restTemplate.exchange(urlTemplate, HttpMethod.GET, requestEntity, TransazioniResponseItem.class, params);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			msg = "Tentativo di lettura transazioni non andato a buon fine. Dettaglio: " + e.getLocalizedMessage();
		}

		if(response != null) {
			TransazioniResponseItem result = response.getBody();
			if (result != null) {
				if(result.getStatus().equalsIgnoreCase("ok")) {
					payLoad = result.getPayload();
					
					String transazioni = FabrickUtils.recuperaTransazioniDaPayload(payLoad);
					
					logger.info("Le transazioni: : " + transazioni);
					msg = "Le tue transazioni: " + transazioni;
				} else {
					logger.info("Tentativo di lettura transazioni non andato a buon fine. Status: KO");
					msg = "Tentativo di lettura transazioni non andato a buon fine. Status: KO";
				}
			}
		}
		return msg;
	}
}
