// server.cpp : Defines the entry point for the console application.
//
#include "stdafx.h"

#include "FalconImpl.h"

#include "occi.h"

using namespace oracle::occi;
using namespace std;

int main(int argc, char* argv[])
{
	Environment* env = Environment::createEnvironment(Environment::THREADED_MUTEXED);

	try {
		// Initialize the ORB.
		CORBA::ORB_var orb = CORBA::ORB_init(argc, argv);

		// get a reference to the root POA
		CORBA::Object_var obj = orb->resolve_initial_references("RootPOA");
		PortableServer::POA_var rootPOA = PortableServer::POA::_narrow(obj);
	    
		CORBA::PolicyList policies;
		policies.length(1);
		policies[(CORBA::ULong)0] = rootPOA->create_lifespan_policy(PortableServer::PERSISTENT);

		// get the POA Manager
		PortableServer::POAManager_var poa_manager = rootPOA->the_POAManager();

		PortableServer::POA_var editorPOA = rootPOA->create_POA("editor_falcon_agent_poa", poa_manager, policies); 
		DatabaseImpl* editorOps = new DatabaseImpl(env); 
		PortableServer::ObjectId_var editorOpsId = PortableServer::string_to_ObjectId("Database");
		editorPOA->activate_object_with_id(editorOpsId, editorOps);

//		PortableServer::POA_var monitorPOA = rootPOA->create_POA("monitor_falcon_agent_poa", poa_manager, policies); 
//		EditorOperationsImpl* monitorOps = new MonitorOperationsImpl(); 
//		PortableServer::ObjectId_var monitorOpsId = PortableServer::string_to_ObjectId("MonitorOperations");
//		monitorPOA->activate_object_with_id(monitorOpsId, monitorOps);

		// Activate the POA Manager
		poa_manager->activate();

		cout << editorPOA->servant_to_reference(editorOps) << " is ready" << endl;
//		cout << monitorPOA->servant_to_reference(monitorOps) << " is ready" << endl;

		// Wait for incoming requests
		orb->run();
	} catch (const CORBA::Exception& e) {
		cerr << e << endl;
		return 1;
	}
	Environment::terminateEnvironment(env);
	return 0;
}

