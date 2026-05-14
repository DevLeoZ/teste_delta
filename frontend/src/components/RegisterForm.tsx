import { useState } from "react"
import "../styles/global.css"

export function RegisterForm() {
    const [name, setName] = useState("")
    const [lastName, setLastName] = useState("")
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")

    async function handleRegister(e: React.FormEvent) {
        e.preventDefault()

        const res = await fetch("http://localhost:8080/auth/register", {
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
        })

        if (!res.ok) {
            alert("Erro ao cadastrar")
            return
        }

        alert("Cadastro realizado com sucesso!")

        window.location.href = "/login"
    }

    return (
        <div className="auth-container">
            <form className="card" onSubmit={handleRegister}>
                <h1>Cadastro</h1>

                <input
                    type="text"
                    placeholder="Nome"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />

                <input
                    type="text"
                    placeholder="Sobrenome"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                />

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

                <button type="submit">Criar conta</button>

                <small>
                    Já tem conta?{" "}
                    <span
                        style={{
                            color: "#38bdf8",
                            cursor: "pointer",
                            textDecoration: "underline"
                        }}
                        onClick={() => window.location.href = "/login"}
                    >
                        faça login
                    </span>
                </small>
            </form>
        </div>
    )
}