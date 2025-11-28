<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Livro | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <div class="container container-small">
        <div class="page-header">
            <h1><i class="fas fa-edit"></i> Editar Livro</h1>
        </div>
        
        <form action="EditarLivro" method="post">
            <input type="hidden" name="id" value="${livro.livroId}">
            
            <div class="form-group">
                <label>Título</label> 
                <input type="text" name="titulo" value="${livro.titulo}" required>
            </div>
            
            <div class="form-group">
                <label>Autor</label> 
                <input type="text" name="autor" value="${livro.autor}" required>
            </div>
            
            <div class="form-grid">
                <div class="form-group">
                    <label>Editora</label> 
                    <input type="text" name="editora" value="${livro.editora}">
                </div>
                <div class="form-group">
                    <label>Ano</label> 
                    <input type="number" name="ano" value="${livro.anoPublicacao}" required>
                </div>
            </div>
            
            <div class="form-grid">
                <div class="form-group">
                    <label>ISBN</label> 
                    <input type="text" name="isbn" value="${livro.isbn}">
                </div>
                <div class="form-group">
                    <label>Qtd Total</label> 
                    <input type="number" name="qtd" value="${livro.quantidadeTotal}" required>
                </div>
            </div>

            <div class="form-group">
                <label>Gênero</label> 
                <input type="text" name="genero" value="${livro.genero}">
            </div>
            
            <button type="submit" class="btn btn-primary btn-block">Salvar Alterações</button>
        </form>
        
        <div style="margin-top: 15px; text-align: center;">
            <a href="ListarLivros" class="btn btn-outline">Cancelar</a>
        </div>
    </div>
</body>
</html>