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
    if (project.isArchived) {
        addBtn.setAttribute("disabled", null);
    }
    document.getElementById("addBtn").appendChild(addBtn);
}

/**
 * Adds a teammate to the project.
 * First checks if it is valid, then calls API.
 * If API returns 200, then the teamview is updated
 */
 function addTeammate(){
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
            error("Teammate already exists");
            return;
        }
    }

    console.log("Requested teammate: " + req);

    var path = "/project/" + project.name + "/addTeammate";
    var js = {"projectName" : project.name,"teammateName":req};
    post(path, js);
}

/**
 * Helper for loadTeamView(). Used to create a row div, and append to the document
 * @param {string} req 
 */
 function createTeammateRow(req){
    var rowDiv = document.createElement("div");
    rowDiv.className = "row";
    rowDiv.id = req;
    var colDiv = document.createElement("div");
    colDiv.className = "col-md-6";
    var nameP = document.createElement("p");
    nameP.innerHTML = req;
    colDiv.appendChild(nameP);
    rowDiv.appendChild(colDiv);
    var btnDiv = document.createElement("div");
    btnDiv.className = "col-md-auto";
    var removeBtn = buildButton("Remove", "removeTeammate(\"" + req + "\")", "danger");
    if(project.isArchived){
        removeBtn.setAttribute("disabled", null);
    }
    btnDiv.appendChild(removeBtn);
    rowDiv.appendChild(btnDiv);
    document.getElementById("team").appendChild(rowDiv);
    rowDiv.appendChild(document.createElement("hr"));
}

function removeTeammate(name){
    console.log("Attempting to delete: " + name);

    var path = "/project/" + project.name + "/removeTeammate";
    var js = {"projectName" : project.name,"teammateName":name};
    post(path,js);
}