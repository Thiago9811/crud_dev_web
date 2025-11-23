<%-- 
    Document   : gerenciar-emprestimos.jsp
    Created on : 23 de nov. de 2025, 13:40:22
    Author     : Thiago
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestão de Empréstimos</title>
</head>
<body>
    <h1>Biblioteca - Controle de Empréstimos</h1>
    
    <c:if test="${not empty msgSistema}">
        <div style="background-color: #ddd; padding: 10px; margin-bottom: 10px;">
            <b>${msgSistema}</b>
        </div>
    </c:if>

    <div style="border: 1px solid blue; padding: 15px;">
        <h3>Novo Empréstimo</h3>
        <form action="EmprestimoController" method="post">
            <input type="hidden" name="acao" value="emprestar">
            
            <label>ID Usuário:</label> <input type="number" name="usuarioId" required>
            <label>ID Livro:</label> <input type="number" name="livroId" required>
            
            <button type="submit">Confirmar Empréstimo</button>
        </form>
    </div>
    <br>

    <h3>Histórico de Empréstimos</h3>
    <table border="1" width="100%">
        <thead>
            <tr>
                <th>ID</th>
                <th>Livro</th>
                <th>Usuário</th>
                <th>Data Emp.</th>
                <th>Data Prevista</th>
                <th>Data Real</th>
                <th>Status</th>
                <th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="emp" items="${listaEmprestimos}">
                <tr>
                    <td>${emp.emprestimoId}</td>
                    <td>${emp.tituloLivroAux}</td> <td>${emp.nomeUsuarioAux}</td> <td>${emp.dataEmprestimo}</td>
                    <td>${emp.dataDevolucaoPrevista}</td>
                    <td>${emp.dataDevolucaoReal}</td>
                    <td>
                        <c:choose>
                            <c:when test="${emp.statusEmprestimo == 'ATIVO'}">
                                <span style="color:green; font-weight:bold">EM ABERTO</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color:gray">${emp.statusEmprestimo}</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${emp.statusEmprestimo == 'ATIVO'}">
                            <form action="EmprestimoController" method="post">
                                <input type="hidden" name="acao" value="devolver">
                                <input type="hidden" name="emprestimoId" value="${emp.emprestimoId}">
                                <button type="submit" style="color:red">Devolver</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <br>
    <a href="index.html">Voltar ao Menu</a>
</body>
</html>