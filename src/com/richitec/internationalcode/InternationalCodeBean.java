package com.richitec.internationalcode;

import java.io.Serializable;

public class InternationalCodeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 452055789260873164L;

	// identifier
	private Integer id;
	// code
	private Integer code;
	// country name
	private String countryName;
	// abbreviation
	private String abbreviation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Override
	public String toString() {
		// init international code description
		StringBuilder _internationalCodeDescription = new StringBuilder();

		// append id, code, country name and abbreviation
		_internationalCodeDescription.append("International code id: ")
				.append(id).append(", ");
		_internationalCodeDescription.append("code: ").append(code)
				.append(", ");
		_internationalCodeDescription.append("country name: ")
				.append(countryName).append(", ");
		_internationalCodeDescription.append("abbreviation: ")
				.append(abbreviation).append("\n");

		return _internationalCodeDescription.toString();
	}

}
