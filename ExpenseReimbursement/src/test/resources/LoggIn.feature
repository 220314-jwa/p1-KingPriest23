Feature: Employee login

	# Background: the user is on the homepage, is not logged in
	# you can use this when all of your scenarios have a certian prerequisite

	Scenario: Successful login
		Given the employee is on the homepage
		When the employee enters the correct useraname
		And the employee enters the correct password
		And the employee clicks the login button
		Then the nav will show the employee's first name
		
