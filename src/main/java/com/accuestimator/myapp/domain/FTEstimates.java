package com.accuestimator.myapp.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

class Values implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Field("factorid")
    private String factorid;

    public String getFactorid() {
        return factorid;
    }

    public Values factorid(String factorid) {
        this.factorid = factorid;
        return this;
    }

    public void setFactorid(String factorid) {
        this.factorid = factorid;
    }
    
    @NotNull
    @Field("factorname")
   private String factorname;

    public String getFactorname() {
        return factorname;
    }

    public Values factorname(String factorname) {
        this.factorname = factorname;
        return this;
    }

    public void setFactorname(String factorname) {
        this.factorname = factorname;
    }

    @Field("high")
   private String high;

   public String getHigh() {
        return high;
    }

    public Values high(String high) {
        this.high = high;
        return this;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    @Field("medium")
   private String medium;

   public String getMedium() {
        return medium;
    }

    public Values Medium(String medium) {
        this.medium = medium;
        return this;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Field("low")
   private String low;

    public String getLow() {
        return low;
    }

    public Values Low(String low) {
        this.low = low;
        return this;
    }

    public void setLow(String low) {
        this.low = low;
    }

    @Field("points")
   private String points;

    public String getPoints() {
        return points;
    }

    public Values points(String points) {
        this.points = points;
        return this;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Values{" +
            ", factorid='" + factorid + "'" +
            ", factorname='" + factorname + "'" +
            ", high='" + high + "'" +
            ", medium='" + medium + "'" +
            ", low='" + low + "'" +
            ", points='" + points + "'" +
            '}';
    }

 }

class FTEstimate implements Serializable {

    private static final long serialVersionUID = 211L;

  @Field("taskid")  
  private String taskid;

   public String getTaskid() {
        return taskid;
    }

    public FTEstimate Low(String taskid) {
        this.taskid = taskid;
        return this;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

  @Field("taskname")
  private String taskname;

   public String getTaskname() {
        return taskname;
    }

    public FTEstimate taskname(String taskname) {
        this.taskname = taskname;
        return this;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

  @Field("values")
  private Values[] values;

   public Values[] getValues() {
        return values;
    }

    public FTEstimate values(Values[] values) {
        this.values = values;
        return this;
    }

    public void setValues(Values[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "FTEstimate{" +
            ", taskid='" + taskid + "'" +
            ", taskname='" + taskname + "'" +
            ", values='" + values + "'" +
            '}';
    }



}

public class FTEstimates implements Serializable {

    private static final long serialVersionUID = 133L;
    
    @Field("estimate")
    private FTEstimate[] estimates;

    public FTEstimate[] getEstimates() {
        return estimates;
    }

    public FTEstimates estimates(FTEstimate[] estimates) {
        this.estimates = estimates;
        return this;
    }

    public void setEstimates(FTEstimate[] estimates) {
        this.estimates = estimates;
    }

      @Override
    public String toString() {
        return "FTEstimates{" +
            ", estimates='" + estimates + "'" +
            '}';
    }
      
     

}