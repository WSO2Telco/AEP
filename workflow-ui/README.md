# Workflow UI

## Setting up Dev Environment
install nodejs
require Node 4 or higher, together with NPM 3 or higher.

Install Angular-cli

```bash
npm install -g @angular/cli
```

navigate to project root folder and run 
```bash
npm install
```
this will install all project dependencies defined in package.json file in the root directory


To Build client application code and copy to server/public folder
```bash
npm run build
```





## Server Deployment

Run 
```bash
npm run bundle
```
this will copy package.json and server folder to bundle directory 
this directory can be extracted and deploy as a server 

Navigate to bundle directory and 
Run 
```bash
npm install
```
this will download all the dependencies  required to run the server

Run 
```bash
npm start
```
to Start the server

following environment variables can be set prior to start the server to override settings



EnvVariable                         |Default Value  
---                                 |---
wm_context                          |workflow-manager
wm_server_port                      |3060
wm_auth_server_url                  |https://localhost:9443/services
wm_business_process_engine_url      |http://localhost:9763/activiti-rest/service
wm_reporting_url                    |https://localhost:9443/apihostobject
wm_business_process_engine_user     |kermit
wm_business_process_engine_password |kermit
wm_allowed_roles                    |admin,subscriber,operator1-admin-role,operator2-admin-role,operator2-cc-role"

