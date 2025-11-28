<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Usuário | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <div class="container container-small">
        <div class="page-header">
            <h1><i class="fas fa-user-edit"></i> Editar Usuário</h1>
        </div>
        
        <form action="EditarUsuario" method="post">
            <input type="hidden" name="id" value="${usuario.usuarioId}">
            
            <div class="form-group">
                <label>Nome</label>
                <input type="text" name="nome" value="${usuario.nome}" required>
            </div>
            
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" value="${usuario.email}" required>
            </div>
            
            <div class="form-grid">
                <div class="form-group">
                    <label>Telefone</label>
                    <input type="text" name="telefone" value="${usuario.telefone}">
                </div>
                <div class="form-group">
                    <label>CPF</label>
                    <input type="text" name="cpf" value="${usuario.cpf}">
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary btn-block">Salvar Alterações</button>
        </form>
        
        <div style="margin-top: 15px; text-align: center;">
            <a href="UsuariosServlet" class="btn btn-outline">Cancelar</a>
        </div>
    </div>
</body>
</html>