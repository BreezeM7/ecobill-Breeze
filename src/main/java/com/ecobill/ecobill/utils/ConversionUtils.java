package com.ecobill.ecobill.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ConversionUtils {

    public Long integerToLongConversion(Integer intValue) {
        return Long.valueOf(intValue);
    }

    public Long doubleToLongConversion(Double doubleValue) {
        return doubleValue.longValue();
    }

    public Date StringToDateConversion(String stringValue) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(stringValue);

    }
}
