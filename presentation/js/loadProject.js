var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";
var project;

function loadProject(name){
    console.log("Requested project: " + name);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", api_url + "/project/" + name, true);
    xhr.send();

    xhr.onloadend = function () {        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("Response: " + xhr.response);
            var js = JSON.parse(xhr.responseText);
            project = js["p"];
            console.log("Found project: " + JSON.stringify(project));
            document.getElementById("nameHeader").innerHTML = project.name;
            createProjectView();
        } else {
            console.log("invalid project");
        }
    };
}

function testLoad(){
    project.tasks = [{name:"1"}, {name:"2"}, {name:"3"}];
    var listDiv = document.getElementById('list-puntate');
    var ul = document.createElement('ul');
    listDiv.appendChild(ul);
    for(var i = 0; i < project.tasks.length; ++i) {
        var li = document.createElement('li');
        li.innerHTML = project.tasks[i].name
        console.log(project.tasks[i]);
        ul.appendChild(li);                                 
    }
}

/**
 * creates a child task, meaning has decomp button and
 */
function createProjectView(){
    var rowDiv = document.createElement("div");
    rowDiv.className = "row mb-3";
    var nameDiv = document.createElement('div');
    nameDiv.className = "col-md-2";
    var nameP = document.createElement('p');
    nameP.innerHTML = "1. " + project.tasks[0];
    nameDiv.appendChild(nameP); 
    rowDiv.appendChild(nameDiv);
    var tasksDiv = document.getElementById("tasks");
    tasksDiv.appendChild(rowDiv);
}

/*
function loadTasks(){
    var section = <section class="container" style="padding: 0;padding-left: 0rem;">
    <div class="row mb-3">
        <div class="col-md-2">
        <p>
            1. Name of Task
        </p>
        </div>
        <div class="col-md-10">
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">+</button>
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">Rename</button>
        </div> 
    </div>  
    </section>
}
*/
/**
<section class="container" style="padding: 0;">
    <div class="row mb-3">
        <div class="col-md-2">
        <p>
            1. Name of Task
        </p>
        </div>
        <div class="col-md-10">
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">+</button>
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">Rename</button>
        </div> 
    </div>  
</section>


<section class="container" style="padding: 0rem;padding-left: 3rem;">
    <div class="row mb-3">
        <div class="col-md-2">
        <p>
            1.1 Name of Task
        </p>
        </div>
        <div class="col-md-10">
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">Decompose</button>
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">Rename</button>
        </div> 
    </div>  
</section>
<section class="container" style="padding: 0rem;padding-left: 6rem;">
    <div class="row mb-3">
        <div class="col-md-2">
        <p>
            Teammate A
        </p>
        </div>
        <div class="col-md-10">
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">+</button>
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">-</button>
        </div> 
    </div>  
</section>
 */