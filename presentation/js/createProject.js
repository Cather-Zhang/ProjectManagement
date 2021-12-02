var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";

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
            loadProjectPage(xhr.responseText);
        }
    };
}

function loadProjectPage(result) {
    console.log("result:" + result);
    var js = JSON.parse(result);

    var status = js["statusCode"];
    
    if (status == 200) {      
        var project = js["project"];
        window.location.href = "/presentation/html/project/index.html?name=" + project.name;
    } else {
        var msg = js["error"];
        window.location.href = "/presentation/html/404/index.html";
    }
}
