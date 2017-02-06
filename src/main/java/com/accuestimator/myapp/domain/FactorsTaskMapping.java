package com.accuestimator.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.accuestimator.myapp.domain.enumeration.STATEENUM;

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
    @Field("task")
    private String task;

    @NotNull
    @Field("factor")
    private String factor;

    @NotNull
    @Field("factor_category")
    private String factorCategory;

    @NotNull
    @Field("formula")
    private String formula;

    @NotNull
    @Field("value")
    private Float value;

    @NotNull
    @Field("version")
    private String version;

    @NotNull
    @Field("state")
    private STATEENUM state;

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

    @NotNull
    @Field("active")
    private Boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTask() {
        return task;
    }

    public FactorsTaskMapping task(String task) {
        this.task = task;
        return this;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFactor() {
        return factor;
    }

    public FactorsTaskMapping factor(String factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(String factor) {
        this.factor = factor;
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

    public String getFormula() {
        return formula;
    }

    public FactorsTaskMapping formula(String formula) {
        this.formula = formula;
        return this;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Float getValue() {
        return value;
    }

    public FactorsTaskMapping value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
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
            ", task='" + task + "'" +
            ", factor='" + factor + "'" +
            ", factorCategory='" + factorCategory + "'" +
            ", formula='" + formula + "'" +
            ", value='" + value + "'" +
            ", version='" + version + "'" +
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
