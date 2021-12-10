var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";

/**
 * Creates a gray button, and sets its handler function
 * @param {string} text inner HTML of button
 * @param {string} handler function to call
 * @param {string} type
 * @returns 
*/
function buildButton(text, handler, type){
    if (type == undefined) {type = "secondary"}
    var b = document.createElement("button");
    b.setAttribute("type", "button");
    b.setAttribute("onclick", handler);
    b.classList = "btn btn-" + type;
    b.innerHTML = text;
    b.style.marginRight = ".3rem"
    return b;
}

/**
 * Deletes DOM element by ID name
 * @param {string} name 
 */
function deleteElement(name){
    document.getElementById(name).remove();
}

/**
 * Posts from the base URL of the API, on the specified path, with a body
 * Very basic, at some point, make async and promise and on fail and on success functions
 * @param {string} path 
 * @param {JSON} js 
 */
function post(path, js) {
    console.log("Posting on : " + path)
    console.log("Body : " + JSON.stringify(js));
    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + path, true);
    xhr.send(JSON.stringify(js));
    loading();
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            var response = JSON.parse(xhr.responseText);
            if (response["statusCode"] == "200") {
                console.log("Post successful")
                window.location.reload();                
            }
            else {
                error(response["error"]);
                console.log("Post unsuccessful: " + response["error"])
            }
        }
        else {
            console.log("An error has occured with XHR");
        }
    };
}

/**
 * Opens the specified modal with the task prefix attatched
 * @param {string} prefix 
 * @param {string} modal 
 */
function openModal(prefix, modal){
    $('#' + modal).modal('show');
    console.log(modal + " opened from " + prefix);
    if(prefix == ""){
        requestedParent = null;
    }
    else {
        requestedParent = prefix;
    }
}

function loading(){
    var e = document.createElement("div");
    e.classList = "alert alert-info text-center";
    e.setAttribute("role", "alert");
    e.innerHTML = "Loading...";
    document.getElementById("loadingDiv").appendChild(e);
}

function error(msg){
    var e = document.createElement("div");
    e.classList = "alert alert-danger text-center";
    e.setAttribute("role", "alert");
    e.innerHTML = msg;
    document.getElementById("loadingDiv").appendChild(e);
}