/**
 * Deletes the project with the given name
 * @param {string} name 
 */
function deleteProject(name) {
    post("/admin/delete", {"projectName" : name});
}

/**
 * Archives the project
 * @param {string} name 
 */
function archiveProject(name){
    post("/admin/archive", {"projectName" : name});
}