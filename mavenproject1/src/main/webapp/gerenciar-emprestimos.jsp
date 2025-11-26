<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<table border="1" width="100%">
        <thead>
            <tr>
                <th>ID</th><th>Livro</th><th>Usuário</th><th>Data Emp.</th><th>Data Prevista</th><th>Status</th><th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="e" items="${listaEmprestimos}">
                <tr>
                    <td>${e.emprestimoId}</td>
                    <td>${e.tituloLivroAux}</td>
                    <td>${e.nomeUsuarioAux}</td>
                    <td>${e.dataEmprestimo}</td>
                    <td>${e.dataDevolucaoPrevista}</td>
                    
                    <td>
                        <c:choose>
                            <c:when test="${e.statusEmprestimo == 'ativo'}">
                                <span style="color:green; font-weight:bold">EM ABERTO</span>
                            </c:when>
                            <c:otherwise>${e.statusEmprestimo}</c:otherwise>
                        </c:choose>
                    </td>
                    
                    <td>
                        <c:if test="${e.statusEmprestimo == 'ativo' && sessionScope.usuarioLogado.tipoUsuario == 'admin'}">
                            <form action="EmprestimoController" method="post">
                                <input type="hidden" name="acao" value="devolver">
                                <input type="hidden" name="emprestimoId" value="${e.emprestimoId}">
                                <button type="submit" style="color:red">Devolver</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>