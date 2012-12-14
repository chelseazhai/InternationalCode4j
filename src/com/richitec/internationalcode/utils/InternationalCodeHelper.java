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
		// define return result
		List<Integer> _ret = new ArrayList<Integer>();

		// get all international code array
		Object[] _allInternationalCodeArray = InternationalCodeManager
				.getInstance().getAllInternationalCode();

		// check the array
		if (null != _allInternationalCodeArray) {
			for (int i = 0; i < _allInternationalCodeArray.length; i++) {
				_ret.add((Integer) _allInternationalCodeArray[i]);
			}
		}

		return _ret;
	}

	// get all international code with international prefix
	public static List<String> getAllInternationalCodesWithInternationalPrefix() {
		// define return result
		List<String> _ret = new ArrayList<String>();

		// get all international code array
		Object[] _allInternationalCodeArray = InternationalCodeManager
				.getInstance().getAllInternationalCode();

		// check the array
		if (null != _allInternationalCodeArray) {
			for (int i = 0; i < _allInternationalCodeArray.length; i++) {
				// append international prefix
				for (int j = 0; j < INTERNATIONAL_PREFIXES.length; j++) {
					_ret.add(INTERNATIONAL_PREFIXES[j]
							+ (Integer) _allInternationalCodeArray[i]);
				}
			}
		}

		return _ret;
	}

}
