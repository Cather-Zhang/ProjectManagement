var requestedParent;
/**
 * Load the task view on the left of the screen
 */
function loadTaskView(){
    var t = document.getElementById("tasks")
    var topLevelBtn = buildButton("+", "openModal(\"\", \"addTaskModal\")", "secondary");
    topLevelBtn.id = null;
    var col = document.createElement("div");
    col.classList = "col-md-2";
    topLevelBtn.style.marginBottom = "1rem";
    col.appendChild(topLevelBtn);
    t.appendChild(col);
    //manually traverses through the top level tasks because they are not
    //a part of a parent task
    for (let index = 0; index < project.tasks.length; index++) {
        traverseTasks(project.tasks[index]);
    }
}

/**
 * Helper that recursively traverses the task tree of the project, and calls generateTaskRow()
 * @param {object} parent 
 */
function traverseTasks(parent){
    if (parent.subtasks == undefined || parent.subtasks == null || parent.subtasks.length == 0) {
        //generate leaf task rowoh n
        createTaskRow(parent, true);
        return;
    } else {
        //generate parent task row
        createTaskRow(parent, false);
    }
    for (let j = 0; j < parent.subtasks.length; j++) {
        //traverse through the rest of the list
        traverseTasks(parent.subtasks[j]);
    }
}

/**
 * Helper that actually generates the DOM elements, and a row div for the respective task
 * @param {object} parent 
 * @param {boolean} leaf 
 */
function createTaskRow(task, leaf){
    //new row for a new task
    var rowDiv = document.createElement("div");
    rowDiv.className = "row mb-3";
    rowDiv.id = task.prefix;
    //name div for the task name
    var nameDiv = document.createElement('div');
    nameDiv.className = "col-md-auto";
    nameDiv.style = "padding-left:" + ((task.prefix.match(/./g) || []).length * 1.5)+ "rem;";
    //paragraph element for the task name
    var nameP = document.createElement('p');
    if (task.isComplete && task.subtasks.length == 0) {
        nameP.innerHTML = "✔" + task.prefix + " " + task.name;
    }
    else {
        nameP.innerHTML = task.prefix + " " + task.name;
    }
    //append nameP to nameDiv and nameDiv to rowDiv
    nameDiv.appendChild(nameP);
    rowDiv.appendChild(nameDiv);
    //button div thats in line with name div
    var btnDiv = document.createElement("div");
    btnDiv.className = "col-md-auto";
    //teammate div
    var teammatesDiv = document.createElement("div");
    teammatesDiv.className = "row mb-3";
    teammatesDiv.id = "team" + task.prefix;
    for (let i = 0; i < task.assignees.length; i++){
        var t = document.createElement("div");
        t.className = "col-md-auto";
        t.style = "padding-left:" + ((task.prefix.match(/./g) || []).length * 1.5 + 1)+ "rem;";
        t.innerHTML = task.assignees[i].name;
        var buttonsT = buildButton("Unassign", "unassignTeammate(\"" + task.assignees[i].name + "\", \"" + task.prefix + "\")", "secondary");
        var btnTDiv = document.createElement("div");
        btnTDiv.className = "col-md-auto";
        btnTDiv.appendChild(buttonsT);
        teammatesDiv.appendChild(t);
        teammatesDiv.append(btnTDiv);
    }

    if (!leaf) { //if the task is decomposed already
        btnDiv.appendChild(buildButton("+", "openModal(\"" + task.prefix + "\", \"addTaskModal\")", "secondary"));
        btnDiv.appendChild(buildButton("Rename", "openModal(\"" + task.prefix + "\", \"renameModal\")", "secondary"));
    }
    else { // if it is a 'leaf' (has no subtasks)
        btnDiv.appendChild(buildButton("Decompose", "openModal(\"" + task.prefix + "\", \"decomposeModal\")", "secondary"));
        btnDiv.appendChild(buildButton("Rename", "openModal(\"" + task.prefix + "\", \"renameModal\")", "secondary"));
        btnDiv.appendChild(buildButton("Assign", "openModal(\"" + task.prefix + "\", \"assignModal\")", "secondary"));
        btnDiv.appendChild(buildButton("✔", "markTask(\"" + task.prefix + "\")", "secondary"));
    }
    rowDiv.appendChild(btnDiv);
    rowDiv.appendChild(teammatesDiv);
    var tasksDiv = document.getElementById("tasks");
    tasksDiv.appendChild(rowDiv);
}

function addTask(){
    var parentID = requestedParent;
    requestedParent = null;
    var form = document.getElementById("topLevelTaskName")
    var req = form.value;
    form.value = "";
    //if the requested teammate name does not contain any characters
    if (!(/[a-zA-Z]/.test(req))) {
        //warning modal
        form.value = "";
        return;
    }
    var path = "/project/" + project.name + "/task/add";
    var js = {"projectName":project.name,"parentPrefix":parentID,"taskName":req}
    post(path, js);
}

function decompTask(){
    var parentID = requestedParent;
    requestedParent = null;
    console.log(parentID);
    var form = document.getElementById("decompTasks")
    var req = form.value;
    form.value = "";
    //if the requested teammate name does not contain any characters
    if (!(/[a-zA-Z]/.test(req))) {
        //warning modal
        form.value = "";
        return;
    }
    var tasks = req.split(',');

    var path = "/project/" + project.name + "/task/" + parentID + "/decomposeTask";
    var js = {"projectName":project.name,"parentPrefix":parentID,"taskNames":tasks};
    post(path, js);
}

function assignTeammate(){
    var parentID = requestedParent;
    requestedParent = null;
    var form = document.getElementById("assignee")
    var req = form.value;
    form.value = "";
    //if the requested teammate name does not contain any characters
    if (!(/[a-zA-Z]/.test(req))) {
        //warning modal
        form.value = "";
        return;
    }

    var path = "/project/" + project.name + "/task/" + parentID + "/assign";
    var js = {"projectName":project.name,"taskPrefix":parentID,"teammateName":req};
    post(path, js);
}

function unassignTeammate(assignee, prefix){
    var path = "/project/" + project.name + "/task/" + prefix + "/unassign";
    var js = {"projectName":project.name,"taskPrefix":prefix,"teammateName":assignee};
    post(path, js);
}

function markTask(parentID){
    var path = "/project/" + project.name + "/task/" + parentID + "/markTask";
    var js = {"projectName":project.name,"taskPrefix":parentID}
    post(path, js)
}

function renameTask(){
    var parentID = requestedParent;
    requestedParent = null;
    var form = document.getElementById("rename")
    var req = form.value;
    form.value = "";
    //if the requested teammate name does not contain any characters
    if (!(/[a-zA-Z]/.test(req))) {
        //warning modal
        form.value = "";
        return;
    }

    var path = "/project/" + project.name + "/task/" + parentID + "/rename";
    var js = {"projectName":project.name,"taskPrefix":parentID,"newTaskName":req}
    post(path, js)
}