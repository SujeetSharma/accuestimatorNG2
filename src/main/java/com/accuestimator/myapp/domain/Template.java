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
 * A Template.
 */

@Document(collection = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("active")
    private Boolean active;

    @NotNull
    @Field("factor_task_id")
    private String factorTaskId;

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

    @Field("referenced_from")
    private String referencedFrom;

    @Field("modifiedby")
    private String modifiedby;

    @Field("modifiedon")
    private ZonedDateTime modifiedon;

    @Field("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public Template active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFactorTaskId() {
        return factorTaskId;
    }

    public Template factorTaskId(String factorTaskId) {
        this.factorTaskId = factorTaskId;
        return this;
    }

    public void setFactorTaskId(String factorTaskId) {
        this.factorTaskId = factorTaskId;
    }

    public String getVersion() {
        return version;
    }

    public Template version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public STATEENUM getState() {
        return state;
    }

    public Template state(STATEENUM state) {
        this.state = state;
        return this;
    }

    public void setState(STATEENUM state) {
        this.state = state;
    }

    public String getCreatedby() {
        return createdby;
    }

    public Template createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public Template createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public String getReferencedFrom() {
        return referencedFrom;
    }

    public Template referencedFrom(String referencedFrom) {
        this.referencedFrom = referencedFrom;
        return this;
    }

    public void setReferencedFrom(String referencedFrom) {
        this.referencedFrom = referencedFrom;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public Template modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public ZonedDateTime getModifiedon() {
        return modifiedon;
    }

    public Template modifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
        return this;
    }

    public void setModifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    public String getDescription() {
        return description;
    }

    public Template description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template template = (Template) o;
        if (template.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, template.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + id +
            ", active='" + active + "'" +
            ", factorTaskId='" + factorTaskId + "'" +
            ", version='" + version + "'" +
            ", state='" + state + "'" +
            ", createdby='" + createdby + "'" +
            ", createdon='" + createdon + "'" +
            ", referencedFrom='" + referencedFrom + "'" +
            ", modifiedby='" + modifiedby + "'" +
            ", modifiedon='" + modifiedon + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
