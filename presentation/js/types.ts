interface Teammate {
    name: string,
    tasks: string[]
}

interface Task {
    taskId: string,
    name: string,
    subTasks: Task[]
    assignees: string,
    isCompleted: false
}

interface Project {
    name: string,
    teammates: Teammate[],
    tasks: Task[]
    archived: boolean,
    progress: number
}
