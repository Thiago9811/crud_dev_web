<!DOCTYPE html>
<html>
<head>
    <title>Biblioteca</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        /* --- FUNDO GERAL --- */
        body {
            font-family: sans-serif;
            margin: 0;
            padding: 0;
            background-image: url("https://ecoscotidianos.wordpress.com/wp-content/uploads/2013/11/timbuktu-tombuctc3ba.jpg");
            background-size: cover; 
            background-repeat: no-repeat;
            background-position: center center;
            display: flex; 
            flex-direction: column; 
            min-height: 100vh;
            overflow: hidden; 
        }
        /* --- BARRA SUPERIOR BRANCA --- */
        .menu-container {
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            background: white;
            box-shadow: 0px 2px 8px rgba(0,0,0,0.25);
            position: fixed;
            top: 0;
            left: 0;
            z-index: 999;
            height: 70px;
            box-sizing: border-box;
        }
        /* --- LOGO + TEXTO --- */
        .menu-left {
            display: flex;
            align-items: center;
            gap: 10px;
            color: black;
            font-size: 22px;
            font-weight: bold;
        }
        .menu-left img {
            height: 50px;
        }
        /* --- BOTÕES DO TOPO --- */
        .menu-right {
            display: flex;
            gap: 30px;
            margin-right: 50px;
        }
        .top-btn {
            padding: 0;
            background: none;
            border: none;
            color: #333;
            cursor: pointer;
            font-weight: bold;
            font-size: 16px;
            transition: 0.2s;
        }
        .top-btn:hover {
            color: #007bff;
            text-decoration: underline;
        }

        /* --- TÍTULO + LOGIN + BARRA DE AÇÕES (LAYOUT PRINCIPAL) --- */
        .main-content-wrapper {
            display: flex;
            justify-content: space-between; 
            padding-top: 70px; 
            padding-bottom: 0; 
            padding-left: 80px;
            height: calc(100vh - 70px); 
            box-sizing: border-box;
            align-items: center; 
            width: 100%;
            flex: 1; 
        }
        
        .title-box {
            max-width: 600px;
            margin-right: 40px; 
            margin-top: 0; 
        }
        
        h1 {
            font-size: 56px;
            color: white;
            text-shadow: 3px 3px 6px black;
            margin: 0 0 20px 0;
            line-height: 1.2;
        }
        .subtitle {
            font-size: 20px;
            color: white;
            text-shadow: 2px 2px 4px black;
            line-height: 1.6;
            font-weight: normal;
        }
        /* --- ÁREA DE LOGIN --- */
        .login-area {
            margin-top: 30px;
        }
        .login-fields {
            display: flex;
            flex-direction: column;
            gap: 15px;
            max-width: 350px;
        }
        .field-group {
            display: flex;
            flex-direction: column;
        }
        label {
            color: white;
            font-size: 16px;
            margin-bottom: 5px;
            text-shadow: 2px 2px 4px black;
        }
        .field-group input {
            padding: 10px;
            border-radius: 6px;
            border: none;
            font-size: 16px;
        }
        /* Texto abaixo do login */
        .signup-text {
            margin-top: 10px;
            font-size: 14px;
            color: white;
            text-shadow: 2px 2px 4px black;
        }
        /* A palavra clicável CADASTRAR-SE */
        .signup-link {
            font-size: 16px;
            font-weight: bold;
            color: #00c3ff;
            cursor: pointer;
            transition: 0.2s;
        }
        .signup-link:hover {
            text-decoration: underline;
            color: #7ad9ff;
        }

        /* --- BARRA DE BOTÕES VERTICAL (FULL HEIGHT E TRANSPARENTE) --- */
        .action-bar {
            display: flex;
            flex-direction: column; 
            justify-content: space-evenly; 
            align-items: center;
            gap: 0; 
            padding: 0; 
            background: none;
            box-shadow: none; 
            box-sizing: border-box;
            width: 300px; 
            margin: 0; 
            height: 100%; 
            position: sticky; 
            top: 0;
            
            border-left: 1px solid rgba(0, 0, 0, 0.4); 
            padding-right: 50px;
        }

        .action-bar form {
            flex: none; 
            width: 100%;
        }

        .action-bar button {
            flex: none; 
            width: 250px;
            display: flex;
            flex-direction: column;
            justify-content: center; 
            align-items: center;
            gap: 10px; 
            padding: 35px 15px; 
            border: none;
            border-radius: 8px;
            background: rgba(212, 165, 116, 0.95);
            color: white; 
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.5);
            cursor: pointer;
            transition: 0.3s;
        }

        .action-bar button:hover {
            transform: scale(1.02); 
            background: #b8895a; 
            box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.3);
        }

        .action-bar .btn-title {
            font-size: 22px; 
            font-weight: bold;
            color: white; 
        }
        .action-bar .btn-description {
            font-size: 15px; 
            font-weight: normal;
            color: #eee; 
            line-height: 1.5; 
        }
        
        /* Oculta seções e rodapé já que a página não rola */
        section { display: none; }
        footer { display: none; }
    </style>
</head>
<body>
    <div class="menu-container">
        <div class="menu-left">
            A Biblioteca
        </div>
        <div class="menu-right">
            <form action="ListarLivros" method="get">
                <button type="submit" class="top-btn">Acervo</button>
            </form>
        </div>
    </div>
    
    <div class="main-content-wrapper">
        
        <div class="title-box">
            <h1>Biblioteca Comunitária do Ingá</h1>
            <p class="subtitle">O passado nos ensina, o presente nos transforma, o futuro depende de nós. Preserve o conhecimento. Visite-nos.</p>
        </div>
        
        <div class="action-bar">
            <form action="UsuariosServlet" method="get">
                <button type="submit">
                    <div class="btn-title">Central de Usuários</div>
                    <div class="btn-description">A peça mais importante do acervo de uma biblioteca comunitária é a própria comunidade. Organizar os registros e compreender o perfil dos seus membros é essencial.</div>
                </button>
            </form>
            <form action="ListarLivros" method="get">
                <button type="submit">
                    <div class="btn-title">Central de Livros</div>
                    <div class="btn-description">Ao compartilhar conhecimento, sempre aprendemos algo novo. Ajude-nos a construir um acervo cada vez mais rico, diverso e completo.</div>
                </button>
            </form>
            <form action="EmprestimoController" method="get">
                <button type="submit">
                    <div class="btn-title">Central de Empréstimos</div>
                    <div class="btn-description">É preciso garantir que o conhecimento refresque e sacie outras pessoas ao longo do caminho. O zelo pelo acervo comunitário é um trabalho coletivo.</div>
                </button>
            </form>
        </div>
        
    </div>

    <section></section>
    <footer></footer>

</body>
</html>