/**
 * Deletes the project with the given name
 * @param {string} name 
 */
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
            var js = JSON.parse(xhr.responseText);
            if (js["statusCode"] != "200") {return;}
            console.log ("XHR:" + xhr.responseText);
            console.log("Deleted project " + name);
            window.location.reload();
        }
    };
}
