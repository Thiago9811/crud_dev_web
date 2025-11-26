<%-- 
    Document   : cadastro-livro
    Created on : 26 de nov. de 2025, 01:42:24
    Author     : Thiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${sessionScope.usuarioLogado.tipoUsuario != 'ADMIN' && sessionScope.usuarioLogado.tipoUsuario != 'admin'}">
    <c:redirect url="dashboard.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Cadastrar Livro</title>
        <style>
            body{font-family:sans-serif; max-width:600px; margin:20px auto; padding: 20px;} 
            label{display:block; margin-top:10px;} 
            input{width:100%; padding:8px; margin-bottom: 10px; border: 1px solid #ccc; border-radius: 4px;} 
            button{margin-top:20px; padding:10px; width:100%; background-color:blue; color:white; cursor:pointer; border:none; border-radius: 4px;}
            .btn-cancel { background-color: #6c757d; margin-top: 10px; display: block; text-align: center; text-decoration: none; color: white; padding: 10px; border-radius: 4px;}
        </style>
    </head>
    <body>
        <h1>Novo Livro</h1>
        
        <form action="CadastroLivroController" method="post">
            <label>Título:</label> <input type="text" name="titulo" required>
            <label>Autor:</label> <input type="text" name="autor" required>
            <label>Editora:</label> <input type="text" name="editora">
            <label>Ano Publicação:</label> <input type="number" name="ano" required>
            <label>ISBN:</label> <input type="text" name="isbn">
            <label>Gênero:</label> <input type="text" name="genero">
            <label>Quantidade Total:</label> <input type="number" name="qtd" value="1" required>
            
            <button type="submit">Salvar Livro</button>
        </form>
        
        <a href="ListarLivros" class="btn-cancel">Cancelar / Voltar à Lista</a>
    </body>
</html>