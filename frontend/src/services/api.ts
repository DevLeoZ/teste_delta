const API_URL = "http://localhost:8080";

export async function login(email: string, password: string) {
    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email,
            password
        })
    });

    if (!response.ok) {
        throw new Error("Erro ao realizar login");
    }

    return response.json();
}

export async function register(
    name: string,
    lastName: string,
    email: string,
    password: string
) {

    const response = await fetch(`${API_URL}/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name,
            lastName,
            email,
            password
        })
    });

    return response.json();
}

export async function getTasks() {

    const response = await fetch(`${API_URL}/tasks`);

    return response.json();
}

export async function createTask(
    title: string,
    completed: boolean,
    userId: number
) {

    const response = await fetch(`${API_URL}/tasks`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title,
            completed,
            userId
        })
    });

    return response.json();
}