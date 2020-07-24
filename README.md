# DiscordBot

## Importing project into Eclipse

Clone the project

Import as "Existing Maven Project"

Right click the project and click Run as > Maven Install


## Running Program from command line:

Open the terminal in your project folder and run one of the following commands:

With Default user:

`mvn clean compile exec:java`

With Specific user:

`mvn clean compile exec:java -Dexec.args="Keith"`

## Setup Steps
- Create a discord account
- Create a discord application and a discord bot on the discord developer site.
- Clone this repository
- Import this project into eclipse as a maven project
- Edit the config.json to include your channel name and token.
- Run maven install on your project
- Run the bot (you should see a link output to the console)
- Send your link to your teacher. (your teacher will verify your bot)
- Rerun your program. Your bot should be connected!
