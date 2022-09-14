package com.artion.springboot.form.app.editors;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

public class NombreMayusculaEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        setValue(text.toUpperCase().trim());
    }
}
