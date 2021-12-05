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
            console.log ("Result:" + xhr.responseText);
            var js = JSON.parse(xhr.responseText);
        
            var status = js["statusCode"];
            
            if (status == 200) {      
                window.location.href = "/presentation/html/project.html?name=" + js["projectName"];
            } else {
                var msg = js["error"];
                var e = document.createElement("div");
                e.classList = "alert alert-danger";
                e.setAttribute("role", "alert");
                e.innerHTML = msg;
                document.getElementById("alertDiv").appendChild(e);
            }
        }
    };
}
