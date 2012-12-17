package com.richitec.internationalcode.utils;

import java.util.ArrayList;
import java.util.List;

import com.richitec.internationalcode.InternationalCodeManager;

public class InternationalCodeHelper {

	// international prefix strings
	public static final String[] INTERNATIONAL_PREFIXES = new String[] { "",
			"+", "00", "+00" };

	// get all international code
	public static List<Integer> getAllInternationalCodes() {
		return InternationalCodeManager.getInstance().getAllInternationalCode();
	}

	// get all international code with international prefix
	public static List<String> getAllInternationalCodesWithInternationalPrefix() {
		// define return result
		List<String> _ret = new ArrayList<String>();

		// traversal all international code
		for (Integer internationalCode : InternationalCodeManager.getInstance()
				.getAllInternationalCode()) {
			// append international prefix
			for (String internationalPrefix : INTERNATIONAL_PREFIXES) {
				_ret.add(internationalPrefix + internationalCode);
			}
		}

		return _ret;
	}

}
