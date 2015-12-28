package ru.ifmo.falconmanager.monitor.gui;

import ru.ifmo.falconmanager.database.*;
import ru.ifmo.falconmanager.database.models.*;
import ru.ifmo.falconmanager.gui.TableController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 18:28:37
 */
public class MainForm {
    private JPanel rootPane;
    private JTabbedPane tabbedPane1;
    private JToggleButton airportsCreateButton = new JToggleButton();
    private JToggleButton airportsEditButton = new JToggleButton();
    private JButton airportsDeleteButton = new JButton();
    private JTable airportsTable;
    private JPanel airportsCardPane;
    private JPanel airportsEditPane;
    private JPanel airportsCreatePane;
    private JLabel airportsEditId;
    private JTextField airportsEditShortName;
    private JTextField airportsEditFullName;
    private JButton airportsEditApply;
    private JTextField airportsCreateShortName;
    private JTextField airportsCreateFullName;
    private JButton airportsCreateApply;
    private JButton airplanesDeleteButton = new JButton();
    private JToggleButton airplanesCreateButton = new JToggleButton();
    private JToggleButton airplanesEditButton = new JToggleButton();
    private JTable airplanesTable;
    private JButton airplaneSeatsDeleteButton = new JButton();
    private JToggleButton airplaneSeatsCreateButton = new JToggleButton();
    private JToggleButton airplaneSeatsEditButton = new JToggleButton();
    private JPanel airplanesCardPane;
    private JPanel airplaneSeatsCardPane;
    private JPanel airplaneSeatsEditPane;
    private JPanel airplaneSeatsCreatePane;
    private JPanel airplanesEditPane;
    private JPanel airplanesCreatePane;
    private JTextField airplanesEditName;
    private JButton airplanesEditApply;
    private JTextField airplanesCreateName;
    private JButton airplanesCreateApply;
    private JLabel airplanesEditId;
    private JTextField airplaneSeatsEditSeat;
    private JButton airplaneSeatsEditApply;
    private JLabel airplaneSeatsEditId;
    private JLabel airplaneSeatsEditAirplaneId;
    private JTextField airplaneSeatsCreateSeat;
    private JButton airplaneSeatsCreateApply;
    private JLabel airplaneSeatsCreateAirplaneId;
    private JTable airplaneSeatsTable;
    private JSplitPane airplanesSplitPane;
    private JTable flightsTable;
    private JButton flightsDeleteButton = new JButton();
    private JPanel flightsCardPane;
    private JPanel flightsEditPane;
    private JPanel flightsCreatePane;
    private JTextField flightsEditArrivalDate;
    private JTextField flightsEditDepartureDate;
    private JButton flightsEditApply;
    private JLabel flightsEditId;
    private JTextField flightsCreateArrivalDate;
    private JButton flightsCreateApply;
    private JTextField flightsCreateDepartureDate;
    private JToggleButton flightsCreateButton = new JToggleButton();
    private JToggleButton flightsEditButton = new JToggleButton();
    private JSplitPane flightsSplitPane;
    private JTable ticketsTable;
    private JButton ticketsDeleteButton;
    private JToggleButton ticketsCreateButton;
    private JToggleButton ticketsEditButton;
    private JPanel ticketsCardPane;
    private JPanel ticketsEditPane;
    private JPanel ticketsCreatePane;
    private JComboBox flightsCreateAirplane;
    private JComboBox flightsEditAirplane;
    private JTextField ticketsCreateClientName;
    private JTextField ticketsCreateClientPassport;
    private JComboBox ticketsCreateSeat;
    private JButton ticketsCreateApply;
    private JLabel ticketsCreateFlightId;
    private JTextField ticketsEditClientName;
    private JTextField ticketsEditClientPassport;
    private JComboBox ticketsEditSeat;
    private JButton ticketsEditApply;
    private JLabel ticketsEditId;
    private JLabel ticketsEditFlightId;
    private JComboBox flightsEditDepartureAirport;
    private JComboBox flightsEditArrivalAirport;
    private JComboBox flightsCreateDepartureAirport;
    private JComboBox flightsCreateArrivalAirport;
    private JToggleButton flightsFilterButton;
    private JPanel flightsFilterPane;
    private JButton flightsFilterApply;
    private JComboBox flightsFilterDepartureAirport;
    private JComboBox flightsFilterArrivalAirport;
    private JTextField flightsFilterDepartureDate;
    private JCheckBox flightsFilterDepartureAirportCheckBox;
    private JCheckBox flightsFilterArrivalAirportCheckBox;
    private JCheckBox flightsFilterDepartureDateCheckBox;
    private ButtonGroup ticketsButtonGroup;
    private ButtonGroup flightsButtonGroup;
    private ButtonGroup airplaneSeatsButtonGroup = new ButtonGroup();
    private ButtonGroup airplanesButtonGroup = new ButtonGroup();
    private ButtonGroup airportsButtonGroup = new ButtonGroup();

    private FilteredAirplaneSeatStore airplaneSeatsFilteredStore;
    private FilteredFlightStore flightsFilteredStore;
    private FilteredTicketStore ticketsFilteredStore;

    public MainForm() {
        this.$$$setupUI$$$();

        airplanesSplitPane.setDividerLocation(0.5);
        flightsSplitPane.setDividerLocation(0.7);

        airportsTable.setModel(new AirportTableModel(DatabaseSnapshot.getInstance().getAirports()));
        airplaneSeatsFilteredStore = new FilteredAirplaneSeatStore(DatabaseSnapshot.getInstance().getAirplaneSeats());
        airplaneSeatsTable.setModel(new AirplaneSeatTableModel(airplaneSeatsFilteredStore));
        airplanesTable.setModel(new AirplaneTableModel(DatabaseSnapshot.getInstance().getAirplanes()));
        flightsFilteredStore = new FilteredFlightStore(DatabaseSnapshot.getInstance().getFlights());
        flightsTable.setModel(new FlightTableModel(flightsFilteredStore));
        ticketsFilteredStore = new FilteredTicketStore(DatabaseSnapshot.getInstance().getTickets());
        ticketsTable.setModel(new TicketTableModel(ticketsFilteredStore));

        addAirportComboBox(DatabaseSnapshot.getInstance().getAirports(), flightsFilterDepartureAirport);
        addAirportComboBox(DatabaseSnapshot.getInstance().getAirports(), flightsFilterArrivalAirport);
        addAirportComboBox(DatabaseSnapshot.getInstance().getAirports(), flightsCreateDepartureAirport);
        addAirportComboBox(DatabaseSnapshot.getInstance().getAirports(), flightsCreateArrivalAirport);
        addAirportComboBox(DatabaseSnapshot.getInstance().getAirports(), flightsEditDepartureAirport);
        addAirportComboBox(DatabaseSnapshot.getInstance().getAirports(), flightsEditArrivalAirport);
        addAirplaneComboBox(DatabaseSnapshot.getInstance().getAirplanes(), flightsCreateAirplane);
        addAirplaneComboBox(DatabaseSnapshot.getInstance().getAirplanes(), flightsEditAirplane);
        addAirplaneSeatComboBox(airplaneSeatsFilteredStore, ticketsCreateSeat);
        addAirplaneSeatComboBox(airplaneSeatsFilteredStore, ticketsEditSeat);

        final TableController<AirportInfo> airportsTableController = new TableController<AirportInfo>(
                DatabaseSnapshot.getInstance().getAirports(),
                airportsTable,
                airportsCardPane,
                airportsCreateApply,
                airportsEditApply,
                airportsButtonGroup,
                new JToggleButton[]{airportsCreateButton, airportsEditButton},
                airportsEditButton,
                airportsDeleteButton
        ) {
            public void loadObject(AirportInfo object) {
                airportsEditId.setText(Integer.toString(object.getId()));
                airportsEditShortName.setText(object.getShortName());
                airportsEditFullName.setText(object.getFullName());
            }

            public AirportInfo getCreatedObject() {
                if (checkShortName(airportsCreateShortName.getText())) {
                    return new AirportInfo(
                            airportsCreateShortName.getText(),
                            airportsCreateFullName.getText()
                    );
                } else {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Airport short name must consist of 3 uppercase letters.");
                    return null;
                }
            }

            private boolean checkShortName(String code) {
                for (char ch : code.toCharArray()) {
                    if (ch < 'A' || ch > 'Z') {
                        return false;
                    }
                }
                return code.length() == 3;
            }

            public AirportInfo getUpdatedObject() {
                if (checkShortName(airportsEditShortName.getText())) {
                    return new AirportInfo(
                            Integer.parseInt(airportsEditId.getText()),
                            airportsEditShortName.getText(),
                            airportsEditFullName.getText()
                    );
                } else {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Airport short name must consist of 3 uppercase letters.");
                    return null;
                }
            }
        };

        final TableController<AirplaneSeatInfo> airplaneSeatsTableController = new TableController<AirplaneSeatInfo>(
                airplaneSeatsFilteredStore,
                airplaneSeatsTable,
                airplaneSeatsCardPane,
                airplaneSeatsCreateApply,
                airplaneSeatsEditApply,
                airplaneSeatsButtonGroup,
                new JToggleButton[]{airplaneSeatsCreateButton, airplaneSeatsEditButton},
                airplaneSeatsEditButton,
                airplaneSeatsDeleteButton
        ) {
            public void loadObject(AirplaneSeatInfo object) {
                airplaneSeatsEditId.setText(Integer.toString(object.getId()));
                airplaneSeatsEditAirplaneId.setText(Integer.toString(object.getAirplaneId()));
                airplaneSeatsEditSeat.setText(object.getSeat());
            }

            public AirplaneSeatInfo getCreatedObject() {
                if (checkSeat(airplaneSeatsCreateSeat.getText())) {
                    return new AirplaneSeatInfo(
                            Integer.parseInt(airplaneSeatsCreateAirplaneId.getText()),
                            airplaneSeatsCreateSeat.getText()
                    );
                } else {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Airplane seat must consist of up to 5 characters.");
                    return null;
                }
            }

            private boolean checkSeat(String text) {
                return text.length() <= 5;
            }

            public AirplaneSeatInfo getUpdatedObject() {
                if (checkSeat(airplaneSeatsEditSeat.getText())) {
                    return new AirplaneSeatInfo(
                            Integer.parseInt(airplaneSeatsEditId.getText()),
                            Integer.parseInt(airplaneSeatsEditAirplaneId.getText()),
                            airplaneSeatsEditSeat.getText()
                    );
                } else {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Airplane seat must consist of up to 5 characters.");
                    return null;
                }
            }
        };

        final TableController<AirplaneInfo> airplanesTableController = new TableController<AirplaneInfo>(
                DatabaseSnapshot.getInstance().getAirplanes(),
                airplanesTable,
                airplanesCardPane,
                airplanesCreateApply,
                airplanesEditApply,
                airplanesButtonGroup,
                new JToggleButton[]{airplanesCreateButton, airplanesEditButton},
                airplanesEditButton,
                airplanesDeleteButton
        ) {
            public void loadObject(AirplaneInfo object) {
                airplanesEditId.setText(Integer.toString(object.getId()));
                airplanesEditName.setText(object.getName());
            }

            public AirplaneInfo getCreatedObject() {
                return new AirplaneInfo(airplanesCreateName.getText());
            }

            public AirplaneInfo getUpdatedObject() {
                return new AirplaneInfo(
                        Integer.parseInt(airplanesEditId.getText()),
                        airplanesEditName.getText()
                );
            }

            public void valueChanged(ListSelectionEvent e) {
                super.valueChanged(e);
                AirplaneInfo airplane = getSelectedObject();
                if (airplane == null) {
                    airplaneSeatsTableController.hideCardPane();
                    airplaneSeatsCreateButton.setEnabled(false);
                } else {
                    airplaneSeatsCreateButton.setEnabled(true);
                    airplaneSeatsCreateAirplaneId.setText(Integer.toString(airplane.getId()));
                    airplaneSeatsFilteredStore.setAirplaneId(airplane.getId());
                }
            }
        };

        final TableController<TicketInfo> ticketsTableController = new TableController<TicketInfo>(
                ticketsFilteredStore,
                ticketsTable,
                ticketsCardPane,
                ticketsCreateApply,
                ticketsEditApply,
                ticketsButtonGroup,
                new JToggleButton[]{ticketsCreateButton, ticketsEditButton},
                ticketsEditButton,
                ticketsDeleteButton
        ) {
            public void loadObject(TicketInfo object) {
                ticketsEditId.setText(Integer.toString(object.getId()));
                ticketsEditFlightId.setText(Integer.toString(object.getFlightId()));
                ticketsEditSeat.setSelectedItem(object.getSeat());
                ticketsEditClientName.setText(object.getClientName());
                ticketsEditClientPassport.setText(object.getClientPassport());
            }

            public TicketInfo getCreatedObject() {
                return new TicketInfo(
                        Integer.parseInt(ticketsCreateFlightId.getText()),
                        ((AirplaneSeatInfo) ticketsCreateSeat.getSelectedItem()).getId(),
                        ticketsCreateClientName.getText(),
                        ticketsCreateClientPassport.getText()
                );
            }

            public TicketInfo getUpdatedObject() {
                return new TicketInfo(
                        Integer.parseInt(ticketsEditId.getText()),
                        Integer.parseInt(ticketsEditFlightId.getText()),
                        ((AirplaneSeatInfo) ticketsEditSeat.getSelectedItem()).getId(),
                        ticketsEditClientName.getText(),
                        ticketsEditClientPassport.getText()
                );
            }
        };

        final TableController<FlightInfo> flightsTableController = new TableController<FlightInfo>(
                DatabaseSnapshot.getInstance().getFlights(),
                flightsTable,
                flightsCardPane,
                flightsCreateApply,
                flightsEditApply,
                flightsButtonGroup,
                new JToggleButton[]{flightsFilterButton, flightsCreateButton, flightsEditButton},
                flightsEditButton,
                flightsDeleteButton
        ) {
            private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            public void loadObject(FlightInfo object) {
                flightsEditId.setText(Integer.toString(object.getId()));
                flightsEditDepartureAirport.setSelectedItem(object.getDepartureAirport());
                flightsEditDepartureDate.setText(dateFormat.format(object.getDepartureDate()));
                flightsEditArrivalAirport.setSelectedItem(object.getArrivalAirport());
                flightsEditArrivalDate.setText(dateFormat.format(object.getArrivalDate()));
                flightsEditAirplane.setSelectedItem(object.getAirplane());
            }

            public FlightInfo getCreatedObject() {
                try {
                    return new FlightInfo(
                            ((AirportInfo) flightsCreateDepartureAirport.getSelectedItem()).getId(),
                            dateFormat.parse(flightsCreateDepartureDate.getText()),
                            ((AirportInfo) flightsCreateArrivalAirport.getSelectedItem()).getId(),
                            dateFormat.parse(flightsCreateArrivalDate.getText()),
                            ((AirplaneInfo) flightsCreateAirplane.getSelectedItem()).getId()
                    );
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Date must be supplied in the following format: \"dd.MM.yyyy hh:mm\"");
                    return null;
                }
            }

            public FlightInfo getUpdatedObject() {
                try {
                    return new FlightInfo(
                            Integer.parseInt(flightsEditId.getText()),
                            ((AirportInfo) flightsEditDepartureAirport.getSelectedItem()).getId(),
                            dateFormat.parse(flightsEditDepartureDate.getText()),
                            ((AirportInfo) flightsEditArrivalAirport.getSelectedItem()).getId(),
                            dateFormat.parse(flightsEditArrivalDate.getText()),
                            ((AirplaneInfo) flightsEditAirplane.getSelectedItem()).getId()
                    );
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Date must be supplied in the following format: \"dd.MM.yyyy hh:mm\"");
                    return null;
                }
            }

            public void valueChanged(ListSelectionEvent e) {
                super.valueChanged(e);
                FlightInfo flight = getSelectedObject();
                if (flight == null) {
                    ticketsTableController.hideCardPane();
                    ticketsCreateButton.setEnabled(false);
                } else {
                    ticketsCreateButton.setEnabled(true);
                    ticketsCreateFlightId.setText(Integer.toString(flight.getId()));
                    ticketsFilteredStore.setFlightId(flight.getId());
//                    DatabaseSnapshot.getInstance().reloadTickets();
                    airplaneSeatsFilteredStore.setAirplaneId(flight.getAirplaneId());
//                    DatabaseSnapshot.getInstance().reloadAirplaneSeats();
                }
            }
        };
        flightsFilterApply.addActionListener(new ActionListener() {
            private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            public void actionPerformed(ActionEvent ae) {
                try {
                    flightsFilteredStore.setDepartureDate(
                            flightsFilterDepartureDateCheckBox.isSelected() ?
                                    dateFormat.parse(flightsFilterDepartureDate.getText()) :
                                    null
                    );
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(MainForm.this.$$$getRootComponent$$$(), "Date must be supplied in the following format: \"dd.MM.yyyy\"");
                    return;
                }
                flightsFilteredStore.setDepartureAirportId(
                        flightsFilterDepartureAirportCheckBox.isSelected() ?
                                ((AirportInfo) flightsFilterDepartureAirport.getSelectedItem()).getId() :
                                0
                );
                flightsFilteredStore.setArrivalAirportId(
                        flightsFilterArrivalAirportCheckBox.isSelected() ?
                                ((AirportInfo) flightsFilterArrivalAirport.getSelectedItem()).getId() :
                                0
                );
            }
        });
    }

    public void addAirplaneComboBox(final AbstractObjectStore<AirplaneInfo> store, final JComboBox comboBox) {
        comboBox.setRenderer(new ListCellRenderer() {
            private final JLabel label = new JLabel();
            private final Color[] backgroundColors = new Color[]{label.getBackground(), Color.LIGHT_GRAY};

            {
                label.setOpaque(true);
            }

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                AirplaneInfo object = (AirplaneInfo) value;
                label.setText(object == null ? null : object.getName());
                int colorId = isSelected ? 1 : 0;
                label.setBackground(backgroundColors[colorId]);
                return label;
            }
        });
        store.addObjectStoreListener(new ObjectStoreListener<AirplaneInfo>() {
            public void updated(Collection<AirplaneInfo> airplaneInfos) {
                AbstractObjectInfo object = (AbstractObjectInfo) comboBox.getSelectedItem();
                comboBox.removeAllItems();
                for (AbstractObjectInfo airplane : store.getObjects()) {
                    if (object == null || object.getId() == airplane.getId()) {
                        object = airplane;
                    }
                    comboBox.addItem(airplane);
                }
                comboBox.setSelectedItem(object);
            }
        });
    }

    public void addAirplaneSeatComboBox(final AbstractObjectStore<AirplaneSeatInfo> store, final JComboBox comboBox) {
        comboBox.setRenderer(new ListCellRenderer() {
            private final JLabel label = new JLabel();
            private final Color[] backgroundColors = new Color[]{label.getBackground(), Color.LIGHT_GRAY};
            private final Color[] foregroundColors = new Color[]{label.getForeground(), Color.RED};

            {
                label.setOpaque(true);
            }

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                AirplaneSeatInfo object = (AirplaneSeatInfo) value;
                label.setText(object == null ? null : object.getSeat());
                label.setBackground(backgroundColors[isSelected ? 1 : 0]);
                label.setForeground(foregroundColors[contains(object) ? 1 : 0]);
                return label;
            }

            private boolean contains(AirplaneSeatInfo seat) {
                if (seat == null) {
                    return false;
                }
                for (TicketInfo ticket : ticketsFilteredStore.getObjects()) {
                    if (ticket.getSeatId() == seat.getId()) {
                        return true;
                    }
                }
                return false;
            }
        });
        store.addObjectStoreListener(new ObjectStoreListener<AirplaneSeatInfo>() {
            public void updated(Collection<AirplaneSeatInfo> airplaneInfos) {
                AbstractObjectInfo object = (AbstractObjectInfo) comboBox.getSelectedItem();
                comboBox.removeAllItems();
                for (AbstractObjectInfo airplane : store.getObjects()) {
                    if (object == null || object.getId() == airplane.getId()) {
                        object = airplane;
                    }
                    comboBox.addItem(airplane);
                }
                comboBox.setSelectedItem(object);
            }
        });
    }

    public void addAirportComboBox(final AbstractObjectStore<AirportInfo> store, final JComboBox comboBox) {
        comboBox.setRenderer(new ListCellRenderer() {
            private final JLabel label = new JLabel();
            private final Color[] backgroundColors = new Color[]{label.getBackground(), Color.LIGHT_GRAY};

            {
                label.setOpaque(true);
            }

            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                AirportInfo object = (AirportInfo) value;
                label.setText(object == null ? null : object.getShortName() + " (" + object.getFullName() + ")");
                int colorId = isSelected ? 1 : 0;
                label.setBackground(backgroundColors[colorId]);
                return label;
            }
        });
        store.addObjectStoreListener(new ObjectStoreListener<AirportInfo>() {
            public void updated(Collection<AirportInfo> airportInfos) {
                AbstractObjectInfo object = (AbstractObjectInfo) comboBox.getSelectedItem();
                comboBox.removeAllItems();
                for (AbstractObjectInfo airport : store.getObjects()) {
                    if (object == null || object.getId() == airport.getId()) {
                        object = airport;
                    }
                    comboBox.addItem(airport);
                }
                comboBox.setSelectedItem(object);
            }
        });
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPane = new JPanel();
        rootPane.setLayout(new BorderLayout(0, 0));
        tabbedPane1 = new JTabbedPane();
        rootPane.add(tabbedPane1, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Flights", panel1);
        flightsSplitPane = new JSplitPane();
        flightsSplitPane.setDividerLocation(250);
        flightsSplitPane.setDividerSize(8);
        flightsSplitPane.setOneTouchExpandable(true);
        panel1.add(flightsSplitPane, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        flightsSplitPane.setLeftComponent(panel2);
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        panel2.add(toolBar1, BorderLayout.SOUTH);
        flightsFilterButton = new JToggleButton();
        flightsFilterButton.setText("Filter");
        toolBar1.add(flightsFilterButton);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, BorderLayout.CENTER);
        flightsCardPane = new JPanel();
        flightsCardPane.setLayout(new CardLayout(0, 0));
        panel3.add(flightsCardPane, BorderLayout.SOUTH);
        flightsCardPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        flightsEditPane = new JPanel();
        flightsEditPane.setLayout(new GridBagLayout());
        flightsCardPane.add(flightsEditPane, "Edit");
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        flightsEditPane.add(spacer1, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Id");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Departure airport");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(label2, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Departure date");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("Arrival airport");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Arrival date");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(label5, gbc);
        flightsEditId = new JLabel();
        flightsEditId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(flightsEditId, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        flightsEditPane.add(spacer2, gbc);
        flightsEditDepartureDate = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsEditPane.add(flightsEditDepartureDate, gbc);
        flightsEditApply = new JButton();
        flightsEditApply.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 5;
        flightsEditPane.add(flightsEditApply, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Airplane");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        flightsEditPane.add(label6, gbc);
        flightsEditArrivalDate = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsEditPane.add(flightsEditArrivalDate, gbc);
        flightsEditAirplane = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsEditPane.add(flightsEditAirplane, gbc);
        flightsEditDepartureAirport = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsEditPane.add(flightsEditDepartureAirport, gbc);
        flightsEditArrivalAirport = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsEditPane.add(flightsEditArrivalAirport, gbc);
        flightsCreatePane = new JPanel();
        flightsCreatePane.setLayout(new GridBagLayout());
        flightsCardPane.add(flightsCreatePane, "Create");
        final JLabel label7 = new JLabel();
        label7.setText("Departure airport");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        flightsCreatePane.add(label7, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Departure date");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        flightsCreatePane.add(label8, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Arrival airport");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        flightsCreatePane.add(label9, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("Arrival date");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        flightsCreatePane.add(label10, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Airplane");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        flightsCreatePane.add(label11, gbc);
        flightsCreateDepartureDate = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsCreatePane.add(flightsCreateDepartureDate, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        flightsCreatePane.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        flightsCreatePane.add(spacer4, gbc);
        flightsCreateArrivalDate = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsCreatePane.add(flightsCreateArrivalDate, gbc);
        flightsCreateApply = new JButton();
        flightsCreateApply.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        flightsCreatePane.add(flightsCreateApply, gbc);
        flightsCreateAirplane = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsCreatePane.add(flightsCreateAirplane, gbc);
        flightsCreateDepartureAirport = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsCreatePane.add(flightsCreateDepartureAirport, gbc);
        flightsCreateArrivalAirport = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsCreatePane.add(flightsCreateArrivalAirport, gbc);
        flightsFilterPane = new JPanel();
        flightsFilterPane.setLayout(new GridBagLayout());
        flightsCardPane.add(flightsFilterPane, "Filter");
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        flightsFilterPane.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        flightsFilterPane.add(spacer6, gbc);
        flightsFilterApply = new JButton();
        flightsFilterApply.setText("Filter");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 6;
        flightsFilterPane.add(flightsFilterApply, gbc);
        flightsFilterDepartureAirport = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsFilterPane.add(flightsFilterDepartureAirport, gbc);
        flightsFilterArrivalAirport = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsFilterPane.add(flightsFilterArrivalAirport, gbc);
        flightsFilterDepartureDate = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        flightsFilterPane.add(flightsFilterDepartureDate, gbc);
        flightsFilterDepartureAirportCheckBox = new JCheckBox();
        flightsFilterDepartureAirportCheckBox.setText("Departure airport");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        flightsFilterPane.add(flightsFilterDepartureAirportCheckBox, gbc);
        flightsFilterArrivalAirportCheckBox = new JCheckBox();
        flightsFilterArrivalAirportCheckBox.setText("Arrival airport");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        flightsFilterPane.add(flightsFilterArrivalAirportCheckBox, gbc);
        flightsFilterDepartureDateCheckBox = new JCheckBox();
        flightsFilterDepartureDateCheckBox.setText("Departure date");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        flightsFilterPane.add(flightsFilterDepartureDateCheckBox, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, BorderLayout.CENTER);
        flightsTable = new JTable();
        scrollPane1.setViewportView(flightsTable);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        flightsSplitPane.setRightComponent(panel4);
        final JToolBar toolBar2 = new JToolBar();
        toolBar2.setFloatable(false);
        panel4.add(toolBar2, BorderLayout.SOUTH);
        ticketsCreateButton = new JToggleButton();
        ticketsCreateButton.setEnabled(false);
        ticketsCreateButton.setText("Create");
        toolBar2.add(ticketsCreateButton);
        ticketsEditButton = new JToggleButton();
        ticketsEditButton.setEnabled(false);
        ticketsEditButton.setText("Edit");
        toolBar2.add(ticketsEditButton);
        ticketsDeleteButton = new JButton();
        ticketsDeleteButton.setEnabled(false);
        ticketsDeleteButton.setText("Delete");
        toolBar2.add(ticketsDeleteButton);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, BorderLayout.CENTER);
        ticketsCardPane = new JPanel();
        ticketsCardPane.setLayout(new CardLayout(0, 0));
        panel5.add(ticketsCardPane, BorderLayout.SOUTH);
        ticketsCardPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        ticketsEditPane = new JPanel();
        ticketsEditPane.setLayout(new GridBagLayout());
        ticketsCardPane.add(ticketsEditPane, "Edit");
        final JLabel label12 = new JLabel();
        label12.setText("Id");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(label12, gbc);
        final JLabel label13 = new JLabel();
        label13.setText("Flight id");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(label13, gbc);
        final JLabel label14 = new JLabel();
        label14.setText("Seat");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(label14, gbc);
        final JLabel label15 = new JLabel();
        label15.setText("Client name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(label15, gbc);
        final JLabel label16 = new JLabel();
        label16.setText("Client passport");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(label16, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        ticketsEditPane.add(spacer7, gbc);
        ticketsEditId = new JLabel();
        ticketsEditId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(ticketsEditId, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        ticketsEditPane.add(spacer8, gbc);
        ticketsEditFlightId = new JLabel();
        ticketsEditFlightId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsEditPane.add(ticketsEditFlightId, gbc);
        ticketsEditClientName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ticketsEditPane.add(ticketsEditClientName, gbc);
        ticketsEditClientPassport = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ticketsEditPane.add(ticketsEditClientPassport, gbc);
        ticketsEditSeat = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ticketsEditPane.add(ticketsEditSeat, gbc);
        ticketsEditApply = new JButton();
        ticketsEditApply.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        ticketsEditPane.add(ticketsEditApply, gbc);
        ticketsCreatePane = new JPanel();
        ticketsCreatePane.setLayout(new GridBagLayout());
        ticketsCardPane.add(ticketsCreatePane, "Create");
        final JLabel label17 = new JLabel();
        label17.setText("Flight id");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsCreatePane.add(label17, gbc);
        final JLabel label18 = new JLabel();
        label18.setText("Client name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsCreatePane.add(label18, gbc);
        final JLabel label19 = new JLabel();
        label19.setText("Client passport");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsCreatePane.add(label19, gbc);
        ticketsCreateClientName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ticketsCreatePane.add(ticketsCreateClientName, gbc);
        ticketsCreateClientPassport = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ticketsCreatePane.add(ticketsCreateClientPassport, gbc);
        ticketsCreateFlightId = new JLabel();
        ticketsCreateFlightId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsCreatePane.add(ticketsCreateFlightId, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        ticketsCreatePane.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        ticketsCreatePane.add(spacer10, gbc);
        ticketsCreateApply = new JButton();
        ticketsCreateApply.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        ticketsCreatePane.add(ticketsCreateApply, gbc);
        final JLabel label20 = new JLabel();
        label20.setText("Seat");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        ticketsCreatePane.add(label20, gbc);
        ticketsCreateSeat = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ticketsCreatePane.add(ticketsCreateSeat, gbc);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, BorderLayout.CENTER);
        ticketsTable = new JTable();
        scrollPane2.setViewportView(ticketsTable);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Airplanes", panel6);
        airplanesSplitPane = new JSplitPane();
        airplanesSplitPane.setDividerLocation(250);
        airplanesSplitPane.setDividerSize(8);
        airplanesSplitPane.setOneTouchExpandable(true);
        airplanesSplitPane.setResizeWeight(0.5);
        panel6.add(airplanesSplitPane, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        airplanesSplitPane.setRightComponent(panel7);
        final JToolBar toolBar3 = new JToolBar();
        toolBar3.setFloatable(false);
        panel7.add(toolBar3, BorderLayout.SOUTH);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel7.add(panel8, BorderLayout.CENTER);
        airplaneSeatsCardPane = new JPanel();
        airplaneSeatsCardPane.setLayout(new CardLayout(0, 0));
        panel8.add(airplaneSeatsCardPane, BorderLayout.SOUTH);
        airplaneSeatsCardPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        airplaneSeatsEditPane = new JPanel();
        airplaneSeatsEditPane.setLayout(new GridBagLayout());
        airplaneSeatsCardPane.add(airplaneSeatsEditPane, "Edit");
        final JLabel label21 = new JLabel();
        label21.setText("Id");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsEditPane.add(label21, gbc);
        final JLabel label22 = new JLabel();
        label22.setText("Airplane id");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsEditPane.add(label22, gbc);
        final JLabel label23 = new JLabel();
        label23.setText("Seat");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsEditPane.add(label23, gbc);
        airplaneSeatsEditAirplaneId = new JLabel();
        airplaneSeatsEditAirplaneId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsEditPane.add(airplaneSeatsEditAirplaneId, gbc);
        airplaneSeatsEditId = new JLabel();
        airplaneSeatsEditId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsEditPane.add(airplaneSeatsEditId, gbc);
        airplaneSeatsEditSeat = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airplaneSeatsEditPane.add(airplaneSeatsEditSeat, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplaneSeatsEditPane.add(spacer11, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplaneSeatsEditPane.add(spacer12, gbc);
        airplaneSeatsEditApply = new JButton();
        airplaneSeatsEditApply.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        airplaneSeatsEditPane.add(airplaneSeatsEditApply, gbc);
        airplaneSeatsCreatePane = new JPanel();
        airplaneSeatsCreatePane.setLayout(new GridBagLayout());
        airplaneSeatsCardPane.add(airplaneSeatsCreatePane, "Create");
        final JLabel label24 = new JLabel();
        label24.setText("Airplane id");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsCreatePane.add(label24, gbc);
        final JLabel label25 = new JLabel();
        label25.setText("Seat");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsCreatePane.add(label25, gbc);
        airplaneSeatsCreateAirplaneId = new JLabel();
        airplaneSeatsCreateAirplaneId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airplaneSeatsCreatePane.add(airplaneSeatsCreateAirplaneId, gbc);
        airplaneSeatsCreateSeat = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airplaneSeatsCreatePane.add(airplaneSeatsCreateSeat, gbc);
        airplaneSeatsCreateApply = new JButton();
        airplaneSeatsCreateApply.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        airplaneSeatsCreatePane.add(airplaneSeatsCreateApply, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplaneSeatsCreatePane.add(spacer13, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplaneSeatsCreatePane.add(spacer14, gbc);
        final JScrollPane scrollPane3 = new JScrollPane();
        panel8.add(scrollPane3, BorderLayout.CENTER);
        airplaneSeatsTable = new JTable();
        scrollPane3.setViewportView(airplaneSeatsTable);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        airplanesSplitPane.setLeftComponent(panel9);
        final JToolBar toolBar4 = new JToolBar();
        toolBar4.setFloatable(false);
        panel9.add(toolBar4, BorderLayout.SOUTH);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel9.add(panel10, BorderLayout.CENTER);
        airplanesCardPane = new JPanel();
        airplanesCardPane.setLayout(new CardLayout(0, 0));
        panel10.add(airplanesCardPane, BorderLayout.SOUTH);
        airplanesCardPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        airplanesEditPane = new JPanel();
        airplanesEditPane.setLayout(new GridBagLayout());
        airplanesCardPane.add(airplanesEditPane, "Edit");
        final JLabel label26 = new JLabel();
        label26.setText("Id");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        airplanesEditPane.add(label26, gbc);
        final JLabel label27 = new JLabel();
        label27.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airplanesEditPane.add(label27, gbc);
        airplanesEditId = new JLabel();
        airplanesEditId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        airplanesEditPane.add(airplanesEditId, gbc);
        airplanesEditName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airplanesEditPane.add(airplanesEditName, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplanesEditPane.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplanesEditPane.add(spacer16, gbc);
        airplanesEditApply = new JButton();
        airplanesEditApply.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        airplanesEditPane.add(airplanesEditApply, gbc);
        airplanesCreatePane = new JPanel();
        airplanesCreatePane.setLayout(new GridBagLayout());
        airplanesCardPane.add(airplanesCreatePane, "Create");
        final JLabel label28 = new JLabel();
        label28.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airplanesCreatePane.add(label28, gbc);
        airplanesCreateName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airplanesCreatePane.add(airplanesCreateName, gbc);
        final JPanel spacer17 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplanesCreatePane.add(spacer17, gbc);
        airplanesCreateApply = new JButton();
        airplanesCreateApply.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        airplanesCreatePane.add(airplanesCreateApply, gbc);
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airplanesCreatePane.add(spacer18, gbc);
        final JScrollPane scrollPane4 = new JScrollPane();
        panel10.add(scrollPane4, BorderLayout.CENTER);
        airplanesTable = new JTable();
        scrollPane4.setViewportView(airplanesTable);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("Airports", panel11);
        final JToolBar toolBar5 = new JToolBar();
        toolBar5.setBorderPainted(true);
        toolBar5.setEnabled(true);
        toolBar5.setFloatable(false);
        panel11.add(toolBar5, BorderLayout.SOUTH);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new BorderLayout(0, 0));
        panel11.add(panel12, BorderLayout.CENTER);
        airportsCardPane = new JPanel();
        airportsCardPane.setLayout(new CardLayout(0, 0));
        panel12.add(airportsCardPane, BorderLayout.SOUTH);
        airportsCardPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        airportsEditPane = new JPanel();
        airportsEditPane.setLayout(new GridBagLayout());
        airportsCardPane.add(airportsEditPane, "Edit");
        final JLabel label29 = new JLabel();
        label29.setText("Id");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        airportsEditPane.add(label29, gbc);
        final JLabel label30 = new JLabel();
        label30.setText("Short name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airportsEditPane.add(label30, gbc);
        final JLabel label31 = new JLabel();
        label31.setText("Full name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        airportsEditPane.add(label31, gbc);
        airportsEditId = new JLabel();
        airportsEditId.setText("##");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        airportsEditPane.add(airportsEditId, gbc);
        airportsEditShortName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airportsEditPane.add(airportsEditShortName, gbc);
        airportsEditFullName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airportsEditPane.add(airportsEditFullName, gbc);
        airportsEditApply = new JButton();
        airportsEditApply.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 9;
        airportsEditPane.add(airportsEditApply, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airportsEditPane.add(spacer19, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airportsEditPane.add(spacer20, gbc);
        airportsCreatePane = new JPanel();
        airportsCreatePane.setLayout(new GridBagLayout());
        airportsCardPane.add(airportsCreatePane, "Create");
        final JLabel label32 = new JLabel();
        label32.setText("Short name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        airportsCreatePane.add(label32, gbc);
        final JLabel label33 = new JLabel();
        label33.setText("Full name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        airportsCreatePane.add(label33, gbc);
        airportsCreateShortName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airportsCreatePane.add(airportsCreateShortName, gbc);
        airportsCreateFullName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        airportsCreatePane.add(airportsCreateFullName, gbc);
        final JPanel spacer21 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airportsCreatePane.add(spacer21, gbc);
        airportsCreateApply = new JButton();
        airportsCreateApply.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 9;
        airportsCreatePane.add(airportsCreateApply, gbc);
        final JPanel spacer22 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        airportsCreatePane.add(spacer22, gbc);
        final JScrollPane scrollPane5 = new JScrollPane();
        panel12.add(scrollPane5, BorderLayout.CENTER);
        airportsTable = new JTable();
        airportsTable.setAutoCreateRowSorter(false);
        scrollPane5.setViewportView(airportsTable);
        flightsButtonGroup = new ButtonGroup();
        flightsButtonGroup.add(flightsFilterButton);
        ticketsButtonGroup = new ButtonGroup();
        ticketsButtonGroup.add(ticketsCreateButton);
        ticketsButtonGroup.add(ticketsEditButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPane;
    }
}
