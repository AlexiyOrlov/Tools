package dev.buildtool.tools.javafx;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;

/**
 * Created on 7/13/18 by alexiy.
 * @param <T> return value class
 * @param <C> container class
 */
@SuppressWarnings("unchecked")
public class Dialog2<T,C extends Node> extends Dialog<T> {
    /**
     *
     * @param container dialog's main container
     */
    public Dialog2(String title, C container, Modality modality, ButtonType... buttonTypes) {
        setTitle(title);
        getDialogPane().setContent(container);
        getDialogPane().getButtonTypes().addAll(buttonTypes);
        if(modality!=null)
            initModality(modality);
    }

    /**
     * Returns the dialog's content root, to which you can add new elements
     */
    public C getContainer()
    {
        return (C) getDialogPane().getContent();
    }
}
