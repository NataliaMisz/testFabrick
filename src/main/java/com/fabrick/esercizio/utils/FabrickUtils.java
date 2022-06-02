package com.fabrick.esercizio.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fabrick.esercizio.beans.BonificoRequest;
import com.fabrick.esercizio.beans.Transaction;

public class FabrickUtils {

	public static String recuperaSysdate() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}

	public static String recuperaTransazioniDaPayload(Map<String, List<Transaction>> payLoad) {
		List<Transaction> transazioniLista = payLoad.get("list");
		String transazioni = "";
		if (transazioniLista != null && !transazioniLista.isEmpty()) {
			for (Transaction transazione : transazioniLista) {
				transazioni = transazioni.concat("\n"+transazione.getAccountingDate() + "      " + transazione.getAmount() + transazione.getCurrency() 
						+ "     causale: " + transazione.getDescription());              
			}
		} else {
			transazioni = "Nessuna transazione registrata.";
		}
		return transazioni;
	}

	public static BonificoRequest creaStaticBonificoRequest() {
		BonificoRequest bonificoData = new BonificoRequest();

		Map<String, String> accountMap = new HashMap<>();
		accountMap.put("accountCode", "IT23A0336844430152923804660");
		Map<String, String> beneficiaryMap = new HashMap<>();
		accountMap.put("fiscalCode1", "MRLFNC81L04A859L");
		
		bonificoData.getCreditor().setName( "Mario Rossi");
		bonificoData.getCreditor().setAccount(accountMap);
		
		bonificoData.setDescription("Pagamento affitto del mese: Giugno");
		bonificoData.setAmount("840");
		bonificoData.setCurrency("EUR");
		bonificoData.setExecutionDate(recuperaSysdate());

		bonificoData.getTaxRelief().setIsCondoUpgrade("false");
		bonificoData.getTaxRelief().setCreditorFiscalCode("56258745832");
		bonificoData.getTaxRelief().setBeneficiaryType("NATURAL_PERSON");
		bonificoData.getTaxRelief().setNaturalPersonBeneficiary(beneficiaryMap);
		
		return bonificoData;
	}

}

