<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope.usuarioLogado}">
    <c:redirect url="login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <title>Usuários | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <nav class="navbar">
        <a href="dashboard.jsp" class="logo"><i class="fas fa-book-open"></i> Biblioteca</a>
        <div class="nav-links">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="UsuariosServlet" class="active">Usuários</a>
        </div>
    </nav>

    <div class="container" style="max-width: 1200px;">
        <div class="top-actions">
            <div>
                <h1 style="margin:0; color: var(--primary);">Leitores e Admins</h1>
                <p style="margin:0; color: #666;">Gerencie quem acessa o sistema</p>
            </div>
            
            <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                <a href="CadastroUsuario" class="btn btn-primary"><i class="fas fa-user-plus"></i> Novo Usuário</a>
            </c:if>
        </div>

        <!-- Barra de Busca -->
        <div style="background: white; padding: 15px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.05);">
            <form action="UsuariosServlet" method="get" class="search-bar">
                <input type="text" name="busca" placeholder="Buscar por nome ou email...">
                <button type="submit" class="btn btn-accent"><i class="fas fa-search"></i></button>
            </form>
        </div>

        <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome / Email</th>
                        <th>CPF / Tel</th>
                        <th>Perfil</th>
                        <th>Status</th>
                        <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                            <th style="text-align: right;">Ações</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${listaDeUsuarios}">
                        <tr>
                            <td>#${u.usuarioId}</td>
                            <td>
                                <strong>${u.nome}</strong><br>
                                <small style="color: #666">${u.email}</small>
                            </td>
                            <td>
                                ${u.cpfFormatado}<br>
                                <small>${u.telefone}</small>
                            </td>
                            <td>
                                <span class="status-badge" style="background: ${u.tipoUsuario == 'ADMIN' || u.tipoUsuario == 'admin' ? '#333' : '#eee'}; color: ${u.tipoUsuario == 'ADMIN' || u.tipoUsuario == 'admin' ? '#fff' : '#333'};">
                                    ${u.tipoUsuario}
                                </span>
                            </td>
                            <td>
                                <div style="font-size: 0.9rem;">Empréstimos: <b>${u.qtdEmprestimosAtivos}</b></div>
                                <c:if test="${u.temPenalidadePendente}">
                                    <span class="status-badge status-bad" style="margin-top: 5px; display: inline-block;">Multa Pendente</span>
                                </c:if>
                                <c:if test="${not u.temPenalidadePendente}">
                                    <span style="color: var(--success); font-size: 0.8rem;"><i class="fas fa-check"></i> Regular</span>
                                </c:if>
                            </td>

                            <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                                <td style="text-align: right;">
                                    <a href="EditarUsuario?id=${u.usuarioId}" class="btn btn-outline btn-sm">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <a href="ExcluirUsuario?id=${u.usuarioId}" 
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Tem certeza que deseja excluir ${u.nome}?');">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>