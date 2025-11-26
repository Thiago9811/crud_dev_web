<%-- 
    Document   : cadastro-usuario
    Created on : 25 de nov. de 2025, 23:38:20
    Author     : Thiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Novo Cadastro</title>
        <style>/* Seus estilos CSS aqui */</style>
    </head>
    <body>
        <h1>Cadastro de Usuário</h1>
        
        <form action="CadastroUsuario" method="post">
            <label>Nome:</label> <input type="text" name="nome" required><br>
            <label>Email:</label> <input type="email" name="email" required><br>
            <label>Telefone:</label> <input type="text" name="telefone" required><br>
            <label>CPF:</label> <input type="text" name="cpf" required><br>
            <label>Senha:</label> <input type="password" name="senha" required><br>
            
            <div class="radio-group">
                <input type="radio" id="membro" name="tipoUsuario" value="membro" checked>
                <label for="membro">Leitor</label>
                <input type="radio" id="admin" name="tipoUsuario" value="admin">
                <label for="admin">Admin</label>
            </div>
            
            <button type="submit">Salvar</button>
        </form>
        
        <a href="dashboard.jsp">Voltar</a>
    </body>
</html>