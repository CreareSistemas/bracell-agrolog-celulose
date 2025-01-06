package br.com.crearesistemas.util;

public final class NumberUtils {
	private static final char VIRGULA = ',';
	private static final char PONTO = '.';

	private static final int BASE_10 = 10;

	private NumberUtils() {
	}

	public static int parseInt(Integer value) {
		try {
			return value == null ? 0 : value.intValue();
		} catch (Exception e) {}		
		return 0;
	}
	
	public static Integer parseInt(String value) {
		Integer result = null;
		
		try {
			result = value == null ? null : Integer.parseInt(value, BASE_10);
		} catch (Exception e) {}
		
		return result;
	}

	public static Integer parseInt(Float value) {
		Integer result = null;
		
		try {
			result = value == null ? null : value.intValue();
		} catch (Exception e) {}
		
		return result;
	}
	
	public static Integer parseInt(Long value) {
		Integer result = null;
		
		try {
			result = value == null ? null : value.intValue();
		} catch (Exception e) {}
		
		return result;
	}
	
	public static long parseLong(String value) {
		long result = 0L;
		try {
			result = value == null ? 0L : Long.parseLong(value, BASE_10);
		} catch (Exception e) {}
		return result;
	}

	
	public static float parseFloat(String float1) {
		return Float.parseFloat(float1);
	}

	public static double parseDouble(String value) {
		return Double.parseDouble(value);
	}
	
	public static Double parseObjectDouble(String value) {
		return value == null? null: Double.parseDouble(value);
	}

	public static float fixAndParseFloat(String value) {
		value = NumberUtils.fixDecimal(value);
		return parseFloat(value);
	}

	public static double fixAndParseDouble(String value) {
		value = NumberUtils.fixDecimal(value);
		return parseDouble(value);
	}

	public static Integer fix(Integer value) {
		if (value == null || value < 0) {
			return 0;
		}
		return value;
	}

	public static Long fixLong(Integer n) {
		return n == null ? null : n.longValue();
	}

	public static String fixDecimal(String decimalNumber) {
		return decimalNumber.replace(VIRGULA, PONTO);
	}

	public static boolean parseBool(String str) {
		return Boolean.parseBoolean(str);
	}

	public static long[] parseLongArray(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			long[] longArray = new long[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				longArray[i] = parseLong(stringArray[i]);
			}
			return longArray;
		}
	}

	public static int[] parseIntArray(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			int[] intArray = new int[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				intArray[i] = parseInt(stringArray[i]);
			}
			return intArray;
		}
	}

	public static float parseFloat(Double value) {
		return value == null ? null : value.floatValue();
	}

	public static String double2string(double n) {
		return String.format("%s", n);
	}

	public static long nullSafe(Long value) {
		return value == null ? 0 : value;
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

	
}
