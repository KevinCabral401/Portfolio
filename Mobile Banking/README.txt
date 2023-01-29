Kevin Cabral

This is my mobile banking App

The first activity is a login page that makes a web request to the web API
that I have created. This is the main.py included in the zip.
The Username and Password already in the API is (username, password) otherwise
feel free to create your own. 

Upon logging in the app with present you with a recycler view of your accounts.
The database for accounts in the app is not relational and will show all accounts as 
long as you have signed in. 

There are options for transfering funds between accounts,
depositing funds into an account directly, and if you click on the detail view of
an account it will present an option for depositing a "Check" into an account
which utilizes the camera. 

There are two menu items, one for adding accounts that will present 
a dialog box for entering the new accounts information.
The other menu item brings up a list view of recent activity,
this information is not persistent and will only show activity in the current
activity lifecycle. 

