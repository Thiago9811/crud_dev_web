<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Acesso | Biblioteca</title>
    <!-- Importando o CSS global -->
    <link rel="stylesheet" href="style.css">
    <!-- Ícones FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<!-- O style inline aqui serve apenas para centralizar VERTICALMENTE o cartão de login na tela -->
<body style="display: flex; align-items: center; justify-content: center; min-height: 100vh;">

    <!-- Usamos 'container-small' para o cartão ficar mais estreito e elegante -->
    <div class="container container-small" style="margin: 0;">
        
        <div class="page-header">
            <!-- Ícone grande de livro -->
            <i class="fas fa-book-reader" style="font-size: 4rem; color: var(--accent); margin-bottom: 1rem;"></i>
            <h1>Acesso ao Sistema</h1>
            <p>Bem-vindo à Biblioteca</p>
        </div>

        <!-- Mensagem de Erro Estilizada -->
        <c:if test="${not empty erro}">
            <div style="background: #f8d7da; color: #721c24; padding: 12px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #f5c6cb; font-weight: bold; text-align: center;">
                <i class="fas fa-exclamation-circle"></i> ${erro}
            </div>
        </c:if>
        
        <form action="LoginController" method="post">
            
            <div class="form-group">
                <label>E-mail</label>
                <!-- Input com ícone interno -->
                <div style="position: relative;">
                    <i class="fas fa-envelope" style="position: absolute; left: 15px; top: 14px; color: #999;"></i>
                    <input type="email" name="email" placeholder="Digite seu e-mail" required style="padding-left: 45px;">
                </div>
            </div>
            
            <div class="form-group">
                <label>Senha</label>
                <div style="position: relative;">
                    <i class="fas fa-lock" style="position: absolute; left: 15px; top: 14px; color: #999;"></i>
                    <input type="password" name="senha" placeholder="Digite sua senha" required style="padding-left: 45px;">
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary btn-block" style="margin-top: 20px;">
                ENTRAR <i class="fas fa-sign-in-alt"></i>
            </button>
        </form>

</body>
</html>