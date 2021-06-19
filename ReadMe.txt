Backend:
-Right click on project then Maven --> Update Project
-Right click on JumiataskApplication.java and run as java application
-Will run on http://localhost:9000
-DB is embedded inside
-The API will run on '/api/v1/customers/phone'
-Query Params for API:
	-countries (List<String>)
	-isValid (Boolean)
	ex: localhost:9000/api/v1/customers/phone?countries=cameroon, morocco&isValid=true