<%-- 
    Document   : editar-livro
    Created on : 25 de nov. de 2025, 20:41:37
    Author     : Thiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Editar Livro</title>
        <style>body{font-family:sans-serif; max-width:600px; margin:20px auto;} label{display:block; margin-top:10px;} input{width:100%; padding:5px;} button{margin-top:20px; padding:10px; width:100%; background-color:blue; color:white; cursor:pointer;}</style>
    </head>
    <body>
        <h1>Editar Livro</h1>
        
        <form action="EditarLivro" method="post">
            <input type="hidden" name="id" value="${livro.livroId}">
            
            <label>Título:</label> 
            <input type="text" name="titulo" value="${livro.titulo}" required>
            
            <label>Autor:</label> 
            <input type="text" name="autor" value="${livro.autor}" required>
            
            <label>Editora:</label> 
            <input type="text" name="editora" value="${livro.editora}">
            
            <label>Ano Publicação:</label> 
            <input type="number" name="ano" value="${livro.anoPublicacao}" required>
            
            <label>ISBN:</label> 
            <input type="text" name="isbn" value="${livro.isbn}">
            
            <label>Gênero:</label> 
            <input type="text" name="genero" value="${livro.genero}">
            
            <label>Quantidade Total:</label> 
            <input type="number" name="qtd" value="${livro.quantidadeTotal}" required>
            
            <button type="submit">Salvar Alterações</button>
        </form>
        
        <br>
        <a href="ListarLivros">Cancelar</a>
    </body>
</html>
