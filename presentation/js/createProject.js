/**
 * Creates a new project with the given name
 */
function createProject() {
    var projectName = document.getElementById("createProjectField").value;

    console.log("Posting on : /presentation/html/project.html?name=")
    console.log("Body : " + JSON.stringify({"name" : projectName}));

    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/project", true);
    xhr.send(JSON.stringify({"name" : projectName}));
    loading();

    xhr.onloadend = function () {        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            //console.log ("Result:" + xhr.responseText);
            var js = JSON.parse(xhr.responseText);
        
            var status = js["statusCode"];
            
            if (status == 200) { 
                console.log("Post successful");
                window.location.href = "/presentation/html/project.html?name=" + js["projectName"];
            } else {
                error(js["error"]);
                console.log("Post unsuccessful: " + msg);
            }
        }
    };
}
