package com.haulmont.sample.petclinic.web.visit.visit;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class TimeRange implements Serializable {

  public Date getStart() {
    return toDate(getStartLocalDate());
  }

  public Date getEnd() {
    return toDate(getEndLocalDate());
  }

  public abstract LocalDate getStartLocalDate();

  public abstract LocalDate getEndLocalDate();

  protected Date toDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}