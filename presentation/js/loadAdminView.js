var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";
var projectNames = [];

function loadAdminView(){
    getProjectNames();
}

function getProjectNames(){
    var xhr = new XMLHttpRequest();
    xhr.open("GET", api_url + "/admin", true);
    xhr.send();

    xhr.onloadend = function () {        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            //console.log ("Response: " + xhr.response);
            var js = JSON.parse(xhr.responseText);
            //console.log(js);
            var projectList = js["list"];
            for (let i = 0; i < projectList.length; i++) {
                //console.log(projectList[i].name);
                projectNames[i] = projectList[i].name;
            }
            addElements();
        } else {
            console.log("error has occurred");
        }
    };
}

function addElements(){
    var parentDiv = document.getElementById("projects");
    for (let index = 0; index < projectNames.length; index++) {
        //section
        var section = document.createElement("section")
        section.classList = "container";
        section.style = "padding: 1rem;"

        //row
        var row = document.createElement("div");
        row.classList = "row mb-3";

        //nameDiv
        var nameDiv = document.createElement("div");
        nameDiv.classList = "col-md-6";
        var name = document.createElement("p");
        name.innerHTML = projectNames[index];
        nameDiv.appendChild(name);

        //archive button div
        var archDiv = document.createElement("div");
        archDiv.classList = "col-md-3";
        var archBtn = document.createElement("button");
        archBtn.classList = "btn btn-outline-primary";
        archBtn.type = "button";
        archBtn.setAttribute("onclick", "archiveProject(\"" + projectNames[index] + "\")");
        archBtn.innerHTML = "Archive";
        archDiv.appendChild(archBtn);

        //delete button div
        var delDiv = document.createElement("div");
        delDiv.classList = "col-md-3";
        var delBtn = document.createElement("button");
        delBtn.classList = "btn btn-outline-primary";
        delBtn.type = "button";
        delBtn.setAttribute("onclick", "deleteProject(\"" + projectNames[index] + "\")");
        delBtn.innerHTML = "Delete";
        delDiv.appendChild(delBtn);

        row.appendChild(nameDiv);
        row.appendChild(archDiv);
        row.appendChild(delDiv);

        section.appendChild(row);

        parentDiv.appendChild(section);
    }
}

function buildElement(type, attributes){
    var e = document.createElement(type);
    for(let i = 0; i < attributes.length; i++){
        var att = attributes[i][0];
        var val = attributes[i][1];
        e.setAttribute(att, val);
    }
    return e;
}

/*
<section class="container" style="padding: 1rem;">
    <div class="row align-items-center mb-3">
        <div class="col-md-6">
            <p>
                "Project Name" (Identifier, Name, Status)
            </p>
        </div>
        <div class="col-md-3">
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">Archive</button>
        </div> 
        <div class="col-md-3">
            <button type="button" onclick="handleClick()" class="btn btn-outline-primary">Delete</button>
        </div>
    </div>  
</section>
*/