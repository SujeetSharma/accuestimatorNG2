package com.accuestimator.myapp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.accuestimator.myapp.domain.enumeration.TYPEENUM;

import com.accuestimator.myapp.domain.enumeration.STATEENUM;

/**
 * A Estimates.
 */

@Document(collection = "estimates")
public class Estimates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("project")
    private String project;

    @NotNull
    @Field("type")
    private TYPEENUM type;

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

    @NotNull
    @Field("copied_from")
    private String copiedFrom;

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

    public String getProject() {
        return project;
    }

    public Estimates project(String project) {
        this.project = project;
        return this;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public TYPEENUM getType() {
        return type;
    }

    public Estimates type(TYPEENUM type) {
        this.type = type;
        return this;
    }

    public void setType(TYPEENUM type) {
        this.type = type;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public Estimates taskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
        return this;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTask() {
        return task;
    }

    public Estimates task(String task) {
        this.task = task;
        return this;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFactor() {
        return factor;
    }

    public Estimates factor(String factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getFactorCategory() {
        return factorCategory;
    }

    public Estimates factorCategory(String factorCategory) {
        this.factorCategory = factorCategory;
        return this;
    }

    public void setFactorCategory(String factorCategory) {
        this.factorCategory = factorCategory;
    }

    public String getFormula() {
        return formula;
    }

    public Estimates formula(String formula) {
        this.formula = formula;
        return this;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Float getValue() {
        return value;
    }

    public Estimates value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getVersion() {
        return version;
    }

    public Estimates version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public STATEENUM getState() {
        return state;
    }

    public Estimates state(STATEENUM state) {
        this.state = state;
        return this;
    }

    public void setState(STATEENUM state) {
        this.state = state;
    }

    public String getCopiedFrom() {
        return copiedFrom;
    }

    public Estimates copiedFrom(String copiedFrom) {
        this.copiedFrom = copiedFrom;
        return this;
    }

    public void setCopiedFrom(String copiedFrom) {
        this.copiedFrom = copiedFrom;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Estimates createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public Estimates createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public Estimates modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public ZonedDateTime getModifiedon() {
        return modifiedon;
    }

    public Estimates modifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
        return this;
    }

    public void setModifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    public String getDescription() {
        return description;
    }

    public Estimates description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public Estimates active(Boolean active) {
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
        Estimates estimates = (Estimates) o;
        if (estimates.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, estimates.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Estimates{" +
            "id=" + id +
            ", project='" + project + "'" +
            ", type='" + type + "'" +
            ", taskCategory='" + taskCategory + "'" +
            ", task='" + task + "'" +
            ", factor='" + factor + "'" +
            ", factorCategory='" + factorCategory + "'" +
            ", formula='" + formula + "'" +
            ", value='" + value + "'" +
            ", version='" + version + "'" +
            ", state='" + state + "'" +
            ", copiedFrom='" + copiedFrom + "'" +
            ", createdby='" + createdby + "'" +
            ", createdon='" + createdon + "'" +
            ", modifiedby='" + modifiedby + "'" +
            ", modifiedon='" + modifiedon + "'" +
            ", description='" + description + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
