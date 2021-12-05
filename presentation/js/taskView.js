var requestedParent;
/**
 * Load the task view on the left of the screen
 */
function loadTaskView(){
    var t = document.getElementById("tasks")
    var topLevelBtn = buildButton("+", "addTask(" + null + ")", "secondary");
    topLevelBtn.id = null;
    var col = document.createElement("div");
    col.classList = "col-md-2";
    topLevelBtn.style.marginBottom = "1rem";
    col.appendChild(topLevelBtn);
    t.appendChild(col);
    let depth = 0;
    //manually traverses through the top level tasks because they are not
    //a part of a parent task
    for (let index = 0; index < project.tasks.length; index++) {
        traverseTasks(project.tasks[index], depth);
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
        createTaskRow(parent, depth, true);
        return;
    } else {
        //generate parent task row
        createTaskRow(parent, depth, false);
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
function createTaskRow(task, depth, leaf){
    //new row for a new task
    var rowDiv = document.createElement("div");
    rowDiv.className = "row mb-3";
    rowDiv.id = task.prefix;
    //name div for the task name
    var nameDiv = document.createElement('div');
    nameDiv.className = "col-md-auto";
    nameDiv.style = "padding-left:" + (depth*2 - 1)+ "rem;";
    //paragraph element for the task name
    var nameP = document.createElement('p');
    nameP.innerHTML = task.prefix + " " + task.name;
    //append nameP to nameDiv and nameDiv to rowDiv
    nameDiv.appendChild(nameP);
    rowDiv.appendChild(nameDiv);
    //button div thats in line with name div
    var btnDiv = document.createElement("div");
    btnDiv.className = "col-md-auto";
    if (!leaf) { //if the task is decomposed already
        btnDiv.appendChild(buildButton("+", "addTask(\"" + task.prefix + "\")", "secondary"));
        btnDiv.appendChild(buildButton("Rename", "renameTask(\"" + task.prefix + "\")", "secondary"));
    }
    else { // if it is a 'leaf' (has no subtasks)
        btnDiv.appendChild(buildButton("Decompose", "decompTask(\"" + task.prefix + "\")", "secondary"));
        btnDiv.appendChild(buildButton("Rename", "renameTask(\"" + task.prefix + "\")", "secondary"));
        btnDiv.appendChild(buildButton("Assign", "assignTeammate(\"" + task.prefix + "\")", "secondary"));
        btnDiv.appendChild(buildButton("✔", "markTask(\"" + task.prefix + "\")", "secondary"));
    }
    rowDiv.appendChild(btnDiv);
    var tasksDiv = document.getElementById("tasks");
    tasksDiv.appendChild(rowDiv);
}

function addTask(parentID){
    $('#addTaskModal').modal('show');
    if(parentID != null || parentID != undefined || parentID != ""){
        requestedParent = parentID;
    }
    var form = document.getElementById("topLevelTaskName")
    var req = form.value;
    form.value = "";
    //if the requested teammate name does not contain any characters
    if (!(/[a-zA-Z]/.test(req))) {
        //warning modal
        form.value = "";
        return;
    }

    console.log("Attempting to add task: " + req + " to " + requestedParent);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/project/" + project.name + "/task/add", true);
    xhr.send(JSON.stringify({"projectName" : project.name,"parentPrefix":parentID,"taskName":req}));
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log(xhr.responseText);
            var js = JSON.parse(xhr.responseText);
            if (js["statusCode"] != "200") {return;}
            var newTask;
            if(parentID == null){
                newTask = {ID:project.tasks.length+1 + "",name:req};
            }
            //TODO make it traverse through the task list from the parentID, then append a task to its subtasks
            else {
                var pathArr = parentID.split();
                newTask = {ID:project.tasks[pathArr[0]] + "",name:req};
            }
            project.tasks.push(newTask);
            var depth = newTask.prefix.split(".").length-1;
            createTaskRow(newTask, depth, true);
            console.log("Successfully added task: " + req + " to " + requestedParent);

        }
        else {
            console.log("invalid teammate");
        }
    };
}

//TODO make function to parse parentID = 3.1.2 as parentID = [3][1][2] then for parentID.length do task.subtask[parentID[i]]