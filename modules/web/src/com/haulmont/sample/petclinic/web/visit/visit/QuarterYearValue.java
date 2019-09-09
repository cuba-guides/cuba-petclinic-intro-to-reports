package com.haulmont.sample.petclinic.web.visit.visit;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.IsoFields;

public class QuarterYearValue extends TimeRange {

  private Quarter quarter;
  private Year year;

  public QuarterYearValue(Quarter quarter, Year year) {
    this.quarter = quarter;
    this.year = year;
  }

  @Override
  public LocalDate getStartLocalDate() {
    return quarter.getStartOfQuarter(year);
  }

  @Override
  public LocalDate getEndLocalDate() {
    return quarter.getEndOfQuarter(year);
  }

  @Override
  public String toString() {
    return quarter + " - " + year;
  }


  public static QuarterYearValue fromDate(LocalDate date) {
    return new QuarterYearValue(Quarter.fromDate(date), Year.of(date.getYear()));
  }

  public QuarterYearValue minusQuarters(int quartersToSubstract) {
      if (quartersToSubstract < 0) {
          throw new IllegalArgumentException("Only positive numbers are allowed");
      }
      return minusQuarters(this, quartersToSubstract);
  }

  private QuarterYearValue minusQuarters(QuarterYearValue quarterYearValue, int quartersToSubstract) {
      return quartersToSubstract == 0 ? quarterYearValue : minusQuarters(
              quarterYearValue.previous(),
              quartersToSubstract - 1
      );
  }

  public QuarterYearValue previous() {
    int quarterNumber = quarter.getQuarterNumber();

    if (quarterNumber == 1) {
      return new QuarterYearValue(Quarter.fromNumber(4), year.minusYears(1));
    }
    else {
      return new QuarterYearValue(Quarter.fromNumber(quarterNumber - 1), year.minusYears(0));
    }
  }

  public enum Quarter {


    Q1(Month.JANUARY, Month.MARCH, 1),
    Q2(Month.APRIL, Month.JUNE, 2),
    Q3(Month.JULY, Month.SEPTEMBER, 3),
    Q4(Month.OCTOBER, Month.DECEMBER, 4);

    private final Month start;
    private final Month end;
    private final int quarterNumber;

    Quarter(Month start, Month end, int quarterNumber) {
      this.start = start;
      this.end = end;
      this.quarterNumber = quarterNumber;
    }

    public LocalDate getEndOfQuarter(Year year) {
      return YearMonth.of(year.getValue(), end.getValue()).atEndOfMonth();
    }

    public LocalDate getStartOfQuarter(Year year) {
      return YearMonth.of(year.getValue(), start.getValue()).atDay(1);
    }

    public int getQuarterNumber() {
      return quarterNumber;
    }

    @Override
    public String toString() {
      return name();
    }

    public static Quarter fromDate(LocalDate date) {
      int quarterValue = date.get(IsoFields.QUARTER_OF_YEAR);
      return fromNumber(quarterValue);
    }

    public static Quarter fromNumber(int quarterNumber) {

      Quarter[] values = Quarter.values();

      for (Quarter potentialQuarter : values) {
        if (potentialQuarter.quarterNumber == quarterNumber) {
          return potentialQuarter;
        }
      }
      throw new IllegalArgumentException("Invalid Quarter: " + quarterNumber);
    }
  }
}