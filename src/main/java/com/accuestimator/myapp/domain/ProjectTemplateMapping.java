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
 * A ProjectTemplateMapping.
 */

@Document(collection = "project_template_mapping")
public class ProjectTemplateMapping implements Serializable {

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
    @Field("active")
    private Boolean active;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProjectTemplateMapping name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ProjectTemplateMapping description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public ProjectTemplateMapping projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public ProjectTemplateMapping templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Boolean isActive() {
        return active;
    }

    public ProjectTemplateMapping active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVersion() {
        return version;
    }

    public ProjectTemplateMapping version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public STATEENUM getState() {
        return state;
    }

    public ProjectTemplateMapping state(STATEENUM state) {
        this.state = state;
        return this;
    }

    public void setState(STATEENUM state) {
        this.state = state;
    }

    public String getCreatedby() {
        return createdby;
    }

    public ProjectTemplateMapping createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public ProjectTemplateMapping createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public ProjectTemplateMapping modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public ZonedDateTime getModifiedon() {
        return modifiedon;
    }

    public ProjectTemplateMapping modifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
        return this;
    }

    public void setModifiedon(ZonedDateTime modifiedon) {
        this.modifiedon = modifiedon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectTemplateMapping projectTemplateMapping = (ProjectTemplateMapping) o;
        if (projectTemplateMapping.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, projectTemplateMapping.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectTemplateMapping{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", projectId='" + projectId + "'" +
            ", templateId='" + templateId + "'" +
            ", active='" + active + "'" +
            ", version='" + version + "'" +
            ", state='" + state + "'" +
            ", createdby='" + createdby + "'" +
            ", createdon='" + createdon + "'" +
            ", modifiedby='" + modifiedby + "'" +
            ", modifiedon='" + modifiedon + "'" +
            '}';
    }
}
