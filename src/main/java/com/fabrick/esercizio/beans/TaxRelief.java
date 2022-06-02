package com.fabrick.esercizio.beans;

import java.util.Map;

public class TaxRelief {

	private String taxReliefId;
	private String isCondoUpgrade;
	private String creditorFiscalCode;
	private String beneficiaryType;
	private Map<String, String> naturalPersonBeneficiary;
	private Map<String, String> legalPersonBeneficiary;
	
	public String getTaxReliefId() {
		return taxReliefId;
	}
	public void setTaxReliefId(String taxReliefId) {
		this.taxReliefId = taxReliefId;
	}
	public String getIsCondoUpgrade() {
		return isCondoUpgrade;
	}
	public void setIsCondoUpgrade(String isCondoUpgrade) {
		this.isCondoUpgrade = isCondoUpgrade;
	}
	public String getCreditorFiscalCode() {
		return creditorFiscalCode;
	}
	public void setCreditorFiscalCode(String creditorFiscalCode) {
		this.creditorFiscalCode = creditorFiscalCode;
	}
	public String getBeneficiaryType() {
		return beneficiaryType;
	}
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}
	public Map<String, String> getNaturalPersonBeneficiary() {
		return naturalPersonBeneficiary;
	}
	public void setNaturalPersonBeneficiary(Map<String, String> naturalPersonBeneficiary) {
		this.naturalPersonBeneficiary = naturalPersonBeneficiary;
	}
	public Map<String, String> getLegalPersonBeneficiary() {
		return legalPersonBeneficiary;
	}
	public void setLegalPersonBeneficiary(Map<String, String> legalPersonBeneficiary) {
		this.legalPersonBeneficiary = legalPersonBeneficiary;
	}

}
