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
 * A ProjectUserMapping.
 */

@Document(collection = "project_user_mapping")
public class ProjectUserMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("userid")
    private String userid;

    @NotNull
    @Field("projectid")
    private String projectid;

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

    public String getUserid() {
        return userid;
    }

    public ProjectUserMapping userid(String userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProjectid() {
        return projectid;
    }

    public ProjectUserMapping projectid(String projectid) {
        this.projectid = projectid;
        return this;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public Boolean isActive() {
        return active;
    }

    public ProjectUserMapping active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getVersion() {
        return version;
    }

    public ProjectUserMapping version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public STATEENUM getState() {
        return state;
    }

    public ProjectUserMapping state(STATEENUM state) {
        this.state = state;
        return this;
    }

    public void setState(STATEENUM state) {
        this.state = state;
    }

    public String getCreatedby() {
        return createdby;
    }

    public ProjectUserMapping createdby(String createdby) {
        this.createdby = createdby;
        return this;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public ProjectUserMapping createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public ProjectUserMapping modifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
        return this;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public ZonedDateTime getModifiedon() {
        return modifiedon;
    }

    public ProjectUserMapping modifiedon(ZonedDateTime modifiedon) {
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
        ProjectUserMapping projectUserMapping = (ProjectUserMapping) o;
        if (projectUserMapping.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, projectUserMapping.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProjectUserMapping{" +
            "id=" + id +
            ", userid='" + userid + "'" +
            ", projectid='" + projectid + "'" +
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
