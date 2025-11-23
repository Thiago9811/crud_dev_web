<%-- 
    Document   : listar-usuarios
    Created on : 23 de nov. de 2025, 15:44:16
    Author     : Thiago
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Usuários</title>
        <style>
            body { font-family: sans-serif; padding: 20px; text-align: center; }
            table { margin: 20px auto; border-collapse: collapse; width: 80%; }
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #28a745; color: white; }
            tr:nth-child(even) { background-color: #f2f2f2; }
        </style>
    </head>
    <body>
        <h1>Usuários Cadastrados</h1>
        
        <table>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Email</th>
                <th>Telefone</th>
                <th>CPF</th>
            </tr>
            
            <c:forEach var="u" items="${listaDeUsuarios}">
                <tr>
                    <td>${u.usuarioId}</td>
                    <td>${u.nome}</td>
                    <td>${u.email}</td>
                    <td>${u.telefone}</td>
                    <td>${u.cpf}</td>
                </tr>
            </c:forEach>
        </table>
        
        <br>
        <a href="index.html">Voltar ao Menu</a>
    </body>
</html>