import { useEffect, useState } from "react"

type Task = {
    id: number
    title: string
    completed: boolean
    user: {
        id: number
        name: string
    }
}

export default function TaskPage() {

    const [tasks, setTasks] = useState<Task[]>([])
    const [title, setTitle] = useState("")
    const [userId, setUserId] = useState<number | null>(null)

    // pega usuário logado
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem("user") || "{}")

        if (!user?.id) {
            window.location.href = "/"
            return
        }

        setUserId(user.id)
    }, [])

    // só carrega tasks quando userId existir
    useEffect(() => {
        if (userId) {
            loadTasks()
        }
    }, [userId])

    async function loadTasks() {
        const res = await fetch(`http://localhost:8080/tasks/user/${userId}`)
        const data = await res.json()
        setTasks(data)
    }

    async function createTask() {
        if (!title || !userId) return

        await fetch("http://localhost:8080/tasks", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title,
                completed: false,
                userId
            })
        })

        setTitle("")
        loadTasks()
    }

    async function toggleTask(task: Task) {
        if (!userId) return

        await fetch(`http://localhost:8080/tasks/${task.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: task.title,
                completed: !task.completed,
                userId
            })
        })

        loadTasks()
    }

    async function deleteTask(id: number) {
        await fetch(`http://localhost:8080/tasks/${id}`, {
            method: "DELETE"
        })

        loadTasks()
    }

    function logout() {
        localStorage.removeItem("user")
        window.location.href = "/"
    }

    return (
        <div className="auth-container">
            <div
                className="card"
                style={{
                    width: "850px",
                    padding: "30px",
                    position: "relative"
                }}
            >

                {/* HEADER */}
                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        marginBottom: "20px"
                    }}
                >
                    <h1 style={{ margin: 0 }}>
                        Minhas Tarefas
                    </h1>

                    <button
                        onClick={logout}
                        style={{
                            background: "#ef4444",
                            color: "white",
                            border: "none",
                            padding: "8px 14px",
                            borderRadius: "8px",
                            cursor: "pointer",
                            fontWeight: 600,
                            fontSize: "13px",
                            height: "36px",
                            width: "auto"
                        }}
                        onMouseOver={(e) => {
                            (e.target as HTMLButtonElement).style.background = "#dc2626"
                        }}
                        onMouseOut={(e) => {
                            (e.target as HTMLButtonElement).style.background = "#ef4444"
                        }}
                    >
                        Sair
                    </button>
                </div>

                {/* INPUT */}
                <div
                    style={{
                        display: "flex",
                        gap: "10px",
                        marginBottom: "20px"
                    }}
                >
                    <input
                        placeholder="Digite uma nova tarefa..."
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        style={{
                            flex: 1,
                            height: "42px",
                            padding: "0 12px",
                            fontSize: "14px",
                            borderRadius: "10px",
                            border: "1px solid #334155",
                            background: "#0f172a",
                            color: "#e5e7eb",
                            outline: "none"
                        }}
                    />

                    <button
                        onClick={createTask}
                        style={{
                            width: "50%",
                            height: "42px",
                            padding: "0 16px",
                            borderRadius: "10px"
                        }}
                    >
                        Adicionar
                    </button>
                </div>

                {/* TABLE */}
                <table
                    style={{
                        width: "100%",
                        borderCollapse: "collapse",
                        fontSize: "14px"
                    }}
                >
                    <thead>
                    <tr style={{ textAlign: "left", borderBottom: "1px solid #334155" }}>
                        <th>ID</th>
                        <th>Tarefa</th>
                        <th>Status</th>
                        <th>Usuário</th>
                        <th>Ações</th>
                    </tr>
                    </thead>

                    <tbody>
                    {tasks.length === 0 ? (
                        <tr>
                            <td colSpan={5} style={{ textAlign: "center", padding: "20px" }}>
                                Nenhuma tarefa cadastrada
                            </td>
                        </tr>
                    ) : (
                        tasks.map(task => (
                            <tr
                                key={task.id}
                                style={{ borderBottom: "1px solid #1f2937" }}
                            >
                                <td>{task.id}</td>
                                <td>{task.title}</td>

                                <td>
                                        <span
                                            style={{
                                                padding: "4px 8px",
                                                borderRadius: "6px",
                                                background: task.completed ? "#16a34a" : "#f59e0b",
                                                color: "white",
                                                fontSize: "12px"
                                            }}
                                        >
                                            {task.completed ? "Concluída" : "Pendente"}
                                        </span>
                                </td>

                                <td>{task.user?.name}</td>

                                <td style={{ display: "flex", gap: "6px" }}>
                                    <button onClick={() => toggleTask(task)}>
                                        Alterar/Status
                                    </button>

                                    <button
                                        onClick={() => deleteTask(task.id)}
                                        style={{
                                            background: "#ef4444",
                                            color: "white"
                                        }}
                                    >
                                        Excluir
                                    </button>
                                </td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>

            </div>
        </div>
    )
}