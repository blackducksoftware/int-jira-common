/*
 * int-jira-common
 *
 * Copyright (c) 2022 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.jira.common.model.response;

import java.util.List;
import java.util.Optional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.jira.common.model.JiraResponseModel;

public class AvailableAppResponseModel extends JiraResponseModel {
    private JsonObject links;
    private String key;
    private String name;
    private JsonObject vendor;
    private JsonObject logo;
    private String version;
    private String license;
    private String summary;
    private String description;
    private JsonObject versionDetails;
    private String marketPlaceType;
    private Boolean usesLicensing;
    private JsonArray categories;
    private Double rating;
    private Long ratingCount;
    private Long reviewCount;
    private Long downloadCount;
    private Long installationCount;
    private Boolean installed;
    private Boolean installable;
    private Boolean preinstalled;
    private Boolean stable;
    private Boolean dataCenterCompatible;
    private Boolean statusDataCenterCompatible;
    private List<JsonElement> requests;
    private String hamsProductKey;

    public AvailableAppResponseModel() {
    }

    public AvailableAppResponseModel(
        JsonObject links,
        String key,
        String name,
        JsonObject vendor,
        JsonObject logo,
        String version,
        String license,
        String summary,
        String description,
        JsonObject versionDetails,
        String marketPlaceType,
        Boolean usesLicensing,
        JsonArray categories,
        Double rating,
        Long ratingCount,
        Long reviewCount,
        Long downloadCount,
        Long installationCount,
        Boolean installed,
        Boolean installable,
        Boolean preinstalled,
        Boolean stable,
        Boolean dataCenterCompatible,
        Boolean statusDataCenterCompatible,
        List<JsonElement> requests,
        String hamsProductKey
    ) {
        this.links = links;
        this.key = key;
        this.name = name;
        this.vendor = vendor;
        this.logo = logo;
        this.version = version;
        this.license = license;
        this.summary = summary;
        this.description = description;
        this.versionDetails = versionDetails;
        this.marketPlaceType = marketPlaceType;
        this.usesLicensing = usesLicensing;
        this.categories = categories;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.reviewCount = reviewCount;
        this.downloadCount = downloadCount;
        this.installationCount = installationCount;
        this.installed = installed;
        this.installable = installable;
        this.preinstalled = preinstalled;
        this.stable = stable;
        this.dataCenterCompatible = dataCenterCompatible;
        this.statusDataCenterCompatible = statusDataCenterCompatible;
        this.requests = requests;
        this.hamsProductKey = hamsProductKey;
    }

    public Optional<String> getBinaryLink() {
        if (null != links) {
            JsonElement binaryLink = links.get("binary");
            if (null != binaryLink) {
                return Optional.ofNullable(binaryLink.getAsString());
            }
        }
        return Optional.empty();
    }

    public JsonObject getLinks() {
        return links;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public JsonObject getVendor() {
        return vendor;
    }

    public JsonObject getLogo() {
        return logo;
    }

    public String getVersion() {
        return version;
    }

    public String getLicense() {
        return license;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public JsonObject getVersionDetails() {
        return versionDetails;
    }

    public String getMarketPlaceType() {
        return marketPlaceType;
    }

    public Boolean getUsesLicensing() {
        return usesLicensing;
    }

    public JsonArray getCategories() {
        return categories;
    }

    public Double getRating() {
        return rating;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public Long getReviewCount() {
        return reviewCount;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public Long getInstallationCount() {
        return installationCount;
    }

    public Boolean getInstalled() {
        return installed;
    }

    public Boolean getInstallable() {
        return installable;
    }

    public Boolean getPreinstalled() {
        return preinstalled;
    }

    public Boolean getStable() {
        return stable;
    }

    public Boolean getDataCenterCompatible() {
        return dataCenterCompatible;
    }

    public Boolean getStatusDataCenterCompatible() {
        return statusDataCenterCompatible;
    }

    public List<JsonElement> getRequests() {
        return requests;
    }

    public String getHamsProductKey() {
        return hamsProductKey;
    }
}
