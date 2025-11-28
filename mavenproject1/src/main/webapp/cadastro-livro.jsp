<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${sessionScope.usuarioLogado.tipoUsuario != 'ADMIN' && sessionScope.usuarioLogado.tipoUsuario != 'admin'}">
    <c:redirect url="dashboard.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Novo Livro | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <div class="container container-small">
        <div class="page-header">
            <h1><i class="fas fa-book-medical"></i> Cadastrar Livro</h1>
            <p>Adicione um novo título ao acervo</p>
        </div>
        
        <form action="CadastroLivroController" method="post" class="form-grid" style="grid-template-columns: 1fr;">
            <div class="form-group">
                <label>Título</label>
                <input type="text" name="titulo" required placeholder="Ex: Dom Casmurro">
            </div>
            
            <div class="form-group">
                <label>Autor</label>
                <input type="text" name="autor" required placeholder="Ex: Machado de Assis">
            </div>
            
            <div class="form-grid">
                <div class="form-group">
                    <label>Editora</label>
                    <input type="text" name="editora">
                </div>
                <div class="form-group">
                    <label>Ano</label>
                    <input type="number" name="ano" required>
                </div>
            </div>
            
            <div class="form-grid">
                <div class="form-group">
                    <label>ISBN</label>
                    <input type="text" name="isbn">
                </div>
                <div class="form-group">
                    <label>Qtd. Total</label>
                    <input type="number" name="qtd" value="1" required>
                </div>
            </div>
            
            <div class="form-group">
                <label>Gênero</label>
                <input type="text" name="genero">
            </div>
            
            <button type="submit" class="btn btn-primary btn-block">Salvar Livro</button>
        </form>
        
        <div style="margin-top: 15px; text-align: center;">
            <a href="ListarLivros" class="btn btn-outline">Cancelar</a>
        </div>
    </div>
</body>
</html>