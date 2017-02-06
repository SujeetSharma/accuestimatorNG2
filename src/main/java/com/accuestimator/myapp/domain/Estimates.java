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
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @NotNull
    @Field("project_id")
    private String projectId;

    @NotNull
    @Field("template_id")
    private String templateId;

    @NotNull
    @Field("task_id")
    private String taskId;

    @NotNull
    @Field("factor_id")
    private String factorId;

    @NotNull
    @Field("type")
    private TYPEENUM type;

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
    @Field("referenced_from")
    private String referencedFrom;

    @Field("createdby")
    private String createdby;

    @Field("createdon")
    private ZonedDateTime createdon;

    @Field("modifiedby")
    private String modifiedby;

    @Field("modifiedon")
    private ZonedDateTime modifiedon;

    @NotNull
    @Field("active")
    private Boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Estimates name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProjectId() {
        return projectId;
    }

    public Estimates projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public Estimates templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTaskId() {
        return taskId;
    }

    public Estimates taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFactorId() {
        return factorId;
    }

    public Estimates factorId(String factorId) {
        this.factorId = factorId;
        return this;
    }

    public void setFactorId(String factorId) {
        this.factorId = factorId;
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

    public String getReferencedFrom() {
        return referencedFrom;
    }

    public Estimates referencedFrom(String referencedFrom) {
        this.referencedFrom = referencedFrom;
        return this;
    }

    public void setReferencedFrom(String referencedFrom) {
        this.referencedFrom = referencedFrom;
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
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", projectId='" + projectId + "'" +
            ", templateId='" + templateId + "'" +
            ", taskId='" + taskId + "'" +
            ", factorId='" + factorId + "'" +
            ", type='" + type + "'" +
            ", value='" + value + "'" +
            ", version='" + version + "'" +
            ", state='" + state + "'" +
            ", referencedFrom='" + referencedFrom + "'" +
            ", createdby='" + createdby + "'" +
            ", createdon='" + createdon + "'" +
            ", modifiedby='" + modifiedby + "'" +
            ", modifiedon='" + modifiedon + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
