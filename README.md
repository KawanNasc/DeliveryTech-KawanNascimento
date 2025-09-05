<h1>Delivery Tech API</h1>
<p>Sistema de delivery desenvolvido c/ Spring Boot e Java 21.</p>

<h2> 👨‍💻 Desenvolvedor</h2>
    <p>Kawan Nascimento da Silva -
        TI56 02726 -
        ARQUITETURA DE SISTEMAS
    </p>

<h2>🚀 Tecnologias</h2>
<ul>
    <li><b>Java 21 LTS</b> (versão mais recente);</li>
    <li>Spring Boot 3.2.x;</li>
    <li>Spring Web;</li>
    <li>Spring Data JPA;</li>
    <li>H2 Database;</li>
    <li>Maven.</li>
</ul>

<h2>⚡ Recursos modernos utilizados</h2>
<ul>
    <li>Records (Java 14+);</li>
    <li>Text Blocks (Java 15+);</li>
    <li>Pattern Matching (Java 17+);</li>
    <li>Virtual Threads (Java 21)</li>
</ul>

<h2>🔧 Configuração</h2>
<ul>
    <li>Porta: 8080</li>
    <li>Banco: H2 em memória (Perfil: desenvolvimento)</li>
</ul>

<h2>🏃‍♂️ Como executar</h2>
<ol>
    <li><b>Pré-requisito:</b> JDK 21 instalado</li>
    <li>Clone o repositório;</li>
    <li>Execute: `./mvnw spring-boot:run`;</li>
    <li>Acesse nossos endpoints pela web ou Postman. 
        <br/>
        (Em caso do Postman - Poderá a variável global 
        <i>{{baseURL}}</i>
        /<b>endpoint que deseja consultar.</b></li>
</ol>

<h2>📋 Endpoints (Em desenvolvimento)</h2>
<ul>
<li><h3>Endpoints de verificação</h3></li>
    <ul>
        <li>GET</li>
        <ul>
            <li>/health: Status da aplicação (inclui versão Java);</li>
            <li>/info: Informações da aplicação;</li>
            <li>/h2-console: Console do banco H2 (Na web);</li>
        </ul>
    </ul>

<li><h3>Endpoints de /client/</h3></li>
    <p>Parâmetros: <i>name, email, phone e address</i></p>
    <ul>
        <li>POST: 
            Inserir valores dos parâmetros no <i>body.</i>
        </li>
        <li>GET</li>
        <ul>
            <li>/: Lista todos os clintes;</li>
            <li>{id}: Procurar cliente/id;</li>
            <li>{email}: Procurar cliente/e-mail;</li>
        </ul>
        <li>PUT</li>
        <ul>
            <li>{id}: Id do cliente a ser atualizado no <i>body</i>;</li>
        </ul>
    </ul>
    <h4>Apenas funcionando com implementação de models</h4>
    <ul>
        <li>GET -  {name}: Procurar cliente/nome;</li>
        <li>DELETE - {id}: Id do cliente a ser excluído.</li>
    </ul>

<li><h3>Endpoints de /request/</h3></li>
    <p>Parâmetros: <i>clientId e restaurantId</i>.</p>
    <ul>
        <li>POST: 
            Inserir valores dos parâmetros no <i>body.</i>
        </li>
        <ul>
            <li>?clientId= &restaurantId= : 
            <br/>
            Criar pedido especificando cliente que pediu e restaurante atendente;</li>
        </ul>
        <li>GET</li>
        <ul>
            <li>{id}: Id do pedido a ser pesquisado;</li>
            <li>client/{id}: Listar pedidos/id do cliente;</li>
        </ul>
        <li>PUT</li>
        <ul>
            <li>{id}/status: Atualizar status do pedido/id</li>
        </ul>
    </ul>
    <h4>Apenas funcionando com implementação de models</h4>
    <ul>
        <li>/1/itens?product_id= &quantity= :
        <br/>
        Adicionar o id do item e quantia a ser selecionado;</li>
        <li>PUT - {id}/confirm: Confirmar pedido/id</li>
        <li>PUT - {id}/cancel: Cancelar pedido/id</li>
    </ul>
</ul>