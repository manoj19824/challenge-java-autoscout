package com.autoscout.challenge.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;

public class Utility {

    public static String priceFormat(double price){
        BigDecimal bd = new BigDecimal(Double.toString(price));
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return String.format("€%s,-", bd.doubleValue());
    }

    public static String percentageFormat(double value){
        DecimalFormat df = new DecimalFormat("#");
        return df.format(value) + "%";
    }

    public static YearMonth convertToYearMonth(long epoch){
        return YearMonth.from(Instant.ofEpochMilli(epoch)
                .atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static String mileageFormat(double value){
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(value) + " KM";
    }

}
