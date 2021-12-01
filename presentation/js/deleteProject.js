var api_url = "https://28q0071kya.execute-api.us-east-2.amazonaws.com/beta";

function deleteProject(name) {
    console.log("hello");
    console.log("Project Name: " + name);
    var js = JSON.stringify({"projectName" : name});
    console.log("Request body: " + js);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", api_url + "/admin/delete", true);   
    xhr.send(js);

    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            console.log("Deleted project " + name);
            window.location.reload();
        } else {
            //DO NOTHING
        }
    };
}
