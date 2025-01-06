package br.com.crearesistemas.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class DateUtils {
	
	public static boolean compareWithPrevious(Date current, Date candidate) {
		if (current == null) {
			return true;
		}
		
		if (candidate == null) {
			return false;
		}
		
		if (candidate.after(current)) {
			return true;
		}
		
		return false;
	}
	
	//"2021-07-21T14:10:00"
	public static String formatDate(Date data)  {
		if (data != null) {
			return formatDate(data, "yyyy-MM-dd'T'HH:mm:ss");	
		} else {
			return formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss");
		}
	}
	
	public static String formatDate(Date data, String format)  {
		String date = null;
		try {
			date = new SimpleDateFormat(format).format(data);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static boolean estaEntreDatas(Date dataInicio, Date dataFim,	Date data) {
		if ((dataInicio != null) && (dataFim != null) && (data != null))
			if (data.compareTo(dataInicio) >= 0)
				if (data.compareTo(dataFim) <= 0)				  
				  return true;
		  return false;
	}

	public static int diferencaEmSegundos(Date dataInicio, Date dataFim) {
		int result = 0;
		try {
			DateTime dataInicial = new DateTime(dataInicio);
			DateTime dataFinal = new DateTime(dataFim);
			Seconds duracaoEmSegundos = Seconds.secondsBetween(dataInicial, dataFinal);
			result = duracaoEmSegundos.getSeconds();
		} catch (Exception e) {}
		return result;
	}

	public static Date SomarSegundos(Date date,	int segundos) {
		DateTime data = new DateTime(date);
		return data.plusSeconds(segundos).toDate();
	}

	public static Date getDate(Date data, Date dataDefault) {		
		return (data == null)?dataDefault: data;
	}

	
	public static Date diminuirSegundos(Date date, int segundos) {
		DateTime data = new DateTime(date);
		return data.minusSeconds(segundos).toDate();
	}

	public static Date somarSegundos(Date date,	int segundos) {
		DateTime data = new DateTime(date);
		return data.plusSeconds(segundos).toDate();
	}

	public static String formatDateDef(Date data) {
		if (data != null) {
			return formatDate(data, "dd/MM/yyyy HH:mm:ss");	
		} else {
			return null;
		}
	}

	//"2021-07-21T14:10:00"
	public static Date getDate(String strDate) {
		Date date = null;
		try {
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	        date = parser.parse(strDate);
		} catch (Exception e) {}
	        
		return date;
	}

	public static Date getLastDate(Date first, Date second) {
		Date now = new Date();
		Date result = now;
		
		if (first != null && second != null) {
			if (first.after(second)) {
				result = first;
			} else {
				result = second;
			}
		} else if (first != null && second == null) {
			if (first.after(now)) {
				result = first;
			} else {
				result = now;
			}			
		} else if (first == null && second != null) {
			if (second.after(now)) {
				result = second;
			} else {
				result = now;
			}	
		}
		
		return result;
	}
	
}
