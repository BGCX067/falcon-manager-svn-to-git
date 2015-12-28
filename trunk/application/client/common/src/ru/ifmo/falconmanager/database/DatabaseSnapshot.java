package ru.ifmo.falconmanager.database;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Any;
import org.omg.BiDirPolicy.BOTH;
import org.omg.BiDirPolicy.BIDIRECTIONAL_POLICY_TYPE;
import ru.ifmo.falconmanager.idl.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Dmitry Paraschenko
 * @version 16.05.2008 15:09:05
 */
public class DatabaseSnapshot extends DatabaseListenerPOA {
    private static final DatabaseSnapshot INSTANCE = new DatabaseSnapshot();

    private Logger log = Logger.getLogger(DatabaseSnapshot.class);
    private DatabaseOperations databaseOps;

    private AirplaneStore airplanes;
    private AirplaneSeatStore airplaneSeats;
    private AirportStore airports;
    private FlightStore flights;
    private TicketStore tickets;

    private DatabaseSnapshot() {
        configureLog4j();
        org.omg.CORBA.ORB orb = configureCorba();
        log.info("ORB class: " + orb.getClass().toString());

        airplanes = new AirplaneStore();
        airplaneSeats = new AirplaneSeatStore();
        airports = new AirportStore();
        flights = new FlightStore();
        tickets = new TicketStore();

        databaseOps = DatabaseHelper.bind(orb, "/editor_falcon_agent_poa", "Database".getBytes());
        
        try {
//            Any bidirPolicy = orb.create_any();
//            bidirPolicy.insert_short(BOTH.value);
//            Policy[] policies = {orb.create_policy(BIDIRECTIONAL_POLICY_TYPE.value, bidirPolicy)};
            Policy[] policies = {};

            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            POA callbackPOA = rootPOA.create_POA("bidir", rootPOA.the_POAManager(), policies);
            callbackPOA.activate_object(this);
            callbackPOA.the_POAManager().activate();

            databaseOps.addDatabaseListener(DatabaseListenerHelper.narrow(callbackPOA.servant_to_reference(this)));
        } catch (Exception e) {
            log.error("Failed to init CORBA. Message: [" + e.getMessage() + "]");
            log.error("Application will now terminate");
            System.exit(1);
        }
    }

    public static DatabaseSnapshot getInstance() {
        return INSTANCE;
    }
    
    private org.omg.CORBA.ORB configureCorba() {
        Properties p = new Properties();
        p.put("org.omg.CORBA.ORBClass", "com.inprise.vbroker.orb.ORB");
        p.put("org.omg.CORBA.ORBSingletonClass", "com.inprise.vbroker.orb.ORBSingleton");
        p.put("javax.rmi.CORBA.StubClass", "com.inprise.vbroker.rmi.CORBA.StubImpl");
        p.put("javax.rmi.CORBA.UtilClass", "com.inprise.vbroker.rmi.CORBA.UtilImpl");
        p.put("javax.rmi.CORBA.PortableRemoteObjectClass", "com.inprise.vbroker.rmi.CORBA.PortableRemoteObjectImpl");
        return org.omg.CORBA.ORB.init(new String[0], null);
//        return org.omg.CORBA.ORB.init(new String[0], p);
    }

    /**
     * Configures log4j system.
     *
     * Config is loaded from external file. If DatabaseSnapshot fails to load config,
     * then BasicConfigurator is used
     */
    private void configureLog4j() {
        try {
            File f = new File("log4j.properties");
            Properties p = new Properties();
            p.load(new FileInputStream(f));
            PropertyConfigurator.configure(p);
            log.debug("Log4j system successfully configured from property file");
        } catch (Exception e) {
            BasicConfigurator.configure();
            log.error("Error loading log4j properties file!" +
                    "\n Message: [" + e.getMessage() + "] " +
                    "\n BasicCofigurator used");
        }
        log.info("Logging system initialized");
    }


    public void reloadAll() {
        reloadAirplanes();
        reloadAirplaneSeats();
        reloadAirports();
        reloadFlights();
        reloadTickets();
    }
    
    public AirplaneStore getAirplanes() {
        return airplanes;
    }

    public AirplaneSeatStore getAirplaneSeats() {
        return airplaneSeats;
    }

    public AirportStore getAirports() {
        return airports;
    }

    public FlightStore getFlights() {
        return flights;
    }

    public TicketStore getTickets() {
        return tickets;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("======= DatabaseSnapshot =======\n");
        sb.append("===== Airplanes =====\n");
        sb.append(airplanes);
        sb.append("===== AirplaneSeats =====\n");
        sb.append(airplaneSeats);
        sb.append("===== Airports =====\n");
        sb.append(airports);
        sb.append("===== Flights =====\n");
        sb.append(flights);
        sb.append("========================\n");
        return sb.toString();
    }

    public void reloadAirplanes() {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Loading airplanes...");
                airplanes.applyAll(databaseOps.getAllAirplanes());
                log.debug("Airplanes loaded");
            }
        }).start();
    }

    public void addAirplane(final AirplaneInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Adding airplane " + object + "...");
                if (databaseOps.addAirplane(object.getName())) {
                    log.debug("Airplane #" + object.getId() + " added");
                } else {
                    log.debug("Airplane #" + object.getId() + " not added");
                    JOptionPane.showMessageDialog(null, "Airplane not added.");
                }
                reloadAirplanes();
            }
        }).start();
    }

    public void updateAirplane(final AirplaneInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Updating airplane " + object + "...");
                if (databaseOps.updateAirplane(object.getId(), object.getName())) {
                    log.debug("Airplane #" + object.getId() + " updated");
                } else {
                    log.debug("Airplane #" + object.getId() + " not updated");
                    JOptionPane.showMessageDialog(null, "Airplane not updated.");
                }
                reloadAirplanes();
            }
        }).start();
    }

    public void deleteAirplane(final int id) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Deleting airplane #" + id + "...");
                if (databaseOps.deleteAirplane(id)) {
                    log.debug("Airplane #" + id + " deleted");
                } else {
                    log.debug("Airplane #" + id + " not deleted");
                    JOptionPane.showMessageDialog(null, "Airplane not deleted.");
                }
                reloadAirplanes();
            }
        }).start();
    }

    public void reloadAirplaneSeats() {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Loading airplane seats...");
                airplaneSeats.applyAll(databaseOps.getAllAirplaneSeats());
                log.debug("Airplane seats loaded");
            }
        }).start();
    }

//    public void reloadAirplaneSeatsOfAirplane() {
//        reloadAirplaneSeatsOfAirplane(selectedAirplaneId);
//    }

//    public void reloadAirplaneSeatsOfAirplane(final int airplaneId) {
//        selectedAirplaneId = airplaneId;
//        new Thread(new Runnable() {
//            public void run() {
//                log.debug("Loading airplane seats of airplane #" + airplaneId + "...");
//                Result result = databaseOps.getAllAirplaneSeatsOfAirplane(airplaneId);
//                airplaneSeats.applyAll(result.getAirplaneSeats());
//                result._release();
//                log.debug("Airplane seats of airplane #" + airplaneId + " loaded");
//            }
//        }).start();
//    }
    
    public void addAirplaneSeat(final AirplaneSeatInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Adding airplane seat " + object + "...");
                if (databaseOps.addAirplaneSeat(object.getAirplaneId(), object.getSeat())) {
                    log.debug("Airplane seat #" + object.getId() + " added");
                } else {
                    log.debug("Airplane seat #" + object.getId() + " not added");
                    JOptionPane.showMessageDialog(null, "Airplane seat not added.");
                }
                reloadAirplaneSeats();
            }
        }).start();
    }

    public void updateAirplaneSeat(final AirplaneSeatInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Updating airplane seat " + object + "...");
                if (databaseOps.updateAirplaneSeat(object.getId(), object.getAirplaneId(), object.getSeat())) {
                    log.debug("Airplane seat #" + object.getId() + " updated");
                } else {
                    log.debug("Airplane seat #" + object.getId() + " not updated");
                    JOptionPane.showMessageDialog(null, "Airplane seat not updated.");
                }
                reloadAirplaneSeats();
            }
        }).start();
    }

    public void deleteAirplaneSeat(final int id) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Deleting airplane seat #" + id + "...");
                if (databaseOps.deleteAirplaneSeat(id)) {
                    log.debug("Airplane seat #" + id + " deleted");
                } else {
                    log.debug("Airplane seat #" + id + " not deleted");
                    JOptionPane.showMessageDialog(null, "Airplane seat not deleted.");
                }
                reloadAirplaneSeats();
            }
        }).start();
    }

    public void reloadAirports() {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Loading airports...");
                airports.applyAll(databaseOps.getAllAirports());
                log.debug("Airports loaded");
            }
        }).start();
    }

    public void addAirport(final AirportInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Adding airport " + object + "...");
                if (databaseOps.addAirport(object.getShortName(), object.getFullName())) {
                    log.debug("Airport #" + object.getId() + " added");
                } else {
                    log.debug("Airport #" + object.getId() + " not added");
                    JOptionPane.showMessageDialog(null, "Airport not added.");
                }
                reloadAirports();
            }
        }).start();
    }

    public void updateAirport(final AirportInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Updating airport " + object + "...");
                if (databaseOps.updateAirport(object.getId(), object.getShortName(), object.getFullName())) {
                    log.debug("Airport #" + object.getId() + " updated");
                } else {
                    log.debug("Airport #" + object.getId() + " not updated");
                    JOptionPane.showMessageDialog(null, "Airport not updated.");
                }
                reloadAirports();
            }
        }).start();
    }

    public void deleteAirport(final int id) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Deleting airport #" + id + "...");
                if (databaseOps.deleteAirport(id)) {
                    log.debug("Airport #" + id + " deleted");
                } else {
                    log.debug("Airport #" + id + " not deleted");
                    JOptionPane.showMessageDialog(null, "Airport not deleted.");
                }
                reloadAirports();
            }
        }).start();
    }

    public void reloadFlights() {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Loading flights...");
                flights.applyAll(databaseOps.getAllFlights());
                log.debug("Flights loaded");
            }
        }).start();
    }

    public void addFlight(final FlightInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Adding flight " + object + "...");
                if (databaseOps.addFlight(
                        object.getDepartureAirportId(),
                        FlightInfo.formatDate(object.getDepartureDate()),
                        object.getArrivalAirportId(),
                        FlightInfo.formatDate(object.getArrivalDate()),
                        object.getAirplaneId())
                ) {
                    log.debug("Flight #" + object.getId() + " added");
                } else {
                    log.debug("Flight #" + object.getId() + " not added");
                    JOptionPane.showMessageDialog(null, "Flight not added.");
                }
                reloadFlights();
            }
        }).start();
    }

    public void updateFlight(final FlightInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Updating flight " + object + "...");
                if (databaseOps.updateFlight(
                        object.getId(),
                        object.getDepartureAirportId(),
                        FlightInfo.formatDate(object.getDepartureDate()),
                        object.getArrivalAirportId(),
                        FlightInfo.formatDate(object.getArrivalDate()),
                        object.getAirplaneId())
                ) {
                    log.debug("Flight #" + object.getId() + " updated");
                } else {
                    log.debug("Flight #" + object.getId() + " not updated");
                    JOptionPane.showMessageDialog(null, "Flight not updated.");
                }
                reloadFlights();
            }
        }).start();
    }

    public void deleteFlight(final int id) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Deleting flight #" + id + "...");
                if (databaseOps.deleteFlight(id)) {
                    log.debug("Flight #" + id + " deleted");
                } else {
                    log.debug("Flight #" + id + " not deleted");
                    JOptionPane.showMessageDialog(null, "Flight not deleted.");
                }
                reloadFlights();
            }
        }).start();
    }

    public void reloadTickets() {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Loading tickets...");
                tickets.applyAll(databaseOps.getAllTickets());
                log.debug("Tickets loaded");
            }
        }).start();
    }

//    public void reloadTicketsOfFlight() {
//        reloadTicketsOfFlight(selectedFlightId);
//    }

//    public void reloadTicketsOfFlight(final int flightId) {
//        selectedFlightId = flightId;
//        new Thread(new Runnable() {
//            public void run() {
//                log.debug("Loading tickets of flight #" + flightId + "...");
//                Result result = databaseOps.getAllTicketsOfFlight(flightId);
//                tickets.applyAll(result.getTickets());
//                result._release();
//                log.debug("Airplane tickets of flight #" + flightId + " loaded");
//            }
//        }).start();
//    }

    public void addTicket(final TicketInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Adding ticket " + object + "...");
                if (databaseOps.addTicket(
                        object.getFlightId(),
                        object.getSeatId(),
                        object.getClientName(),
                        object.getClientPassport())
                ) {
                    log.debug("Ticket #" + object.getId() + " added");
                } else {
                    log.debug("Ticket #" + object.getId() + " not added");
                    JOptionPane.showMessageDialog(null, "Ticket not added.");
                }
                reloadTickets();
            }
        }).start();
    }

    public void updateTicket(final TicketInfo object) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Updating ticket " + object + "...");
                if (databaseOps.updateTicket(
                        object.getId(),
                        object.getFlightId(),
                        object.getSeatId(),
                        object.getClientName(),
                        object.getClientPassport())
                ) {
                    log.debug("Ticket #" + object.getId() + " updated");
                } else {
                    log.debug("Ticket #" + object.getId() + " not updated");
                    JOptionPane.showMessageDialog(null, "Ticket not updated.");
                }
                reloadTickets();
            }
        }).start();
    }

    public void removeTicket(final int id) {
        new Thread(new Runnable() {
            public void run() {
                log.debug("Deleting ticket #" + id + "...");
                if (databaseOps.deleteTicket(id)) {
                    log.debug("Ticket #" + id + " deleted");
                } else {
                    log.debug("Ticket #" + id + " not deleted");
                    JOptionPane.showMessageDialog(null, "Ticket not deleted.");
                }
                reloadTickets();
            }
        }).start();
    }

    public AirplaneInfo loadAirplane(int id) {
        log.debug("Loading airplane #" + id + "...");
        for (Airplane object : databaseOps.getAirplane(id)) {
            if (object.id == id) {
                log.debug("Airplane #" + id + " loaded");
                return new AirplaneInfo(object);
            }
        }
        log.debug("Airplane #" + id + " not loaded");
        return null;
    }

    public AirplaneSeatInfo loadAirplaneSeat(int id) {
        log.debug("Loading airplane seat #" + id + "...");
        for (AirplaneSeat object : databaseOps.getAirplaneSeat(id)) {
            if (object.id == id) {
                log.debug("Airplane seat #" + id + " loaded");
                return new AirplaneSeatInfo(object);
            }
        }
        log.debug("Airplane seat #" + id + " not loaded");
        return null;
    }

    public AirportInfo loadAirport(int id) {
        log.debug("Loading airport #" + id + "...");
        for (Airport object : databaseOps.getAirport(id)) {
            if (object.id == id) {
                log.debug("Airport #" + id + " loaded");
                return new AirportInfo(object);
            }
        }
        log.debug("Airport #" + id + " not loaded");
        return null;
    }

    public FlightInfo loadFlight(int id) {
        log.debug("Loading flight #" + id + "...");
        for (Flight object : databaseOps.getFlight(id)) {
            if (object.id == id) {
                log.debug("Flight #" + id + " loaded");
                return new FlightInfo(object);
            }
        }
        log.debug("Flight #" + id + " not loaded");
        return null;
    }

    public TicketInfo loadTicket(int id) {
        log.debug("Loading ticket #" + id + "...");
        for (Ticket object : databaseOps.getTicket(id)) {
            if (object.id == id) {
                log.debug("Ticket #" + id + " loaded");
                return new TicketInfo(object);
            }
        }
        log.debug("Ticket #" + id + " not loaded");
        return null;
    }

    public void updateTickets() {
        log.debug("Updating tickets...");
        reloadTickets();
    }

    public void updateFlights() {
        log.debug("Updating flights...");
        reloadFlights();
    }

    public void updateAirplaneSeats() {
        log.debug("Updating airplane seats...");
        reloadAirplaneSeats();
    }

    public void updateAirplanes() {
        log.debug("Updating airplanes...");
        reloadAirplanes();
    }

    public void updateAirports() {
        log.debug("Updating airports...");
        reloadAirports();
    }
}
