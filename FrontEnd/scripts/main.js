let loggedInUser;
let nav = document.getElementsByTagName('nav')[0];
let logInBtn;
// call checklogin on the other JS files so that we can make sure other things happen first

async function checkLogin() {
    let userId = sessionStorage.getItem('Auth-Token');
    loggedOutNavBar();
    let httpResp = await fetch('http://localhost:8080/users/' + userId);
    if (httpResp && httpResp.status === 200) {
        loggedInUser = await httpResp.json();
        loggedInNavBar();
    } else {
        loggedOutNavBar();
    }
}

function loggedInNavBar() {
    nav.innerHTML = `<ul id="navList">
        <li><a href="index.html">TRMSApp</a></li>
        <li>&#128062;</li>
        <li><a href="all-requests.html">Available Requests</a></li>
        <li><a href="my-requests.html">My Requests</a></li>
    </ul>
    <form id="loginWindow">
        <span id="nameDisplay" style="padding-right:10px"></span>
        <button type="button" id="logInBtn">Log Out</button>
    </form>`;
    logInBtn = document.getElementById('logInBtn');
    logInBtn.addEventListener('click', logOut);
    document.getElementById('nameDisplay').innerText = loggedInUser.firstName;
}

function loggedOutNavBar() {
    nav.innerHTML = `<ul id="navList">
        <li><a href="index.html">PetApp</a></li>
        <li>&#128062;</li>
        <li><a href="all-requests.html">Available Requests</a></li>
        <li><a href="my-requests.html">My Requests</a></li>
    </ul>
    <form id="loginWindow">
        <label for="usernameLogin">Username: </label><input type="text" id="usernameLogin">&nbsp;
        <label for="passwordLogin">Password: </label><input type="password" id="passwordLogin">
        <button type="button" id="logInBtn">Log In</button>
    </form>`;
    logInBtn = document.getElementById('logInBtn');
    logInBtn.addEventListener('click', logIn);
}

async function logIn() {
    let credentials = {
        username:document.getElementById('usernameLogin').value,
        password:document.getElementById('passwordLogin').value
    };
    let credentialJSON = JSON.stringify(credentials);

    let httpResp = await fetch('http://localhost:8080/auth',
        {method:'POST', body:credentialJSON});
    if (httpResp && httpResp.status === 200) {
        loggedInUser = await httpResp.json();
        sessionStorage.setItem('Auth-Token', loggedInUser.id);
        await checkLogin();
        loggedInNavBar();
    }
}

function logOut() {
    sessionStorage.removeItem('Auth-Token');
    loggedInUser=null;
    loggedOutNavBar();
}

let styleSelector = document.getElementById('styleSelector');
changeStyle();
// when the style selector element has a selection, call the changeStyle function
styleSelector.addEventListener('change',changeStyle);

function changeStyle() {
    document.getElementsByTagName('link')[0].href='styles/'+styleSelector.value;
}
