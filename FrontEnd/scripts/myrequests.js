let requestsTable = document.getElementById('requestsTable');
let messageBox = document.getElementById('messageBox');

checkLogin().then(setMessageBox);

function setMessageBox() {
    if (loggedInUser) {
        if (loggedInUser.requests && loggedInUser.requests.length>0) {
            showRequests(loggedInUser.requests);
        } else {
            messageBox.innerText = 'You don\'t seem to have any requests. Try making some!';
        }
    } else {
        messageBox.innerText = 'You need to be logged in to view your requests! (You may need to refresh the page.)';
    }
}

function showRequests(requestsArr) {
    requestsTable.innerHTML = `<tr>
    <th>ID</th>
    <th>submitterId</th>
    <th>description</th>
    <th>cost</th>
    <th>eventDate</th>
    <th>submittedDate</th>
    </tr>`;
    
    for (let request of requestsArr) {
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${request.id}</td>
            <td>${request.submitterId}</td>
            <td>${request.description}</td>
            <td>${request.cost}</td>
            <td>${request.eventDate}</td>
            <td>${request.submittedDate}</td>
        `;
        // add the row to the table
        requestsTable.appendChild(row);
    }
}
