<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Novo Usuário | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <div class="container container-small">
        <div class="page-header">
            <h1><i class="fas fa-user-plus"></i> Cadastro</h1>
            <p>Preencha os dados abaixo</p>
        </div>
        
        <form action="CadastroUsuario" method="post">
            <div class="form-group">
                <label>Nome Completo</label>
                <input type="text" name="nome" required>
            </div>
            
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>
            
            <div class="form-grid">
                <div class="form-group">
                    <label>Telefone</label>
                    <input type="text" name="telefone" required>
                </div>
                <div class="form-group">
                    <label>CPF</label>
                    <input type="text" name="cpf" required>
                </div>
            </div>
            
            <div class="form-group">
                <label>Senha</label>
                <input type="password" name="senha" required>
            </div>
            
            <div class="form-group">
                <label>Tipo de Perfil</label>
                <div class="radio-group">
                    <label style="margin:0; font-weight:normal; cursor:pointer;">
                        <input type="radio" name="tipoUsuario" value="membro" checked> Leitor
                    </label>
                    <label style="margin:0; font-weight:normal; cursor:pointer;">
                        <input type="radio" name="tipoUsuario" value="admin"> Admin
                    </label>
                </div>
            </div>
            
            <button type="submit" class="btn btn-success btn-block">Cadastrar</button>
        </form>
        
        <div style="margin-top: 15px; text-align: center;">
            <a href="dashboard.jsp" class="btn btn-outline">Voltar</a>
        </div>
    </div>
</body>
</html>