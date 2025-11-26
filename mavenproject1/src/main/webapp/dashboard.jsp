<%-- 
    Document   : Dashboard
    Created on : 25 de nov. de 2025, 21:54:12
    Author     : Thiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty sessionScope.usuarioLogado}">
    <c:redirect url="LoginController"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Painel Principal</title>
    <style>
        .painel { display: flex; justify-content: center; gap: 20px; margin-top: 30px; }
        .card { border: 1px solid #ccc; padding: 20px; width: 200px; text-align: center; border-radius: 8px; }
        .card h3 { border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .btn { display: block; width: 100%; padding: 10px; margin-top: 5px; text-decoration: none; color: white; border-radius: 4px; }
        .btn-blue { background-color: #007bff; }
        .btn-green { background-color: #28a745; }
    </style>
</head>
<body>
    <div style="text-align: right; padding: 10px;">
        Olá, <b>${sessionScope.usuarioLogado.nome}</b> (${sessionScope.usuarioLogado.tipoUsuario}) | 
        <a href="LoginController?acao=logout" style="color: red;">Sair</a>
    </div>

    <h1 style="text-align: center;">Sistema de Biblioteca</h1>

    <div class="painel">
        <div class="card">
            <h3>Gerenciar Usuários</h3>
            <a href="UsuariosServlet" class="btn btn-green">Listar Usuários</a>
            
            <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                <a href="CadastroUsuario" class="btn btn-blue">Novo Usuário</a>
            </c:if>
        </div>

        <div class="card">
            <h3>Gerenciar Livros</h3>
            <a href="ListarLivros" class="btn btn-green">Acervo de Livros</a>
            <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                <a href="CadastroLivroController" class="btn btn-blue">Novo Livro</a>
            </c:if>
        </div>

        <div class="card">
            <h3>Empréstimos</h3>
            <a href="EmprestimoController" class="btn btn-green">Painel de Empréstimos</a>
        </div>
    </div>
</body>
</html>
