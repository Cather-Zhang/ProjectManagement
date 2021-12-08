/**
 * Deletes the project with the given name
 * @param {string} name 
 */
function deleteProject(name) {
    post("/admin/delete", {"projectName" : name});
}
