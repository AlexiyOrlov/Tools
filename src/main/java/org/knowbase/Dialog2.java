package org.knowbase;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;

/**
 * Created on 7/13/18 by alexiy.
 */
public class Dialog2<T> extends Dialog<T> {
    public Dialog2(String title, Node container, Modality modality, ButtonType... buttonTypes) {
        setTitle(title);
        getDialogPane().setContent(container);
        getDialogPane().getButtonTypes().addAll(buttonTypes);
        if(modality!=null)
            initModality(modality);
    }
}
