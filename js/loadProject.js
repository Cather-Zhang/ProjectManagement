var projectName = "";

function loadName(name) {
    projectName = name;
    console.log(projectName);
    updateHeader();
}

function updateHeader(){
    var h = document.getElementById("projectNameH");
    h.innerHTML = projectName;
}