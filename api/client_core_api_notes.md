# API Design (User - Client Core)

## General Description and Notes
* This API contains functions that are aimed to be platform independent (as long as the device runs Java)
* The intention is that a separate application (on Android or iOS for example) is made to wrap around and use this API
* Server has to check permissibility of user before adding them to guardian or user list
* Adding/Removing of modules is only available if user is a Guardian, but anyone can request a Module list
* For adding/removing stuff, should we have them return a boolean, or be void but throw exception on error?
* Server should handle checking of activity of modules for changes and pushing to devices
* Chat functionality between members: include in app or hope that members have their own way of messaging each other?
	* Announcement log -> chat log between server, dependents, and guardians (with filters)?

## Module management
* getModuleList() - request list from server; ArrayList<Module>
* getModuleData() - get data per module; string?
* Module Creation/Removal
	* findOpenModules() - creates a list of modules that are on, but not connected to server (request from server) (???); ArrayList<Module>
	* addModule() - create a module locally and push creation data to server; bool for success/fail?
	* moduleIsActive() - from getModuleList, check if module is actually active and functioning properly; bool -> calls isActive() in module calss
	* removeModule() - remove a module from server list; bool for success/fail?
* setSystemSensitivity() - define a set of conditions before the server sends a security alert to the guardians and dependents, (e.g. send alert only when sensor A and B both trigger); void

## Server management (from the perspective of the app)
* abstract, as some methods are platform dependent while others are not
* Protected ServerListener - server connected to
	* List of listeners for multiple servers?
* serverIsUp() - check if previously save server is up and running; bool
* generateDeviceKey() - generate a unique key per device; 1 time run per install; string (alphanumeric)
* findLocalServers() - using local WiFi connection (bluetooth?), look for any open servers; ArrayList
* requestServerConnect(deviceKey, userType) - try to connect to a server given a device key and userType (Guardian or Dependent); ServerListener Object?
 	* this can double as an attendance function so the server knows who’s on the property
* pushToGuardian(name, data) - push a notification to another Guardian, could be expanded to broadcast a message to all guardians, server as man-in-the-middle?; bool for success/fail?
* pushToDependent(name, data) - push a notification to another dependent, could be expanded to broadcast a message to all dependents, server as man-in-the-middle?; bool for success/fail?
* getLogsOfLast(Time object with days, hours, and minutes) - request data of module logs of past X time; ArrayList<String?>
* receiveAlert() - receive an alert from the server, MOST IMPORTANT; string or custom notification class?
	* If we make an abstract custom notification class, it’ll need to create a custom link to the module page or a page with a list of modules that were triggered
	* Server Alert class (interface)
		* String message
		* List of triggered modules
		* Custom page/link creation method
* pushToAll(data) - push an alert to everyone on the server member list, usually for announcements from another guardian (allow dependents too?); bool or exception?

## UserAction methods
* Guardian Management
	* getGuardianList() - request list of guardians from server; ArrayList<Guardian>
	* addGuardian() - add a guardian to the list of guardians, authentication?; bool for success/fail, or void but throw exception on error?
	* removeGuardian() - remove a guardian from the list of guardians (requires being a guardian to do so); bool for success/fail, or void but throw exception on error?
* Dependent Management
	* getDependentList() - request list of dependents from server; ArrayList<Dependent>
	* addDependent() - add a dependent to the list of dependents (requires being a guardian); bool or exception?
	* removeDependent() - add a dependent to the list of dependents (requires being a guardian?); bool or exception?
	* requestLocation(name) - request the location of another member (requests to dependents only, or both type can send each other requests?)
 
## User Class (abstract)
* contains basic information about each user, to be sent to and from server (depending on creation/removal or simple data request, respectively)
* Protected userType = {Guardian, Dependent} - different methods available depending on userType
* Protected Device Key and nickname
* Protected OnlineStatus = {online,offline}
* Guardian extends User
 	* userType = Guardian
* Dependent extends User
 	* userType = Dependent
