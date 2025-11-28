<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Novo Empréstimo | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <nav class="navbar">
        <a href="dashboard.jsp" class="logo"><i class="fas fa-book-open"></i> Biblioteca</a>
        <div class="nav-links">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="EmprestimoController">Voltar</a>
        </div>
    </nav>

    <div class="container container-small">
        <div class="page-header">
            <h1><i class="fas fa-hand-holding-open"></i> Novo Empréstimo</h1>
            <p>Selecione o usuário e o livro para registrar a saída.</p>
        </div>

        <form action="EmprestimoController" method="post">
            <input type="hidden" name="action" value="emprestar" />

            <div class="form-group">
                <label>Selecione o Usuário</label>
                <select name="usuarioId" required>
                    <option value="">-- Buscar Usuário --</option>
                    <c:forEach var="u" items="${listaUsuarios}">
                        <option value="${u.usuarioId}">${u.nome} (CPF: ${u.cpf})</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Selecione o Livro</label>
                <select name="livroId" required>
                    <option value="">-- Buscar Livro Disponível --</option>
                    <c:forEach var="l" items="${listaLivros}">
                        <option value="${l.livroId}">
                            ${l.titulo} (Disp: ${l.quantidadeDisponivel})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-accent btn-block">
                Confirmar Empréstimo <i class="fas fa-check"></i>
            </button>
        </form>
        
        <div style="margin-top: 20px; text-align: center;">
            <a href="EmprestimoController" class="btn btn-outline">Cancelar</a>
        </div>
    </div>
</body>
</html>