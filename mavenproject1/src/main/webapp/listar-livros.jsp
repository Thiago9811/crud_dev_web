<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Acervo | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <nav class="navbar">
        <a href="dashboard.jsp" class="logo"><i class="fas fa-book-open"></i> Biblioteca</a>
        <div class="nav-links">
            <a href="dashboard.jsp">Dashboard</a>
        </div>
    </nav>

    <div class="container" style="max-width: 1200px;">
        <div class="top-actions">
            <div>
                <h1 style="margin:0; color: var(--primary);">Acervo de Livros</h1>
                <p style="margin:0; color: #666;">Gerencie o catálogo da biblioteca</p>
            </div>
            <form action="CadastroLivroController" method="get">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Novo Livro
                </button>
            </form>
        </div>
        
        <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Autor / Editora</th>
                        <th>Ano</th>
                        <th>ISBN / Gênero</th>
                        <th style="text-align: center;">Estoque</th>
                        <th style="text-align: right;">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="l" items="${listaDeLivros}">
                        <tr>
                            <td>#${l.livroId}</td>
                            <td>
                                <strong>${l.titulo}</strong>
                            </td>
                            <td>
                                ${l.autor}<br>
                                <small style="color:#777">${l.editora}</small>
                            </td>
                            <td>${l.anoPublicacao}</td>
                            <td>
                                ${l.isbn}<br>
                                <small class="status-badge" style="background: #eee; color: #555;">${l.genero}</small>
                            </td>
                            <td style="text-align: center;">
                                <div style="font-weight: bold;">${l.quantidadeDisponivel} / ${l.quantidadeTotal}</div>
                                <c:if test="${l.quantidadeDisponivel == 0}">
                                    <span class="status-badge status-bad">Esgotado</span>
                                </c:if>
                            </td>

                            <td style="text-align: right;">
                                <a href="EditarLivro?id=${l.livroId}" class="btn btn-outline btn-sm" title="Editar">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <a href="ExcluirLivro?id=${l.livroId}" 
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('Tem certeza que deseja excluir este livro?');"
                                   title="Excluir">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        
        <div style="margin-top: 30px; text-align: center;">
            <a href="dashboard.jsp" class="btn btn-outline">Voltar ao Painel</a>
        </div>
    </div>
</body>
</html>