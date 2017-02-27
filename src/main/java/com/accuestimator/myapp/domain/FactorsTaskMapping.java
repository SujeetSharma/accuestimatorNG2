package com.accuestimator.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.accuestimator.myapp.domain.enumeration.STATEENUM;
import com.accuestimator.myapp.domain.enumeration.ESTTYPEENUM;

/**
 * A FactorsTaskMapping.
 */

@Document(collection = "factors_task_mapping")
public class FactorsTaskMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("task_category")
    private String taskCategory;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("factor_category")
    private String factorCategory;

    @NotNull
    @Field("version")
    private String version;

    @NotNull
    @Field("state")
    private STATEENUM state;

    @NotNull
    @Field("esttype")
    private ESTTYPEENUM esttype;

     @Field("createdby")
    private String createdby;

    @Field("createdon")
    private ZonedDateTime createdon;

    @Field("modifiedby")
    private String modifiedby;

    @Field("modifiedon")
    private ZonedDateTime modifiedon;

    @Field("description")
    private String description;

    @Field("estimates")
    private FTEstimates estimates;

    @NotNull
    @Field("active")
    private Boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FTEstimates getEstimates() {
        return estimates;
    }

    public FactorsTaskMapping estimates(FTEstimates estimates) {
        this.estimates = estimates;
        return this;
    }

    public void setEstimates(FTEstimates estimates) {
        this.estimates = estimates;
    }

    public String getName() {
        return name;
    }

    public FactorsTaskMapping name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public FactorsTaskMapping taskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
        return this;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getFactorCategory() {
        return factorCategory;
    }

    public FactorsTaskMapping factorCategory(String factorCategory) {
        this.factorCategory = factorCategory;
        return this;
    }

    public void setFactorCategory(String factorCategory) {
        this.factorCategory = factorCategory;
    }

    public String getVersion() {
        return version;
    }

    public FactorsTaskMapping version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public STATEENUM getState() {
        return state;
    }

    public FactorsTaskMapping state(STATEENUM state) {
        this.state = state;
        return this;
    }

    public void setState(STATEENUM state) {
        this.state = state;
    }

    public ESTTYPEENUM getEsttype() {
        return esttype;
    }

    public FactorsTaskMapping esttype(ESTTYPEENUM esttype) {
        this.esttype = esttype;
        return this;
    }

    public void setEsttype(ESTTYPEENUM esttype) {
        this.esttype = esttype;
    }

    public String getCreatedby() {
        return createdby;
    }

    public FactorsTaskMapping createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public FactorsTaskMapping createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public FactorsTaskMapping modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public ZonedDateTime getModifiedon() {
        return modifiedon;
    }

    public FactorsTaskMapping modifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
        return this;
    }

    public void setModifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    public String getDescription() {
        return description;
    }

    public FactorsTaskMapping description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public FactorsTaskMapping active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FactorsTaskMapping factorsTaskMapping = (FactorsTaskMapping) o;
        if (factorsTaskMapping.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, factorsTaskMapping.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FactorsTaskMapping{" +
            "id=" + id +
            ", taskCategory='" + taskCategory + "'" +
            ", factorCategory='" + factorCategory + "'" +
            ", version='" + version + "'" +
            ", estimates='" + estimates + "'" +
            ", state='" + state + "'" +
            ", createdby='" + createdby + "'" +
            ", createdon='" + createdon + "'" +
            ", modifiedby='" + modifiedby + "'" +
            ", modifiedon='" + modifiedon + "'" +
            ", description='" + description + "'" +
            ", active='" + active + "'" +
            '}';
    }
}

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

class FTEstimates implements Serializable {

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