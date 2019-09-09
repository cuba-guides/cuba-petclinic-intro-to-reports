package com.haulmont.sample.petclinic.web.visit.visit;

import java.time.LocalDate;
import java.time.YearMonth;

public class MonthYearValue extends TimeRange {

  private YearMonth value;

  public MonthYearValue(YearMonth value) {
    this.value = value;
  }

  public static MonthYearValue fromDate(LocalDate date) {
    return new MonthYearValue(YearMonth.of(date.getYear(),date.getMonthValue()));
  }

    public MonthYearValue minusMonths(int monthsToSubstract) {
        if (monthsToSubstract < 0) {
            throw new IllegalArgumentException("Only positive numbers are allowed");
        }
        return new MonthYearValue(value.minusMonths(monthsToSubstract));
    }


  @Override
  public LocalDate getStartLocalDate() {
    return value.atDay(1);
  }

  @Override
  public LocalDate getEndLocalDate() {
    return value.atEndOfMonth();
  }

  @Override
  public String toString() {
    return value.toString();
  }
}