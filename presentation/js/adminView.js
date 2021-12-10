var projectNames = [];
var projects = []
/**
 * Loads the admin view
 */
function loadAdminView(){
    var xhr = new XMLHttpRequest();
    xhr.open("GET", api_url + "/admin", true);
    xhr.send();
    xhr.onloadend = function () {        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            var js = JSON.parse(xhr.responseText);
            if (js["statusCode"] != "200") {return;}
            var projectList = js["list"];
            projects = projectList;
            console.log(projectList);
            for (let i = 0; i < projectList.length; i++) {
                projectNames[i] = projectList[i].name;
            }
            generateProjectRows();
        } else {
            error(js["error"]);
            console.log("error has occurred");
        }
    };
}

/**
 * Generates divs for each project, with button
 */
function generateProjectRows(){
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
        var name = buildButton(projectNames[index] + " | " + (Math.round(projects[index].progress*10000)/100).toFixed(2) + "%", "goTo(\"" + projectNames[index] + "\")", "link");
        name.style.color = "white";
        name.style.fontSize = "1.5rem";
        /*
        document.createElement("h3");
        name.innerHTML = projectNames[index];*/
        nameDiv.appendChild(name);
        row.appendChild(nameDiv);

        //archive button div
        var archDiv = document.createElement("div");
        archDiv.classList = "col-md-3";
        var archBtn = buildButton("Archive", "archiveProject(\"" + projectNames[index] + "\")", "warning");
        if(projects[index].isArchived){
            archBtn.setAttribute("disabled",null);
        }
        archDiv.appendChild(archBtn);
        row.appendChild(archDiv);

        //delete button div
        var delDiv = document.createElement("div");
        delDiv.classList = "col-md-3";
        var delBtn = buildButton("Delete", "deleteProject(\"" + projectNames[index] + "\")", "danger");
        delDiv.appendChild(delBtn);

        
        row.appendChild(delDiv);

        section.appendChild(row);

        parentDiv.appendChild(section);
    }
}

function goTo(project){
    window.location.href = "/presentation/html/project.html?name=" + project;
}