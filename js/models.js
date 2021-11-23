var task = {taskId:"",name:"",subtasks:[null],assignees:[null],parentTaskID:"",isCompleted:false}

var teammate ={name:"",tasks:[null]}

function loadProjectJSON(name, tasks){
    var project = {name:"",teammates:[null],tasks:[null],archived:false,progress:0}
    project.name = name;
    project.tasks = tasks;
    return project;
}