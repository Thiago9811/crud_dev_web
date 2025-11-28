<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Empréstimos | Biblioteca</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>

    <nav class="navbar">
        <a href="dashboard.jsp" class="logo">
            <i class="fas fa-book-open"></i> Biblioteca
        </a>
        <div class="nav-links">
            <a href="dashboard.jsp">Dashboard</a>
            <a href="UsuariosServlet">Usuários</a>
            <a href="ListarLivros">Livros</a>
            <a href="EmprestimoController" class="active">Empréstimos</a>
        </div>
    </nav>

    <div class="container">
        <!-- CABEÇALHO COM BOTÃO DE RELATÓRIO -->
        <div class="page-header" style="display: flex; justify-content: space-between; align-items: center;">
            <h1 style="margin:0;">Controle de Empréstimos</h1>
            
            <!-- Botão de Relatórios (Apenas Admin) -->
            <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'admin' || sessionScope.usuarioLogado.tipoUsuario == 'ADMIN'}">
                <a href="RelatorioController" class="btn btn-outline">
                    <i class="fas fa-chart-line"></i> Relatórios Gerenciais
                </a>
            </c:if>
        </div>

        <!-- MENSAGEM DO SISTEMA -->
        <c:if test="${not empty msgSistema}">
            <c:set var="alertClass" value="${tipoMsg == 'success' ? '#d4edda' : (tipoMsg == 'danger' ? '#f8d7da' : '#fff3cd')}" />
            <c:set var="textClass" value="${tipoMsg == 'success' ? '#155724' : (tipoMsg == 'danger' ? '#721c24' : '#856404')}" />
            <c:set var="borderClass" value="${tipoMsg == 'success' ? '#c3e6cb' : (tipoMsg == 'danger' ? '#f5c6cb' : '#ffeeba')}" />
            
            <div style="padding: 15px; border-radius: 8px; margin-bottom: 20px; font-weight: bold; background-color: ${alertClass}; color: ${textClass}; border: 1px solid ${borderClass};">
                <i class="fas ${tipoMsg == 'success' ? 'fa-check-circle' : (tipoMsg == 'danger' ? 'fa-times-circle' : 'fa-exclamation-triangle')}"></i>
                ${msgSistema}
            </div>
        </c:if>

        <!-- NOVO EMPRÉSTIMO COM AUTOCOMPLETE -->
        <div style="background: #f8f9fa; padding: 25px; border-radius: 8px; border: 1px solid #ddd; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0,0,0,0.05);">
            <h3 style="margin-top: 0; color: #007bff; border-bottom: 2px solid #e9ecef; padding-bottom: 10px;">
                <i class="fas fa-plus-circle"></i> Novo Empréstimo
            </h3>
            
            <form action="EmprestimoController" method="post" style="margin-top: 20px;">
                <input type="hidden" name="acao" value="emprestar">
                
                <div class="form-grid">
                    <!-- Campo de Busca Usuário -->
                    <div class="form-group">
                        <label><i class="fas fa-user"></i> Nome do Usuário</label>
                        <input type="text" name="nomeUsuario" list="listaUsuarios" placeholder="Comece a digitar o nome..." required autocomplete="off">
                        <datalist id="listaUsuarios">
                            <c:forEach var="nome" items="${sugestaoUsuarios}">
                                <option value="${nome}"></option>
                            </c:forEach>
                        </datalist>
                    </div>
                    
                    <!-- Campo de Busca Livro -->
                    <div class="form-group">
                        <label><i class="fas fa-book"></i> Título do Livro</label>
                        <input type="text" name="tituloLivro" list="listaLivros" placeholder="Comece a digitar o título..." required autocomplete="off">
                        <datalist id="listaLivros">
                            <c:forEach var="titulo" items="${sugestaoLivros}">
                                <option value="${titulo}"></option>
                            </c:forEach>
                        </datalist>
                    </div>
                </div>
                
                <button type="submit" class="btn btn-green btn-block" style="margin-top: 10px;">
                    Confirmar Empréstimo <i class="fas fa-check"></i>
                </button>
            </form>
        </div>

        <h3><i class="fas fa-history"></i> Histórico de Empréstimos</h3>
        <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Livro</th>
                        <th>Usuário</th>
                        <th>Data Emp.</th>
                        <th>Previsão</th>
                        <th>Status</th>
                        <th>Ação</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="e" items="${listaEmprestimos}">
                        <tr>
                            <td>#${e.emprestimoId}</td>
                            <td><strong>${e.tituloLivroAux}</strong></td>
                            <td>${e.nomeUsuarioAux}</td>
                            <td>${e.dataEmprestimo}</td>
                            <td>${e.dataDevolucaoPrevista}</td>
                            
                            <td>
                                <c:choose>
                                    <c:when test="${e.statusEmprestimo == 'ativo'}">
                                        <span class="status-badge status-ok">EM ABERTO</span>
                                    </c:when>
                                    <c:when test="${e.statusEmprestimo == 'atrasado'}">
                                        <span class="status-badge status-bad">ATRASADO</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status-badge" style="background: #eee; color: #555;">${e.statusEmprestimo}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            
                            <td>
                                <c:if test="${(e.statusEmprestimo == 'ativo' || e.statusEmprestimo == 'atrasado') && sessionScope.usuarioLogado.tipoUsuario == 'admin'}">
                                    <form action="EmprestimoController" method="post" style="margin:0;">
                                        <input type="hidden" name="acao" value="devolver">
                                        <input type="hidden" name="emprestimoId" value="${e.emprestimoId}">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Confirmar devolução do livro?');">
                                            <i class="fas fa-undo"></i> Devolver
                                        </button>
                                    </form>
                                </c:if>
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