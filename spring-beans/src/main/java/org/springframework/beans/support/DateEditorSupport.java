package org.springframework.beans.support;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateEditorSupport extends PropertyEditorSupport {

	private String format = "yyyy-MM-dd";

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("text: " + text);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			java.util.Date date = sdf.parse(text);
			this.setValue(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
