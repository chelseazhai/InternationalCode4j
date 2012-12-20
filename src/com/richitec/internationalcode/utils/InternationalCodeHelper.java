package com.richitec.internationalcode.utils;

import java.util.ArrayList;
import java.util.List;

import com.richitec.internationalcode.AreaAbbreviation;
import com.richitec.internationalcode.InternationalCodeManager;

public class InternationalCodeHelper {

	// international prefix strings
	public static final String[] INTERNATIONAL_PREFIXES = new String[] { "+00",
			"00", "+", "" };

	// international code manager instance
	private static final InternationalCodeManager INTERNATIONALCODEMANAGERINSTANCE = InternationalCodeManager
			.getInstance();

	// get international prefix except for exception string
	public static List<String> getInternationalPrefix(String exception) {
		// define return result
		List<String> _ret = new ArrayList<String>();

		// append all international prefixes
		for (String internationalPrefix : INTERNATIONAL_PREFIXES) {
			_ret.add(internationalPrefix);
		}

		// check exception
		if (null != exception && _ret.contains(exception)) {
			// remove exception international prefix
			_ret.remove(exception);
		}

		return _ret;
	}

	// get all international code
	public static List<Integer> getAllInternationalCodes() {
		return INTERNATIONALCODEMANAGERINSTANCE.getAllInternationalCode();
	}

	// get all international code with international prefix
	public static List<String> getAllInternationalCodesWithInternationalPrefix() {
		// define return result
		List<String> _ret = new ArrayList<String>();

		// traversal all international code
		for (Integer internationalCode : INTERNATIONALCODEMANAGERINSTANCE
				.getAllInternationalCode()) {
			// append international prefix
			for (String internationalPrefix : INTERNATIONAL_PREFIXES) {
				_ret.add(internationalPrefix + internationalCode);
			}
		}

		return _ret;
	}

	// get international code with international prefix by abbreviation
	public static List<String> getInternationalCodeWithInternationalPrefixByAbbreviation(
			AreaAbbreviation abbreviation) {
		// define return result
		List<String> _ret = new ArrayList<String>();

		// traversal all international prefix
		for (String internationalPrefix : INTERNATIONAL_PREFIXES) {
			_ret.add(internationalPrefix
					+ getInternationalCodeByAbbreviation(abbreviation));
		}

		return _ret;
	}

	public static List<String> getInternationalCodeWithInternationalPrefixByAbbreviation(
			List<AreaAbbreviation> abbreviations) {
		// define return result
		List<String> _ret = new ArrayList<String>();

		// traversal all international code matching with given abbreviations
		for (Integer internationalCode : getInternationalCodeByAbbreviation(abbreviations)) {
			// append international prefix
			for (String internationalPrefix : INTERNATIONAL_PREFIXES) {
				_ret.add(internationalPrefix + internationalCode);
			}
		}

		return _ret;
	}

	// get international code by abbreviation
	public static Integer getInternationalCodeByAbbreviation(
			AreaAbbreviation abbreviation) {
		return INTERNATIONALCODEMANAGERINSTANCE
				.getInternationalCodeByAbbreviation(abbreviation);
	}

	public static List<Integer> getInternationalCodeByAbbreviation(
			List<AreaAbbreviation> abbreviations) {
		return INTERNATIONALCODEMANAGERINSTANCE
				.getInternationalCodeByAbbreviation(abbreviations);
	}

}
