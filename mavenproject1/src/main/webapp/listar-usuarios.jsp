<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${empty sessionScope.usuarioLogado}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <title>Listar Usuários</title>
        <style>
            body { font-family: sans-serif; padding: 20px; }
            .top-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
            .search-box { display: flex; gap: 10px; }
            table { width: 100%; border-collapse: collapse; margin-top: 10px; }
            th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }
            th { background-color: #f4f4f4; }
            .btn { padding: 8px 15px; text-decoration: none; color: white; border-radius: 4px; border: none; cursor: pointer; }
            .btn-blue { background-color: #007bff; }
            .btn-green { background-color: #28a745; }
            .btn-red { background-color: #dc3545; }
            .btn-grey { background-color: #6c757d; }
            .tag-multa { background-color: #ffcccc; color: red; padding: 3px 8px; border-radius: 10px; font-weight: bold; font-size: 12px; }
        </style>
    </head>
    <body>

        <div class="top-bar">
            <h1>Usuários do Sistema</h1>
            
            <a href="dashboard.jsp" class="btn btn-grey">Voltar ao Painel</a>
        </div>

        <div class="top-bar">
            <form action="UsuariosServlet" method="get" class="search-box">
                <input type="text" name="busca" placeholder="Buscar por nome..." style="padding: 5px;">
                <button type="submit" class="btn btn-blue">Pesquisar</button>
            </form>

            <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                <a href="CadastroUsuario" class="btn btn-green">+ Novo Usuário</a>
            </c:if>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CPF</th>
                    <th>Tipo</th>
                    <th>Situação (Empréstimos/Multas)</th> <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                        <th>Ações</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${listaDeUsuarios}">
                    <tr>
                        <td>${u.usuarioId}</td>
                        <td>${u.nome}</td>
                        <td>${u.email}</td>
                        <td>${u.cpfFormatado}</td>
                        <td>${u.tipoUsuario}</td>
                        
                        <td>
                            Livros com ele: <b>${u.qtdEmprestimosAtivos}</b>
                            <br>
                            
                            <c:if test="${u.temPenalidadePendente}">
                                <span class="tag-multa">MULTA PENDENTE</span>
                            </c:if>
                            <c:if test="${not u.temPenalidadePendente}">
                                <span style="color: green; font-size: 12px;">Nada consta</span>
                            </c:if>
                        </td>

                        <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                            <td>
                                <a href="EditarUsuario?id=${u.usuarioId}" style="color: blue;">Editar</a>
                                &nbsp;|&nbsp;
                                <a href="ExcluirUsuario?id=${u.usuarioId}" 
                                   onclick="return confirm('Tem certeza que deseja excluir ${u.nome}?');" 
                                   style="color: red;">Excluir</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>