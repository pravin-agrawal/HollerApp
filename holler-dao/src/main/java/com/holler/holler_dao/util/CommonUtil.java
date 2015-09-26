package com.holler.holler_dao.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class CommonUtil {

	public static final String DATE_FORMAT_MMMDDYYYY = "MMM-dd-yyyy";
	public static final String DATE_FORMAT_MMM_DD_YYY_HHMM_AA = "MMM-dd-yyyy hh:mm aa";

	private CommonUtil() {

	}

	public boolean containsAny(Collection<?> collection, List<?> objects) {
		if (CommonUtil.nullOrEmpty(collection) || CommonUtil.nullOrEmpty(objects)) {
			return false;
		}
		for (Object object : objects) {
			if (collection.contains(object)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAll(Collection<?> collection, List<?> objects) {
		if (CommonUtil.nullOrEmpty(collection) || CommonUtil.nullOrEmpty(objects)) {
			return false;
		}
		if (objects.size() == 1) {
			return collection.contains(objects.get(objects.size() - 1));
		}
		Object lastElement = objects.remove(objects.size() - 1);
		return collection.contains(lastElement) && containsAll(collection, objects);
	}

	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	public static String httpToHttps(String url) {
		return url.replaceFirst("http", "https");
	}

	public static String getUrlString(HttpServletRequest request)
			throws MalformedURLException {
		return getUrl(request).toString();
	}

	public static URL getUrl(HttpServletRequest request)
			throws MalformedURLException {
		String file = (request).getRequestURI();
		if ((request).getQueryString() != null) {
			file += '?' + (request).getQueryString();
		}
		URL reconstructedURL = new URL(request.getScheme(),
				request.getServerName(), request.getServerPort(), file);
		return reconstructedURL;
	}

	private static Properties getProperties(InputStream inputStream)
			throws IOException {
		Properties props = new Properties();
		if (inputStream == null) {
			throw new FileNotFoundException("inputStream is null");
		}
		props.load(inputStream);
		return props;
	}

	public static Properties getPropertiesFromAbsolutePath(String propFileName)
			throws IOException {
		File file = new File(propFileName);
		InputStream inputStream = new FileInputStream(file);
		return getProperties(inputStream);
	}

	public static boolean notNullAndEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static boolean nullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}


	public static float percent(Integer num, Integer deno) {
		return (num * 100) / deno;
	}

	public static String getFullName(String firstName, String lastName) {
		StringBuilder sb = new StringBuilder();
		sb.append(firstName == null ? "" : firstName);
		sb.append(" ");
		sb.append(lastName == null ? "" : lastName);
		return sb.toString();
	}

	public static String stripHtml(String htmlText) {
		if (CommonUtil.notNullAndEmpty(htmlText)) {
			String strippedText = htmlText.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
			return strippedText;
		}
		return null;
	}

	public static int getTemplateId(Object templateId) {
		int templateIdVal = 0;
		if (CommonUtil.isNull(templateId)) {
			return templateIdVal;
		}
		if (templateId instanceof String) {
			if (isTemplateMinorVersion((String) templateId)) {
				templateIdVal = Integer.parseInt(((String) templateId).split("-")[0]);
			} else {
				templateIdVal = Integer.parseInt((String) templateId);
			}
		} else {
			templateIdVal = (Integer) templateId;
		}
		return templateIdVal;
	}

	public static <T> Iterable<T> safe(Iterable<T> iterable) {
		return iterable == null ? Collections.<T>emptyList() : iterable;
	}

	public static String[] safe(String[] array) {
		return array == null ? new String[0] : array;
	}

	public static String convertDateToMMDDYYYY(Date date) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(date);
	}

	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public static void writeFileForDownload(File file,
	                                        HttpServletResponse response) throws IOException {
		byte[] buffer = new byte[(int) file.length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
				}
		}
		response.setContentType("application/octet-stream");
		Date date = new Date();
		response.setHeader("Content-Disposition", "attachment;filename=user"
				+ date.getTime() + ".csv");
		OutputStream out = response.getOutputStream();
		out.write(buffer);
		out.flush();
		out.close();
	}

	public static String convertStreamToString(java.io.InputStream is) throws IOException {
		/*java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";*/

	/*	BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is,FConstants.UTF_8));
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				output.append(line);
			}
			return output.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error while extracting string from stream");
			return null;
		} finally{
			br.close();
		}*/
		StringBuilder output = new StringBuilder();
		Scanner scan = new Scanner(is);
		String line;

		while (scan.hasNextLine()) {
			line = scan.nextLine();
			output.append(line);
		}
		return output.toString();
	}

	public static boolean nullOrEmpty(String str) {
		return null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim());
	}

	public static boolean notNullAndEmpty(String str) {
		return !(null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim()));
	}

	public static Integer getOffset(Integer pageNumber, Integer pageSize) {
		return pageNumber > 0 ? (pageNumber - 1) * pageSize : 0;
	}

	public static <T> List<T> makeArrayList(T... elements) {
		ArrayList<T> list = new ArrayList<T>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	public static Properties getPropertiesFromClasspath(String propFileName) throws IOException {
		Properties props = new Properties();
		InputStream inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(propFileName);
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName

					+ "' not found in the classpath");
		}
		props.load(inputStream);
		return props;
	}

	public static <T, W> Set<T> filter(Collection<W> collection, Class<T> klass) {
		Set<T> result = new HashSet<T>();
		if (notNullAndEmpty(collection)) {
			Iterator<W> itr = collection.iterator();
			while (itr.hasNext()) {
				W value = itr.next();
				if (value.getClass().equals(klass)) {
					result.add((T) value);
				}
			}
		}
		return result;
	}

	public static int occurance(String string, char c) {
		Assert.isTrue(!StringUtils.isEmpty(string), "Input string can not be null.");
		int count = 0;
		if (CommonUtil.notNullAndEmpty(string)) {
			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(i) == c) {
					count++;
				}
			}
		}
		return count;
	}

	public static void trim(String[] array) {
		if (array == null) {
			return;
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
				cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
				cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	public static boolean isSameYear(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
				cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
	}

	public static boolean isNotNull(Object obj) {
		return (null != obj ? true : false);
	}

	public static boolean isNull(Object obj) {
		return (null == obj ? true : false);
	}

	public static int getHour(String hhmmss) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dfh = new SimpleDateFormat("HH");
		Date newDate = new Date();
		try {
			newDate = df.parse(hhmmss);
		} catch (Exception ex) {

		}
		String hourStr = dfh.format(newDate);
		int hour = Integer.parseInt(hourStr);
		return hour;
	}

	public static int getMin(String hhmmss) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dfm = new SimpleDateFormat("mm");
		Date newDate = new Date();
		try {
			newDate = df.parse(hhmmss);
		} catch (Exception ex) {

		}
		String minuteStr = dfm.format(newDate);
		int min = Integer.parseInt(minuteStr);
		return min;
	}

	public static long getPercentage(long numerator, long denominator) {
		return (denominator != 0 ? ((long) ((double) (numerator / denominator)) * 100) : 0);
	}

	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		Date newDate = cal.getTime();
		return newDate;
	}

	public static Integer FormatInt(Integer input) {
		if (input == null) {
			input = 0;
		}
		return input;
	}

    public static Float FormatFloat(Float input) {
        if (input == null) {
            input = 0f;
        }
        return input;
    }

	public static String FormatString(String input) {
		if (input == null) {
			input = "";
		}
		return input.trim();
	}

	public static int ConvertStringToInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String GetFormattedStringForCSV(Object input) {
		String string = "";
		if (input == null) {
			return string;
		} else {
			string = ((String) input).trim();
			if (string.equalsIgnoreCase("null")) {
				string = "null";
			}
			return string;
		}
	}

	public static boolean CheckString(String input) {
		if (input == null || "".equals(input)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNullAsStringValue(String input) {
		if ("null".equalsIgnoreCase(input)) {
			return true;
		} else {
			return false;
		}
	}

	public static String CheckForNullAndGetValue(String input) {
		if (!isNullAsStringValue(input)) {
			return input;
		} else {
			return "";
		}
	}

	/**
	 * Method <code>isTemplateMinorVersion</code> return true in case of minor version.
	 * In case of minor version id will be templateId-minorVersionId
	 *
	 * @param templateIdString
	 * @return
	 */
	public static boolean isTemplateMinorVersion(String templateIdString) {
		if (isNotNull(templateIdString)) {
			if (templateIdString.indexOf("-") != -1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean returnFalseIfBooleanDataIsNull(Boolean data) {
		if (data == null) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isDuplicateEntryInStringArray(String[] array) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < array.length; ++i) {
			if (set.contains(array[i])) {
				return true;
			} else {
				set.add(array[i]);
			}
		}
		return false;
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	public static double getPairUniqueCombination(Integer value1, Integer value2, double size) {
		if (value1 < value2) {
			return value1 * Math.pow(10.0, size) + value2;
		} else {
			return value2 * Math.pow(10.0, size) + value1;
		}
	}

	public static Field[] filterNonStatic(Field[] fields) {
		List<Field> result = new ArrayList<Field>();
		for (int i = 0; i < fields.length; i++) {
			if (!Modifier.isStatic(fields[i].getModifiers())) {
				result.add(fields[i]);
			}
		}
		return (Field[]) result.toArray(new Field[]{});
	}

	public static <T> T copyPropertiesGraceful(Object source, T target) throws Exception {
		Class<? extends Object> srcClass = source.getClass();
		Class<? extends Object> trgClass = target.getClass();
		for (Field srcField : filterNonStatic(srcClass.getDeclaredFields())) {
			srcField.setAccessible(true);
			try {
				Field targetField = trgClass.getDeclaredField(srcField.getName());
				if (Modifier.isStatic(targetField.getModifiers())) {
					continue;
				}
				targetField.setAccessible(true);
				targetField.set(target, srcField.get(source));
			} catch (NoSuchFieldException ex) {
				//log.info("No field " + srcField.getName() + " in the target object");
			}
		}
		return target;
	}

	public static boolean isValidUrl(String url) {
		return url.indexOf("http://") == 0 || url.indexOf("https://") == 0;
	}

	public static String getHoursFromMilliseconds(Long milliseconds) {
		BigDecimal bd = new BigDecimal(Long.toString(milliseconds));
		BigDecimal result = bd.divide(new BigDecimal(1000 * 60 * 60), 2, BigDecimal.ROUND_HALF_UP);
		return result.toString();
	}

	public static BigDecimal calculatePercentage(BigDecimal dividend, BigDecimal divisor) {
		BigDecimal percentageScale = new BigDecimal(100);
		BigDecimal result = dividend.multiply(percentageScale).divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
		return result;
	}

	public static BigDecimal roundUpToTwoDecimalPlaces(BigDecimal value) {
		return value.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static String calculatePerformance(BigDecimal marksScored, BigDecimal averageMarks, BigDecimal standardDeviation) {
		BigDecimal performance = new BigDecimal(0);
		BigDecimal difference = marksScored.subtract(averageMarks);
		if (standardDeviation.compareTo(BigDecimal.ZERO) != 0) {
			performance = difference.divide(standardDeviation, 2, BigDecimal.ROUND_HALF_UP);
		}
		return performance.toString();
	}

	public boolean isIE(HttpServletRequest request) {
		String browser = request.getHeader("User-Agent");
		return (!(browser == null || (!browser.contains("MSIE"))));
	}

    public static Date getDateFromString(String deadline){
        long milliSeconds = Long.parseLong(deadline);
        Date targetDate = new Date(milliSeconds);
        return targetDate;
    }

    public static Integer getIntegerFromDbData(Object data){
        if(isNull(data)){
            return 0;
        }else if(data instanceof BigInteger){
            return ((BigInteger)data).intValue();
        }else if(data instanceof BigDecimal){
            return ((BigDecimal)data).intValue();
        }else if(data instanceof Integer){
            return (Integer)data;
        }
        return 0;
    }
}
