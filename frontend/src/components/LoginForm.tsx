import { useState } from "react"

export default function LoginForm() {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    async function handleLogin(e: React.FormEvent) {
        e.preventDefault()

        const res = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email,
                password
            })
        })

        if (!res.ok) {
            alert("Login inválido")
            return
        }

        const data = await res.json()

        // Armazena token de autenticação e userId para uso nas requisições
        localStorage.setItem(
            "user",
            JSON.stringify({
                id: Number(data.token),
                token: data.token
            })
        )

        window.location.href = "/tasks"
    }

    return (
        <div className="auth-container">
            <form className="card" onSubmit={handleLogin}>
                <h1>Login</h1>

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Senha"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button type="submit">Entrar</button>
            </form>
        </div>
    )
}