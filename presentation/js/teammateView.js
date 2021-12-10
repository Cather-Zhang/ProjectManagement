var teamList = [];

/**
 * Loads the teammate view at the bottom of the page, displaying teammates and their tasks
 */
function loadTeammateView(name){
    console.log("Requested project: " + name);
    var xhr = new XMLHttpRequest();
    xhr.open("GET", api_url + "/project/" + name + "/teammates", true);
    xhr.send();
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            var js = JSON.parse(xhr.responseText);
            if (js["statusCode"] != "200") {console.log("Error");return;}
            teamList = js["teammates"];
            console.log(teamList);
            document.getElementById("teamHeader").innerHTML = "Teammates for " + name;

            loadTeammateList();
        }
        else {
            error(js["error"]);
            console.log("Error with XHR");
        }
    };
}

function loadTeammateViewNav(){
    window.location.href = "/presentation/html/team.html?name=" + project.name;
}

function loadTeammateList(){
    for (let index = 0; index < teamList.length; index++) {
        var element = teamList[index];
        console.log(element);
        var rowDiv = document.createElement("div");
        rowDiv.classList = "row mb-3";
        //name div
        var nameDiv = document.createElement("div");
        nameDiv.classList = "col-md-3";
        var nameP = document.createElement("h2");
        nameP.innerHTML = element.name;
        nameDiv.appendChild(nameP);
        //tasks div
        var listDiv = document.createElement("div");
        listDiv.classList = "col-md-auto";
        for (let index = 0; index < element.tasks.length; index++) {
            var tasksDiv = document.createElement("div");
            tasksDiv.classList = "row";
            var task = element.tasks[index];
            console.log(task);
            var taskDiv = document.createElement("div");
            taskDiv.classList = "col-md-auto";
            var taskP = document.createElement("h4");
            taskP.innerHTML = task;
            taskDiv.appendChild(taskP);
            tasksDiv.appendChild(taskDiv);
            listDiv.appendChild(tasksDiv);
        }
        if(element.tasks.length == 0){
            var h = document.createElement("h4")
            h.innerHTML = "No Tasks Found"
            listDiv.appendChild(h);
        }
        rowDiv.appendChild(nameDiv);
        rowDiv.appendChild(listDiv);
        rowDiv.setAttribute("style", "border:1px solid white;");
        document.getElementById("team").appendChild(rowDiv);
    }
}