package org.casadocodigo.loja.CustomEditor;

import java.beans.PropertyEditorSupport;

import org.casadocodigo.loja.models.Role;

public class RoleEditor extends PropertyEditorSupport{
	
	// Converts a String to a Category (when submitting form)
    @Override
    public void setAsText(String text) {
        Role c = new Role();
        c.setName(text);
        this.setValue(c);
    }

    // Converts a Category to a String (when displaying form)
    @Override
    public String getAsText() {
        Role c = (Role) this.getValue();
        return c.getName();
    }

}
