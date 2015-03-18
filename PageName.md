# Introduction #

Add your content here.
# DATASOURCE #
> PARAR O JBOSS

> na jboss/modules criar a seguinte estrutura de diretorios:
> com/mysql/main
> baixar o drive do mysql link: http://dev.mysql.com/downloads/connector/j/
> no diretorioi criado tbm criar um arquivo com o nome "module.xml"
> > conteudo do arquivo:
> > <?xml version="1.0" encoding="UTF-8"?>


&lt;module xmlns="urn:jboss:module:1.0" name="com.mysql"&gt;


> > 

&lt;resources&gt;


> > > 

&lt;resource-root path="mysql-connector-java-5.1.30-bin.jar"/&gt;



> > 

&lt;/resources&gt;


> > 

&lt;dependencies&gt;


> > > 

&lt;module name="javax.api"/&gt;



> > 

&lt;/dependencies&gt;




&lt;/module&gt;




> Atenção troque "mysql-connector-java-5.1.30-bin.jar" pelo nome do arquivo que foi baixado.

> Alterar o arquivo jboss/standalone/configuration/standalone.xml
> > procurar por "drivers"
> > dentro dest tag colar:
> > 

&lt;driver name="mysql" module="com.mysql"&gt;


> > > 

&lt;driver-class&gt;

com.mysql.jdbc.Driver

&lt;/driver-class&gt;



> > 

&lt;/driver&gt;




> REINICIAR O JBOSS

> depois é só criar o DS pela interfacedo JBOSS

> Nome DS: SistemaNFeDS
> > java:jboss/datasources/SistemaNFe

> Conenction: jdbc:mysql://localhost:3306/nfe
> # FIM #

LINK com opções de icone:
http://jqueryui.com/themeroller/



#TODO

Todo:

Logado como Empresa

Telas:
> DashBoard:
> > Com informações agrupadas, exemplo Qtdade de Notas por Emitente


> Listagens:
> > Emitentes:
> > Notas:
> > Clientes:
> > Produtos:
> > Obs: Notas, clientes e produtos poder agrupar por emitente(combo)
> > > Em todas as listas ter a possibilidade de filtrar os registros que serão listados, tbm ordenação;

> Cadastros:
> > Emitentes:
> > Notas: ReadOnly, somente visualizar
> > Clientes:
> > Produtos:
> > Obs: Para clientes e Produtos ter somente edição, sem opção de "novo"

> Relatórios:
> > Tela de filtro(campos de acordo com o relatório)
> > Após listar os registros, permite ao usuário gerar gráfico ou relatório em tela.


Logado como gestor da ferramenta

Telas:

> DashBoard:
> > Resumo das empresas cadastradas;


> Listagens:
> > Empresas;
> > Usuários(**)**


> Cadastros
> > Empresas


> No cadastro da empresa:
> > ter opção de bloquear ou liberar;



Futuro:

> Adicionar uma forma de envio de mensagens do adminsitrados para as empresas cadastradas
> > Exemplo: Mensagens de parabens, dicas, ....

> Usuários e grupos adminstradores do sistema;
> Usuários e grupos das empresas(consultor, ...);

> Ter opção para o cliente efetuar download do XML;