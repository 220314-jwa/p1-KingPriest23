Feature: Employee login

	# Background: the user is on the homepage, is not logged in
	# you can use this when all of your scenarios have a certian prerequisite

	Scenario: Successful login
		Given the employee is on the homepage
		When the employee enters the correct useraname
		And the employee enters the correct password
		And the employee clicks the login button
		Then the nav will show the employee's first name
		
	Scenario: username does not exist
		Given the employee is on the homepage
		When the employee enters an incorrect username
		And the employee clicks the login button
		Then an incorrect credentials message will be displayed
		
	Scenario: incorrect password
		Given the employee is on the homepage
		When the employee enters the correct username
		And the employee enters the incorrect password
		And the employee clicks the login button
		Then an incorrect credentials message will be displayed
		
	Scenario Outline: invalid input
		Given the employee is on the homepage
		When the employee enters the username "<username>" // mehtod parameter with strings
		And the employee enters the password "<password>"
		And the employee clicks the login button
		Then an invalid input message will be displayed
		
		Examples: 
			| username | password |
			| a        | pass     |
			| user123  | at       |
			| %%%$$%%  | pword    |