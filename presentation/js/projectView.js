var project = {
    name: null,
    teammates: [{name:"Jair"},{name:"Brianna"},{name:"Austin"},{name:"Cather"}],
    tasks: [
        {taskId:"1", name:"Top Level", subTasks:[
            {taskId:"1.1", name:"Child", subTasks:[
                {taskId:"1.1.1", name:"Leaf", subTasks:null}
            ]}, 
            {taskId:"1.2", name:"Leaf", subTasks:null}
        ]},
        {taskId:"2", name:"Top Level", subTasks:[
            {taskId:"2.1", name:"Child", subTasks:null},
            {taskId:"2.2", name:"Child", subTasks:[
                {taskId:"2.2.1", name:"Leaf", subTasks:null},
                {taskId:"2.2.2", name:"Leaf", subTasks:null}
            ]}
        ]}
    ],
    archived: false,
    progress: 0
};

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
            console.log("Response: " + xhr.response);
            var js = JSON.parse(xhr.responseText);
            if (js["statusCode"] != "200") {errorLoadingProject(js["error"]);return;}
            //TODO CHANGE BACK TO PROJECT = JS["P"]
            project.name = js["p"]["name"];
            console.log("Found project: " + JSON.stringify(project));
            document.getElementById("nameHeader").innerHTML = project.name;
            loadTaskView();
            loadTeamView();
        }
        else {
            console.log("invalid project");
        }
    };
}

function loadProjectNav(){
    var name = document.getElementById("navLoad").value;
    if (!(/[a-zA-Z]/.test(name))) {
        return;
    }
    window.location.href = "/presentation/html/project/index.html?name=" + name;
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

/**
 * Load the team view on the right of the screen
 */
function loadTeamView(){
    var teamDiv = document.getElementById("team");
    var teammateTitle = document.createElement("h2");
    teammateTitle.innerHTML = "Teammates"; 
    teamDiv.appendChild(teammateTitle);
    for(let i = 0; i < project.teammates.length; i++) {
        createTeammateRow(project.teammates[i].name);
    }
    //add teammate modal button
    var addBtn = document.createElement("button");
    addBtn.setAttribute("type", "button");
    addBtn.setAttribute("class", "btn btn-secondary");
    addBtn.setAttribute("data-bs-toggle", "modal");
    addBtn.setAttribute("data-bs-target", "#addTeammateModal");
    addBtn.innerHTML = "Add";
    document.getElementById("addBtn").appendChild(addBtn);
}

/**
 * Helper for loadTeamView(). Used to create a row div, and append to the document
 * @param {string} req 
 */
function createTeammateRow(req){
    var rowDiv = document.createElement("div");
    rowDiv.className = "row";
    var colDiv = document.createElement("div");
    colDiv.className = "col-md-6";
    var nameP = document.createElement("p");
    nameP.innerHTML = req;
    colDiv.appendChild(nameP);
    rowDiv.appendChild(colDiv);
    var btnDiv = document.createElement("div");
    btnDiv.className = "col-md-auto";
    var removeBtn = buildButton("Remove", "removeTeammate(\"" + req + "\")", "danger")
    btnDiv.appendChild(removeBtn);
    rowDiv.appendChild(btnDiv);
    document.getElementById("team").appendChild(rowDiv);
    rowDiv.appendChild(document.createElement("hr"));
}

/**
 * Load the task view on the left of the screen
 */
function loadTaskView(){
    if (project.tasks == undefined || project.tasks == null) {
        document.getElementById("tasks").innerHTML = "No tasks avaliable";
        return;
    }
    var t = document.getElementById("tasks")
    var b = buildButton("+", "addTopLevelTask()");
    var c = document.createElement("div");
    c.classList = "col-md-2";
    b.style.marginBottom = "1rem";
    c.appendChild(b);
    t.appendChild(c);
    let depth = 0;
    //manually traverses through the top level tasks because they are not
    //a part of a parent task
    for (let index = 0; index < project.tasks.length; index++) {
        traverseTasks(project.tasks[index], depth+1);
    }
}

/**
 * Helper that recursively traverses the task tree of the project, and calls generateTaskRow()
 * @param {object} parent 
 * @param {number} depth 
 */
function traverseTasks(parent, depth){
    if (parent.subTasks == undefined || parent.subTasks == null) {
        //generate leaf task rowoh n
        generateTaskRow(parent, depth, true);
        return;
    } else {
        //generate parent task row
        generateTaskRow(parent, depth, false);
    }
    for (let j = 0; j < parent.subTasks.length; j++) {
        //traverse through the rest of the list
        traverseTasks(parent.subTasks[j], depth+1);
    }
}

/**
 * Helper that actually generates the DOM elements, and a row div for the respective task
 * @param {object} parent 
 * @param {number} depth 
 * @param {boolean} leaf 
 */
function generateTaskRow(parent, depth, leaf){
    //new row for a new task
    var rowDiv = document.createElement("div");
    rowDiv.className = "row mb-3";
    //name div for the task name
    var nameDiv = document.createElement('div');
    nameDiv.className = "col-md-auto";
    nameDiv.style = "padding-left:" + (depth*2 - 1)+ "rem;";
    //paragraph element for the task name
    var nameP = document.createElement('p');
    nameP.innerHTML = parent.taskId + " " + parent.name;
    //append nameP to nameDiv and nameDiv to rowDiv
    nameDiv.appendChild(nameP);
    rowDiv.appendChild(nameDiv);
    //button div thats in line with name div
    var btnDiv = document.createElement("div");
    btnDiv.className = "col-md-auto";
    if (!leaf) { //if the task is decomposed already
        btnDiv.appendChild(buildButton("+", "addSubTask(\"" + parent.taskId + "\")", "secondary"));
        btnDiv.appendChild(buildButton("Rename", "renameTask(\"" + parent.taskId + "\")", "secondary"));
    }
    else { // if it is a 'leaf' (has no subtasks)
        btnDiv.appendChild(buildButton("Decompose", "decompTask(\"" + parent.taskId + "\")", "secondary"));
        btnDiv.appendChild(buildButton("Rename", "renameTask(\"" + parent.taskId + "\")", "secondary"));
        btnDiv.appendChild(buildButton("Assign", "assignTeammate(\"" + parent.taskId + "\")", "secondary"));
        btnDiv.appendChild(buildButton("✔", "markTask(\"" + parent.taskId + "\")", "secondary"));
    }
    rowDiv.appendChild(btnDiv);
    var tasksDiv = document.getElementById("tasks");
    tasksDiv.appendChild(rowDiv);
}

/**
 * Adds a teammate to the project.
 * First checks if it is valid, then calls API.
 * If API returns 200, then the teamview is updated
 */
function addTeammateToProject(){
    var form = document.getElementById("newTeammate")
    var req = form.value;
    form.value = "";
    //if the requested teammate name does not contain any characters
    if (!(/[a-zA-Z]/.test(req))) {
        //warning modal
        return;
    }
    //check if the teammate already exists within the project
    for (let index = 0; index < project.teammates.length; index++) {
        if (project.teammates[index].name == req) {
            //warning modal
            return;
        }
    }

    console.log("Requested teammate: " + req);

    //temporary
    project.teammates.push({name:req});
    createTeammateRow(req);
    //temporary

    /*
    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/project/" + project.name + "/addTeammate", true);
    xhr.send(JSON.stringify({"name" : req}));
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            project.teammates.push({name:req});
            createTeammateRow(req);
            console.log("Successfully added teammate: " + req);
        }
        else {
            console.log("invalid teammate");
        }
    };
    */
}