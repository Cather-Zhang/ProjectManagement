var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";

function createProject() {
    var projectName = document.getElementById("createProjectField").value;
    var js = 
    {
        name:JSON.stringify(projectName)
    };
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/project", true);
    xhr.send(js);

    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processCreateProjectResponse(xhr.responseText);
        } else {
            processCreateProjectResponse("N/A");
        }
    };
}

function processCreateProjectResponse(result) {
    // Can grab any DIV or SPAN HTML element and can then manipulate its
    // contents dynamically via javascript
    console.log("result:" + result);
    var js = JSON.parse(result);

    var computation = js["result"];
    var status      = js["statusCode"];
    
    if (status == 200) {      
        window.location.href = "/html/project/?name=" + computation.project.name;
    } else {
        var msg = js["error"];
        window.location.href = "/html/404/";
    }
}

function processAddResponse(result) {
    // Can grab any DIV or SPAN HTML element and can then manipulate its
    // contents dynamically via javascript
    console.log("result:" + result);
    var js = JSON.parse(result);

    var computation = js["result"];
    var status      = js["statusCode"];
    
    if (status == 200) {
        // Update computation result
        document.addForm.result.value = computation
    } else {
        var msg = js["error"];
        document.addForm.result.value = "error:" + msg
    }
}

function addClick() {
    var form = document.addForm;
    var arg1 = form.arg1.value;
    var arg2 = form.arg2.value;

    var data = {};
    data["arg1"] = arg1;
    data["arg2"] = arg2;

    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", add_url, true);

    // send the collected data as JSON
    xhr.send(js);

    // This will process results and update HTML as appropriate. 
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);
        
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processAddResponse(xhr.responseText);
        } else {
            processAddResponse("N/A");
        }
    };
}