# parkingpi
Sistema de controle de vagas de estacionamento

Projeto para a disciplina de Computação Móvel do curso de Ciência da Computação 

Universidade Anhembi Morumbi

Elaborado por:

 - ANA CAROLINA DE BRANCO - 20283049
 - FERNANDA LIVIERO FERNANDES POLO - 20584180
 - LEANDRO FORCEMO DE OLIVEIRA - 20574011
 - MARCOS ANTONIO LEITE ROCHA - 20558102
 - RUBENS DIAS NETO - 20548777

1 INTRODUÇÃO

Este documento descreve em detalhes o projeto da disciplina Computação móvel do curso Ciência da Computação para controle de vagas de estacionamento. Os estacionamentos no modelo tradicional não possuem controle efetivo de ocupação ocupação, alguns estacionamentos modernos, normalmente encontrados em shoppings de grandes cidades, possuem sistemas que indicam a quantidade de vagas disponíveis por piso de estacionamento, mas nenhum destes modelos de estacionamento são integrados a internet e tampouco compartilham a informação. Em vista da constante mudança de mercado e comportamento dos consumidores, surge à necessidade de mudar o modelo de operação e gerenciamento dos estacionamentos para atender o mercado no qual o consumidor está a cada dia que passa mais exigente e necessita de ter informação atualizada utilizando dos veículos digitais de comunicação através da internet para escolher a melhor opção e por fim tomar a decisão adequada a suas necessidades. Baseado nisto, o sistema de controle de vagas para estacionamento, tem como principal objetivo apoiar no gerenciamento das vagas, permitindo que seu proprietário possua as informações atualizadas e em tempo real a fim de apoiar na tomada de decisões e compartilhamento de informação, vagas, com seus respectivos clientes. Além disto, o projeto reduzirá os custos, mitigando os riscos, ajudando-os a se concentrar em seu core business e competências.

1.1 PÚBLICO ALVO

	Este documento se destina aos arquitetos de software, engenheiros de software, analista e administradores de banco de dados.

1.2 VISÃO GERAL DO DOCUMENTO

Este documento realiza a elicitação dos documentos necessários a fim de desenvolver um sistema para controle de vagas de estacionamento, referente a disciplina Computação Móvel. Logo abaixo descrevemos em detalhe os itens necessários no desenvolvimento do projeto.

	Escopo: Descreve em detalhe o escopo do projeto destacando suas principais funcionalidades e modelo de operação;
	Regra de Negócio: Descreve as regras e premissas a serem contempladas durante o desenvolvimento do sistema a fim de garantir a correta operação do negócio;
	Requisitos Funcionais: Descrevem explicitamente as funcionalidades e serviços do sistema;
	Requisitos Não Funcionais: Definem propriedades e restrições do sistema, processamento, memória, versão de software, entre outros;
	DER: Diagrama Entidade Relacionamento é o modelo utilizado para representar o primeiro passo da estrutura de um Banco de Dados.
	MER: Modelo Entidade Relacionamento é um modelo conceitual utilizado na Engenharia de Software para descrever os objetos (entidades) envolvidos em um domínio de negócios, com suas características (atributos) e como elas se relacionam entre si (relacionamentos);
	Diagrama de Sequência: Descreve a perspectiva temporal de um sistema, representando cenários do caso de uso.
	Diagrama de Atividade: Descreve os passos a serem percorridos para a conclusão de um método específico do sistema.


2 ESCOPO

Este documento descreve em detalhe o escopo do projeto controle de vagas para estacionamento que conta com aplicação desenvolvida para a plataforma Android para o processo de visualização da situação das vagas do estacionamento, ou seja, exibe se a vaga está ocupada ou disponível, assim como a quantidade de vagas ocupadas e disponíveis. Esta informação é disponibilizada em tempo real, com a finalidade de manter os proprietários e clientes do estacionamento atualizados através de um aplicativo a ser instalado em smartphones, tablets. 
O projeto possui seis sensores de distância ultrassónico, modelo HC-SR04 capaz de medir distâncias de 2cm a 4m com ótima precisão. Além disso, esse modulo possui um circuito pronto com emissor e receptor acoplados e 4 pinos (VCC, Trigger, ECHO, GND) para medição. O sensor emitirá uma onda sonora que ao encontrar um obstáculo rebaterá de volta em direção ao módulo no qual indicará a presença de veiculo em uma especifica vaga, uma vez que cada sensor representa uma vaga no estacionamento.
Esta informação é encaminhada para um Raspberry Pi no qual, existe código escrito em Python para tratar a informação enviada pelo sensor e então enviar dos dados para o banco de dados Firebase, hospedado na nuvem. Basicamente é atualizado o status de cada vaga, onde 0 significa que a vaga está disponível e 1 significa que a vaga esta ocupada. 
O Raspberry Pi é um microcomputador de reduzidas dimensões, do tamanho de um cartão de crédito, desenvolvido pela Raspberry Pi Foundation (http://www.raspberrypi.org/) e pela Universidade de Cambridge, que foi criado com o intuito de promover o estudo da ciência da computação nas escolas do ensino básico ao ensino secundário. Firebase é uma plataforma do Google.
Uma vez que a informação é armazenada no banco de dados a aplicação desenvolvida através da plataforma Android Studio, consulta a situação das vagas do estacionamento, desta forma, é possível identificar se a vaga está ocupada ou disponível de forma amigável e de qualquer lugar desde que se possua conexão com a Internet.
Importante ressaltar que o projeto contempla somente o controle de vagas para estacionamento e no futuro poderão ser adicionadas novas funcionalidades, uma vez que podemos ver claramente o aumento de soluções que utilizam tecnologia IoT, do qual destacamos as seguintes funcionalidades, por exemplo, a navegação do automóvel até a vaga mais próxima disponível, segurança onde o proprietário do automóvel pode receber um alerta caso o sensor da vaga não detecte mais o automóvel estacionado, entre outras funcionalidades. 
Portanto, uma vez que o objetivo do estacionamento é atender o público que utiliza meios digitais, através da internet para obter informações em tempo real e além disto, permitir uma gestão eficiente para os proprietários do estacionamento, o controle de vagas de estacionamento atende completamente os requisitos técnicos no qual garante total visibilidade da situação de cada vaga do estacionamento.



3 REQUISITOS DE SOFTWARE E FUNCIONALIDADES

Requisito de software e funcionalidades detalha as necessidades dos usuários e clientes, exigências do negócio, desejos da empresa, solicitação da empresa para o desenvolvimento do controle de vagas para estacionamento. O software deverá atender estas necessidades, exigências, desejos e solicitações, e materializar isso em um aplicativo. O requisito de software para controle de vagas é dividido em três partes, regra de negócio, requisitos funcionais e requisitos não funcionais.

IMAGEM REQUISITO DE SOFTWARE  -- TODO

3.1 REGRAS DE NEGÓCIO

	Descreve as regras, premissas e restrições, a serem contempladas durante o desenvolvimento do sistema, a fim de garantir a correta operação do negócio. Ressaltamos que para o projeto não consideraremos o desenvolvimento das regras de negócio relacionados ao cadastro de estacionamento e cadastro de vagas. Para demonstração do projeto estamos considerando um único estacionamento.
Tabela 1 – Premissas do Sistema
Premissa	Módulo	Descrição
001	Cadastro Estacionamento	O cadastro de estacionamento irá gerenciar os dados corporativos de cada estacionamento. 
002	Cadastro de Vagas	Cada vaga possuirá seu respectivo sensor.
003	Cadastro de Vagas	Associar a vaga a um estacionamento.
003	Consulta vaga Estacionamento	Será exibido a situação atual das vagas do estacionamento.
004	Cadastro de usuários	O cadastro de usuários servirá para controlar a autorização de acesso ao sistema.
Fonte: Elaborado pelos Autores (2017)
Tabela 2 – Restrições do Sistema
 
Fonte – Elaborado pelos Autores (2017)
