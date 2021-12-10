var project = {
    name: null,
    teammates: [{name:"Jair"},{name:"Brianna"},{name:"Austin"},{name:"Cather"}],
    tasks: [
        {prefix:"1", name:"Top Level", subtasks:[
            {prefix:"1.1", name:"Child", subtasks:[
                {prefix:"1.1.1", name:"Leaf", subtasks:null}
            ]}, 
            {prefix:"1.2", name:"Leaf", subtasks:null}
        ]},
        {prefix:"2", name:"Top Level", subtasks:[
            {prefix:"2.1", name:"Child", subtasks:null},
            {prefix:"2.2", name:"Child", subtasks:[
                {prefix:"2.2.1", name:"Leaf", subtasks:null},
                {prefix:"2.2.2", name:"Leaf", subtasks:null}
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
            if (js["statusCode"] == "200") {
                project = js["project"];
                console.log("Found project: " + JSON.stringify(project));
                document.getElementById("nameHeader").innerHTML = project.name;
                loadTaskView();
                loadTeamView();
            }
            else {
                error(js["error"]);
                document.getElementById("nameHeader").innerHTML = "Error loading project"
            }
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
 * Loads the project from the nav search bar
 */
 function loadProjectNav2(){
    var name = new URL(window.location).searchParams.get("name");
    window.location.href = "/presentation/html/project.html?name=" + name;
}

function loadCreateProjectNav(){
    window.location.href = "/presentation/html/create.html";
}
