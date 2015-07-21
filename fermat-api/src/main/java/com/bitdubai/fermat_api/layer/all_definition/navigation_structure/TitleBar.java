package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import java.awt.Color;
import java.awt.Image;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class TitleBar implements com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatTitleBar {

    String label;
    Color color;
    Image backgroundImage;



    com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView runtimeSearchView;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setRuntimeSearchView(com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView runtimeSearchView){this.runtimeSearchView=runtimeSearchView;};
    public com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView getRuntimeSearchView() {
        if(runtimeSearchView!=null){
            return runtimeSearchView;
        }
        return null;
    }
}
