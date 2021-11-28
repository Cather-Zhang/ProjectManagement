var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";

function createProject() {
    var projectName = document.getElementById("createProjectField").value;
    console.log("Project Name: " + projectName)
    var js = "{\"name\":\"" + projectName + "\"}";
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/project", true);
    xhr.send(JSON.stringify({"name" : projectName}));

    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processCreateProjectResponse(xhr.responseText);
        } else {
            processCreateProjectResponse("N/A");
        }
    };
}

function processCreateProjectResponse(result) {
    // Can grab any DIV or SPAN HTML element and can then manipulate its
    // contents dynamically via javascript
    console.log("result:" + result);
    var js = JSON.parse(result);

    var status = js["statusCode"];
    
    if (status == 200) {      
        var project = js["project"];
        window.location.href = "/html/project/?name=" + project.name;
    } else {
        var msg = js["error"];
        window.location.href = "/html/404/";
    }
}
