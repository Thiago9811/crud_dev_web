<%-- 
    Document   : editor-usuario
    Created on : 24 de nov. de 2025, 23:18:06
    Author     : Thiago
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar Usuário</title>
    </head>
    <body>
        <h1>Editar Usuário</h1>
        
        <form action="EditarUsuario" method="post">
            <input type="hidden" name="id" value="${usuario.usuarioId}">
            
            <label>Nome:</label>
            <input type="text" name="nome" value="${usuario.nome}" required><br><br>
            
            <label>Email:</label>
            <input type="email" name="email" value="${usuario.email}" required><br><br>
            
            <label>Telefone:</label>
            <input type="text" name="telefone" value="${usuario.telefone}"><br><br>
            
            <label>CPF:</label>
            <input type="text" name="cpf" value="${usuario.cpf}"><br><br>
            
            <button type="submit">Salvar Alterações</button>
        </form>
        
        <br>
        <a href="UsuariosServlet">Cancelar</a>
    </body>
</html>