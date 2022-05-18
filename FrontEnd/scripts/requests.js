let requestsTable = document.getElementById('requestsTable');
checkLogin().then(sendRequest);

async function sendRequest() {
    // sending basic GET request to /requests
    let httpResponse = await fetch('http://localhost:8080/requests');

    if (httpResponse && httpResponse.status===200) {
        let responseBody = await httpResponse.json();
        showRequests(responseBody);
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
    
    // for each request in the requests array from the http response
    for (let request of requestsArr) {
        // these requests are coming from Java so the fields are the same
        let row = document.createElement('tr');
        row.innerHTML = `
            <td>${request.id}</td>
            <td>${request.submitterId}</td>
            <td>${request.description}</td>
            <td>${request.cost}</td>
            <td>${request.eventDate}</td>
            <td>${request.submittedDate}</td>
            <!-- the ID of the adopt button will be "adopt" plus the pet's ID so that
            we can get the ID later and know which pet the user is trying to adopt -->
            <td><button type="button" id="grade${request.id}">Grade</submit></td>
        `;
        // add the row to the table
        requestsTable.appendChild(row);
        let gradeBtn = document.getElementById('grade' + request.id);
        adoptBtn.addEventListener('click', adoptPet);
    }
}

async function gradeRequest() {
    let requestId = event.target.id;
    requestId = requestId.slice(5); // removes the "grade" part of the button ID leaving just the request ID
    let messageBox = document.getElementById('messageBox');

    let userJSON = JSON.stringify(loggedInUser);
    if (loggedInUser) {
        let httpResponse = await fetch('http://localhost:8080/requests/' + requestId + '/grade',
            {method:'PUT', body:userJSON});
        if (httpResponse && httpResponse.status===200) {
            messageBox.innerText = 'Request graded successfully.';
            loggedInUser = await httpResponse.json();
            await sendRequest();
        } else {
            messageBox.innerText = 'Something went wrong, please try again.';
        }
    } else {
        messageBox.innerText = 'You have to be logged in to grade a request. (You may need to refresh the page.)';
    }
}
