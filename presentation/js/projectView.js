var project = {
    name: null,
    teammates: [{name:"Jair"},{name:"Brianna"},{name:"Austin"},{name:"Cather"}],
    tasks: [
        {ID:"1", name:"Top Level", subTasks:[
            {ID:"1.1", name:"Child", subTasks:[
                {ID:"1.1.1", name:"Leaf", subTasks:null}
            ]}, 
            {ID:"1.2", name:"Leaf", subTasks:null}
        ]},
        {ID:"2", name:"Top Level", subTasks:[
            {ID:"2.1", name:"Child", subTasks:null},
            {ID:"2.2", name:"Child", subTasks:[
                {ID:"2.2.1", name:"Leaf", subTasks:null},
                {ID:"2.2.2", name:"Leaf", subTasks:null}
            ]}
        ]}
    ],
    archived: false,
    progress: 0
}; // temporary demo project

/**
 * loads the project with the given project name (from URL query)
 * @param {string} name 
 */
function loadProject(name) {
    console.log("Requested project: " + name);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", api_url + "/project/" + name, true);
    xhr.send();
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            var js = JSON.parse(xhr.responseText);
            if (js["statusCode"] != "200") {errorLoadingProject(js["error"]);return;}
            project = js["project"];
            console.log("Found project: " + JSON.stringify(project));
            document.getElementById("nameHeader").innerHTML = project.name;
            loadTaskView();
            loadTeamView();
        }
        else {
            console.log("Error with XHR")
            errorLoadingProject("There was an error with the API")
        }
    };
}

/**
 * Loads the project from the nav search bar
 */
function loadProjectNav(){
    var name = document.getElementById("navLoad").value;
    if (!(/[a-zA-Z]/.test(name))) {
        return;
    }
    window.location.href = "/presentation/html/project.html?name=" + name;
}

/**
 * Helper that displays the error in the body
 * @param {*} err 
 */
function errorLoadingProject(err){
    var e = document.createElement("div");
    e.classList = "alert alert-danger";
    e.setAttribute("role", "alert");
    e.innerHTML = err;
    document.getElementById("alertDiv").appendChild(e);
    document.getElementById("nameHeader").innerHTML = "Error loading project"
}
