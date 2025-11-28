<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Relatórios | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <nav class="navbar">
        <a href="dashboard.jsp" class="logo"><i class="fas fa-book-open"></i> Biblioteca</a>
        <div class="nav-links">
            <a href="dashboard.jsp">Voltar ao Painel</a>
        </div>
    </nav>

    <div class="container">
        <div class="page-header">
            <h1><i class="fas fa-chart-line"></i> Relatórios Administrativos</h1>
            <p>Visão geral do desempenho e status da biblioteca.</p>
        </div>

        <h3 style="color: var(--primary); border-left: 4px solid var(--accent); padding-left: 10px; margin-top: 40px;">
            <i class="fas fa-user-clock"></i> Empréstimos por Usuário
        </h3>
        <div class="table-responsive">
            <c:if test="${not empty relEmprestimos}">
                <table>
                    <thead>
                        <tr>
                            <th>Usuário</th>
                            <th style="text-align: center;">Total Empréstimos</th>
                            <th style="text-align: center;">Atrasados</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="linha" items="${relEmprestimos}">
                            <tr>
                                <td>${linha.usuario}</td>
                                <td style="text-align: center;"><b>${linha.totalEmprestimos}</b></td>
                                <td style="text-align: center;">
                                    <c:if test="${linha.atrasados > 0}">
                                        <span class="status-badge status-bad">${linha.atrasados}</span>
                                    </c:if>
                                    <c:if test="${linha.atrasados == 0}">
                                        <span class="status-badge status-ok">0</span>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty relEmprestimos}">
                <p style="text-align:center; color:#666; margin-top:20px;">Nenhum dado encontrado.</p>
            </c:if>
        </div>

        <h3 style="color: var(--primary); border-left: 4px solid var(--danger); padding-left: 10px; margin-top: 40px;">
            <i class="fas fa-exclamation-triangle"></i> Penalidades
        </h3>
        <div class="table-responsive">
            <c:if test="${not empty relPenalidades}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Usuário</th>
                            <th>Tipo</th>
                            <th>Valor</th>
                            <th>Status</th>
                            <th>Data</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="linha" items="${relPenalidades}">
                            <tr>
                                <td>#${linha.penalidadeId}</td>
                                <td>${linha.usuario}</td>
                                <td>${linha.tipo}</td>
                                <td>R$ ${linha.valor}</td>
                                <td>
                                    <span class="status-badge ${linha.status == 'Paga' ? 'status-ok' : 'status-bad'}">
                                        ${linha.status}
                                    </span>
                                </td>
                                <td>${linha.data}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty relPenalidades}">
                <p style="text-align:center; color:#666; margin-top:20px;">Nenhuma penalidade registrada.</p>
            </c:if>
        </div>

        <h3 style="color: var(--primary); border-left: 4px solid var(--success); padding-left: 10px; margin-top: 40px;">
            <i class="fas fa-boxes"></i> Estoque de Livros
        </h3>
        <div class="table-responsive">
            <c:if test="${not empty relEstoque}">
                <table>
                    <thead>
                        <tr>
                            <th>Livro</th>
                            <th style="text-align: center;">Total</th>
                            <th style="text-align: center;">Disponível</th>
                            <th style="text-align: center;">Emprestados</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="linha" items="${relEstoque}">
                            <tr>
                                <td>${linha.titulo}</td>
                                <td style="text-align: center;">${linha.total}</td>
                                <td style="text-align: center; color: var(--success); font-weight: bold;">${linha.disponivel}</td>
                                <td style="text-align: center;">${linha.emprestados}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        
        <div style="margin-top: 40px; text-align: center;">
            <a href="dashboard.jsp" class="btn btn-outline">Voltar ao Painel</a>
        </div>
    </div>
</body>
</html>