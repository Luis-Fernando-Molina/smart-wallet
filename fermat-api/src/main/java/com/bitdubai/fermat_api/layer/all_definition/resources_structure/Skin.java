package com.bitdubai.fermat_api.layer.all_definition.resources_structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;



/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin</code>
 * implements the functionality of a Fermat Skin.
 * <p/>
 *
 * Created by Leon Acosta and Matias Furszyfer
 * @version 1.0
 * @since Java JDK 1.7
 */

public class Skin implements Serializable {//implements FermatSkin {

    private UUID id;

    private String name;

    private Version version;

    // agregar

    private VersionCompatibility navigationStructureCompatibility;

    private ScreenSize screenSize;

    private Map<String,Resource> lstPortraitResources;

    private Map<String,Resource> lstLandscapeResources;

    private Map<String,Layout> lstPortraitLayouts;

    private Map<String,Layout> lstLandscapeLayouts;


    public Skin(UUID id, String name, Version version, VersionCompatibility navigationStructureCompatibility, ScreenSize screenSize, Map<String, Resource> lstPortraitResources, Map<String, Resource> lstLandscapeResources, Map<String, Layout> lstPortraitLayouts, Map<String, Layout> lstLandscapeLayouts) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.navigationStructureCompatibility = navigationStructureCompatibility;
        this.screenSize = screenSize;
        this.lstPortraitResources = lstPortraitResources;
        this.lstLandscapeResources = lstLandscapeResources;
        this.lstPortraitLayouts = lstPortraitLayouts;
        this.lstLandscapeLayouts = lstLandscapeLayouts;
    }

    public Skin() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public VersionCompatibility getNavigationStructureCompatibility() {
        return navigationStructureCompatibility;
    }

    public void setNavigationStructureCompatibility(VersionCompatibility navigationStructureCompatibility) {
        this.navigationStructureCompatibility = navigationStructureCompatibility;
    }

    public Map<String, Resource> getLstPortraitResources() {
        return lstPortraitResources;
    }

    public void setLstPortraitResources(Map<String, Resource> lstPortraitResources) {
        this.lstPortraitResources = lstPortraitResources;
    }

    public Map<String, Resource> getLstLandscapeResources() {
        return lstLandscapeResources;
    }

    public void setLstLandscapeResources(Map<String, Resource> lstLandscapeResources) {
        this.lstLandscapeResources = lstLandscapeResources;
    }

    public Map<String, Layout> getLstPortraitLayouts() {
        return lstPortraitLayouts;
    }

    public void setLstPortraitLayouts(Map<String, Layout> lstPortraitLayouts) {
        this.lstPortraitLayouts = lstPortraitLayouts;
    }

    public Map<String, Layout> getLstLandscapeLayouts() {
        return lstLandscapeLayouts;
    }

    public void setLstLandscapeLayouts(Map<String, Layout> lstLandscapeLayouts) {
        this.lstLandscapeLayouts = lstLandscapeLayouts;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", navigationStructureCompatibility=" + navigationStructureCompatibility +
                ", screenSize=" + screenSize +
                ", lstPortraitResources=" + lstPortraitResources +
                ", lstLandscapeResources=" + lstLandscapeResources +
                ", lstPortraitLayouts=" + lstPortraitLayouts +
                ", lstLandscapeLayouts=" + lstLandscapeLayouts +
                '}';
    }
}
