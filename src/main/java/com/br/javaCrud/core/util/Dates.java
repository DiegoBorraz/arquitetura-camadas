package com.br.javaCrud.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Dates {
    public static Date convertoToDate(int year, int month, int day) {
        LocalDate dataNascimento = LocalDate.of(year, month, day);
        Instant instant = dataNascimento.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date dataNascimentoDate = Date.from(instant);
        return dataNascimentoDate;
    }

    public static Date converterStringParaData(String dataString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dataString);
        } catch (ParseException e) {
            return null;
        }
    }
}
