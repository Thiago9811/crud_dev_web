<%-- 
    Document   : listar-livros
    Created on : 25 de nov. de 2025, 20:26:59
    Author     : Thiago
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Acervo de Livros</title>
        <style>
            body { font-family: sans-serif; padding: 20px; text-align: center; }
            table { margin: 20px auto; border-collapse: collapse; width: 95%; font-size: 14px;}
            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
            th { background-color: #007bff; color: white; }
            tr:nth-child(even) { background-color: #f2f2f2; }
            .disponivel { color: green; font-weight: bold; }
            .indisponivel { color: red; font-weight: bold; }
        </style>
    </head>
    <body>
        <h1>Acervo da Biblioteca</h1>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Editora</th>
                    <th>Ano</th>
                    <th>Gênero</th>
                    <th>ISBN</th>
                    <th>Total</th>
                    <th>Disp.</th> </tr>
            </thead>
            <tbody>
                <th>Ações</th>
                <c:forEach var="l" items="${listaDeLivros}">
                    <tr>
                        <td>${l.livroId}</td>
                        <td>${l.titulo}</td>
                        <td>${l.autor}</td>
                        <td>${l.editora}</td>
                        <td>${l.anoPublicacao}</td>
                        <td>${l.genero}</td>
                        <td>${l.isbn}</td>
                        <td>${l.quantidadeTotal}</td>
                        <td>${l.quantidadeDisponivel}</td>

                        <td>
                            <a href="EditarLivro?id=${l.livroId}">Editar</a>
                            |
                            <a href="ExcluirLivro?id=${l.livroId}" onclick="return confirm('Tem certeza que deseja excluir este livro?');" style="color:red;">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <br>
        <form action="CadastroLivroController" method="get">
            <button type="submit" style="background-color: blue; color: white; padding: 10px; cursor: pointer;">Cadastrar Novo Livro</button>
        </form>
        <br>
        <a href="dashboard.jsp">Voltar ao Menu Principal</a>
    </body>
</html>