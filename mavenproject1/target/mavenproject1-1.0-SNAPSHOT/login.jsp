<%-- 
    Document   : Login
    Created on : 25 de nov. de 2025, 21:53:32
    Author     : Thiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head><title>Login - Biblioteca</title></head>
<body style="text-align: center; padding-top: 50px;">
    <h1>Acesso ao Sistema</h1>
    <div style="color: red">${erro}</div>
    
    <form action="LoginController" method="post" style="display: inline-block; border: 1px solid #ccc; padding: 20px;">
        Email: <input type="email" name="email" required><br><br>
        Senha: <input type="password" name="senha" required><br><br>
        <button type="submit">Entrar</button>
    </form>
    
    <br><br>
    <a href="CadastroUsuario">Criar Conta (Primeiro Acesso)</a>
</body>
</html>