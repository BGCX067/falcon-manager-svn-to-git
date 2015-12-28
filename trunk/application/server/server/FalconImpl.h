#include "stdafx.h"
#include "Falcon_s.hh"

#include "occi.h"

typedef ru::ifmo::falconmanager::idl::DatabaseListener		TDatabaseListener	;
typedef ru::ifmo::falconmanager::idl::DatabaseListener_ptr	TDatabaseListener_ptr;
typedef ru::ifmo::falconmanager::idl::DatabaseListener_var	TDatabaseListener_var;

typedef ru::ifmo::falconmanager::idl::Airplane				TAirplane			;
typedef ru::ifmo::falconmanager::idl::AirplaneArray			TAirplaneArray		;
typedef ru::ifmo::falconmanager::idl::AirplaneSeat			TAirplaneSeat		;
typedef ru::ifmo::falconmanager::idl::AirplaneSeatArray		TAirplaneSeatArray	;
typedef ru::ifmo::falconmanager::idl::Airport				TAirport			;
typedef ru::ifmo::falconmanager::idl::AirportArray			TAirportArray		;
typedef ru::ifmo::falconmanager::idl::Flight				TFlight				;
typedef ru::ifmo::falconmanager::idl::FlightArray			TFlightArray		;
typedef ru::ifmo::falconmanager::idl::Ticket				TTicket				;
typedef ru::ifmo::falconmanager::idl::TicketArray			TTicketArray		;

#define INT_LENGTH 12
#define DATA_LENGTH 21

using namespace oracle::occi;
using namespace std;

class DatabaseImpl : public POA_ru::ifmo::falconmanager::idl::Database {
public:
    DatabaseImpl(Environment* env) {
        environment = env;
    }

	void addDatabaseListener(TDatabaseListener_ptr listener) {
//		cout << "addDatabaseListener" << endl;
		listeners.push_back(TDatabaseListener::_duplicate(listener));
	}

    CORBA::Boolean addAirplane(const char* name) {
        Connection* connection = createConnection();
	    Statement* stmt = connection->createStatement("INSERT INTO airplanes (name) VALUES (:name)");
		stmt->setString(1, name);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
	    connection->commit();
		environment->terminateConnection(connection);
		if (ok) {
			fireAirplane();
		}
		return ok;
    }

    CORBA::Boolean addAirplaneSeat(CORBA::Long airplaneId, const char* seat) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("INSERT INTO airplanes_seats (airplane_id, seat) VALUES (:airplane_id, :seat)");
		stmt->setInt(1, airplaneId);
		stmt->setString(2, seat);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
	    connection->commit();
		environment->terminateConnection(connection);
		if (ok) {
			fireAirplaneSeat();
		}
		return ok;
    }

	CORBA::Boolean addAirport(const char* shortName, const char* longName) {
        Connection* connection = createConnection();
        Statement* stmt = connection->createStatement("INSERT INTO airports (short_name, long_name) VALUES (:short_name, :long_name)");
        stmt->setString(1, shortName);
        stmt->setString(2, longName);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirport();
		}
		return ok;
    }

	CORBA::Boolean addFlight(CORBA::Long departureAirportId,
                             const char* departureDate,
							 CORBA::Long arrivalAirportId,
                             const char* arrivalDate,
                             CORBA::Long airplaneId
	) {
        Connection* connection = createConnection();
//        Statement* stmt = connection->createStatement("INSERT INTO flights (departure_airport_id, departure_date, arrival_airport_id, arrival_date, airplane_id) VALUES ((SELECT id FROM airports WHERE short_name = :departure_airport), TO_DATE(:departure_date, 'YYYY.MM.DD HH24:MI:SS'), (SELECT id FROM airports WHERE short_name = :arrival_airport), TO_DATE(:arrival_date, 'YYYY.MM.DD HH24:MI:SS'), :airplane_id)");
        Statement* stmt = connection->createStatement("INSERT INTO flights (departure_airport_id, departure_date, arrival_airport_id, arrival_date, airplane_id) VALUES (:departure_airport_id, TO_DATE(:departure_date, 'YYYY.MM.DD HH24:MI:SS'), :arrival_airport_id, TO_DATE(:arrival_date, 'YYYY.MM.DD HH24:MI:SS'), :airplane_id)");
		stmt->setInt(1, departureAirportId);
		stmt->setString(2, departureDate);
		stmt->setInt(3, arrivalAirportId);
		stmt->setString(4, arrivalDate);
		stmt->setInt(5, airplaneId);
		
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireFlight();
		}
		return ok;
    }

	CORBA::Boolean addTicket(CORBA::Long flightId,
                             CORBA::Long seatId,
                             const char* clientName,
                             const char* clientPassport
	) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("INSERT INTO tickets (flight_id, seat_id, client_name, client_passport) VALUES (:flight_id, :seat_id, :client_name, :client_passport)");
		stmt->setInt(1, flightId);
		stmt->setInt(2, seatId);
		stmt->setString(3, clientName);
		stmt->setString(4, clientPassport);
		
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireTicket();
		}
		return ok;
    }

	CORBA::Boolean updateAirplane(CORBA::Long id, const char* name) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("UPDATE airplanes SET name = :name WHERE id = :id");
        stmt->setString(1, name);
        stmt->setInt(2, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirplane();
		}
		return ok;
    }

	CORBA::Boolean updateAirplaneSeat(CORBA::Long id, CORBA::Long airplaneId, const char* seat) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("UPDATE airplanes_seats SET airplane_id = :airplane_id, seat = :seat WHERE id = :id");
        stmt->setInt(1, airplaneId);
        stmt->setString(2, seat);
        stmt->setInt(3, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirplaneSeat();
		}
		return ok;
    }

	CORBA::Boolean updateAirport(CORBA::Long id, const char* shortName, const char* longName) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("UPDATE airports SET short_name = :short_name, long_name = :long_name WHERE id = :id");
        stmt->setString(1, shortName);
        stmt->setString(2, longName);
        stmt->setInt(3, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirport();
		}
		return ok;
    }

	CORBA::Boolean updateFlight(CORBA::Long id, 
								CORBA::Long departureAirportId,
								const char* departureDate,
								CORBA::Long arrivalAirportId,
								const char* arrivalDate,
								CORBA::Long airplaneId
	) {
        Connection* connection = createConnection();
//        Statement* stmt = connection->createStatement("UPDATE flights SET departure_airport_id = (SELECT id FROM airports WHERE short_name = :departure_airport), departure_date = TO_DATE(:departure_date, 'YYYY.MM.DD HH24:MI:SS'), arrival_airport_id = (SELECT id FROM airports WHERE short_name = :arrival_airport), arrival_date = TO_DATE(:arrival_date, 'YYYY.MM.DD HH24:MI:SS'), airplane_id = :airplane_id WHERE id = :id");
        Statement* stmt = connection->createStatement("UPDATE flights SET departure_airport_id = :departure_airport_id, departure_date = TO_DATE(:departure_date, 'YYYY.MM.DD HH24:MI:SS'), arrival_airport_id = :arrival_airport_id, arrival_date = TO_DATE(:arrival_date, 'YYYY.MM.DD HH24:MI:SS'), airplane_id = :airplane_id WHERE id = :id");
		stmt->setInt(1, departureAirportId);
		stmt->setString(2, departureDate);
		stmt->setInt(3, arrivalAirportId);
		stmt->setString(4, arrivalDate);
		stmt->setInt(5, airplaneId);
		stmt->setInt(6, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireFlight();
		}
		return ok;
    }

	CORBA::Boolean updateTicket(CORBA::Long id, 
								CORBA::Long flightId,
								CORBA::Long seatId,
								const char* clientName,
								const char* clientPassport
	) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("UPDATE tickets SET flight_id = :flight_id, seat_id = :seat_id, client_name = :client_name, client_passport = :client_passport WHERE id = :id");
		stmt->setInt(1, flightId);
		stmt->setInt(2, seatId);
		stmt->setString(3, clientName);
		stmt->setString(4, clientPassport);
		stmt->setInt(5, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireTicket();
		}
		return ok;
    }

	CORBA::Boolean deleteAirplane(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("DELETE FROM airplanes WHERE id = :id");
        stmt->setInt(1, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirplane();
		}
		return ok;
    }

	CORBA::Boolean deleteAirplaneSeat(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("DELETE FROM airplanes_seats WHERE id = :id");
        stmt->setInt(1, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirplaneSeat();
		}
		return ok;
    }

	CORBA::Boolean deleteAirport(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("DELETE FROM airports WHERE id = :id");
        stmt->setInt(1, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireAirport();
		}
		return ok;
    }

	CORBA::Boolean deleteFlight(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("DELETE FROM flights WHERE id = :id");
        stmt->setInt(1, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireFlight();
		}
		return ok;
    }

	CORBA::Boolean deleteTicket(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("DELETE FROM tickets WHERE id = :id");
        stmt->setInt(1, id);
		CORBA::Boolean ok;
		try {
			stmt->executeUpdate();
			ok = true;
		} catch (SQLException ex) {
			ok = false;
		}
        connection->terminateStatement(stmt);
        connection->commit();
        environment->terminateConnection(connection);
		if (ok) {
			fireTicket();
		}
		return ok;
    }

	TAirplaneArray* getAirplane(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, name FROM airplanes WHERE id = :id");
		stmt->setInt(1, id);
        ResultSet* rs = stmt->executeQuery();
		TAirplaneArray* airplanes = getAirplaneArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirplaneArray::_duplicate(airplanes);
    }

    TAirplaneSeatArray* getAirplaneSeat(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, airplane_id, seat FROM airplanes_seats WHERE id = :id");
		stmt->setInt(1, id);
        ResultSet* rs = stmt->executeQuery();
		TAirplaneSeatArray* airplanes = getAirplaneSeatArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirplaneSeatArray::_duplicate(airplanes);
    }

	TAirportArray* getAirport(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, short_name, long_name FROM airports WHERE id = :id");
		stmt->setInt(1, id);
        ResultSet* rs = stmt->executeQuery();
		TAirportArray* airports = getAirportArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirportArray::_duplicate(airports);
    }

	TFlightArray* getFlight(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, departure_airport_id, TO_CHAR(departure_date, 'YYYY.MM.DD HH24:MI:SS'), arrival_airport_id, TO_CHAR(arrival_date, 'YYYY.MM.DD HH24:MI:SS'), airplane_id FROM flights WHERE id = :id");
		stmt->setInt(1, id);
        ResultSet* rs = stmt->executeQuery();
		TFlightArray* flights = getFlightArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TFlightArray::_duplicate(flights);
    }

	TTicketArray* getTicket(CORBA::Long id) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, flight_id, seat_id, client_name, client_passport FROM tickets WHERE id = :id");
		stmt->setInt(1, id);
        ResultSet* rs = stmt->executeQuery();
		TTicketArray* tickets = getTicketArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TTicketArray::_duplicate(tickets);
    }

	TAirplaneArray* getAllAirplanes() {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, name FROM airplanes");
        ResultSet* rs = stmt->executeQuery();
		TAirplaneArray* airplanes = getAirplaneArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirplaneArray::_duplicate(airplanes);
	}

	TAirplaneSeatArray* getAllAirplaneSeats() {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, airplane_id, seat FROM airplanes_seats");
        ResultSet* rs = stmt->executeQuery();
		TAirplaneSeatArray* airplanes = getAirplaneSeatArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirplaneSeatArray::_duplicate(airplanes);
	}

	TAirplaneSeatArray* getAllAirplaneSeatsOfAirplane(CORBA::Long airplaneId) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, airplane_id, seat FROM airplanes_seats WHERE airplane_id = :airplane_id");
		stmt->setInt(1, airplaneId);
        ResultSet* rs = stmt->executeQuery();
		TAirplaneSeatArray* airplanes = getAirplaneSeatArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirplaneSeatArray::_duplicate(airplanes);
	}

	TAirportArray* getAllAirports() {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, short_name, long_name FROM airports");
        ResultSet* rs = stmt->executeQuery();
		TAirportArray* airports = getAirportArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TAirportArray::_duplicate(airports);
    }

	TFlightArray* getAllFlights() {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, departure_airport_id, TO_CHAR(departure_date, 'YYYY.MM.DD HH24:MI:SS'), arrival_airport_id, TO_CHAR(arrival_date, 'YYYY.MM.DD HH24:MI:SS'), airplane_id FROM flights");
        ResultSet* rs = stmt->executeQuery();
		TFlightArray* flights = getFlightArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TFlightArray::_duplicate(flights);
    }

	TTicketArray* getAllTickets() {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, flight_id, seat_id, client_name, client_passport FROM tickets");
        ResultSet* rs = stmt->executeQuery();
		TTicketArray* tickets = getTicketArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TTicketArray::_duplicate(tickets);
    }

	TTicketArray* getAllTicketsOfFlight(CORBA::Long flightId) {
        Connection* connection = createConnection();
		Statement* stmt = connection->createStatement("SELECT id, flight_id, seat_id, client_name, client_passport FROM tickets WHERE flight_id = :flight_id");
		stmt->setInt(1, flightId);
        ResultSet* rs = stmt->executeQuery();
		TTicketArray* tickets = getTicketArray(rs);
        stmt->closeResultSet(rs);
        connection->terminateStatement(stmt);
		connection->rollback();
        environment->terminateConnection(connection);
		return TTicketArray::_duplicate(tickets);
    }

private:
    Environment* environment;

	vector<TDatabaseListener_var> listeners;

	Connection* createConnection() {
		return environment->createConnection("scott", "tiger", "");
	}

	void fireAirport() {
		vector<TDatabaseListener_var> listeners = this->listeners; 
		for (CORBA::ULong i = 0; i < listeners.size(); ++i) {
//			cout << "fireAirport " << i << endl;
			listeners[i]->updateAirports();
		}
	};

	void fireAirplane() {
		vector<TDatabaseListener_var> listeners = this->listeners; 
		for (CORBA::ULong i = 0; i < listeners.size(); ++i) {
//			cout << "fireAirplane " << i << endl;
			listeners[i]->updateAirplanes();
		}
	};

	void fireAirplaneSeat() {
		vector<TDatabaseListener_var> listeners = this->listeners; 
		for (CORBA::ULong i = 0; i < listeners.size(); ++i) {
//			cout << "fireAirplaneSeat " << i << endl;
			listeners[i]->updateAirplaneSeats();
		}
	};

	void fireFlight() {
		vector<TDatabaseListener_var> listeners = this->listeners; 
		for (CORBA::ULong i = 0; i < listeners.size(); ++i) {
//			cout << "fireFlight " << i << endl;
			listeners[i]->updateFlights();
		}
	};

	void fireTicket() {
		vector<TDatabaseListener_var> listeners = this->listeners; 
		for (CORBA::ULong i = 0; i < listeners.size(); ++i) {
//			cout << "fireTicket " << i << endl;
			listeners[i]->updateTickets();
		}
	};

	TAirplaneArray* getAirplaneArray(ResultSet* rs) {
        rs->setMaxColumnSize(1, INT_LENGTH);
        rs->setMaxColumnSize(2, 100);
		vector<TAirplane> objects;
		PortableServer::POA_var default_poa = _default_POA();
		while (rs->next()) {
			TAirplane object;
			object.id = rs->getInt(1);
			object.name = rs->getString(2).c_str();
			objects.push_back(object);
        }
		TAirplaneArray* result = new TAirplaneArray();
		result->length((CORBA::ULong) objects.size());
		for (CORBA::ULong i = 0; i < objects.size(); ++i) {
			(*result)[i] = objects[i];
		}
		return result;
	}

	TAirplaneSeatArray* getAirplaneSeatArray(ResultSet* rs) {
        rs->setMaxColumnSize(1, INT_LENGTH);
        rs->setMaxColumnSize(2, INT_LENGTH);
        rs->setMaxColumnSize(3, 100);
		vector<TAirplaneSeat> objects;
		PortableServer::POA_var default_poa = _default_POA();
		while (rs->next()) {
			TAirplaneSeat object;
			object.id = rs->getInt(1);
			object.airplaneId = rs->getInt(2);
			object.seat = rs->getString(3).c_str();
			objects.push_back(object);
        }
		TAirplaneSeatArray* result = new TAirplaneSeatArray();
		result->length((CORBA::ULong) objects.size());
		for (CORBA::ULong i = 0; i < objects.size(); ++i) {
			(*result)[i] = objects[i];
		}
		return result;
	}

	TAirportArray* getAirportArray(ResultSet* rs) {
        rs->setMaxColumnSize(1, INT_LENGTH);
        rs->setMaxColumnSize(2, 3);
        rs->setMaxColumnSize(3, 100);
		vector<TAirport> objects;
		PortableServer::POA_var default_poa = _default_POA();
		while (rs->next()) {
			TAirport object;
			object.id = rs->getInt(1);
			object.shortName = rs->getString(2).c_str();
			object.fullName = rs->getString(3).c_str();
			objects.push_back(object);
        }
		TAirportArray* result = new TAirportArray();
		result->length((CORBA::ULong) objects.size());
		for (CORBA::ULong i = 0; i < objects.size(); ++i) {
			(*result)[i] = objects[i];
		}
		return result;
	}

	TFlightArray* getFlightArray(ResultSet* rs) {
        rs->setMaxColumnSize(1, INT_LENGTH);
        rs->setMaxColumnSize(2, INT_LENGTH);
        rs->setMaxColumnSize(3, DATA_LENGTH);
        rs->setMaxColumnSize(4, INT_LENGTH);
        rs->setMaxColumnSize(5, DATA_LENGTH);
        rs->setMaxColumnSize(4, INT_LENGTH);
		vector<TFlight> objects;
		PortableServer::POA_var default_poa = _default_POA();
		while (rs->next()) {
			TFlight object;
			object.id = rs->getInt(1);
			object.departureAirportId = rs->getInt(2);
			object.departureDate = rs->getString(3).c_str();
			object.arrivalAirportId = rs->getInt(4);
			object.arrivalDate = rs->getString(5).c_str();
			object.airplaneId = rs->getInt(6);
			objects.push_back(object);
        }
		TFlightArray* result = new TFlightArray();
		result->length((CORBA::ULong) objects.size());
		for (CORBA::ULong i = 0; i < objects.size(); ++i) {
			(*result)[i] = objects[i];
		}
		return result;
	}

	TTicketArray* getTicketArray(ResultSet* rs) {
        rs->setMaxColumnSize(1, INT_LENGTH);
        rs->setMaxColumnSize(2, INT_LENGTH);
        rs->setMaxColumnSize(3, INT_LENGTH);
        rs->setMaxColumnSize(4, 100);
        rs->setMaxColumnSize(5, 20);
		vector<TTicket> objects;
		PortableServer::POA_var default_poa = _default_POA();
		while (rs->next()) {
			TTicket object;
			object.id = rs->getInt(1);
			object.flightId = rs->getInt(2);
			object.seatId = rs->getInt(3);
			object.clientName = rs->getString(4).c_str();
			object.clientPassport = rs->getString(5).c_str();
			objects.push_back(object);
        }
		TTicketArray* result = new TTicketArray();
		result->length((CORBA::ULong) objects.size());
		for (CORBA::ULong i = 0; i < objects.size(); ++i) {
			(*result)[i] = objects[i];
		}
		return result;
	}
};

