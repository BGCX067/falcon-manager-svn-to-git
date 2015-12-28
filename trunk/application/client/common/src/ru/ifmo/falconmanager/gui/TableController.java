package ru.ifmo.falconmanager.gui;

import ru.ifmo.falconmanager.database.AbstractObjectStore;
import ru.ifmo.falconmanager.database.ObjectInfo;
import ru.ifmo.falconmanager.database.ObjectStoreListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

/**
 * @author Dmitry Paraschenko
 * @version 17.05.2008 0:08:33
 */
public abstract class TableController<I extends ObjectInfo<?>> implements ActionListener, ListSelectionListener {
    private static final String EDIT_COMMAND = "Edit";

    private AbstractObjectStore<I> store;

    private String currentAction = null;
    private JTable table;
    private JPanel cardPane;
    private ButtonGroup buttonGroup;
    private JToggleButton editButton;
    private JButton deleteButton;

    private I selectedObject;
    
    protected TableController(AbstractObjectStore<I> store,
                              JTable table,
                              JPanel cardPane,
                              JButton createApplyButton,
                              JButton editApplyButton,
                              ButtonGroup buttonGroup,
                              JToggleButton[] buttons, 
                              JToggleButton editButton,
                              JButton deleteButton
    ) {
        this.store = store;
        this.table = table;
        this.cardPane = cardPane;
        this.buttonGroup = buttonGroup;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        this.selectedObject = null;
        for (JToggleButton button : buttons) {
            button.addActionListener(this);
        }
        cardPane.setVisible(false);
        buttonGroup.clearSelection();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(this);
        createApplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                I info = getCreatedObject();
                if (info != null) {
                    info.addToDatabase();
                }
            }
        });
        editApplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                I info = getUpdatedObject();
                if (info != null) {
                    info.updateInDatabase();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                I info = getSelectedObject();
                if (info != null) {
                    info.removeFromDatabase();
                }
            }
        });
        store.addObjectStoreListener(new ObjectStoreListener<I>() {
            public void updated(Collection<I> set) {
                valueChanged(null);
            }
        });
    }

    public void hideCardPane() {
        currentAction = null;
        cardPane.setVisible(false);
        buttonGroup.clearSelection();
    }

    public I getSelectedObject() {
        int row = table.getSelectionModel().getLeadSelectionIndex();
        I result;
        if (row < 0) {
            row = store.getIndexOf(selectedObject);
            table.getSelectionModel().setSelectionInterval(row, row);
        }
        result = store.getObjectAt(row);
        if (result != null) {
            selectedObject = result;
        }
        return result;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(currentAction)) {
            hideCardPane();
        } else {
            currentAction = command;
            cardPane.setVisible(true);
            CardLayout cardLayout = (CardLayout) cardPane.getLayout();
            cardLayout.show(cardPane, command);
            if (EDIT_COMMAND.equals(command)) {
                valueChanged(null);
            }
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        I object = getSelectedObject();
        if (object == null) {
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            if (EDIT_COMMAND.equals(currentAction)) {
                hideCardPane();
            }
        } else {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            loadObject(object);
        }
    }

    public abstract void loadObject(I object);

    public abstract I getCreatedObject();
    
    public abstract I getUpdatedObject();
}
