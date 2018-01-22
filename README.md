# Complete CRUD App

This application makes a complete CRUD by entering **key**-**value** fields with persistence in Redis Server.

## Requirements
- BD Redis
- Maven
## How to use
First, it is necessary to do git clone of the project, open Redis server. Then execute the following command in the project root:

			$mvn jetty:run
			
With this, you will be packaging the project and executing the goal jetty:run of maven.
> You can execute the 'monitor' command in redis, to see the results

Use the browser and enter the following address:

			http://localhost:9999
