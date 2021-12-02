/**
 * Creates a new project with the given name
 */
function createProject() {
    var projectName = document.getElementById("createProjectField").value;
    console.log("Project Name: " + projectName)
    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/project", true);
    xhr.send(JSON.stringify({"name" : projectName}));

    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            var js = JSON.parse(xhr.responseText);
            loadNewProject(xhr.responseText);
        }
    };
}

/**
 * Loads the new project's page
 * @param {*} result 
 */
function loadNewProject(result) {
    console.log("result:" + result);
    var js = JSON.parse(result);

    var status = js["statusCode"];
    
    if (status == 200) {      
        var project = js["project"];
        window.location.href = "/presentation/html/project/index.html?name=" + project.name;
    } else {
        console.log("woauibefoiuaebuiof")
        var msg = js["error"];
        var e = document.createElement("div");
        e.classList = "alert alert-danger";
        e.setAttribute("role", "alert");
        e.innerHTML = msg;
        document.getElementById("alertDiv").appendChild(e);
    }
}