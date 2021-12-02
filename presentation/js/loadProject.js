var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";
var project = {
    name: null,
    teammates: [{name:"Jair"},{name:"Brianna"},{name:"Austin"},{name:"Cather"}],
    tasks: [
        {taskId:"1", subTasks:[
            {taskId:"1.1", subTasks:[
                {taskId:"1.1.1", subTasks:null}
            ]}, 
            {taskId:"1.2", subTasks:null}
        ]},
        {taskId:"2", subTasks:[
            {taskId:"2.1", subTasks:null},
            {taskId:"2.2", subTasks:[
                {taskId:"2.2.1", subTasks:null},
                {taskId:"2.2.2", subTasks:null}
            ]}
        ]}
    ],
    archived: false,
    progress: 0
};

function loadProject(name) {
    console.log("Requested project: " + name);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", api_url + "/project/" + name, true);
    xhr.send();
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log("Response: " + xhr.response);
            var js = JSON.parse(xhr.responseText);
            //TODO CHANGE BACK TO PROJECT = JS["P"]
            project.name = js["p"]["name"];
            console.log("Found project: " + JSON.stringify(project));
            document.getElementById("nameHeader").innerHTML = project.name;
            loadTaskView();
            loadTeam();
        }
        else {
            console.log("invalid project");
        }
    };
}

function loadTaskView(){
    let depth = 0;
    //manually traverses through the top level tasks because they are not
    //a part of a parent task
    for (let index = 0; index < project.tasks.length; index++) {
        traverseTasks(project.tasks[index], depth+1);
    }
}

function loadTeam(){
    var teamDiv = document.getElementById("team");
    var teammateTitle = document.createElement("h2");
    teammateTitle.innerHTML = "Teammates"; 
    teamDiv.appendChild(teammateTitle);
    for(let i = 0; i < project.teammates.length; i++) {
        createTeammateRow(project.teammates[i].name);
    }
    var addBtn = document.createElement("button");
    addBtn.setAttribute("type", "button");
    addBtn.setAttribute("class", "btn btn-secondary");
    addBtn.setAttribute("data-bs-toggle", "modal");
    addBtn.setAttribute("data-bs-target", "#addTeammateModal");
    addBtn.innerHTML = "Add";
    document.getElementById("addBtn").appendChild(addBtn);
}

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
    var removeBtn = buildButton("Remove", "removeTeammate(\"" + req + "\")")
    btnDiv.appendChild(removeBtn);
    rowDiv.appendChild(btnDiv);
    document.getElementById("team").appendChild(rowDiv);
    rowDiv.appendChild(document.createElement("hr"));
}

/**
 * Helper that physically traverses the task tree of the project, and calls generateTaskRow()
 * @param {object} parent 
 * @param {number} depth 
 * @returns 
 */
function traverseTasks(parent, depth){
    if (parent.subTasks == undefined || parent.subTasks == null) {
        generateTaskRow(parent, depth, true);
        return;
    } else {
        generateTaskRow(parent, depth, false);
    }
    for (let j = 0; j < parent.subTasks.length; j++) {
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
    nameP.innerHTML = parent.taskId;
    //append nameP to nameDiv and nameDiv to rowDiv
    nameDiv.appendChild(nameP);
    rowDiv.appendChild(nameDiv);
    //button div thats in line with name div
    var btnDiv = document.createElement("div");
    btnDiv.className = "col-md-auto";
    if (!leaf) { //if the task is decomposed already
        btnDiv.appendChild(buildButton("+", "addTask()"));
        btnDiv.appendChild(buildButton("Rename", "renameTask(" + parent.taskId + ")"));
    }
    else { // if it is a 'leaf' (has no subtasks)
        btnDiv.appendChild(buildButton("Decompose", "decompTask(" + parent.taskId + ")"));
        btnDiv.appendChild(buildButton("Rename", "renameTask(" + parent.taskId + ")"));
        btnDiv.appendChild(buildButton("Assign", "assignTeammate(" + parent.taskId + ")"));
        btnDiv.appendChild(buildButton("Mark", "markTask(" + parent.taskId + ")"));
    }
    rowDiv.appendChild(btnDiv);
    var tasksDiv = document.getElementById("tasks");
    tasksDiv.appendChild(rowDiv);
}

function addTeammate(){
    var req = document.getElementById("newTeammate").value;
    for (let index = 0; index < project.teammates.length; index++) {
        if (project.teammates[index].name == req) {
            //warning modal
            return;
        }
    }

    console.log("Requested project: " + req);
    project.teammates.push({name:req});
    createTeammateRow(req);
    
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